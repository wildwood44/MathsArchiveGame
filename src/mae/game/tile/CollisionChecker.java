package mae.game.tile;

import mae.game.Entity;
import mae.game.GamePanel;

public class CollisionChecker {
	GamePanel gp;
	//private int collisionCount = 0;

	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}

	public void checkTile(Entity entity) {
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

		int entityLeftCol = entityLeftWorldX / gp.tileSize;
		int entityRightCol = entityRightWorldX / gp.tileSize;
		int entityTopRow = entityTopWorldY / gp.tileSize;
		int entityBottomRow = entityBottomWorldY / gp.tileSize;
		int tileNum1, tileNum2;
		switch (entity.direction) {
			case "up":
					entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
					tileNum1 = gp.tileM.mapTileNum[gp.currentMap.getId()][entityLeftCol][entityTopRow];
					tileNum2 = gp.tileM.mapTileNum[gp.currentMap.getId()][entityRightCol][entityTopRow];
					if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
						entity.collisionOn = true;
					}
				break;
			case "down":
					entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
					tileNum1 = gp.tileM.mapTileNum[gp.currentMap.getId()][entityLeftCol][entityBottomRow];
					tileNum2 = gp.tileM.mapTileNum[gp.currentMap.getId()][entityRightCol][entityBottomRow];
					if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
						entity.collisionOn = true;
					}
				break;
			case "left":
					entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
					tileNum1 = gp.tileM.mapTileNum[gp.currentMap.getId()][entityLeftCol][entityTopRow];
					tileNum2 = gp.tileM.mapTileNum[gp.currentMap.getId()][entityLeftCol][entityBottomRow];
					if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
						entity.collisionOn = true;
					}
				break;
			case "right":
					entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
					tileNum1 = gp.tileM.mapTileNum[gp.currentMap.getId()][entityRightCol][entityTopRow];
					tileNum2 = gp.tileM.mapTileNum[gp.currentMap.getId()][entityRightCol][entityBottomRow];
					if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
						entity.collisionOn = true;
					}
				}

	}

	public int checkObject(Entity entity, boolean player) {
		int index = 999;
		for (int i = 0; i < gp.obj[gp.currentMap.getId()].length; ++i) {
			if (gp.obj[gp.currentMap.getId()][i] != null) {
				entity.solidArea.x += entity.worldX;
				entity.solidArea.y += entity.worldY;
				gp.obj[gp.currentMap.getId()][i].solidArea.x += gp.obj[gp.currentMap.getId()][i].worldX;
				gp.obj[gp.currentMap.getId()][i].solidArea.y += gp.obj[gp.currentMap.getId()][i].worldY;
				switch (entity.direction) {
					case "up": entity.solidArea.y -= entity.speed; break;
					case "down": entity.solidArea.y += entity.speed; break;
					case "left": entity.solidArea.x -= entity.speed; break;
					case "right": entity.solidArea.x += entity.speed; break;
				}
				if (entity.solidArea.intersects(gp.obj[gp.currentMap.getId()][i].solidArea)) {
					if (gp.obj[gp.currentMap.getId()][i].collision) {
						entity.collisionOn = true;
					}
					if (player) {
						index = i;
					}
				}

				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				gp.obj[gp.currentMap.getId()][i].solidArea.x = gp.obj[gp.currentMap.getId()][i].solidAreaDefaultX;
				gp.obj[gp.currentMap.getId()][i].solidArea.y = gp.obj[gp.currentMap.getId()][i].solidAreaDefaultY;
			}
		}

		return index;
	}

	public int checkEntity(Entity entity, Entity[][] target) {
		int index = 999;

		for (int i = 0; i < target[1].length; i++) {
			if (target[gp.currentMap.getId()][i] != null) {
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;
				target[gp.currentMap.getId()][i].solidArea.x = target[gp.currentMap.getId()][i].worldX + target[gp.currentMap.getId()][i].solidArea.x;
				target[gp.currentMap.getId()][i].solidArea.y = target[gp.currentMap.getId()][i].worldY + target[gp.currentMap.getId()][i].solidArea.y;
				//System.out.println(entity.worldX + " " + entity.worldY);
				//System.out.println(target[gp.currentMap.getId()][i].worldX + " " + target[gp.currentMap.getId()][i].worldY);
				switch (entity.direction) {
					case "up": entity.solidArea.y -= entity.speed; break;
					case "down": entity.solidArea.y += entity.speed; break;
					case "left": entity.solidArea.x -= entity.speed; break;
					case "right": entity.solidArea.x += entity.speed; break;
				}
				if (entity.solidArea.intersects(target[gp.currentMap.getId()][i].solidArea)) {
					if(target[gp.currentMap.getId()][i] != entity) {
						entity.collisionOn = true;
						index = i;
					}
				}
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				target[gp.currentMap.getId()][i].solidArea.x = target[gp.currentMap.getId()][i].solidAreaDefaultX;
				target[gp.currentMap.getId()][i].solidArea.y = target[gp.currentMap.getId()][i].solidAreaDefaultY;
			}
		}
		//System.out.println(index);
		return index;
	}

	public void checkPlayer(Entity entity) {
		entity.solidArea.x += entity.worldX;
		entity.solidArea.y += entity.worldY;
		gp.player.solidArea.x += gp.player.worldX;
		gp.player.solidArea.y += gp.player.worldY;
		switch (entity.direction) {
			case "up": entity.solidArea.y -= entity.speed; break;
			case "down": entity.solidArea.y += entity.speed; break;
			case "left": entity.solidArea.x -= entity.speed; break;
			case "right": entity.solidArea.x += entity.speed; break;
		}
		if (entity.solidArea.intersects(gp.player.solidArea)) {
			entity.collisionOn = true;
		}

		entity.solidArea.x = entity.solidAreaDefaultX;
		entity.solidArea.y = entity.solidAreaDefaultY;
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
	}
}