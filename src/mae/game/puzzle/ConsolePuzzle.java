package mae.game.puzzle;

import mae.game.object.SumType;

public class ConsolePuzzle {
	final int levels = 12;
	Puzzle problems[] = new Puzzle[levels];
	
	public void setPuzzle() {
		problems[0] = new Puzzle("13-?=11", SumType.MINUS);
		problems[1] = new Puzzle("3/3=?", SumType.DIVIDE);//"0=?";
		problems[2] = new Puzzle("6+?=8", SumType.PLUS);
		problems[3] = new Puzzle("2+2=?", SumType.PLUS);
		problems[4] = new Puzzle("2-1=?", SumType.MINUS);
		problems[5] = new Puzzle("0=?", SumType.EQUAL);//"0=?";
		problems[6] = new Puzzle("6+?=8", SumType.PLUS);
		problems[7] = new Puzzle("2+2=?", SumType.PLUS);
		problems[8] = new Puzzle("2-1=?", SumType.MINUS);
		problems[9] = new Puzzle("0=?", SumType.EQUAL);//"0=?";
		problems[10] = new Puzzle("6+?=8", SumType.PLUS);
		problems[11] = new Puzzle("2+2=?", SumType.PLUS);
	}
	
	public Puzzle getPuzzle(int floor) {
		if (problems[floor]!=null) {
			return problems[floor];
		}
		return new Puzzle("X", SumType.MULTI);
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
