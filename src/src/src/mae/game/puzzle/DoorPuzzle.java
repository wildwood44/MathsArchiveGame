package mae.game.puzzle;

import mae.game.object.SumType;

public class DoorPuzzle {
	final int levels = 12;
	Puzzle problems[][] = new Puzzle[levels][levels];
	public int[] input = new int[5];
	
	public void setPuzzle() {
		problems[0][0] = new Puzzle("0+1=?", SumType.PLUS);
		problems[0][1] = new Puzzle("0=?", SumType.EQUAL, PuzzleType.EQUALS);
		problems[0][2] = new Puzzle("6+?=8", SumType.PLUS);
		problems[0][4] = new Puzzle("2+2=?", SumType.PLUS);
		problems[0][6] = new Puzzle("2-1=?", SumType.MINUS);
		problems[0][10] = new Puzzle("?+?=6", SumType.PLUS);
		problems[1][0] = new Puzzle("6/2=?", SumType.DIVIDE);
		problems[1][4] = new Puzzle("?/1=2", SumType.DIVIDE);
		problems[1][6] = new Puzzle("?*?=25", SumType.MULTI);
		problems[1][10] = new Puzzle("?*8=24", SumType.MULTI);
		problems[2][4] = new Puzzle("1-?=2", SumType.MINUS);
		problems[2][6] = new Puzzle("?/5=0.2", SumType.DIVIDE);
		problems[3][0] = new Puzzle("10% of 20=?", SumType.PERSN, PuzzleType.PERCENTAGE);
		problems[3][4] = new Puzzle("?0% of 144=72", SumType.PERSN, PuzzleType.PERCENTAGE);
		problems[3][6] = new Puzzle("?0% of 1=0.2", SumType.PERSN, PuzzleType.PERCENTAGE);
		problems[3][10] = new Puzzle("35% of 200=?*10", SumType.MULTI, PuzzleType.PERCENTAGE);
		problems[4][4] = new Puzzle("3/4+1/3=a/b", 2, SumType.PLUS, PuzzleType.FRACTION);
		problems[4][10] = new Puzzle("1/2*a/b=1/24", 2, SumType.MULTI, PuzzleType.FRACTION);
		problems[5][0] = new Puzzle("8a+3b=28", 2, SumType.PLUS, PuzzleType.ALGEBRA);
		problems[5][6] = new Puzzle("4x+3y+4y=ax+bx", 2, SumType.PLUS, PuzzleType.ALGEBRA);
		problems[5][10] = new Puzzle("x*x*x*x*x*x=x?", SumType.MULTI, PuzzleType.ALGEBRA);
	}
	
	public Puzzle getPuzzle(int floor, int room) {
		if (problems[floor][room]!=null) {
			return problems[floor][room];
		}
		return new Puzzle("X", SumType.MULTI);
	}
	
	public String getPuzzleText(int floor, int room) {
		if (problems[floor][room]!=null) {
			return problems[floor][room].printPuzzle();
		}
		return "X";
	}
	
	public boolean submit(int floor, int room, int card) {
		if (problems[floor][room]!=null) {
			//return problems[floor][room].isCorrect(card);
		}
		return false;
	}
}
