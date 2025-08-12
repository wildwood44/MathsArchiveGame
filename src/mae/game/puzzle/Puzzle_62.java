package mae.game.puzzle;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import mae.game.GamePanel;
import mae.game.GameState;
import mae.game.object.SumType;
import mae.game.object.Obj_Arrow;
import mae.game.object.Obj_Oasis;
import mae.game.object.Obj_PathFinder;
import mae.game.object.Object;

public class Puzzle_62 extends Puzzle {
	GamePanel gp;
	int x, y, z;
	int question = 1;
	int moveCounter = 0;
	String distanceX = "", distanceY = "", input2 = "";
	double xEndPoint, yEndPoint;
	double wholeNumber = 0, startX = 0, startY = 0;
	Object oasis, pathfinder, arrow; 
	boolean transition = false;

	public Puzzle_62(GamePanel gp) {
		super(gp, "Transform()+(down(),right())=(a,b)",2, SumType.PLUS, PuzzleType.GRAPH);
		this.gp = gp;
		generatePuzzle();
	}
	
	public void generatePuzzle() {
		oasis = new Obj_Oasis(gp);
		oasis.worldX = (int)(Math.round(Math.random()*6)+3);
		oasis.worldY = (int)(Math.round(Math.random()*4)+1);
		pathfinder = new Obj_PathFinder(gp);
		pathfinder.worldX = (int)(Math.round(Math.random()*6)+3);
		while(pathfinder.worldX==oasis.worldX) {
			pathfinder.worldX = (int)(Math.round(Math.random()*6)+3);
		}
		startX = pathfinder.worldX;
		pathfinder.worldY = (int)(Math.round(Math.random()*4)+1);
		while(pathfinder.worldY==oasis.worldY) {
			pathfinder.worldY = (int)(Math.round(Math.random()*4)+1);
		}
		startY = pathfinder.worldY;
		arrow = new Obj_Arrow(gp);
		arrow.worldX = gp.tileSize + 8;
		arrow.worldY = gp.tileSize*3;
	}

	public void drawPuzzle(Graphics2D g2) {
		DecimalFormat format = new DecimalFormat("0.#");
		x = gp.tileSize * 2;
		y = (int)(gp.tileSize/2);
		BufferedImage sandbox = setup("/res/objects/img_sandbox", gp.tileSize*8, gp.tileSize*6);
		display(g2, sandbox, x, y);
    	for(double i = 0.5; i < 8; i++) {
			g2.setStroke(new BasicStroke(1));
			g2.setColor(Color.black);
    		g2.drawLine(x, (int)(i*gp.tileSize), gp.tileSize*10, (int)(i*gp.tileSize));
    		g2.drawString(format.format(i-0.5), gp.tileSize*9, (int)(i*gp.tileSize));
    	}
    	for(double i = 1; i < 10; i++) {
			g2.setStroke(new BasicStroke(1));
			g2.setColor(Color.black);
    		g2.drawLine((int)(i*gp.tileSize), y, (int)(i*gp.tileSize), gp.tileSize*7);
    		g2.drawString(format.format(i-2), (int)(i*gp.tileSize), gp.tileSize*6);
    	}
		//g2.setColor(Color.red);
		//g2.drawRect((int)(gp.tileSize*3), (int)(gp.tileSize*0.5), gp.tileSize*8, gp.tileSize*5);
		g2.setColor(Color.white);
		g2.drawString("Translate to oasis.", x, y+16);
		oasis.display(g2,oasis.worldX*gp.tileSize,oasis.worldY*gp.tileSize);
		pathfinder.display(g2, pathfinder.worldX*gp.tileSize, pathfinder.worldY*gp.tileSize);
		arrow.display(g2, arrow.worldX, arrow.worldY);
		if(isCorrect) {
			g2.setColor(correct);
		} else if(isWrong) {
			g2.setColor(wrong);
		} else {
			g2.setColor(Color.white);
		}
		g2.drawString("x: "+ distanceX, gp.tileSize + 8, gp.tileSize*5);
		g2.drawString("y: "+ distanceY, gp.tileSize + 8, gp.tileSize*6);
		update();
	}

	
	public void display(Graphics2D g2, BufferedImage image, int screenX, int screenY) {
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		g2.drawImage(image, tempScreenX, tempScreenY, null);
	}
	
	public void interact() {
		//Change direction
		if(gp.keyH.leftPressed || gp.keyH.rightPressed) {
			arrow.interact();
		//Choose direction
		} else if(gp.keyH.numberPressed) {
			if(xEndPoint==0||yEndPoint==0||
				xEndPoint > 9 || yEndPoint > 6 ||
				xEndPoint < 3 || yEndPoint < 1) {
				input2 = gp.currentCard+"";
				if(arrow.spriteNum==1) {
					yEndPoint = pathfinder.worldY - convert(input2);
					distanceY = "-"+input2;
				} else if(arrow.spriteNum==2) {
					xEndPoint = pathfinder.worldX + convert(input2);
					distanceX = input2;
				} else if(arrow.spriteNum==3) {
					yEndPoint = pathfinder.worldY + convert(input2);
					distanceY = input2;
				} else {
					xEndPoint = pathfinder.worldX - convert(input2);
					distanceX = "-"+input2;
				}
			} if((xEndPoint > 9 || xEndPoint < 3 || 
				yEndPoint > 6 || yEndPoint < 1)) {
				//gp.playSE(3);
				//isWrong=true;
			} else if (pathfinder.worldX==oasis.worldX &&
				pathfinder.worldY==oasis.worldY) {
				gp.playSE(2);
				isCorrect=true;
				gp.score.addPoints(5);
			} else {
				transition = true;
			}
			
		}
		gp.keyH.leftPressed = false;
		gp.keyH.rightPressed = false;
		gp.keyH.enterPressed = false;
		gp.keyH.upPressed = false;
		gp.keyH.downPressed = false;
		gp.keyH.numberPressed = false;
	}
	
	public void update() {
		super.update();
		if (transition) {
			//Move pathfinder
			moveCounter++;
			if(moveCounter % 5 == 0) {
				if(pathfinder.worldX > xEndPoint) {
					pathfinder.worldX--;
				} else if(pathfinder.worldX < xEndPoint) {
					pathfinder.worldX++;
				} else if(pathfinder.worldY > yEndPoint) {
					pathfinder.worldY--;
				} else if(pathfinder.worldY < yEndPoint) {
					pathfinder.worldY++;
				} else {
					transition=false;
					if (pathfinder.worldX==oasis.worldX &&
						pathfinder.worldY==oasis.worldY) {
						question++;
						gp.playSE(2);
						isCorrect=true;
						opened = true;
					} else {
						gp.playSE(3);
						isWrong=true;
					}
					moveCounter=0;
					xEndPoint=0;
					yEndPoint=0;
					distanceX="";
					distanceY="";
					pathfinder.worldX = (int) startX;
					pathfinder.worldY = (int) startY;
					input2="";
					input=1;
				}
				moveCounter++;
			}
		}
		//Exit puzzle and create new one
		if (opened && question>1 && spriteCounter > 59) {
			question=1;
			generatePuzzle();
			gp.gameState = GameState.playState;
		}
	}
}
