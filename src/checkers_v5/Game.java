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
    List<MovesAndScores> successorEvaluations;

    //To reference current objects
    Checker selectedChecker;
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
     * @param move
     * @param state
     */
    public void move(Move move, Tile[] state) {
        int oldPos = move.getOriginalPos();
        int newPos = move.getNewPos();
        state[newPos - 1].setChecker(move.getChecker());
        state[oldPos - 1].removeChecker();
        //If the move is a capturing move, make sure to remove the piece from board and representations
        if (move.isCapturingMove()) {
            capturedChecker = move.getCapturedChecker();
            int capturedPos = capturedChecker.getPosition();
            state[capturedPos - 1].removeChecker();
            
            // Regicide - if normal piece captures a king it is crowned 
            if (capturedChecker.isKing() && !move.getChecker().isKing()) {
                state[newPos-1].getChecker().setKing();
            }
            capturedChecker = null;
            move.getChecker().move(newPos);
        }

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
            if (tile.hasChecker() == false) { //current.getOwner().equals(player) &&
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
                        int capturedPos = tile.getPosition();
                        m.setCapturedChecker(state[capturedPos - 1].getChecker());
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

    public List<Move> getAllHints() {
        List<Move> potentialMoves = new ArrayList<>();
        if (this.currentPlayer.equals("User")) {
            for (Checker checker : getUserCheckers()) {
                for (Move move : getUserMoves(getLegalMoves(checker, gameState))) {
                    potentialMoves.add(move);
                }
            }
        } else {
            for (Checker checker : getCompCheckers()) {
                for (Move move : getCompMoves(getLegalMoves(checker, gameState))) {
                    potentialMoves.add(move);
                }
            }

        }
        return potentialMoves;
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
    public void nextTurn() {
        if (this.currentPlayer.equals("User")) {
            this.currentPlayer = "Computer";
        } else {
            this.currentPlayer = "User";
        }
    }

    public void startEval() {
        deCount = 0;
        seCount = 0;
        pCount = 0;
        successorEvaluations = new ArrayList<>();
        minimaxAB(depthLimit, currentPlayer, Integer.MIN_VALUE, Integer.MAX_VALUE, cloneState(gameState));
    }

    public Tile[] cloneState(Tile[] currentState) {
        Tile[] state = new Tile[32];
        for (int i = 0; i < currentState.length; i++) {
            state[i] = new Tile(currentState[i].position, currentState[i].getRectangle());
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

    public int minimaxAB(int depth, String currentPlayer, int alpha, int beta, Tile[] state) {
        Move tempBestMove = null;
        int bestScore;
        if (currentPlayer.equals("User")) {
            bestScore = Integer.MAX_VALUE;
        } else {
            bestScore = Integer.MIN_VALUE;
        }
        if (depth == 0 || getAllLegalMoves(state).isEmpty()) {
            //Check if the tree has been traversed to specified level, or node is terminal node
            seCount++;
            //successorEvaluations.add(new MovesAndScores(bestScore, tempBestMove, currentPlayer));
            return calc_heuristic(state); // return static evaluation of node

        }
        if (currentPlayer.equals("User")) {
            Tile[] tempState = cloneState(state);
            List<Move> legalMoves = getAllLegalMoves(tempState);
            List<Move> userMoves = getUserMoves(legalMoves); // Generate available moves for player
            for (Move move : userMoves) {
                Checker moving = move.getChecker().cloneChecker();
                Tile newTile = tempState[move.getNewPos() - 1]; //TODO: clone tile?
                deCount++;
                //move(moving, newTile, tempState); //place piece at first available position
                this.move(move, tempState);
                int score = minimaxAB(depth - 1, "Computer", alpha, beta, tempState); // start recursion
                bestScore = Math.min(bestScore, score); //get best score
                beta = Math.min(beta, score);
                if (bestScore == beta) {
                    tempBestMove = move;
                }
                //if (beta > bestScore) {
                //beta = bestScore;
                //tempBestMove = move;
                //successorEvaluations.add(new MovesAndScores(score, tempBestMove, "User"));// save the best move temporarily
                //}
                //Perform pruning if appropriate
                if (alpha >= beta) {
                    pCount++;
                    break;
                }
            }
            this.bestMove = tempBestMove;
            return bestScore;
        } else {
            //Computer is maximizing player
            Tile[] tempState = cloneState(state);
            List<Move> legalMoves = getAllLegalMoves(tempState);
            List<Move> compMoves = getCompMoves(legalMoves);
            for (Move move : compMoves) {
                deCount++;
                Checker moving = move.getChecker().cloneChecker();
                Tile newTile = tempState[move.getNewPos() - 1];
                //move(moving, newTile, tempState);
                this.move(move, tempState);
                int score = minimaxAB(depth - 1, "User", alpha, beta, tempState);
                bestScore = Math.max(score, bestScore);
                alpha = Math.max(alpha, score);
                if (alpha == score) {
                    tempBestMove = move;
                }

                //if (alpha < bestScore) {
                //alpha = bestScore;
                //tempBestMove = move;
                //successorEvaluations.add(new MovesAndScores(score, tempBestMove, "Computer"));
                //}
                if (alpha >= beta) {
                    pCount++;
                    break;
                }
            }
            this.bestMove = tempBestMove;
            return bestScore;
        }
        //this.bestMove = tempBestMove;
        //return bestScore;
    }

    public Move getBestMove2() {
        Checker bestChecker = this.gameState[bestMove.getOriginalPos() - 1].getChecker();
        Tile bestTile = this.gameState[bestMove.getNewPos() - 1];
        Move best = new Move(bestChecker, bestTile);
        if (this.bestMove.isCapturingMove()) {
            Checker captured = this.gameState[this.bestMove.getCapturedChecker().getPosition() - 1].getChecker();
            best.setCapturedChecker(captured);

        }

        return best;

    }
    


    public boolean existsMultiLeg(Checker checker) {
        boolean existsMultileg = false;
        List<Move> multileg = getLegalMoves(checker, this.gameState);
        for (Move capture : multileg) {
            if (capture.getChecker().equals(checker) && capture.isCapturingMove()) {
                existsMultileg = true;
            }
        }
        return existsMultileg;
    }

    public Move getMultiLeg(Checker checker) {
        Move multi = null;
        List<Move> multileg = getLegalMoves(checker, this.gameState);
        for (Move capture : multileg) {
            if (capture.getChecker().equals(checker) && capture.isCapturingMove()) {
                multi = capture;
            }
        }
        return multi;
    }

    public boolean isValidUserMove(Checker checker, Tile tile) {
        boolean validMove = false;
        List<Move> moves = getLegalMoves(checker, gameState);
        for (Move move : moves) {
            if (move.getChecker().equals(checker) && move.getTile().equals(tile)) {
                validMove = true;
                break;
            }
        }
        return validMove;
    }

    public Move getValidUserMove(Checker checker, Tile tile) {
        Move validMove = null;
        List<Move> moves = getLegalMoves(checker, gameState);
        for (Move move : moves) {
            if (move.getChecker().equals(checker) && move.getTile().equals(tile)) {
                validMove = move;
                break;
            }
        }
        return validMove;
    }

    public boolean isForcedCapture(Move move) {
        boolean forcedCapture = false;
        List<Move> moves = getUserMoves(getAllLegalMoves(this.gameState));
        for (Move capture : moves) {
            if (capture.isCapturingMove() && !capture.equals(move)) {
                // If there is a legal move which is a capturing move and the user hasn't selected it, then return true
                forcedCapture = true;
            }
        }
        return forcedCapture;
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
                if (c.isKing()) {
                    if ("Computer".equals(c.getOwner())) {
                        // Additional points for a piece if it's a king, the opponent loses points
                        ai_score += 3;
                        human_score -= 5;

                    }
                    if ("User".equals(c.getOwner())) {
                        human_score += 3;
                        ai_score -= 5;
                    }
                } else {
                    // Back row pieces only get points if they aren't Kings
                    if (c.getRow() == 1 && "Computer".equals(c.getOwner())) {
                        ai_score += 3;
                    }
                    if (c.getRow() == 8 && "User".equals(c.getOwner())) {
                        human_score += 3;
                    }
                }
            }
        }
        return ai_score - human_score;
    }

    private void setDifficulty(int difficulty) {
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
