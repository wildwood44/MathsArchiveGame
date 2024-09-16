package mae.game;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import mae.data.DataStorage;

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
			ds.found = new boolean[7][50];

			ds.direction = gp.player.direction;
			//Player Position
			ds.currentMap = gp.currentMap.getId();
			ds.worldX = gp.player.worldX;
			ds.worldY = gp.player.worldY;
			//Object on map
			ds.mapObjectId = new int[gp.maxMap][gp.obj[1].length];
			ds.mapObjectWorldX = new int[gp.maxMap][gp.obj[1].length];
			ds.mapObjectWorldY = new int[gp.maxMap][gp.obj[1].length];
			ds.mapObjectLootIds = new int[gp.maxMap][gp.obj[1].length];
			ds.mapObjectOpened = new boolean[gp.maxMap][gp.obj[1].length];
			ds.mapNpcId = new int[gp.maxMap][gp.npc[1].length];
			ds.mapNpcWorldX = new int[gp.maxMap][gp.npc[1].length];
			ds.mapNpcWorldY = new int[gp.maxMap][gp.npc[1].length];
			ds.mapNpcDirection = new String[gp.maxMap][gp.npc[1].length];
			for(int mapNum = 0; mapNum < gp.maxMap; mapNum++) {
				for(int i = 0; i < gp.obj[1].length; i++) {
					if(gp.obj[mapNum][i] == null) {
						ds.mapObjectId[mapNum][i] = -1;
					} else {
						ds.mapObjectId[mapNum][i] = gp.obj[mapNum][i].id;
						ds.mapObjectWorldX[mapNum][i] = gp.obj[mapNum][i].worldX;
						ds.mapObjectWorldY[mapNum][i] = gp.obj[mapNum][i].worldY;
						ds.mapObjectOpened[mapNum][i] = gp.obj[mapNum][i].opened;
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
			oos.writeObject(ds);
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Save Exception!");
		}
	}
	
	public void load(int sf) {
		try {
			//System.out.println("Loaded");
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));
			if(sf == 0) {
				ois = new ObjectInputStream(new FileInputStream(new File("save1.dat")));
			} else if(sf == 1) {
				ois = new ObjectInputStream(new FileInputStream(new File("save2.dat")));
			} else if(sf == 2) {
				ois = new ObjectInputStream(new FileInputStream(new File("save3.dat")));
			}
			DataStorage ds = (DataStorage)ois.readObject();
			
			gp.currentMap = gp.maps[ds.currentMap];
			gp.player.direction = ds.direction;
			gp.player.worldX = ds.worldX;
			gp.player.worldY = ds.worldY;
			for(int mapNum = 0; mapNum < gp.maxMap; mapNum++) {
				for(int i = 0; i < gp.obj[1].length; i++) {
					if(ds.mapObjectId[mapNum][i] < 0) {
						gp.obj[mapNum][i] = null;
					} else {
						gp.obj[mapNum][i] = gp.eGenerator.getObject(ds.mapObjectId[mapNum][i]);
						gp.obj[mapNum][i].worldX = ds.mapObjectWorldX[mapNum][i];
						gp.obj[mapNum][i].worldY = ds.mapObjectWorldY[mapNum][i];
						gp.obj[mapNum][i].opened = ds.mapObjectOpened[mapNum][i];
						if(gp.obj[mapNum][i].opened == true) {
							//gp.obj[mapNum][i].down1 = gp.obj[mapNum][i].image2;
						}
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
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Load Exception!");
		}
	}
}
