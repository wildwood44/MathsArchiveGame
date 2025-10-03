package mae.game.object;

import mae.game.GamePanel;
import mae.game.GameState;

public class Obj_Exit extends Obj_Door {
	GamePanel gp;
	public final static int objId = 7;

	public Obj_Exit(GamePanel gp) {
		super(gp);
		this.gp = gp;
		this.enId = objId;
	}

	public void interact() {
		if(!puzzle.opened) {
			gp.gameState = GameState.puzzleState;
			gp.ui.selectedObject = puzzle;
		}else if(!opened) {
			gp.playSE(4);
			opened = true;
			moving = true;
		} 
		else if (opened){
			gp.score.stopTimer();
			gp.s.credits = true;
		}
		gp.keyH.enterPressed = false;
		gp.keyH.upPressed = false;
		gp.keyH.downPressed = false;
	}
}
