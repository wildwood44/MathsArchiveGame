package mae.game.npc;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import mae.game.GamePanel;

public class Prisoner_85 extends NPC {
	GamePanel gp;
	Graphics2D g2;
	public final static int npcId = 85;
	public BufferedImage flyingLeft1, flyingLeft2, flyingRight1, flyingRight2;

	public Prisoner_85(GamePanel gp) {
		super(gp);
		this.gp = gp;
		id = npcId;
		image = idle1;
		width = gp.tileSize;
		height = gp.tileSize;
		speed=1;
		moving=true;
		getImage();
	}

	public BufferedImage getSpriteSheet() {
		BufferedImage sprite = null;
		try {
			sprite = ImageIO.read(getClass().getResourceAsStream("/res/npc/spr_firecracker1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sprite;
	}

	public BufferedImage getPlayerImage(int xGrid, int yGrid) {
		if (image == null) {
			image = getSpriteSheet();
		}
		return image.getSubimage(xGrid * gp.tileSize, yGrid * gp.tileSize, gp.tileSize, gp.tileSize);
	}
	
	public void getImage() {
		idle1 = getPlayerImage(0, 0);
		idle2 = getPlayerImage(2, 0);
		idle3 = getPlayerImage(3, 0);
		idle4 = getPlayerImage(5, 0);
		right1 = getPlayerImage(0, 0);
		right2 = getPlayerImage(1, 0);
		left1 = getPlayerImage(3, 0);
		left2 = getPlayerImage(4, 0);
		flyingRight1 = getPlayerImage(0, 1);
		flyingRight2 = getPlayerImage(1, 1);
		flyingLeft1 = getPlayerImage(3, 1);
		flyingLeft2 = getPlayerImage(4, 1);
		specialRight1 = getPlayerImage(0, 2);
		specialRight2 = getPlayerImage(1, 2);
		specialLeft1 = getPlayerImage(3, 2);
		specialLeft2 = getPlayerImage(4, 2);
	}

	public void setAction() {
		actionLockCounter++;
		if(actionLockCounter==120) {
			Random random = new Random();
			int i = random.nextInt(100)+1;
			if(worldY+5>gp.tileSize*3) {
				npcState = NPCState.NormalState;
				speed= 1;
			} else {
				npcState = NPCState.FlyingState;
				speed = 1;
			}
			if(i <= 25) {
				direction = "up";
			} if(i > 25 && i <= 50) {
				direction = "down";
			} if(i > 50 && i <= 75) {
				facingLeft = true;
				direction = "left";
				if(i > 70) {
					npcState = NPCState.SpecialState;
					speed = 5;
				}
			} if(i > 75 && i <= 100) {
				facingLeft = false;
				direction = "right";
				if(i > 95) {
					npcState = NPCState.SpecialState;
					speed = 5;
				}
			}
			moving=true;
			actionLockCounter=0;
		}
		//getImage();
	}
	
	public void display(Graphics2D g2, int screenX, int screenY) {
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		switch(direction) {
		case "idle":
			switch(npcState) {
			case FlyingState:
				if(facingLeft) {
					if(spriteNum == 1) {image = flyingLeft1;}
					else if(spriteNum == 2) {image = flyingLeft2;}
					break;
				}
				else {
					if(spriteNum == 1) {image = flyingRight1;}
					else if(spriteNum == 2) {image = flyingRight2;}
					break;
				}
			default:
				if(facingLeft) {
					if(spriteNum == 1) {image = idle1;}
					else if(spriteNum == 2) {image = left2;}
					break;
				}
				else {
					if(spriteNum == 1) {image = idle3;}
					else if(spriteNum == 2) {image = idle4;}
					break;
				}
			}
			break;
		case "up":
		case "down":
			if(facingLeft) {
				if(spriteNum == 1) {image = flyingLeft1;}
				else if(spriteNum == 2) {image = flyingLeft2;}
				break;
			}
			else {
				if(spriteNum == 1) {image = flyingRight1;}
				else if(spriteNum == 2) {image = flyingRight2;}
				break;
			}
		case "left":
			switch(npcState) {
			case NormalState:
				if(spriteNum == 1) {image = left1;} 
				else if(spriteNum == 2) {image = left2;}
				break;
			case FlyingState:
				if(spriteNum == 1) {image = flyingLeft1;}
				else if(spriteNum == 2) {image = flyingLeft2;}
				break;
			case SpecialState:
				if(spriteNum == 1) {image = specialLeft1;}
				else if(spriteNum == 2) {image = specialLeft2;}
				break;
			}
			break;
		case "right":
			switch(npcState) {
			case NormalState:
				if(spriteNum == 1) {image = right1;} 
				else if(spriteNum == 2) {image = right2;}
				break;
			case FlyingState:
				if(spriteNum == 1) {image = flyingRight1;}
				else if(spriteNum == 2) {image = flyingRight2;}
				break;
			case SpecialState:
				if(spriteNum == 1) {image = specialRight1;}
				else if(spriteNum == 2) {image = specialRight2;}
				break;
			}
			break;
		}
		g2.setComposite(AlphaComposite.SrcOver.derive(alpha));
		g2.drawImage(image, tempScreenX, tempScreenY, null);
        g2.setComposite(AlphaComposite.SrcOver.derive(1f));
	}

}
