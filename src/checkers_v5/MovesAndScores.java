package checkers_v5;

/**
 *
 * @author liv
 */
public class MovesAndScores {
    int score;
    Move move;
    String player;
    
    MovesAndScores(int currentScore, Move move, String player) {
        this.score=currentScore;
        this.move = move;
        this.player = player;
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
