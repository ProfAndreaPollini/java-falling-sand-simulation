package org.fallingsand;

import lombok.Data;
import org.fallingsand.materials.Material;
import org.fallingsand.materials.Materials;

import java.util.ArrayList;
import java.util.List;

@Data
public class GridDomain {
	private final int rows;
	private final int cols ;
	private Cell[] cells;
	
	public GridDomain(int rows, int cols){
		this.rows = rows;
		this.cols = cols;
		cells = new Cell[rows*cols];
		for (int i = 0; i < cells.length; i++) {
			cells[i] = new Cell(this, i/cols, i %cols);
		}
	}
	
	public int getIdxFromCoords(int row, int col) {
		return row*cols + col;
	}
	
	public void insertAt(int row, int col, Material material) {
//		if (cells[getIdxFromCoords(row,col)] == null) {
//			cells[getIdxFromCoords(row,col)] = new Cell(this,row,col);
//		}
		var idx = getIdxFromCoords(row,col);
		if (!isIndexValid(idx)) return;
		cells[idx].setMaterial(material);
		cells[idx].setDirty(true);
	}
	
	public Cell getCellAt(int row, int col) {
		if (isPosInvalid(row, col)) return null;
		var idx = getIdxFromCoords(row,col);
		if (!isIndexValid(idx)) return null;
		return cells[idx];
	}
	
	public Cell getCellTarget(Cell cell) {
		var dir = cell.getDirection();
		var dx = 0;
		var dy=0;
		switch(dir) {
			case NONE -> {
			}
			case LEFT -> {
				dx = -1;
			}
			case RIGHT -> {
				dx = 1;
			}
			case UP -> {
				dy=-1;
			}
			case DOWN -> {
				dy = 1;
			}
			
		}
		
		return getCellAt(cell.row+dy,cell.col+dx);
	}
	
	public Cell getCellAtDirection(Cell cell,Direction dir) {
		var dx = 0;
		var dy=0;
		switch(dir) {
			case NONE -> {
			}
			case LEFT -> {
				dx = -1;
			}
			case RIGHT -> {
				dx = 1;
			}
			case UP -> {
				dy=-1;
			}
			case DOWN -> {
				dy = 1;
			}
			
		}
		
		return getCellAt(cell.row+dy,cell.col+dx);
	}
	
	public Cell getCellAt(Cell cell, int dx, int dy) {
		return getCellAt(cell.row+dy,cell.col+dx);
	}
	
	public boolean isPosInvalid(int row, int col) {
		return row < 0 || col < 0 || row >= rows || col >= cols;
	}
	
	private boolean isIndexValid(int idx) {
		return idx >= 0 || idx < rows * cols;
	}
	
	public List<Cell> getDirtyCells() {
		List<Cell> ret= new ArrayList<>(); // optimize
		//Logger.info("cells to render {}",cells.length);
		for (int i = cells.length-1; i >=0; i--) {
			if (!cells[i].isEmpty())
				ret.add(cells[i]);
		}
		
		return ret;
	}
	
	public List<Cell> getUpdatedCells() {
		List<Cell> ret= new ArrayList<>(); // optimize
		//Logger.info("cells to render {}",cells.length);
		for (int i = cells.length-1; i >=0; i--) {
			if (!cells[i].isEmpty() ) ret.add(cells[i]);
		}
		
		return ret;
	}
	
	public void tick() {
//		if (Math.random() > 0.5)
//		cells[50].setMaterial(Materials.WATER);
		//cells[70].setMaterial(Materials.SAND);
		
		for (int i = cells.length-1; i >=0 ; i= i-1) {
			if (!cells[i].isEmpty()) {
				cells[i].update();
			}
		}
	}
	
	public boolean isEmpty(int row, int col) {
		if(isPosInvalid(row,col)) return false;
		var idx = getIdxFromCoords(row,col);
		//if (!isIndexValid(idx)) return
		return cells[idx].isEmpty();
	}
	
	public boolean canMove( Cell cell, Direction dir) {
		var target = getCellAtDirection(cell, dir);
		if (target == null) return false;
		if(cell.getMaterial().canInteractWithMaterial(target.getMaterial())) return true;
		return target.isEmpty();
	}
	
	public void moveCellContent(Cell src, Cell target) {
		target.setMaterial(src.getMaterial());
		src.setMaterial(null);
		src.setDirty(false);
	}
	
	public void swapCells(Cell src, Cell dest) {
		var mat = dest.getMaterial();
		dest.setMaterial(src.getMaterial());
		src.setMaterial(mat);
	}
}
