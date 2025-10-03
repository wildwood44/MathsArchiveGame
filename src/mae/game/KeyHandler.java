package mae.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import mae.game.items.Itm_Teleporter;
import mae.game.tile.TileManager;

public class KeyHandler implements KeyListener {
	GamePanel gp;
	public boolean upPressed;
	public boolean downPressed;
	public boolean leftPressed;
	public boolean rightPressed;
	public boolean enterPressed;
	public boolean skipPressed;
	public boolean tagPressed;
	public boolean numberPressed;
	public boolean showDebugText = false;

	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		GameState gameState = gp.gameState;
		if (gameState == GameState.titleState) {
			titleState(code);
		} else if (gp.gameState == GameState.menuState) {
			menuState(code);
		} else if (gp.gameState == GameState.pauseState) {
			pauseState(code);
		} else if (gp.gameState == GameState.notifyState) {
			notificationState(code);
		} else if (gp.gameState == GameState.talkingState) {
			dialogueState(code);
		} else if (gp.gameState == GameState.helpState) {
			helpState(code);
		} else if (gp.gameState == GameState.saveState) {
			saveState(code);
		} else if (gp.gameState == GameState.optionsState) {
			optionsState(code);
		} else if (gp.gameState == GameState.fastTravelState) {
			fastTravelState(code);
		} else if (gp.gameState == GameState.puzzleState) {
			puzzleState(code);
		} else if (gp.gameState == GameState.playState) {
			playState(code);
		}

	}
	
	public void titleState(int code) {
		if (code == KeyEvent.VK_ESCAPE) {
			if(gp.ui.subState == 1 || gp.ui.subState == 2) {
				gp.ui.commandNum = 1;
				gp.ui.subState = 0;
			}
		}
		if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
			gp.playSE(0);
			gp.ui.commandNum = getNext(gp.ui.commandNum, 2);
		}

		if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			gp.playSE(0);
			gp.ui.commandNum = getPrev(gp.ui.commandNum, 2);
		}

		if (code == KeyEvent.VK_ENTER) {
			if(gp.ui.subState == 0) {
				if (gp.ui.commandNum == 0) {
					gp.gameState = GameState.loadingState;
					gp.loadingProgress = 0;
					gp.ui.resetSlots();
					gp.loadingProgress = 100;
					gp.gameState = GameState.playState;
				}
				if (gp.ui.commandNum == 1) {
					gp.ui.commandNum = 0;
					gp.ui.subState = 1;
				}
				if (gp.ui.commandNum == 2) {
					System.exit(0);
				}
			} else if(gp.ui.subState == 1) {
				gp.gameState = GameState.loadingState;
				gp.loadingProgress = 0;
				gp.saveLoad.load(gp.ui.commandNum);
				gp.loadingProgress = 25;
				gp.ui.resetSlots();
				gp.loadingProgress = 50;
				gp.tileM = new TileManager(gp);
				gp.loadingProgress = 75;
				gp.eHandler = new EventHandler(gp);
				gp.loadingProgress = 100;
				gp.playSE(1);
				gp.gameState = GameState.playState;
			}
		}
	}

	public void dialogueState(int code) {
		if (code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}
		if (code == KeyEvent.VK_Q) {
			skipPressed = true;
		}

		if (code == 84) {
			if (!showDebugText) {
				showDebugText = true;
			} else if (showDebugText) {
				showDebugText = false;
			}
		}
	}
	
	public void	menuState(int code) {
		switch (code) {
		case KeyEvent.VK_ENTER :
			if (gp.ui.playerSlotCol == 0) { //Help
				gp.gameState = GameState.helpState;
			} else if (gp.ui.playerSlotCol == 1) { //Save game
				gp.gameState = GameState.saveState;
			} else if (gp.ui.playerSlotCol == 2) { //Fast-Travel
				gp.gameState = GameState.fastTravelState;
			} else if (gp.ui.playerSlotCol == 3) { //Open options
				gp.gameState = GameState.optionsState;
				gp.ui.resetSlots();
			} else if (gp.ui.playerSlotCol == 4) { //Quit game
				gp.gameState = GameState.titleState;
				gp.ui.commandNum = 0;
				gp.restart();
			}
			break;
		case KeyEvent.VK_ESCAPE :
			gp.gameState = GameState.playState;
			break;
		case KeyEvent.VK_UP :
		case KeyEvent.VK_W :
			//gp.playSE(2);
			gp.ui.playerSlotCol = getNext(gp.ui.playerSlotCol, 4);
			break;
		case KeyEvent.VK_DOWN :
		case KeyEvent.VK_S :
			//gp.playSE(2);
			gp.ui.playerSlotCol = getPrev(gp.ui.playerSlotCol, 4);
		}
	}
	
	public void helpState(int code) {
		switch (code) {
		case KeyEvent.VK_ESCAPE :
			gp.ui.resetSlots();
			gp.gameState = GameState.menuState;
			break;
		case KeyEvent.VK_ENTER :
			enterPressed = true;
			break;
		}
	}
	
	public void saveState(int code) {
		switch (code) {
		case KeyEvent.VK_ESCAPE :
			gp.ui.resetSlots();
			gp.gameState = GameState.menuState;
			break;
		case KeyEvent.VK_ENTER :
			gp.playSE(3);
			if(gp.ui.commandNum == 0) {
				gp.saveLoad.save(1);
			} else if(gp.ui.commandNum == 1) {
				gp.saveLoad.save(2);
			} else if(gp.ui.commandNum == 2) {
				gp.saveLoad.save(3);
			}
			gp.saveLoad.save(0);
			gp.gameState = GameState.playState;
			break;
		case KeyEvent.VK_W :
		case KeyEvent.VK_UP :
			gp.ui.commandNum = getNext(gp.ui.commandNum, 2);
			break;
		case KeyEvent.VK_DOWN :
		case KeyEvent.VK_S :
			gp.ui.commandNum = getPrev(gp.ui.commandNum, 2);
			break;
		}
	}

	public void optionsState(int code) {
		int maxCommandNum = 0;
		switch (gp.ui.subState) {
		case 0: maxCommandNum =  4;
		}
		switch(code) {
		case KeyEvent.VK_ESCAPE: gp.gameState = GameState.menuState; break;
		case KeyEvent.VK_ENTER: enterPressed = true; break; 
		case KeyEvent.VK_UP :
		case KeyEvent.VK_W :
			gp.ui.commandNum = getNext(gp.ui.commandNum, maxCommandNum);
			break;
		case KeyEvent.VK_DOWN :
		case KeyEvent.VK_S :
			gp.ui.commandNum = getPrev(gp.ui.commandNum, maxCommandNum);
			break;
		case KeyEvent.VK_A :
			if(gp.ui.subState == 0) {
				if(gp.ui.commandNum == 1 && gp.music.volumeScale > 0) {
					gp.music.volumeScale--;
					gp.music.checkVolume();
					gp.playSE(1);
				}
				if(gp.ui.commandNum == 2 && gp.se.volumeScale > 0) {
					gp.se.volumeScale--;
					gp.playSE(1);
				}
			}
			break;
		case KeyEvent.VK_D :
			if(gp.ui.subState == 0) {
				if(gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
					gp.music.volumeScale++;
					gp.music.checkVolume();
					gp.playSE(1);
				}
				if(gp.ui.commandNum == 2 && gp.se.volumeScale < 5) {
					gp.se.volumeScale++;
					gp.playSE(1);
				}
			}
		}
	}

	public void fastTravelState(int code) {
		switch (code) {
		case KeyEvent.VK_ESCAPE :
			gp.ui.resetSlots();
			gp.gameState = GameState.menuState;
			break;
		case KeyEvent.VK_ENTER: 
			enterPressed = true;
			Itm_Teleporter tp = (Itm_Teleporter) gp.items[1];
			int room = tp.getRoom(gp.ui.playerSlotRow,gp.ui.playerSlotCol);
			gp.eHandler.teleport(gp.maps[room+11], 4, 3);
			gp.ui.resetSlots();
			gp.gameState = GameState.playState;
			break;
		case KeyEvent.VK_UP :
		case KeyEvent.VK_W :
			//gp.playSE(2);
			gp.ui.playerSlotCol = getNext(gp.ui.playerSlotCol, 2);
			break;
		case KeyEvent.VK_DOWN :
		case KeyEvent.VK_S :
			//gp.playSE(2);
			gp.ui.playerSlotCol = getPrev(gp.ui.playerSlotCol, 2);
			break;
		case KeyEvent.VK_LEFT :
		case KeyEvent.VK_A :
			//gp.playSE(2);
			gp.ui.playerSlotRow = getNext(gp.ui.playerSlotRow, 3);
			break;
		case KeyEvent.VK_RIGHT :
		case KeyEvent.VK_D :
			//gp.playSE(2);
			gp.ui.playerSlotRow = getPrev(gp.ui.playerSlotRow, 3);
			break;
		}
	}
	
	public void gameOverState(int code) {
		switch (code) {
		case KeyEvent.VK_ENTER :
			//gp.restart();
			gp.gameState = GameState.titleState;
			break;
		}
	}
	
	public void notificationState(int code) {
		switch (code) {
		case KeyEvent.VK_0:
			gp.currentCard = gp.kc[gp.currentCard].setCard(0);
			gp.gameState = GameState.playState;
			break;
		case KeyEvent.VK_1:
			gp.currentCard = gp.kc[gp.currentCard].setCard(1);
			gp.gameState = GameState.playState;
			break;
		case KeyEvent.VK_2:
			gp.currentCard = gp.kc[gp.currentCard].setCard(2);
			gp.gameState = GameState.playState;
			break;
		case KeyEvent.VK_3:
			gp.currentCard = gp.kc[gp.currentCard].setCard(3);
			gp.gameState = GameState.playState;
			break;
		case KeyEvent.VK_4:
			gp.currentCard = gp.kc[gp.currentCard].setCard(4);
			gp.gameState = GameState.playState;
			break;
		case KeyEvent.VK_5:
			gp.currentCard = gp.kc[gp.currentCard].setCard(5);
			gp.gameState = GameState.playState;
			break;
		case KeyEvent.VK_6:
			gp.currentCard = gp.kc[gp.currentCard].setCard(6);
			gp.gameState = GameState.playState;
			break;
		case KeyEvent.VK_7:
			gp.currentCard = gp.kc[gp.currentCard].setCard(7);
			gp.gameState = GameState.playState;
			break;
		case KeyEvent.VK_8:
			gp.currentCard = gp.kc[gp.currentCard].setCard(8);
			gp.gameState = GameState.playState;
			break;
		case KeyEvent.VK_9:
			gp.currentCard = gp.kc[gp.currentCard].setCard(9);
			gp.gameState = GameState.playState;
			break;
		default :
			gp.gameState = GameState.playState;
		}
	}
	
	public void puzzleState(int code) {
		switch (code) {
		case KeyEvent.VK_ESCAPE :
			gp.gameState = GameState.playState;
			break;
		case KeyEvent.VK_ENTER :
			enterPressed = true;
			if (gp.ui.selectedObject != null) {
				gp.ui.selectedObject.interact();
			}
			break;
		case KeyEvent.VK_0:
			gp.currentCard = gp.kc[gp.currentCard].setCard(0);
			numberPressed = true;
			if (gp.ui.selectedObject != null) {
				gp.ui.selectedObject.interact();
			}
			break;
		case KeyEvent.VK_1:
			gp.currentCard = gp.kc[gp.currentCard].setCard(1);
			numberPressed = true;
			if (gp.ui.selectedObject != null) {
				gp.ui.selectedObject.interact();
			}
			break;
		case KeyEvent.VK_2:
			gp.currentCard = gp.kc[gp.currentCard].setCard(2);
			numberPressed = true;
			if (gp.ui.selectedObject != null) {
				gp.ui.selectedObject.interact();
			}
			break;
		case KeyEvent.VK_3:
			gp.currentCard = gp.kc[gp.currentCard].setCard(3);
			numberPressed = true;
			if (gp.ui.selectedObject != null) {
				gp.ui.selectedObject.interact();
			}
			break;
		case KeyEvent.VK_4:
			gp.currentCard = gp.kc[gp.currentCard].setCard(4);
			numberPressed = true;
			if (gp.ui.selectedObject != null) {
				gp.ui.selectedObject.interact();
			}
			break;
		case KeyEvent.VK_5:
			gp.currentCard = gp.kc[gp.currentCard].setCard(5);
			numberPressed = true;
			if (gp.ui.selectedObject != null) {
				gp.ui.selectedObject.interact();
			}
			break;
		case KeyEvent.VK_6:
			gp.currentCard = gp.kc[gp.currentCard].setCard(6);
			numberPressed = true;
			if (gp.ui.selectedObject != null) {
				gp.ui.selectedObject.interact();
			}
			break;
		case KeyEvent.VK_7:
			gp.currentCard = gp.kc[gp.currentCard].setCard(7);
			numberPressed = true;
			if (gp.ui.selectedObject != null) {
				gp.ui.selectedObject.interact();
			}
			break;
		case KeyEvent.VK_8:
			gp.currentCard = gp.kc[gp.currentCard].setCard(8);
			numberPressed = true;
			if (gp.ui.selectedObject != null) {
				gp.ui.selectedObject.interact();
			}
			break;
		case KeyEvent.VK_9:
			gp.currentCard = gp.kc[gp.currentCard].setCard(9);
			numberPressed = true;
			if (gp.ui.selectedObject != null) {
				gp.ui.selectedObject.interact();
			}
			break;
		case KeyEvent.VK_LEFT :
		case KeyEvent.VK_A :
			leftPressed = true;
			if (gp.ui.selectedObject != null) {
				gp.ui.selectedObject.interact();
			}
			break;
		case KeyEvent.VK_RIGHT :
		case KeyEvent.VK_D :
			rightPressed = true;
			if (gp.ui.selectedObject != null) {
				gp.ui.selectedObject.interact();
			}
			break;
		}
	}
	
	public void pauseState(int code) {
		switch (code) {
		default :
			gp.gameState = GameState.playState;
		}
	}
	
	public void playState(int code) {
		switch (code) {
		case KeyEvent.VK_ENTER :
			enterPressed = true;
			System.out.println(tagPressed);
			if (gp.selectedObj != null) {
				gp.obj[1][gp.selectedObj].interact();
			} 
			break;
		case KeyEvent.VK_ESCAPE :
			gp.selectedObj = null;
			gp.gameState = GameState.menuState;
			break;
		case KeyEvent.VK_UP :
		case KeyEvent.VK_W :
			upPressed = true;
			break;
		case KeyEvent.VK_DOWN :
		case KeyEvent.VK_S :
			downPressed = true;
			break;
		case KeyEvent.VK_LEFT :
		case KeyEvent.VK_A :
			leftPressed = true;
			break;
		case KeyEvent.VK_RIGHT :
		case KeyEvent.VK_D :
			rightPressed = true;
			break;
		case KeyEvent.VK_Q :
			gp.currentCard = gp.kc[gp.currentCard].prevCard(gp.currentCard);
			break;
		case KeyEvent.VK_E :
			gp.currentCard = gp.kc[gp.currentCard].nextCard(gp.currentCard);
			break;
		case KeyEvent.VK_X :
			tagPressed = true;
			gp.items[gp.currentCard].use();
			break;
		case KeyEvent.VK_0:
			gp.currentCard = gp.kc[gp.currentCard].setCard(0);
			numberPressed = true;
			break;
		case KeyEvent.VK_1:
			gp.currentCard = gp.kc[gp.currentCard].setCard(1);
			numberPressed = true;
			break;
		case KeyEvent.VK_2:
			gp.currentCard = gp.kc[gp.currentCard].setCard(2);
			numberPressed = true;
			break;
		case KeyEvent.VK_3:
			gp.currentCard = gp.kc[gp.currentCard].setCard(3);
			numberPressed = true;
			break;
		case KeyEvent.VK_4:
			gp.currentCard = gp.kc[gp.currentCard].setCard(4);
			numberPressed = true;
			break;
		case KeyEvent.VK_5:
			gp.currentCard = gp.kc[gp.currentCard].setCard(5);
			numberPressed = true;
			break;
		case KeyEvent.VK_6:
			gp.currentCard = gp.kc[gp.currentCard].setCard(6);
			numberPressed = true;
			break;
		case KeyEvent.VK_7:
			gp.currentCard = gp.kc[gp.currentCard].setCard(7);
			numberPressed = true;
			break;
		case KeyEvent.VK_8:
			gp.currentCard = gp.kc[gp.currentCard].setCard(8);
			numberPressed = true;
			break;
		case KeyEvent.VK_9:
			gp.currentCard = gp.kc[gp.currentCard].setCard(9);
			numberPressed = true;
			break;
		case KeyEvent.VK_SPACE:
			gp.gameState = GameState.pauseState;
			break;
		case 82 :
			switch(gp.currentMap.getId())
			{
			case 0:
				gp.tileM.loadMap("/res/maps/map01",0);
				break;
			}

			break;
		case KeyEvent.VK_T :
			if (!showDebugText) {
				showDebugText = true;
			} else if (showDebugText) {
				showDebugText = false;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_ENTER :
				enterPressed = false;
				break;
			case KeyEvent.VK_LEFT :
			case KeyEvent.VK_A :
				leftPressed = false;
				break;
			case KeyEvent.VK_UP :
			case KeyEvent.VK_W :
				upPressed = false;
				break;
			case KeyEvent.VK_RIGHT :
			case KeyEvent.VK_D :
				rightPressed = false;
				break;
			case KeyEvent.VK_DOWN :
			case KeyEvent.VK_S :
				downPressed = false;
				break;
			case KeyEvent.VK_X :
				tagPressed = false;
				break;
			case KeyEvent.VK_0:
			case KeyEvent.VK_1:
			case KeyEvent.VK_2:
			case KeyEvent.VK_3:
			case KeyEvent.VK_4:
			case KeyEvent.VK_5:
			case KeyEvent.VK_6:
			case KeyEvent.VK_7:
			case KeyEvent.VK_8:
			case KeyEvent.VK_9:
				numberPressed = false;
				break;
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	public int getNext(int current, int size) {
		if (current != 0) {
			--current;
		} else {
			current = size;
		}

		return current;
	}

	public int getPrev(int current, int size) {
		if (current != size) {
			++current;
		} else {
			current = 0;
		}

		return current;
	}
}