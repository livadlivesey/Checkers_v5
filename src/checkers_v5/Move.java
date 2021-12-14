package checkers_v5;

import java.util.Objects;

/**
 *
 * @author liv
 */
public class Move {
    private int originalPos;
    private int newPos;
    Checker checker;
    Tile newTile;
    boolean isCapturing = false;
    
    public Move(Checker checker, Tile destination) {
        this.checker = checker;
        this.newTile = destination;
        this.originalPos = checker.position;
        this.newPos = newTile.position;
    }
    
    public Checker getChecker() {
        return this.checker;
    }
    
    public void setCapturing() {
        this.isCapturing = true;
    }
    
    public boolean getCapturing() {
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
        String s = "Checker: "+ this.checker.toString() + ", Tile: "+ this.newTile.toString();
        return s;
    }
    
    @Override
    public boolean equals(Object o) {
        boolean e = false;
        if (o==null) e= false;
        if (o ==this) e = true;
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
