package mae.game;

import mae.game.items.Item;
import mae.game.npc.NPC;
import mae.game.object.Obj_Calculator;
import mae.game.object.Obj_Calculator_Button;
import mae.game.object.Obj_Card_Holder;
import mae.game.object.Obj_Computer;
import mae.game.object.Obj_Console;
import mae.game.object.Obj_Converter;
import mae.game.object.Obj_Door;
import mae.game.object.Obj_Exit;
import mae.game.object.Obj_Map;
import mae.game.object.Obj_Moon_Pedestal;
import mae.game.object.Obj_Skeleton_Card_Gen;
import mae.game.object.Obj_Skg_Explained;
import mae.game.object.Obj_Stairs;
import mae.game.object.Obj_Sun_Pedestal;

public class EntityGenerator {

	GamePanel gp;
	public EntityGenerator(GamePanel gp) {
		this.gp = gp;
	}
	
	public Entity getObject(int itemId, int floorId) {
		Entity obj = null;
		switch(itemId) {
		case Obj_Door.objId: obj = new Obj_Door(gp);break;
		case Obj_Console.objId: obj = new Obj_Console(gp);break;
		case Obj_Stairs.objId: obj = new Obj_Stairs(gp);break;
		case Obj_Skeleton_Card_Gen.objId: obj = new Obj_Skeleton_Card_Gen(gp);break;
		case Obj_Card_Holder.objId: obj = new Obj_Card_Holder(gp);break;
		case Obj_Skg_Explained.objId: obj = new Obj_Skg_Explained(gp);break;
		case Obj_Exit.objId: obj = new Obj_Exit(gp);break;///7
		case Obj_Computer.objId: obj = new Obj_Computer(gp);break;//8
		case Obj_Sun_Pedestal.objId: obj = new Obj_Sun_Pedestal(gp);//9
		case Obj_Moon_Pedestal.objId: obj = new Obj_Moon_Pedestal(gp);//10
		case Obj_Converter.objId: obj = new Obj_Converter(gp);//11
		case Obj_Calculator.objId: obj = new Obj_Calculator(gp);//12
		case Obj_Calculator_Button.objId: obj = new Obj_Calculator_Button(gp);//13
		case Obj_Map.objId: obj = new Obj_Map(gp);//14
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
