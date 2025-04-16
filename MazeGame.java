import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class MazeGame extends JFrame implements KeyListener {
    private static final int ROWS = 20;
    private static final int COLS = 20;
    private static final int CELL_SIZE = 25;

    private int[][] maze;
    private int playerRow, playerCol;
    private int endRow, endCol;
    private boolean gameWon = false;
    private boolean gameOver = false;

    private int timeLeft = 60;
    private javax.swing.Timer countdownTimer;
    private JLabel timerLabel;
    private GamePanel gamePanel;

    public MazeGame() {
        setTitle("Maze Game");
        setSize(COLS * CELL_SIZE + 50, ROWS * CELL_SIZE + 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        maze = new int[ROWS][COLS];
        generateMazeWithDFS();

        addKeyListener(this);
        setFocusable(true);

        gamePanel = new GamePanel();
        add(gamePanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton newMazeButton = new JButton("Generate New Maze");
        JButton resetButton = new JButton("Reset Player");
        timerLabel = new JLabel("Time Left: 60s");
        timerLabel.setForeground(Color.BLUE);

        newMazeButton.addActionListener(e -> {
            generateMazeWithDFS();
            gamePanel.repaint();
            requestFocusInWindow();
        });

        resetButton.addActionListener(e -> {
            resetPlayer();
            gamePanel.repaint();
            requestFocusInWindow();
        });

        bottomPanel.add(newMazeButton);
        bottomPanel.add(resetButton);
        bottomPanel.add(timerLabel);

        add(bottomPanel, BorderLayout.SOUTH);

        startTimer();
    }

    class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int offsetY = 10;

            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++) {
                    int x = j * CELL_SIZE;
                    int y = i * CELL_SIZE + offsetY;

                    if (maze[i][j] == 1) {
                        g.setColor(Color.BLACK);
                        g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                    } else {
                        g.setColor(Color.WHITE);
                        g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                        g.setColor(Color.GRAY);
                        g.drawRect(x, y, CELL_SIZE, CELL_SIZE);
                    }
                }
            }
            g.setColor(Color.BLUE);
            g.fillOval(playerCol * CELL_SIZE, playerRow * CELL_SIZE + offsetY, CELL_SIZE, CELL_SIZE);g.setColor(Color.RED);
            g.fillOval(endCol * CELL_SIZE, endRow * CELL_SIZE + offsetY, CELL_SIZE, CELL_SIZE);
            if (gameWon) {
                g.setColor(Color.GREEN);
                g.setFont(new Font("Arial", Font.BOLD, 24));
                g.drawString("You Won!", COLS * CELL_SIZE / 2 - 50, ROWS * CELL_SIZE / 2);
            }
            if (gameOver && !gameWon) {
                g.setColor(Color.RED);
                g.setFont(new Font("Arial", Font.BOLD, 24));
                g.drawString("Time's Up! You Lose!", COLS * CELL_SIZE / 2 - 100, ROWS * CELL_SIZE / 2);
            }
        }
         @Override
        public Dimension getPreferredSize() {
            return new Dimension(COLS * CELL_SIZE, ROWS * CELL_SIZE + 20);
        }
    }
    private void generateMazeWithDFS() {
        for (int i = 0; i < ROWS; i++) {
            Arrays.fill(maze[i], 1);
        }

       carvePath(0, 0);
        playerRow = 0;
        playerCol = 0;
        endRow = ROWS - 2;
        endCol = COLS - 2;
        maze[endRow][endCol] = 0; 

        gameWon = false;
        gameOver = false;
        timeLeft = 60;
        if (countdownTimer != null) countdownTimer.stop();
        startTimer();
    }
    private void carvePath(int row, int col) {
        maze[row][col] = 0; // Mark current cell as path

        
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        shuffleDirections(directions);
        for (int[] dir : directions) {
            int newRow = row + 2 * dir[0];
            int newCol = col + 2 * dir[1];

            if (isValidCell(newRow, newCol) && maze[newRow][newCol] == 1) {
                maze[row + dir[0]][col + dir[1]] = 0; 
                carvePath(newRow, newCol); 
            }
        }
    }
    private void shuffleDirections(int[][] directions) {
        Random rand = new Random();
        for (int i = directions.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            int[] temp = directions[index];
            directions[index] = directions[i];
            directions[i] = temp;
        }
    }
    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < ROWS && col >= 0 && col < COLS;
    }
    private void resetPlayer() {
        playerRow = 0;
        playerCol = 0;
        gameWon = false;
        gameOver = false;
        timeLeft = 60;
        if (countdownTimer != null) countdownTimer.stop();
        startTimer();
    }
    private void startTimer() {
        countdownTimer = new javax.swing.Timer(1000, e -> {
            if (!gameWon && !gameOver) {
                timeLeft--;
                timerLabel.setText("Time Left: " + timeLeft + "s");
                if (timeLeft <= 0) {
                    gameOver = true;
                    countdownTimer.stop();
                    gamePanel.repaint();
                }
            }
        });
        countdownTimer.start();
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (gameWon || gameOver) return;

        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP && isValidMove(playerRow - 1, playerCol)) playerRow--;
        else if (key == KeyEvent.VK_DOWN && isValidMove(playerRow + 1, playerCol)) playerRow++;
        else if (key == KeyEvent.VK_LEFT && isValidMove(playerRow, playerCol - 1)) playerCol--;
        else if (key == KeyEvent.VK_RIGHT && isValidMove(playerRow, playerCol + 1)) playerCol++;

        if (playerRow == endRow && playerCol == endCol) {
            gameWon = true;
            countdownTimer.stop();
        }
        gamePanel.repaint();
    }
    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < ROWS && col >= 0 && col < COLS && maze[row][col] == 0;
    }
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MazeGame().setVisible(true));
    }
}
