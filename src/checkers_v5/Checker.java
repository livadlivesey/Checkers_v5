package checkers_v5;

import java.util.ArrayList;
import java.util.List;

/**
 * Write a description of class Checker here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Checker {
    // instance variables - replace the example below with your own
    //private final javafx.scene.paint.Color colour;

    private boolean isKing;
    double tile_size = 20;
    int position;
    private String owner;
    private List<Integer> neighbours;
    private int row;
    private int column;

    public Checker(int position, String owner) {
        this.owner = owner;
        this.position = position;
        this.isKing = false;
        calculateNeighbouringPositions();
        calculate_row();
        calculate_col();
    }
    
    public Checker cloneChecker() {
        Checker clone = new Checker(this.position, this.owner);
        if (this.isKing) {
            clone.setKing();            
        }
        return clone;
    }

    public void calculateNeighbouringPositions() {
        //List<Tile> neighbouringTiles = new ArrayList<Tile>();
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

    public List<Integer> getNeighbouringPositions() {
        calculateNeighbouringPositions();
        return this.neighbours;
    }

    public int getPosition() {
        return this.position;
    }

    public boolean isKing() {
        //if reached the opposite end of board
        if (this.owner == "Computer" && this.row == 8) {
            setKing();
        } else if (this.owner == "User" && this.row == 1) {
            setKing();
        }
        return this.isKing;
    }
    
    

    public void setKing() {
        this.isKing = true;
    }

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
    
    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.column;
    }

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
