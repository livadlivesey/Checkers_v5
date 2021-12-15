package checkers_v5;

import java.util.Objects;

/**
 *
 * @author liv
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
        
        //this.state = state;
    }
    
        /**
     *Returns true if the specified move involves regicide 
     * 
     * @return
     */
    public boolean isRegicide() {
        if (this.isCapturing && this.capturedChecker.isKing() && !this.checker.isKing()) {
            this.regicide = true;
        }
        return regicide;
    }
    
    /**
     *
     * @return
     */
    public Checker getChecker() {
        return this.checker;
    }

    /**
     *
     * @param c
     */
    public void setCapturedChecker(Checker c) {
        this.capturedChecker = c;
        this.isCapturing = true;
    }

    /**
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
     * @return
     */
    public Checker getCapturedChecker() {
        return this.capturedChecker;
    }

    /**
     *
     * @return
     */
    public boolean isCapturingMove() {
        return this.isCapturing;
    }

    /**
     *
     * @return
     */
    public int getOriginalPos() {
        return this.originalPos;
    }

    /**
     *
     * @return
     */
    public int getNewPos() {
        return this.newPos;
    }

    /**
     *
     * @return
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
        //int hash =7;
        //hash = 17*hash+(this.checker!= null && this.newTile != null ? this.checker.hashCode() + this.newTile.hashCode() : 0);
        return Objects.hash(checker, newTile);
    }

}
