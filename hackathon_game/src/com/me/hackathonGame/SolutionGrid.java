package com.me.hackathonGame;

import java.util.LinkedList;

public class SolutionGrid extends Grid {
	
	private static final int MULT = 5;
	private int numSteps;

	public SolutionGrid() {
		super();
		this.cube = new Cube();
		// Randomly place the cube
		int cx = (int) Math.random()*len;
		int cy = (int) Math.random()*len;
		placeCube(cx, cy);
		generateSolution();
		sx = cx;
		sy = cy;
	}
	
	public SolutionGrid(int len) {
		super(len);
		this.cube = new Cube();
		this.numSteps = len*MULT;
		// Randomly place the cube
		int cx = (int) (Math.random()*len);
		int cy = (int) (Math.random()*len);
		placeCube(cx, cy);
		dirSteps = new LinkedList<Direction>();
		generateSolution();
		sx = cx;
		sy = cy;
	}
	
	/**
	 * Generate a solution path
	 */
	public void generateSolution() {
		Direction[] dirs = Direction.values();
		int dirsNum = dirs.length;
		while (dirSteps.size() < numSteps) {
			// TODO: FANCY PATHING ALGORITHM YOOOO
			int randIdx = (int) (Math.random()*dirsNum);
			Direction next = dirs[randIdx];
			moveCube(next);
		}
	}

}
