package mae.game.object;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import mae.game.EntityType;
import mae.game.GamePanel;
import mae.game.GameState;
import mae.game.npc.NPC;
import mae.game.npc.Prisoner_85;
import mae.game.puzzle.Puzzle_85;

public class Obj_Cage extends Object {
	GamePanel gp;
	Graphics2D g2;
	public final static int objId = 16;
	public boolean open = false;
	public int input = 0;
	public boolean correct = true;

	public Obj_Cage(GamePanel gp) {
		super(gp);
		this.gp = gp;
		this.enId = objId;
		id = -1;
		type = EntityType.Object;
		getImage();
		if(!opened) {
			image = idle1;
		} else {
			image = idle2;
		}
		solidArea = new Rectangle(0, 0, gp.tileSize*2, gp.tileSize*2);
		size = gp.tileSize;
	}

	public void interact() {
		if(!opened) {
			gp.gameState = GameState.puzzleState;
			gp.ui.selectedObject = puzzle;
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
		idle1 = setup("/res/objects/cage1", gp.tileSize*2, gp.tileSize*2);
		idle2 = setup("/res/objects/cage2", gp.tileSize*2, gp.tileSize*2);
	}
	
	public void display(Graphics2D g2, int screenX, int screenY) {
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		switch(direction) {
		case "idle":
			if(!opened) {image = idle1;} 
			else {
				NPC npc = new Prisoner_85(gp);
				npc.worldX = worldX;
				npc.worldY = worldY;
				npc.draw(g2);
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
