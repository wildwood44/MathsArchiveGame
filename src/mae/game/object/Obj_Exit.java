package mae.game.object;

import mae.game.GamePanel;
import mae.game.puzzle.Puzzle;

public class Obj_Exit extends Obj_Door {
	public final static int objId = 7;

	public Obj_Exit(GamePanel gp) {
		super(gp);
	}
	
	public Obj_Exit(GamePanel gp, int id, int floor, Puzzle puzzle) {
		this(gp);
		this.puzzle = puzzle;
		this.description = puzzle.printPuzzle();
		//this.key = solution;
	}

	public void interact() {
		if(!opened) {
			gp.playSE(4);
			opened = true;
			moving = true;
		} 
		else if (opened){
			gp.s.credits = true;
		}
		gp.keyH.enterPressed = false;
		gp.keyH.upPressed = false;
		gp.keyH.downPressed = false;
	}
}
