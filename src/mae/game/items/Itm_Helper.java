package mae.game.items;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import mae.game.GamePanel;

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
		if(isSelected) {
			update();
			drawSpeech(g2, x - gp.tileSize/2, y - gp.tileSize);
			if(gp.keyH.enterPressed) {
				isSelected = false;
			}
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
		String text = dialogue[0][0];
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
		dialogue[0][0] = "Addition is when you bring too amounts together.";
		dialogue[0][1] = "For example: If you have one me and another me you get two me's.";
		dialogue[1][0] = "1: Collection";
		dialogue[2][0] = "2: Collection";
	}
	
	public void use() {
		isSelected = true;
	}
}
