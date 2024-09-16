package mae.game.tile;

import java.awt.image.BufferedImage;

import mae.game.GamePanel;

public class Map {
	GamePanel gp;
	BufferedImage worldMap[];
	private MapType mptype;
	private int id;
	private String name;
	private int maxWorldCol;
	private int maxWorldRow;
	private int worldWidth;
	private int worldHeight;
	public boolean miniMapOn = false;
	
	public Map(GamePanel gp, MapType mptype, int id, String name, int maxWorldCol, int maxWorldRow) {
		this.gp = gp;
		this.mptype = mptype;
		this.id = id;
		this.name = name;
		this.maxWorldCol = maxWorldCol;
		this.maxWorldRow = maxWorldRow;
		worldWidth = this.maxWorldCol * gp.tileSize;
		worldHeight = this.maxWorldRow * gp.tileSize;
	}

	public int getId() {
		return id;
	}

	public int getMaxWorldCol() {
		return maxWorldCol;
	}

	public int getMaxWorldRow() {
		return maxWorldRow;
	}

	public int getWorldWidth() {
		return worldWidth;
	}

	public int getWorldHeight() {
		return worldHeight;
	}

	public String getName() {
		return name;
	}

	public MapType getMapType() {
		return mptype;
	}
	
}
