package com.laboon;

public class Cell
{

	private State _state = State.DEAD;
	private State _nextState = null;

	/**
	 * Public getter for the cell
	 * 
	 * @return Current state of the cell
	 */

	public State getState()
	{
		return _state;
	}

	/**
	 * Returns whether or not the cell is alive.
	 * 
	 * @return True if cell alive, false if dead
	 */

	public boolean isAlive()
	{
		if (_state == State.ALIVE)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Any live cell with fewer than two live neighbors dies, as if caused by
	 * under-population. Any live cell with two or three live neighbors lives on
	 * to the next generation. Any live cell with more than three live neighbors
	 * dies, as if by overcrowding. Any dead cell with exactly three live
	 * neighbors becomes a live cell, as if by reproduction.
	 * 
	 * @param int
	 *            numNeighbors - the current number of neighbors
	 * @return State - the new state of the cell (alive or dead)
	 */

	public void determineNextState(int numNeighbors)
	{
		State newState = null;
		if (_state == State.ALIVE)
		{
			if (numNeighbors < 2 || numNeighbors > 3)
			{
				newState = State.DEAD;
			}
			else
			{
				newState = State.ALIVE;
			}
		}
		else if (_state == State.DEAD)
		{
			if (numNeighbors == 3)
			{
				newState = State.ALIVE;
			}
			else
			{
				newState = State.DEAD;
			}
		}
		_nextState = newState;
	}

	public void advanceState()
	{
		_state = _nextState;
		_nextState = null;
	}

	/**
	 * Get the character to display for the current state. Modifying this code
	 * will allow you to change the terminal display of when a cell is alive or
	 * dead.
	 * 
	 * @return Character representation of the cell's state
	 */

	public char getStateRep()
	{
		char toReturn = ' ';
		if (_state == State.DEAD)
		{
			toReturn = '.';
		}
		else if (_state == State.ALIVE)
		{
			toReturn = 'X';
		}
		else
		{
			toReturn = '?';
		}
		return toReturn;
	}

	/**
	 * Constructor for a new cell.
	 * 
	 * @param initialState
	 *            - Initial state of a cell, dead or alive
	 * @param xLoc
	 *            - x location
	 * @param yLoc
	 *            - y location
	 */

	public Cell(State initialState)
	{
		_state = initialState;
	}

}
