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
    public void moveChecker(Checker checker, int oldPosition, int newPosition) {
        checker.move(newPosition);
        gameState[newPosition - 1].setChecker(checker);
        gameState[oldPosition - 1].removeChecker();

    }

    public void move(Checker checker, Tile newTile, Tile[] state) {
        int oldPos = checker.position;
        int newPos = newTile.position;
        checker.move(newPos);
        state[newPos - 1].setChecker(checker);
        state[oldPos - 1].removeChecker();

    }

    /**
     * Gets the legal moves for the given tile For each tile which is
     * surrounding, checks if the move is legal
     *
     * @param current
     * @return List of tile objects which can be moved to
     */
    public List<Tile> getLegalMoves(Checker current) {

        List<Tile> legalMoves = new ArrayList<>();
        List<Integer> neighbours = current.getNeighbouringPositions();
        //System.out.println(neighbours);
        for (int i : neighbours) {
            Tile tile = gameState[i - 1]; //Use -1 as the stored positions are in checker notation, and array starts from index 0
            if (current.getOwner().equals(currentPlayer) && tile.hasChecker() == false) {
                legalMoves.add(tile);

            }
            if (tile.hasChecker() == true && !tile.getChecker().getOwner().equals(current.getOwner())) {
                int newRow = 0;
                int newCol = 0;
                //Hashtable<Integer, Integer> positions = new Hashtable<>();
                if (current.getRow() + 1 == tile.getRow() && current.getCol() + 1 == tile.getCol()) {
                    newRow = tile.getRow() + 1;
                    newCol = tile.getCol() + 1;
                    //positions.put(newRow, newCol);

                } else if (current.getRow() + 1 == tile.getRow() && current.getCol() - 1 == tile.getCol()) {
                    newRow = tile.getRow() + 1;
                    newCol = tile.getCol() - 1;
                    //positions.put(newRow, newCol);

                } else if (current.getRow() - 1 == tile.getRow() && current.getCol() + 1 == tile.getCol()) {
                    newRow = tile.getRow() - 1;
                    newCol = tile.getCol() + 1;
                    //positions.put(newRow, newCol);

                } else if (current.getRow() - 1 == tile.getRow() && current.getCol() - 1 == tile.getCol()) {
                    newRow = tile.getRow() - 1;
                    newCol = tile.getCol() - 1;
                    //positions.put(newRow, newCol);
                }

                for (Tile t : gameState) {
                    //System.out.println("ROW: " + t.getRow());
                    //System.out.println("COL: " + t.getCol());

                    if (t.getRow() == newRow && t.getCol() == newCol && t.hasChecker() == false) {

                        legalMoves.add(t);
                        //System.out.println("Legal tile: " + t.toString());
                    }
                    //Add alert if no possible positions
                }
            }
        }

        //System.out.println(legalMoves);
        return legalMoves;
    }

    /**
     * Gets the legal moves for the given tile For each tile which is
     * surrounding, checks if the move is legal
     *
     * @param current
     * @param state
     * @return List of tile objects which can be moved to
     */
    public List<Move> getLegalMoves2(Checker current, Tile[] state) {

        List<Move> legalMoves = new ArrayList<>();
        List<Integer> neighbours = current.getNeighbouringPositions();
        //System.out.println(neighbours);
        for (int i : neighbours) {
            Tile tile = state[i - 1]; //Use -1 as the stored positions are in checker notation, and array starts from index 0
            if (tile.hasChecker() == false) { //current.getOwner().equals(player) &&
                legalMoves.add(new Move(current, tile));
            }
            if (tile.hasChecker() == true && !tile.getChecker().getOwner().equals(current.getOwner())) {
                int newRow = 0;
                int newCol = 0;

                if (current.getRow() + 1 == tile.getRow() && current.getCol() + 1 == tile.getCol()) {
                    newRow = tile.getRow() + 1;
                    newCol = tile.getCol() + 1;

                } else if (current.getRow() + 1 == tile.getRow() && current.getCol() - 1 == tile.getCol()) {
                    newRow = tile.getRow() + 1;
                    newCol = tile.getCol() - 1;

                } else if (current.getRow() - 1 == tile.getRow() && current.getCol() + 1 == tile.getCol()) {
                    newRow = tile.getRow() - 1;
                    newCol = tile.getCol() + 1;

                } else if (current.getRow() - 1 == tile.getRow() && current.getCol() - 1 == tile.getCol()) {
                    newRow = tile.getRow() - 1;
                    newCol = tile.getCol() - 1;
                }

                for (Tile t : state) {
                    if (t.getRow() == newRow && t.getCol() == newCol && t.hasChecker() == false) {
                        legalMoves.add(new Move(current, t));
                    }

                }
            }
        }

        //System.out.println(legalMoves);
        return legalMoves;
    }

    public List<Move> getAllLegalMoves(Tile[] state) {
        List<Move> allMoves = new ArrayList<>();
        for (Tile t : state) {
            if (t.hasChecker()) {
                Checker c = t.getChecker();
                allMoves.addAll(getLegalMoves2(c, state));
            }

        }
        return allMoves;
    }

    public String getCurrentPlayer() {
        return this.currentPlayer;
    }

    public void removeChecker(Checker checker) {
        for (Tile tile : gameState) {
            if (tile.getChecker().equals(checker)) {
                tile.removeChecker();
            }
        }
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
        System.out.print("Start state: " + Arrays.toString(MinimaxState));
        minimaxAB(depthLimit, currentPlayer, Integer.MIN_VALUE, Integer.MAX_VALUE, MinimaxState);
        System.out.print("Final state: " + Arrays.toString(MinimaxState));
    }
    
    public Tile[] cloneState(Tile[] currentState) {
        Tile [] state = new Tile[32];
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
    public Move getBestMove() {
        int max = -10000;
        int best = -1;
        System.out.println("Successor Evaluations Size: " + successorEvaluations.size());
        for (int i = 0; i < successorEvaluations.size(); i++) {
            if (max <= successorEvaluations.get(i).score) {
                max = successorEvaluations.get(i).score;
                System.out.print("Max: " + max);
                best = i;
            }
        }
        //System.out.println("Successor evaluations: " + successorEvaluations.toString());
        //System.out.println("Best: " + best);
        return successorEvaluations.get(best).getMove();
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
        int bestScore;
        if (currentPlayer.equals("User")) {
            bestScore = Integer.MIN_VALUE;
        } else {
            bestScore = Integer.MAX_VALUE;
        }
        List<Move> legalMoves = getAllLegalMoves(state);
        if (depth == 0 || legalMoves.isEmpty()) {
            seCount++;
            //successorEvaluations.add(new MovesAndScores(bestScore, tempBestMove));
            return calc_heuristic(state); // return static evaluation of node

        }
        if (currentPlayer.equals("User")) {
            int maxScore = Integer.MIN_VALUE;      // User is minimizing player   
            //bestScore = Integer.MIN_VALUE;
            List<Move> userMoves;
            userMoves = getUserMoves(legalMoves); // Generate available moves for player
            for (Move move: userMoves) {
                
                deCount++;
                //state = cloneState(gameState);
                int originalPos = move.checker.getPosition(); // Get position of checker before move for undo move later
                Tile originalTile = state[originalPos-1];
                move(move.checker, move.newTile, state); //place piece at first available position
                int score = minimaxAB(depth - 1, "Computer", alpha, beta, state); // start recursion

                maxScore = Math.max(bestScore, score); //get best score

                //alpha = Math.max(alpha, bestScore);
                if (alpha <= bestScore) {
                    alpha = maxScore;
                    tempBestMove = new Move(move.checker, move.newTile); // save the best move temporarily
                }

                //Un do the move that happened
                //int testPos = move.newTile.getPosition();
                //state[testPos - 1].removeChecker();
                //state[originalPos - 1].setChecker(move.checker);
                //state = cloneState(gameState);
                move(move.checker, originalTile, state);
                //moveChecker(move.checker, testPos, originalPos);
                //Any other states which need to be updated?

                //Perform pruning if appropriate
                if (beta <= alpha) {
                    pCount++;
                    break;
                }

            }
            this.bestMove = tempBestMove;
            return bestScore;
        } else {
            int minScore = Integer.MAX_VALUE; //Computer is maximizing player
            List<Move> compMoves = getCompMoves(legalMoves);
            for (Move move : compMoves) {
                //state = cloneState(gameState);
                deCount++;
                int originalPos = move.checker.getPosition(); //Store original position for later undoing the move
                move(move.checker, move.newTile, state);
                int score = minimaxAB(depth - 1, "User", alpha, beta, state);
                minScore = Math.min(score, minScore);

                //beta = Math.min(score, beta);
                if (score <= beta) {
                    beta = score;
                    tempBestMove = move;
                }

                //Un do the move that happened
                //int testPos = move.newTile.getPosition();
                //state[testPos - 1].removeChecker();
                //state[originalPos - 1].setChecker(move.checker);
                move.checker.position = originalPos;
                //moveChecker(move.checker, testPos, originalPos);
                
                //state = cloneState(gameState);

                if (beta <= alpha) {
                    pCount++;
                    break;
                }
            }
            this.bestMove = tempBestMove;
            return bestScore;
        }
        //this.bestMove = tempBestMove;
        //return beta;
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
        return human_score - ai_score;
    }

    public void setDifficulty(int difficulty) {
        this.depthLimit = 2 * difficulty;
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
