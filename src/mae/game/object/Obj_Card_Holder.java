package mae.game.object;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import mae.game.EntityType;
import mae.game.GamePanel;
import mae.game.GameState;

public class Obj_Card_Holder extends Object {
	GamePanel gp;
	public final static int objId = 5;
	public boolean open = false;

	public Obj_Card_Holder(GamePanel gp) {
		super(gp);
		this.gp = gp;
		id = -1;
		enId = objId;
		type = EntityType.Object;
		getImage();
		if(!opened) {
			image = idle1;
		} else {
			image = idle2;
		}
		solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
		size = gp.tileSize;
	}

	public void interact() {
		if(!opened) {
			opened = true;
			System.out.println(key);
			gp.kc[key].opened = true;
			image = idle2;
			gp.ui.message = "Press \"" + key + "\" to select card " + key;
			gp.gameState = GameState.notifyState;
		}
		gp.keyH.enterPressed = false;
		gp.keyH.upPressed = false;
		gp.keyH.downPressed = false;
	}
	
	@Override
	public void getImage() {
		idle1 = setup("/res/objects/card_holder1", gp.tileSize, gp.tileSize);
		idle2 = setup("/res/objects/card_holder2", gp.tileSize, gp.tileSize);
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
