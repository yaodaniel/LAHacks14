/**
 * 
 */
package com.me.hackathonGame;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * @author Phillip
 *
 */
public abstract class Grid {
	
	protected int len;
	protected Square[][] squares;
	protected Cube cube;
	protected int cx;
	protected int cy;
	protected int sx;
	protected int sy;
	protected LinkedList<Direction> dirSteps;
	
	/**
	 * Default Grid is len of 3
	 */
	public Grid() {
		this.len = 3;
		this.dirSteps = new LinkedList<Direction>();
		this.squares = new Square[len][len];
		initSquares();
	}
	
	/**
	 * @param len, length of the Grid.
	 */
	public Grid(int len) {
		if (len < 0) {
			throw new RuntimeException("Invalid len: " + len);
		}
		this.len = len;
		this.dirSteps = new LinkedList<Direction>();
		this.squares = new Square[len][len];
		initSquares();
	}
	
	/**
	 * Initiate White Squares 
	 */
	public void initSquares() {
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len; j++) {
				this.squares[i][j] = new Square();
			}
		}
	}
	
	/**
	 * @return len, the length of the Grid
	 */
	public int getLen() {
		return len;
	}

	/**
	 * @return all Squares in a 2d array
	 */
	public Square[][] getAllSquares() {
		return squares;
	}
	
	/**
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @return Square at (x, y)
	 */
	public Square getSquare(int x, int y) {
		if (x < 0 || y < 0 || x >= len || y >= len) {
			return null;
		}
		return squares[x][y];
	}
	
	/**
	 * @return the cube
	 */
	public Cube getCube() {
		return cube;
	}

	/**
	 * @param dir, the Direction Cube wants to move
	 * @return true if valid move
	 */
	public boolean validCubeMove(Direction dir) {
		switch (dir) {
			case SOUTH:
				if (cy <= 0) { return false; }
				break;
			case WEST:
				if (cx <= 0) { return false; }
				break;
			case EAST:
				if (cx >= len-1) { return false; }
				break;
			case NORTH:
				if (cy >= len-1) { return false; }
				break;
			default:
				return true;
		}
		return true;
	}
	
	/**
	 * @return cube x coordinate
	 */
	public int getCubeX() {
		return cx;
	}
	
	/**
	 * @return cube y coordinate
	 */
	public int getCubeY() {
		return cy;
	}
	
	/**
	 * @return start cube x coordinate
	 */
	public int getStartCubeX() {
		return sx;
	}
	
	/**
	 * @return start cube y coordinate
	 */
	public int getStartCubeY() {
		return sy;
	}
	
	/**
	 * Place Cube at (x, y) and color square
	 * @param x cube x coordinate
	 * @param y cube y coordinate
	 */
	public void placeCube(int x, int y) {
		if (x < 0 || y < 0 || x > len-1 || y > len-1) {
			throw new RuntimeException("Tried to place cube outside bounds.");
		}
		cx = x;
		cy = y;
		GameColor color = cube.bot();
		squares[cx][cy].setColor(color);
	}
	
	/** Move Cube towards Direction and color Square
	 * @param dir, the Direction
	 * @return true if successful
	 */
	public boolean moveCube(Direction dir) {
		if (!validCubeMove(dir)) {
			return false;
		}
		GameColor color;
		switch (dir) {
			case NORTH:
				cy++;
				cube.north();
				color = cube.bot();
				squares[cx][cy].setColor(color);
				break;
			case WEST:
				cx--;
				cube.west();
				color = cube.bot();
				squares[cx][cy].setColor(color);
				break;
			case EAST:
				cx++;
				cube.east();
				color = cube.bot();
				squares[cx][cy].setColor(color);
				break;
			case SOUTH:
				cy--;
				cube.south();
				color = cube.bot();
				squares[cx][cy].setColor(color);
				break;
			default:
		}
		dirSteps.add(dir);
		return true;
	}
	
	
	/**
	 * For SolutionGrid, the Directions to the Solution
	 * For PlayGrid, the Directions player took
	 * @return list of Directions
	 */
	public LinkedList<Direction> getSteps() {
		return dirSteps;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + len;
		result = prime * result + Arrays.hashCode(squares);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Grid))
			return false;
		Grid other = (Grid) obj;
		if (len != other.len)
			return false;
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len; j++) {
				if (!squares[i][j].equals(other.squares[i][j])) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public String toString() {
		String str = "";
		for (int i = len-1; i >= 0; i--) {
			for (int j = 0; j < len; j++) {
				str += "(" + j + ", " + i + ")" + squares[i][j].toString() + " ";
			}
			str += "\n";
		}
		str += cube.toString();
		return str;
	}

	/**
	 * @param dirs the Directions to follow
	 * @return ReplayGridIterator that returns each Grid per step
	 */
	public ReplayGridIterator gridIterator(LinkedList<Direction> dirs) {
		GameColor[] colors = getCube().getStartColors();
		Cube copyCube = new Cube(colors);
		ReplayGridIterator playGridIter = new ReplayGridIterator(len, copyCube, sx, sy, dirs);
		return playGridIter;
	}
}
