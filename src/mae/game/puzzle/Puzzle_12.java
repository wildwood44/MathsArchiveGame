package mae.game.puzzle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import mae.game.GamePanel;
import mae.game.GameState;
import mae.game.object.SumType;

public class Puzzle_12 extends Puzzle {
	GamePanel gp;
	int question = 1;
	int bonus = 0;
	double[] values = new double[5]; 
	boolean[] hidden = new boolean[5]; 
	String[] input = new String[5];
	int[] hide = new int[2];
 	int current = 0;

	public Puzzle_12(GamePanel gp) {
		super(gp, "", SumType.PLUS, PuzzleType.FRACTION);
		this.gp = gp;
		generatePuzzle();
	}
	
	public void generatePuzzle() {
		values = new double[5+bonus]; 
		hidden = new boolean[5+bonus]; 
		input = new String[5+bonus];
		hide = new int[2+bonus];
		values[0] = (Math.round(Math.random()*5)+1);
		double differnce = (Math.round(Math.random()*5)+1);
		for(int i = 1; i < values.length; i++) {
			values[i] = values[i-1] + differnce;
			hidden[i] = false;
		}
		System.out.println(values.length +" Size");
		//hide[0] = (int) (Math.round(Math.random()*values.length));
		for(int i = 0; i < hide.length; i++) {
			hide[i] = (int) (Math.round(Math.random()*(values.length-1)));
			for(int j = 0; j < i; j++) {
				while (hide[i] == hide[j]) {
					hide[i]++;
					if(hide[i]>values.length-1) {
						hide[i]=0;
					}
				}
			}
			hidden[hide[i]] = true;
		}
	}

	public void drawPuzzle(Graphics2D g2) {
		//Factors
		DecimalFormat format = new DecimalFormat("0.#");
		x = gp.tileSize * 2;
		y = (int)(gp.tileSize * 0.25);
		g2.setFont(g2.getFont().deriveFont(0, 100.0F));
		if(isCorrect) {
			g2.setColor(correct);
		} else if(isWrong) {
			g2.setColor(wrong);
		} else {
			g2.setColor(Color.white);
		}
		if(hidden[current]) {
			g2.drawString("?", gp.tileSize*5, gp.tileSize*3);
		} else {
			g2.drawString(format.format(values[current]), gp.tileSize*5, gp.tileSize*3);
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
			if(convert(input[current])==values[current]) {
				hidden[current] = false;
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
