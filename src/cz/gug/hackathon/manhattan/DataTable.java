package cz.gug.hackathon.manhattan;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import cz.gug.hackaton.manhattan.actors.GameLevel;

import android.graphics.Point;
import android.util.Log;

public class DataTable {

	private static final String TAG = DataTable.class.getSimpleName();
	protected int[][] dataTable;
	int rows, columns;
	private List<Point> solutionTapSequence;

	public DataTable(int columns, int rows) {
		this.rows = rows;
		this.columns = columns;
		dataTable = new int[rows][columns];
		initializeTable();
		solutionTapSequence = new ArrayList<Point>();
	}

	// ///// Protected methods

	/**
	 * Sets all table fields to 0.
	 */
	protected void initializeTable() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				dataTable[i][j] = 0;
			}
		}
	}

	/**
	 * Resets all table fields to 0.
	 */
	protected void resetTable() {
		initializeTable();
		solutionTapSequence.clear();
	}

	/**
	 * Prints all table fields in LogCat.
	 */
	protected void printLog() {
		String debug = new String();
		for (int i = 0; i < rows; i++) {
			debug = "";
			for (int j = 0; j < columns; j++) {
				debug = debug.concat(dataTable[i][j] + " ");
			}
			Log.d(TAG, debug);
		}
	}

	// ///// Public interface

	/**
	 * Is the table back in all-zeros state?
	 * 
	 * @return Is the table back in all-zeros state?
	 */
	public boolean solved() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (dataTable[i][j] != 0) {
					return false;
				}
			}
		}
		return true;
	}
	
	
	/**
	 * getValues returns cell value for given x and y coord
	 * 
	 * @return returns table value or -1 if out of range
	 */
	public int getValues(int row,int col) {
		
		//if (col >= 0 && col < columns &&
		//	row >= 0 && row < rows) {
			
			return dataTable[row][col];
			
		//} 
		
		//return -1;
	}

	/**
	 * Executes the screen tap logic on the table.
	 * 
	 * @param x
	 *            The x coordinate of the tap
	 * @param y
	 *            The y coordinate of the tap
	 */
	public void tap(int x, int y) {
		// switch the y column
		
		if (dataTable[x][y] != -1) {
		
			for (int i = 0; i < rows; i++) {
				if (dataTable[i][y] != -1) {
					dataTable[i][y] = 1 - dataTable[i][y];
				}
			}

			// switch the x row
			for (int j = 0; j < columns; j++) {
				if (j != y) { // field (x,y) was already switched in the previous
					// cycle
					if (dataTable[x][j] != -1) {
						dataTable[x][j] = 1 - dataTable[x][j];
					}
				}
			}
		
	
			for (int i = 0; i < rows; i++) {
				System.out.println();
				for (int j = 0; j < columns; j++) {
					System.out.print(dataTable[i][j] + " ");
				}
			}
			
		}
	}

	/**
	 * Randomly shuffles the table (by simulating the given number of taps)
	 * before the new game is started.
	 * 
	 * @param numSteps
	 *            The number of simulated screen taps
	 */
	public void shuffle(int numSteps) {
		Random coord = new Random();
		Set<Point> tapPointsSet = new HashSet<Point>();
		Iterator<Point> it;
		
		resetTable();
		
		if ((numSteps >= (rows * columns)) || (numSteps <= 0)) {
			numSteps = (int) (rows * columns) / 2;
		}
		
		while (tapPointsSet.size() < numSteps) {
			int x = coord.nextInt(rows);
			int y = coord.nextInt(columns);
			Point tapPoint = new Point(x, y);
			
			if (tapPointsSet.add(tapPoint)) {
				solutionTapSequence.add(tapPoint);
			}
		}
		
		it = tapPointsSet.iterator();

		while (it.hasNext()) {
			Point tapPoint = it.next();
			Log.d(TAG, "Shuffle() - Tapping on coords: (" + tapPoint.x + "," + tapPoint.y + ")");
			Log.d(TAG, "-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|-|");
			tap(tapPoint.x, tapPoint.y);
			printLog();
			Log.d(TAG, "----------------------------------------------------");
		}

	}

	public void initLevel(GameLevel level) {
	
		this.dataTable = level.getTable();
		
		Point[] spots = level.getSpots();
	
		for (int i = 0;  i < 10; i++) {
			if (spots[i] != null) {
				tap(spots[i].x, spots[i].y);
			}
		}
		
	}

}
