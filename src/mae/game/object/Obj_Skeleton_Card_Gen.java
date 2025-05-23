package mae.game.object;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import mae.game.EntityType;
import mae.game.GamePanel;

public class Obj_Skeleton_Card_Gen extends Object {
	public final static int objId = 4;
	int slot1 = -1, slot2 = -1;

	public Obj_Skeleton_Card_Gen(GamePanel gp) {
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
		//Remove 2 cards + Card should not be skeleton
		if(slot1 == -1 && gp.currentCard != 0) {
			slot1 = (int) gp.kc[gp.currentCard].useCard();
			gp.kc[gp.currentCard].locked = true;
			gp.currentCard = gp.kc[gp.currentCard].nextCard(gp.currentCard);
		}
		else if(slot2 == -1 && gp.currentCard != 0) {
			slot2 = (int) gp.kc[gp.currentCard].useCard();
			gp.kc[gp.currentCard].locked = true;
			gp.currentCard = gp.kc[gp.currentCard].nextCard(9);
		}
		//Return cards + Skeleton card changed
		if(slot1 != -1 && slot2 != -1) {
			switch(sum) {
			case PLUS: gp.kc[0].setValue(slot1 + slot2); break;
			case MINUS: gp.kc[0].setValue(slot1 - slot2); break;
			case MULTI: gp.kc[0].setValue(slot1 * slot2); break;
			case DIVIDE: gp.kc[0].setValue((double)slot1 / (double)slot2); break;
			default: gp.kc[0].setValue(slot1 + (slot2*10));
			}
			gp.kc[slot1].locked = false;
			slot1 = -1;
			gp.kc[slot2].locked = false;
			slot2 = -1;
		}
		gp.keyH.enterPressed = false;
		gp.keyH.upPressed = false;
		gp.keyH.downPressed = false;
		gp.keyH.numberPressed = false;
	}
	
	@Override
	public void getImage() {
		idle1 = setup("/res/objects/skeleton_card_generator1_"+sum.name(), gp.tileSize, gp.tileSize);
		idle2 = setup("/res/objects/skeleton_card_generator2_"+sum.name(), gp.tileSize, gp.tileSize);		
		left1 = setup("/res/objects/skeleton_card_generator3_"+sum.name(), gp.tileSize, gp.tileSize);
	}
	
	public void display(Graphics2D g2, int screenX, int screenY) {
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		switch(direction) {
		case "idle":
			if(slot2 != -1) {image = left1;} 
			else if(slot1 != -1) {image = idle2;} 
			else {image = idle1;}
			break;
		}
		g2.drawImage(image, tempScreenX, tempScreenY, null);
	}
}
