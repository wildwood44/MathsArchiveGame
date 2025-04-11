package mae.game;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import mae.game.npc.NPC;

public class UI {
	GamePanel gp;
	Font arial_40;
	Graphics2D g2;
	public boolean messageOn = false;
	DecimalFormat dFormat = new DecimalFormat("#0.00");
	public String currentDialogue = "";
	public String nameDialogue = "";
	public String title = "";
	public String message = "";
	public Entity selectedObject;
	public int selectedObjectX;
	public int selectedObjectY;
	public int commandNum = 0;
	public int playerSlotCol = 0;
	public int playerSlotRow = 0;
	public int npcSlotCol = 0;
	public int npcSlotRow = 0;
	public int slotCol2 = 0;
	public int slotRow2 = 0;
	public int subState;
	public int firstValue = 0;
	public int bottomValue = 0;
	public int topValue = 0;
	public long counter = 0;
	public NPC npc;
	int charIndex = 0;
	String combinedText = "";
	public JPanel bgPanel[] = new JPanel[10];

	public UI(GamePanel gp) {
		this.gp = gp;
		arial_40 = new Font("Monospaced", 0, 40);
	}

	public void draw(Graphics2D g2) {
		this.g2 = g2;
		g2.setFont(arial_40);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setColor(Color.white);
		if (gp.gameState == GameState.titleState) {
			drawTitleScreen();
		}

		else if (gp.gameState == GameState.loadingState) {
			drawLoadingScreen();
		}

		else if (gp.gameState == GameState.playState) {
			drawPlayerMenu();
		}

		else if (gp.gameState == GameState.notifyState) {
			drawMessage();
		}

		else if (gp.gameState == GameState.pauseState) {
			drawPauseScreen();
		}

		else if (gp.gameState == GameState.talkingState) {
			drawDialogueScreen();
		}
		
		else if (gp.gameState == GameState.menuState) {
			drawMenuBarScreen();
		}

		else if (gp.gameState == GameState.saveState) {
			drawSaveScreen();
		}

		else if (gp.gameState == GameState.optionsState) {
			drawOptionsScreen();
		}

		else if (gp.gameState == GameState.fastTravelState) {
			drawFastTravel();
		}

		else if (gp.gameState == GameState.puzzleState) {
			drawPuzzleWindow();
		}
	}

	public void drawLabel(String text) {
		int x = 8 * gp.tileSize;
		int y = 5 * gp.tileSize;
		message = text;
		g2.setFont(g2.getFont().deriveFont(0, 44.0F));
		g2.setColor(Color.white);
		g2.drawString(message, x, y);
	}
	
	public void drawPlayerMenu() {
		g2.setColor(Color.black);
		g2.fillRect(0, gp.tileSize*4, gp.maxWorldRow * gp.tileSize, gp.maxWorldCol * (gp.tileSize/2));
		gp.kc[gp.currentCard].draw(g2);
		if(gp.items[gp.currentCard]!=null) {
			if(gp.items[gp.currentCard].opened) {
				gp.items[gp.currentCard].draw(g2);
			}
		}
		//drawUpIcon(gp.tileSize/2, gp.tileSize*6, gp.tileSize, gp.tileSize);
		/*g2.setColor(Color.WHITE);
		g2.drawRect(4, (int)(gp.tileSize*4.1), gp.tileSize*2, gp.tileSize*2);
		drawLabel(gp.currentMap.getName());*/
		g2.setFont(g2.getFont().deriveFont(0, 22.0F));
		if(gp.keyH.tagPressed) {
			g2.setColor(Color.CYAN);
			g2.drawRect(gp.tileSize*3, (int)(gp.tileSize*4.1), gp.tileSize*2, gp.tileSize*2);
		}
	}
	
	public void drawMessage() {
		String text = message;
		int x = getXforCenteredText(text);
		int y = gp.screenHeight/3;
		int lineY = y;
		for(String line : breakLines(message, 30, " ")) {
			x = getXforCenteredText(line);
			g2.setColor(Color.black);
			g2.drawString(line, x-1, lineY-1);
			g2.setColor(Color.white);
			g2.drawString(line, x, lineY);
			lineY += gp.tileSize;
		}
	}
	
