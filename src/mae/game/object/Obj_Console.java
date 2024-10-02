package mae.game.object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import mae.game.EntityType;
import mae.game.GamePanel;
import mae.game.puzzle.Puzzle;

public class Obj_Console extends Object {
	GamePanel gp;
	//private static int id = -1;
	public boolean open = false;
	private int floor;
	private int unlock;

	public Obj_Console(GamePanel gp, int floor, Puzzle puzzle, int unlock) {
		super(gp);
		this.gp = gp;
		this.puzzle = puzzle;
		this.floor = floor;
		this.description = puzzle.printPuzzle();
		this.unlock = unlock;
		id = -1;
		type = EntityType.Object;
		if(!opened) {
			image = setup("/res/objects/Obj_Console1", gp.tileSize, gp.tileSize);
		} else {
			image = setup("/res/objects/Obj_Console2", gp.tileSize, gp.tileSize);
		}
		getImage(image);
		solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize*2);
		int size = gp.tileSize;
		//for(int lock: unlock) {
			for(int j = 0; j < gp.obj[floor].length - 1; j++) {
				if(gp.obj[floor][j] != null && gp.obj[floor][j].id == unlock){
					gp.obj[floor][j].setLock(true);
					break;
				}
			}
		//}
	}

	public void interact() {
		if(!opened) {
			System.out.println(description);
			System.out.println(gp.kc[gp.currentCard].useCard() + " " + floor +" "+ unlock);
			if(puzzle.isCorrect(gp.kc[gp.currentCard].useCard())) {
				isCorrect = true;
				gp.playSE(2);
				for(int j = 0; j < gp.obj[floor].length - 1; j++) {
					if(gp.obj[floor][j] != null && gp.obj[floor][j].id == unlock){
						gp.obj[floor][j].setLock(false);
						break;
					}
				}
				opened = true;
			} else {
				isWrong = true;
				gp.playSE(3);
			}
		}
		gp.keyH.enterPressed = false;
		gp.keyH.upPressed = false;
		gp.keyH.downPressed = false;
	}
	
	@Override
	public void getImage(BufferedImage image) {
		idle1 = setup("/res/objects/Obj_Console1", gp.tileSize, gp.tileSize);
		idle2 = setup("/res/objects/Obj_Console2", gp.tileSize, gp.tileSize);
	}
	
	public void display(Graphics2D g2, int screenX, int screenY) {
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		switch(direction) {
		case "idle":
			if(!opened) {image = idle1;} 
			else {image = idle2;}
			break;
		}
		g2.drawImage(image, tempScreenX, tempScreenY, null);
		if(isSelected) {
			drawSpeechBubble(g2, tempScreenX - gp.tileSize, tempScreenY - gp.tileSize*2);
		}
		
	}
}
