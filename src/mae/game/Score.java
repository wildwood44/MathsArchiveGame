package mae.game;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Score implements Serializable {
	GamePanel gp;
	public int points;
	public int completePuzzle;
	public long timer;
	public float hour;
	public float min;
	public float seconds;
	
	public Score(GamePanel gp){
		this.gp = gp;
		points = 0;
		completePuzzle = 0;
		timer = System.currentTimeMillis();
	}
	
	public void addPoints(int point) {
		points += point;
	}
	
	public String getTotalCompletion() {
		return gp.ps.totalCompletePuzzle() + "/" + gp.ps.totalPuzzle();
	}
	
	public void stopTimer() {
		seconds = getElapsedTimeSeconds();
		min = getElapsedTimeMinutes();
		hour = getElapsedTimeHours();
	}
	
	public float getElapsedTimeHours() {
        return (System.currentTimeMillis() - timer) / 1000f / 60f / 60f;
    }
	
	public float getElapsedTimeMinutes() {
        return (System.currentTimeMillis() - timer) / 1000f / 60f;
    }
	
	public float getElapsedTimeSeconds() {
        return (System.currentTimeMillis() - timer) / 1000f;
    }
	
	public String elapsedTime() {
		return String.format("%d",(long)hour)+"h : "+String.format("%d",(long)min)+"m : "+String.format("%d",(long)seconds) +"s";
	}
}
