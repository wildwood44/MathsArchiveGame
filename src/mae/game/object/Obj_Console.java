package mae.game.object;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import mae.game.EntityType;
import mae.game.GamePanel;

public class Obj_Console extends Object {
	GamePanel gp;
	public final static int objId = 2;
	public boolean open = false;
	public int input = 0;
	public boolean correct = true;

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
		//}
	}

	public void interact() {
		if(!opened) {
			ans[input] = gp.kc[gp.currentCard].useCard();
			System.out.println(gp.currentCard);
			if(puzzle.getInputCount() -1 > input) {
				if(puzzle.isCorrect(gp.kc[gp.currentCard].useCard())) {
					correct = true;
				} else {
					correct = false;
				}
				input++;
			} else if(puzzle.isCorrect(gp.kc[gp.currentCard].useCard()) && correct) {
				isCorrect = true;
				gp.playSE(2);
				for(int j = 0; j < gp.obj[floor].length - 1; j++) {
					if(gp.obj[floor][j] != null && gp.obj[floor][j].id == key){
						gp.obj[floor][j].setLock(false);
						break;
					}
				}
				gp.score.addPoints(1);
				input = 0;
				opened = true;
				puzzle.opened = true;
			} else {
				isWrong = true;
				gp.playSE(3);
				input = 0;
			}
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
			drawSpeech(g2, tempScreenX - gp.tileSize, tempScreenY - (int)(gp.tileSize*1.5));
		}
		
	}
}
