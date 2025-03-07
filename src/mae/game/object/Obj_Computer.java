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
		startDialogue(this, 0);
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
		dialogue[0][1] = "1:Collection";
		dialogue[0][2] = "2: Math puzzles";
		dialogue[0][3] = "3: Exit";
		dialogue[0][4] = "4:Collection";
		dialogue[0][5] = "5: Math puzzles";
		dialogue[0][6] = "6: Exit";
		dialogue[0][7] = "7:Collection";
		dialogue[0][8] = "8: Math puzzles";
		dialogue[0][9] = "9: Exit";
	}
}
