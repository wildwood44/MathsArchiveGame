package mae.game.object;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import mae.game.Entity;
import mae.game.GamePanel;
import mae.game.puzzle.Puzzle;
import mae.game.puzzle.PuzzleType;

public class Object extends Entity {
	private final Color correct = Color.green;
	private final Color wrong = Color.red;
	protected boolean isCorrect = false;
	protected boolean isWrong = false;
	protected int input;
	public Puzzle puzzle;
	protected double ans[] = new double[5];
	private static final DecimalFormat df = new DecimalFormat("0.#");
	public Object(GamePanel gp) {
		super(gp);
		key = -1;
	}
	
	public void update() {
		if(isCorrect || isWrong) {
			spriteCounter++;
			if (spriteCounter > 60) {
				isCorrect = false;
				isWrong = false;
				spriteCounter=0;
			}
		}
	}
	
	public String submitAns() {
		String desc = description;
		String key;
		if(description.contains("=a")) {
			key = String.valueOf(df.format(ans[0]));
			desc = description.replace("=a", "="+key);
			key = String.valueOf(df.format(ans[1]));
			desc = desc.replace("b", key);
		} if (description.contains("?")) {
			key = String.valueOf(df.format(gp.kc[gp.currentCard].useCard()));
			desc = description.replace("?", key);
		}
	    return desc;
	}
	
	public void drawSpeechBubble(Graphics2D g2, int x, int y) {
		if(puzzle.getPuzzleType()==PuzzleType.GEOMATRY && !locked) {
			BufferedImage sb_tl = setup("/res/speechbubble/speech_bubble-top_left", gp.tileSize, gp.tileSize);
			g2.drawImage(sb_tl, x, y - gp.tileSize, null);
			BufferedImage sb_t = setup("/res/speechbubble/speech_bubble-top", gp.tileSize, gp.tileSize);
			g2.drawImage(sb_t, x + gp.tileSize, y- gp.tileSize, null);
			BufferedImage sb_tr = setup("/res/speechbubble/speech_bubble-top_right", gp.tileSize, gp.tileSize);
			g2.drawImage(sb_tr, x + gp.tileSize * 2, y- gp.tileSize, null);
			sb_tl = setup("/res/speechbubble/speech_bubble-left", gp.tileSize, gp.tileSize);
			g2.drawImage(sb_tl, x, y, null);
			sb_t = setup("/res/speechbubble/speech_bubble-centre", gp.tileSize, gp.tileSize);
			g2.drawImage(sb_t, x + gp.tileSize, y, null);
			sb_tr = setup("/res/speechbubble/speech_bubble-right", gp.tileSize, gp.tileSize);
			g2.drawImage(sb_tr, x + gp.tileSize * 2, y, null);
		} else {
			BufferedImage sb_tl = setup("/res/speechbubble/speech_bubble-top_left", gp.tileSize, gp.tileSize);
			g2.drawImage(sb_tl, x, y, null);
			BufferedImage sb_t = setup("/res/speechbubble/speech_bubble-top", gp.tileSize, gp.tileSize);
			g2.drawImage(sb_t, x + gp.tileSize, y, null);
			BufferedImage sb_tr = setup("/res/speechbubble/speech_bubble-top_right", gp.tileSize, gp.tileSize);
			g2.drawImage(sb_tr, x + gp.tileSize * 2, y, null);
		}
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
		if(puzzle.getPuzzleType()==PuzzleType.GEOMATRY && !locked) {
			puzzle.getGeomatry(g2, text, x, y);
		} else if(puzzle.getPuzzleType()==PuzzleType.GRAPH && !locked) {
			puzzle.drawGraph(g2, text, x+gp.tileSize, y+gp.tileSize/2, (isCorrect||isWrong), ans[0], ans[1]);
		} else {
		y=y+gp.tileSize;
		try {
			int length, textX;
			if(text.length() > 6) {
				for(String line : text.split("((?==))")){
					if(line.length() > 6) {
						for(String line2 : line.split("((?=,))")){
							length = (int) g2.getFontMetrics().getStringBounds(line2, g2).getWidth();
							textX = x + (int)(gp.tileSize*1.5) - length/2;
							g2.drawString(line2, textX, y);
							y+=gp.tileSize/3;
						}
					} else {
						length = (int) g2.getFontMetrics().getStringBounds(line, g2).getWidth();
						textX = x + (int)(gp.tileSize*1.5) - length/2;
						g2.drawString(line, textX, y);
						y+=gp.tileSize/3;
					}
				}
			} else {
				length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
				textX = x + (int)(gp.tileSize*1.5) - length/2;
				g2.drawString(text, textX, y);
				y+=gp.tileSize/4;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}}
	}
}
