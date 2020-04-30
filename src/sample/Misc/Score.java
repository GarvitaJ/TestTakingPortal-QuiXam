package sample.Misc;

import java.io.Serializable;

public class Score implements Serializable {
   public String USN;
   public  int score;
    public Score(String USN, int score){
        this.USN=USN;
        this.score=score;
    }

    public int getScore() {
        return score;
    }

    public String getUSN() {
        return USN;
    }
}
