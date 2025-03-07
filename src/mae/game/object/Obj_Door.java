package mae.game.object;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import mae.game.EntityType;
import mae.game.GamePanel;
import mae.game.tile.MapType;

public class Obj_Door extends Object {
	GamePanel gp;
	public final static int objId = 1;
	public boolean open = false;
	public int input = 0;
	public boolean correct = true;

	public Obj_Door(GamePanel gp) {
		super(gp);
		this.gp = gp;
		this.enId = objId;
		type = EntityType.Object;
		getImage();
		if(!opened) {
			image = idle1;
		} else {
			image = left2;
		}
		solidArea = new Rectangle(0, 0, gp.tileSize*2, gp.tileSize*2);
		size = gp.tileSize * 2;
	}

	public void interact() {
		if(!opened) {
			ans[input] = gp.kc[gp.currentCard].useCard();
			//If more than one input
			if(puzzle.getInputCount() -1 > input) {
				if(puzzle.isCorrect(gp.kc[gp.currentCard].useCard())) {
					correct = true;
				} else {
					correct = false;
				}
				input++;
			//If all input are correct
			} else if(puzzle.isCorrect(gp.kc[gp.currentCard].useCard()) && !locked && correct || gp.testDoor) {
				isCorrect = true;
				gp.playSE(4);
				moving = true;
				input = 0;
			//If input(s) are incorrect
			} else {
				correct = true;
				input=0;
				isWrong = true;
				gp.playSE(3);
			}
		} 
		if (opened){
			if(gp.currentMap.getMapType() == MapType.ROOM){
				int location = floor + (id - 11);
				for(int j = 0; j < gp.obj[floor].length - 1; j++) {
					if(gp.obj[floor][j] != null && gp.obj[floor][j].id == id-11){
						location = gp.obj[floor][j].worldX / gp.tileSize;
						break;
					}
				}
				gp.eHandler.teleport(gp.maps[floor], location, 3);
			} else {
				gp.eHandler.teleport(gp.maps[id + 11], 2, 3);
			}
		}
		gp.keyH.enterPressed = false;
		gp.keyH.upPressed = false;
		gp.keyH.downPressed = false;
		gp.keyH.numberPressed = false;
	}
	
	public boolean animation() {
		return false;
	}

	public void update() {
		collisionOn = false;
		super.update();
		if(moving) {
			spriteCounter++;
			if (spriteCounter > 30) {
				if(spriteNum == 1) {
					spriteNum = 2;
				} else if (spriteNum == 2) {
					spriteNum = 3;
				} else if (spriteNum == 3) {
					spriteNum = 4;
					moving = false;
					opened = true;
				}
			}
		}
	}
	
	public void getImage() {
		idle1 = setup("/res/objects/lab_door1", gp.tileSize*2, gp.tileSize*2);
		idle2 = setup("/res/objects/lab_door2", gp.tileSize*2, gp.tileSize*2);
		left1 = setup("/res/objects/lab_door3", gp.tileSize*2, gp.tileSize*2);
		left2 = setup("/res/objects/lab_door4", gp.tileSize*2, gp.tileSize*2);
		if(!opened) {
			image = idle1;
		} else {
			spriteNum = 4;
			image = left2;
		}
	}

	
	public void setLock(boolean locked) {
		if(locked) {
			description = "LOCKED";
			this.locked = true;
		} else {
			description = puzzle.printPuzzle();
			this.locked = false;
		}
	}
	
	public void display(Graphics2D g2, int screenX, int screenY) {
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		switch(direction) {
		case "idle":
			if(spriteNum == 1) {image = idle1;} 
			else if(spriteNum == 2) {image = idle2;}
			else if(spriteNum == 3) {image = left1;}
			else if(spriteNum == 4) {image = left2;}
			break;
		}
		g2.drawImage(image, tempScreenX, tempScreenY, null);
		if(gp.currentMap.getMapType() == MapType.LEVEL) {
			//DRAW ROOM NUMBER	
			g2.setFont(g2.getFont().deriveFont(0, 10.0F));
			g2.setColor(Color.WHITE);
		    g2.drawString(id+"", tempScreenX+8, tempScreenY+gp.tileSize/2);
	    	//DRAW SPEECH BUBBLE
			if(isSelected && !opened) {
				drawSpeech(g2, tempScreenX - gp.tileSize, tempScreenY - gp.tileSize);
			}
		}
	    g2.setComposite(AlphaComposite.SrcOver.derive(1f));
	}
}
