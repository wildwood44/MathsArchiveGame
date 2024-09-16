package mae.game.object;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import mae.game.Entity;
import mae.game.GamePanel;

public class Object extends Entity {
	private final Color correct =
			Color.green;
	private final Color wrong = Color.red;
	protected boolean isCorrect = false;
	protected boolean isWrong = false;
	protected int input;
	public Object(GamePanel gp) {
		super(gp);
		key = -1;
	}
	
	public void update() {
		if(isCorrect || isWrong) {
			spriteCounter++;
			int i = 5;
			if (spriteCounter > 60) {
				isCorrect = false;
				isWrong = false;
				spriteCounter=0;
			}
		}
	}
	
	public String submitAns() {
	    return description.replace("?", Integer.toString(gp.kc[gp.currentCard].useCard()));
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
		String text = description;
		if(isCorrect) {
			g2.setColor(correct);
			text = submitAns();
		} else if(isWrong) {
			g2.setColor(wrong);
			text = submitAns();
		} else {
			g2.setColor(Color.white);
		}
		//g2.drawString(text, textX, y+gp.tileSize);
		y=y+gp.tileSize;
		try {
			for(String line : text.split("£")) {
				int length, textX;
				if(text.length() > 6) {
					for(String line2 : line.split("((?==))")){
						length = (int) g2.getFontMetrics().getStringBounds(line2, g2).getWidth();
						textX = x + (int)(gp.tileSize*1.5) - length/2;
						g2.drawString(line2, textX, y);
						y+=gp.tileSize/3;
					}
				} else {
					length = (int) g2.getFontMetrics().getStringBounds(line, g2).getWidth();
					textX = x + (int)(gp.tileSize*1.5) - length/2;
					g2.drawString(line, textX, y);
					y+=gp.tileSize/4;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
