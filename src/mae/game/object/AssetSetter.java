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
				gp.obj[j][i] = new Obj_Door(gp);
				gp.obj[j][i].id = count;
				gp.obj[j][i].floor = j;
				gp.obj[j][i].worldX = (i * 2 + 1) * gp.tileSize;
				gp.obj[j][i].worldY = 2 * gp.tileSize;
				gp.obj[j][i].setPuzzle(gp.ps.getPuzzle(j, i));
				count++;
			}
			for(i = 6; i < levels; i++) {
				gp.obj[j][i] = new Obj_Door(gp);
				gp.obj[j][i].id = count;
				gp.obj[j][i].floor = j;
				gp.obj[j][i].worldX = (i * 2 + 4) * gp.tileSize;
				gp.obj[j][i].worldY = 2 * gp.tileSize;
				gp.obj[j][i].setPuzzle(gp.ps.getPuzzle(j, i));
				count++;
			}
			//i++;
			gp.obj[j][i] = new Obj_Stairs(gp);
			gp.obj[j][i].setFloor(j);
			gp.obj[j][i].worldX = ((i * 2 + 3) * gp.tileSize)/2;
			gp.obj[j][i].worldY = 2 * gp.tileSize;
			i++;
		}
		//Rooms >= 12
		count = levels;
		for(int j = 0; j < levels; j++) {
			for(i = 0; i < levels; i++) {
				gp.obj[count][0] = new Obj_Door(gp);
				gp.obj[count][0].id = count;
				gp.obj[count][0].floor = j;
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
		gp.obj[room][i] = new Obj_Card_Holder(gp);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		gp.obj[room][i].lock(1);
		room++;
		gp.obj[room][i] = new Obj_Skg_Explained(gp);
		gp.obj[room][i].worldX = 5 * gp.tileSize;
		gp.obj[room][i].worldY = 1 * gp.tileSize;
		gp.obj[room][i].setSumType(SumType.PLUS);
		i++;
		gp.obj[room][i] = new Obj_Skeleton_Card_Gen(gp);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		gp.obj[room][i].setSumType(SumType.PLUS);
		room = levels + 4;
		i = 1;
		gp.obj[room][i] = new Obj_Console(gp);
		gp.obj[room][i].floor = floor;
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		gp.obj[room][i].lock(156);
		gp.obj[room][i].setPuzzle(gp.ps.getPuzzle(room, i));
		room = levels + 6;
		gp.obj[room][i] = new Obj_Card_Holder(gp);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		gp.obj[room][i].lock(2);
		room = levels + 10;
		gp.obj[room][i] = new Obj_Card_Holder(gp);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		gp.obj[room][i].lock(3);
		//Floor 2
		floor++;
		room = levels + 12;
		gp.obj[room][i] = new Obj_Card_Holder(gp);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		gp.obj[room][i].lock(4);
		room = levels + 16;
		gp.obj[room][i] = new Obj_Console(gp);
		gp.obj[room][i].floor = floor;
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		gp.obj[room][i].lock(19);
		gp.obj[room][i].setPuzzle(gp.ps.getPuzzle(room, i));
		room = levels + 18;
		gp.obj[room][i] = new Obj_Console(gp);
		gp.obj[room][i].floor = floor;
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		gp.obj[room][i].setPuzzle(gp.ps.getPuzzle(room,i));
		gp.obj[room][i].lock(156);
		room = levels + 22;
		gp.obj[room][i] = new Obj_Card_Holder(gp);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		gp.obj[room][i].lock(5);
		//Floor 3
		floor++;
		room = levels + 28;
		gp.obj[room][i] = new Obj_Console(gp);
		gp.obj[room][i].floor = floor;
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		gp.obj[room][i].setPuzzle(gp.ps.getPuzzle(room,i));
		gp.obj[room][i].lock(156);
		room = levels + 30;
		gp.obj[room][i] = new Obj_Skg_Explained(gp);
		gp.obj[room][i].worldX = 5 * gp.tileSize;
		gp.obj[room][i].worldY = 1 * gp.tileSize;
		gp.obj[room][i].setSumType(SumType.MINUS);
		i++;
		gp.obj[room][i] = new Obj_Skeleton_Card_Gen(gp);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		gp.obj[room][i].setSumType(SumType.MINUS);
		i = 1;
		//Floor 4
		floor++;
		room = levels + 36;
		gp.obj[room][i] = new Obj_Console(gp);
		gp.obj[room][i].floor = floor;
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		gp.obj[room][i].setPuzzle(gp.ps.getPuzzle(room,i));
		gp.obj[room][i].lock(41);
		room = levels + 40;
		gp.obj[room][i] = new Obj_Card_Holder(gp);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		gp.obj[room][i].lock(6);
		room = levels + 42;
		gp.obj[room][i] = new Obj_Console(gp);
		gp.obj[room][i].floor = floor;
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		gp.obj[room][i].setPuzzle(gp.ps.getPuzzle(room,i));
		gp.obj[room][i].lock(47);
		room = levels + 46;
		gp.obj[room][i] = new Obj_Console(gp);
		gp.obj[room][i].floor = floor;
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		gp.obj[room][i].setPuzzle(gp.ps.getPuzzle(room,i));
		gp.obj[room][i].lock(156);
		//Floor 5
		floor++;
		room = levels + 52;
		gp.obj[room][i] = new Obj_Skg_Explained(gp);
		gp.obj[room][i].worldX = 5 * gp.tileSize;
		gp.obj[room][i].worldY = 1 * gp.tileSize;
		gp.obj[room][i].setSumType(SumType.MULTI);
		i++;
		gp.obj[room][i] = new Obj_Skeleton_Card_Gen(gp);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		gp.obj[room][i].setSumType(SumType.MULTI);
		i = 1;
		room = levels + 58;
		gp.obj[room][i] = new Obj_Console(gp);
		gp.obj[room][i].floor = floor;
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		gp.obj[room][i].setPuzzle(gp.ps.getPuzzle(room,i));
		gp.obj[room][i].lock(156);
		//Floor 6
		floor++;
		room = levels + 60;
		gp.obj[room][i] = new Obj_Console(gp);
		gp.obj[room][i].floor = floor;
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		gp.obj[room][i].setPuzzle(gp.ps.getPuzzle(room,i));
		gp.obj[room][i].lock(156);
		room = levels + 66;
		gp.obj[room][i] = new Obj_Card_Holder(gp);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		gp.obj[room][i].lock(7);
		room = levels + 70;
		gp.obj[room][i] = new Obj_Console(gp);
		gp.obj[room][i].floor = floor;
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		gp.obj[room][i].setPuzzle(gp.ps.getPuzzle(room,i));
		gp.obj[room][i].lock(67);
		//Floor 7
		floor++;
		room = levels + 72;
		gp.obj[room][i] = new Obj_Skg_Explained(gp);
		gp.obj[room][i].worldX = 5 * gp.tileSize;
		gp.obj[room][i].worldY = 1 * gp.tileSize;
		gp.obj[room][i].setSumType(SumType.DIVIDE);
		i++;
		gp.obj[room][i] = new Obj_Skeleton_Card_Gen(gp);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		gp.obj[room][i].setSumType(SumType.DIVIDE);
		i = 1;
		room = levels + 78;
		gp.obj[room][i] = new Obj_Console(gp);
		gp.obj[room][i].floor = floor;
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		gp.obj[room][i].setPuzzle(gp.ps.getPuzzle(room,i));
		gp.obj[room][i].lock(156);
		room = levels + 82;
		gp.obj[room][i] = new Obj_Console(gp);
		gp.obj[room][i].floor = floor;
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		gp.obj[room][i].setPuzzle(gp.ps.getPuzzle(room,i));
		gp.obj[room][i].lock(73);
		//Floor 8
		floor++;
		room = levels + 88;
		gp.obj[room][i] = new Obj_Console(gp);
		gp.obj[room][i].floor = floor;
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		gp.obj[room][i].setPuzzle(gp.ps.getPuzzle(room,i));
		gp.obj[room][i].lock(156);
		//Floor 9
		floor++;
		room = levels + 96;
		gp.obj[room][i] = new Obj_Console(gp);
		gp.obj[room][i].floor = floor;
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		gp.obj[room][i].setPuzzle(gp.ps.getPuzzle(room,i));
		gp.obj[room][i].lock(156);
		room = levels + 100;
		gp.obj[room][i] = new Obj_Console(gp);
		gp.obj[room][i].floor = floor;
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		gp.obj[room][i].setPuzzle(gp.ps.getPuzzle(room,i));
		gp.obj[room][i].lock(97);
		room = levels + 102;
		gp.obj[room][i] = new Obj_Console(gp);
		gp.obj[room][i].floor = floor;
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		gp.obj[room][i].setPuzzle(gp.ps.getPuzzle(room,i));
		gp.obj[room][i].lock(101);
		room = levels + 106;
		gp.obj[room][i] = new Obj_Console(gp);
		gp.obj[room][i].floor = floor;
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		gp.obj[room][i].setPuzzle(gp.ps.getPuzzle(room,i));
		gp.obj[room][i].lock(103);
		//Floor 10
		floor++;
		room = levels + 108;
		gp.obj[room][i] = new Obj_Card_Holder(gp);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		gp.obj[room][i].lock(8);
		room = levels + 112;
		gp.obj[room][i] = new Obj_Console(gp);
		gp.obj[room][i].floor = floor;
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		gp.obj[room][i].setPuzzle(gp.ps.getPuzzle(room,i));
		gp.obj[room][i].lock(156);
		//Floor 11
		floor++;
		room = levels + 126;
		gp.obj[room][i] = new Obj_Card_Holder(gp);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		gp.obj[room][i].lock(9);
		room = levels + 130;
		gp.obj[room][i] = new Obj_Console(gp);
		gp.obj[room][i].floor = floor;
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		gp.obj[room][i].setPuzzle(gp.ps.getPuzzle(room,i));
		gp.obj[room][i].lock(156);
		//Floor 12
		floor++;
		room = levels + 136;
		gp.obj[room][i] = new Obj_Console(gp);
		gp.obj[room][i].floor = floor;
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		gp.obj[room][i].setPuzzle(gp.ps.getPuzzle(room,i));
		gp.obj[room][i].lock(139);
		room = levels + 138;
		gp.obj[room][i] = new Obj_Exit(gp);
		gp.obj[room][i].id = 157;
		gp.obj[room][i].floor = floor;
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
	}
	
	public void setPuzzles() {
		gp.ps.setPuzzle();
	}

	public void setNPC() {
		//int i = 0;
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