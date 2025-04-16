# Maze Game

A simple maze game built using Java Swing. The objective of the game is to navigate a player from the starting point (top-left) to the end point (near the bottom-right) within a time limit. The game generates a random maze using Depth-First Search (DFS) algorithm.

## Features

- **Random Maze Generation**: The maze is generated using the Depth-First Search (DFS) algorithm.
- **Player Movement**: The player can move using the arrow keys (Up, Down, Left, Right).
- **Timer**: A countdown timer of 60 seconds is displayed. The game ends when the timer runs out or when the player reaches the end point.
- **Win Condition**: The player wins if they reach the end point before the timer runs out.
- **Game Reset**: The game can be reset or a new maze can be generated at any time.

## Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/yourusername/maze-game.git
    ```
   
2. Compile the Java code:
    ```bash
    javac MazeGame.java
    ```

3. Run the game:
    ```bash
    java MazeGame
    ```

## Usage

- **Arrow Keys**: Use the arrow keys (Up, Down, Left, Right) to move the player through the maze.
- **New Maze**: Click the "Generate New Maze" button to create a new random maze.
- **Reset Player**: Click the "Reset Player" button to reset the player’s position to the start.
- **Timer**: The game will show the remaining time. If the timer reaches 0, the game ends, and the player loses.
- **Winning**: You win by reaching the red "End Point" before time runs out.

## Controls

- **Up Arrow**: Move up.
- **Down Arrow**: Move down.
- **Left Arrow**: Move left.
- **Right Arrow**: Move right.

## Code Explanation

- **Maze Generation**: The maze is represented as a 2D array where `1` is a wall and `0` is an open path. The Depth-First Search (DFS) algorithm is used to carve paths in the maze.
- **Player Movement**: The player is represented as a blue circle and can be moved using the arrow keys. The game checks for valid moves (ensuring the player stays on the path).
- **Game Timer**: The game runs a countdown timer, and the player must reach the end point before the time runs out.

## Example

![Game Screenshot](game_screenshot.png)

## Contributions

Feel free to fork this repository, improve upon it, or open issues if you find bugs or have feature requests. Contributions are welcome!

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
