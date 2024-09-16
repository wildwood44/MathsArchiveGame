package mae.game.tile;

import java.awt.image.BufferedImage;

public class Tile {
	public BufferedImage image;
	public boolean collision = false;
	public boolean collisionUp = false;
	public boolean collisionDown = false;
	public boolean collisionLeft = false;
	public boolean collisionRight = false;
	public boolean overlay = false;
}