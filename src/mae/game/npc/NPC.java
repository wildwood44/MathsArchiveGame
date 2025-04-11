package mae.game.npc;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import mae.game.Entity;
import mae.game.GamePanel;

public class NPC extends Entity {
	NPCState npcState = NPCState.NormalState;
	
	public NPC(GamePanel gp) {
		super(gp);
		collision = false;
		// TODO Auto-generated constructor stub
	}
	
	public BufferedImage getSpecialSpriteSheet() {
		BufferedImage sprite = null;

		try {
			sprite = ImageIO.read(getClass().getResourceAsStream("/res/sprite/WildWyrdSpecialSprites.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sprite;
	}

	public BufferedImage getSpecialImage(int xGrid, int yGrid) {
		if (image != getSpecialSpriteSheet()) {
			image = getSpecialSpriteSheet();
		}
		return image.getSubimage(xGrid * gp.tileSize, yGrid * gp.tileSize, gp.tileSize, gp.tileSize);
	}

	public BufferedImage getSpriteSheet() {
		BufferedImage sprite = null;
		try {
			sprite = ImageIO.read(getClass().getResourceAsStream("/res/sprite/WildWyrdSprites.png"));
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
	
	public void getImage() {}
	
	public void update() {
		setAction();
		collisionOn = false;
		gp.cChecker.checkTile(this);
		if(!collisionOn) {
			switch(direction) {
			case "up": worldY -= speed; break;
			case "down": worldY += speed; break;
			case "left": worldX -= speed; break;
			case "right": worldX += speed; break;
			}
		}
		spriteCounter++;
		if (spriteCounter > 4) {
			if(spriteNum == 1) {
				spriteNum = 2;
			} else if (spriteNum == 2) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
	}
}
