package mae.game.object;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import mae.game.EntityType;
import mae.game.GamePanel;

public class Obj_Converter extends Object {
	public final static int objId = 11;
	int slot1 = -1;

	public Obj_Converter(GamePanel gp) {
		super(gp);
		this.gp = gp;
		id = -1;
		enId = objId;
		width = gp.tileSize;
		height = gp.tileSize;
		type = EntityType.Object;
		solidArea = new Rectangle(0, 0, width, height);
	}

	public void interact() {
		//Remove card + Card should not be skeleton
		if(slot1 == -1 && gp.currentCard != 0) {
			slot1 = (int) gp.kc[gp.currentCard].useCard();
			gp.kc[gp.currentCard].locked = true;
			gp.currentCard = gp.kc[gp.currentCard].nextCard(gp.currentCard);
		}
		//Return cards + Skeleton card changed
		if(slot1 != -1) {
			switch(sum) {
			case ELEVEN: gp.kc[0].setValue(slot1*11); break;
			case NEGATIVE: gp.kc[0].setValue(slot1 - slot1 - slot1); break;
			case SQUARED: gp.kc[0].setValue(slot1 * slot1); break;
			case DECIMAL: gp.kc[0].setValue((double)slot1 + (double)0.5); break;
			default: gp.kc[0].setValue(slot1 *10);
			}
			gp.kc[slot1].locked = false;
			slot1 = -1;
		}
		gp.keyH.enterPressed = false;
		gp.keyH.upPressed = false;
		gp.keyH.downPressed = false;
	}
	
	@Override
	public void getImage() {
		idle1 = setup("/res/objects/converter1_"+sum.name(), gp.tileSize, gp.tileSize);
		idle2 = setup("/res/objects/converter1_"+sum.name(), gp.tileSize, gp.tileSize);		
		//left1 = setup("/res/objects/converter3_"+sum.name(), gp.tileSize, gp.tileSize);
	}
	
	public void display(Graphics2D g2, int screenX, int screenY) {
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		switch(direction) {
		case "idle":
			if(slot1 != -1) {image = idle2;} 
			else {image = idle1;}
			break;
		}
		g2.drawImage(image, tempScreenX, tempScreenY, null);
	}
}
