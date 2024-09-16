package mae.game.object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import mae.game.EntityType;
import mae.game.GamePanel;

public class Obj_Skg_Explained extends Object {
	SumType sum;
	int slot1 = -1, slot2 = -1;

	public Obj_Skg_Explained(GamePanel gp, SumType sum) {
		super(gp);
		this.gp = gp;
		this.sum = sum;
		id = -1;
		type = EntityType.Object;
		image = setup("/res/objects/skg_explained_"+sum.name(), gp.tileSize*4, gp.tileSize*2);

		getImage(image);
		solidArea = new Rectangle(0, 0, gp.tileSize*4, gp.tileSize*2);
		int size = gp.tileSize;
	}

	public void interact() {
	}
	
	@Override
	public void getImage(BufferedImage image) {
		idle1 = setup("/res/objects/skg_explained_"+sum.name(), gp.tileSize*4, gp.tileSize*2);

	}
	
	public void display(Graphics2D g2, int screenX, int screenY) {
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		g2.drawImage(image, tempScreenX, tempScreenY, null);
	}
}
