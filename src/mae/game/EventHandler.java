package mae.game;

import mae.game.cutscenes.CutsceneManager;
import mae.game.tile.Map;
import mae.game.tile.TileManager;

public class EventHandler {
	GamePanel gp;
	EventRect[][][] eventRect;
	Entity eventMaster;
	int previousEventX, previousEventY;
	int eventRectDefaultX;
	int eventRectDefaultY;
	boolean canTouchEvent = true;
	boolean cutsceneActive = false;

	public EventHandler(GamePanel gp) {
		this.gp = gp;
		eventMaster = new Entity(gp);
		eventRect = new EventRect[gp.maxMap][gp.currentMap.getMaxWorldCol()][gp.currentMap.getMaxWorldRow()];//new Rectangle();
		int map = 0;
		int col = 0;
		int row = 0;
		while(map < gp.maxMap && col < gp.currentMap.getMaxWorldCol() && row < gp.currentMap.getMaxWorldRow()) {
			eventRect[map][col][row] = new EventRect();
			eventRect[map][col][row].x = 25;
			eventRect[map][col][row].y = 25;
			eventRect[map][col][row].width = 32;
			eventRect[map][col][row].height = 32;
			eventRect[map][col][row].eventRectDefaultX = this.eventRect[map][col][row].x;
			eventRect[map][col][row].eventRectDefaultY = this.eventRect[map][col][row].y;
			col++;
			if(col == gp.currentMap.getMaxWorldCol()) {
				col = 0;
				row++;
				if(row == gp.currentMap.getMaxWorldRow()) {
					row = 0;
					map++;
				}
			}
		}
	}

	public void checkEvent() {
		int xDistance = Math.abs(gp.player.worldX - previousEventX);
		int yDistance = Math.abs(gp.player.worldY - previousEventY);
		int distance = Math.max(xDistance, yDistance);
		if(distance > gp.tileSize) {
			canTouchEvent = true;
		}
		if(canTouchEvent) {
			if(gp.currentMap.getId() == 0) {
			}
		}
	}

	public void checkCutscene() {
		if(gp.s.gameStart) {
			cutscene(1);
		} else if(gp.s.credits) {
			cutscene(2);
		}
	}

	public void cutscene(int read) {
		gp.gameState = GameState.cutsceneState;
		CutsceneManager cm = gp.csManager;
		cm.sceneNum = read;
	}

	public boolean hit(int map, int col, int row, String reqDirection) {
		boolean hit = false;
		if(map == gp.currentMap.getId()) {
			//try {
				gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
				gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
				eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
				eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;
				if(gp.player.solidArea.intersects(eventRect[map][col][row]) && !eventRect[map][col][row].eventDone) {
					if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
						hit = true;
						previousEventX = gp.player.worldX;
						previousEventY = gp.player.worldY;
					}
				}
				gp.player.solidArea.x = gp.player.solidAreaDefaultX;
				gp.player.solidArea.y = gp.player.solidAreaDefaultY;
				eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
				eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
			//} catch(ArrayIndexOutOfBoundsException e) {
			//	e.printStackTrace();
			//}
		}
		return hit;
	}
	
	public boolean hitRow(int map, int row, String reqDirection) {
		boolean hit = false;
		if(map == gp.currentMap.getId()) {
			//try {
				gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
				gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
				int col = gp.player.solidArea.y/gp.tileSize;
				eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
				eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;
				if(gp.player.solidArea.y/gp.tileSize == eventRect[map][col][row].y/gp.tileSize && !eventRect[map][col][row].eventDone) {
					if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
						hit = true;
						previousEventX = gp.player.worldX;
						previousEventY = gp.player.worldY;
					}
				}
				gp.player.solidArea.x = gp.player.solidAreaDefaultX;
				gp.player.solidArea.y = gp.player.solidAreaDefaultY;
				eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
				eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
			//} catch(ArrayIndexOutOfBoundsException e) {
			//	e.printStackTrace();
			//}
		}
		return hit;
	}

	public void teleport(Map map, int col, int row) {
		gp.currentMap = map;
		gp.tileM = new TileManager(gp);
		gp.eHandler = new EventHandler(gp);
		gp.player.worldX = gp.tileSize * col;
		gp.player.worldY = gp.tileSize * row;
		previousEventX = gp.player.worldX;
		previousEventY = gp.player.worldY;
		canTouchEvent = false;
	}
}