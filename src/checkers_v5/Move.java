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
    //Tile[] state;

    public Move(Checker checker, Tile destination) {
        this.checker = checker;
        this.newTile = destination;
        this.originalPos = checker.getPosition();
        this.newPos = destination.getPosition();
        this.isCapturing = false;
        this.capturedChecker = null;
        //this.state = state;
    }
    
    

    public Checker getChecker() {
        return this.checker;
    }


    public void setCapturedChecker(Checker c) {
        this.capturedChecker = c;
        this.isCapturing = true;
    }

    public void calcIsCapturingMove(Checker checker, Tile tile) {
        if (checker.getRow() == tile.getRow() + 2 || checker.getRow() == tile.getRow() - 2) {
            this.isCapturing = true;
        }
    }

    public Checker getCapturedChecker() {
        return this.capturedChecker;
    }

    public boolean isCapturingMove() {
        return this.isCapturing;
    }

    public int getOriginalPos() {
        return this.originalPos;
    }

    public int getNewPos() {
        return this.newPos;
    }

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
