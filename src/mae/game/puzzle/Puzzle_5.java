package mae.game.puzzle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import mae.game.GamePanel;
import mae.game.GameState;
import mae.game.object.SumType;

public class Puzzle_5 extends Puzzle {
	GamePanel gp;
	int question = 1;
	int bonus = 0;
	double[] values = new double[2];
 	double[] ans = new double[3];
 	double answer = 0;
	String[] input = new String[3];
 	int current = 0;

	public Puzzle_5(GamePanel gp) {
		super(gp, "", SumType.PLUS, PuzzleType.EQUALS);
		this.gp = gp;
		generatePuzzle();
	}
	
	public void generatePuzzle() {
		input = new String[values.length];
		for(int i = 0; i < values.length; i++) {
			values[0] = (Math.round(Math.random()*49)+1);
			answer += values[i];
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
		for(int i = 1; i <= values.length; i++) {
			g2.drawString(values[i]+"", gp.tileSize * i, gp.tileSize*2);
		}
		g2.drawLine(gp.tileSize*4, gp.tileSize*4, gp.tileSize*8, gp.tileSize*4);
		for(int i = 1; i <= ans.length; i++) {
			g2.drawString(input[i]+"", gp.tileSize * i, gp.tileSize*4);
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
			input[current]=format.format(gp.kc[gp.currentCard].useCard());
			if(convert(input[current])==ans[current]) {
				gp.playSE(2);
				isCorrect=true;
				current++;
				if(current>2) {
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
		if (opened && spriteCounter > 59) {
			question=1;
			generatePuzzle();
			gp.gameState = GameState.playState;
		}
	}
}
