package com.rfb;

import java.awt.Graphics;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener {

	private static final long serialVersionUID = 2152873003479416165L;

	/**
	 * The number of blocks that fit on the board horizontally
	 */
	public static final int BOARD_WIDTH = 10;
	
	/**
	 * The number of rows that fit on the board
	 */
	public static final int BOARD_LENGTH = 22;

	/**
	 * 0=Left < X < BoardWidth-1=Right <Br/> 0=Top < Y < BoardHeight-1=Bottom
	 */
	PieceType[][] board;
	RussianFallingBlocks parent;
	Sounds sounds;
	Timer timer;
	boolean isFallingFinished = false;
	boolean isStarted = false;
	boolean isPaused = false;
	boolean isGameOver = false;
	int score;
	int numOfLinesRemoved;
	int numOfLinesSkipped;
	Piece current;
	
	int currentX = 0;	
	int currentY = 0;

	// Levels!
	int level;
	int delay;

	public Game(RussianFallingBlocks parent) {
		this.parent = parent;
		this.sounds = new Sounds();
		this.board = new PieceType[BOARD_WIDTH][BOARD_LENGTH];
		setFocusable(true);
		addKeyListener(new Controller(this));
		clearBoard();
	}

	public void start() {
		this.current = new Piece();
		isStarted = true;
		isPaused = false;
		isGameOver = false;
        isFallingFinished = false;
        numOfLinesRemoved = 0;
    	numOfLinesSkipped = 0;
    	score = 0;
    	level = 1;
    	delay = Preferences.INITIAL_DELAY;
		timer = new Timer(delay, this);
		timer.start();
        clearBoard();
        sounds.playMusic();
        newPiece();
        timer.start();
	}

	public void playPause(int keyCode) {
		if(!isStarted) return;
		
		isPaused = !isPaused;
		if(isPaused) {
			timer.stop();
			sounds.stopMusic();
			if(keyCode == 'Q' || keyCode == 'q') parent.getStatusBar().updateStatus("Paused - press Q again to quit.");
			else parent.getStatusBar().updateStatus("Paused");
		} else {
			sounds.playMusic();
			timer.start();
			parent.getStatusBar().updateStatus("Playing");
		}
		

	}

	void clearBoard() {
		for (int i = 0; i < BOARD_WIDTH; i++) {
			for(int j = 0; j < BOARD_LENGTH; j++) {
				board[i][j] = new PieceType();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (isFallingFinished) {
			isFallingFinished = false;
			newPiece();
		} else {
			oneLineDown();
		}
	}

	/**
	 * Gets a random piece and tries to insert it into the board. If it can't do that, you lose forever.
	 */
	private void newPiece() {
		current.setRandomShape();
		currentX = BOARD_WIDTH / 2 + 1;
		currentY = current.minY();

		if (!tryMove(current, currentX, currentY)) {
			current.setPieceType(new PieceType());
			timer.stop();
			sounds.stopMusic();
			sounds.switchMusic();
			isStarted = false;
			isGameOver = true;
			parent.getStatusBar().setText("Game Over. Level "+level+", Final Score: "+String.valueOf(getScore()));
		}
	}
	
	public int getScore() {
		//return numOfLinesSkipped + 10 * numOfLinesRemoved;
		return score;
	}

	/**
	 * @param newPiece an already-positioned piece to test if it can be moved there
	 * @param newX the horizontal center of the piece
	 * @param newY the vertical center of the piece
	 * @return true if the piece has been placed
	 */
	boolean tryMove(Piece newPiece, int newX, int newY) {
		// For each coordinate, figure out if there are any conflicts or you run over an edge
		for (int i = 0; i < 4; ++i) {
			int x = newX + newPiece.x(i);
			int y = newY + newPiece.y(i);
			if (x < 0 || x >= BOARD_WIDTH || y < 0 || y >= BOARD_LENGTH)
				return false;
			if (!typeAt(x, y).isBlank())
				return false;
		}

		current = newPiece;
		this.currentX = newX;
		this.currentY = newY;
		repaint();
		return true;
	}
	
	/**
	 * @param newPiece an already-rotated piece attempting to be placed in the board
	 * @param newX the horizontal center of the piece
	 * @param newY the vertical center of the piece
	 * @param depth used to identify/prevent endless recursion and track
	 * @return true if the piece has been placed
	 */
	boolean tryRotate(Piece newPiece, int newX, int newY, int depth) {
		boolean moved = tryMove(newPiece, newX, newY);
		if(moved) return true;
		else if(depth > 4) return false;
		else {
			if(newPiece.minX() < 0) { // if on left, shift right
				moved = tryRotate(newPiece, newX + 1, newY, depth + 1);
			} else if(newPiece.maxX() >= BOARD_WIDTH) { // if on right, shift left
				moved = tryRotate(newPiece, newX - 1, newY, depth + 1);
			} else moved = false;
		}
		return moved;
	}
	
	/**
	 * @param newPiece an already-rotated piece attempting to be placed in the board
	 * @param newX the horizontal center of the piece
	 * @param newY the vertical center of the piece
	 * @return true if the piece has been placed
	 */
	boolean tryRotate(Piece newPiece, int newX, int newY) {
		return tryRotate(newPiece, newX, newY, 0);
	}

	/** 
	 * Determine width from height to keep the shape square - height more likely to be a restriction
	 * @return
	 */
	public int squareWidth() {
		if(Preferences.ORIENT_LIMIT_HEIGHT)
			return squareHeight();
		else
			return (int) getSize().getWidth() / BOARD_WIDTH;
	}

	int squareHeight() {
		if(Preferences.ORIENT_LIMIT_HEIGHT)
			return (int) getSize().getHeight() / BOARD_LENGTH;
		else
			return squareWidth();
	}

	PieceType typeAt(int x, int y) {
		return board[x][y];
	}

	void dropDown() {
		// Initialize newY to the current Y to test new values
		int newY = currentY;
		int linesToSkip = 0;
		// Try moving lower until you reach the bottom
		while (newY < BOARD_LENGTH) {
			if (!tryMove(current, currentX, newY + 1))
				break;
			++newY;
			++linesToSkip;
		}
		numOfLinesSkipped += linesToSkip;
		incrementScore(factorial(linesToSkip / 3));
		// Fire pieceDropped once finished
		pieceDropped();
	}

	private void incrementScore(int points) {
		// TODO Auto-generated method stub
		if((long) score + points > Integer.MAX_VALUE) {
			
		} else {
			score += points;
		}
	}

	void oneLineDown() {
		if (!tryMove(current, currentX, currentY + 1))
			pieceDropped();
	}

	/**
	 * Adds the piece into the blocks. Adds by piece type to preserve color
	 */
	private void pieceDropped() {
		//for each square in the piece, add it to the board   
		for (int i = 0; i < 4; ++i) {
			int x = currentX + current.x(i);
			int y = currentY + current.y(i);
			board[x][y] = current.getPieceType();
		}
		
		removeFullLines();

		if (!isFallingFinished)
			newPiece();
	}

	private void removeFullLines() {
		int numFullLines = 0;

		// For each line, check if it is full
		for (int j = 0; j < BOARD_LENGTH; j++) {
			boolean lineIsFull = true;
			
			// Check each cell in the line to see if it is occupied, break if any found
			for (int i = 0; i < BOARD_WIDTH; ++i) {
				if (typeAt(i, j).isBlank()) {
					lineIsFull = false;
					break;
				}
			}

			// If line is full, clear it and add to the score
			if (lineIsFull) {
				++numFullLines;
				// Clearing the line means replacing blocks with row above
				for (int x = 0; x < BOARD_WIDTH; ++x) {
					for (int y = j; y >= 0; --y) {
						if(y == 0) {
							board[x][y] = new PieceType();
						} else {
							board[x][y] = typeAt(x, y - 1);
						}
					}
				}
			}
		}

		if (numFullLines > 0) {
			numOfLinesRemoved += numFullLines;
			incrementScore(100 * factorial(numFullLines));
			isFallingFinished = true;
			current.setPieceType(new PieceType());
			
			levelUp();
			
			repaint();
		}
	}
	
	public static int factorial(int i) {
		if(i > 0) return i * factorial(i - 1);
		else return 1;
	}
	
	/**
	 * Increase the speed as the user clears more rows.
	 */
	public void levelUp() {
		int lvl = numOfLinesRemoved / Preferences.LEVEL_ROWS + 1;
		// num=0-4, level=1 - num=5-9, level=2, etc.
		
		if (lvl > level) {
			level = lvl;
			// Subtract 1/3 of the delay period from 
			// the timer to make the blocks fall faster
			int subtr = delay / Preferences.LEVEL_REDUCER;
			delay -= subtr;
			timer.stop();
			timer = new Timer(delay, this);
			timer.start();
		}		
	}
	
	public int getLevel() {
		return level;
	}
	
	public Sounds getSounds() {
		return sounds;
	}
	
	public void paint(Graphics g) {
		super.paint(g);		
		Painter p = new Painter(this);
		if(isStarted) { 
			p.paintGame(g);
		} else {
			p.paintTitle(g);
		}
		parent.getStatusBar().update();
	}

	public Piece getCurrent() { return current; }
	public int getCurrentX() { return currentX; }
	public int getCurrentY() { return currentY; }
	
	public boolean isStarted() {
		return isStarted;
	}
	
	public boolean isPaused() {
		return isPaused;
	}
	
	public boolean isGameOver() {
		return isGameOver;
	}
	
	/**
	 * Prompts to exit, but does nothing if user cancels.
	 */
	public void exitPrompt() {
		if(JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?") == JOptionPane.YES_OPTION) {
			exit();
		}
	}
	
	public void exit() {
		System.exit(0);
	}
}
