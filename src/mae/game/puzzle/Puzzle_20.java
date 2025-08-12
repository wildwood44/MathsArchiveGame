package mae.game.puzzle;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;

import mae.game.GamePanel;
import mae.game.GameState;
import mae.game.object.SumType;

public class Puzzle_20 extends Puzzle {
	GamePanel gp;
	int x, y, z;
	int question = 1;
	double value1 = (Math.round(Math.random()*60)+10), 
			value2 = (Math.round(Math.random()*60)+10);
	double[] factors1 = new double[(int) (value1)]; 
	double[] factors2 = new double[(int) (value2)];
	ArrayList<Double> factors = new ArrayList<Double>(); 
	double[] answer = new double[4];
	String[] input = new String[4];

	public Puzzle_20(GamePanel gp) {
		super(gp, "", SumType.DIVIDE, PuzzleType.FRACTION);
		this.gp = gp;
		generatePuzzle();
	}
	
	public void generatePuzzle() {
		value1 = (Math.round(Math.random()*60)+10); 
		value2 = (Math.round(Math.random()*60)+10);
		factors1 = new double[(int) (value1)]; 
		factors2 = new double[(int) (value2)];
		factors = new ArrayList<Double>(); 
		answer = new double[4];
		input = new String[4];
		while(!isEven(value1)) {
			value1 = (Math.round(Math.random()*60)+10);
			factors1 = new double[(int) (value1)];
		}
		while(!isEven(value2)) {
			value2 = (Math.round(Math.random()*60)+10);
			factors2 = new double[(int) (value2)];
		}
		for(int i = 1; i <= value1; i++) {
			if(isWhole(value1 / i)) {
				factors1[i-1] = value1 / i;
			}
		}
		for(int i = 1; i <= value2; i++) {
			if(isWhole(value2 / i)) {
				factors2[i-1] = value2 / i;
			}
		}
		for(int i = 1; i <= factors1.length; i++) {
			for(int j = 1; j <= factors2.length; j++) {
				//Check if whole number
				if(factors1[(i-1)]!=0 && factors2[(j-1)]!=0 &&
						factors1[(i-1)]!=1 && factors2[(j-1)]!=1) {
					if(factors1[(i-1)] == factors2[(j-1)]) {
						factors.add(factors1[(i-1)]);
					}
				}
			}
		}
		//Get lowest not 1 factor
		for(int i = factors.size()-1; i > -1; i--) {
			if(factors.get(i)!=0) {
				answer[0] = factors.get(i);
				break;
			}
		}
		//Get lowest prime factor
		for(int i = factors.size()-1; i > 0-1; i--) {
			if(isPrime((double)(factors.get(i)))) {
				answer[1] = factors.get(i);
				break;
			}
		}
		//Get highest factor
		answer[2] = factors.get(0);
		//Get highest prime factor
		for(int i = 0; i < factors.size(); i++) {
			if(isPrime((double)(factors.get(i)))) {
				answer[3] = factors.get(i);
				break;
			}
		}
	}

	public void drawPuzzle(Graphics2D g2) {
		//Factors
		DecimalFormat format = new DecimalFormat("0.#");
		x = gp.tileSize * 2;
		y = (int)(gp.tileSize * 0.25);
		BufferedImage scoreboard = setup("/res/objects/puzzle_20", gp.tileSize*8, gp.tileSize*6);
		display(g2, scoreboard, x, y);
		g2.setColor(Color.white);
		y = (int)(gp.tileSize);
		if(question==1) {
			g2.drawString("Find lowest factor after 1", x, y);
		} else if(question==2) {
			g2.drawString("Find lowest prime factor", x, y);
		} else if(question==3) {
			g2.drawString("Find greatest factor", x, y);
		} else if(question==4) {
			g2.drawString("Find greatest prime factor", x, y);
		}
		x = (int)(gp.tileSize * 3.80);
		y = (int)(gp.tileSize * 3.80);
		g2.drawString(format.format(value1), x, y);
		x = (int)(gp.tileSize * 7.80);
		g2.drawString(format.format(value2), x, y);
		update();
		g2.setFont(g2.getFont().deriveFont(0, 22.0F));
		int textX = gp.tileSize * 5;
		int textY = gp.tileSize * 4;
		if(isCorrect) {
			g2.setColor(correct);
		} else if(isWrong) {
			g2.setColor(wrong);
		} else {
			g2.setColor(Color.white);
		}
		g2.setStroke(new BasicStroke());
		for(int i = 0; i < 4; i++) {
			g2.drawRect(textX, y, gp.tileSize/2, gp.tileSize);
			if(input[i]!=null) {
				g2.drawString(input[i], textX+16, textY+16);
			}
			textX+=gp.tileSize/2;
		}
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
			if(convert(input[question-1])==answer[question-1]) {
				gp.playSE(2);
				isCorrect=true;
				question++;
				if(question>=5) {
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
	
	public void update() {
		super.update();
		//Exit puzzle and create new one
		if (opened && spriteCounter > 59) {
			question=1;
			generatePuzzle();
			gp.gameState = GameState.playState;
		}
	}
}
