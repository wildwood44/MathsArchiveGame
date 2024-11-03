package mae.game.items;

import mae.game.Entity;
import mae.game.GamePanel;

public class Item extends Entity {

	public Item(GamePanel gp) {
		super(gp);
		this.gp = gp;
		description = "";
	}
	
	public void use() {}
}
