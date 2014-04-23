package com.me.hackathonGame;

public class Cube {
	
	GameColor[] cubeColors;
	GameColor[] startColors;
	
	public Cube() {
		cubeColors = new GameColor[6];
		startColors = new GameColor[6];
		newCubeColors();
	}
	
	public Cube(GameColor[] colors) {
		cubeColors = new GameColor[6];
		startColors = new GameColor[6];
		loadCubeColors(colors);
	}
	
	/**
	 * Generate new cube cubeColors
	 */
	public void newCubeColors() {
		GameColor[] colors = GameColor.values();
		int numColors = colors.length-1;
		for (int i = 0; i < 6; i++) {
			int randIdx = (int) (Math.random()*numColors+1);
			cubeColors[i] = colors[randIdx];
			startColors[i] = colors[randIdx];
		}
	}
	
	/**
	 * @param colors, the Colors to load to Cube
	 */
	public void loadCubeColors(GameColor[] colors) {
		for (int i = 0; i < 6; i++) {
			cubeColors[i] = colors[i];
			startColors[i] = colors[i];
		}
	}
	
	/**
	 * @return the current Cube Colors
	 */
	public GameColor[] getCubeColors() {
		return cubeColors;
	}
	
	/**
	 * @return the starting Cube Colors
	 */
	public GameColor[] getStartColors() {
		return startColors;
	}
	
	/**
	 * Resets to starting Cube Colors
	 */
	public void resetColors() {
		for (int i = 0; i < 6; i++) {
			cubeColors[i] = startColors[i];
		}
	}
	
	/**
	 * @return top color
	 */
	public GameColor top() {
		return cubeColors[0];
	}
	
	/**
	 * @return front color
	 */
	public GameColor front() {
		return cubeColors[1];
	}

	/**
	 * @return bot color
	 */
	public GameColor bot() {
		return cubeColors[3];
	}
	
	/**
	 * @return back color
	 */
	public GameColor back() {
		return cubeColors[5];
	}
	
	/**
	 * @return left color
	 */
	public GameColor left() {
		return cubeColors[2];
	}
	
	/**
	 * @return right color
	 */
	public GameColor right() {
		return cubeColors[4];
	}
	
	/**
	 * Move north
	 */
	public void north() {
		GameColor[] next = new GameColor[6];
		next[0] = cubeColors[5];
		next[1] = cubeColors[0];
		next[2] = cubeColors[2];
		next[3] = cubeColors[1];
		next[4] = cubeColors[4];
		next[5] = cubeColors[3];
		cubeColors = next;
	}
	
	/**
	 * Move west
	 */
	public void west() {
		GameColor[] next = new GameColor[6];
		next[0] = cubeColors[4];
		next[1] = cubeColors[1];
		next[2] = cubeColors[0];
		next[3] = cubeColors[2];
		next[4] = cubeColors[3];
		next[5] = cubeColors[5];
		cubeColors = next;
	}
	
	/**
	 * Move east
	 */
	public void east() {
		GameColor[] next = new GameColor[6];
		next[0] = cubeColors[2];
		next[1] = cubeColors[1];
		next[2] = cubeColors[3];
		next[3] = cubeColors[4];
		next[4] = cubeColors[0];
		next[5] = cubeColors[5];
		cubeColors = next;
	}
	
	/**
	 * Move south
	 */
	public void south() {
		GameColor[] next = new GameColor[6];
		next[0] = cubeColors[1];
		next[1] = cubeColors[3];
		next[2] = cubeColors[2];
		next[3] = cubeColors[5];
		next[4] = cubeColors[4];
		next[5] = cubeColors[0];
		cubeColors = next;
	}

	@Override
	public String toString() {
		String str = "Cube" + "\n";
		str += "\t\t[Top:\t" + top() + "]\n";
		str += "\t\t[Front:\t" + front() + "]\n";
		str += "[Left:\t" + left() + "]";
		str += "\t[Bot:\t" + bot() + "]";
		str += "\t[Right:\t" + right() + "]\n";
		str += "\t\t[Back:\t" + back() + "]\n";
		
		return str;
	}
}
