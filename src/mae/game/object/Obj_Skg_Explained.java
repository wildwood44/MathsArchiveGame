package mae.game.object;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import mae.game.EntityType;
import mae.game.GamePanel;

public class Obj_Skg_Explained extends Object {
	public final static int objId = 6;

	public Obj_Skg_Explained(GamePanel gp) {
		super(gp);
		this.gp = gp;
		enId = objId;
		id = -1;
		width = gp.tileSize*4;
		height = gp.tileSize*2;
		type = EntityType.Object;
		solidArea = new Rectangle(0, 0, width, height);
	}

	public void interact() {
	}
	
	@Override
	public void getImage() {
		idle1 = setup("/res/objects/skg_explained_"+sum.name(), gp.tileSize*4, gp.tileSize*2);
		image = idle1;
	}
	
	public void display(Graphics2D g2, int screenX, int screenY) {
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		g2.drawImage(image, tempScreenX, tempScreenY, null);
	}
}
