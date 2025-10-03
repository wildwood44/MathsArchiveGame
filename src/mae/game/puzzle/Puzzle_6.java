package mae.game.puzzle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import mae.game.GamePanel;
import mae.game.GameState;
import mae.game.object.SumType;

public class Puzzle_6 extends Puzzle {
	GamePanel gp;
	int question = 1;
	int bonus = 0;
	int[] values = new int[8]; 
	int[] totals = new int[8]; 
	int finalValue = 0;
	String[] input = new String[9];

	public Puzzle_6(GamePanel gp) {
		super(gp, "", SumType.PLUS, PuzzleType.MISSING);
		this.gp = gp;
		generatePuzzle();
	}
	
	public void generatePuzzle() {
		finalValue=0;
		for(int i = 0; i < totals.length; i++) {
			//for(int j = 0; j < values.length; j++) {
				values[i] = (int) (Math.round(Math.random()*11)+1);
				//totals[i] += values[j];
			//}
			finalValue += values[i];
		}
	}

	public void drawPuzzle(Graphics2D g2) {
		DecimalFormat format = new DecimalFormat("0.#");
		x = gp.tileSize*3;
		y = (int)(gp.tileSize*1);
		g2.setFont(g2.getFont().deriveFont(0, 40.0F));
		//g2.drawLine(x, y, gp.tileSize*8, y);
		g2.drawLine((int)(gp.tileSize*3.5), y-gp.tileSize, (int)(gp.tileSize*3.5), (int)(gp.tileSize*6.5));
		g2.drawLine(gp.tileSize*8, y-gp.tileSize, gp.tileSize*8, (int)(gp.tileSize*6.5));
		for(int i = 0; i < values.length; i++) {
			g2.setColor(Color.white);
			for(int j = 0; j < values[i]; j++) {
				if((j+1)%5!=0) {
					g2.drawString("|", x+32, y);
				} else {
					g2.drawString("—", x, y);
				}
				x += 8;
			}
			if(question-1 == i) {
				g2.drawString(">", (gp.tileSize*7), y);
			} else if(question-1 > i) {
				//Check if answer is correct
				if(isCorrect) {
					g2.setColor(correct);
				} else if(isWrong) {
					g2.setColor(wrong);
				} else {
					g2.setColor(Color.white);
				}
				g2.drawString(values[i]+"", (gp.tileSize*8), y);
			}
			x = (int)(gp.tileSize*3);
			g2.drawLine(x, y+6, gp.tileSize*9, y+6);
			y+=(int)(gp.tileSize/1.5);
		}
		//Check if answer is correct
		if(isCorrect) {
			g2.setColor(correct);
		} else if(isWrong) {
			g2.setColor(wrong);
		} else {
			g2.setColor(Color.white);
		}
		if(question ==9) {
			g2.drawString("Total=", (gp.tileSize*5), y);
		} if(opened) {
			g2.drawString(finalValue+"", (gp.tileSize*8), y);
		}
		update();
	}

	
	public void display(Graphics2D g2, BufferedImage image, int screenX, int screenY) {
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		g2.drawImage(image, tempScreenX, tempScreenY, null);
	}
	
	public void interact() {
		DecimalFormat format = new DecimalFormat("0.#");
		if(gp.keyH.numberPressed) {
			input[question-1]=format.format(gp.kc[gp.currentCard].useCard());
			if(question < 9) {
				if(convert(input[question-1])==values[question-1]) {
					gp.playSE(2);
					isCorrect=true;
					question++;
				} else {
					gp.playSE(3);
					isWrong=true;
				}
			} else if (question >= 9) {
				if (convert(input[question-1])==finalValue) {
					gp.playSE(2);
					isCorrect=true;
					opened = true;
					gp.score.addPoints(5);
				} else {
					gp.playSE(3);
					isWrong=true;
				}
			}
		}
		gp.keyH.enterPressed = false;
		gp.keyH.upPressed = false;
		gp.keyH.downPressed = false;
		gp.keyH.numberPressed = false;
	}
	public static boolean areAllFalse(boolean[] array)
	{
	    for(boolean b : array) if(b) return false;
	    return true;
	}
	
	public void update() {
		super.update();
		//Exit puzzle and create new one
		if (opened && spriteCounter > 59) {
			opened=false;
			question=1;
			generatePuzzle();
			gp.gameState = GameState.playState;
		}
	}
}
