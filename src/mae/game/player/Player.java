package mae.game.player;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;

import javax.imageio.ImageIO;

import mae.game.Entity;
import mae.game.GamePanel;
import mae.game.KeyHandler;
import mae.game.items.Item;
import mae.game.tile.TileManager;

public class Player extends Entity {
	GamePanel gp;
	KeyHandler keyH;
	public final int screenX;
	public final int screenY;
	public Boolean approval;
	public Object currentLight;

	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp);
		this.gp = gp;
		this.keyH = keyH;
		screenX = gp.screenWidth / 2;
		screenY = gp.screenHeight;
		solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		//solidArea.width = gp.tileSize;
		solidArea.height = 40;
		setDefaultValues();
		getImage();
		getPlayerImage(0, 0);
	}

	public void setDefaultValues() {
		worldX = gp.tileSize * 3;
		worldY = gp.tileSize * 3;
		speed = 5;
		//direction = "down";
	}

	public void setDefaultPositions() {
		gp.currentMap = gp.maps[0];
		gp.tileM = new TileManager(gp);
		worldX = gp.tileSize * 12;
		worldY = gp.tileSize * 8;
		direction = "down";
	}

	public BufferedImage getSpriteSheet() {
		BufferedImage sprite = null;
		try {
			sprite = ImageIO.read(getClass().getResourceAsStream("/res/player/player.png"));
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

	public void update() {
		int objIndex;
		if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.enterPressed || keyH.numberPressed) {
			if (keyH.leftPressed) {
				direction = "left";
			} else if (keyH.rightPressed) {
				direction = "right";
			} 
			
			collisionOn = false;
			takeDamage = false;
			gp.cChecker.checkTile(this);
			objIndex = gp.cChecker.checkObject(this, true);
			canInteract(objIndex);
			
			gp.eHandler.checkEvent();
			if (collisionOn == false && keyH.enterPressed == false) {
				switch (direction) {
					case "left":
						worldX -= speed;
						break;
					case "right":
						worldX += speed;
				}
			}
			spriteCounter++;
			if (spriteCounter > 10) {
				if(spriteNum == 1) {
					spriteNum = 2;
				} else if (spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		} else {
			direction = "idle";
			objIndex = gp.cChecker.checkObject(this, true);
			//gp.objectExists(objIndex, gp.currentMap.getId());
			canInteract(objIndex);
		}

		if (keyH.upPressed || keyH.downPressed || keyH.enterPressed || keyH.numberPressed) {
			objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
		}
	}
	
	public void pickUpObject(int i) {
		
	}

	public void pickUpObject(Item item) {
		if(canObtainItem(item)) {gp.playSE(21);}
	}

	public void pickUpObject(Item item, int qnt) {
		for(int i = 1; i <= qnt; i++) {
			if(canObtainItem(item)) {gp.playSE(21);}
		}
	}

	public void canInteract(int i) {
		for(int j = 0; j < gp.obj[gp.currentMap.getId()].length; j++) {
			if(gp.obj[gp.currentMap.getId()][j] != null) {
				gp.obj[gp.currentMap.getId()][j].isInteractingWith(false);
			}
		}
		if (i != 999) {
			gp.obj[gp.currentMap.getId()][i].isInteractingWith(true);
			if (keyH.upPressed || keyH.downPressed || keyH.enterPressed || keyH.numberPressed) {
				gp.obj[gp.currentMap.getId()][i].interact();
			}
		}
	}
	
	private boolean canObtainItem(Item item) {
		// TODO Auto-generated method stub
		return false;
	}

	public void setItems() {
		//inventory.add(new Itm_Hazelnut(gp));
		//inventory.add(new Itm_Bandage(gp));
	}

	public void getImage() {
		idle1 = getPlayerImage(0, 1);
		idle2 = getPlayerImage(0, 0);
		left1 = getPlayerImage(1, 1);
		left2 = getPlayerImage(2, 1);
		right1 = getPlayerImage(1, 0);
		right2 = getPlayerImage(2, 0);
	}

	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		image = getPlayerImage(0, 0);
		int x = screenX;
		int y = screenY;
		if (screenX > worldX) {
			x = worldX;
		}

		if (screenY > worldY) {
			y = worldY;
		}
		switch(direction) {
		case "idle":
			if(facingLeft) {image = idle1;}
			else {image = idle2;}
			break;
		case "left":
			if(spriteNum == 1) {image = left1;} 
			else if(spriteNum == 2) {image = left2;}
			facingLeft = true;
			break;
		case "right":
			if(spriteNum == 1) {image = right1;} 
			else if(spriteNum == 2) {image = right2;}
			facingLeft = false;
			break;
		}

		int rightOffset = gp.screenWidth - screenX;
		if (rightOffset > gp.currentMap.getWorldWidth() - gp.player.worldX) {
			x = gp.screenWidth - (gp.currentMap.getWorldWidth() - worldX);
		}

		int bottomOffset = gp.screenHeight - screenY;
		if (bottomOffset > gp.currentMap.getWorldHeight() - gp.player.worldY) {
			y = gp.screenHeight - (gp.currentMap.getWorldHeight() - worldY);
		}
		if(drawing) {
			g2.drawImage(image, x, y, (ImageObserver) null);
		}
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}
}