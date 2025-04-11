package mae.game.object;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import mae.game.EntityType;
import mae.game.GamePanel;
import mae.game.puzzle.Puzzle;
import mae.game.tile.MapType;

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
		g2.drawImage(image, tempScreenX, tempScreenY, null);
		if(isSelected && gp.currentMap.getMapType() == MapType.LEVEL && opened) {
			drawSpeech(g2, tempScreenX - gp.tileSize, tempScreenY - gp.tileSize);
		}
	}
	public void setPuzzle(Puzzle puzzle) {
		if(floor == 0) {
			description="Up";
		} else if (floor == 11) {
			description="Down";
		} else {
			description="Up/Down";
		}
	}
	
	public void getImage() {
		if(floor == 0) {
			idle1 = setup("/res/objects/lab_stairs2", gp.tileSize*2, gp.tileSize*2);
			idle2 = setup("/res/objects/lab_stairs1", gp.tileSize*2, gp.tileSize*2);
			description="Up";
		} else if (floor == 11) {
			idle1 = setup("/res/objects/lab_stairs5", gp.tileSize*2, gp.tileSize*2);
			description="Down";
		} else {
			idle1 = setup("/res/objects/lab_stairs4", gp.tileSize*2, gp.tileSize*2);
			idle2 = setup("/res/objects/lab_stairs3", gp.tileSize*2, gp.tileSize*2);
			description="Up/Down";
		}
	}
	public void setFloor(int floor) {
		this.floor = floor;
		if(floor == 0) {
			if(opened) {
				image = idle1;
			} else {
				image = idle2;
			}
			description="Up";
		} else if (floor == 11) {
			if (opened) {
				image = idle1;
			} else {
				image = idle2;
			}
			description="Down";
		} else {
			image = idle1;
			description="Up/Down";
		}
		getImage();
	}
	
	public void drawSpeech(Graphics2D g2, int x, int y) {
		String text = description;
		drawSpeechBubble(g2,x,y);
		int length, textX;
		try {
			g2.setColor(Color.white);
			length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
			textX = x + (int)(gp.tileSize*1.5) - length/2;
			g2.drawString(text, textX, y+gp.tileSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void drawSpeechBubble(Graphics2D g2, int x, int y) {
		BufferedImage image = setup("/res/speechbubble/speech-bubble_small", gp.tileSize*3, gp.tileSize*2);
		g2.drawImage(image, x, y, null);
		g2.setFont(g2.getFont().deriveFont(1, 24.0F));	
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
