package mae.game.object;

import mae.game.GamePanel;
import mae.game.npc.NPC;

public class AssetManager {
	GamePanel gp;
	
	public AssetManager(GamePanel gp) {
		this.gp = gp;
	}
	
	//Create npc
	public void createActor(NPC npc, int x, int y, String direction) {
		for (int i = 0; i < gp.npc[gp.currentMap.getId()].length; i++) {
			if(gp.npc[gp.currentMap.getId()][i] == null) {
				gp.npc[gp.currentMap.getId()][i] = npc;
				gp.npc[gp.currentMap.getId()][i].worldX = x;
				gp.npc[gp.currentMap.getId()][i].worldY = y;
				gp.npc[gp.currentMap.getId()][i].direction = direction;
				break;
			}
		}
	}
	
	//Create npc on another map
	public void createActor(NPC npc, int map, int x, int y, String direction) {
		for (int i = 0; i < gp.npc[gp.currentMap.getId()].length; i++) {
			if(gp.npc[map][i] == null) {
				gp.npc[map][i] = npc;
				gp.npc[map][i].worldX = x;
				gp.npc[map][i].worldY = y;
				gp.npc[map][i].direction = direction;
				break;
			}
		}
	}
	
	//Select npc
	public NPC getActor(String npcName) {
		for (int i = 0; i < gp.npc[gp.currentMap.getId()].length; i++) {
			if(gp.npc[gp.currentMap.getId()][i] != null && gp.npc[gp.currentMap.getId()][i].name == npcName) {
				return gp.npc[gp.currentMap.getId()][i];
			}
		}
		return null;
	}
	
	//Destroy npc
	public void destroyActor(String npcName) {
		for (int i = 0; i < gp.npc[gp.currentMap.getId()].length; i++) {
			if(gp.npc[gp.currentMap.getId()][i] != null && gp.npc[gp.currentMap.getId()][i].name == npcName) {
				gp.npc[gp.currentMap.getId()][i] = null;
				break;
			}
		}
	}
	
	//Change direction npc faces
	public void changeActorDirection(String npcName, String direction) {
		NPC actor = getActor(npcName);
		actor.direction = direction;
		actor.update();
	}
	
	//Move a npc in a direction to tile * tileSize
	public boolean moveActor(String npcName, String direction, int move) {
		boolean moveComplete = false;
		NPC actor = getActor(npcName);
		actor.moving = true;
		actor.direction = direction;
		switch(actor.direction) {
		case "up":
			if(actor.worldY < gp.tileSize * move){
				moveComplete = true;
			} else {actor.worldY -= 1;}
			break;
		case "down":
			if(actor.worldY > gp.tileSize * move){
				moveComplete = true;
			} else {actor.worldY += 1;}
			break;
		case "left":
			if(actor.worldX < gp.tileSize * move){
				moveComplete = true;
			} else {actor.worldX -= 1;}
			break;
		case "right":
			if(actor.worldX > gp.tileSize * move){
				moveComplete = true;
			} else {actor.worldX += 1;}
			break;
		}
		if(!moveComplete) {
			actor.update();
		}
		actor.moving = false;
		return moveComplete;
	}
}
