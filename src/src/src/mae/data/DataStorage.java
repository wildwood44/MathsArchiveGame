package mae.data;

import java.io.Serializable;
import java.util.ArrayList;


public class DataStorage implements Serializable {
	//Character stats;
	public int maxHealth[];
	public int health[];
	public int maxStamina[];
	public int stamina[];
	public int baseAttack[];
	public int baseDefence[];
	public int baseAccuracy[];
	public int baseEvasion[];
	public int baseSpeed[];
	//Player character details
	public String direction;
	public int currentMap;
	public int worldX, worldY;
	//Player inventory
	public int shill;
	public ArrayList<Integer> itemId = new ArrayList<>();
	public ArrayList<Integer> itemAmount = new ArrayList<>();
	//Equipment
	public int currentHat[];
	public int currentShirt[];
	public int currentTrousers[];
	public int currentPrimary[];
	public int currentSecondary[];
	//Object on map
	public int mapObjectId[][];
	public int mapObjectName[][];
	public int mapObjectWorldX[][];
	public int mapObjectWorldY[][];
	public int mapObjectLootIds[][];
	public int mapObjectLootNames[][];
	public boolean mapObjectOpened[][];
	//NPCs on map
	public int mapNpcId[][];
	public int mapNpcName[][];
	public int mapNpcWorldX[][];
	public int mapNpcWorldY[][];
	public String mapNpcDirection[][];
	//Glossary
	public boolean found[][];
}
