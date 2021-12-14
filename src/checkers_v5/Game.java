package checkers_v5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Write a description of class Game here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Game {

    // instance variables - replace the example below with your own
    int deCount, seCount, pCount;
    int depthLimit;

    Tile[] gameState;
    Tile[] MinimaxState;
    List<MovesAndScores> successorEvaluations;

    //To reference current objects
    Checker currentChecker;
    Checker capturedChecker;
    Tile currentTile;
    Tile selectedTile;

    String currentPlayer;
    Move bestMove;

    /**
     * Constructor for objects of class Game
     *
     *
     * @param gameState An array of 32 'Tile' objects, which represent the
     * potential positions on the board 32 are used in accordance with checker
     * notation
     * @param difficulty
     */
    public Game(Tile[] gameState, int difficulty) {
        currentPlayer = "User";
        this.gameState = gameState;
        setDifficulty(difficulty);
    }
    /**
     * Updates the state representation with the current move
     *
     * @param checker The checker that has been moved
     * @param oldPosition The original checker position
     * @param newPosition The new position
     */
    public void move(Checker checker, Tile newTile, Tile[] state) {
        //System.out.println("MOVING: "+checker.toString());
        int oldPos = checker.getPosition();
        int newPos = newTile.getPosition();
        checker.move(newPos);
        state[newPos - 1].setChecker(checker);
        state[oldPos - 1].removeChecker();
    }

    /**
     * Gets the legal moves for the given tile For each tile which is
     * surrounding, checks if the move is legal
     *
     * @param current
     * @param state
     * @return List of tile objects which can be moved to
     */
    public List<Move> getLegalMoves(Checker current, Tile[] state) {
        //Initialise list to hold legal moves
        List<Move> legalMoves = new ArrayList<>();
        //Get the neighbouring positions
        List<Integer> neighbours = current.getNeighbouringPositions();
        for (int i : neighbours) {
            Tile tile = state[i - 1]; //Use -1 as the stored positions are in checker notation, and array starts from index 0
            if (tile.hasChecker() == false && current.getOwner().equals(currentPlayer) ) { //current.getOwner().equals(player) &&
                legalMoves.add(new Move(current, tile));
            }
            if (tile.hasChecker() == true && !tile.getChecker().getOwner().equals(current.getOwner())) {
                // If the tile is occupied by the opponent's checker, intialise newRow, newCol
                int newRow = 0;
                int newCol = 0;
                
                if (current.getRow() + 1 == tile.getRow() && current.getCol() + 1 == tile.getCol()) {
                    // If the opponent's checker is bottom right diagonal
                    newRow = tile.getRow() + 1;
                    newCol = tile.getCol() + 1;
                } else if (current.getRow() + 1 == tile.getRow() && current.getCol() - 1 == tile.getCol()) {
                    // If the opponent's checker is bottom left diagonal
                    newRow = tile.getRow() + 1;
                    newCol = tile.getCol() - 1;
                } else if (current.getRow() - 1 == tile.getRow() && current.getCol() + 1 == tile.getCol()) {
                    // If the opponent's checker is top right diagonal
                    newRow = tile.getRow() - 1;
                    newCol = tile.getCol() + 1;
                } else if (current.getRow() - 1 == tile.getRow() && current.getCol() - 1 == tile.getCol()) {
                    // If the opponent's checker is top left diagonal
                    newRow = tile.getRow() - 1;
                    newCol = tile.getCol() - 1;
                }
                for (Tile t : state) {
                    if (t.getRow() == newRow && t.getCol() == newCol && t.hasChecker() == false) {
                        //Iterate through the tiles to find the tile with the matching row and column values, to be added as a potential move
                        Move m = new Move(current, t);
                        //Set as a capturing move
                        m.setCapturingMove();
                        int capturedPos = tile.getPosition();

                        m.setCapturedChecker(gameState[capturedPos-1].getChecker());
                        System.out.println(m);
                        legalMoves.add(m);   
                    }
                }
            }
        }
        return legalMoves;
    }

    /**
     *
     * @param state
     * @return
     */
    public List<Move> getAllLegalMoves(Tile[] state) {
        List<Move> allMoves = new ArrayList<>();
        for (Tile t : state) {
            if (t.hasChecker()) {
                Checker c = t.getChecker();
                allMoves.addAll(getLegalMoves(c, state));
            }

        }
        return allMoves;
    }

    public String getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     *
     * @param checker
     */
    public void removeChecker(Checker checker) {
        for (Tile tile : gameState) {
            if (tile.hasChecker() && tile.getChecker().equals(checker)) {
                tile.removeChecker();
            }
        }
    }

    public List<Checker> getUserCheckers() {
        List<Checker> userCheckers = new ArrayList<>();
        for (Tile t : gameState) {
            if (t.getChecker().getOwner().equals("User")) {
                userCheckers.add(t.getChecker());
            }
        }
        return userCheckers;
    }

    public List<Checker> getCompCheckers() {
        List<Checker> compCheckers = new ArrayList<>();
        for (Tile t : gameState) {
            if (t.getChecker().getOwner().equals("Computer")) {
                compCheckers.add(t.getChecker());
            }
        }
        return compCheckers;
    }

    public List<Checker> getCheckers() {
        List<Checker> allCheckers = new ArrayList<>();
        for (Tile t : gameState) {
            if (t.hasChecker()) {
                allCheckers.add(t.getChecker());
            }
        }
        return allCheckers;
    }

    /**
     * Changes the string representing the current player to alternate turns
     *
     * @param currentPlayer
     */
    public void nextTurn(String currentPlayer) {
        if (currentPlayer.equals("User")) {
            currentPlayer = "Computer";
        } else {
            currentPlayer = "User";
        }
    }

    public void startEval() {
        deCount = 0;
        seCount = 0;
        pCount = 0;
        successorEvaluations = new ArrayList<>();
        MinimaxState = cloneState(gameState);
        currentPlayer = "Computer";
        //System.out.print("Start state: " + Arrays.toString(MinimaxState));
        minimaxAB2(depthLimit, currentPlayer, Integer.MIN_VALUE, Integer.MAX_VALUE, MinimaxState);
        //System.out.print("Final state: " + Arrays.toString(MinimaxState));
    }

    public Tile[] cloneState(Tile[] currentState) {
        Tile[] state = new Tile[32];
        for (int i = 0; i < currentState.length; i++) {
            state[i] = new Tile(currentState[i].position);
            if (currentState[i].hasChecker()) {
                state[i].setChecker(currentState[i].getChecker().cloneChecker());
            }
        }
        return state;
    }

    /**
     *
     *
     * @return
     */
    public Move getBestMove(String currentPlayer) {
        int max = -10000;
        int best = -1;
        Move tempBest = null;
        System.out.println("Successor Evaluations Size: " + successorEvaluations.size());
        for (int i = 0; i < successorEvaluations.size(); i++) {
            if (max <= successorEvaluations.get(i).score && currentPlayer.equals(successorEvaluations.get(i).player)) {
                max = successorEvaluations.get(i).score;
                System.out.print("Max: " + max);
                best = i;
                tempBest = successorEvaluations.get(i).getMove();
            }
        }
        //System.out.println("Successor evaluations: " + successorEvaluations.toString());
        //System.out.println("Best: " + best);
        return tempBest;
        //return successorEvaluations.get(best).getMove();
    }
    
     public int minimaxAB2(int depth, String currentPlayer, int alpha, int beta, Tile[] state) {
        Move tempBestMove = null;
        int bestScore;
        if (currentPlayer.equals("User")) {
            bestScore = Integer.MAX_VALUE;
        } else {
            bestScore = Integer.MIN_VALUE;
        }
        List<Move> legalMoves = getAllLegalMoves(state);
        if (depth == 0 || legalMoves.isEmpty()) {
            //Check if the tree has been traversed to specified level, or node is terminal node
            seCount++;
            //successorEvaluations.add(new MovesAndScores(bestScore, tempBestMove, currentPlayer));
            return calc_heuristic(state); // return static evaluation of node

        }
        if (currentPlayer.equals("User")) {
            //int maxScore = Integer.MIN_VALUE;      // User is minimizing player   
            Tile[] tempState = cloneState(state);
            List<Move> userMoves;
            userMoves = getUserMoves(legalMoves); // Generate available moves for player
            for (Move move : userMoves) {
                //System.out.println("move.checker: "+move.checker);
                Checker moving = move.getChecker().cloneChecker();
                //System.out.println("moving: "+moving);
                //System.out.println("move.newTile: "+move.newTile);
                Tile newTile = tempState[move.newTile.getPosition()-1];
                //System.out.println("newTile: "+newTile);
                deCount++;

                move(moving, newTile, tempState); //place piece at first available position
                int score = minimaxAB(depth - 1, "Computer", alpha, beta, tempState); // start recursion
                bestScore = Math.min(bestScore, score); //get best score

                if (beta > bestScore) {
                    beta = bestScore;
                    tempBestMove = move; 
                    //successorEvaluations.add(new MovesAndScores(score, tempBestMove, "User"));// save the best move temporarily
                }
                //Perform pruning if appropriate
                if (beta <= alpha) {
                    pCount++;
                    break;
                }
            }
            this.bestMove = tempBestMove;
            return bestScore;
        } else {
            //int minScore = Integer.MAX_VALUE; //Computer is maximizing player
            Tile[] tempState = cloneState(state);
            List<Move> compMoves = getCompMoves(legalMoves);
            for (Move move : compMoves) {
                deCount++; 
                Checker moving = move.checker.cloneChecker();
                Tile newTile = tempState[move.newTile.getPosition()-1];           
                move(moving, newTile, tempState);
                int score = minimaxAB(depth - 1, "User", alpha, beta, tempState);
                bestScore = Math.max(score, bestScore);

                if (alpha < bestScore) {
                    alpha = bestScore;
                    tempBestMove = new Move(move.getChecker(), move.getTile());
                    //successorEvaluations.add(new MovesAndScores(score, tempBestMove, "Computer"));
                }
                if (beta <= alpha) {
                    pCount++;
                    break;
                }
            }
            this.bestMove = tempBestMove;
            return bestScore;
        }
    }


    /**
     * Minimax algorithm with alpha beta pruning
     *
     * @param depth
     * @param currentPlayer
     * @param alpha
     * @param beta
     * @param state
     * @return
     */
    public int minimaxAB(int depth, String currentPlayer, int alpha, int beta, Tile[] state) {
        Move tempBestMove = null;
        int max_value = Integer.MIN_VALUE;
        int min_value = Integer.MAX_VALUE;
        
        alpha= Math.max(alpha, max_value);
        beta = Math.min(beta, min_value);
        List<Move> legalMoves = getAllLegalMoves(state);
        if (depth == 0 || legalMoves.isEmpty()) {
            seCount++;
            //successorEvaluations.add(new MovesAndScores(bestScore, tempBestMove));
            return calc_heuristic(state); // return static evaluation of node

        }
        //store all moves onto state
        
        if (currentPlayer.equals("User")) { // User is minimizing player
            List<Move> userMoves;
            userMoves = getUserMoves(legalMoves); // Generate available moves for player
            for (Move move : userMoves) {
                Tile[] tempState = cloneState(state);
                deCount++;
                Checker moving = tempState[move.checker.getPosition()-1].getChecker();
                Tile newTile = tempState[move.newTile.getPosition()-1];
                int originalPos = move.checker.getPosition(); // Get position of checker before move for undo move later
                Tile originalTile = tempState[originalPos - 1];
                //move(move.checker, move.newTile, tempState); //place piece at first available position
                move(moving, newTile, tempState);
                int score = minimaxAB(depth - 1, "Computer", alpha, beta, tempState); // start recursion
                
                min_value = Math.min(min_value, score);
                if (min_value == score) {
                    tempBestMove = move;
                    beta = Math.min(beta, min_value);
                }
                if (beta <= alpha) {
                    pCount++;
                    break;
                    
                }
            }
            this.bestMove = tempBestMove;
            return min_value;
        } else {
            //int minScore = Integer.MAX_VALUE; //Computer is maximizing player
            List<Move> compMoves = getCompMoves(legalMoves);
            for (Move move : compMoves) {
                Tile[] tempState = cloneState(state);
                deCount++;
                int originalPos = move.checker.getPosition(); //Store original position for later undoing the move
                Tile originalTile = tempState[originalPos - 1];
                move(move.checker, move.newTile, tempState);
                int score = minimaxAB(depth - 1, "User", alpha, beta, tempState);
                max_value = Math.max(score, max_value);

                if (max_value == score) {
                    tempBestMove = move;
                    alpha= Math.max(alpha, max_value);
                }
                if (beta <= alpha) {
                    pCount++;
                    break;
                }
            }
            this.bestMove = tempBestMove;
            return max_value;
        }
    }

    public Move getBestMove2() {
        return this.bestMove;
    }

    public List<Move> getUserMoves(List<Move> moves) {
        List<Move> userMoves = new ArrayList<>();
        for (int i = 0; i < moves.size(); i++) {
            Move move = moves.get(i);
            if (move.getChecker().getOwner().equals("User")) {
                userMoves.add(move);
            }
        }
        return userMoves;
    }

    public List<Move> getCompMoves(List<Move> moves) {
        List<Move> compMoves = new ArrayList<>();
        for (int i = 0; i < moves.size(); i++) {
            Move move = moves.get(i);
            if (move.getChecker().getOwner().equals("Computer")) {
                compMoves.add(move);
            }
        }
        return compMoves;
    }

    public int calc_heuristic(Tile[] state) {
        int human_score = 0;
        int ai_score = 0;
        for (Tile tile : state) {
            if (tile.hasChecker()) {
                Checker c = tile.getChecker();
                if ("Computer".equals(c.getOwner())) {
                    ai_score += 2;
                }
                if ("User".equals(c.getOwner())) {
                    human_score += 2;
                }
                if (c.getRow() == 1 && "Computer".equals(c.getOwner())) {
                    ai_score += 3;
                }
                if (c.getRow() == 8 && "User".equals(c.getOwner())) {
                    human_score += 3;
                }
                if (c.isKing()) {
                    if ("Computer".equals(c.getOwner())) {
                        ai_score += 3;
                        human_score -= 5;

                    }
                    if ("User".equals(c.getOwner())) {
                        human_score += 3;
                        ai_score -= 5;
                    }
                }
            }
        }
        return ai_score - human_score;
    }

    public void setDifficulty(int difficulty) {
        this.depthLimit = difficulty;
    }

    public boolean compWin(Tile[] state) {
        int i = 0;
        //Checker[] user = Checker[12];
        boolean compWin = false;
        for (Tile t : state) {
            if (t.hasChecker() && t.getChecker().getOwner().equals("User")) {
                i++;
                //user[i] = t.getChecker();

            }
        }
        if (i < 1) {

            compWin = true;
        }

        return compWin;
    }

    public boolean userWin(Tile[] state) {
        int i = 0;
        boolean userWin = false;
        for (Tile t : state) {
            if (t.hasChecker() && t.getChecker().getOwner().equals("Computer")) {
                i++;
            }
        }
        if (i < 1) {
            userWin = true;
        }

        return userWin;
    }

    public boolean isGameOver(Tile[] state) {
        return userWin(state) || compWin(state);
    }

}
