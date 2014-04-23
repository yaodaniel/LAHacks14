package com.me.hackathonGame;

import java.util.Iterator;
import java.util.LinkedList;

public class ReplayGridIterator implements Iterator<PlayGrid> {

	PlayGrid playGrid;
	LinkedList<Direction> dirs;
	Iterator<Direction> dirsIter;
	
	/**
	 * @param len number of Squares along length (len*len total Squares) 
	 * @param cube the Cube
	 * @param cx Cube's starting x coordinate 
	 * @param cy Cube's starting y coordinate
	 * @param dirs list of Directions 
	 */
	public ReplayGridIterator(int len, Cube cube, int cx, int cy, LinkedList<Direction> dirs) {
		this.playGrid = new PlayGrid(len, cube, cx, cy);
		this.dirs = dirs;
		if (dirs == null) {
			System.out.println("Can't do that");
			throw new NullPointerException("null list of directions.");
		}
		this.dirsIter = dirs.iterator();
	}
	
	@Override
	public boolean hasNext() {
		return dirsIter.hasNext();
	}

	@Override
	public PlayGrid next() {
		Direction nextDir = dirsIter.next();
		playGrid.moveCube(nextDir);
		return playGrid;
	}

	@Override
	public void remove() {
		return;
	}
}
