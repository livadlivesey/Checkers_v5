package checkers_v5;


/**
 * Write a description of class Board here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Board
{
    // instance variables - replace the example below with your own
    int[][] checkerboard;
    Checker[] state;

    /**
     * Constructor for objects of class Board
     */
    public Board()
    {
        // initialise instance variables
        checkerboard = new int[8][8];
        state = new Checker[32];
        for (int row=0; row<8; row++) {
            for (int col=0; col<8; col++) {
                if (row % 2 == col % 2) {
                    checkerboard[row][col] = 0;
                }
            }
        }
        
    }
    
    public void printBoard() {
        for (int row=0; row<8; row++) {
            for (int col=0; col<8; col++) {
                System.out.print(checkerboard[row][col]);
            }
        }
    }

    public int getTile(int row, int col) {
        return checkerboard[row][col];
    }
    
    public void setTile(int row, int col) {
        checkerboard[row][col] = 1;
    }
}
