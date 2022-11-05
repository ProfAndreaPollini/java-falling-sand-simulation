package org.fallingsand.materials;

import org.fallingsand.Cell;
import org.fallingsand.Color;
import org.fallingsand.GridDomain;

public class Stone extends Material {
	@Override
	public void update(GridDomain domain, Cell cell) {
		return;
	}
	
	@Override
	public Color getColor() {
		return new Color(50,50,50);
	}
}
