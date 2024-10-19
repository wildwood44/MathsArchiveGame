package mae.game.object;

import mae.game.GamePanel;
import mae.game.items.KeyCard;
import mae.game.tile.Map;
import mae.game.tile.MapType;


public class AssetSetter {
	GamePanel gp;
	final int levels = 12;

	public AssetSetter(GamePanel gp) {
		this.gp = gp;
		//gp.puzzle.setPuzzle();
		//gp.puzzle.setSolution();
	}
	
	public void setMaps() {
		for(int i = 0; i < levels; i++) {
			gp.maps[i] = new Map(gp, MapType.LEVEL, i, "Floor " + (i + 1), 29, 4);
		}
		for(int i = levels; i < 156; i++) {
			gp.maps[i] = new Map(gp, MapType.ROOM, i, "Room " + (i - 11), 15, 4);
		}
	}

	public void setObject() {
		int i = 0, count = 1;
		//Levels < 12
		for(int j = 0; j < levels; j++) {
			for(i = 0; i < 6; i++) {
				gp.obj[j][i] = new Obj_Door(gp, count, j, 
						gp.doorPuzzle.getPuzzle(j, i));
				gp.obj[j][i].worldX = (i * 2 + 1) * gp.tileSize;
				gp.obj[j][i].worldY = 2 * gp.tileSize;
				count++;
			}
			for(i = 6; i < levels; i++) {
				gp.obj[j][i] = new Obj_Door(gp, count, j, 
						gp.doorPuzzle.getPuzzle(j, i));
				gp.obj[j][i].worldX = (i * 2 + 4) * gp.tileSize;
				gp.obj[j][i].worldY = 2 * gp.tileSize;
				count++;
			}
			i++;
			gp.obj[j][i] = new Obj_Stairs(gp, j);
			gp.obj[j][i].worldX = ((i * 2 + 1) * gp.tileSize)/2;
			gp.obj[j][i].worldY = 2 * gp.tileSize;
			i++;
		}
		//Rooms >= 12
		count = levels;
		for(int j = 0; j < levels; j++) {
			for(i = 0; i < levels; i++) {
				gp.obj[count][0] = new Obj_Door(gp, count, j);
				gp.obj[count][0].worldX = 2 * gp.tileSize;
				gp.obj[count][0].worldY = 2 * gp.tileSize;
				gp.obj[count][0].opened = true;
				count++;
			}
		}
		i++;
		int room = levels + 1;
		i = 1;
		//Floor 1
		int floor = 0;
		gp.obj[room][i] = new Obj_Card_Holder(gp, 1);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		room++;
		gp.obj[room][i] = new Obj_Skg_Explained(gp, SumType.PLUS);
		gp.obj[room][i].worldX = 5 * gp.tileSize;
		gp.obj[room][i].worldY = 1 * gp.tileSize;
		i++;
		gp.obj[room][i] = new Obj_Skeleton_Card_Gen(gp, SumType.PLUS);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		room = levels + 4;
		i = 1;
		gp.obj[room][i] = new Obj_Console(gp, floor, gp.consPuzzle.getPuzzle(0), 156);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		room = levels + 6;
		gp.obj[room][i] = new Obj_Card_Holder(gp, 2);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		room = levels + 10;
		gp.obj[room][i] = new Obj_Card_Holder(gp, 3);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		//Floor 2
		floor++;
		room = levels + 12;
		gp.obj[room][i] = new Obj_Card_Holder(gp, 4);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		room = levels + 16;
		gp.obj[room][i] = new Obj_Console(gp, floor, gp.consPuzzle.getPuzzle(1), 19);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		System.out.println("Lock: "+ gp.obj[room][19]);
		room = levels + 18;
		gp.obj[room][i] = new Obj_Console(gp, floor, gp.consPuzzle.getPuzzle(1), 156);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		room = levels + 22;
		gp.obj[room][i] = new Obj_Card_Holder(gp, 5);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		//Floor 3
		floor++;
		room = levels + 28;
		gp.obj[room][i] = new Obj_Console(gp, floor, gp.consPuzzle.getPuzzle(2), 156);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		room = levels + 30;
		gp.obj[room][i] = new Obj_Skg_Explained(gp, SumType.MINUS);
		gp.obj[room][i].worldX = 5 * gp.tileSize;
		gp.obj[room][i].worldY = 1 * gp.tileSize;
		i++;
		gp.obj[room][i] = new Obj_Skeleton_Card_Gen(gp, SumType.MINUS);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		i = 1;
		//Floor 4
		floor++;
		room = levels + 36;
		gp.obj[room][i] = new Obj_Console(gp, floor, gp.consPuzzle.getPuzzle(1), 41);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		room = levels + 40;
		gp.obj[room][i] = new Obj_Card_Holder(gp, 6);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		room = levels + 42;
		gp.obj[room][i] = new Obj_Console(gp, floor, gp.consPuzzle.getPuzzle(1), 47);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		room = levels + 46;
		gp.obj[room][i] = new Obj_Console(gp, floor, gp.consPuzzle.getPuzzle(3), 156);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		//Floor 5
		floor++;
		room = levels + 52;
		gp.obj[room][i] = new Obj_Skg_Explained(gp, SumType.MULTI);
		gp.obj[room][i].worldX = 5 * gp.tileSize;
		gp.obj[room][i].worldY = 1 * gp.tileSize;
		i++;
		gp.obj[room][i] = new Obj_Skeleton_Card_Gen(gp, SumType.MULTI);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		i = 1;
		room = levels + 58;
		gp.obj[room][i] = new Obj_Console(gp, floor, gp.consPuzzle.getPuzzle(4), 156);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		//Floor 6
		floor++;
		room = levels + 60;
		gp.obj[room][i] = new Obj_Console(gp, floor, gp.consPuzzle.getPuzzle(5), 156);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		room = levels + 66;
		gp.obj[room][i] = new Obj_Card_Holder(gp, 7);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		room = levels + 70;
		gp.obj[room][i] = new Obj_Console(gp, floor, gp.consPuzzle.getPuzzle(1), 67);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		//Floor 7
		floor++;
		room = levels + 72;
		gp.obj[room][i] = new Obj_Skg_Explained(gp, SumType.DIVIDE);
		gp.obj[room][i].worldX = 5 * gp.tileSize;
		gp.obj[room][i].worldY = 1 * gp.tileSize;
		i++;
		gp.obj[room][i] = new Obj_Skeleton_Card_Gen(gp, SumType.DIVIDE);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		i = 1;
		room = levels + 78;
		gp.obj[room][i] = new Obj_Console(gp, floor, gp.consPuzzle.getPuzzle(6), 156);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		room = levels + 82;
		gp.obj[room][i] = new Obj_Console(gp, floor, gp.consPuzzle.getPuzzle(1), 73);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		//Floor 8
		floor++;
		room = levels + 88;
		gp.obj[room][i] = new Obj_Console(gp, floor, gp.consPuzzle.getPuzzle(7), 156);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		//Floor 9
		floor++;
		room = levels + 96;
		gp.obj[room][i] = new Obj_Console(gp, floor, gp.consPuzzle.getPuzzle(8), 156);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		room = levels + 100;
		gp.obj[room][i] = new Obj_Console(gp, floor, gp.consPuzzle.getPuzzle(8), 97);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		room = levels + 102;
		gp.obj[room][i] = new Obj_Console(gp, floor, gp.consPuzzle.getPuzzle(8), 101);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		room = levels + 106;
		gp.obj[room][i] = new Obj_Console(gp, floor, gp.consPuzzle.getPuzzle(8), 103);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		//Floor 10
		floor++;
		room = levels + 108;
		gp.obj[room][i] = new Obj_Card_Holder(gp, 8);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		room = levels + 112;
		gp.obj[room][i] = new Obj_Console(gp, floor, gp.consPuzzle.getPuzzle(9), 156);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		//Floor 11
		floor++;
		room = levels + 126;
		gp.obj[room][i] = new Obj_Card_Holder(gp, 9);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		room = levels + 130;
		gp.obj[room][i] = new Obj_Console(gp, floor, gp.consPuzzle.getPuzzle(10), 156);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		//Floor 12
		floor++;
		room = levels + 136;
		gp.obj[room][i] = new Obj_Console(gp, floor, gp.consPuzzle.getPuzzle(11), 139);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		room = levels + 138;
		gp.obj[room][i] = new Obj_Exit(gp, 157, floor);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
	}
	
	public void setPuzzles() {
		gp.doorPuzzle.setPuzzle();
		gp.consPuzzle.setPuzzle();
	}

	public void setNPC() {
		int i = 0;
	}
	
	public void setItem() {
		for(int i = 0; i < 10; i++) {
			gp.kc[i] = new KeyCard(gp, i);
		}
	}
	
	public int getRandomNumber(int min, int max) {
	    return (int) ((Math.random() * (max - min)) + min);
	}
}