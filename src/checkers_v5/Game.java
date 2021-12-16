package checkers_v5;

import java.util.ArrayList;
import java.util.List;

/**
 * Game holds the model for the game, including the logic for moves, the state
 * representation and the implementation of checkers rules
 *
 * @author 215865
 */
public class Game {

    // instance variables - replace the example below with your own
    int depthLimit;

    // The value selected by the user when opening game and choosing difficulty level
    String difficulty;

    // The board representation for the game
    Tile[] gameState;

    //To reference current objects
    Checker selectedChecker;
    Checker capturedChecker;
    Tile currentTile;
    Tile selectedTile;

    boolean minimax;

    // The current player
    String currentPlayer;

    //Best move as a result of minimax
    Move bestMove;
    List<Move> multiLegMoves = new ArrayList<>();

    /**
     * Constructor for objects of class Game
     *
     *
     * @param gameState An array of 32 'Tile' objects, which represent the
     * potential positions on the board 32 are used in accordance with checker
     * notation
     * @param difficulty The value selected by User when starting the program
     */
    public Game(Tile[] gameState, String difficulty) {
        this.currentPlayer = "User"; //User goes first
        this.gameState = gameState;
        setDifficulty(difficulty); //Assigns the depth limit for the minimax algorithm based on difficulty
        minimax = false;
    }

