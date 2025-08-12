package mae.game.object;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import mae.game.EntityType;
import mae.game.GamePanel;

public class Obj_Arrow extends Object {
	public final static int objId = 32;

	public Obj_Arrow(GamePanel gp) {
		super(gp);
		this.gp = gp;
		enId = objId;
		id = -1;
		width = gp.tileSize;
		height = gp.tileSize;
		type = EntityType.Object;
		solidArea = new Rectangle(0, 0, width, height);
		getImage();
	}

	public void interact() {
		if(gp.keyH.rightPressed) {
			spriteNum++;
			if (spriteNum>4) {
				spriteNum=1;
			}
		} else if(gp.keyH.leftPressed) {
			spriteNum--;
			if (spriteNum<1) {
				spriteNum=4;
			}
		}
	}
	
	@Override
	public void getImage() {
		idle1 = setup("/res/objects/arrow1", width, height);
		left1 = setup("/res/objects/arrow2", width, height);
		idle2 = setup("/res/objects/arrow3", width, height);
		right1 = setup("/res/objects/arrow4", width, height);
		image = idle1;
	}
	
	public void display(Graphics2D g2, int screenX, int screenY) {
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		switch(direction) {
		case "idle":
			if(spriteNum == 1) {image = idle1;} 
			else if(spriteNum == 2) {image = left1;}
			else if(spriteNum == 3) {image = idle2;}
			else if(spriteNum == 4) {image = right1;}
			break;
		}
		g2.drawImage(image, tempScreenX, tempScreenY, null);
	}
}
