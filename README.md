# Checkers AI in Java using Minimax with Alpha-Beta Pruning

## Summary

* This implementation of a Checkers game uses a the Minimax algorithm with alpha beta pruning and a heuristic function 
* to determine the best move taken for the AI. There are different levels of difficulty based on depth limited search, and the heuristic function.
* JavaFX and FXML were used to build the GUI, and a Controller class allows user interaction.

## How to run

* This uses JavaFX and FXML
* Java version: 8
* JavaFX version: 8
* JDK: 1.8
* JRE: "Base JRE"

### Option 1:
1. Download Netbeans
2. Download Java 8
3. Open up Netbeans project
4. Set Netbeans project preferences to use Java 8 
5. Set language to level 8


### Option 2:
1. Open Citrix
2. Open intelliJ


### Option 3:
1. Get JAR file
2. Run JAR file

## How to play/Rules

* Open the game and select your difficulty level
* The User (Red pieces) starts first
* Hover over and then click a piece to select it
* Then select a destination tile to move it to
* If your move isn't valid, an alert will explain why
* If you're in the "Easy" mode, legal moves will be suggested to you
* After each user move, the AI will take their turn
* Rules are included in the game in the 'Rules' menu item
* If you're stuck, Hints can be accessed from the 'Hints' menu item
* To restart the game, press start new game in 'New Game' menu item
* If the piece has become a King, it will have a blue ring around the edge
* A game is won by a player when there are no more opponent's pieces left on the board, or when there are no more available moves, and the player has more pieces on the current board.
