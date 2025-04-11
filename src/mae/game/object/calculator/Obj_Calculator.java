package mae.game.object.calculator;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import mae.game.EntityType;
import mae.game.GamePanel;
import mae.game.object.Object;
import mae.game.object.SumType;
import mae.game.puzzle.Puzzle;

public class Obj_Calculator extends Object {
	GamePanel gp;
	public final static int objId = 20;
	public boolean open = false;
	public int stage = 1;
	public String input = "";
	public String input2 = "";
	public String input3 = "";
	public double ans;
	public boolean correct = true;

	public Obj_Calculator(GamePanel gp) {
		super(gp);
		this.gp = gp;
		this.enId = objId;
		id = -1;
		type = EntityType.Object;
		getImage();
		if(!opened) {
			image = idle1;
		} else {
			image = idle2;
		}
		solidArea = new Rectangle(0, 0, gp.tileSize*3, gp.tileSize);
		size = gp.tileSize;
		//for(int lock: unlock) {
		//}
	}
	
	public String getInput(int input) {
		if(stage == 1||stage == 2) {
			this.input+=input;
			stage++;
			return this.input;
		} else if (stage == 4){
			input3+=input;
			stage++;
		} else {
			input3+=input;
		}
		return input3;
	}
	
	public String getDigit() {
		if(stage == 2 && !input.contains(".")) {
			if(input == "") {
				input += "0.";
			} else {
				input += ".";
			}
			stage=1;
			return input;
		} else if (stage == 5 && !input3.contains(".")){
			if(input3 == "") {
				input3 += "0.";
			} else {
				input3 += ".";
			}
			stage=5;
		}
		return input3;
	}
	
	public String getSum(String input) {
		if(stage == 2||stage == 3) {
			input2=input;
			switch(input2)	{
			case "+": sum=SumType.PLUS;break;
			case "-": sum=SumType.MINUS;break;
			case "*": sum=SumType.MULTI;break;
			case "/": sum=SumType.DIVIDE;break;
			default: sum=SumType.EQUAL;break;
			}
			stage=4;
		}
		return input2;
	}
	
	public String getStage() {
		if(stage == 1) {
			return input+"_";
		} else if(stage == 2) {
			return input+"_";
		} else if(stage == 3) {
			return input+"_";
		} else if(stage == 4) {
			return input+input2+"_";
		} else if(stage == 5) {
			return input+input2+input3+"_";
		}
		return "="+ans;
	}
	
	public String getAnswer() {
		String problem = input+" "+input3+"=?";
		Puzzle p = new Puzzle(gp, problem, sum);
		if (problem.contains("√")) {
			ans  = p.findAlgebra(1);
		} else {
			ans = p.findMissing();
		}
		return "="+ans;
	}
	
	public void reset() {
		stage = 1;
		input = "";
		input2 = "";
		input3 = "";
	}
	
	@Override
	public void getImage() {
		idle1 = setup("/res/objects/Calculator_Bar", gp.tileSize*3, gp.tileSize);
	}
	
	public void display(Graphics2D g2, int screenX, int screenY) {
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		g2.drawImage(image, tempScreenX, tempScreenY, null);
		g2.setColor(Color.GREEN);
		int length = (int) g2.getFontMetrics().getStringBounds(description, g2).getWidth();
		g2.drawString(getStage(), tempScreenX+gp.tileSize/2, tempScreenY+gp.tileSize/2);
	}
}
