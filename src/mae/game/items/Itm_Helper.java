package mae.game.items;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import mae.game.GamePanel;
import mae.game.GameState;

public class Itm_Helper extends Item {
	GamePanel gp;
	private int id = 1;

	public Itm_Helper(GamePanel gp) {
		super(gp);
		this.gp = gp;
		this.enId = id;
		getImage();
		setDialogue();
		image = idle1;
		x = gp.screenWidth/3;
		y = (4 * gp.tileSize) + 5;
	}
	
	public void getImage() {
		idle1 = setup(("/res/objects/helper1"), gp.tileSize*2, gp.tileSize*2);
		idle2 = setup(("/res/objects/helper2"), gp.tileSize*2, gp.tileSize*2);
	}

	public void draw(Graphics2D g2) {
		if(spriteNum==1) {
			image = idle1;
		}if(spriteNum==2) {
			image = idle2;
		}
		g2.drawImage(image, x, y, null);
        g2.setComposite(AlphaComposite.SrcOver.derive(1f));
    	//DRAW SPEECH BUBBLE
		if(gp.gameState == GameState.talkingState) {
			//update();
			drawSpeech(g2, x - gp.tileSize/2, y - gp.tileSize);
		}
	}

	public void update() {
		collisionOn = false;
		if(isSelected) {
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
	public void drawSpeechBubble(Graphics2D g2, int x, int y) {
		BufferedImage sb_tl = setup("/res/speechbubble/speech-bubble_long", gp.tileSize*4, gp.tileSize*2);
		g2.drawImage(sb_tl, x-gp.tileSize/2, y, null);
	}
	public void drawSpeech(Graphics2D g2, int x, int y) {			
		int textY = y + 48;
		int length, textX;
		//Draw box
		drawSpeechBubble(g2,x,y);
		g2.setColor(Color.WHITE);
		g2.setFont(g2.getFont().deriveFont(1, 12.0F));
		for (String line : gp.ui.breakLines(dialogue[dialogueSet][dialogueIndex],20," ")) {
			length = (int) g2.getFontMetrics().getStringBounds(line, g2).getWidth();
			textX = x + (int)(gp.tileSize*1.5) - length/2;
			g2.drawString(line, textX, textY);
			textY += 20;
		}
	}
	public void setDialogue() {
		dialogue[0][0] = "Addition is when you bring too amounts together.";
		dialogue[0][1] = "For example: If you have one me and another me you get two me's.";
		dialogue[1][0] = "A quick way of adding mutliple of the same value is by multiplcation.";
		dialogue[1][1] = "For example  4+4+4+4=16 can be simplified to 4*4=16.";
		dialogue[1][2] = "The reverse of this is spliting a number by into smaller numbers through division.";
		dialogue[1][3] = "For example 12 can be split into 3 4s or 4 3s.";
		dialogue[2][0] = "Did you know there are numbers between whole numbers.";
		dialogue[2][1] = "I am talking about decimals.";
		dialogue[2][2] = "For instance in between 4 and 5 you can have 4.5";
		dialogue[2][3] = "Which a simple way of saying four and a half.";
		dialogue[2][4] = "Additionally between 4 and 4.5 you can have 4.25 and so on.";
		dialogue[2][5] = "You can also have numbers less than 0 called negatives.";
		dialogue[2][6] = "They are in reverse order from 0, -1, -2, etc.";
		dialogue[3][0] = "The % symbol stands for percentage.";
		dialogue[3][1] = "To find the percentage of a number you must...";
		dialogue[3][2] = "Divide the whole number by 10 to get 10% or by 100 for 100%.";
		dialogue[3][3] = "You can then times the divide number to find the total percentage.";
		dialogue[3][4] = "Example: 40% of 120 = 120/10=12*4 = 48.";
		dialogue[3][5] = "A quicker way if by split the whole number into 50% or 25%.";
		dialogue[3][6] = "You can get those by simply halving add adding from there.";
		dialogue[3][7] = "Example: 65% of 120 = 120/10=12*4=48 and 120/4=30 = 78.";
		dialogue[3][0] = "To calculate fraction you need to ";
	}
	
	public void use() {
		System.out.println(gp.currentMap.getId() +" "+ gp.currentMap.getFloor());
		if(dialogue[gp.currentMap.getFloor()][dialogueIndex]!=null) {
			startDialogue(this, gp.currentMap.getFloor());
		}
		
	}
}
