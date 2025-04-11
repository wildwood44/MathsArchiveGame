package mae.game.tile;

import java.awt.image.BufferedImage;

import mae.game.GamePanel;

public class Map {
	GamePanel gp;
	BufferedImage worldMap[];
	private MapType mptype;
	private int id;
	private int floor;
	private String name;
	private int maxWorldCol;
	private int maxWorldRow;
	private int worldWidth;
	private int worldHeight;
	public boolean miniMapOn = false;
	
	public Map(GamePanel gp, MapType mptype, int id, int floor, String name, int maxWorldCol, int maxWorldRow) {
		this.gp = gp;
		this.mptype = mptype;
		this.id = id;
		this.floor = floor;
		this.name = name;
		this.maxWorldCol = maxWorldCol;
		this.maxWorldRow = maxWorldRow;
		worldWidth = this.maxWorldCol * gp.tileSize;
		worldHeight = 3 * gp.tileSize;
	}

	public int getId() {
		return id;
	}

	public int getFloor() {
		return floor;
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
