package com.rfb;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Painter {
	
	Game game;
	
	public Painter(Game game) {
		this.game = game;
	}
	
	public void paintGame(Graphics g) {
		// Left side is center minus half the game board size
		int horizontal = ((int) game.getSize().getWidth() / 2) - (Game.BOARD_WIDTH * game.squareWidth() / 2);
		Piece current = game.getCurrent();
		for(int y=0; y < Game.BOARD_LENGTH; ++y) {
			for(int x=0; x < Game.BOARD_WIDTH; x++) {
				PieceType type = game.typeAt(x, y);
				drawSquare(g, 
					horizontal + x * game.squareWidth(), 
					0 + y * game.squareHeight(), 
					type);				
			}
		}
		
		// If there is a shape being handled, draw it
		if(!current.getPieceType().isBlank()) {
			for(int i=0; i<4; ++i) {
				int x = game.getCurrentX() + current.x(i);
				int y = game.getCurrentY() + current.y(i);
				drawSquare(g, 
						horizontal + x * game.squareWidth(),
						0 + y * game.squareHeight(),
						current.getPieceType()
					);
				
			}
		}
	}
	
	public void paintTitle(Graphics g) {
		int h = (int) game.getSize().getHeight();
		int w = (int) game.getSize().getWidth();
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, w, h);
		
		// Stuff to move
		int titleFontSize = 30;
		int subFontSize = 24;
		int hOffset = 5;
		String title = "Russian Falling Blocks";
		String sub = "Press B to begin";
		
		//int hAdjust = ((int) game.getSize().getWidth() / 2) - (Game.BOARD_WIDTH * game.squareWidth() / 2);
		int hPos = hOffset;// + hAdjust;
		int titleVpos = h/2;
		int subVpos = titleVpos + titleFontSize + 10;
		
		g.setColor(new Color(0,255,0));
		g.setFont(new Font("Arial",Font.BOLD, titleFontSize));
		g.drawString(title, hPos, titleVpos);
		
		g.setColor(new Color(100,200,100));
		g.setFont(new Font("Arial",Font.BOLD, subFontSize));
		g.drawString(sub, hPos, subVpos);
		
	}
	
	/**
	 * Draws a square piece
	 * @param g the Graphics container
	 * @param x the pixel horizontal position
	 * @param y the pixel vertical position
	 * @param shape the piece this square belongs to (used to determine color)
	 */
	void drawSquare(Graphics g, int x, int y, PieceType pieceType) {
		Color c = pieceType.getColor();
		g.setColor(c);
		
		if(pieceType.isBlank()) { // Fill whole block with blank			
			g.fillRect(x, y, game.squareWidth() , game.squareHeight());
		} else { // Fancy up the edges
			g.fillRect(x + 1, y + 1, game.squareWidth() - 2, game.squareHeight() - 2);
	
			// Bright line on the top and left
			g.setColor(c.brighter());
			g.drawLine(x, y + game.squareHeight() - 1, x, y); // x-x = left
			g.drawLine(x, y, x + game.squareWidth() - 1, y); //y-y=top
	
			// Dark line on the bottom and right
			g.setColor(c.darker());
			g.drawLine(x + 1, y + game.squareHeight() - 1, x + game.squareWidth() - 1, y //y+h-y+h=bottom
					+ game.squareHeight() - 1);
			g.drawLine(x + game.squareWidth() - 1, y + game.squareHeight() - 1, x //x+w-x+w=right
					+ game.squareWidth() - 1, y + 1);
		}
	}
}