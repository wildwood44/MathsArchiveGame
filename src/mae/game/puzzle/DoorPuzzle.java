package mae.game.puzzle;

import mae.game.GamePanel;
import mae.game.object.SumType;

public class DoorPuzzle {
	GamePanel gp;
	final int levels = 12;
	Puzzle problems[][] = new Puzzle[levels][levels];
	public int[] input = new int[5];
	
	public DoorPuzzle(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setPuzzle() {
		problems[0][0] = new Puzzle(gp, "0+1=?", SumType.PLUS);
		problems[0][1] = new Puzzle(gp, "0=?", SumType.EQUAL, PuzzleType.EQUALS);
		problems[0][2] = new Puzzle(gp, "6+?=8", SumType.PLUS);
		problems[0][4] = new Puzzle(gp, "2+2=?", SumType.PLUS);
		problems[0][6] = new Puzzle(gp, "2-1=?", SumType.MINUS);
		problems[0][10] = new Puzzle(gp, "?+?=6", SumType.PLUS);
		problems[1][0] = new Puzzle(gp, "6/2=?", SumType.DIVIDE);
		problems[1][4] = new Puzzle(gp, "?/1=2", SumType.DIVIDE);
		problems[1][6] = new Puzzle(gp, "?*?=25", SumType.MULTI);
		problems[1][10] = new Puzzle(gp, "?*8=24", SumType.MULTI);
		problems[2][4] = new Puzzle(gp, "1-?=2", SumType.MINUS);
		problems[2][6] = new Puzzle(gp, "?/5=0.2", SumType.DIVIDE);
		problems[3][0] = new Puzzle(gp, "10% of 20=?", SumType.PERSN, PuzzleType.PERCENTAGE);
		problems[3][4] = new Puzzle(gp, "?0% of 144=72", SumType.PERSN, PuzzleType.PERCENTAGE);
		problems[3][6] = new Puzzle(gp, "?0% of 1=0.2", SumType.PERSN, PuzzleType.PERCENTAGE);
		problems[3][10] = new Puzzle(gp, "35% of 200=?*10", SumType.MULTI, PuzzleType.PERCENTAGE);
		problems[4][4] = new Puzzle(gp, "3/4+1/3=a/b", 2, SumType.PLUS, PuzzleType.FRACTION);
		problems[4][10] = new Puzzle(gp, "1/2*a/b=1/24", 2, SumType.MULTI, PuzzleType.FRACTION);
		problems[5][0] = new Puzzle(gp, "8a+3b=28", 2, SumType.PLUS, PuzzleType.ALGEBRA);
		problems[5][6] = new Puzzle(gp, "4x+3y+4y=ax+by", 2, SumType.PLUS, PuzzleType.ALGEBRA);
		problems[5][10] = new Puzzle(gp, "x*x*x*x*x*x=x?", SumType.MULTI, PuzzleType.ALGEBRA);
		problems[6][0] = new Puzzle(gp, "300cm=?m", 2, SumType.EQUAL, PuzzleType.MEASURE);
		problems[6][6] = new Puzzle(gp, "?mi/35min=6mph", 2, SumType.DIVIDE, PuzzleType.TIME);
		problems[6][10] = new Puzzle(gp, "?m=7000mm", SumType.EQUAL, PuzzleType.MEASURE);
		problems[7][4] = new Puzzle(gp, "Triangle=(120,50,?)", SumType.PLUS, PuzzleType.GEOMATRY);
		//problems[8][0] = new Puzzle(gp, "0.042mg", SumType.EQUAL, PuzzleType.WEIGHT);
		//problems[8][4] = new Puzzle(gp, "2.2lb", SumType.EQUAL, PuzzleType.WEIGHT);
		//problems[8][6] = new Puzzle(gp, "7000g", SumType.EQUAL, PuzzleType.WEIGHT);
		//problems[8][10] = new Puzzle(gp, "6lb", SumType.EQUAL, PuzzleType.WEIGHT);
		problems[8][0] = new Puzzle(gp, "range=(27,23,26,29,24)", SumType.EQUAL, PuzzleType.STAT);
		problems[8][4] = new Puzzle(gp, "mode=(1,2,2,4,4,4,6,10)", SumType.EQUAL, PuzzleType.STAT);
		problems[8][6] = new Puzzle(gp, "mean=(3,4,5,8,8,11,12,13)", SumType.EQUAL, PuzzleType.STAT);
		problems[8][10] = new Puzzle(gp, "medium=(35,23,76,34,56)", SumType.EQUAL, PuzzleType.STAT);
		problems[9][0] = new Puzzle(gp, "√50=a√b", 2, SumType.EQUAL, PuzzleType.RADICAL);
		problems[9][4] = new Puzzle(gp, "8√4=?", SumType.EQUAL, PuzzleType.RADICAL);
		problems[10][6] = new Puzzle(gp, "ReflectY(2,3)=(a,b)", 2, SumType.EQUAL, PuzzleType.GRAPH);
		problems[10][10] = new Puzzle(gp, "Transform(1,7)+(left(2),up(2))=(a,b)",2, SumType.PLUS, PuzzleType.GRAPH);
		problems[11][4] = new Puzzle(gp, "even(1,2,3,4,5,6)=?%", 2, SumType.EQUAL, PuzzleType.PROBABIL);
		problems[11][6] = new Puzzle(gp, "prime(1,2,3,5,7,11,13,17,19,23=a/b)", SumType.EQUAL, PuzzleType.PROBABIL);
	}
	
	public Puzzle getPuzzle(int floor, int room) {
		if (problems[floor][room]!=null) {
			return problems[floor][room];
		}
		return new Puzzle(gp, "X", SumType.MULTI);
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
