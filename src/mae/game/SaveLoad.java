package mae.game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import mae.data.DataStorage;
import mae.game.object.SumType;

public class SaveLoad {
	GamePanel gp;
	
	public SaveLoad(GamePanel gp) {
		this.gp = gp;
	}
	
	public void save(int sf) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));
			if(sf == 1) {
				oos = new ObjectOutputStream(new FileOutputStream(new File("save1.dat")));
			} else if(sf == 2) {
				oos = new ObjectOutputStream(new FileOutputStream(new File("save2.dat")));
			} else if(sf == 3) {
				oos = new ObjectOutputStream(new FileOutputStream(new File("save3.dat")));
			}
			DataStorage ds = new DataStorage();
			// Key Cards			
			ds.sk_value = gp.kc[0].useCard();
			ds.locked = new boolean[10];
			for(int i = 0; i < gp.kc.length; i++) {
				ds.locked[i] = gp.kc[i].opened;
			}
			//Player Position
			ds.direction = gp.player.direction;
			ds.currentMap = gp.currentMap.getId();
			ds.worldX = gp.player.worldX;
			ds.worldY = gp.player.worldY;
			//Items
			ds.mapItemId = new int[gp.items.length];
			ds.mapItemOpened = new boolean[gp.items.length];
			//Object on map
			ds.mapObjectId = new int[gp.maxMap][gp.obj[1].length];
			ds.mapObjectEnId = new int[gp.maxMap][gp.obj[1].length];
			ds.mapObjectFloor = new int[gp.maxMap][gp.obj[1].length];
			ds.mapObjectWorldX = new int[gp.maxMap][gp.obj[1].length];
			ds.mapObjectWorldY = new int[gp.maxMap][gp.obj[1].length];
			ds.mapObjectOpened = new boolean[gp.maxMap][gp.obj[1].length];
			ds.mapObjectKey = new int[gp.maxMap][gp.obj[1].length];
			ds.mapObjectSum = new SumType[gp.maxMap][gp.obj[1].length];
			//ds.mapObjectPuzzle = new Puzzle[gp.maxMap][gp.obj[1].length];
			ds.mapNpcId = new int[gp.maxMap][gp.npc[1].length];
			ds.mapNpcWorldX = new int[gp.maxMap][gp.npc[1].length];
			ds.mapNpcWorldY = new int[gp.maxMap][gp.npc[1].length];
			ds.mapNpcDirection = new String[gp.maxMap][gp.npc[1].length];
			for(int mapNum = 0; mapNum < gp.maxMap; mapNum++) {
				for(int i = 0; i < gp.obj[1].length; i++) {
					if(gp.obj[mapNum][i] == null) {
						ds.mapObjectEnId[mapNum][i] = -1;
					} else {
						System.out.println(gp.obj[mapNum][i].worldX);
						ds.mapObjectId[mapNum][i] = gp.obj[mapNum][i].id;
						ds.mapObjectEnId[mapNum][i] = gp.obj[mapNum][i].enId;
						ds.mapObjectFloor[mapNum][i] = gp.obj[mapNum][i].floor;
						ds.mapObjectWorldX[mapNum][i] = gp.obj[mapNum][i].worldX;
						ds.mapObjectWorldY[mapNum][i] = gp.obj[mapNum][i].worldY;
						ds.mapObjectOpened[mapNum][i] = gp.obj[mapNum][i].opened;
						ds.mapObjectKey[mapNum][i] = gp.obj[mapNum][i].key;
						ds.mapObjectSum[mapNum][i] = gp.obj[mapNum][i].sum;
						if(gp.obj[mapNum][i].puzzle != null) {
							//ds.mapObjectPuzzle[mapNum][i] = gp.obj[mapNum][i].puzzle;
						}
					}
				}
				//NPCs on map
				for(int i = 0; i < gp.npc[1].length; i++) {
					if(gp.npc[mapNum][i] == null) {
						ds.mapNpcId[mapNum][i] = -1;
					} else {
						ds.mapNpcId[mapNum][i] = gp.npc[mapNum][i].id;
						ds.mapNpcWorldX[mapNum][i] = gp.npc[mapNum][i].worldX;
						ds.mapNpcWorldY[mapNum][i] = gp.npc[mapNum][i].worldY;
						ds.mapNpcDirection[mapNum][i] = gp.npc[mapNum][i].direction;
					}
				}
			}
			//Items
			for(int i = 0; i < 2; i++) {
				if(gp.items[i] == null) {
					ds.mapItemId[i] = -1;
				} else {
					ds.mapItemId[i] = gp.items[i].id;
					ds.mapItemOpened[i] = gp.items[i].opened;
				}
			}
			oos.writeObject(ds);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Save Exception!");
		}
	}
	
	public void load(int sf) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));
			if(sf == 0) {
				ois = new ObjectInputStream(new FileInputStream(new File("save1.dat")));
			} else if(sf == 1) {
				ois = new ObjectInputStream(new FileInputStream(new File("save2.dat")));
			} else if(sf == 2) {
				ois = new ObjectInputStream(new FileInputStream(new File("save3.dat")));
			}
			DataStorage ds = (DataStorage)ois.readObject();
			// Key Cards
			gp.kc[0].setValue(ds.sk_value);;
			for(int i = 0; i < gp.kc.length; i++) {
				 gp.kc[i].opened = ds.locked[i];
			}
			gp.currentMap = gp.maps[ds.currentMap];
			gp.player.direction = ds.direction;
			gp.player.worldX = ds.worldX;
			gp.player.worldY = ds.worldY;
			for(int mapNum = 0; mapNum < gp.maxMap; mapNum++) {
				for(int i = 0; i < gp.obj[1].length; i++) {
					if(ds.mapObjectEnId[mapNum][i] < 0) {
						gp.obj[mapNum][i] = null;
					} else {
						gp.obj[mapNum][i] = gp.eGenerator.getObject(ds.mapObjectEnId[mapNum][i], ds.mapObjectFloor[mapNum][i]);
						gp.obj[mapNum][i].id = ds.mapObjectId[mapNum][i];
						gp.obj[mapNum][i].setFloor(ds.mapObjectFloor[mapNum][i]);
						gp.obj[mapNum][i].worldX = ds.mapObjectWorldX[mapNum][i];
						gp.obj[mapNum][i].worldY = ds.mapObjectWorldY[mapNum][i];
						gp.obj[mapNum][i].opened = ds.mapObjectOpened[mapNum][i];
						if(!gp.obj[mapNum][i].opened) {
							gp.obj[mapNum][i].lock(ds.mapObjectKey[mapNum][i]);
						}
						gp.obj[mapNum][i].setSumType(ds.mapObjectSum[mapNum][i]);
						gp.obj[mapNum][i].getImage();
						gp.obj[mapNum][i].setPuzzle(gp.ps.getPuzzle(mapNum, i));
					}
				}
				//NPCs on map
				for(int i = 0; i < gp.npc[1].length; i++) {
					if(ds.mapNpcId[mapNum][i] < 0) {
						gp.npc[mapNum][i] = null;
					} else {
						gp.npc[mapNum][i] = gp.eGenerator.getNpc(ds.mapNpcId[mapNum][i]);
						gp.npc[mapNum][i].worldX = ds.mapNpcWorldX[mapNum][i];
						gp.npc[mapNum][i].worldY = ds.mapNpcWorldY[mapNum][i];
						gp.npc[mapNum][i].direction = ds.mapNpcDirection[mapNum][i];
					}
				}
			}
			//Items
			for(int i = 0; i < 1; i++) {
				if(ds.mapItemId[i] < 0) {
					gp.items[i] = null;
				} else {
					gp.items[i] = gp.eGenerator.getItem(ds.mapItemId[i]);
					gp.items[i].opened = ds.mapItemOpened[i];
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Load Exception!");
		}
	}
}
