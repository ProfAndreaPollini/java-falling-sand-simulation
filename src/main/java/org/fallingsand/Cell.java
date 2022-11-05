package org.fallingsand;

import lombok.Data;
import org.fallingsand.materials.Material;

@Data
public class Cell {
	private Material material;
	private final GridDomain domain;
	public int row;
	public final int col;
	public boolean dirty = false;
	public boolean moved = false;
	public Direction direction = Direction.NONE;
	
	public boolean isDirty() {
		return dirty;
	}
	
	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}
	
	public Cell(GridDomain domain, int row, int col) {
		this.domain = domain;
		this.row = row;
		this.col = col;
		material = null;
		if (Math.random() > 0.5) {
			direction =Direction.LEFT;
		} else {
			direction =Direction.RIGHT;
		}
		
	}
	
	public void  update() {
		material.update(domain,this);
	}
	
	public boolean isEmpty() {
		return material == null;
	}
	
	public void changeMaterialTo(Material material) {
		this.material = material;
	}
}
