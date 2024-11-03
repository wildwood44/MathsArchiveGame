package mae.data;

import java.io.Serializable;
import java.util.ArrayList;

import mae.game.object.SumType;


public class DataStorage implements Serializable {
	//Player character details
	public String direction;
	public int currentMap;
	public int worldX, worldY;
	//Key Cards
	public double sk_value;
	public boolean locked[];// = new boolean[10];
	//Player inventory
	public int shill;
	public ArrayList<Integer> itemId = new ArrayList<>();
	public ArrayList<Integer> itemAmount = new ArrayList<>();

	//Object on map
	public int mapObjectId[][];
	public int mapObjectEnId[][];
	public int mapObjectFloor[][];
	public int mapObjectName[][];
	public int mapObjectWorldX[][];
	public int mapObjectWorldY[][];
	public boolean mapObjectOpened[][];
	public int mapObjectKey[][];
	public SumType mapObjectSum[][];
	//NPCs on map
	public int mapNpcId[][];
	public int mapNpcName[][];
	public int mapNpcWorldX[][];
	public int mapNpcWorldY[][];
	public String mapNpcDirection[][];
}
