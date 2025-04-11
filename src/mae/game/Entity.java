package mae.game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Timer;

import mae.game.object.SumType;
import mae.game.puzzle.Puzzle;
import mae.game.tile.UtilityTool;

public class Entity {
	public static final long RUNNING_TIME = 2000;
	public int speed;
	public BufferedImage image;
	public BufferedImage image2;
	public GamePanel gp;
	public int x;
	public int y;
	public int width;// = gp.tileSize;
	public int height;// = gp.tileSize;
	public float alpha = 1f;
	public int id, enId;
	public int floor;
	public String name;
	public int worldX;
	public int worldY;
	public BufferedImage[][] sprites = new BufferedImage[100][100];
	public int dialogueSet = 0;
	public int dialogueIndex = 0;
	public Rectangle solidArea = new Rectangle(0, 0, 64, 64);
	public int solidAreaDefaultX = 0;
	public int solidAreaDefaultY = 0;
	public String description = "X";
	public String[][] dialogue = new String[18][15];
	public String direction = "idle";
	public BufferedImage idle1, idle2, idle3, idle4, left1, left2, right1, right2, specialLeft1, specialLeft2, specialRight1, specialRight2;
	public int spriteCounter = 0;
	public int spriteNum = 1;
	public EntityType type;
	public int key;	
	public SumType sum;
	public Puzzle puzzle;
	//Status
	public boolean facingLeft = true;
	public boolean moving = false;
	public boolean collision = false;
	public boolean collisionOn = false;
	public boolean takeDamage = false;
	public boolean destroy = false;
	public boolean opened = false;
	public boolean locked = false;
	public boolean drawing = true;	
	public int actionLockCounter = 0;
	protected boolean isCorrect = false;
	protected boolean isWrong = false;
	protected final Color correct = Color.green;
	protected final Color wrong = Color.red;
	protected boolean isSelected = false;
	public Timer timer;
	public long startTime = -1;
	

	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setAction() {}

