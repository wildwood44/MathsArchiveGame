package mae.game.npc;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import mae.game.GamePanel;

public class Prisoner_85 extends NPC {
	GamePanel gp;
	Graphics2D g2;
	public final static int npcId = 85;

	public Prisoner_85(GamePanel gp) {
		super(gp);
		this.gp = gp;
		id = npcId;
		image = idle1;
		width = gp.tileSize;
		height = gp.tileSize;
		getImage();
		System.out.println(width + " " + height);
	}

	public BufferedImage getSpriteSheet() {
		BufferedImage sprite = null;

		try {
			sprite = ImageIO.read(getClass().getResourceAsStream("/res/npc/spr_firecracker1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sprite;
	}

	public BufferedImage getPlayerImage(int xGrid, int yGrid) {
		if (image == null) {
			image = getSpriteSheet();
		}
		System.out.println(xGrid * gp.tileSize +" "+ yGrid * gp.tileSize +" "+width + " " + height);
		return image.getSubimage(xGrid * gp.tileSize, yGrid * gp.tileSize, gp.tileSize, gp.tileSize);
	}
	
	public void getImage() {
		idle1 = getPlayerImage(0, 0);
		idle2 = getPlayerImage(0, 1);
		left1 = getPlayerImage(1, 0);
		left2 = getPlayerImage(1, 1);
		right1 = getPlayerImage(2, 0);
		right2 = getPlayerImage(2, 1);

	}

}
