package mae.game.object;

import mae.game.GamePanel;
import mae.game.items.Itm_Helper;
import mae.game.items.Itm_Teleporter;
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
		int room = levels;
		i = 1;
		//Floor 1
		int floor = 0;
		//Room 1
		gp.obj[room][i] = new Obj_Computer(gp);
		gp.obj[room][i].worldX = 6 * gp.tileSize;
		gp.obj[room][i].worldY = 1 * gp.tileSize;
		i++;
		gp.obj[room][i] = new Obj_Sun_Pedestal(gp);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		room++;
		//Room 2
		gp.obj[room][i] = new Obj_Card_Holder(gp);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		gp.obj[room][i].lock(1);
		room++;
		//Room 3
		gp.obj[room][i] = new Obj_Skg_Explained(gp);
		gp.obj[room][i].worldX = 5 * gp.tileSize;
		gp.obj[room][i].worldY = 1 * gp.tileSize;
		gp.obj[room][i].setSumType(SumType.PLUS);
		i++;
		gp.obj[room][i] = new Obj_Skeleton_Card_Gen(gp);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		gp.obj[room][i].setSumType(SumType.PLUS);
		room++;
		i = 1;
		//Room 4
		gp.obj[room][i] = new Obj_Moon_Pedestal(gp);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		room++;
		//Room 5
		gp.obj[room][i] = new Obj_Console(gp);
		gp.obj[room][i].floor = floor;
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		gp.obj[room][i].lock(156);
		gp.obj[room][i].setPuzzle(gp.ps.getPuzzle(room, i));
		room = levels + 6;
		//Room 7
		gp.obj[room][i] = new Obj_Card_Holder(gp);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		gp.obj[room][i].lock(2);
		room = levels + 8;
		//Room 9
		gp.obj[room][i] = new Obj_Calculator(gp);
		gp.obj[room][i].worldX = 6 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		i++;
		gp.obj[room][i] = new Obj_Calculator_Button(gp);
		gp.obj[room][i].worldX = 5 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		gp.obj[room][i].setButton(0,"Up");
		i++;
		gp.obj[room][i] = new Obj_Calculator_Button(gp);
		gp.obj[room][i].worldX = (int)(5.5 * gp.tileSize);
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		gp.obj[room][i].setButton(1,"0");
		i++;
		gp.obj[room][i] = new Obj_Calculator_Button(gp);
		gp.obj[room][i].worldX = 6 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		gp.obj[room][i].setButton(2,".");
		i++;
		gp.obj[room][i] = new Obj_Calculator_Button(gp);
		gp.obj[room][i].worldX = (int)(6.5 * gp.tileSize);
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		gp.obj[room][i].setButton(3,"+");
		i++;
		gp.obj[room][i] = new Obj_Calculator_Button(gp);
		gp.obj[room][i].worldX = 7 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		gp.obj[room][i].setButton(3,"-");
		i++;
		gp.obj[room][i] = new Obj_Calculator_Button(gp);
		gp.obj[room][i].worldX = (int)(7.5 * gp.tileSize);
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		gp.obj[room][i].setButton(3,"*");
		i++;
		gp.obj[room][i] = new Obj_Calculator_Button(gp);
		gp.obj[room][i].worldX = 8 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		gp.obj[room][i].setButton(3,"/");
		i++;
		/*gp.obj[room][i] = new Obj_Calculator_Button(gp);
		gp.obj[room][i].worldX = (int)(8.5 * gp.tileSize);
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		gp.obj[room][i].setButton(2,"√");
		i++;*/
		gp.obj[room][i] = new Obj_Calculator_Button(gp);
		gp.obj[room][i].worldX = 9 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		gp.obj[room][i].setButton(4,"=");
		i=1;
		room = levels + 10;
		//Room 11
		gp.obj[room][i] = new Obj_Card_Holder(gp);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		gp.obj[room][i].lock(3);
		//Floor 2
		floor++;
		room = levels + 12;
		//Room 13
		gp.obj[room][i] = new Obj_Card_Holder(gp);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		gp.obj[room][i].lock(4);
		room = levels + 15;
		//Room 16
		gp.obj[room][i] = new Obj_Coming_Soon(gp);
		gp.obj[room][i].worldX = 5 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		room++;
		//Room 17
		gp.obj[room][i] = new Obj_Console(gp);
		gp.obj[room][i].floor = floor;
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		gp.obj[room][i].lock(19);
		gp.obj[room][i].setPuzzle(gp.ps.getPuzzle(room, i));
		room = levels + 18;
		//Room 19
		gp.obj[room][i] = new Obj_Console(gp);
		gp.obj[room][i].floor = floor;
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		gp.obj[room][i].setPuzzle(gp.ps.getPuzzle(room,i));
		gp.obj[room][i].lock(156);
		room = levels + 22;
		//Room 23
		gp.obj[room][i] = new Obj_Card_Holder(gp);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		gp.obj[room][i].lock(5);
		//Floor 3
		floor++;
		room = levels + 24;
		//Room 25
		gp.obj[room][i] = new Obj_Coming_Soon(gp);
		gp.obj[room][i].worldX = 5 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		room = levels + 28;
		//Room 29
		gp.obj[room][i] = new Obj_Console(gp);
		gp.obj[room][i].floor = floor;
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		gp.obj[room][i].setPuzzle(gp.ps.getPuzzle(room,i));
		gp.obj[room][i].lock(156);
		room = levels + 30;
		//Room 31
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
		room = levels + 35;
		//Room 36
		gp.obj[room][i] = new Obj_Coming_Soon(gp);
		gp.obj[room][i].worldX = 5 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		room++;
		//Room 37
		gp.obj[room][i] = new Obj_Console(gp);
		gp.obj[room][i].floor = floor;
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		gp.obj[room][i].setPuzzle(gp.ps.getPuzzle(room,i));
		gp.obj[room][i].lock(41);
		room = levels + 40;
		//Room 41
		gp.obj[room][i] = new Obj_Card_Holder(gp);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		gp.obj[room][i].lock(6);
		room = levels + 42;
		//Room 43
		gp.obj[room][i] = new Obj_Console(gp);
		gp.obj[room][i].floor = floor;
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		gp.obj[room][i].setPuzzle(gp.ps.getPuzzle(room,i));
		gp.obj[room][i].lock(47);
		room = levels + 46;
		//Room 47
		gp.obj[room][i] = new Obj_Console(gp);
		gp.obj[room][i].floor = floor;
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		gp.obj[room][i].setPuzzle(gp.ps.getPuzzle(room,i));
		gp.obj[room][i].lock(156);
		//Floor 5
		floor++;
		room = levels + 48;
		//Room 49
		gp.obj[room][i] = new Obj_Converter(gp);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		gp.obj[room][i].setSumType(SumType.NEGATIVE);
		room = levels + 52;
		//Room 53
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
		//Room 59
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
		room = levels + 63;
		//Room 64
		gp.obj[room][i] = new Obj_Converter(gp);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		gp.obj[room][i].setSumType(SumType.TEN);
		room = levels + 66;
		//Room 67
		gp.obj[room][i] = new Obj_Card_Holder(gp);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		gp.obj[room][i].lock(7);
		room = levels + 70;
		//Room 71
		gp.obj[room][i] = new Obj_Console(gp);
		gp.obj[room][i].floor = floor;
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		gp.obj[room][i].setPuzzle(gp.ps.getPuzzle(room,i));
		gp.obj[room][i].lock(67);
		//Floor 7
		floor++;
		room = levels + 72;
		//Room 73
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
		//Room 79
		gp.obj[room][i] = new Obj_Console(gp);
		gp.obj[room][i].floor = floor;
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		gp.obj[room][i].setPuzzle(gp.ps.getPuzzle(room,i));
		gp.obj[room][i].lock(156);
		room = levels + 80;
		//Room 81
		gp.obj[room][i] = new Obj_Converter(gp);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		gp.obj[room][i].setSumType(SumType.DECIMAL);
		room = levels + 82;
		//Room 83
		gp.obj[room][i] = new Obj_Console(gp);
		gp.obj[room][i].floor = floor;
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		gp.obj[room][i].setPuzzle(gp.ps.getPuzzle(room,i));
		gp.obj[room][i].lock(73);
		//Floor 8
		floor++;
		room = levels + 88;
		//Room 89
		gp.obj[room][i] = new Obj_Console(gp);
		gp.obj[room][i].floor = floor;
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		gp.obj[room][i].setPuzzle(gp.ps.getPuzzle(room,i));
		gp.obj[room][i].lock(156);
		//Floor 9
		floor++;
		room = levels + 96;
		//Room 97
		gp.obj[room][i] = new Obj_Console(gp);
		gp.obj[room][i].floor = floor;
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		gp.obj[room][i].setPuzzle(gp.ps.getPuzzle(room,i));
		gp.obj[room][i].lock(156);
		room = levels + 99;
		//Room 100
		gp.obj[room][i] = new Obj_Skeleton_Card_Gen(gp);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		gp.obj[room][i].setSumType(SumType.TEN);
		room++;
		//Room 101
		gp.obj[room][i] = new Obj_Console(gp);
		gp.obj[room][i].floor = floor;
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		gp.obj[room][i].setPuzzle(gp.ps.getPuzzle(room,i));
		gp.obj[room][i].lock(97);
		room = levels + 102;
		//Room 103
		gp.obj[room][i] = new Obj_Console(gp);
		gp.obj[room][i].floor = floor;
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		gp.obj[room][i].setPuzzle(gp.ps.getPuzzle(room,i));
		gp.obj[room][i].lock(101);
		room = levels + 106;
		//Room 107
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
		room = levels + 120;
		//Room 121
		gp.obj[room][i] = new Obj_Converter(gp);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		gp.obj[room][i].setSumType(SumType.SQUARED);
		room = levels + 126;
		gp.obj[room][i] = new Obj_Card_Holder(gp);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		gp.obj[room][i].lock(9);
		room = levels + 130;
		//Room 131
		gp.obj[room][i] = new Obj_Console(gp);
		gp.obj[room][i].floor = floor;
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		gp.obj[room][i].setPuzzle(gp.ps.getPuzzle(room,i));
		gp.obj[room][i].lock(156);
		//Floor 12
		floor++;
		room = levels + 136;
		//Room 137
		gp.obj[room][i] = new Obj_Console(gp);
		gp.obj[room][i].floor = floor;
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		gp.obj[room][i].setPuzzle(gp.ps.getPuzzle(room,i));
		gp.obj[room][i].lock(139);
		room = levels + 138;
		//Room 139
		gp.obj[room][i] = new Obj_Exit(gp);
		gp.obj[room][i].id = 157;
		gp.obj[room][i].floor = floor;
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 2 * gp.tileSize;
		room = levels + 143;
		//Room 144
		gp.obj[room][i] = new Obj_Converter(gp);
		gp.obj[room][i].worldX = 12 * gp.tileSize;
		gp.obj[room][i].worldY = 3 * gp.tileSize;
		gp.obj[room][i].setSumType(SumType.ELEVEN);
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
		gp.items[0] = new Itm_Helper(gp);
		gp.items[1] = new Itm_Teleporter(gp);
	}
	
	public int getRandomNumber(int min, int max) {
	    return (int) ((Math.random() * (max - min)) + min);
	}
}