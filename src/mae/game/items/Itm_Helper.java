package mae.game.items;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import mae.game.GamePanel;
import mae.game.GameState;

public class Itm_Helper extends Item {
	GamePanel gp;
	public final static int id = 0;
	//public final static itemId = 1;
	public Itm_Helper(GamePanel gp) {
		super(gp);
		this.gp = gp;
		this.enId = id;
		getImage();
		setDialogue();
		image = idle1;
		x = gp.screenWidth/3;
		y = (4 * gp.tileSize) + 5;
	}
	
	public void getImage() {
		idle1 = setup(("/res/objects/helper1"), gp.tileSize*2, gp.tileSize*2);
		idle2 = setup(("/res/objects/helper2"), gp.tileSize*2, gp.tileSize*2);
	}

	public void draw(Graphics2D g2) {
		if(spriteNum==1) {
			image = idle1;
		}if(spriteNum==2) {
			image = idle2;
		}
		g2.drawImage(image, x, y, null);
        g2.setComposite(AlphaComposite.SrcOver.derive(1f));
    	//DRAW SPEECH BUBBLE
		if(gp.gameState == GameState.talkingState) {
			//update();
			drawSpeech(g2, x - gp.tileSize/2, y - gp.tileSize*4);
		}
	}

	public void update() {
		collisionOn = false;
		if(isSelected) {
			spriteCounter++;
			if (spriteCounter > 10) {
				if(spriteNum == 1) {
					spriteNum = 2;
				} else if (spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter=0;
			}
		}
	}
	public void drawSpeechBubble(Graphics2D g2, int x, int y) {
		BufferedImage sb_tl = setup("/res/speechbubble/speech-bubble_large", gp.tileSize*4, gp.tileSize*4);
		g2.drawImage(sb_tl, x-gp.tileSize/2, y, null);
	}
	public void drawSpeech(Graphics2D g2, int x, int y) {			
		int textY = y + 48;
		int length, textX;
		//Draw box
		drawSpeechBubble(g2,x,y);
		g2.setColor(Color.WHITE);
		g2.setFont(g2.getFont().deriveFont(1, 12.0F));
		for (String line : gp.ui.breakLines(dialogue[dialogueSet][dialogueIndex],20," ")) {
			length = (int) g2.getFontMetrics().getStringBounds(line, g2).getWidth();
			textX = x + (int)(gp.tileSize*1.5) - length/2;
			g2.drawString(line, textX, textY);
			textY += 20;
		}
	}
	public void setDialogue() {
		dialogue[0][0] = "Addition is when you bring too amounts together.";
		dialogue[0][1] = "For example: If you have one me and another me you get two me's.";
		dialogue[1][0] = "A quick way of adding multiple of the same value is by multiplication.";
		dialogue[1][1] = "For example  4+4+4+4=16 can be simplified to 4*4=16.";
		dialogue[1][2] = "The reverse of this is splitting a number by into smaller numbers through division.";
		dialogue[1][3] = "For example 12 can be split into 3 4s or 4 3s.";
		dialogue[2][0] = "Did you know there are numbers between whole numbers.";
		dialogue[2][1] = "I am talking about decimals.";
		dialogue[2][2] = "For instance in between 4 and 5 you can have 4.5";
		dialogue[2][3] = "Which a simple way of saying four and a half.";
		dialogue[2][4] = "Additionally between 4 and 4.5 you can have 4.25 and so on.";
		dialogue[2][5] = "You can also have numbers less than 0 called negatives.";
		dialogue[2][6] = "They are in reverse order from 0, -1, -2, etc.";
		dialogue[3][0] = "The % symbol stands for percentage.";
		dialogue[3][1] = "To find the percentage of a number you must...";
		dialogue[3][2] = "Divide the whole number by 10 to get 10% or by 100 for 100%.";
		dialogue[3][3] = "You can then times the divide number to find the total percentage.";
		dialogue[3][4] = "Example: 40% of 120 = 120/10=12*4 = 48.";
		dialogue[3][5] = "A quicker way if by split the whole number into 50% or 25%.";
		dialogue[3][6] = "You can get those by simply halving add adding from there.";
		dialogue[3][7] = "Example: 65% of 120 = 120/10=12*4=48 and 120/4=30 = 78.";
		dialogue[4][0] = "To add or subtract fractions you first ->";
		dialogue[4][1] = "Multiple both fractions to the lowest commonly shared multiple of the denominator.";
		dialogue[4][2] = "Denominator = lower number.";
		dialogue[4][3] = "Then add or subtract the numerators.";
		dialogue[4][4] = "Numerator = upper number.";
		dialogue[4][5] = "For multiplication you multiply the numerators and denominators.";
		dialogue[4][6] = "For division you flip the second values numerator and denominator and multiply.";
		dialogue[4][7] = "Finally divide both the numerator and denominator of the resulting factors by the greatest common factor.";
		dialogue[5][0] = "Graphs use x (left-right) and y (up-down) coordinates in an axis.";
		dialogue[5][1] = "They start with thick lines where 0,0 key points cross.";
		dialogue[5][2] = "Each point going right and up increase the x and y axis by 1.";
		dialogue[5][3] = "And if descends in the opposite directions.";
		dialogue[5][4] = "From a starting point you can transform the axis.";
		dialogue[5][5] = "Transform - Move from the original point.";
		dialogue[5][6] = "Reflect - Place new points in parallel with line in graph.";
		dialogue[5][7] = "Rotate - Rotate points from distance from centre point.";
		dialogue[5][8] = "Enlargement = Increase (or decrease) distance between points.";
		dialogue[6][0] = "There are many types of measurement conversion.";
		dialogue[6][1] = "Such as perimeter, weight, distance, time, currency, etc.";
		dialogue[6][2] = "For example 1m = 100cm, 1kg = 1000g, 60 minutes = 1 hour, etc.";
		dialogue[6][3] = "You can calculate speed(mph) by dividing distance/time.";
		dialogue[7][0] = "Geometry is the calculation of the lines, angles and surface of a shape.";
		dialogue[7][1] = "Perimeter - The length of each face of a shape.";
		dialogue[7][2] = "Angles - Measure up to 360 degrees for a full turn, 180 for a half turn and 90 for a right angle.";
		dialogue[7][3] = "The number of angles in a square add up to a full turn but a triangle only has enough for a half turn.";
		dialogue[7][4] = "Surface area - You can calculate the area of the rectangle by calculating length x width.";
		dialogue[7][5] = "A triangle is the same but halfed.";
		dialogue[7][6] = "Other shape change be calculated by splitting the shape into smaller rectangles and triangles.";
		dialogue[7][7] = "The resulting surface area is squared.";
		dialogue[8][0] = "There are four types of averages. These are:";
		dialogue[8][1] = "Medium - The middle number.";
		dialogue[8][2] = "Is there is a list of even number then the medium is the halfway point between the middle two numbers.";
		dialogue[8][3] = "Mean - Add up all the numbers then divide by how many numbers there are.";
		dialogue[8][4] = "Mode - Most frequent number.";
		dialogue[8][5] = "Range - The range between that largest and smallest number.";
		dialogue[9][0] = "To calculate the probability you need to figure out how many value in a list match a certain criteria.";
		dialogue[9][1] = "For example in the sequence XVII you get 50% for I and 25% for X or V.";
		dialogue[10][0] = "Algebra is when letters are used in place of numbers";
		dialogue[10][1] = "These letters can be any number so to simplify expressions such as xy(x * y) are used.";
		dialogue[10][2] = "Likewise with x^3 (x*x*x)";
		dialogue[11][0] = "To simplify surds you need to simplify the expression to it simplest form.";
		dialogue[11][1] = "The √ symbol is used to find the square root. E.g √16 = 4.";
		dialogue[11][2] = "A number in front of this makes it to the power of that number. E.g 3√64 = 4.";
		dialogue[11][3] = "If this is not possible then divide the value into two smaller number based on the smallest possible factor.";
		dialogue[11][4] = "Eg:√21 = √3*7. This the result when there are no square factors.";
		dialogue[11][5] = "Otherwise you can go further with the following: √8 = √4*2 = √4*√2 = 2*√2 = 2√2.";
		//dialogue[11][0] = "https://www.wikihow.com/Simplify-Radical-Expressions https://www.thenational.academy/teachers/programmes/maths-secondary-ks4-higher/units/surds/lessons/simplifying-surds#slide-deck";
	}
	
	public void use() {
		if(opened) {
			if(dialogue[gp.currentMap.getFloor()][dialogueIndex]!=null) {
				startDialogue(this, gp.currentMap.getFloor());
			}
		}
		
	}
}
