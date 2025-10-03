package mae.game.object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.math.BigDecimal;

import mae.game.EntityType;
import mae.game.GamePanel;

public class Obj_Pointer_Left extends Object {
	public final static int objId = 32;
	public BigDecimal value;

	public Obj_Pointer_Left(GamePanel gp, BigDecimal desc) {
		super(gp);
		this.gp = gp;
		enId = objId;
		id = -1;
		width = gp.tileSize;
		height = gp.tileSize;
		type = EntityType.Object;
		solidArea = new Rectangle(0, 0, width, height);
		value = desc;
		getImage();
	}
	
	@Override
	public void getImage() {
		idle1 = setup("/res/objects/pointer_left", width, height);
		image = idle1;
	}
	
	public void display(Graphics2D g2, int screenX, int screenY) {
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		g2.drawString(value.toString(), tempScreenX+gp.tileSize, tempScreenY+(gp.tileSize/2));
		g2.drawImage(image, tempScreenX, tempScreenY, null);
	}
	
	public void increaseValue () {
		System.out.println("Value: "+value);
		value = value.add(new BigDecimal(1));
	}
	
	public void setValue (BigDecimal value) {
		this.value = value;
	}
}
