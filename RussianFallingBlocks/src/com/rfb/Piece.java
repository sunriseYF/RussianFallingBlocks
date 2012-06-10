package com.rfb;

import java.util.Random;

import com.rfb.PieceType.Shape;

public class Piece {
	
	private PieceType pieceType;
	private int shapeMap[][];
	
	public Piece() {
		this.shapeMap = new int[4][2];
		setPieceType(new PieceType());
	}
	
	/*
	 * Getters and setters for the private shape variables  
	 */
	private void setX(int i, int x) { shapeMap[i][0] = x; }
	private void setY(int i, int y) { shapeMap[i][1] = y; }
	public int x(int i) { return shapeMap[i][0]; }
	public int y(int i) { return shapeMap[i][1]; }
    public PieceType getPieceType()  { return pieceType; }
    public void setPieceType(PieceType pieceType) {
		int[][] pieceCoords = pieceType.getCoords();
		for(int i=0; i < 4; i++) {
			for(int j=0; j< 2; j++) {
				shapeMap[i][j] = pieceCoords[i][j];
			}
		}
		this.pieceType = pieceType;
	}

    /**
     * @return the leftmost horizontal position of the block 
     */
    public int minX() {
    	int m = shapeMap[0][0];
    	for (int i=0; i < 4; i++) {
    		m = Math.min(m, shapeMap[i][0]);
    	}
    	return m;
    }
    
    public int maxX() {
    	int m = shapeMap[0][0];
    	for(int i=0; i<4; i++) {
    		m = Math.max(m, shapeMap[i][0]);
    	}
    	return m;
    }

    /**
     * @return the lowest vertical position of the block
     */
    public int minY() {
      int m = shapeMap[0][1];
      for (int i=0; i < 4; i++) {
          m = Math.max(m, shapeMap[i][1]);
      }
      return m;
    }
    
    /**
     * Initializes this object's shape to a random type
     */
    public void setRandomShape() {
        Random r = new Random();
        int shapeNum = Math.abs(r.nextInt()) % 7 + 1;
        Shape[] values = Shape.values(); 
        setPieceType(new PieceType(values[shapeNum]));
    }
    
    /**
     * Rotates the blocks in this piece by setting x = y and y = -x: 90 degrees left
     * @return the resulting shape and orientation
     */
    public Piece rotateLeft() {
    	if(pieceType.isSquare()) return this; 	
    	Piece result = new Piece();
    	result.setPieceType(pieceType);
    	for(int i=0; i<4; ++i) {
    		result.setX(i, y(i));
    		result.setY(i, -x(i));
    	}
    	return result;
    }
    
    /**
     * Rotates the blocks in this piece by setting x = y and y = -x: 90 degrees left
     * @return the resulting shape and orientation
     */
    public Piece rotateRight() {
    	if(pieceType.isSquare()) return this;
    	Piece result = new Piece();
    	result.setPieceType(pieceType);
    	for(int i=0; i<4; ++i) { // for each coordinate, rotate 90 degrees by setting x=-y and y=x
    		result.setX(i, -y(i));
    		result.setY(i, x(i));
    	}
    	return result;
    }
}