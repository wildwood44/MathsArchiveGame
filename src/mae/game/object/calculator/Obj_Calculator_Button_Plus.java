package mae.game.object.calculator;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import mae.game.EntityType;
import mae.game.GamePanel;
import mae.game.object.Object;

public class Obj_Calculator_Button_Plus extends Object {
	GamePanel gp;
	public final static int objId = 24;
	public int btnId;
	private String btnTxt;
	public boolean open = false;
	public int input = 0;
	public boolean correct = true;

	public Obj_Calculator_Button_Plus(GamePanel gp) {
		super(gp);
		this.gp = gp;
		this.enId = objId;
		id = -1;
		type = EntityType.Object;
		getImage();
		if(!isSelected) {
			image = idle2;
		} else {
			image = idle1;
		}
		solidArea = new Rectangle(0, 0, gp.tileSize/2, gp.tileSize/2);
		size = gp.tileSize/2;
		x = gp.tileSize/2;
		solidAreaDefaultX = x;
		btnId = 3;
		btnTxt = "+";
	}
	
	public void setButton(int id, String value) {
		btnId = id;
		btnTxt = value;
	}
	
	public void setButton(int id, int value) {
		btnId = id;
		input = value;
	}

	public void interact() {
		Obj_Calculator cal = (Obj_Calculator) gp.obj[20][1];
		if(cal.stage == 0) {
			cal.reset();
		} else if(btnId==3) {
			cal.getSum(btnTxt);
		}
		gp.keyH.enterPressed = false;
		gp.keyH.upPressed = false;
		gp.keyH.downPressed = false;
	}
	
	@Override
	public void getImage() {
		idle1 = setup("/res/objects/Obj_Button", gp.tileSize/2, gp.tileSize/2);
		idle2 = setup("/res/objects/Obj_Button_Selected", gp.tileSize/2, gp.tileSize/2);
	}
	
	public void display(Graphics2D g2, int screenX, int screenY) {
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		if(!isSelected) {
			image = idle1;
		} else {
			image = idle2;
		}
		g2.drawImage(image, tempScreenX, tempScreenY, null);
		g2.setColor(Color.BLACK);
		int length = (int) g2.getFontMetrics().getStringBounds(description, g2).getWidth();
		if(btnId == 1) {
			g2.drawString(""+input, tempScreenX+10, tempScreenY+gp.tileSize/3);
		} else {
			g2.drawString(btnTxt, tempScreenX+10, tempScreenY+gp.tileSize/3);
		}
	}
}
