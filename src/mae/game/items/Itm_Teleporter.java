package mae.game.items;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import mae.game.GamePanel;

public class Itm_Teleporter extends Item {
	GamePanel gp;
	private int id = 1;

	public Itm_Teleporter(GamePanel gp) {
		super(gp);
		this.gp = gp;
		this.enId = id;
		getImage();
		image = idle1;
		x = gp.screenWidth/3;
		y = (4 * gp.tileSize) + 5;
	}
	
	public void getImage() {
		idle1 = setup(("/res/objects/teleporter"), gp.tileSize*2, gp.tileSize*2);
	}

	public void draw(Graphics2D g2) {
		image = idle1;
		g2.drawImage(image, x, y, null);
        g2.setComposite(AlphaComposite.SrcOver.derive(1f));
    	
	}
	
	public void use() {
		isSelected = true;
	}
}
