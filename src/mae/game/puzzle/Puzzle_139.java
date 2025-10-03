package mae.game.puzzle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import mae.game.Entity;
import mae.game.GamePanel;
import mae.game.GameState;
import mae.game.object.SumType;

public class Puzzle_139 extends Puzzle {
	GamePanel gp;
	int question = 1;
	int bonus = 0;
	//Entity door = gp.obj[150][1];
	String[] questions = new String[13]; 
	double[] values = new double[13];  
	double[] values_d1 = new double[3];  
	double[] values_d2 = new double[3]; 
    String upper[] = new String[3];
    String lower[] = new String[3];
	String[] input = new String[13];
	int[] input2 = new int[12];
 	int current = 0;
 	Fraction f;

	public Puzzle_139(GamePanel gp) {
		super(gp, "", SumType.PLUS, PuzzleType.MISSING);
		this.gp = gp;
		generatePuzzle();
	}
	
	@Override
	public void getImage() {
		idle1 = setup("/res/objects/puzzle_40_surface", width, height);
	}
	
	public void lock(int key) {
		this.key = key;
		for(int j = 0; j < gp.obj[floor].length - 1; j++) {
			if(gp.obj[floor][j] != null && gp.obj[floor][j].id == key){
				gp.obj[floor][j].setLock(true);
				break;
			}
		}
	}
	
	public void generatePuzzle() {
		DecimalFormat format = new DecimalFormat("0.#");
		values[0] = 46;
		values[1] = values[0]/23;
		values[2] = values[1]/10;
		values[3] = values[2]*100;
		f = new Fraction(values[2]+"");
		values_d1[0] = f.getNumerator();
		values_d2[0] = f.getDenominator();
		values[5] = 1 + (values_d1[0]*12) + values_d2[0];
		values[6] = values[5]/2;
		values[7] = 6;
		values[8] = Math.round((values[0] + values[1] + Math.round(values[2]) + values[5] + values[6] + values[7])/6);
		values[9] = 100 - values[8];
		values_d1[1] = 86/2;
		values_d2[1] = 14/2;
		Radical r = new Radical("√"+(values_d1[1] + values_d2[1]));
		values_d1[2] = r.getIndex();
		values_d2[2] = r.getRoot();
		questions[0] = "How many rooms don't have prisoners and are not Locked?";
		questions[1] = "What is it's lowerest not 1 factor of "+format.format(values[0])+"?";
		questions[2] = "What do you get when you divide "+format.format(values[1])+" by 10";
		questions[3] = "What is "+values[2]+" in percentages?";
		questions[4] = "What is "+format.format(values[3])+"% as a fraction?";
		questions[5] = "Translate 1 by the x = numerator and y = denominator of previous fraction.";
		questions[6] = "How many mph would you go in 2 hours going at a distance of "+format.format(values[5])+"?";
		questions[7] = "Round to the nearest whole number the area of a circle with a circumferance of " +values[6]+ "?";
		questions[8] = "What is the rounded mean of all previous rounded values? ("+format.format(values[0])+","+format.format(values[1])+","+format.format(Math.round(values[2]))+","+format.format(values[5])+","+format.format(values[6])+","+format.format(values[7])+")";
		questions[9] = "If you have a "+format.format(values[8])+" change of failure what is the change of success?";
		questions[10] = "Factor "+format.format(values[9])+"x + "+format.format(values[8])+"y.";
		questions[11] = "What do you get if you √"+format.format(values_d1[1])+"+"+format.format(values_d2[1])+"?";
		questions[12] = "Input correct - Opening";
	}

	public void drawPuzzle(Graphics2D g2) {
		x = gp.tileSize*2;
		y = (int)(gp.tileSize*1);
		g2.setColor(Color.white);
		int textY = y;
		for (String line : gp.ui.breakLines(questions[question-1],40," ")) {
			g2.drawString(line, x, textY);
			textY += (gp.tileSize/2);
		}
		BufferedImage final_puzzle;
		if(question==1) {
			final_puzzle = setup("/res/objects/Obj_Final", gp.tileSize*6, gp.tileSize*4);
			display(g2, final_puzzle, x+gp.tileSize, y+(gp.tileSize));
		} else {
			final_puzzle = setup("/res/objects/Obj_Final"+(question), gp.tileSize*6, gp.tileSize*4);
			display(g2, final_puzzle, x+gp.tileSize, y+(gp.tileSize));
		}
		
		if(input[question-1] == null) {
			if(question==5) {
				f.hideFraction(true);
				f.printFraction(g2, x, textY);
			} else if(question==11) {
				g2.drawString("(x, y)", x, textY);
			} else if(question==12) {
				g2.drawString("a√b", x, textY);
			} else {
				g2.drawString("_", x, textY);
			}
		} else {
			//Check if answer is correct
			if(isCorrect) {
				g2.setColor(correct);
			} else if(isWrong) {
				g2.setColor(wrong);
			} else {
				g2.setColor(Color.white);
			}
			System.out.println("input: "+input2[current]);
			if(question==5) {
				if(upper[current]!=null&&lower[current]==null) {
					g2.drawString(upper[current], x, textY);
					g2.drawString("-", x, textY+16);
					g2.drawString("b", x, textY+32);
				} else if(lower[current]!=null) {
					g2.drawString(upper[current], x, textY);
					g2.drawString("-", x, textY+16);
					g2.drawString(lower[current], x, textY+32);
				}
			} else if(question==11) {
				if(upper[current]!=null&&lower[current]==null) {
					g2.drawString("?("+upper[current]+"x, y)", x, textY);
				} else if(lower[current]!=null) {
					g2.drawString("?("+upper[current]+"x, "+lower[current]+"y)", x, textY);
				}
			} else if(question==12) {
				if(upper[current]!=null&&lower[current]==null) {
					g2.drawString(upper[current]+"√b", x, textY);
				} else if(lower[current]!=null) {
					g2.drawString(upper[current]+"√"+lower[current], x, textY);
				}
			} else {
				g2.drawString(input[question-1], x, textY);
			}
		}
		update();
	}
	
	public void setQuestions() {
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
			if(question==5||question==11||question==12) {
				System.out.println("Current: " + current);
				if(input2[current]==0) {
					upper[current] = input[question-1];
					input2[current]++;
					System.out.println(values_d1[current]);
				} else if(input2[current]==1) {
					lower[current] = input[question-1];
					System.out.println(values_d2[current]);
					if(convert(upper[current])==values_d1[current] &&
						convert(lower[current])==values_d2[current]) {
						gp.playSE(2);
						isCorrect=true;
						if(question==12) {
							opened=true;
							gp.score.addPoints(10);
						} else {
							question++;
							current++;
						}
					} else {
						gp.playSE(3);
						isWrong=true;
					}
					input2[current]=0;
				}
			} else {
				if(convert(input[question-1])==values[question-1]) {
					gp.playSE(2);
					isCorrect=true;
					if(question==12) {
						opened=true;
						Entity door = gp.obj[150][1];
						door.opened = true;
						gp.score.addPoints(10);
					} else {
						question++;
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
		if (opened && spriteCounter > 59) {
			generatePuzzle();
			gp.gameState = GameState.playState;
		}
	}
}
