package mae.game.puzzle;

import mae.game.GamePanel;
import mae.game.object.SumType;

public class PuzzleSetter {
	GamePanel gp;
	final int levels = 12;
	Puzzle problems[][];
	public int[] input = new int[5];
	
	public PuzzleSetter(GamePanel gp) {
		this.gp = gp;
		problems = new Puzzle[gp.maxMap][25];
	}
	
	public void setPuzzle() {
		problems[0][0] = new Puzzle(gp, "0 1=?", SumType.PLUS);
		problems[0][1] = new Puzzle(gp, "0=?", SumType.EQUAL, PuzzleType.EQUALS);
		problems[0][2] = new Puzzle(gp, "6 ?=8", SumType.PLUS);
		problems[0][3] = new Puzzle(gp, "? 56=61", SumType.PLUS);
		problems[0][4] = new Puzzle(gp, "2 2=?", SumType.PLUS);
		problems[0][6] = new Puzzle(gp, "2 1=?", SumType.MINUS);
		problems[0][8] = new Puzzle(gp, "65 45=?", SumType.MINUS);
		problems[0][10] = new Puzzle(gp, "? ?=6", SumType.PLUS);
		problems[1][0] = new Puzzle(gp, "6 2=?", SumType.DIVIDE);
		problems[1][3] = new Puzzle(gp, "6 ?=42", SumType.MULTI);
		problems[1][4] = new Puzzle(gp, "? 1=2", SumType.DIVIDE);
		problems[1][6] = new Puzzle(gp, "? ?=25", SumType.MULTI);
		problems[1][10] = new Puzzle(gp, "? 8=24", SumType.MULTI);
		problems[2][0] = new Puzzle(gp, "0.75 4=?", SumType.MULTI);
		problems[2][4] = new Puzzle(gp, "1 ?=2", SumType.MINUS);
		problems[2][6] = new Puzzle(gp, "? 5=0.2", SumType.DIVIDE);
		problems[2][11] = new Puzzle(gp, "-4 -3=?", SumType.PLUS);
		problems[3][0] = new Puzzle(gp, "10% of 20=?", SumType.PERSN, PuzzleType.PERCENTAGE);
		problems[3][4] = new Puzzle(gp, "?0% of 144=72", SumType.PERSN, PuzzleType.PERCENTAGE);
		problems[3][6] = new Puzzle(gp, "?0% of 1=0.2", SumType.PERSN, PuzzleType.PERCENTAGE);
		problems[3][10] = new Puzzle(gp, "35% of 200=?*10", SumType.MULTI, PuzzleType.PERCENTAGE);
		problems[4][0] = new Puzzle(gp, "0.75 0.333=a/b", 2, SumType.MINUS, PuzzleType.FRACTION);
		problems[4][4] = new Puzzle(gp, "0.5 0.25=a/b", 2, SumType.PLUS, PuzzleType.FRACTION);
		problems[4][10] = new Puzzle(gp, "0.5 a/b=0.042", 2, SumType.MULTI, PuzzleType.FRACTION);
		problems[5][0] = new Puzzle(gp, "Transform(1,5)+(up(2),right(2))=(a,b)",2, SumType.PLUS, PuzzleType.GRAPH);
		problems[5][3] = new Puzzle(gp, "Rotate90(5,6)=(a,b)", SumType.EQUAL, PuzzleType.GRAPH);
		problems[5][6] = new Puzzle(gp, "ReflectY(2,3)=(a,b)", 2, SumType.EQUAL, PuzzleType.GRAPH);
		problems[5][10] = new Puzzle(gp, "ReflectX(4,-2)=(a,b)", 2, SumType.EQUAL, PuzzleType.GRAPH);
		problems[6][0] = new Puzzle(gp, "300cm=?m", 2, SumType.EQUAL, PuzzleType.MEASURE);
		problems[6][6] = new Puzzle(gp, "?mi/35min=6mph", 2, SumType.DIVIDE, PuzzleType.TIME);
		problems[6][8] = new Puzzle(gp, "?kg=0.006ton", SumType.EQUAL, PuzzleType.WEIGHT);
		problems[6][10] = new Puzzle(gp, "?m=7000mm", SumType.EQUAL, PuzzleType.MEASURE);
		problems[7][0] = new Puzzle(gp, "Square=(80,120,80,?)", SumType.PLUS, PuzzleType.GEOMATRY);
		problems[7][4] = new Puzzle(gp, "Triangle=(120,50,?)", SumType.PLUS, PuzzleType.GEOMATRY);
		problems[8][0] = new Puzzle(gp, "range(27,23,26,29,24)=?", SumType.EQUAL, PuzzleType.STAT);
		problems[8][3] = new Puzzle(gp, "medium(17,5,9,13,16,2)=?", SumType.EQUAL, PuzzleType.STAT);
		problems[8][4] = new Puzzle(gp, "mode(1,2,2,4,4,4,6,10)=?", SumType.EQUAL, PuzzleType.STAT);
		problems[8][6] = new Puzzle(gp, "mean(3,4,5,8,8,11,12,13)=?", SumType.EQUAL, PuzzleType.STAT);
		problems[8][10] = new Puzzle(gp, "medium(35,23,76,34,56)=?", SumType.EQUAL, PuzzleType.STAT);
		problems[9][0] = new Puzzle(gp, "even(1,2,3,4,5,6)=?0%", SumType.EQUAL, PuzzleType.PROBABIL);
		problems[9][4] = new Puzzle(gp, "prime(1,2,3,5,7,11,13,17,19)=a/b", 2, SumType.EQUAL, PuzzleType.PROBABIL);
		problems[10][0] = new Puzzle(gp, "10x*x*x*x^2=ax^b", 2, SumType.MULTI, PuzzleType.ALGEBRA);
		problems[10][6] = new Puzzle(gp, "8a+3b=28", 2, SumType.PLUS, PuzzleType.ALGEBRA);
		problems[10][10] = new Puzzle(gp, "4x+5y+4y=ax+by", 2, SumType.PLUS, PuzzleType.ALGEBRA);
		problems[11][4] = new Puzzle(gp, "√81=?", 2, SumType.EQUAL, PuzzleType.RADICAL);
		problems[11][6] = new Puzzle(gp, "√289", SumType.EQUAL, PuzzleType.RADICAL);
		problems[11][11] = new Puzzle(gp, "8√4=?", SumType.EQUAL, PuzzleType.RADICAL);
		//problems[11][11] = new Puzzle(gp, "√12=a√b", 2, SumType.EQUAL, PuzzleType.RADICAL);
		//problems[16][1] = new Puzzle(gp, "13 ?=11", SumType.MINUS);
		problems[16][1] = new Puzzle_5(gp);
		problems[17][1] = new Puzzle_6(gp);
		problems[21][1] = new Puzzle_10(gp);
		problems[23][1] = new Puzzle_12(gp);
		problems[28][1] = new Puzzle(gp, "3 3=?", SumType.DIVIDE);
		problems[30][1] = new Puzzle(gp, "? 6=24", SumType.MULTI);
		problems[31][1] = new Puzzle_20(gp);
		problems[33][1] = new Puzzle_22(gp);
		problems[35][1] = new Puzzle_24(gp);
		problems[37][1] = new Puzzle_26(gp);
		problems[40][1] = new Puzzle(gp, "? 4=0.75", SumType.DIVIDE);
		problems[48][1] = new Puzzle(gp, "20% of 20=?", SumType.PERSN, PuzzleType.PERCENTAGE);
		problems[50][1] = new Puzzle_39(gp);
		problems[51][1] = new Puzzle_40(gp);
		problems[54][1] = new Puzzle(gp, "50% of 10=?", SumType.PERSN, PuzzleType.PERCENTAGE);
		problems[58][1] = new Puzzle(gp, "Decrease 24 by 75%=?", SumType.PERSN, PuzzleType.D_PERCENTAGE);
		problems[62][1] = new Puzzle_51(gp);
		problems[70][1] = new Puzzle(gp, "3/5 1/2=a/b", 2, SumType.MINUS, PuzzleType.FRACTION);
		problems[72][1] = new Puzzle(gp, "Rotate90(2,3)=(a,b)", 2, SumType.EQUAL, PuzzleType.GRAPH);
		problems[73][1] = new Puzzle_62(gp);
		problems[82][1] = new Puzzle(gp, "Rotate180(-1,-2)=(a,b)", 2, SumType.EQUAL, PuzzleType.GRAPH);
		problems[90][1] = new Puzzle(gp, "7000g=?kg", SumType.EQUAL, PuzzleType.WEIGHT);
		problems[94][1] = new Puzzle(gp, "42000000mg=?kg", SumType.EQUAL, PuzzleType.WEIGHT);
		problems[96][1] = new Puzzle_85(gp);
		problems[100][1] = new Puzzle(gp, "Square=(115,145,70,?)", SumType.PLUS, PuzzleType.GEOMATRY);
		problems[108][1] = new Puzzle(gp, "range(18,22,14,12,15,16)=?", SumType.EQUAL, PuzzleType.STAT);
		problems[110][1] = new Puzzle_99(gp);
		problems[112][1] = new Puzzle(gp, "mode(7,2,13,7,3,5,11,1)=?", SumType.EQUAL, PuzzleType.STAT);
		problems[114][1] = new Puzzle(gp, "mean(4,5,6,7,8)=?", SumType.EQUAL, PuzzleType.STAT);
		problems[118][1] = new Puzzle(gp, "medium(7,8,2,4,2)=?", SumType.EQUAL, PuzzleType.STAT);
		problems[124][1] = new Puzzle(gp, "odd(1,2,3,4,5,6)=?0%", SumType.EQUAL, PuzzleType.PROBABIL);
		problems[141][1] = new Puzzle_130(gp);
		problems[142][1] = new Puzzle(gp, "x*x*x*x=x?", SumType.MULTI, PuzzleType.ALGEBRA);
		problems[148][1] = new Puzzle(gp, "√50=a√b", 2, SumType.EQUAL, PuzzleType.RADICAL);
		problems[150][1] = new Puzzle_139(gp);
	}
	
	public Puzzle getPuzzle(int floor, int room) {
		if (problems[floor][room]!=null) {
			return problems[floor][room];
		}
		return new Puzzle(gp, "X", SumType.MULTI);
	}
	
	public int totalPuzzle() {
		int total = 0;
		for(int i = 0; i < problems.length; i++) {
			for(int j = 0; j < problems[i].length; j++) {
				if(problems[i][j] != null) {
					total++;
				}
			}
		}
		return total;
	}
	
	public int totalCompletePuzzle() {
		int total = 0;
		for(int i = 0; i < problems.length; i++) {
			for(int j = 0; j < problems[i].length; j++) {
				if(problems[i][j] != null) {
					if(problems[i][j].opened) {
						System.out.println("Ping");
						total++;
					}
				}
			}
		}
		return total;
	}
	
	public String getPuzzleText(int floor, int room) {
		if (problems[floor][room]!=null) {
			return problems[floor][room].printPuzzle();
		}
		return "X";
	}
}
