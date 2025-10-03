package mae.game.puzzle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import mae.game.GamePanel;
import mae.game.GameState;
import mae.game.object.SumType;

public class Puzzle_51 extends Puzzle {
	GamePanel gp;
	int question = 1;
	int bonus = 0;
	BigDecimal[] values = new BigDecimal[5];
	Fraction[] fraction = new Fraction[5];
	boolean[] hidden = new boolean[5]; 
	String[] input = new String[5];
	int[] input2 = new int[5];
    String upper[] = new String[5];
    String lower[] = new String[5];
	int[] hide = new int[2];
 	int current = 0;

	public Puzzle_51(GamePanel gp) {
		super(gp, "", SumType.PLUS, PuzzleType.FRACTION);
		this.gp = gp;
		generatePuzzle();
	}
	
	public void generatePuzzle() {
		values = new BigDecimal[5+bonus];
		fraction = new Fraction[5+bonus];
		hidden = new boolean[5+bonus]; 
		input = new String[5+bonus];
		input2 = new int[5+bonus];
		upper = new String[5+bonus];
		lower = new String[5+bonus];
		hide = new int[2+bonus];
		values[0] = new BigDecimal((Math.random()*0.5)+0.1);
		values[0] = values[0].setScale(1, RoundingMode.HALF_UP);
		fraction[0] = new Fraction(values[0].toString());
		BigDecimal differnce = new BigDecimal((Math.random()*0.5)+0.1);
		differnce = differnce.setScale(1, RoundingMode.HALF_UP);
		for(int i = 1; i < values.length; i++) {
			values[i] = values[i-1].add(differnce);
			values[i] = values[i].setScale(1, RoundingMode.HALF_UP);
			fraction[i] = new Fraction(values[i].toString());
			hidden[i] = false;
		}
		//Hide random value
		for(int i = 0; i < hide.length; i++) {
			hide[i] = (int) (Math.round(Math.random()*(values.length-1)));
			for(int j = 0; j < i; j++) {
				//No repeats or whole numbers
				while (hide[i] == hide[j] || fraction[hide[i]].isWhole()) {
					hide[i]++;
					if(hide[i]>values.length-1) {
						hide[i]=0;
					}
				}
			}
			hidden[hide[i]] = true;
			fraction[hide[i]].hideFraction(hidden[hide[i]]);
		}
	}

	public void drawPuzzle(Graphics2D g2) {
		//Factors
		//DecimalFormat format = new DecimalFormat("0.#");
		x = gp.tileSize * 2;
		y = (int)(gp.tileSize * 0.25);
		g2.setFont(g2.getFont().deriveFont(0, 50.0F));
		if(isCorrect) {
			g2.setColor(correct);
		} else if(isWrong) {
			g2.setColor(wrong);
		} else {
			g2.setColor(Color.white);
		}
		if(hidden[current]) {
			fraction[current].printFraction(g2, gp.tileSize*5, gp.tileSize*3);
		} else {
			fraction[current].printFraction(g2, gp.tileSize*5, gp.tileSize*3);
		}
		g2.drawLine(gp.tileSize*4, gp.tileSize*4, gp.tileSize*8, gp.tileSize*4);
		x=gp.tileSize*5;
		for(int i = 0; i < values.length; i++) {
			if(i==current) {
				g2.setColor(Color.white);
			} else {
				g2.setColor(Color.gray);
			}
			g2.drawString(".", x, gp.tileSize*5);
			x+=gp.tileSize/4;
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
		if(gp.keyH.rightPressed) {
			current++;
			if(current > values.length-1) {
				current = 0;
			}
		} else if(gp.keyH.leftPressed) {
			current--;
			if(current < 0) {
				current = values.length-1;
			}
		}
		if(gp.keyH.numberPressed) {
			input[current]=format.format(gp.kc[gp.currentCard].useCard());
			if(input2[current]==0) {
				upper[current] = input[current];
				input2[current]++;
			} else if(input2[current]==1) {
				lower[current] = input[current];
				if(convert(upper[current])==fraction[current].getNumerator() &&
					convert(lower[current])==fraction[current].getDenominator()) {
					hidden[current] = false;
					fraction[current].hideFraction(hidden[current]);
					gp.playSE(2);
					isCorrect=true;
					question++;
					if(areAllFalse(hidden)) {
						bonus++;
						opened = true;
						gp.score.addPoints(5);
					}
				} else {
					gp.playSE(3);
					isWrong=true;
				}
				input2[current]=0;
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
		if (areAllFalse(hidden)) {
			question=1;
			generatePuzzle();
			gp.gameState = GameState.playState;
		}
	}
}
