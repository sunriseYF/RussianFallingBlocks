package com.rfb;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controller extends KeyAdapter {
	
	Game game;
	
	public Controller(Game game) {
		this.game = game;
	}
	
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		Piece current = game.getCurrent();
				
		// Handle play/pause controls
		if (keyCode == 'p' || keyCode == 'P') {
			game.playPause(keyCode);
			return;
		} else if(keyCode == 'h' || keyCode == 'H') {
			RussianFallingBlocks.showHelp(game);
			return;
		}
		
		// Handle quitting or starting the game
		if(keyCode == 'q' || keyCode == 'Q' || keyCode == KeyEvent.VK_ESCAPE) {
			if (game.isPaused() || !game.isStarted()) {
				game.exitPrompt();
			} else {
				game.playPause(keyCode);
				return;
			}
		//} else if(!game.isStarted() && keyCode != KeyEvent.VK_ALT && keyCode != KeyEvent.VK_TAB) {
		} else if((!game.isStarted() || game.isPaused()) && keyCode == KeyEvent.VK_B) {
			game.clearBoard();
			game.start();
			return;
		} else if(game.isGameOver()) {
			game.repaint();
			return;
		}
		
		if(keyCode == KeyEvent.VK_S) { 
			game.getSounds().toggleMusic();
		}
		
		if(current.getPieceType().isBlank() || game.isPaused()) return;					
		
        switch (keyCode) {
        case KeyEvent.VK_LEFT:
            game.tryMove(current, game.getCurrentX() - 1, game.getCurrentY());
            break;
        case KeyEvent.VK_RIGHT:
        	game.tryMove(current, game.getCurrentX() + 1, game.getCurrentY());
            break;
        case KeyEvent.VK_DOWN: // Rotate right, but allow shifting right from left edge or left from right edge
        	game.tryRotate(current.rotateRight(), game.getCurrentX(), game.getCurrentY());
            break;
        case KeyEvent.VK_UP:
        	game.tryRotate(current.rotateLeft(), game.getCurrentX(), game.getCurrentY());
            break;
        case KeyEvent.VK_SPACE:
        	game.dropDown();
            break;
        case 'd':
        case 'D':
        	game.oneLineDown();
            break;
        }
	}
	
}