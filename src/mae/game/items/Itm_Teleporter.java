package mae.game.items;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;

import mae.game.GamePanel;
import mae.game.GameState;

public class Itm_Teleporter extends Item {
	GamePanel gp;
	public final static int id = 1;

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
	
	public int getRoom(int x, int y) {
		if(x == 0) {
			if(y==0) {
				return 1;
			}else if(y==1) {
				return 4;
			}else if(y==2) {
				return 9;
			}
		} else if(x == 1) {
			if(y==0) {
				return 16;
			}else if(y==1) {
				return 25;
			}else if(y==2) {
				return 36;
			}
		} else if(x == 2) {
			if(y==0) {
				return 49;
			}else if(y==1) {
				return 64;
			}else if(y==2) {
				return 81;
			}
		} else if(x == 3) {
			if(y==0) {
				return 100;
			}else if(y==1) {
				return 121;
			}else if(y==2) {
				return 144;
			}
		}
		return 1;
	}

	public void draw(Graphics2D g2) {
		image = idle1;
		g2.drawImage(image, x, y, null);
        g2.setComposite(AlphaComposite.SrcOver.derive(1f));
    	
	}
	
	public void use() {
		isSelected = true;
		gp.gameState=GameState.fastTravelState;
	}
}
