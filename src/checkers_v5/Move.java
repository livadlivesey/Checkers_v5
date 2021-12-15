package checkers_v5;

import java.util.Objects;

/**
 *
 * @author 215865
 * 
 */
public class Move {

    private final int originalPos;
    private final int newPos;
    Checker checker;
    Tile newTile;
    boolean isCapturing = false;
    Checker capturedChecker;
    boolean regicide;

    /**
     *
     * Creates an instance of a 'Move' object
     * Holds reference to the checker and tile of the move, as well as if the move is regicide 
     * or a capturing move
     * 
     * 
     * @param checker
     * @param destination
     */
    public Move(Checker checker, Tile destination) {
        this.checker = checker;
        this.newTile = destination;
        this.originalPos = checker.getPosition();
        this.newPos = destination.getPosition();
        this.isCapturing = false;
        this.capturedChecker = null;
        this.regicide = isRegicide();
    }
    
     /** 
     * 
     * If the move is a capturing move, and is capturing a king when the current checker is not a king
     * then the move is regicide
     * 
     * @return true if regicide, false otherwise
     */
    public boolean isRegicide() {
        if (this.isCapturing && this.capturedChecker.isKing() && !this.checker.isKing()) {
            this.regicide = true;
        }
        return regicide;
    }
    
    /**
     *
     * @return current checker
     */
    public Checker getChecker() {
        return this.checker;
    }

    /**
     *
     * @param c captured checker
     */
    public void setCapturedChecker(Checker c) {
        this.capturedChecker = c;
        this.isCapturing = true;
    }

    /**
     * Determines if the move is a capturing move based on the row
     * 
     * @param checker
     * @param tile
     */
    public void calcIsCapturingMove(Checker checker, Tile tile) {
        if (checker.getRow() == tile.getRow() + 2 || checker.getRow() == tile.getRow() - 2) {
            this.isCapturing = true;
        }
    }

    /**
     *
     * @return captured checker
     */
    public Checker getCapturedChecker() {
        return this.capturedChecker;
    }

    /**
     *
     * @return true if capturing move, false otherwise
     */
    public boolean isCapturingMove() {
        return this.isCapturing;
    }

    /**
     *
     * @return original position of checker
     */
    public int getOriginalPos() {
        return this.originalPos;
    }

    /**
     *
     * @return  position of the destination tile
     */
    public int getNewPos() {
        return this.newPos;
    }

    /**
     *
     * @return the destination tile
     */
    public Tile getTile() {
        return this.newTile;
    }

    @Override
    public String toString() {
        String s = "Checker: " + this.checker.toString() + ", Tile: " + this.newTile.toString();
        if (this.isCapturing) {
            s += "Capturing: "+ this.capturedChecker;
        }
        return s;
    }

    @Override
    public boolean equals(Object o) {
        boolean e = false;
        if (o == null) {
            e = false;
        }
        if (o == this) {
            e = true;
        }
        if (o instanceof Move) {
            Move m = (Move) o;
            if (m.checker == this.checker && m.newTile == this.newTile) {
                e = true;
            }
        }
        return e;
    }

    @Override
    public int hashCode() {
        return Objects.hash(checker, newTile);
    }

}