    /**
     * Updates the state representation with the current move If the move is a
     * capturing move, implements Regicide functionality
     *
     * @param move
     * @param state
     */
    public void move(Move move, Tile[] state) {
        //Update the state representation and carries out the given move in the given state
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
                if (!minimax) { //Checks if this is part of minimax or part of the game, in order to paint checker
                    state[newPos - 1].getChecker().paintKing();
                }
            }
        }
        if (!minimax) {
            if (move.getChecker().isKing()) {
                state[newPos - 1].getChecker().paintKing();
            }
        }
        capturedChecker = null;
        move.getChecker().move(newPos);

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
            if (tile.hasChecker() == false) {
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
                        //Add the move to the list
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
     * @return the current player
     */
    public String getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * For all the tiles in the current state, if they have a checker on them
     * and its owned by the user, add it to the list.
     *
     * @param state The current state representation
     * @return userCheckers A list of the checkers owned by the User and still
     * currently active
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
     * For all the tiles in the current state, if they have a checker on them,
     * and it's owned by the AI, add it to the list.
     *
     * @param state The current state representation
     * @return compCheckers A list of the checkers owned by the Computer and
     * still currently active
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
     * For all the tiles in the current state, if they have a checker on them,
     * add it to the list
     *
     * @param state The current state representation
     * @return allCheckers A list of all the currently active checkers in the
     * game
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
     * Starts the evaluation of all the possible state representations up to the
     * given depth limit Uses the minimax algorithm and heuristic function to
     * evaluate the state
     *
     * To be called by the AI when choosing a move OR when the user requests a
     * hint
     *
     * @param currentPlayer
     */
    public void startEval(String currentPlayer) {
        minimax = true;
        minimaxAB(this.depthLimit, currentPlayer, Integer.MIN_VALUE, Integer.MAX_VALUE, cloneState(gameState));
        minimax = false;
    }

    /**
     * Clones the given state representation by looping through the tiles and
     * creating new ones in the same position. Clones the pieces in the given
     * state using cloneChecker and then assigns them to the corresponding tile
     *
     * Helper function for minimax algorithm to generate new states
     *
     * @param currentState
     * @return state A cloned/deep copied version of the given state
     * representation
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
     * This method implements a recursive search algorithm to generate moves for
     * the AI or generate hints for the user when requested by recursing the
     * game tree.
     *
     * Each successive state (with the additional move implemented) represents a
     * 'Node' in the game search tree.
     *
     * It uses alpha-beta pruning to limit the extent of search by breaking the
     * recursion when no changes will be made the current values of alpha or
     * beta.
     *
     *
     * @param depth
     * @param currentPlayer
     * @param alpha
     * @param beta
     * @param state
     * @return bestScore An integer of the score of
     */
    public int minimaxAB(int depth, String currentPlayer, int alpha, int beta, Tile[] state) {
        Move tempBestMove = null;
        int bestScore;
        if (currentPlayer.equals("User")) {
            bestScore = Integer.MAX_VALUE; // User is the minimizing player so set bestScore to max
        } else {
            bestScore = Integer.MIN_VALUE; // User is maximising player so set bestScore to min
        }
        if (depth == 0 || getAllLegalMoves(state).isEmpty()) {
            //Check if the tree has been traversed to specified level, or node is terminal node, or the game is over
            return evaluate(state);
        }
        if (currentPlayer.equals("User")) {
            Tile[] tempState = cloneState(state);
            List<Move> legalMoves = getAllLegalMoves(tempState);
            List<Move> userMoves = getUserMoves(legalMoves); // Generate available moves for player
            for (Move move : userMoves) {
                if (violatesForcedCapture(move, tempState, "User")) {
                    continue;
                }
                //Carrying out move in tempstate
                this.move(move, tempState);
                int score = minimaxAB(depth - 1, "Computer", alpha, beta, tempState); // start recursion
                bestScore = Math.min(bestScore, score); //get best score
                beta = Math.min(beta, score); //update beta with lowest score
                if (score == beta) {
                    tempBestMove = move; //update best move
                }
                //Perform pruning of game tree if appropriate
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
            List<Move> compMoves = getCompMoves(legalMoves); //Generate available moves for AI
            for (Move move : compMoves) {
                if (violatesForcedCapture(move, tempState, "Computer")) {
                    continue;
                }
                this.move(move, tempState);
                int score = minimaxAB(depth - 1, "User", alpha, beta, tempState); //Start recursion
                bestScore = Math.max(score, bestScore); //Update bestScore to be max as needed
                alpha = Math.max(alpha, score); //Update alpha to be highest as needed
                if (score == alpha) {
                    tempBestMove = move; //update bestMove when new best move is seen
                }
                if (alpha >= beta) {
                    break; //Prune game tree as appropriate
                }
            }  
            this.bestMove = tempBestMove;
            return bestScore;
        }
    }
    
    /**
     * Retrieves the best move .
     *
     * @return best The best move seen during the minimax algorithm
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
     * Iterates through the legal moves for the given checker and returns the
     * first capturing move using the same selected checker to be multi leg
     *
     * @param checker The checker which is currently selected
     * @return multi The first multi-leg move available
     */
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

    /**
     * Iterates through the legal moves for the given checker, finding potential
     * multi leg moves, returning true if there is a potential multi-leg move.
     *
     * @param checker The checker which is currently selected
     * @return true if there is a potential multi-leg move
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
     * Checks if the move is a valid user move
     *
     *
     * @param checker
     * @param tile
     * @return true if valid move, false otherwise
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
     * Retrieves the valid move object from the legal moves available which
     * match with the specified checker and tile
     *
     *
     * @param checker
     * @param tile
     * @return validMove The corresponding Move object for a move between the
     * checker and the tile
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
     * Checks if the given move, in the context of the given state and the given
     * player violates forced capture
     *
     *
     *
     * @param move
     * @param state
     * @param currentPlayer
     * @return true if the move violates forced capture rules, false otherwise
     */
    public boolean violatesForcedCapture(Move move, Tile[] state, String currentPlayer) {
        boolean forcedCapture = false;
        List<Move> moves;
        if (currentPlayer.equals("Computer")) {
            moves = getCompMoves(getAllLegalMoves(state));
            for (Move capture : moves) {
                if (capture.isCapturingMove() && !move.isCapturingMove()) {
                    // If there is a legal move which is a capturing move and the user hasn't selected it, then return true
                    forcedCapture = true;
                }
            }
        } else {
            moves = getUserMoves(getAllLegalMoves(state));
            for (Move capture : moves) {
                if (capture.isCapturingMove() && !move.isCapturingMove()) {
                    // If there is a legal move which is a capturing move and the user hasn't selected it, then return true
                    forcedCapture = true;
                }
            }
        }
        return forcedCapture;
    }

    /**
     * Filters moves by the checker piece and its owner Only moves for the User
     *
     * @param moves Legal moves for both players
     * @return List of user moves
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
     * Filters Moves by the checker piece and it's owner to be only moves for
     * the AI
     *
     * @param moves Legal moves for both players
     * @return compMoves List of moves for the AI
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
     * The heuristic function for the Minimax algorithm Invoked when the depth
     * of the game tree search reaches the limit
     *
     * There are weights for pieces in the state, and the weights depend on the
     * difficulty of the game This helps to make varying levels of difficulty
     * for the player
     *
     *
     * @param state The current state representation
     * @return An integer representing the heuristic value of the given state
     */
    public int evaluate(Tile[] state) {
        int human_score = 0;
        int comp_score = 0;
        int piece_weight;
        int king_weight;
        int backrow_weight;
        int protected_weight;
        if (this.difficulty.equals("Easy")) {
            piece_weight = 3;
            king_weight = 3;
            backrow_weight = 1;
            protected_weight = 2;
        } else if (this.difficulty.equals("Intermediate")) {
            piece_weight = 4;
            king_weight = 5;
            backrow_weight = 2;
            protected_weight = 2;
        } else {
            piece_weight = 5;
            king_weight = 7;
            backrow_weight = 4;
            protected_weight = 3;
        }
        for (Checker user : getUserCheckers(state)) {
            human_score += piece_weight; //Add score for each piece on the board
            if (user.isKing()) {
                human_score += king_weight;
                //comp_score -= 2;
            }
            if (user.getRow() == 8) {
                human_score += backrow_weight;
            }
            if (user.isProtected(state)) { //If there are no neighbouring tiles free, protected
                human_score += protected_weight;
            }
        }
        for (Checker comp : getCompCheckers(state)) {
            comp_score += piece_weight;
            if (comp.isKing()) { //If is a king
                comp_score += king_weight;
            }
            if (comp.getRow() == 1) { //If backrow piece
                comp_score += backrow_weight;
            }
            if (comp.isProtected(state)) { //If protected
                comp_score += protected_weight;
            }
        }
        return comp_score - human_score; //Returns the difference between scores
    }

    /**
     * Sets the depth limit for the minimax algorithm corresponding to the
     * difficulty level
     *
     * @param difficulty The specified difficulty from the UI
     */
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
     * Returns if the computer has won or not
     *
     * If there are no more user checkers left on the board, computer wins If
     * there are no more legal moves, and the computer has more checkers on the
     * board, computer wins
     *
     * @param state The current state representation
     * @return true if computer wins, false otherwise
     */
    public boolean compWin(Tile[] state) {
        boolean compWin = false;
        int num = getCompCheckers(state).size();
        int userNum = getUserCheckers(state).size();
        if (userNum == 0) {
            compWin = true;
        } else if (getAllLegalMoves(state).isEmpty() && num > userNum) {
            compWin = true;
        }
        return compWin;
    }

    /**
     * Returns if the user has won or not
     *
     *
     * @param state The current state representation
     * @return true if user wins, false otherwise
     */
    public boolean userWin(Tile[] state) {
        boolean userWin = false;
        int num = getUserCheckers(state).size();
        int compNum = getCompCheckers(state).size();
        if (compNum == 0) {
            userWin = true;
        } else if (getAllLegalMoves(state).isEmpty() && num > compNum) {
            userWin = true;
        }
        return userWin;
    }

    /**
     * Returns if the game is over yet
     *
     *
     *
     * @param state The current state representation
     * @return true if and only if both userWin and compWin are false
     */
    public boolean isGameOver(Tile[] state) {
        return !(userWin(state) == false && compWin(state) == false);
    }

}
