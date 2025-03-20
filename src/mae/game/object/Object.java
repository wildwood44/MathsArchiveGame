package mae.game.object;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import mae.game.Entity;
import mae.game.GamePanel;
import mae.game.puzzle.PuzzleType;

public class Object extends Entity {
	//public static int objId;
	public int size;
	protected int input;
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
		if(description.contains("a")) {
			key = String.valueOf(df.format(ans[0]));
			desc = description.replace("a/", key+"/");
			desc = desc.replace("a,", key+",");
			desc = desc.replace("a√", key+"√");
			desc = desc.replace("a+", "*"+key+"+");
			desc = desc.replace("ax", key+"x");
			key = String.valueOf(df.format(ans[1]));
			if(desc.matches(".*\\db.*")){
				desc = desc.replace("b", "*"+key);
			}
			else {
				desc = desc.replace("b", key);
			}
		} if (description.contains("?")) {
			key = String.valueOf(df.format(gp.kc[gp.currentCard].useCard()));
			desc = description.replace("?", key);
		}
	    return desc;
	}
	
	public void drawSpeechBubble(Graphics2D g2, int x, int y) {
		if(puzzle.getPuzzleType()==PuzzleType.GRAPH ||
			puzzle.getPuzzleType()==PuzzleType.GEOMATRY ||
			puzzle.getPuzzleType()==PuzzleType.STAT || 
			puzzle.getPuzzleType()==PuzzleType.PROBABIL && !locked) {
			BufferedImage sb_tl = setup("/res/speechbubble/speech-bubble", gp.tileSize*3, gp.tileSize*3);
			g2.drawImage(sb_tl, x, y - gp.tileSize, null);
		} else if(puzzle.getPuzzleType()==PuzzleType.TIME ||
			puzzle.getPuzzleType()==PuzzleType.WEIGHT ||
			puzzle.getPuzzleType()==PuzzleType.D_PERCENTAGE && !locked) {
			BufferedImage sb_tl = setup("/res/speechbubble/speech-bubble_long", gp.tileSize*4, gp.tileSize*2);
			g2.drawImage(sb_tl, x-gp.tileSize/2, y, null);
		} else {
			BufferedImage sb_tl = setup("/res/speechbubble/speech-bubble_small", gp.tileSize*3, gp.tileSize*2);
			g2.drawImage(sb_tl, x, y, null);
		}
		g2.setFont(g2.getFont().deriveFont(1, 24.0F));
		
	}
	
	public void drawSpeech(Graphics2D g2, int x, int y) {
		String regex = "=";
		//Get regex for line break
		if((puzzle.getPuzzleType()==PuzzleType.PERCENTAGE ||
			puzzle.getPuzzleType()==PuzzleType.D_PERCENTAGE) && 
			!locked) {
			regex = " ";
		} else if(puzzle.getPuzzleType()==PuzzleType.STAT ||
			puzzle.getPuzzleType()==PuzzleType.PROBABIL ||
			puzzle.getPuzzleType()==PuzzleType.GRAPH ||
			puzzle.getPuzzleType()==PuzzleType.GEOMATRY &&
			!locked) {
			regex = ",";
		}
		//Break text into lines to fit in box
		String text = description;
		String[] lines;
		int breakPoint = 6;
		/*if(puzzle.getPuzzleType()==PuzzleType.FRACTION||
			puzzle.getPuzzleType()==PuzzleType.PROBABIL||
			puzzle.getPuzzleType()==PuzzleType.PERCENTAGE//||
			//puzzle.getPuzzleType()==PuzzleType.STAT
			) {
			breakPoint = 8;
		} else if(puzzle.getPuzzleType()==PuzzleType.MEASURE||
			puzzle.getPuzzleType()==PuzzleType.TIME||
			puzzle.getPuzzleType()==PuzzleType.ALGEBRA) {
			breakPoint = 10;
		} else */if (puzzle.getPuzzleType()==PuzzleType.GRAPH ||
			puzzle.getPuzzleType()==PuzzleType.GEOMATRY||
			puzzle.getPuzzleType()==PuzzleType.D_PERCENTAGE) { 
			breakPoint = 12;
		}
		//Draw content
		if(isCorrect) {
			g2.setColor(correct);
			text = submitAns();
		} else if(isWrong) {
			g2.setColor(wrong);
			text = submitAns();
		} else {
			g2.setColor(Color.white);
		}
		lines = gp.ui.breakLines(text,breakPoint,regex);
		//Draw box
		drawSpeechBubble(g2,x,y);
		if(puzzle.getPuzzleType()==PuzzleType.GRAPH||
			puzzle.getPuzzleType()==PuzzleType.GEOMATRY) {
			y=y-gp.tileSize/3;
		} else if(lines.length > 2) {
			y=y-gp.tileSize;
		}
		if(puzzle.getPuzzleType()==PuzzleType.GEOMATRY && !locked) {
			puzzle.drawGeomatry(g2, text, x, y);
		} else if(puzzle.getPuzzleType()==PuzzleType.GRAPH && !locked) {
			puzzle.drawGraph(g2, text, x+gp.tileSize, y+gp.tileSize/2, (isCorrect||isWrong), ans[0], ans[1]);
		} else if(puzzle.getPuzzleType()==PuzzleType.FRACTION && !locked) {
			puzzle.drawFraction(g2, text, x+gp.tileSize, y+gp.tileSize/2, (isCorrect||isWrong));
		} else {
			y=y+gp.tileSize;
			try {
				int length, textX;
				if(text.length() > breakPoint) {
					for (String line : lines) {
						length = (int) g2.getFontMetrics().getStringBounds(line, g2).getWidth();
						textX = x + (int)(gp.tileSize*1.5) - length/2;
						g2.drawString(line, textX, y);
						y += gp.tileSize/3;
					}
				} else {
					length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
					textX = x + (int)(gp.tileSize*1.5) - length/2;
					g2.drawString(text, textX, y);
					y+=gp.tileSize/3;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
