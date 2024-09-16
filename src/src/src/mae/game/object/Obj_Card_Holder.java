package mae.game.object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import mae.game.EntityType;
import mae.game.GamePanel;
import mae.game.GameState;

public class Obj_Card_Holder extends Object {
	GamePanel gp;
	//private static int id = -1;
	public boolean open = false;
	private int card;

	public Obj_Card_Holder(GamePanel gp, int card) {
		super(gp);
		this.gp = gp;
		this.card = card;
		id = -1;
		type = EntityType.Object;
		if(!opened) {
			image = setup("/res/objects/card_holder1", gp.tileSize, gp.tileSize);
		} else {
			image = setup("/res/objects/card_holder2", gp.tileSize, gp.tileSize);
		}
		getImage(image);
		solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
		int size = gp.tileSize;
	}

	public void interact() {
		if(!opened) {
			opened = true;
			gp.kc[card].opened = true;
			image = idle2;
			gp.ui.message = "Press \"" + card + "\" to select card " + card;
			gp.gameState = GameState.notifyState;
		}
		gp.keyH.enterPressed = false;
		gp.keyH.upPressed = false;
		gp.keyH.downPressed = false;
	}
	
	@Override
	public void getImage(BufferedImage image) {
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
