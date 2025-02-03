package mae.game.object;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import mae.game.EntityType;
import mae.game.GamePanel;

public class Obj_Map extends Object {
	GamePanel gp;
	public final static int objId = 14;
	public boolean open = false;
	public int input = 0;
	public boolean correct = true;

	public Obj_Map(GamePanel gp) {
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
		idle1 = setup("/res/objects/Obj_Map", gp.tileSize*3, gp.tileSize*2);
	}
	
	public void display(Graphics2D g2, int screenX, int screenY) {
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		g2.drawImage(image, tempScreenX, tempScreenY, null);
	}
}
