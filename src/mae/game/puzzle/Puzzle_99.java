package mae.game.puzzle;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import mae.game.GamePanel;
import mae.game.GameState;
import mae.game.object.SumType;

public class Puzzle_99 extends Puzzle {
	GamePanel gp;
	int x, y, z;
	int question = 1;
	double[][] value = new double[4][8];
	double[] answer = new double[4];
	Puzzle[] puzzles = new Puzzle[4];
	String[] input = new String[5];

	public Puzzle_99(GamePanel gp) {
		super(gp, "mean()=?", SumType.EQUAL, PuzzleType.STAT);
		this.gp = gp;
		generatePuzzle();
	}
	
	public void generatePuzzle() {
		for(int i = 0; i < value.length; i++) {
			for(int j = 0; j < value[i].length-1; j++) {
				value[i][j] = Math.round(Math.random()*10);
				answer[i] += value[i][j];
			}
			int count = 0;
			double temp = answer[i];
			while(!(temp%8==0)){
				count++;
				temp = answer[i];
				value[i][7]=count;
				temp += value[i][7];
			}
			answer[i] += count;
			answer[i] = answer[i]/8;
		}
	}

	public void drawPuzzle(Graphics2D g2) {
		DecimalFormat format = new DecimalFormat("0.#");
		x = gp.tileSize * 2;
		y = (int)(gp.tileSize * 1.5);
		BufferedImage scoreboard = setup("/res/objects/img_scoreboard", gp.tileSize*8, gp.tileSize*2);
		display(g2, scoreboard, x, y);
		g2.setColor(Color.white);
		if(question!=5) {
			g2.drawString("Find player "+question+"'s mean.", x, y);
		} else if(question==5) {
			g2.drawString("Who wins (1-4)?", x, y);
		}
		int textX = x + 48;
		int textY = y + 20;
		for(int i = 0; i < value.length; i++) {
			g2.setColor(Color.black);
			for(int j = 0; j < value[i].length; j++) {
				if(isCorrect) {
					g2.setColor(correct);
				} else if(isWrong) {
					g2.setColor(wrong);
				}
				g2.drawString(""+format.format(value[i][j]), textX, textY);
				textX += 48;
			}
			textX = x + 48;
			if(input[i]!=null) {
				g2.drawString(input[i], textX+gp.tileSize*6, textY);
			}
			textY += gp.tileSize/2;
		}
		update();
		y = (int)(gp.tileSize * 4.5);
		g2.setFont(g2.getFont().deriveFont(0, 22.0F));
		//int textX = x + 5;
		//int textY = y + gp.tileSize/2;
		g2.setStroke(new BasicStroke());
	}

	
	public void display(Graphics2D g2, BufferedImage image, int screenX, int screenY) {
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		g2.drawImage(image, tempScreenX, tempScreenY, null);
	}
	
	public void interact() {
		if(gp.keyH.numberPressed) {
			if(question!=5) {
				input[question-1]=gp.currentCard+"";
				if(convert(input[question-1])==answer[question-1]) {
					gp.playSE(2);
					isCorrect=true;
					question++;
				} else {
					gp.playSE(3);
					isWrong=true;
				}
			}else {
				input[question-1]=gp.currentCard+"";
				if(convert(input[question-1])==largestPos(answer)+1) {
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
