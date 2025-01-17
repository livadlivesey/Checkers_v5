package checkers_v5;


import java.util.List;
import javafx.scene.shape.Rectangle;


/**
 *
 * @author 215865
 */
public class Tile {

    private boolean hasChecker;
    private Checker currentChecker;
    private int row;
    int position;
    private int column;
    Rectangle rectangle;

    /**
     * Creates an instance of a tile object, which represents each position on the board
     * 
     * A tile holds reference to it's javaFX representation, and the checker on it, if any
     *
     * @param position position on the board in checker notation
     * @param rectangle javaFX representation
     */
    public Tile(int position, Rectangle rectangle) {
        hasChecker = false;
        currentChecker = null;
        this.position = position;
        this.row=getRow();
        this.column = getCol();
        this.rectangle = rectangle;
        //calculate_row();
        //calculate_col();
    }
    
    /**
     * Return the JavaFX rectangle for the GUI
     * 
     * @return
     */
    public Rectangle getRectangle() {
        return this.rectangle;
    }

    /**
     *  
     * 
     * @return row of the tile
     */
    public int getRow() {
        calculate_row();
        return this.row;
    }

    /**
     * 
     * @return col of the tile
     */
    public int getCol() {
        calculate_col();
        return this.column;
    }

    /**
     * Given the position of the tile in checker notation, calculate the row
     * 
     * This method is used for checking jump/capturing moves.
     * 
     */
    public void calculate_row() {
        if (1 <= this.position && this.position <= 4) {
            this.row = 1;
        } else if (5 <= this.position && this.position <= 8) {
            this.row = 2;
        } else if (9 <= this.position && this.position <= 12) {
            this.row = 3;
        } else if (13 <= this.position && this.position <= 16) {
            this.row = 4;
        } else if (17 <= this.position && this.position <= 20) {
            this.row = 5;
        } else if (21 <= this.position && this.position <= 24) {
            this.row = 6;
        } else if (25 <= this.position && this.position <= 28) {
            this.row = 7;
        } else if (29 <= this.position && this.position <= 32) {
            this.row = 8;
        }
    }

    /**
     * Given the position on the board in checker notation, calculate the column position
     * 
     * This method is used for finding capture/jump moves.
     * 
     */
    public void calculate_col() {
        int pos = this.position;
        switch (pos) {
            case 5:
            case 13:
            case 21:
                this.column = 1;
                break;
            case 1:
            case 9:
            case 17:
            case 25:
                this.column = 2;
                break;
            case 6:
            case 14:
            case 22:
            case 30:
                this.column = 3;
                break;
            case 2:
            case 10:
            case 18:
            case 26:
                this.column = 4;
                break;
            case 7:
            case 15:
            case 23:
            case 31:
                this.column = 5;
                break;
            case 3:
            case 11:
            case 19:
            case 27:
                this.column = 6;
                break;
            case 8:
            case 16:
            case 24:
            case 32:
                this.column = 7;
                break;
            case 4:
            case 12:
            case 20:
            case 28:
                this.column = 8;
                break;
            default:
                break;
        }
        }

    /**
     *
     * @return true if checker present, false otherwise
     */
    public boolean hasChecker() {
        return hasChecker;
    }

    /**
     * Set position of the tile to the given int
     * 
     * @param newPosition
     */
    public void setPosition(int newPosition) {
        this.position = newPosition;
    }

    /**
     *
     * @return the position of the tile
     */
    public int getPosition() {
        return this.position;
    }

    /**
     *
     * @return the current checker on the tile
     */
    public Checker getChecker() {
        return this.currentChecker;
    }

    /**
     * 
     * Reassign the given checker to take the position of the tile
     * 
     * Used during moves
     *
     * @param checker
     */
    public void setChecker(Checker checker) {
        checker.move(this.position);
        currentChecker = checker;
        hasChecker = true;
    }

    /**
     * Remove reference to the checker on the tile
     * 
     * Used when pieces are removed from the board, or moved to another position
     * 
     */
    public void removeChecker() {
        currentChecker = null;
        hasChecker = false;
    }

    @Override
    public String toString() {
        String s = "[Position: " + this.position + ", HasChecker: " + this.hasChecker + ", CurrentChecker: " + this.currentChecker + "]";
        return s;
    }

}
