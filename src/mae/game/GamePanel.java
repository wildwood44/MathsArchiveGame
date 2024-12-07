package mae.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import mae.game.sound.Sound;
import mae.game.tile.CollisionChecker;
import mae.game.tile.TileManager;
import mae.game.cutscenes.CutsceneManager;
import mae.game.items.KeyCard;
import mae.game.tile.Map;
import mae.game.npc.NPC;
import mae.game.object.AssetManager;
import mae.game.object.AssetSetter;
import mae.game.player.Player;
import mae.game.puzzle.ConsolePuzzle;
import mae.game.puzzle.PuzzleSetter;


public class GamePanel extends JPanel implements Runnable {
	//SCREEN SETTINGS
	final int originalTileSize = 16; // 16x16 tile
	final int scale = 4;
	public final int tileSize = originalTileSize * scale;  // 64x64 tile
	public final int maxScreenCol = 12;
	public final int maxScreenRow = 8;
	public final int screenWidth = tileSize * maxScreenCol; //768 pixels
	public final int screenHeight = tileSize * maxScreenRow; //576 pixels
	//WORLD SETTINGS
	public final int maxWorldCol = 52;
	public final int maxWorldRow = 50;
	//public final int worldWidth = maxWorldCol * tileSize;
	//public final int worldHeight = maxWorldRow * tileSize;
	public final int maxMap = 156;
	// FOR FULL SCREEN
	int screenWidth2 = screenWidth;
	int screenHeight2 = screenHeight;
	BufferedImage tempScreen;
	Graphics2D g2;
	public boolean fullScreenOn = false;
	//FPS
	int FPS = 60;
	//SYSTEM
	public TileManager tileM;
	public KeyHandler keyH = new KeyHandler(this);
	public Sound music = new Sound();
	public Sound se = new Sound();
	public CollisionChecker cChecker;
	public AssetSetter aSetter = new AssetSetter(this);
	public AssetManager am = new AssetManager(this);
	public UI ui = new UI(this);
	public EventHandler eHandler;
	Config config = new Config(this);
	Thread gameThread;
	int playerY = 100;
	int playerX = 100;
	int playerSpeed = 4;
	public GameState gameState;
	public boolean cutsceneOn = false;
	public Map currentMap;
	public Integer selectedObj;
	public BufferedImage background;
	public Entity[][] obj = new Entity[maxMap][25];
	public NPC[][] npc = new NPC[maxMap][5];
	public Map[] maps;
	public PuzzleSetter ps = new PuzzleSetter(this);
	public SaveLoad saveLoad = new SaveLoad(this);
	public EntityGenerator eGenerator = new EntityGenerator(this);
	public CutsceneManager csManager = new CutsceneManager(this);
	public Player player = new Player(this, keyH);
	public Story s = new Story();
	public KeyCard kc[] = new KeyCard[10];
	public int currentCard = 0;
	public ArrayList<Entity> entityList = new ArrayList<>();
	//Testing
	public boolean testDoor = false;
	public boolean testKey = false;
	public int startFloor = 0;

	public GamePanel() {
		obj = new Entity[maxMap][156];
		maps = new Map[maxMap];
		player = new Player(this, keyH);
		entityList = new ArrayList<>();
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}

