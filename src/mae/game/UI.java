package mae.game;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
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

		else if (gp.gameState == GameState.playState) {
			drawPlayerMenu();
		}

		else if (gp.gameState == GameState.notifyState) {
			drawMessage();
		}

		else if (gp.gameState == GameState.pauseState) {
			drawPauseScreen();
		}
		
		else if (gp.gameState == GameState.menuState) {
			drawMenuBarScreen();
		}
	}

	public void drawLabel(String text) {
		int x = 8 * gp.tileSize;
		int y = 5 * gp.tileSize;
		g2.setFont(g2.getFont().deriveFont(0, 44.0F));
		g2.setColor(Color.white);
		message = text;
		g2.drawString(message, x, y);
	}
	
	public void drawPlayerMenu() {
		g2.setColor(Color.black);
		g2.fillRect(0, gp.tileSize*4, gp.maxWorldRow * gp.tileSize, gp.maxWorldCol * (gp.tileSize/2));
		gp.kc[gp.currentCard].draw(g2);
		g2.setFont(g2.getFont().deriveFont(0, 22.0F));
		g2.setColor(Color.white);
		drawLabel(gp.currentMap.getName());
	}
	
	public void drawMessage() {
		String text = message;
		int x = getXforCenteredText(text);
		int y = gp.screenHeight/3;
		g2.drawString(text, x, y);
	}
	
	// DRAW PAUSE SCREEN
	public void drawPauseScreen() {
		String text = "PAUSED";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight/2;
		g2.drawString(text, x, y);
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

	public int getItemIndexOnSlot() {
		int itemIndex = slotCol2 + slotRow2 * 5;
		return itemIndex;
	}
	
	//Automatic line breaks
	public String[] breakLines(String text, int size) {
		ArrayList<String> lines = new ArrayList<String>();
		try {
			while(text.length() > 0){
				int pos = text.lastIndexOf("=", size);
				//Manual line break
				if(text.contains("£")) {
					pos = text.lastIndexOf("£", size);
					if (pos == -1) {
						pos = text.lastIndexOf(text.length()-1, size);
					}
					//text = text.replaceFirst("£", "");
					//pos -= 1;
				}
				if (size > text.length()) {
					pos = text.length() - 1;
				}
				System.out.println(size + " " + text.length());
				try {
					String found = text.substring(0, pos + 1);
					text = text.substring(pos + 1);
					lines.add(found);
				} catch (StringIndexOutOfBoundsException e) {
					String found = text.substring(0, pos + 1);
					text = text.substring(pos + 1);
					lines.add(found);
				}
				System.out.println(pos);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
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
	
	private void drawLockIcon(int x, int y, int width, int height) {
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
		g2.setBackground(Color.gray);
		g2.setFont(g2.getFont().deriveFont(1, 80.0F));
		String text = "Escape the archives";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight / 3;
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
	
	public void drawLoadScreen() {
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