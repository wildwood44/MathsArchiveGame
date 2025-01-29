package mae.game.object;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import mae.game.EntityType;
import mae.game.GamePanel;

public class Obj_Coming_Soon extends Object {
	GamePanel gp;
	public final static int objId = 2;
	public boolean open = false;
	public int input = 0;
	public boolean correct = true;

	public Obj_Coming_Soon(GamePanel gp) {
		super(gp);
		this.gp = gp;
		this.enId = objId;
		id = -1;
		type = EntityType.Object;
		description = "Coming Soon";
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
	
	@Override
	public void getImage() {
		idle1 = setup("/res/objects/Obj_Coming_Soon", gp.tileSize*3, gp.tileSize);
	}
	
	public void display(Graphics2D g2, int screenX, int screenY) {
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		g2.drawImage(image, tempScreenX, tempScreenY, null);
		g2.setColor(Color.BLACK);
		int length = (int) g2.getFontMetrics().getStringBounds(description, g2).getWidth();
		g2.drawString(description, tempScreenX+gp.tileSize/2, tempScreenY+gp.tileSize/2);
	}
}
