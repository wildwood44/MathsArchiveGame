package mae.game;

import java.io.Serializable;

public class Story implements Serializable {
	public int chapter;
	public int part;
	public boolean[] swh;
	public boolean gameStart;
	public boolean credits;
	public int[] branchSwitch;
	//public boolean[] PKSwitch = new boolean[]{true, true, true, true, true, true};
	
	public Story() {
		chapter = 0;
		part = 1;
		swh = new boolean[]{true, false, false};
		gameStart = true;
		credits = false;
		branchSwitch = new int[1];
	}
}