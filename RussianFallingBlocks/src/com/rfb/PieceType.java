package com.rfb;

import java.awt.Color;

public class PieceType {
	
	Shape shape;
	
	public PieceType() {
		shape = Shape.Blank;
	}
	
	public PieceType(PieceType otherPiece) {
		shape = otherPiece.shape;
	}
	
	public PieceType(Shape shape) {
		this.shape = shape;
	}
	
	public boolean isSquare() {
		return shape == Shape.SquareBlock;
	}
	
	public boolean isBlank() {
		return shape == Shape.Blank;
	}
			
	enum Shape {
		/**
		 * 
		 */
		Blank,
		
		/**  [][]
		 *     [][]
		 */
		ZBlock,
		
		/**   [][]
		 *  [][]
		 */
		SBlock,
		
		/**
		 *  [][][][]
		 */
		BarBlock,
		
		/**  [][]
		 *   [][]
		 */
		SquareBlock, 
		
		/**      []
		 *   [][][]
		 */
		LBlock,
		
		/** []
		 *  [][][]
		 */
		RBlock,
		
		/**    []
		 *   [][][]
		 */
		TBlock,
	}
	
	public Color getColor() {
		return getColor(shape);
	}
	
	public static Color getColor(Shape p) {
		Color c = null;
		switch(p){
		case Blank:
			c = new Color(0,0,0); // black
			break;
		case ZBlock:
			c = new Color(100,255,100); // Green
			break;
		case SBlock:
			c = new Color(100,100,255); // Blue
			break;
		case BarBlock:
			c = new Color(255,100,100); // Red
			break;
		case SquareBlock:
			c = new Color(100,255,255); // GB= Cyan
			break;
		case LBlock:
			c = new Color(255,255,100); // RG=Yellow
			break;
		case RBlock:
			c = new Color(255,100,255); // R-B=Pink
			break;
		case TBlock:
			c = new Color(100,100,100); // Gray
			break;
		}
		return c;	
	}
	
	public int[][] getCoords() {
		return getCoords(shape);
	}
		
	public static int[][] getCoords(Shape p) {
		
		switch(p){
		case Blank:
			return new int[][]{ { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } };
		case ZBlock:
			return new int[][]{ { -1, -1 }, { -1, 0 }, { 0, 0 }, { 0, 1 } };
		case SBlock:
			return new int[][]{ { 0, -1 }, { 0, 0 }, { -1, 0 }, { -1, 1 } };
		case BarBlock:
			return new int[][]{ { 0, -1 }, { 0, 0 }, { 0, 1 }, { 0, 2 } };
		case SquareBlock:
			return new int[][]{ { 0, 1 }, { 0, 0 }, { 1, 0 }, { 1, 1 }  };
		case LBlock:
			return new int[][]{ { 1, 0 }, { 0, 0 }, { 0, 1 }, { 0, 2 } };
		case RBlock:
			return new int[][]{ { -1, 0 }, { 0, 0 }, { 0, 1 }, { 0, 2 } };
		case TBlock:
			return new int[][]{ { -1, 0 }, { 0, 0 }, { 0, 1 }, { 1, 0 } };
		default:
			return null;
		}	
	}
}