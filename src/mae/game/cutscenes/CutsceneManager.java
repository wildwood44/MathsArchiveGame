package mae.game.cutscenes;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import mae.game.Entity;
import mae.game.EventHandler;
import mae.game.GamePanel;
import mae.game.GameState;
import mae.game.items.Item;
import mae.game.tile.TileManager;

public class CutsceneManager {
	GamePanel gp;
	Graphics2D g2;
	public int sceneNum;
	public int scenePhase;
	public int counter = 0;
	public float alpha = 0f; 
	int y;
	public String endCredit = "Written and developed by wildwood44";
	public int read = 0;
	public final int NA = 0;
	public final int prologue = 1;
	public final int chapter1Start = 2;
	public BufferedImage background;
	public Entity projectile;

	public CutsceneManager(GamePanel gp) {
		this.gp = gp;
	}

	public void draw(Graphics2D g2) {
		this.g2 = g2;
		switch (sceneNum) {
			case 1 : scene_ending(); break;
		}

	}

	//End Credits
	private void scene_ending() {
		if (scenePhase == 0) {
			if(fadeOut(0.005f)) {
				scenePhase++;
			}
		} else if (scenePhase == 1) {
			drawBlackBackground(alpha);
			alpha += 0.005f;
			if(alpha > 1f) {
				alpha = 1f;
			}
			String text = "Thank you for playing.\nThere is more coming soon.";
			drawString(alpha, 30f, 200, text, 70);
			if(counterReached(300)) {
				scenePhase++;
			}
		} else if (scenePhase == 2) {
			drawBlackBackground(1f);
			drawString(1f, 65f, gp.screenHeight/2, "Escape the archives", 40);
			drawString(1f, 65f, (gp.screenHeight/3)*2, "- Maths", 40);
			if(counterReached(300)) {
				scenePhase++;
			}
		} else if (scenePhase == 3) {
			drawBlackBackground(1f);
			y = gp.screenHeight/2;
			drawString(1f, 30f, y, endCredit, 40);
			if(counterReached(400)) {
				scenePhase++;
			}
		} else if (scenePhase == 4) {
			drawBlackBackground(1f);
			y--; 
			drawString(1f, 30f, y, endCredit, 40);
			if(counterReached(400)) {
				scenePhase++;
			}
		} else if (scenePhase == 5) {
			System.exit(0);
		}
	}
	
	private boolean moveCamera(String direction, int move, int speed) {
		boolean moveComplete = false;
		//actor = getActor(npcName);
		gp.player.direction = direction;
		switch(gp.player.direction) {
		case "up":
			gp.player.worldY -= speed;
			if(gp.player.worldY < gp.tileSize * move){
				moveComplete = true;
			}
			break;
		case "down":
			gp.player.worldY += speed;
			if(gp.player.worldY > gp.tileSize * move){
				moveComplete = true;
			}
			break;
		case "left":
			gp.player.worldX -= speed;
			if(gp.player.worldX < gp.tileSize * move){
				moveComplete = true;
			}
			break;
		case "right":
			gp.player.worldX += speed;
			if(gp.player.worldX > gp.tileSize * move){
				moveComplete = true;
			}
			break;
		}
		gp.player.update();
		//drawRoom();
		return moveComplete;
	}
	
	private void drawStage() {
		//OBJECTS
		for (int i = 0; i < gp.obj[gp.currentMap.getId()].length; i++) {
			if (gp.obj[gp.currentMap.getId()][i] != null) {
				gp.entityList.add(gp.obj[gp.currentMap.getId()][i]);
				//this.obj[1][i].draw(g2, this);
				gp.obj[gp.currentMap.getId()][i].draw(g2);
			}
		}
		//NPC
		for (int i = 0; i < gp.npc[gp.currentMap.getId()].length; i++) {
			if (gp.npc[gp.currentMap.getId()][i] != null) {
				//entityList.add(npc[currentMap.getId()][i]);
				gp.npc[gp.currentMap.getId()][i].draw(g2);
			}
		}
	}
	
	public void changeStage(int i) {
		gp.currentMap = gp.maps[i];
		gp.tileM = new TileManager(gp);
		gp.eHandler = new EventHandler(gp);
	}
	
	public boolean counterReached(int target) {
		boolean counterReached = false;
		counter++;
		if(counter > target) {
			counterReached = true;
			counter = 0;
		}
		return counterReached;
	}
	
	public boolean fadeOut(float fadeSpeed) {
		boolean fadeComp = false;
		alpha += fadeSpeed;
		if(alpha > 1f) {
			alpha = 1f;
		}
		drawBlackBackground(alpha);
		if(alpha == 1f) {
			fadeComp = true;
		}
		return fadeComp;
	}
	
	public boolean fadeIn(float fadeSpeed) {
		boolean fadeComp = false;
		alpha -= fadeSpeed;
		if(alpha < 0f) {
			alpha = 0f;
		}
		drawBlackBackground(alpha);
		if(alpha == 0f) {
			fadeComp = true;
		}
		drawBlackBackground(alpha);
		return fadeComp;
	}
	
	public void drawBlackBackground(float alpha) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g2.setColor(Color.black);
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
	}
	
	public void drawString(float alpha, float fontSize, int y, String text, int lineHeight) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(fontSize));
		for (String line : text.split("\n")) {
			int x = gp.ui.getXforCenteredText(line);
			g2.drawString(line, x, y);
			y += lineHeight;
		}
	}
	
}