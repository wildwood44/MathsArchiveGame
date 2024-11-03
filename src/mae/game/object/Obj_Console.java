package mae.game.object;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import mae.game.EntityType;
import mae.game.GamePanel;

public class Obj_Console extends Object {
	GamePanel gp;
	public final static int objId = 2;
	public boolean open = false;

	public Obj_Console(GamePanel gp) {
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
		solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize*2);
		size = gp.tileSize;
		//for(int lock: unlock) {
			for(int j = 0; j < gp.obj[floor].length - 1; j++) {
				if(gp.obj[floor][j] != null && gp.obj[floor][j].id == key){
					gp.obj[floor][j].setLock(true);
					break;
				}
			}
		//}
	}

	public void interact() {
		if(!opened) {
			System.out.println(description);
			System.out.println(gp.kc[gp.currentCard].useCard() + " " + floor +" "+ key);
			if(puzzle.isCorrect(gp.kc[gp.currentCard].useCard())) {
				isCorrect = true;
				gp.playSE(2);
				for(int j = 0; j < gp.obj[floor].length - 1; j++) {
					if(gp.obj[floor][j] != null && gp.obj[floor][j].id == key){
						gp.obj[floor][j].setLock(false);
						break;
					}
				}
				opened = true;
			} else {
				isWrong = true;
				gp.playSE(3);
			}
		}
		gp.keyH.enterPressed = false;
		gp.keyH.upPressed = false;
		gp.keyH.downPressed = false;
	}
	
	@Override
	public void getImage() {
		idle1 = setup("/res/objects/Obj_Console1", gp.tileSize, gp.tileSize);
		idle2 = setup("/res/objects/Obj_Console2", gp.tileSize, gp.tileSize);
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
		if(isSelected) {
			drawSpeechBubble(g2, tempScreenX - gp.tileSize, tempScreenY - gp.tileSize*2);
		}
		
	}
}
