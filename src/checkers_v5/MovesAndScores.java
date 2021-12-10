package checkers_v5;

/**
 *
 * @author liv
 */
public class MovesAndScores {
    int score;
    Move move;
    
    MovesAndScores(int currentScore, Move move) {
        this.score=currentScore;
        this.move = move;
    }
    
    public Move getMove() {
        return this.move;
        
    }
    
    public int getScore() {
        return this.score;
    }
    
    @Override
    public String toString() {
        String s = "[Move: " + this.move.toString() + ", Score: " + this.score + " ]";
        return s;
    }
}
