package mae.game.puzzle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.Random;

import mae.game.GamePanel;
import mae.game.GameState;
import mae.game.object.SumType;

public class Puzzle_130 extends Puzzle {
	GamePanel gp;
	Random r = new Random();
	int question = 1;
	int bonus = 0;
	Algebra[] values = new Algebra[10]; 
	boolean[] hidden = new boolean[10]; 
	String[] input = new String[10];
	int[] input2 = new int[10];
	String[] input3 = new String[10];
	int[] hide = new int[4];
 	int current = 0;
 	String alphabet = "xyz";

	public Puzzle_130(GamePanel gp) {
		super(gp, "", SumType.PLUS, PuzzleType.FRACTION);
		this.gp = gp;
		generatePuzzle();
	}
	
	public void generatePuzzle() {
		values = new Algebra[10]; 
		hidden = new boolean[10]; 
		input = new String[10];
		hide = new int[4];
		for(int i = 0; i < values.length; i++) {
			hidden[i] = false;
		}
		for(int i = 0; i < 4; i++) {
			//values[i] = String.valueOf(Math.round(Math.random()*9)+1);
			String sValue = String.valueOf(alphabet.charAt(r.nextInt(alphabet.length())));
			values[i] = new Algebra((int)((Math.random()*9)+1)+sValue);
		}
		values[4] = new Algebra(values[0].add(values[1]));
		values[5] = new Algebra(values[1].add(values[2]));
		values[6] = new Algebra(values[2].add(values[3]));
		values[7] = new Algebra(values[4].add(values[5]));
		values[8] = new Algebra(values[5].add(values[6]));
		values[9] = new Algebra(values[7].add(values[8]));
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
			values[hide[i]].hideAlgebra(hidden[hide[i]]);
		}
	}

	public void drawPuzzle(Graphics2D g2) {
		DecimalFormat format = new DecimalFormat("0.#");
		int textLength = 2;
		x = gp.tileSize * 2;
		y = (int)(gp.tileSize * 0.25);
		BufferedImage pyramid = setup("/res/objects/pyramid-3", gp.tileSize*8, gp.tileSize*6);
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
		for(int i = 0; i < 4; i++) {
			/*g2.drawString(values[i].getFullValue(), x, y);
			if(current == i) {
				cursorX = x-(int)(gp.tileSize*0.5);
				cursorY = y-gp.tileSize;
				g2.drawRect(cursorX, cursorY, cursorW, cursorH);
			}*/
			drawBrickValues(g2, i);
			x += gp.tileSize*1.75;
		}
		x -= (int)(gp.tileSize*6.125);
		y -= (int)(gp.tileSize*1.25);
		for(int i = 4; i < 7; i++) {
			drawBrickValues(g2, i);
			x += gp.tileSize*1.75;
		}
		x -= (int)(gp.tileSize*4.375);
		y -= (int)(gp.tileSize*1.25);
		for(int i = 7; i < 9; i++) {
			drawBrickValues(g2, i);
			x += gp.tileSize*1.75;
		}
		x -= (int)(gp.tileSize*2.625);
		y -= (int)(gp.tileSize*1.25);
		drawBrickValues(g2, 9);
		update();
	}
	
	public void drawBrickValues(Graphics2D g2, int i) {
		int cursorX = x-(int)(gp.tileSize*0.5);
		int cursorY = y-gp.tileSize;
		int cursorW = (int)(gp.tileSize*1.75);
		int cursorH = (int)(gp.tileSize*1.25);
		int length = values[i].getFullValue().length();
		if(length > 6) {
			x-=10;
			cursorW=cursorW*2;
			g2.setFont(g2.getFont().deriveFont(0, 20.0F));
		} else if(length > 3) {
			x-=5;
			cursorW=(int)(cursorW*1.5);
			g2.setFont(g2.getFont().deriveFont(0, 30.0F));
		}
		g2.drawString(values[i].getFullValue(), x,y);
		if(current == i) {
			cursorX = x-(int)(gp.tileSize*0.5);
			cursorY = y-gp.tileSize;
			g2.drawRect(cursorX, cursorY, cursorW, cursorH);
		}
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
			if(input2[current]<values[current].algebraLength()-1) {
				input3[input2[current]] = input[current];
				input2[current]++;
			} else { 
				input3[input2[current]] = input[current];
				input2[current]=0;
				if(values[current].isCorrect(input3)) {
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
