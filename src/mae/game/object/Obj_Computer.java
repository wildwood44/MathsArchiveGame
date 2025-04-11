package mae.game.object;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import mae.game.EntityType;
import mae.game.GamePanel;
import mae.game.GameState;

public class Obj_Computer extends Object {
	GamePanel gp;
	public final static int objId = 8;
	public boolean open = false;
	public int input = 0;
	public boolean correct = true;

	public Obj_Computer(GamePanel gp) {
		super(gp);
		this.gp = gp;
		this.enId = objId;
		id = -1;
		type = EntityType.Object;
		getImage();
		solidArea = new Rectangle(0, 0, gp.tileSize*3, gp.tileSize*3);
		size = gp.tileSize*3;
		setDialogue();
	}

	public void interact() {
		moving = true;
		startDialogue(this, gp.currentCard);
		gp.keyH.enterPressed = false;
		gp.keyH.upPressed = false;
		gp.keyH.downPressed = false;
	}

	public void update() {
		collisionOn = false;
		//super.update();
		if(gp.gameState == GameState.talkingState) {
			spriteCounter++;
			if (spriteCounter > 10) {
				if(spriteNum == 1) {
					spriteNum = 2;
				} else if (spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter=0;
			}
		}
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
		idle1 = setup("/res/objects/Computer", gp.tileSize*3, gp.tileSize*3);
		left1 = setup("/res/objects/Computer_Smile", gp.tileSize*2, gp.tileSize*2);
		left2 = setup("/res/objects/Computer_O", gp.tileSize*2, gp.tileSize*2);
	}
	
	public void display(Graphics2D g2, int screenX, int screenY) {
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		g2.drawImage(idle1, tempScreenX, tempScreenY, null);
		int position = 0;
		if(gp.player.worldX < worldX) {
			position=tempScreenX;
		} else if (gp.player.worldX > worldX+gp.tileSize*3) {
			position=tempScreenX+gp.tileSize;
		} else {
			position=tempScreenX+gp.tileSize/2;
		}
		BufferedImage eyes = setup("/res/objects/Computer_Eyes", gp.tileSize*2, gp.tileSize*2);
		g2.drawImage(eyes, position, tempScreenY+gp.tileSize/4, null);
		BufferedImage mouth = left1;
		if(spriteNum==1) {
			mouth = left1;
		}if(spriteNum==2) {
			mouth = left2;
		}
		g2.drawImage(mouth, position, tempScreenY+gp.tileSize/6, null);
		if(gp.gameState == GameState.talkingState) {
			drawSpeech(g2,position-gp.tileSize/2,tempScreenY-gp.tileSize);
		}
	}
	public void drawSpeechBubble(Graphics2D g2, int x, int y) {
		BufferedImage sb_tl = setup("/res/speechbubble/speech-bubble_long", gp.tileSize*4, gp.tileSize*2);
		g2.drawImage(sb_tl, x-gp.tileSize/2, y, null);
	}
	public void drawSpeech(Graphics2D g2, int x, int y) {			
		int textY = y + 48;
		int length, textX;
		//Draw box
		drawSpeechBubble(g2,x,y);
		String text = dialogue[dialogueSet][dialogueIndex];
		g2.setColor(Color.WHITE);
		g2.setFont(g2.getFont().deriveFont(1, 12.0F));
		for (String line : gp.ui.breakLines(text,20," ")) {
			length = (int) g2.getFontMetrics().getStringBounds(line, g2).getWidth();
			textX = x + (int)(gp.tileSize*1.5) - length/2;
			g2.drawString(line, textX, textY);
			textY += 20;
		}
	}
	public void setDialogue() {
		dialogue[0][0] = "Hello I am INFORMATION TERMINAL please input query: ";
		dialogue[0][1] = "1: Collection";
		dialogue[0][2] = "2: Math puzzles";
		dialogue[0][3] = "3: Owners";
		dialogue[0][4] = "4: Key cards";
		dialogue[0][5] = "5: Skeleton Key Card";
		dialogue[0][6] = "6: Floors";
		dialogue[0][7] = "7: Help";
		dialogue[0][8] = "8: …";
		dialogue[0][9] = "9: Exit";
		dialogue[1][0] = "These archives contain plants and alien species";
		dialogue[1][1] = "from across the universe.";
		dialogue[1][2] = "All part of the master's collection.";
		dialogue[2][0] = "One of the masters loves to create math problems,";
		dialogue[2][1] = "so they are used for all the locks.";
		dialogue[3][0] = "If you need help take the device on the pedestal.";
		dialogue[3][1] = "It can give you hints on how to solve the puzzles.";
		dialogue[4][0] = "Get the key cards.";
		dialogue[4][1] = "There should be nine.";
		dialogue[4][2] = "They are used to open doors maching the";
		dialogue[4][3] = "corresponding answer to a puzzle.";
		dialogue[5][0] = "You have the skeleton key card!";
		dialogue[5][1] = "If you find a converter you can use it";
		dialogue[5][2] = "and other key cards to change this card's value.";
		dialogue[6][0] = "Each floor has a maths theme: ";
		dialogue[6][1] = "1: Addition and Subtraction";
		dialogue[6][2] = "2: Multiplication and Division";
		dialogue[6][3] = "3: Decimals and Negatives";
		dialogue[6][4] = "4: Percentages";
		dialogue[6][5] = "5: Fractions";
		dialogue[6][6] = "6: Graphs and Transformations";
		dialogue[6][7] = "7: Conversion";
		dialogue[6][8] = "8: Geometry and Trigonometry";
		dialogue[6][9] = "9: Statistics";
		dialogue[6][10] = "10: Probability";
		dialogue[6][11] = "11: Algebra";
		dialogue[6][12] = "And 12: Radicals";
		dialogue[7][0] = "There are 144 staff including the two masters,";
		dialogue[7][1] = "each is assigned an ID.";
		dialogue[7][2] = "Each staff member will go to each room";
		dialogue[7][3] = "and change its open/closed status.";
		dialogue[7][4] = "So Staff ID:1 will open every room.";
		dialogue[7][5] = "Staff ID:2 will close every second room.";
		dialogue[7][6] = "Staff ID:3 will change the status of every third room.";
		dialogue[7][7] = "Etc.";
		dialogue[7][8] = "The rooms left open are low-security rooms";
		dialogue[7][9] = "that don’t contain any living collection pieces";
		dialogue[7][10] = "but will contain something useful.";
		dialogue[8][0] = "[ERROR DATA NOT AVAILABLE]";
		dialogue[9][0] = "There are 144 staff including the two masters,";
		dialogue[9][1] = "each is assigned an ID.";
		dialogue[9][2] = "Each staff member will go to each room";
		dialogue[9][3] = "and change its open/closed status.";
		dialogue[9][4] = "So Staff ID:1 will open every room.";
		dialogue[9][5] = "Staff ID:2 will close every second room.";
		dialogue[9][6] = "Staff ID:3 will change the status of every third room.";
		dialogue[9][7] = "Etc.";
		dialogue[9][8] = "The exit is in one of the rooms visited twice by staff";
	}
}
