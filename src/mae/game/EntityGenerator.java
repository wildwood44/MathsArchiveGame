package mae.game;

import mae.game.items.Item;
import mae.game.npc.NPC;

public class EntityGenerator {

	GamePanel gp;
	public EntityGenerator(GamePanel gp) {
		this.gp = gp;
	}
	
	public Entity getObject(int itemId) {
		Entity obj = null;
		switch(itemId) {
		//default
		}
		return obj;
	}
	
	public Item getItem(int itemId) {
		Item obj = null;
		switch(itemId) {
		//default
		}
		return obj;
	}
	
	public NPC getNpc(int npcId) {
		NPC npc = null;
		switch(npcId) {
		}
		return npc;
	}
}
