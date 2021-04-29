package CompleteSearch;

import java.util.*;
import java.lang.*;
import java.io.*;


public class Game {
	
	Board sudoku;
	
	public class Cell{
		private int row = 0;
		private int column = 0;
		
		public Cell(int row, int column) {
			this.row = row;
			this.column = column;
		}
		public int getRow() {
			return row;
		}
		public int getColumn() {
			return column;
		}
	}
	
	public class Region{
		private Cell[] matrix;
		private int num_cells;
		// added field
		boolean isFilled;
		public Region(int num_cells) {
			this.matrix = new Cell[num_cells];
			this.num_cells = num_cells;
		}
		public Cell[] getCells() {
			return matrix;
		}
		public void setCell(int pos, Cell element){
			matrix[pos] = element;
		}
		
	}
	
	public class Board{
		private int[][] board_values;
		private Region[] board_regions;
		private int num_rows;
		private int num_columns;
		private int num_regions;
		
		public Board(int num_rows,int num_columns, int num_regions){
			this.board_values = new int[num_rows][num_columns];
			this.board_regions = new Region[num_regions];
			this.num_rows = num_rows;
			this.num_columns = num_columns;
			this.num_regions = num_regions;
		}
		
		public int[][] getValues(){
			return board_values;
		}
		public int getValue(int row, int column) {
			return board_values[row][column];
		}
		public Region getRegion(int index) {
			return board_regions[index];
		}
		public Region[] getRegions(){
			return board_regions;
		}
		public void setValue(int row, int column, int value){
			board_values[row][column] = value;
		}
		public void setRegion(int index, Region initial_region) {
			board_regions[index] = initial_region;
		}	
		public void setValues(int[][] values) {
			board_values = values;
		}

	}
	
	public int[][] solver() {
		//To Do => Please start coding your solution here
		// if at a solution, report success (BC)
		recursiveSolver(sudoku.board_values);
		// region by region approach
		
		return sudoku.board_values;
	}


	private boolean recursiveSolver(int[][] board_values) {	// added with help from internet. code not cut and paste!

		for(Region r : sudoku.board_regions) {
			if(r.isFilled == true) continue;		// if filled (added field) then continue
			else {
				for(Cell c : r.matrix) {	// for each cell in that region (as opposed to columns)
					int row = c.row;
					int col = c.column;
					if(sudoku.board_values[row][col] == -1) {	// if it is empty
						for(int put = 1; put <= r.num_cells; put++) {	// increment put each time
							sudoku.board_values[row][col]=put;
							if(checksOut(row, col, put, r) && recursiveSolver(sudoku.board_values))
								return true;	// boolean is determinant
							sudoku.board_values[row][col]=-1;
							r.isFilled = false;	// unfill if recursive backtracking leads to a false value
						}
					return false;	// if broken out of the loop, return false. this means no solution
					}
				}
			}
		}
		return true;	// once done, return true if solved
		
	}


	private boolean checksOut(int row, int col, int put, Region r) {
		// TODO Auto-generated method stub
		return diagonals(row, col, put) && region(row,col,put,r);
	}


	private boolean region(int row, int col, int put, Region r) {
		// TODO Auto-generated method stub
		boolean setFilled = true;
		for(int i = 0; i < r.num_cells; i++) {
			int thisRow = r.matrix[i].row;
			int thisColumn = r.matrix[i].column;
			if(sudoku.board_values[thisRow][thisColumn]==-1) setFilled = false;
			if(thisRow == row && thisColumn == col) continue;
			if(sudoku.board_values[thisRow][thisColumn]==put) return false;
		}
		if(setFilled) r.isFilled = true;
		return true;
	}


	private boolean diagonals(int row, int col, int put) {
		
		// checks diagonals, top and bottom (just a bunch of try-catch blocks)
		try {
			if(sudoku.board_values[row-1][col-1] == put) return false;
		} catch(Exception e) {}
		try {
			if(sudoku.board_values[row-1][col+1] == put) return false;
		} catch(Exception e) {}
		try {
			if(sudoku.board_values[row+1][col-1] == put) return false;
		} catch(Exception e) {}
		try {
			if(sudoku.board_values[row+1][col+1] == put) return false;
		} catch(Exception e) {}
		
		// checks adjacencies - starting with the top and bottom, then left to right
		try {
			if(sudoku.board_values[row-1][col] == put) return false;
		} catch(Exception e) {}
		try {
			if(sudoku.board_values[row+1][col] == put) return false;
		} catch(Exception e) {}try {
			if(sudoku.board_values[row][col-1] == put) return false;
		} catch(Exception e) {}try {
			if(sudoku.board_values[row][col+1] == put) return false;
		} catch(Exception e) {}
		
		return true;
	}


	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int rows = sc.nextInt();
		int columns = sc.nextInt();
		int[][] board = new int[rows][columns];
		//Reading the board
		for (int i=0; i<rows; i++){
			for (int j=0; j<columns; j++){
				String value = sc.next();
				if (value.equals("-")) {
					board[i][j] = -1;
				}else {
					try {
						board[i][j] = Integer.valueOf(value);
					}catch(Exception e) {
						System.out.println("Ups, something went wrong");
					}
				}	
			}
		}
		int regions = sc.nextInt();
		Game game = new Game();
	    game.sudoku = game.new Board(rows, columns, regions);
		game.sudoku.setValues(board);
		for (int i=0; i< regions;i++) {
			int num_cells = sc.nextInt();
			Game.Region new_region = game.new Region(num_cells);
			for (int j=0; j< num_cells; j++) {
				String cell = sc.next();
				String value1 = cell.substring(cell.indexOf("(") + 1, cell.indexOf(","));
				String value2 = cell.substring(cell.indexOf(",") + 1, cell.indexOf(")"));
				Game.Cell new_cell = game.new Cell(Integer.valueOf(value1)-1,Integer.valueOf(value2)-1);
				new_region.setCell(j, new_cell);
			}
			game.sudoku.setRegion(i, new_region);
		}
		int[][] answer = game.solver();
		for (int i=0; i<answer.length;i++) {
			for (int j=0; j<answer[0].length; j++) {
				System.out.print(answer[i][j]);
				if (j<answer[0].length -1) {
					System.out.print(" ");
				}
			}
			System.out.println();
		}
	}
	


}


