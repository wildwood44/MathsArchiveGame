package mae.game.object;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import mae.game.EntityType;
import mae.game.GamePanel;
import mae.game.GameState;

public class Obj_Puzzle_5 extends Object {
	GamePanel gp;
	Graphics2D g2;
	public final static int objId = 16;
	public boolean open = false;
	public int input = 0;
	public boolean correct = true;

	public Obj_Puzzle_5(GamePanel gp) {
		super(gp);
		this.gp = gp;
		this.enId = objId;
		id = -1;
		type = EntityType.Object;
		width = gp.tileSize;
		height = gp.tileSize;
		getImage();
		image = idle1;
		solidArea = new Rectangle(0, 0, width, height);
		size = gp.tileSize;
	}

	public void interact() {
		if(!opened) {
			gp.gameState = GameState.puzzleState;
			gp.ui.selectedObject = puzzle;
			System.out.println(gp.ui.selectedObject);
		}
		
		gp.keyH.enterPressed = false;
		gp.keyH.upPressed = false;
		gp.keyH.downPressed = false;
		gp.keyH.numberPressed = false;
	}
	
	public void lock(int key) {
		this.key = key;
		for(int j = 0; j < gp.obj[floor].length - 1; j++) {
			if(gp.obj[floor][j] != null && gp.obj[floor][j].id == key){
				gp.obj[floor][j].setLock(true);
				break;
			}
		}
	}
	
	@Override
	public void getImage() {
		idle1 = setup("/res/objects/formula_lock_PLUS1", width, height);
		idle2 = setup("/res/objects/formula_lock_PLUS1", width, height);
	}
	
	public void display(Graphics2D g2, int screenX, int screenY) {
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		switch(direction) {
		case "idle":
			if(!opened) {image = idle1;} 
			else {
				image = idle2;
			}
			break;
		}
		g2.drawImage(image, tempScreenX, tempScreenY, null);
	}
	
	public void drawPuzzle(Graphics2D g2) {
		this.g2 = g2;
		puzzle.drawPuzzle(g2);
	}
}
