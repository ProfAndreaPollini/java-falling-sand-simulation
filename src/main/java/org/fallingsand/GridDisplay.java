package org.fallingsand;

import processing.core.PGraphics;

public class GridDisplay {
	private final int cellSize;
	private final GridDomain domain;
	private final PGraphics ctx;
	
	public GridDisplay(int cellSize, GridDomain domain, PGraphics ctx) {
		this.cellSize = cellSize;
		this.domain = domain;
		
		this.ctx = ctx;
	}
	
	public void showGrid() {
		for (int i = 0; i < domain.getCols(); i++) {
			ctx.line(i*cellSize,0,i*(cellSize),domain.getRows()*cellSize);
		}
		
		for (int j = 0; j < domain.getRows(); j++) {
			ctx.line(0,j * cellSize,domain.getCols()*cellSize,j * cellSize);
		}
	}
	public void setCellColor(int row, int col, int color) {
		ctx.fill(color);
		ctx.rect(col*cellSize,row*cellSize,cellSize,cellSize);
	}
	
	public int getCellSize() {
		return cellSize;
	}
	
	public Vec2 worldToCell(int x, int y) {
		var row = y/cellSize;
		var col = x/cellSize;
		return new Vec2(row,col);
	}
}
