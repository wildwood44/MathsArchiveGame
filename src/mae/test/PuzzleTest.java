package mae.test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import mae.game.GamePanel;
import mae.game.object.SumType;
import mae.game.puzzle.Fraction;
import mae.game.puzzle.Puzzle;
import mae.game.puzzle.PuzzleType;

public class PuzzleTest {
	@Test
	public void equalTo(){
		GamePanel gp = null;
		Puzzle puzzle = new Puzzle(gp, "0=?", SumType.EQUAL, PuzzleType.EQUALS);
		assertEquals(puzzle.isCorrect(0), true);
		puzzle = new Puzzle(gp, "0=?", SumType.EQUAL, PuzzleType.EQUALS);
		assertEquals(puzzle.isCorrect(0), true);
	}
	@Test
	public void findMissing(){
		GamePanel gp = null;
		Puzzle puzzle = new Puzzle(gp, "0 1=?", SumType.PLUS);
		assertEquals(puzzle.isCorrect(1), true);
		puzzle = new Puzzle(gp, "2 1=?", SumType.MINUS);
		assertEquals(puzzle.isCorrect(1), true);
		puzzle = new Puzzle(gp, "53 ?=47", SumType.MINUS);
		assertEquals(puzzle.isCorrect(6), true);
		puzzle = new Puzzle(gp, "6 2=?", SumType.DIVIDE);
		assertEquals(puzzle.isCorrect(3), true);
		puzzle = new Puzzle(gp, "2 3=?", SumType.MULTI);
		assertEquals(puzzle.isCorrect(6), true);
		puzzle = new Puzzle(gp, "2 ?=6", SumType.MULTI);
		assertEquals(puzzle.isCorrect(3), true);
		puzzle = new Puzzle(gp, "? 2=6", SumType.MULTI);
		assertEquals(puzzle.isCorrect(3), true);
		puzzle = new Puzzle(gp, "? ?=25", SumType.MULTI);
		assertEquals(puzzle.isCorrect(5), true);
		puzzle = new Puzzle(gp, "-4 -3=?", SumType.PLUS);
		assertEquals(puzzle.isCorrect(-7), true);
	}
	@Test
	public void findPercentage(){
		GamePanel gp = null;
		Puzzle puzzle = new Puzzle(gp, "10% of 20=?", SumType.PERSN, PuzzleType.PERCENTAGE);
		assertEquals(puzzle.isCorrect(2), true);
		puzzle = new Puzzle(gp, "?0% of 1=0.2", SumType.PERSN, PuzzleType.PERCENTAGE);
		assertEquals(puzzle.isCorrect(2), true);
		puzzle = new Puzzle(gp, "20% of ?=0.2", SumType.PERSN, PuzzleType.PERCENTAGE);
		assertEquals(puzzle.isCorrect(1), true);
		puzzle = new Puzzle(gp, "35% of 200=?*10", SumType.MULTI, PuzzleType.PERCENTAGE);
		assertEquals(puzzle.isCorrect(7), true);
		puzzle = new Puzzle(gp, "Decrease 24 by 75%=?", SumType.PERSN, PuzzleType.D_PERCENTAGE);
		assertEquals(puzzle.isCorrect(6), true);
	}
	@Test
	public void findFaction(){
		GamePanel gp = null;
		Puzzle puzzle = new Puzzle(gp, "0.5 0.25=a/b", 2, SumType.PLUS, PuzzleType.FRACTION);
		assertEquals(puzzle.isCorrect(3), true);
		assertEquals(puzzle.isCorrect(4), true);
		puzzle = new Puzzle(gp, "0.5 a/b=0.042", 2, SumType.MULTI, PuzzleType.FRACTION);
		assertEquals(puzzle.isCorrect(1), true);
		assertEquals(puzzle.isCorrect(12), true);
		puzzle = new Puzzle(gp, "0.75 0.333=a/b", 2, SumType.MINUS, PuzzleType.FRACTION);
		assertEquals(puzzle.isCorrect(5), true);
		assertEquals(puzzle.isCorrect(12), true);
	}
	@Test
	public void convertDecToFrac() {
		Fraction puzzle = new Fraction("0.5");
		assertEquals((puzzle.convertDecToFrac(0.5).equals("1/2")), true);
		assertEquals((puzzle.convertDecToFrac(0.75).equals("3/4")), true);
		assertEquals((puzzle.convertDecToFrac(0.333).equals("1/3")), true);
		assertEquals((puzzle.convertDecToFrac(0.143).equals("1/7")), true);
		assertEquals((puzzle.convertDecToFrac(0.083).equals("1/12")), true);
		//assertEquals((puzzle.convertDecToFrac(0.042).equals("1/24")), true);
	}
	@Test
	public void findAlgebra(){
		GamePanel gp = null;
		Puzzle puzzle = new Puzzle(gp, "8a+3b=28", 2, SumType.PLUS, PuzzleType.ALGEBRA);
		assertEquals(puzzle.isCorrect(2), true);
		assertEquals(puzzle.isCorrect(4), true);
		puzzle = new Puzzle(gp, "4x+3y+4y=ax+by", 2, SumType.PLUS, PuzzleType.ALGEBRA);
		assertEquals(puzzle.isCorrect(4), true);
		assertEquals(puzzle.isCorrect(7), true);
		puzzle = new Puzzle(gp, "4x+5y+4y=ax+by", 2, SumType.PLUS, PuzzleType.ALGEBRA);
		assertEquals(puzzle.isCorrect(4), true);
		assertEquals(puzzle.isCorrect(9), true);
		puzzle = new Puzzle(gp, "x*x*x*x=x^?", SumType.MULTI, PuzzleType.ALGEBRA);
		assertEquals(puzzle.isCorrect(4), true);
		puzzle =  new Puzzle(gp, "10x*x*x*x^2=ax^b", 2, SumType.MULTI, PuzzleType.ALGEBRA);
		assertEquals(puzzle.isCorrect(10), true);
		assertEquals(puzzle.isCorrect(5), true);
	}
	@Test 
	public void findMeasurement() {
		GamePanel gp = null;
		Puzzle puzzle =  new Puzzle(gp, "300cm=?m", 2, SumType.EQUAL, PuzzleType.MEASURE);
		assertEquals(puzzle.isCorrect(3), true);
		//problems[5][6] = new Puzzle(gp, "?mi/35min=6mph", 2, SumType.DIVIDE, PuzzleType.TIME);
		puzzle = new Puzzle(gp, "?m=7000mm", SumType.EQUAL, PuzzleType.MEASURE);
		assertEquals(puzzle.isCorrect(7), true);
	}
	@Test
	public void findTime() {
		GamePanel gp = null;
		Puzzle puzzle = new Puzzle(gp, "?mi/35min=6mph", 2, SumType.DIVIDE, PuzzleType.TIME);
		assertEquals(puzzle.isCorrect(3.5), true);
	}
	@Test
	public void findGeomatry() {
		GamePanel gp = null;
		Puzzle puzzle = new Puzzle(gp, "Triangle=(120,50,?)", SumType.PLUS, PuzzleType.GEOMATRY);
		assertEquals(puzzle.isCorrect(10), true);
		puzzle = new Puzzle(gp, "Square=(115,145,70,?)", SumType.PLUS, PuzzleType.GEOMATRY);
		assertEquals(puzzle.isCorrect(30), true);
	}
	@Test
	public void findWeight() {
		GamePanel gp = null;
		Puzzle puzzle = new Puzzle(gp, "42000000mg=?kg", SumType.EQUAL, PuzzleType.WEIGHT);
		assertEquals(puzzle.isCorrect(42), true);
		//puzzle = new Puzzle(gp, "2.2lb", SumType.EQUAL, PuzzleType.WEIGHT);
		puzzle = new Puzzle(gp, "7000g=?kg", SumType.EQUAL, PuzzleType.WEIGHT);
		assertEquals(puzzle.isCorrect(7), true);
		puzzle = new Puzzle(gp, "?kg=0.006ton", SumType.EQUAL, PuzzleType.WEIGHT);
		assertEquals(puzzle.isCorrect(6), true);
		//puzzle = new Puzzle(gp, "3500mm", SumType.EQUAL, PuzzleType.WEIGHT);
		//assertEquals(puzzle.isCorrect(33.5), true);
	}
	@Test
	public void findStatistic() {
		GamePanel gp = null;
		Puzzle puzzle = new Puzzle(gp, "mean(3,4,5,8,8,11,12,13)=?", SumType.EQUAL, PuzzleType.STAT);
		assertEquals(puzzle.isCorrect(8), true);
		puzzle = new Puzzle(gp, "mode(1,2,2,4,4,4,6,10)=?", SumType.EQUAL, PuzzleType.STAT);
		assertEquals(puzzle.isCorrect(4), true);
		puzzle = new Puzzle(gp, "medium(17,5,9,13,16,2)=?", SumType.EQUAL, PuzzleType.STAT);
		assertEquals(puzzle.isCorrect(11), true);
		puzzle = new Puzzle(gp, "range(27,23,26,29,24)=?", SumType.EQUAL, PuzzleType.STAT);
		assertEquals(puzzle.isCorrect(6), true);
	}
	@Test
	public void findRadical() {
		GamePanel gp = null;
		Puzzle puzzle = new Puzzle(gp, "√50=a√b", 2, SumType.EQUAL, PuzzleType.RADICAL);
		assertEquals(puzzle.isCorrect(5), true);
		assertEquals(puzzle.isCorrect(2), true);
		puzzle = new Puzzle(gp, "8√4=?", SumType.EQUAL, PuzzleType.RADICAL);
		assertEquals(puzzle.isCorrect(16), true);
		puzzle = new Puzzle(gp, "√81=?", SumType.EQUAL, PuzzleType.RADICAL);
		assertEquals(puzzle.isCorrect(9), true);
		puzzle = new Puzzle(gp, "√12=a√b", 2, SumType.EQUAL, PuzzleType.RADICAL);
		assertEquals(puzzle.isCorrect(2), true);
		assertEquals(puzzle.isCorrect(3), true);
		puzzle = new Puzzle(gp, "√50=a√b", 2, SumType.EQUAL, PuzzleType.RADICAL);
		assertEquals(puzzle.isCorrect(5), true);
		assertEquals(puzzle.isCorrect(2), true);
	}
	@Test
	public void findGraph() {
		GamePanel gp = null;
		Puzzle puzzle = new Puzzle(gp, "ReflectY(2,3)=(a,b)", 2, SumType.EQUAL, PuzzleType.GRAPH);
		assertEquals(puzzle.isCorrect(-2), true);
		assertEquals(puzzle.isCorrect(3), true);
		puzzle = new Puzzle(gp, "ReflectX(4,-2)=(a,b)", 2, SumType.EQUAL, PuzzleType.GRAPH);
		assertEquals(puzzle.isCorrect(4), true);
		assertEquals(puzzle.isCorrect(2), true);
		puzzle = new Puzzle(gp, "Transform(1,5)+(up(2),right(2))=(a,b)",2, SumType.PLUS, PuzzleType.GRAPH);
		assertEquals(puzzle.isCorrect(7), true);
		assertEquals(puzzle.isCorrect(3), true);
		puzzle = new Puzzle(gp, "Rotate180(-1,-2)=(a,b)", 2, SumType.EQUAL, PuzzleType.GRAPH);
		assertEquals(puzzle.isCorrect(1), true);
		assertEquals(puzzle.isCorrect(2), true);
		puzzle = new Puzzle(gp, "Rotate90(5,6)=(a,b)", 2, SumType.EQUAL, PuzzleType.GRAPH);
		assertEquals(puzzle.isCorrect(6), true);
		assertEquals(puzzle.isCorrect(-5), true);
	}
	@Test
	public void findProbability() {
		GamePanel gp = null;
		Puzzle puzzle = new Puzzle(gp, "even(1,2,3,4,5,6)=?%", 2, SumType.EQUAL, PuzzleType.PROBABIL);
		assertEquals(puzzle.isCorrect(50), true);
		puzzle = new Puzzle(gp, "even(1,2,3,4,5,6)=?0%", 2, SumType.EQUAL, PuzzleType.PROBABIL);
		assertEquals(puzzle.isCorrect(5), true);
		puzzle = new Puzzle(gp, "odd(1,2,3,4,5,6)=?0%", SumType.EQUAL, PuzzleType.PROBABIL);
		assertEquals(puzzle.isCorrect(5), true);
		puzzle = new Puzzle(gp, "prime(1,2,3,5,7,11,13,17,19,23)=a/b",2, SumType.EQUAL, PuzzleType.PROBABIL);
		assertEquals(puzzle.isCorrect(9), true);
		assertEquals(puzzle.isCorrect(10), true);
	}
}