	public void setupGame() {
		aSetter.setPuzzles();
		aSetter.setItem();
		aSetter.setObject();
		aSetter.setNPC();
		aSetter.setMaps();
		gameState = GameState.titleState;
		currentMap = maps[startFloor];
		kc[0].opened = true;
		//Test
		kc[0].setValue(0);
		tileM = new TileManager(this);
		eHandler = new EventHandler(this);
		cChecker = new CollisionChecker(this);
		tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D) tempScreen.getGraphics();
		//playMusic(31);
		if(fullScreenOn) {
			setFullScreen();
		}
	}
	
	public void setFullScreen() {
		// GET LOCAL SCREEN SERVICE
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		gd.setFullScreenWindow(Main.window);
		// GET FULL SCREEN WIDTH AND HEIGHT
		screenWidth2 = Main.window.getWidth();
		screenHeight2 = Main.window.getHeight();
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long timer = 0;
		int drawCount = 0;

		while (gameThread != null) {
			long currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			timer += currentTime - lastTime;
			lastTime = currentTime;
			if (delta >= 1) {
				update();
				drawToTempScreen();
				drawToScreen();
				delta--;
				drawCount++;
			}

			if (timer >= 1000000000L) {
				System.out.println("FPS:" + drawCount);
				drawCount = 0;
				timer = 0L;
			}
		}

	}

	public void update() {
		if (gameState == GameState.playState) {
			player.update();
			eHandler.checkCutscene();
			for(int i = 0; i < npc[currentMap.getId()].length; i++) {
				if(npc[currentMap.getId()][i] != null) {
					npc[currentMap.getId()][i].update();
					//iTile[currentMap.getId()][i].draw(g2);
				}
			}
		}
		if (gameState == GameState.pauseState) {
			
		}

	}

	public void drawToTempScreen() {
		// DEBUG
		long drawStart = 0L;
		if (this.keyH.showDebugText) {
			drawStart = System.nanoTime();
		}
		// TITLE SCREEN
		if (gameState == GameState.titleState || gameState == GameState.menuState ||
				gameState == GameState.saveState || gameState == GameState.optionsState||
				gameState == GameState.notifyState) {
			ui.draw(g2);
		// CUTSCENE
		} else if (gameState == GameState.cutsceneState) {
			csManager.draw(g2);
			ui.draw(g2);
		// PLAY
		} else if (gameState == GameState.playState) {
				tileM.draw(g2);
				// ADD ENTITIES TO LIST
				entityList.add(player);
				//OBJECTS
				for (int i = 0; i < obj[currentMap.getId()].length; i++) {
					if (obj[currentMap.getId()][i] != null) {
						entityList.add(obj[currentMap.getId()][i]);
					}
				}
				//NPC
				for (int i = 0; i < npc[currentMap.getId()].length; i++) {
					if (npc[currentMap.getId()][i] != null) {
						entityList.add(npc[currentMap.getId()][i]);
					}
				}
				//tileM.draw(g2);
				Collections.sort(entityList, new Comparator<Entity>() {
					@Override
					public int compare(Entity e1, Entity e2) {
						int result = Integer.compare(e1.worldY - 1, e2.worldY);
						return result;
					}
	
				});
	
				for (Entity element : entityList) {
					element.update();
					element.draw(g2);
				}
				entityList.clear();
				// UI
				ui.draw(g2);
		}

		if (keyH.showDebugText) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			g2.setFont(new Font("Arial", 0, 20));
			g2.setColor(Color.white);
			int x = 10;
			int y = 400;
			int lineHeight = 20;
			g2.drawString("WorldX" + player.worldX, x, y);
			y = y + lineHeight;
			g2.drawString("WorldY" + player.worldY, x, y);
			y += lineHeight;
			g2.drawString("Col" + (player.worldX + player.solidArea.x) / tileSize, x, y);
			y += lineHeight;
			g2.drawString("Row" + (player.worldY + player.solidArea.y) / tileSize, x, y);
			y += lineHeight;
			g2.drawString("Draw Time: " + passed, x, y);
		}

	}

	public void drawToScreen() {
		Graphics g = getGraphics();
		g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, (ImageObserver) null);
		g.dispose();
	}
	
	public void restart() {
		aSetter.setObject();
	}
	
	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}
	
	public void stopMusic() {
		music.stop();
	}
	
	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}
	
	public boolean objectExists(int objectId, int mapId) {
		for(int i = 0; i < obj[mapId].length; i++) {
			if(obj[mapId][i] != null &&
					obj[mapId][i].id == objectId) {
				return true;
			}
		}
		return false;
	}
	
	public boolean NpcExists(int npcId, int mapId) {
		for(int i = 0; i < npc[mapId].length; i++) {
			if(npc[mapId][i] != null &&
					npc[mapId][i].id == npcId) {
				return true;
			}
		}
		return false;
	}
}