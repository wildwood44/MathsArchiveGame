package mae.game.cutscenes;

import mae.game.Entity;
import mae.game.GamePanel;

public class Cutscene extends Entity {
	GamePanel gp;

	public Cutscene(GamePanel gp) {
		super(gp);
		this.gp = gp;
	}

}