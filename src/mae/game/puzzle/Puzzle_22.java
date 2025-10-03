package mae.game.puzzle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import mae.game.GamePanel;
import mae.game.GameState;
import mae.game.object.SumType;

public class Puzzle_22 extends Puzzle {
	GamePanel gp;
	int question = 1;
	int bonus = 0;
	double[] values = new double[10]; 
	boolean[] hidden = new boolean[10]; 
	String[] input = new String[10];
	int[] hide = new int[4];
 	int current = 0;

	public Puzzle_22(GamePanel gp) {
		super(gp, "", SumType.PLUS, PuzzleType.FRACTION);
		this.gp = gp;
		generatePuzzle();
	}
	
	public void generatePuzzle() {
		values = new double[10]; 
		hidden = new boolean[10]; 
		input = new String[10];
		hide = new int[4];
		for(int i = 0; i < values.length; i++) {
			hidden[i] = false;
		}
		for(int i = 0; i < 4; i++) {
			values[i] = (Math.round(Math.random()*5)+1);
		}
		values[4] = values[0]*values[1];
		values[5] = values[1]*values[2];
		values[6] = values[2]*values[3];
		values[7] = values[4]*values[5];
		values[8] = values[5]*values[6];
		values[9] = values[7]*values[8];
		for(int i = 0; i < hide.length; i++) {
			hide[i] = (int) (Math.round(Math.random()*(values.length-2)));
			for(int j = 0; j < i; j++) {
				while (hide[i] == hide[j] || values[hide[i]]>=100) {
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
		DecimalFormat format = new DecimalFormat("0.#");
		x = gp.tileSize * 2;
		y = (int)(gp.tileSize * 0.25);
		BufferedImage pyramid = setup("/res/objects/pyramid-2", gp.tileSize*8, gp.tileSize*6);
		display(g2, pyramid, x, y);
		g2.setFont(g2.getFont().deriveFont(0, 40.0F));
		if(isCorrect) {
			g2.setColor(correct);
		} else if(isWrong) {
			g2.setColor(wrong);
		} else {
			g2.setColor(Color.white);
		}
		x = (int)(gp.tileSize*3);
		y = gp.tileSize*6;
		int cursorX = x-(int)(gp.tileSize*0.5);
		int cursorY = y-gp.tileSize;
		int cursorW = (int)(gp.tileSize*1.75);
		int cursorH = (int)(gp.tileSize*1.25);
		for(int i = 0; i < 4; i++) {
			if(hidden[i]) {
				g2.drawString("?", x, y);
			} else {
				g2.drawString(format.format(values[i]), x, y);
			}
			if(current == i) {
				cursorX = x-(int)(gp.tileSize*0.5);
				cursorY = y-gp.tileSize;
				g2.drawRect(cursorX, cursorY, cursorW, cursorH);
			}
			x += gp.tileSize*1.75;
		}
		x -= (int)(gp.tileSize*6.125);
		y -= (int)(gp.tileSize*1.25);
		for(int i = 4; i < 7; i++) {
			if(hidden[i]) {
				g2.drawString("?", x, y);
			} else {
				g2.drawString(format.format(values[i]), x, y);
			}
			if(current == i) {
				cursorX = x-(int)(gp.tileSize*0.5);
				cursorY = y-gp.tileSize;
				g2.drawRect(cursorX, cursorY, cursorW, cursorH);
			}
			x += gp.tileSize*1.75;
		}
		x -= (int)(gp.tileSize*4.375);
		y -= (int)(gp.tileSize*1.25);
		for(int i = 7; i < 9; i++) {
			if(hidden[i]) {
				g2.drawString("?", x,y);
			} else {
				g2.drawString(format.format(values[i]), x,y);
			}
			if(current == i) {
				cursorX = x-(int)(gp.tileSize*0.5);
				cursorY = y-gp.tileSize;
				g2.drawRect(cursorX, cursorY, cursorW, cursorH);
			}
			x += gp.tileSize*1.75;
		}
		x -= (int)(gp.tileSize*2.625);
		y -= (int)(gp.tileSize*1.25);
		if(hidden[9]) {
			g2.drawString("?", x,y);
		} else {
			g2.drawString(format.format(values[9]), x,y);
		}	
		if(current == 9) {
			cursorX = x-(int)(gp.tileSize*0.5);
			cursorY = y-gp.tileSize;
			g2.drawRect(cursorX, cursorY, cursorW, cursorH);
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
