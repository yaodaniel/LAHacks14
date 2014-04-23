/**
 * 
 */
package com.me.hackathonGame;

/**
 * @author Phillip
 *
 */
public class PlayGrid extends Grid {

	public PlayGrid(int len, Cube cube, int cx, int cy) {
		super(len);
		this.cube = cube;
		placeCube(cx, cy);
		sx = cx;
		sy = cy;
	}
	
	/**
	 * Reset Grid
	 */
	public void reset() {
		cx = sx;
		cy = sy;
		initSquares();
		cube.resetColors();
		placeCube(cx, cy);
	}

	/**
	 * @param dir, the Direction to test
	 * @return the Color that the next Square would be, null if invalid move
	 */
	public GameColor getTestMoveColor(Direction dir) {
		if (!validCubeMove(dir)) {
			return null;
		}
		switch (dir) {
			case NORTH:
				return cube.front();
			case WEST:
				return cube.left();
			case EAST:
				return cube.right();
			case SOUTH:
				return cube.back();
			default:
		}
		return null;
	}
}
