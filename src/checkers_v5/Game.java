package checkers_v5;

import java.util.ArrayList;
import java.util.List;

/**
 * Write a description of class Game here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Game {

    // instance variables - replace the example below with your own
    int depthLimit;

    String difficulty;

    Tile[] gameState;

    //To reference current objects
    Checker selectedChecker;
    Checker capturedChecker;
    Tile currentTile;
    Tile selectedTile;

    String currentPlayer;
    Move bestMove;
    List<Move> multiLegMoves = new ArrayList<>();

    /**
     * Constructor for objects of class Game
     *
     *
     * @param gameState An array of 32 'Tile' objects, which represent the
     * potential positions on the board 32 are used in accordance with checker
     * notation
     * @param difficulty
     */
    public Game(Tile[] gameState, String difficulty) {
        this.currentPlayer = "User";
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
                state[newPos - 1].getChecker().setKing();
                if (this.gameState.equals(state)) { //Checks if this is part of minimax or part of the game, in order to paint checker
                    state[newPos - 1].getChecker().paintKing();
                }
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

    /**
     *
     * @return
     */
    public String getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     *
     * @return
     */
    public List<Move> getAllHints() {
        List<Move> potentialMoves = new ArrayList<>();
        if (this.currentPlayer.equals("User")) {
            for (Checker checker : getUserCheckers(gameState)) {
                for (Move move : getUserMoves(getLegalMoves(checker, gameState))) {
                    potentialMoves.add(move);
                }
            }
        } else {
            for (Checker checker : getCompCheckers(gameState)) {
                for (Move move : getCompMoves(getLegalMoves(checker, gameState))) {
                    potentialMoves.add(move);
                }
            }

        }
        return potentialMoves;
    }

    /**
     *
     * @param state
     * @return
     */
    public List<Checker> getUserCheckers(Tile[] state) {
        List<Checker> userCheckers = new ArrayList<>();
        for (Tile t : state) {
            if (t.hasChecker() && t.getChecker().getOwner().equals("User")) {
                userCheckers.add(t.getChecker());
            }
        }
        return userCheckers;
    }

    /**
     *
     * @param state
     * @return
     */
    public List<Checker> getCompCheckers(Tile[] state) {
        List<Checker> compCheckers = new ArrayList<>();
        for (Tile t : state) {
            if (t.hasChecker() && t.getChecker().getOwner().equals("Computer")) {
                compCheckers.add(t.getChecker());
            }
        }
        return compCheckers;
    }

    /**
     *
     * @param state
     * @return
     */
    public List<Checker> getCheckers(Tile[] state) {
        List<Checker> allCheckers = new ArrayList<>();
        for (Tile t : state) {
            if (t.hasChecker()) {
                allCheckers.add(t.getChecker());
            }
        }
        return allCheckers;
    }

    /**
     * Changes the string representing the current player to alternate turns
     *
     */
    public void nextTurn() {
        if (this.currentPlayer.equals("User")) {
            this.currentPlayer = "Computer";
        } else {
            this.currentPlayer = "User";
        }
    }

    /**
     *
     * @param currentPlayer
     */
    public void startEval(String currentPlayer) {
        minimaxAB(this.depthLimit, currentPlayer, Integer.MIN_VALUE, Integer.MAX_VALUE, cloneState(gameState));
    }

    /**
     *
     * @param currentState
     * @return
     */
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
            bestScore = Integer.MAX_VALUE;
        } else {
            bestScore = Integer.MIN_VALUE;
        }
        if (depth == 0 || getAllLegalMoves(state).isEmpty()) {
            //Check if the tree has been traversed to specified level, or node is terminal node, or the game is over
            if (currentPlayer.equals("Computer")) {
                return evaluate(state); // return static evaluation of node
            } else {
                return -evaluate(state); // return inverse as the calculation function is based on the AI
            }
        }
        if (currentPlayer.equals("User")) {
            Tile[] tempState = cloneState(state);
            List<Move> legalMoves = getAllLegalMoves(tempState);
            List<Move> userMoves = getUserMoves(legalMoves); // Generate available moves for player
            for (Move move : userMoves) {
                this.move(move, tempState);
                int score = minimaxAB(depth - 1, "Computer", alpha, beta, tempState); // start recursion
                bestScore = Math.min(bestScore, score); //get best score
                beta = Math.min(beta, score);
                if (bestScore == beta) {
                    tempBestMove = move;
                }
                //Perform pruning if appropriate
                if (alpha >= beta) {
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
                this.move(move, tempState);
                int score = minimaxAB(depth - 1, "User", alpha, beta, tempState);
                bestScore = Math.max(score, bestScore);
                alpha = Math.max(alpha, score);
                if (alpha == score) {
                    tempBestMove = move;
                }
                if (alpha >= beta) {
                    break;
                }
            }
            this.bestMove = tempBestMove;
            return bestScore;
        }
    }

    /**
     * Retrieves the best move by finding the components in the current game
     * State
     *
     * This is because the bestMove is created from a temporary state in the
     * minimax algorithm so the references aren't accurate.
     *
     *
     * @return
     */
    public Move getBestMove() {
        Checker bestChecker = this.gameState[bestMove.getOriginalPos() - 1].getChecker();
        Tile bestTile = this.gameState[bestMove.getNewPos() - 1];
        Move best = new Move(bestChecker, bestTile);
        if (this.bestMove.isCapturingMove()) {
            Checker captured = this.gameState[this.bestMove.getCapturedChecker().getPosition() - 1].getChecker();
            best.setCapturedChecker(captured);
        }
        return best;

    }

    /**
     * Iterates through the legal moves for the given checker, finding potential
     * multi leg moves Uses a recursive call to get subsequent potential moves
     *
     * @param checker The checker which is currently selected
     * @return multiLegMoves A list of subsequent moves that could be taken by
     * the checker
     */
    public Move getMultiLeg2(Checker checker) {
        Move multi = null;
        List<Move> multileg = getLegalMoves(checker, this.gameState);
        for (Move capture : multileg) {
            if (capture.getChecker().equals(checker) && capture.isCapturingMove()) {
                multi = capture;
            }
        }
        return multi;
    }

    /**
     * Iterates through the legal moves for the given checker, finding potential
     * multi leg moves Uses a recursive call to get subsequent potential moves
     *
     * @param checker The checker which is currently selected
     * @return multiLegMoves A list of subsequent moves that could be taken by
     * the checker
     */
    public boolean existsMultiLeg(Checker checker) {
        boolean multi = false;
        List<Move> multileg = getLegalMoves(checker, this.gameState);
        for (Move capture : multileg) {
            if (capture.getChecker().equals(checker) && capture.isCapturingMove()) {
                multi = true;
            }
        }
        return multi;
    }

    /**
     *
     * @param checker
     * @param tile
     * @return
     */
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

    /**
     *
     * @param checker
     * @param tile
     * @return
     */
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

    /**
     *
     * @param move
     * @param state
     * @return
     */
    public boolean isForcedCapture(Move move, Tile[] state) {
        boolean forcedCapture = false;
        List<Move> moves;
        if (this.currentPlayer.equals("Computer")) {
            moves = getCompMoves(getAllLegalMoves(state));
            for (Move capture : moves) {
                if (capture.isCapturingMove() && !move.isCapturingMove()) {
                    // If there is a legal move which is a capturing move and the user hasn't selected it, then return true
                    forcedCapture = true;
                    //System.out.println(capture);
                }
            }
        } else {
            moves = getUserMoves(getAllLegalMoves(state));
            for (Move capture : moves) {
                if (capture.isCapturingMove() && !move.isCapturingMove()) {
                    // If there is a legal move which is a capturing move and the user hasn't selected it, then return true
                    forcedCapture = true;
                    //System.out.println(capture);
                }
            }
        }

        return forcedCapture;
    }

    /**
     *
     * @param moves
     * @return
     */
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

    /**
     *
     * @param moves
     * @return
     */
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

    /**
     * The heuristic function for the minimax
     * 
     * There are weights for pieces in the state, and the weights depend on the difficulty of the game
     * 
     * 
     * @param state
     * @return
     */
    public int evaluate(Tile[] state) {
        int human_score = 0;
        int comp_score = 0;
        int piece_weight;
        int king_weight;
        int backrow_weight;
        int protected_weight;
        if (this.difficulty.equals("Easy")) {
            piece_weight = 2;
            king_weight = 3;
            backrow_weight = 2;
            protected_weight = 3;
        } else if (this.difficulty.equals("Intermediate")) {
            piece_weight = 2;
            king_weight = 3;
            backrow_weight = 2;
            protected_weight = 3;
        } else {
            piece_weight = 5;
            king_weight = 7;
            backrow_weight = 4;
            protected_weight = 3;
        }

        for (Checker user : getUserCheckers(state)) {
            human_score += piece_weight;
            if (user.isKing()) {
                human_score += king_weight;
            }
            if (user.getRow() == 8) {
                human_score += backrow_weight;
            }
            if (user.isProtected(state)) { //If there are no neighbouring tiles free, protected
                human_score += protected_weight;
            } else {
                human_score -= 3;
            }
        }
        for (Checker comp : getCompCheckers(state)) {
            comp_score += piece_weight;
            if (comp.isKing()) {
                comp_score += king_weight;
            }
            if (comp.getRow() == 1) {
                comp_score += backrow_weight;
            }
            if (comp.isProtected(state)) {
                comp_score += protected_weight;
            } else {
                comp_score -= 3;
            }
        }
        return comp_score - human_score;
    }

    private void setDifficulty(String difficulty) {
        if (difficulty.equals("Easy")) {
            this.depthLimit = 3;

        } else if (difficulty.equals("Intermediate")) {
            this.depthLimit = 5;
        } else {
            this.depthLimit = 7;
        }
        this.difficulty = difficulty;
    }

    /**
     *
     * @param state
     * @return
     */
    public boolean compWin(Tile[] state) {
        int i = 0;
        boolean compWin = false;
        for (Tile t : state) {
            if (t.hasChecker() && t.getChecker().getOwner().equals("User")) {
                i++;
            }
        }
        if (i == 0) {
            compWin = true;
        }
        return compWin;
    }

    /**
     *
     * @param state
     * @return
     */
    public boolean userWin(Tile[] state) {
        int i = 0;
        boolean userWin = false;
        for (Tile t : state) {
            if (t.hasChecker() && t.getChecker().getOwner().equals("Computer")) {
                i++;
            }
        }
        if (i == 0) {
            userWin = true;
        }
        return userWin;
    }

    /**
     *
     * @param state
     * @return
     */
    public boolean isGameOver(Tile[] state) {
        return userWin(state) || compWin(state);
    }

}
