package org.fallingsand.materials;

import org.fallingsand.Cell;
import org.fallingsand.Color;
import org.fallingsand.GridDomain;

public abstract class Material {
	public abstract void update(GridDomain domain, Cell cell);
	
	public abstract Color getColor();
	
	public  boolean canInteractWithMaterial(Material material) {
		return false;
	}
}
