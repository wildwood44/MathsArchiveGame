package mae.game.puzzle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import mae.game.GamePanel;
import mae.game.GameState;
import mae.game.object.Obj_Pointer_Left;
import mae.game.object.Obj_Pointer_Right;
import mae.game.object.SumType;
import mae.game.object.Object;

public class Puzzle_40 extends Puzzle {
    private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");
	GamePanel gp;
	int question = 1;
	BigDecimal[] values = new BigDecimal[2];
	BigDecimal[] values2 = new BigDecimal[2];
	BigDecimal[] percentage = new BigDecimal[2]; 
	BigDecimal[] result = new BigDecimal[2]; 
	String[] input = new String[2];
	Obj_Pointer_Left pointLI, pointRI;
	Obj_Pointer_Right pointLD, pointRD;
 	int current = 0;
	int end, start, pos, length;
	boolean transition = false;

	public Puzzle_40(GamePanel gp) {
		super(gp, "", SumType.PLUS, PuzzleType.FRACTION);
		this.gp = gp;
		end = gp.tileSize/2;
		start = gp.tileSize*5;
		pos = start;
		length = start-end;
		generatePuzzle();
	}
	
	public void generatePuzzle() {
		//Get initial values
		for(int i = 0; i < values.length; i++) {
			values[i] = new BigDecimal(Math.round((Math.random()*99)+10));
			input[i] = "";
		}
		values2[0] = values[0];
		values2[1] = values[1];
		//Get difference by percentage
		for(int i = 0; i < percentage.length; i++) {
			percentage[i] = new BigDecimal(Math.round((Math.random()*8)+1)).multiply(new BigDecimal(10));
		}
		//Get result
		result[0] = values[0].add(calculatePercentage(percentage[0],values[0]));
		result[1] = values[1].subtract(calculatePercentage(percentage[1],values[1]));
		//Position pointers
		int length = start-end;
		//pos = max-((difference*percentage)/100)
		pos = new BigDecimal(start).subtract((new BigDecimal(length).multiply(percentage[0])).divide(new BigDecimal(100), 0, RoundingMode.HALF_UP)).intValueExact();
		pointLI = new Obj_Pointer_Left(gp, result[0]);
		pointLI.worldX = (int)(gp.tileSize*4.25);
		pointLI.worldY = pos;
		pointLD = new Obj_Pointer_Right(gp, values2[0]);
		pointLD.worldX = (int)(gp.tileSize*3.75);
		pointLD.worldY = start;
		pos = new BigDecimal(end).add(new BigDecimal(length).multiply(percentage[1]).divide(new BigDecimal(100), 0, RoundingMode.HALF_UP)).intValueExact();
		pointRI = new Obj_Pointer_Left(gp, result[1]);
		pointRI.worldX = (int)(gp.tileSize*7.25);
		pointRI.worldY = pos;
		pointRD = new Obj_Pointer_Right(gp, values2[1]);
		pointRD.worldX = (int)(gp.tileSize*6.75);
		pointRD.worldY = end;
	}

	public void drawPuzzle(Graphics2D g2) {
		x = gp.tileSize * 2;
		y = (int)(gp.tileSize * 0.25);
		BufferedImage metres = setup("/res/objects/puzzle_40", gp.tileSize*8, gp.tileSize*6);
		display(g2, metres, x, y);
		g2.setFont(g2.getFont().deriveFont(0, 20.0F));
		g2.drawString("Find the percentage increase/decrease.", x, y);
		//Set colour
		if(isCorrect) {
			g2.setColor(correct);
		} else if(isWrong) {
			g2.setColor(wrong);
		} else {
			g2.setColor(Color.white);
		}
		//Draw pointers
		pos = new BigDecimal(end).add(new BigDecimal(length).multiply(percentage[1]).divide(new BigDecimal(100), 0, RoundingMode.HALF_UP)).intValueExact();
		pointLI.display(g2, pointLI.worldX, pointLI.worldY);
		pointLD.display(g2, pointLD.worldX, pointLD.worldY);
		pointRI.display(g2, pointRI.worldX, pointRI.worldY);
		pointRD.display(g2, pointRD.worldX, pointRD.worldY);
		//Draw input
		if(input[0] != null) {
			g2.drawString(input[0] + "%", gp.tileSize*5, gp.tileSize*4);
		} else {
			g2.drawString("%", gp.tileSize*5, gp.tileSize*4);
		}
		if(input[1] != null) {
			g2.drawString(input[1] + "%", (int)(gp.tileSize*6.5), gp.tileSize*4);
		} else {
			g2.drawString("%", (int)(gp.tileSize*6.5), gp.tileSize*4);
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
			System.out.println(input[question-1] + " " +percentage[question-1]);
			input[question-1]=format.format(gp.kc[gp.currentCard].useCard());
			transition = true;
		}
		gp.keyH.enterPressed = false;
		gp.keyH.upPressed = false;
		gp.keyH.downPressed = false;
		gp.keyH.numberPressed = false;
	}
	
	public void update() {
		super.update();
		if(transition) {
			if(question==1) {
				pos = new BigDecimal(start).subtract((new BigDecimal(length).multiply(new BigDecimal(input[0]))).divide(new BigDecimal(100), 0, RoundingMode.HALF_UP)).intValueExact();
				if(pointLD.worldY > pos) {
					pointLD.worldY--;
					//pos = max-((difference*percentage)/100) percentage = ((max+pos)/difference)*100
					BigDecimal perc = ((new BigDecimal(start).subtract(new BigDecimal(pointLD.worldY))).divide(new BigDecimal(length), 2, RoundingMode.HALF_UP)).multiply(ONE_HUNDRED);
					pointLD.setValue(values[0].add((values[0].multiply(perc)).divide(new BigDecimal(100), 1, RoundingMode.HALF_UP)));
				} else {
					if(percentage[question-1].toString().equals(input[question-1])) {
						gp.playSE(2);
						isCorrect=true;
						question++;
					} else {
						pointLD.setValue(values[0]);
						pointLD.worldY=start;
						gp.playSE(3);
						isWrong=true;
					}
					transition = false;
				}
			} else {
				pos = new BigDecimal(end).add((new BigDecimal(length).multiply(new BigDecimal(input[1]))).divide(new BigDecimal(100), 0, RoundingMode.HALF_UP)).intValueExact();
				if(pointRD.worldY < pos) {
					pointRD.worldY++;
					BigDecimal perc = ((new BigDecimal(end).subtract(new BigDecimal(pointRD.worldY))).divide(new BigDecimal(length), 2, RoundingMode.HALF_UP)).multiply(ONE_HUNDRED);
					System.out.println(pointRD.worldY +" "+ perc);
					pointRD.setValue(values[1].add((values[1].multiply(perc)).divide(new BigDecimal(100), 1, RoundingMode.HALF_UP)));
				} else {
					if(percentage[question-1].toString().equals(input[question-1])) {
						gp.playSE(2);
						isCorrect=true;
						question++;
						opened=true;
					} else {
						pointRD.setValue(values[1]);
						pointRD.worldY=end;
						gp.playSE(3);
						isWrong=true;
					}
					transition = false;
				}
			}
		}
		//Exit puzzle and create new one
		if (opened && spriteCounter > 59) {
			question=1;
			generatePuzzle();
			gp.gameState = GameState.playState;
		}
	}
	
	public static BigDecimal calculatePercentage(BigDecimal part, BigDecimal whole) {
        if (whole.equals(0)) {
            throw new IllegalArgumentException("Whole number cannot be zero.");
        }
        return whole.multiply(part.divide(ONE_HUNDRED));
    }
}
