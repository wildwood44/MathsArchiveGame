package mae.game.tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import mae.game.GamePanel;

public class TileManager {
	GamePanel gp;
	public Tile[] tile;
	public int[][][] mapTileNum;

	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[260];
		mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
		getTileImage();
		for (int i = 0; i < 12; i++) {
			loadMap("/res/maps/map01",i);
		}
		for (int i = 12; i < 156; i++) {
			loadMap("/res/maps/map02",i);
		}
	}

	public void getTileImage() {
			setup(0, "lab_tile-top_left", true);
			setup(1, "lab_tile-top", true);
			setup(2, "lab_tile-top_right", true);
			setup(3, "lab_tile-left", true);
			setup(4, "lab_tile-centre", false);
			setup(5, "lab_tile-right", true);
			setup(6, "lab_tile-bottom_left", true);
			setup(7, "lab_tile-bottom", false);
			setup(8, "lab_tile-bottom_right", true);
			setup(9, "lab_tile-light", true);
	}
	
	public void setup(int index, String imageName, boolean collision) {
		UtilityTool uTool = new UtilityTool();
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/"+imageName+".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setup(int index, String imageName, boolean collision, boolean nettles) {
		UtilityTool uTool = new UtilityTool();
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/"+imageName+".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadMap(String filePath, int map) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			int col = 0;
			int row = 0;

			while (col < gp.maps[map].getMaxWorldCol() && row < gp.currentMap.getMaxWorldRow()) {
				if (col >= gp.maps[map].getMaxWorldCol()) {
					break;
				}
				if (row >= gp.maps[map].getMaxWorldRow()) {
					break;
				}
				String line = br.readLine();
				while (true) {
					if (col >= gp.maps[map].getMaxWorldCol()) {
						if (col == gp.maps[map].getMaxWorldCol()) {
							col = 0;
							++row;
						}
						break;
					}
					String[] numbers = line.split(" ");
					int num = Integer.parseInt(numbers[col]);
					mapTileNum[map][col][row] = num;
					col++;
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g2) {
		int worldCol = 0;
		int worldRow = 0;

		while (true) {
			if (worldCol >= gp.currentMap.getMaxWorldCol()) {
				break;
			}

			if (worldRow >= gp.currentMap.getMaxWorldRow()) {
				break;
			}
			int tileNum = mapTileNum[gp.currentMap.getId()][worldCol][worldRow];
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			if (gp.player.screenX > gp.player.worldX) {
				screenX = worldX;
			}

			if (gp.player.screenY > gp.player.worldY) {
				screenY = worldY;
			}

			int rightOffset = gp.screenWidth - gp.player.screenX;
			if (rightOffset > gp.currentMap.getWorldWidth() - gp.player.worldX) {
				screenX = gp.screenWidth - (gp.currentMap.getWorldWidth() - worldX);
			}

			int bottomOffset = gp.screenHeight - gp.player.screenY;
			if (bottomOffset > gp.currentMap.getWorldHeight() - gp.player.worldY) {
				screenY = gp.screenHeight - (gp.currentMap.getWorldHeight() - worldY);
			}

			if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
				worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
				worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
				worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
				g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			} 
			else if (gp.player.screenX > gp.player.worldX ||
				gp.player.screenY > gp.player.worldY ||
				rightOffset > gp.currentMap.getWorldWidth() - gp.player.worldX ||
				bottomOffset > gp.currentMap.getWorldHeight() - gp.player.worldY) {
				g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			}
			worldCol++;
			if (worldCol == gp.currentMap.getMaxWorldCol()) {
				worldCol = 0;
				worldRow++;
			}
			
		}

	}
}