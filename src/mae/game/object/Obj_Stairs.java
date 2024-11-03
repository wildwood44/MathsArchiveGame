package mae.game.object;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import mae.game.EntityType;
import mae.game.GamePanel;

public class Obj_Stairs extends Object {
	GamePanel gp;
	public final static int objId = 3;
	public boolean open = false;

	public Obj_Stairs(GamePanel gp) {
		super(gp);
		this.gp = gp;
		id = 156;
		this.enId = objId;
		type = EntityType.Object;
		getImage();
		solidArea = new Rectangle(0, 0, gp.tileSize*2, gp.tileSize*2);
		size = gp.tileSize * 2;
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
		g2.setComposite(AlphaComposite.SrcOver.derive(alpha));
		g2.drawImage(image, tempScreenX, tempScreenY, null);
        g2.setComposite(AlphaComposite.SrcOver.derive(1f));
	}
	
	public void getImage() {
		if(floor == 0) {
			idle1 = setup("/res/objects/lab_stairs2", gp.tileSize*2, gp.tileSize*2);
			idle2 = setup("/res/objects/lab_stairs1", gp.tileSize*2, gp.tileSize*2);
		} else if (floor == 11) {
			idle1 = setup("/res/objects/lab_stairs5", gp.tileSize*2, gp.tileSize*2);
		} else {
			idle1 = setup("/res/objects/lab_stairs4", gp.tileSize*2, gp.tileSize*2);
			idle2 = setup("/res/objects/lab_stairs3", gp.tileSize*2, gp.tileSize*2);
		}
	}
	public void setFloor(int floor) {
		this.floor = floor;
		if(floor == 0) {
			if(opened) {
				image = idle1;
				description="Up";
			} else {
				image = idle2;
			}
		} else if (floor == 11) {
			if (opened) {
				image = idle1;
				description="Down";
			} else {
				image = idle2;
			}
		} else {
			image = idle1;
			description="Up/Down";
		}
		getImage();
	}

	public void interact() {
		if(gp.keyH.upPressed && floor != 11) {
			if(opened || gp.testDoor) {
				int changeMap = gp.currentMap.getId()+1;
				gp.eHandler.teleport(gp.maps[changeMap], 14, 3);
			} else {
				gp.playSE(3);
			}
		} else if(gp.keyH.downPressed && floor != 0) {
			int changeMap = gp.currentMap.getId()-1;
			gp.eHandler.teleport(gp.maps[changeMap], 14, 3);
		}
		gp.keyH.enterPressed = false;
		gp.keyH.upPressed = false;
		gp.keyH.downPressed = false;
	}
}