	// DRAW PAUSE SCREEN
	public void drawPauseScreen() {
		String text = "PAUSED";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight/2;
		g2.drawString(text, x, y);
	}
	// DRAW DIALOGUE
	public void drawDialogueScreen() {
		if (selectedObject.dialogue[selectedObject.dialogueSet][selectedObject.dialogueIndex] != null) {
			selectedObject.update();
			selectedObject.draw(g2);
			gp.player.draw(g2);
		} else {
			//post dialogue
			selectedObject.dialogueIndex = 0;
			if (gp.gameState == GameState.cutsceneState) {
				gp.csManager.scenePhase++;
			}
			if (gp.gameState == GameState.talkingState) {
				gp.gameState = GameState.playState;
			}
		}

		if (gp.keyH.enterPressed) {
			selectedObject.dialogueIndex++;
			gp.keyH.enterPressed = false;
		}
	}

	public void drawMenuBarScreen() {
		int frameX = 20;
		int frameY = 25;
		int frameWidth = gp.tileSize * 4;
		int frameHeight = gp.tileSize * 7;
		drawDialogueWindow(frameX, frameY, frameWidth, frameHeight);
		int slotXstart = frameX + 0;
		int slotYstart = frameY + 15;
		int cursorX = slotXstart + gp.tileSize * playerSlotRow;
		int cursorY = (int) (slotYstart + gp.tileSize * 0.75D * playerSlotCol);
		int cursorWidth = gp.tileSize * 2;
		int cursorHeight = 30;
		g2.setFont(g2.getFont().deriveFont(0, 22.0F));
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke());
		g2.drawString("Save", 30, gp.tileSize);
		g2.drawString("Options", 30, (int) (gp.tileSize * 1.75D));
		g2.drawString("Quit", 30, (int) (gp.tileSize * 2.5D));
		g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
	}
	
	public void drawSaveScreen() {
		int frameX = gp.tileSize * 5;
		int frameY = 25;
		int frameWidth = gp.tileSize * 6;
		int frameHeight = gp.tileSize * 2;
		int slotXstart = frameX + 15;
		int slotYstart = frameY + gp.tileSize + gp.originalTileSize;
		int cursorX = slotXstart;
		int cursorY = (int) (slotYstart + ((gp.tileSize * 2) * commandNum));
		drawDialogueWindow(frameX, frameY, frameWidth, frameHeight);
		int i = 1;
		g2.setColor(Color.white);
		g2.drawString("File " + i, frameX + gp.tileSize, frameY + gp.tileSize + gp.originalTileSize);
		frameY += gp.tileSize * 2;
		drawDialogueWindow(frameX, frameY, frameWidth, frameHeight);
		i++;
		g2.setColor(Color.white);
		g2.drawString("File " + i, frameX + gp.tileSize, frameY + gp.tileSize + gp.originalTileSize);
		frameY += gp.tileSize * 2;
		drawDialogueWindow(frameX, frameY, frameWidth, frameHeight);
		i++;
		g2.setColor(Color.white);
		g2.drawString("File " + i, frameX + gp.tileSize, frameY + gp.tileSize + gp.originalTileSize);
		g2.drawString(">", cursorX, cursorY);
		if(gp.keyH.enterPressed) {
			frameX = gp.tileSize * 4;
			frameY = gp.tileSize * 3;
			frameWidth = gp.tileSize * 4;
			frameHeight = gp.tileSize;
			drawDialogueWindow(frameX, frameY, frameWidth, frameHeight);
			g2.setFont(g2.getFont().deriveFont(0, 22.0F));
			g2.setColor(Color.white);
			g2.setStroke(new BasicStroke());
			g2.drawString("Game Saved!", frameX + 40, frameY + 40);
		}
	}
	
	public void drawOptionsScreen() {
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(22F));
		// SUB WINDOW
		int frameX = gp.tileSize * 2;
		int frameY = gp.tileSize;
		int frameWidth = gp.tileSize * 8;
		int frameHeight = gp.tileSize * 6;
		drawDialogueWindow(frameX, frameY, frameWidth, frameHeight);
		switch(subState) {
		case 0: options_top(frameX, frameY); break;
		case 1: options_fullScreenNotification(frameX, frameY); break;
		case 2: options_control(frameX, frameY); break;
		}
		gp.keyH.enterPressed = false;
	}
	
	public void options_top(int frameX, int frameY) {
		g2.setColor(Color.white);
		int textX;
		int textY;
		//Title
		String text = "Options";
		textX = getXforCenteredText(text);
		textY = frameY + gp.tileSize;
		g2.drawString(text, textX, textY);
		// FULL SCREEN ON/OFF
		textX  = frameX + gp.tileSize;
		textY += gp.tileSize + 2;
		g2.drawString("Full Screen", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed) {
				if(!gp.fullScreenOn) {
					gp.fullScreenOn = true;
				} else {
					gp.fullScreenOn = false;
				}
				subState = 1;
			}
		}
		// MUSIC
		textY += gp.tileSize/2;
		g2.drawString("Music", textX, textY);
		if(commandNum == 1) {
			g2.drawString(">", textX-25, textY);
		}
		// SOUND EFFECTS
		textY += gp.tileSize/2;
		g2.drawString("SE", textX, textY);
		if(commandNum == 2) {
			g2.drawString(">", textX-25, textY);
		}
		// CONTROL
		textY += gp.tileSize/2;
		g2.drawString("Control", textX, textY);
		if(commandNum == 3) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed) {
				subState = 2;
				commandNum = 0;
			}
		}
		// BACK
		textY += gp.tileSize * 2;
		g2.drawString("Back", textX, textY);
		if(commandNum == 4) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed) {
				gp.gameState = GameState.menuState;
			}
		}
		// FULL SCREEN CHECK BOX
		textX = frameX + gp.tileSize * 5;
		textY = frameY + gp.tileSize + 46;
		g2.setStroke(new BasicStroke());
		g2.drawRect(textX, textY, 24, 24);
		if(gp.fullScreenOn) {
			g2.fillRect(textX, textY, 24, 24);
		}
		// MUSIC VOLUME
		textY += gp.tileSize/2;
		g2.drawRect(textX, textY, 120, 24);
		int volumeWidth = 24 * gp.music.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		// SOUND EFFECTS VOLUME
		textY += gp.tileSize/2;
		g2.drawRect(textX, textY, 120, 24);
		volumeWidth = 24 * gp.se.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		gp.config.saveConfig();
	}
	
	public void options_fullScreenNotification(int frameX, int frameY) {
		g2.setColor(Color.white);
		int textX = frameX + gp.tileSize;
		int textY = frameY + gp.tileSize*2;
		currentDialogue = "The change will take effect after restarting the game";
		for (String line : breakLines(currentDialogue,30," ")) {
			g2.drawString(line, textX, textY);
			textY += 40;
		}
		textY  = frameY + gp.tileSize * 5;
		g2.drawString("Back", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed) {
				subState = 0;
			}
		}
	}
	
	public void options_control(int frameX, int frameY) {
		g2.setColor(Color.white);
		// TITLE
		String title = "Control";
		int textX = getXforCenteredText(title);
		int textY = frameY + gp.tileSize;
		g2.drawString(title, textX, textY);
		textX = frameX + gp.tileSize;
		textY += gp.tileSize;
		g2.drawString("Move", textX, textY);textY+=gp.tileSize/2;
		g2.drawString("Select", textX, textY);textY+=gp.tileSize/2;
		g2.drawString("Confirm", textX, textY);textY+=gp.tileSize/2;
		g2.drawString("Menu", textX, textY);textY+=gp.tileSize/2;
		g2.drawString("Pause", textX, textY);textY+=gp.tileSize/2;
		textX = frameX + gp.tileSize * 6;
		textY = frameY + gp.tileSize * 2;
		g2.drawString("WASD", textX, textY);textY+=gp.tileSize/2;
		g2.drawString("1-9", textX, textY);textY+=gp.tileSize/2;
		g2.drawString("ENTER", textX, textY);textY+=gp.tileSize/2;
		g2.drawString("ESC", textX, textY);textY+=gp.tileSize/2;
		g2.drawString("Space", textX, textY);textY+=gp.tileSize/2;
		// BACK
		textX = frameX + gp.tileSize;
		textY = frameY + gp.tileSize * 5;
		g2.drawString("Back", textX, textY);
		if(commandNum == 0) {
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed) {
				subState = 0;
			}
		}
	}
	
	public void drawGameOverScreen() {
		g2.setColor(new Color(0,0,0,150));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		int x;
		int y;
		String text = "Game Over";
		//Shadow
		g2.setColor(Color.black);
		x = getXforCenteredText(text);
		y = gp.tileSize*4;
		//Main
		g2.setColor(Color.white);
		g2.drawString(text, x-4, y-4);
		//Quit
		g2.setFont(g2.getFont().deriveFont(50f));
		text = "Quit";
		x = getXforCenteredText(text);
		y += gp.tileSize*4;
		
	}
	
	//DRAW FAST TRAVEL
	public void drawFastTravel() {
		int frameX = gp.tileSize * 3;
		int frameY = 25;
		int frameWidth = gp.tileSize * 6;
		int frameHeight = gp.tileSize * 3;
		int slotXstart = frameX + 15;
		int slotYstart = frameY + gp.originalTileSize;
		int cursorX = slotXstart + (int)(gp.tileSize * 1.25D * playerSlotRow);
		int cursorY = (int) (slotYstart + gp.tileSize * playerSlotCol);
		drawDialogueWindow(frameX, frameY, frameWidth, frameHeight);
		int cursorWidth = gp.tileSize/2;
		int cursorHeight = 30;
		g2.setFont(g2.getFont().deriveFont(0, 22.0F));
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke());
		g2.drawString(gp.squared[0]+"", slotXstart, gp.tileSize);
		g2.drawString("4", slotXstart, (int) (gp.tileSize * 2));
		g2.drawString("9", slotXstart, (int) (gp.tileSize * 3));
		g2.drawString("16", slotXstart + (int) (gp.tileSize * 1.25D), gp.tileSize);
		g2.drawString("25", slotXstart + (int) (gp.tileSize * 1.25D), gp.tileSize * 2);
		g2.drawString("36", slotXstart + (int) (gp.tileSize * 1.25D), gp.tileSize * 3);
		g2.drawString("49", slotXstart + (int) (gp.tileSize * 2.5D), gp.tileSize);
		g2.drawString("64", slotXstart + (int) (gp.tileSize * 2.5D), gp.tileSize * 2);
		g2.drawString("81", slotXstart + (int) (gp.tileSize * 2.5D), gp.tileSize * 3);
		g2.drawString("100", slotXstart + (int) (gp.tileSize * 3.75D), gp.tileSize);
		g2.drawString("121", slotXstart + (int) (gp.tileSize * 3.75D), gp.tileSize * 2);
		g2.drawString("144", slotXstart + (int) (gp.tileSize * 3.75D), gp.tileSize * 3);
		g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
	}
	
	//DRAW PUZZLE WINDOW
	public void drawPuzzleWindow() {
		int frameX = gp.tileSize;
		int frameY = 25;
		int frameWidth = gp.tileSize * 10;
		int frameHeight = gp.tileSize * 6;
		drawDialogueWindow(frameX, frameY, frameWidth, frameHeight);
		g2.setFont(g2.getFont().deriveFont(0, 22.0F));
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke());
		selectedObject.drawPuzzle(g2);
	}

	public int getItemIndexOnSlot() {
		int itemIndex = slotCol2 + slotRow2 * 5;
		return itemIndex;
	}
	
	//Automatic line breaks
		public String[] breakLines(String text, int size, String regex) {
			ArrayList<String> lines = new ArrayList<String>();
			try {
				while(text.length() > 0){
					int pos = text.lastIndexOf(regex, size);
					if (size > text.length()) {
						pos = text.length() - 1;
					} else if (pos <= 0) {
						pos = text.lastIndexOf("(", size);
						size++;
					}
					//System.out.println(pos + " " + text + " " + size + " " + regex);
					try {
						String found = text.substring(0, pos + 1);
						text = text.substring(pos + 1);
						lines.add(found);
					} catch (StringIndexOutOfBoundsException e) {
						String found = text.substring(0, pos + 1);
						text = text.substring(pos + 1);
						lines.add(found);
					}
				}
			} catch (Exception e) {
				System.out.println(e);
			}
			lines.removeAll(Arrays.asList("", null));
			String[] lineBreaks = lines.toArray(new String[lines.size()]);
			return lineBreaks;
		}
	
	private void drawUpIcon(int x, int y, int width, int height) {
		BufferedImage image;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/res/icons/icons8-sort-up-30.png"));
			if (image != null) {
				g2.drawImage(image, x, y, width, height, null);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void drawDownIcon(int x, int y, int width, int height) {
		BufferedImage image;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/res/icons/icons8-sort-down-30.png"));
			if (image != null) {
				g2.drawImage(image, x, y, width, height, null);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void drawDialogueWindow(int x, int y, int width, int height) {
		try {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
			Color c = new Color(255, 255, 255);
			g2.setColor(c);
			g2.fillRoundRect(x, y, width, height, 35, 35);
			c = new Color(0, 0, 0, 210);
			g2.setColor(c);
			g2.setStroke(new BasicStroke(5.0F));
			g2.fillRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	public void drawSubWindow(int x, int y, int width, int height) {
		Color c = new Color(0, 0, 0);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke());
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
	}

	public void drawTitleScreen() {
		switch(subState) {
		case 0 : drawTitleMenuScreen(); break;
		case 1 : drawLoadScreen(); break;
		}
	}
	
	public void drawTitleMenuScreen() {
		g2.setColor(new Color(0,0,0));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		//TITLE NAME
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 65.0F));
		String text = "Escape the archives";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight / 3;
		//SHADOW
		g2.setColor(Color.gray);
		g2.drawString(text, x+5, y+5);
		//MAIN COLOUR
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		g2.setFont(g2.getFont().deriveFont(1, 40.0F));
		//New Game
		text = "New Game";
		x = getXforCenteredText(text);
		y = (int) (y + gp.tileSize * 2);
		g2.drawString(text, x, y);
		if (commandNum == 0) {
			g2.drawString(">", x - gp.tileSize, y);
		}
		//Load Game
		text = "Load Game";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if (commandNum == 1) {
			g2.drawString(">", x - gp.tileSize, y);
			if(gp.keyH.enterPressed) {
				subState = 1;
			}
		}
		//Quit Game
		text = "Quit";
		x = getXforCenteredText(text);
		y += gp.tileSize;
		g2.drawString(text, x, y);
		if (commandNum == 2) {
			g2.drawString(">", x - gp.tileSize, y);
		}
	}
	//LOADING SCREEN
	public void drawLoadingScreen() {
		g2.setColor(new Color(0,0,0));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		String text = "Loading...";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight / 3;
		//SHADOW
		g2.setColor(Color.gray);
		g2.drawString(text, x+5, y+5);
		//MAIN COLOUR
		g2.setColor(Color.white);
		g2.drawString(text, x, y);
		g2.setFont(g2.getFont().deriveFont(1, 40.0F));
		//DRAW RECT
		y += gp.tileSize/2;
		int loadingComplete = 240;
		int loadingProgress = 0;
		if(gp.loadingProgress!=0) {
			loadingProgress = (gp.loadingProgress/100)*loadingComplete;
		}
		g2.drawRect(x, y, loadingComplete, 24);
		g2.fillRect(x, y, loadingProgress, 24);
	}
	
	public void drawLoadScreen() {
		g2.setColor(new Color(0,0,0));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		int frameX = gp.tileSize * 5;
		int frameY = 25;
		int frameWidth = gp.tileSize * 6;
		int frameHeight = gp.tileSize * 2;
		int slotXstart = frameX + 15;
		int slotYstart = frameY + gp.tileSize + gp.originalTileSize;
		int cursorX = slotXstart;
		int cursorY = (int) (slotYstart + ((gp.tileSize * 2) * commandNum));
		drawDialogueWindow(frameX, frameY, frameWidth, frameHeight);
		int i = 1;
		g2.setColor(Color.white);
		g2.drawString("File " + i, frameX + gp.tileSize, slotYstart);
		frameY += gp.tileSize * 2;
		drawDialogueWindow(frameX, frameY, frameWidth, frameHeight);
		i++;
		g2.setColor(Color.white);
		g2.drawString("File " + i, frameX+ gp.tileSize, slotYstart + ((gp.tileSize * 2)));
		frameY += gp.tileSize * 2;
		drawDialogueWindow(frameX, frameY, frameWidth, frameHeight);
		i++;
		g2.setColor(Color.white);
		g2.drawString("File " + i, frameX+ gp.tileSize, slotYstart + ((gp.tileSize * 4)));
		g2.drawString(">", cursorX, cursorY);
	}
	
	public int countFrequencies(ArrayList<Entity> list) {
        // hashmap to store the frequency of element
        Map<Entity, Integer> hm = new HashMap<Entity, Integer>();
        int count = 0;
        for (Entity i : list) {
            Integer j = hm.get(i);
            hm.put(i, (j == null) ? 1 : j + 1);
            count = j;
        }
        // displaying the occurrence of elements in the arraylist
        for (Map.Entry<Entity, Integer> val : hm.entrySet()) {
            System.out.println("Element " + val.getKey() + " "
                               + "occurs"
                               + ": " + val.getValue() + " times");
        }
        return count;
    }

	public int getXforCenteredText(String text) {
		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth / 2 - length / 2;
		return x;
	}
	
	public int getXforAlignToRightText(String text, int tailX) {
		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = tailX - length;
		return x;
	}

	public void resetSlots() {
		playerSlotRow = 0;
		playerSlotCol = 0;
		slotRow2 = 0;
		slotCol2 = 0;
		commandNum = 0;
	}
}