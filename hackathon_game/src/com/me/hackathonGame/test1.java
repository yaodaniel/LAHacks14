package com.me.hackathonGame;

import java.util.LinkedList;

public class test1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Grid of this many squares
		int len = 4;
		
		// Create SolutionGrid of that length
		// All Squares are initially WHITE, then draws a path
		SolutionGrid solnGrid = new SolutionGrid(len);
		
		// Solution Grid
		System.out.println("Solution Grid: ");
		System.out.println(solnGrid);
		
		//Get all the Squares of Solution Grid
		Square[][] solnSquares = solnGrid.getAllSquares();
		
		System.out.println("Solution Square Color at (0, 0): " + solnSquares[0][0].getColor() + "\n");
		
		// Copy the Starting Cube that the solution used (same colors)
		GameColor[] colors = solnGrid.getCube().getStartColors();
		Cube cube = new Cube(colors);
		int cx = solnGrid.getStartCubeX();
		int cy = solnGrid.getStartCubeY();
		
		// Create PlayGrid of same length and put Cube where SolutionGrid started
		PlayGrid playGrid = new PlayGrid(len, cube, cx, cy);

		System.out.println("Start: (" + cx + ", " + cy + ")");
		
		// Move the Cube on PlayGrid NORTH, changing Grid Colors and Cube orientation
		// Returns false and do nothing if invalid move
		// Use this to move the Cube on playGrid
		playGrid.moveCube(Direction.NORTH);
		// Check if player found the solution: check if all Squares are same color on both Grids
		// Most likely not equal
		System.out.println("Found Solution? " + solnGrid.equals(playGrid));
		cx = playGrid.getCubeX();
		cy = playGrid.getCubeY();
		System.out.println("NORTH: (" + cx + ", " + cy + ")");
		
		// ""
		playGrid.moveCube(Direction.WEST);
		cx = playGrid.getCubeX();
		cy = playGrid.getCubeY();
		System.out.println("WEST: (" + cx + ", " + cy + ")");
		System.out.println("Found Solution? " + solnGrid.equals(playGrid));
		
		// ""
		playGrid.moveCube(Direction.EAST);
		cx = playGrid.getCubeX();
		cy = playGrid.getCubeY();
		System.out.println("EAST: (" + cx + ", " + cy + ")");
		System.out.println("Found Solution? " + solnGrid.equals(playGrid));
		
		// ""
		playGrid.moveCube(Direction.SOUTH);
		cx = playGrid.getCubeX();
		cy = playGrid.getCubeY();
		System.out.println("SOUTH: (" + cx + ", " + cy + ")");
		System.out.println("Found Solution? " + solnGrid.equals(playGrid));
		System.out.println();
		
		// Get current Cube's coordinates
		cx = playGrid.getCubeX();
		cy = playGrid.getCubeY();
		
		//Get all the Squares of Play Grid
		Square[][] playSquares = solnGrid.getAllSquares();
		
		System.out.println("Play Square Color at (0, 0): " + playSquares[0][0].getColor() + "\n");
		
		// Test a move and get the Color, null if invalid move
		System.out.println("Color if moved NORTH: " + playGrid.getTestMoveColor(Direction.NORTH));
		System.out.println("Color if moved WEST: " + playGrid.getTestMoveColor(Direction.WEST));
		System.out.println("Color if moved EAST: " + playGrid.getTestMoveColor(Direction.EAST));
		System.out.println("Color if moved SOUTH: " + playGrid.getTestMoveColor(Direction.SOUTH));
		System.out.println();
		
		// Reset the PlayGrid and Cube back to starting stuff
		playGrid.reset();
		
		// Get the list of Directions that Solution Grid used
		LinkedList<Direction> solnDirs = solnGrid.getSteps();
		System.out.println("Solution Directions: " + solnDirs + "\n");
		
		System.out.println();
		System.out.println("***********************************");
		System.out.println("Reveal Solution");
		System.out.println("***********************************\n");
		
		// Play through the solution step by step
		ReplayGridIterator solnGridIter = playGrid.gridIterator(solnDirs);
		PlayGrid next = null;
		int step = 1;
		while(solnGridIter.hasNext()) {
			next = solnGridIter.next();
			System.out.println("*** STEP: " + step + " ***");
			System.out.println(next);
			step++;
			//Should be equal eventually
			System.out.println("Found Solution? " + solnGrid.equals(next) + "\n");
		}
		
		System.out.println();
		System.out.println("***********************************");
		System.out.println("Simulate GamePlay");
		System.out.println("***********************************\n");
		
		// Do random stuff (8 moves)
		playGrid.reset();
		Direction[] dirs = Direction.values();
		int numDirs = dirs.length;
		for (int i = 0; i < 8; i++) {
			int randIdx = (int) (Math.random() * numDirs);
			playGrid.moveCube(dirs[randIdx]);
			if (solnGrid.equals(playGrid)) {
				System.out.println("Found solution!");
				break;
			}
		}
		// Replay moves
		ReplayGridIterator playGridIter = playGrid.gridIterator(solnDirs);
		next = null;
		step = 1;
		while(playGridIter.hasNext()) {
			next = playGridIter.next();
			System.out.println("*** STEP: " + step + " ***");
			System.out.println(next);
			step++;
		}
	}
}