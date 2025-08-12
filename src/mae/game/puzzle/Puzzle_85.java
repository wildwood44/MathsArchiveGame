package mae.game.puzzle;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import mae.game.GamePanel;
import mae.game.GameState;
import mae.game.npc.Prisoner_85;
import mae.game.object.SumType;

public class Puzzle_85 extends Puzzle {
	GamePanel gp;
	int x, y, z;
	int question = 1;
	double xAxis, yAxis, zAxis;
	String input1 = "", input2 = "", input3 = "";
	double wholeNumber = 0, ans1 = 0, ans2 = 0, ans3 = 0;

	public Puzzle_85(GamePanel gp) {
		super(gp, "", SumType.MULTI, PuzzleType.GEOMATRY);
		this.gp = gp;
		generatePuzzle();
	}
	
	public void generatePuzzle() {
		xAxis = Math.round(Math.random()*10);
		yAxis = Math.round(Math.random()*10);
		zAxis = Math.round(Math.random()*9);
		ans1 = xAxis * yAxis;
		ans2 = xAxis * yAxis * zAxis;
	}

	public void drawPuzzle(Graphics2D g2) {
		x = gp.tileSize * 4;
		y = (int)(gp.tileSize * 1.5);
		BufferedImage cage = setup("/res/objects/cage1", gp.tileSize*4, gp.tileSize*4);
		display(g2, cage, x, y);
		if(question==1) {
			g2.drawString("Find volume of the cage face.", x, y);
		} else if(question==2) {
			g2.drawString("Find volume of the cage.", x, y);
		} else if(question==3) {
			g2.drawString("The cage is cylindrical find he volume.", x, y);
		}
		
		g2.drawString("x: "+xAxis, x-gp.tileSize*2, y);
		g2.drawString("y: "+yAxis, x-gp.tileSize*2, y+gp.tileSize);
		if(question == 2){
			g2.drawString("z: "+zAxis, x-gp.tileSize*2, y+gp.tileSize*2);
		}
		update();
		if(isCorrect) {
			g2.setColor(correct);
		} else if(isWrong) {
			g2.setColor(wrong);
		} else {
			g2.setColor(Color.white);
		}
		y = (int)(gp.tileSize * 4.5);
		g2.setFont(g2.getFont().deriveFont(0, 22.0F));
		int textX = x + 5;
		int textY = y + gp.tileSize/2;
		g2.setStroke(new BasicStroke());
		g2.drawRect(x+gp.tileSize, y, gp.tileSize/2, gp.tileSize);
		g2.drawString(input1, textX+gp.tileSize, textY);
		g2.drawRect(x+(int)(gp.tileSize*1.75), y, gp.tileSize/2, gp.tileSize);
		g2.drawString(input2, textX+(int)(gp.tileSize*1.75), textY);
		g2.drawRect(x+(int)(gp.tileSize*2.5), y, gp.tileSize/2, gp.tileSize);
		g2.drawString(input3, textX+(int)(gp.tileSize*2.5), textY);
		g2.setFont(g2.getFont().deriveFont(0, 12.0F));
		if(question==1) {
			g2.drawString("2", x+(int)(gp.tileSize*3.5), y);
		} else if(question==2) {
			g2.drawString("3", x+(int)(gp.tileSize*3.5), y);
		}
	}

	
	public void display(Graphics2D g2, BufferedImage image, int screenX, int screenY) {
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		g2.drawImage(image, tempScreenX, tempScreenY, null);
	}
	
	public void interact() {
		if(gp.keyH.numberPressed) {
			if(input==1) {
				input++;
				input1 = gp.currentCard+"";
			}else if(input==2) {
				input++;
				input2 = gp.currentCard+"";
			}else if(input==3) {
				input++;
				input3 = gp.currentCard+"";
			}else {
				input=1;
			}
		} if(gp.keyH.enterPressed) {
			wholeNumber = convert(input1+input2+input3);
			if(question==1 && wholeNumber==ans1) {
				gp.playSE(2);
				isCorrect=true;
				question++;
			} else if(question==2 && wholeNumber==ans2) {
				gp.playSE(2);
				isCorrect=true;
				opened = true;
				gp.score.addPoints(5);
				if(gp.npc[gp.currentMap.getId()][0] == null) {
					gp.npc[gp.currentMap.getId()][0] = new Prisoner_85(gp);
					gp.npc[gp.currentMap.getId()][0].worldX = (int)(gp.tileSize*12.5);
					gp.npc[gp.currentMap.getId()][0].worldY = (gp.tileSize*3)-1;
					gp.npc[gp.currentMap.getId()][0].direction = direction;
					gp.score.addPoints(10);
				}
			} else {
				gp.playSE(3);
				isWrong=true;
			}
			input1="";
			input2="";
			input3="";
			input=1;
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
