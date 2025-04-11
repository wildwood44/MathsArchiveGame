package mae.game;

import mae.game.items.Item;
import mae.game.items.Itm_Helper;
import mae.game.items.Itm_Teleporter;
import mae.game.npc.NPC;
import mae.game.npc.Prisoner_85;
import mae.game.object.Obj_Cage;
import mae.game.object.Obj_Card_Holder;
import mae.game.object.Obj_Coming_Soon;
import mae.game.object.Obj_Computer;
import mae.game.object.Obj_Computer_Medical;
import mae.game.object.Obj_Computer_Seccurity;
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
import mae.game.object.calculator.Obj_Calculator;
import mae.game.object.calculator.Obj_Calculator_Button_Divide;
import mae.game.object.calculator.Obj_Calculator_Button_Equal;
import mae.game.object.calculator.Obj_Calculator_Button_Minus;
import mae.game.object.calculator.Obj_Calculator_Button_Multi;
import mae.game.object.calculator.Obj_Calculator_Button_Num;
import mae.game.object.calculator.Obj_Calculator_Button_Plus;
import mae.game.object.calculator.Obj_Calculator_Button_Up;
import mae.game.object.calculator.Obj_Calculator_Button_dot;
import mae.game.puzzle.Puzzle;
import mae.game.puzzle.Puzzle_85;

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
		case Obj_Sun_Pedestal.objId: obj = new Obj_Sun_Pedestal(gp);break;//9
		case Obj_Moon_Pedestal.objId: obj = new Obj_Moon_Pedestal(gp);break;//10
		case Obj_Converter.objId: obj = new Obj_Converter(gp);break;//11
		case Obj_Computer_Seccurity.objId: obj = new Obj_Computer_Seccurity(gp);break;//12
		case Obj_Computer_Medical.objId: obj = new Obj_Computer_Medical(gp);break;//13
		case Obj_Map.objId: obj = new Obj_Map(gp);break;//14
		case Obj_Coming_Soon.objId: obj = new Obj_Coming_Soon(gp);break;//15
		case Obj_Cage.objId: obj = new Obj_Cage(gp);break;//16
		case Obj_Calculator.objId: obj = new Obj_Calculator(gp);break;//20
		case Obj_Calculator_Button_Up.objId: obj = new Obj_Calculator_Button_Up(gp);break;//21
		case Obj_Calculator_Button_Num.objId: obj = new Obj_Calculator_Button_Num(gp);break;//22
		case Obj_Calculator_Button_dot.objId: obj = new Obj_Calculator_Button_dot(gp);break;//23
		case Obj_Calculator_Button_Plus.objId: obj = new Obj_Calculator_Button_Plus(gp);break;//24
		case Obj_Calculator_Button_Minus.objId: obj = new Obj_Calculator_Button_Minus(gp);break;//25
		case Obj_Calculator_Button_Multi.objId: obj = new Obj_Calculator_Button_Multi(gp);break;//26
		case Obj_Calculator_Button_Divide.objId: obj = new Obj_Calculator_Button_Divide(gp);break;//27
		case Obj_Calculator_Button_Equal.objId: obj = new Obj_Calculator_Button_Equal(gp);break;//28
		//default
		}
		return obj;
	}
	
	public Item getItem(int itemId) {
		Item obj = null;
		switch(itemId) {
		case Itm_Helper.id: obj = new Itm_Helper(gp); break; //1
		case Itm_Teleporter.id: obj = new Itm_Teleporter(gp); break; //2
		}
		return obj;
	}
	
	public NPC getNpc(int npcId) {
		NPC npc = null;
		switch(npcId) {
		case Prisoner_85.npcId: npc = new Prisoner_85(gp); break; //85
		}
		return npc;
	}
	
	public Puzzle getPuzzle(int puzzleId) {
		Puzzle puzzle = null;
		switch(puzzleId) {
		//case Puzzle_85.
		}
		return puzzle;
	}
}