	public void update() {
		setAction();
		collisionOn = false;
		gp.cChecker.checkTile(this);
		if(moving) {
			if(!collisionOn) {
				switch(direction) {
				case "up": worldY -= speed; break;
				case "down": worldY += speed; break;
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;
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
			moving = false;
		} else {
			spriteNum = 1;
		}
	}
	
	public void getImage() {
		idle1 = image;
	}

	public void resizeImage(BufferedImage image, int width, int height) {
		//BufferedImage newImage = image.getScaledInstance(width, height, image.SCALE_DEFAULT);

	}

	public BufferedImage setup(String imagePath, int width, int height) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			image = uTool.scaleImage(image, width, height);
		} catch(IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	public String getName() {
		if (name == null) {
			return "None";
		}
		return name;
	}
	
	public void setLock(boolean locked) {
		if(!locked) {
			opened = true;
		} else {
			opened = false;
		}
	}
	
	public void setLabel(String c) {
		description = c;
	}
	
	public void setButton(int id, String value) {
		
	}
	
	public int getCenterX() {
		int centerX = worldX + left1.getWidth()/2; 
		return centerX;
	}
	
	public int getCenterY() {
		int centerY = worldY + left1.getWidth()/2; 
		return centerY;
	}
	
	public int getXDistance(Entity target) {
		int xDistance = Math.abs(worldX - target.worldX); 
		return xDistance;
	}
	
	public int getYDistance(Entity target) {
		int yDistance = Math.abs(worldY - target.worldX); 
		return yDistance;
	}

	public int getLeftX() {
		return worldX + solidArea.x;
	}

	public int getRightX() {
		return worldX + solidArea.x + solidArea.width;
	}

	public int getTopY() {
		return worldY + solidArea.y;
	}

	public int getBottomY() {
		return worldY + solidArea.y + solidArea.height;
	}

	public int getCol() {
		return (worldX + solidArea.x) / gp.tileSize;
	}

	public int getRow() {
		return (worldY + solidArea.y) / gp.tileSize;
	}
	
	public int GetCentreX() {
		int centreX = worldX + solidArea.width;
		return centreX;
	}
	
	public int GetCentreY() {
		int centreY = worldY + solidArea.height;
		return centreY;
	}
	public void setFloor(int floor) {
		this.floor = floor;
	}
	public void setPuzzle(Puzzle puzzle) {
		this.puzzle = puzzle;
		this.description = puzzle.printPuzzle();
	}
	
	public void lock(int key) {
		this.key = key;
	}
	public void setSumType(SumType sum) {
		this.sum = sum;
		//image = setup("/res/objects/skg_explained_"+sum.name(), width, height);
		getImage();
	}

	public void startDialogue(Entity object, int setNum) {
		GamePanel gp = this.gp;
		gp.gameState = GameState.talkingState;
		gp.ui.selectedObject = object;
		dialogueSet = setNum;
	}

	public void draw(Graphics2D g2) {
		//BufferedImage image = null;
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		if (gp.player.worldX < gp.player.screenX) {
			screenX = worldX;
		}
		// STOP MOVING CAMERA
		if (gp.player.worldY < gp.player.screenY) {
			screenY = worldY;
		}
		int rightOffset = gp.screenWidth - gp.player.screenX;
		if (rightOffset > gp.currentMap.getWorldWidth() - gp.player.worldX) {
			screenX = gp.screenWidth - (gp.currentMap.getWorldWidth() - worldX);
		}
		int bottomOffset = gp.screenHeight - gp.player.screenY;
		if (bottomOffset > gp.currentMap.getWorldHeight() - gp.player.worldY) {
			screenY = gp.screenHeight - (gp.currentMap.getWorldHeight() - worldY);
		}
		if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
			worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
			worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
			worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
			display(g2, screenX, screenY);
		}
		else if(gp.player.worldX < gp.player.screenX ||
			    gp.player.worldY < gp.player.screenY ||
			    rightOffset > gp.currentMap.getWorldWidth() - gp.player.worldX ||
			    bottomOffset > gp.currentMap.getWorldHeight() - gp.player.worldY) {
			display(g2, screenX, screenY);
		}
	}
	
	public void display(Graphics2D g2, int screenX, int screenY) {
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		switch(direction) {
		case "idle":
			if(facingLeft) {image = idle1;}
			else {image = idle2;}
			break;
		case "left":
			if(spriteNum == 1) {image = left1;} 
			else if(spriteNum == 2) {image = left2;} 
			break;
		case "right":
			if(spriteNum == 1) {image = right1;} 
			else if(spriteNum == 2) {image = right2;}
			break;
		}
		g2.setComposite(AlphaComposite.SrcOver.derive(alpha));
		g2.drawImage(image, tempScreenX, tempScreenY, null);
        g2.setComposite(AlphaComposite.SrcOver.derive(1f));
	}
	
	public void interact() {}
	
	public void speak() {
		facePlayer();
		//gp.ui.choiceSlot = 0;
		gp.ui.firstValue = 0;
		//startDialogue(this, 0);

		gp.keyH.enterPressed = false;
	}
	
	public void facePlayer() {
		switch(gp.player.direction) {
		case "up": direction = "down"; break;
		case "down": direction = "up"; break;
		case "left": direction = "right"; break;
		case "right": direction = "left"; break;
		}
	}

	public int getDetected(Entity user, Entity[][] target, String targetName) {
		int index = 999;
		int nextWorldX = user.getLeftX();
		int nextWorldY = user.getTopY();
		switch (user.direction) {
			case "bottom": nextWorldY = user.getBottomY() + 1; break;
			case "up": nextWorldY = user.getTopY() - 1; break;
			case "left": nextWorldX = user.getLeftX() - 1; break;
			case "right": nextWorldX = user.getRightX() + 1; break;
		}
		int col = nextWorldX / gp.tileSize;
		int row = nextWorldY / gp.tileSize;

		for (int i = 0; i < target[gp.currentMap.getId()].length; ++i) {
			if (target[gp.currentMap.getId()][i] != null && target[gp.currentMap.getId()][i].getCol() == col && target[gp.currentMap.getId()][i].getRow() == row
					&& target[gp.currentMap.getId()][i].name.equals(targetName)) {
				index = i;
				break;
			}
		}
		return index;
	}
	
	public boolean isInteractingWith(boolean isSelected) {
		this.isSelected = isSelected;
		return isSelected;
	}
	
	public void drawSpeechBubble(Graphics2D g2, int x, int y) {
		BufferedImage sb_tl = setup("/res/speechbubble/speech_bubble-top_left", gp.tileSize, gp.tileSize);
		g2.drawImage(sb_tl, x, y, null);
		BufferedImage sb_t = setup("/res/speechbubble/speech_bubble-top", gp.tileSize, gp.tileSize);
		g2.drawImage(sb_t, x + gp.tileSize, y, null);
		BufferedImage sb_tr = setup("/res/speechbubble/speech_bubble-top_right", gp.tileSize, gp.tileSize);
		g2.drawImage(sb_tr, x + gp.tileSize * 2, y, null);
		BufferedImage sb_bl = setup("/res/speechbubble/speech_bubble-bottom_left", gp.tileSize, gp.tileSize);
		g2.drawImage(sb_bl, x, y + gp.tileSize, null);
		BufferedImage sb_b = setup("/res/speechbubble/speech_bubble-bottom", gp.tileSize, gp.tileSize);
		g2.drawImage(sb_b, x + gp.tileSize, y + gp.tileSize, null);
		BufferedImage sb_br = setup("/res/speechbubble/speech_bubble-bottom_right", gp.tileSize, gp.tileSize);
		g2.drawImage(sb_br, x + gp.tileSize*2, y + gp.tileSize, null);
		g2.setFont(g2.getFont().deriveFont(1, 24.0F));
		g2.setColor(Color.white);
		//int length = (int) g2.getFontMetrics().getStringBounds(description, g2).getWidth();
		//x = width / 2 - length / 2;
		g2.drawString(description, x+gp.tileSize, y+gp.tileSize);
	}
	public void drawSpeech(Graphics2D g2, int x, int y) {
		//Draw box
		drawSpeechBubble(g2,x,y);
	}
	
	public void drawPuzzle(Graphics2D g2) {
		
	}
}