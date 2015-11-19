package com.laboon;

import java.util.Random;

public class World
{

	/**
	 * Size of the world
	 */

	int _size;

	/**
	 * The world, a 2D array of Cells which can be living or dead.
	 */

	Cell[][] _world;

	/**
	 * A random number generator.
	 */

	Random _rng;

	/**
	 * Initial world constructor.
	 * 
	 * @param size
	 *            Size of world
	 * @param seed
	 *            Random number generator seed
	 * @param percent
	 *            Initial percentage of cells alive
	 */

	public World(int size, int seed, int percent)
	{
		_size = size;
		_rng = new Random(seed);
		_world = generateBoard(size, percent);
	}

	/**
	 * Iterated (non-initial) world constructor.
	 * 
	 * @param cells
	 *            Cells living in the new world
	 * @param rng
	 *            Random number generator
	 */

	public World(Cell[][] cells, Random rng)
	{
		_size = cells.length;
		_rng = rng;
		_world = cells;
	}

	/**
	 * Generate the initial state of a cell, given the percentage chance that it
	 * is living.
	 * 
	 * @param percent
	 *            percentage chance that it is living
	 * @return state of cell, State.ALIVE or State.DEAD
	 */

	private State generateInitialState(int percent)
	{
		int livingChance = _rng.nextInt(100);
		State toReturn = (livingChance < percent) ? State.ALIVE : State.DEAD;
		return toReturn;
	}

	/**
	 * The number of living neighbors that a cell has.
	 * 
	 * @param world
	 *            the world
	 * @param x
	 *            x location of cell
	 * @param y
	 *            y location of cell
	 * @return
	 */

	private int getNumNeighbors(Cell[][] world, int x, int y)
	{
		int size = world.length;
		int leftX = (x - 1) % size;
		int rightX = (x + 1) % size;
		int upY = (y - 1) % size;
		int downY = (y + 1) % size;

		// wrap around to the other edges of the world if we're touching a wall
		if (leftX == -1)
		{
			leftX = size - 1;
		}
		if (rightX == -1)
		{
			rightX = size - 1;
		}
		if (upY == -1)
		{
			upY = size - 1;
		}
		if (downY == -1)
		{
			downY = size - 1;
		}

		int numNeighbors = 0;

		if (world[leftX][upY].isAlive())
		{
			numNeighbors++;
		}
		if (world[leftX][downY].isAlive())
		{
			numNeighbors++;
		}
		if (world[leftX][y].isAlive())
		{
			numNeighbors++;
		}
		if (world[rightX][upY].isAlive())
		{
			numNeighbors++;
		}
		if (world[rightX][downY].isAlive())
		{
			numNeighbors++;
		}
		if (world[rightX][y].isAlive())
		{
			numNeighbors++;
		}
		if (world[x][upY].isAlive())
		{
			numNeighbors++;
		}
		if (world[x][downY].isAlive())
		{
			numNeighbors++;
		}

		return numNeighbors;
	}

	/**
	 * Go through one iteration of this World and return new World.
	 * 
	 * @return New world
	 */

	public void iterate()
	{

		// precalculate the next state for each cell
		for (int x = 0; x < _size; x++)
		{
			for (int y = 0; y < _size; y++)
			{

				_world[x][y].determineNextState(getNumNeighbors(_world, x, y));
			}
		}
		// update every cell to its next state
		for (int x = 0; x < _size; x++)
		{
			for (int y = 0; y < _size; y++)
			{

				_world[x][y].advanceState();
			}
		}
	}

	/**
	 * Convert this World to a string for display.
	 * 
	 * @return String representation of world
	 */

	public String toString()
	{

		StringBuilder toReturn = new StringBuilder();
		toReturn.append("  ");
		for (int x = 0; x < _size; x++)
		{
			toReturn.append(x % 10);
		}
		toReturn.append("\n");
		for (int x = 0; x < _size; x++)
		{
			toReturn.append((x % 10) + " ");
			for (int y = 0; y < _size; y++)
			{
				toReturn.append(_world[x][y].getStateRep());
			}
			toReturn.append("\n");
		}
		return toReturn.toString();
	}

	/**
	 * Generate initial game board.
	 * 
	 * @param size
	 *            Size of board
	 * @param percent
	 *            Percent alive
	 * @return Initial world
	 */

	private Cell[][] generateBoard(int size, int percent)
	{
		Cell[][] world = new Cell[size][size];
		for (int j = 0; j < size; j++)
		{
			for (int k = 0; k < size; k++)
			{
				world[j][k] = new Cell(generateInitialState(percent));
			}
		}
		return world;
	}

}
