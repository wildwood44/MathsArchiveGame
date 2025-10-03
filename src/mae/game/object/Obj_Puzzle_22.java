package mae.game.object;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import mae.game.EntityType;
import mae.game.GamePanel;
import mae.game.GameState;

public class Obj_Puzzle_22 extends Object {
	GamePanel gp;
	Graphics2D g2;
	public final static int objId = 16;
	public boolean open = false;
	public int input = 0;
	public boolean correct = true;

	public Obj_Puzzle_22(GamePanel gp) {
		super(gp);
		this.gp = gp;
		this.enId = objId;
		id = -1;
		type = EntityType.Object;
		image = idle1;
		width = gp.tileSize*4;
		height = gp.tileSize*3;
		getImage();
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
		idle1 = setup("/res/objects/pyramid-2", width, height);
	}
	
	public void display(Graphics2D g2, int screenX, int screenY) {
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		switch(direction) {
		case "idle":
			if(!opened) {image = idle1;} 
			else {
				/*NPC npc = new Prisoner_85(gp);
				npc.worldX = worldX;
				npc.worldY = worldY;
				npc.draw(g2);
				image = idle2;*/
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
