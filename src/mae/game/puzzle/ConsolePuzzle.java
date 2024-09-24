package mae.game.puzzle;

import mae.game.GamePanel;
import mae.game.object.SumType;

public class ConsolePuzzle {
	GamePanel gp;
	final int levels = 12;
	Puzzle problems[] = new Puzzle[levels];
	
	public ConsolePuzzle(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setPuzzle() {
		problems[0] = new Puzzle(gp, "13-?=11", SumType.MINUS);
		problems[1] = new Puzzle(gp, "3/3=?", SumType.DIVIDE);//"0=?";
		problems[2] = new Puzzle(gp, "6+?=8", SumType.PLUS);
		problems[3] = new Puzzle(gp, "2+2=?", SumType.PLUS);
		problems[4] = new Puzzle(gp, "2-1=?", SumType.MINUS);
		problems[5] = new Puzzle(gp, "1*1=?", SumType.MULTI);//"0=?";
		problems[6] = new Puzzle(gp, "6+?=8", SumType.PLUS);
		problems[7] = new Puzzle(gp, "2+2=?", SumType.PLUS);
		problems[8] = new Puzzle(gp, "2-1=?", SumType.MINUS);
		problems[9] = new Puzzle(gp, "0=?", SumType.EQUAL);//"0=?";
		problems[10] = new Puzzle(gp, "6+?=8", SumType.PLUS);
		problems[11] = new Puzzle(gp, "2+2=?", SumType.PLUS);
	}
	
	public Puzzle getPuzzle(int floor) {
		if (problems[floor]!=null) {
			return problems[floor];
		}
		return new Puzzle(gp, "X", SumType.MULTI);
	}
	
	public String getPuzzleText(int floor) {
		if (problems[floor]!=null) {
			return problems[floor].printPuzzle();
		}
		return "X";
	}
	
	public boolean submit(int floor, int card) {
		if (problems[floor]!=null) {
			return problems[floor].isCorrect(card);
		}
		return false;
	}
}
