package checkers_v5;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Checker is an object representing each piece on the board
 * It holds a reference to it's owner, its surrounding positions, the row and column, position
 * As well as the corresponding javaFX object in the GUI
 *
 * @author 215865
 * @version (a version number or a date)
 */
public class Checker {

    // instance variables - replace the example below with your own
    private boolean isKing;
    private int position;
    private final String owner;
    private List<Integer> neighbours;
    private List<Integer> surrounding;
    private int row;
    private int column;
    private final Circle circle;

    /**
     * Create a new Checker at the given position, with the given owner, which is in GUI with given circle
     * 
     * @param position The position of the checker in checker notation
     * @param owner The player who owns the piece
     * @param circle GUI object
     */
    public Checker(int position, String owner, Circle circle) {
        this.owner = owner;
        this.position = position;
        this.isKing = false;
        this.circle = circle;
        this.surrounding = new ArrayList<>();
        calculateNeighbouringPositions();
        calculate_row();
        calculate_col();
    }

    /**
     *
     * @return the GUI object
     */
    public Circle getCircle() {
        return this.circle;
    }

    /**
     * Creates a copy of the current checker to be used in the Minimax algorithm when copying the state
     * 
     * @return a clone of the current checker
     */
    public Checker cloneChecker() {
        Checker clone = new Checker(this.position, this.owner, this.circle);
        if (this.isKing) {
            clone.setKing();
        }
        return clone;
    }

    /**
     * Calculates the neighbouring positions for a checker, using checker notation
     * 
     * If the checker is a  king, keep all of them, if they're not a king, remove the positions which are 
     * in the opposite direction to current play. 
     * 
     * 
     */
    public void calculateNeighbouringPositions() {
        List<Integer> neighbouringPositions = new ArrayList<>();
        int pos = this.position;
        if (1 <= pos && pos < 4) {
            neighbouringPositions.add(pos + 4);
            neighbouringPositions.add(pos + 5);
        } else if (pos == 12 || pos == 20 || pos == 28) {
            neighbouringPositions.add(pos - 4);
            neighbouringPositions.add(pos + 4);
        } else if (pos == 4) {
            neighbouringPositions.add(pos + 4);
            //neighbouringTiles.add(board[pos + 3]);
        } else if (6 <= pos && pos <= 8 || 14 <= pos && pos <= 16 || 22 <= pos && pos <= 24) {
            neighbouringPositions.add(pos - 5);
            neighbouringPositions.add(pos - 4);
            neighbouringPositions.add(pos + 3);
            neighbouringPositions.add(pos + 4);
        } else if (9 <= pos && pos <= 11 || 17 <= pos && pos <= 19 || 25 <= pos && pos <= 27) {
            neighbouringPositions.add(pos - 4);
            neighbouringPositions.add(pos - 3);
            neighbouringPositions.add(pos + 4);
            neighbouringPositions.add(pos + 5);
        } else if (30 <= pos && pos <= 32) {
            neighbouringPositions.add(pos - 5);
            neighbouringPositions.add(pos - 4);
        } else if (pos == 5 || pos == 13 || pos == 21) {
            neighbouringPositions.add(pos - 4);
            neighbouringPositions.add(pos + 4);

        } else if (pos == 29) {
            neighbouringPositions.add(pos - 4);
        }
        
        this.surrounding.addAll(neighbouringPositions);
        // If the checker isn't a king, then only add positions which are less than for user, or greater than for computer checkers
        if (!this.isKing()) {
            if (this.owner.equals("User")) {
                neighbouringPositions.removeIf(n -> (n > pos));
            } else if (this.owner.equals("Computer")) {
                neighbouringPositions.removeIf(n -> (n < pos));
            }
        }
        this.neighbours = neighbouringPositions;
    }

    /**
     *
     * @return the neighbouring positions in checker notation
     */
    public List<Integer> getNeighbouringPositions() {
        calculateNeighbouringPositions();
        return this.neighbours;
    }

    /**
     * Checks if a checker is 'protected' for use in heuristic function
     * 
     * A checker is classed as being protected if the surrounding tiles 
     * are occupied by the player's own checkers
     * 
     * @param state
     * @return
     */
    public boolean isProtected(Tile[] state) {
        int count = 0;
        for (Integer i : this.surrounding) {
            Tile t = state[i - 1];
            if (t.hasChecker() && t.getChecker().getOwner().equals(this.owner)) {
                count++;
            }
        }
        return count == this.surrounding.size(); //If every neighbouring tile is occupied by owner checker
    }
    
    /**
     *
     * @return
     */
    public int getPosition() {
        return this.position;
    }

    /**
     * If the checker has reached the opposite end of the board, they are crowned
     * This method is checked during each move so it is up to date
     *
     * @return true if the current checker is king, false otherwise
     */
    public boolean isKing() {
        //if reached the opposite end of board
        if ("Computer".equals(this.owner) && this.row == 8) {
            setKing();
        } else if ("User".equals(this.owner) && this.row == 1) {
            setKing();
        }

        return this.isKing;
    }

    /**
     *
     */
    public void setKing() {
        this.isKing = true;
    }

    /**
     * Make changes to the GUI representation once a king
     * 
     */
    public void paintKing() {
        this.circle.setStroke(Color.BLUE);
        this.circle.setStrokeDashOffset(1.0);
        this.circle.setStrokeWidth(3.0);
    }

    /**
     *
     * @return player who owns the piece
     */
    public String getOwner() {
        return this.owner;
    }

    /**
     * Moves the current piece to the specified position
     *
     * @param newPosition
     *
     */
    public void move(int newPosition) {
        this.position = newPosition;
        calculate_row();
        calculate_col();
        calculateNeighbouringPositions();
        isKing();
    }

    @Override
    public String toString() {
        String s = "[Position: " + this.position + ", Owner: " + this.owner + ", King: " + this.isKing + ", Neighbours: " + this.neighbours + "]";
        return s;
    }

    /**
     *
     * @return
     */
    public int getRow() {
        calculate_row();
        return this.row;
    }

    /**
     *
     * @return
     */
    public int getCol() {
        calculate_col();
        return this.column;
    }

    /**
     * 
     * Given the position on the board in checker notation, calculate the row 
     * 
     * This method is used for finding capture/jump moves.
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
     *Given the position on the board in checker notation, calculate the column position
     * 
     * This method is used for finding capture/jump moves.
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

}
