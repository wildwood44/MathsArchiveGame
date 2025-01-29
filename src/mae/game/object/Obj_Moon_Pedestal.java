package mae.game.object;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import mae.game.EntityType;
import mae.game.GamePanel;
import mae.game.GameState;

public class Obj_Moon_Pedestal extends Object {
	GamePanel gp;
	public final static int objId = 10;
	public boolean open = false;
	public int input = 0;
	public boolean correct = true;

	public Obj_Moon_Pedestal(GamePanel gp) {
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
		solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
		size = gp.tileSize;
		//for(int lock: unlock) {
		//}
	}

	public void interact() {
		if(!opened) {
			gp.items[1].opened = true;
			opened = true;
			gp.ui.message = "Press ENTER while selected for puzzle help!";
			gp.gameState = GameState.notifyState;
		}
		gp.keyH.enterPressed = false;
		gp.keyH.upPressed = false;
		gp.keyH.downPressed = false;
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
		idle1 = setup("/res/objects/telp_pedestal1", gp.tileSize, gp.tileSize);
		idle2 = setup("/res/objects/telp_pedestal2", gp.tileSize, gp.tileSize);
	}
	
	public void display(Graphics2D g2, int screenX, int screenY) {
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		switch(direction) {
		case "idle":
			if(!opened) {image = idle1;} 
			else {image = idle2;}
			break;
		}
		g2.drawImage(image, tempScreenX, tempScreenY, null);
	}
}
