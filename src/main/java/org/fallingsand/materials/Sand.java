package org.fallingsand.materials;

import org.fallingsand.Cell;
import org.fallingsand.Color;
import org.fallingsand.Direction;
import org.fallingsand.GridDomain;

public class Sand extends Material {
	private int c= 0;
	@Override
	public void update(GridDomain domain, Cell cell) {
		c++;
		if (c %3 != 0) return;
		
		c = 0;
		
		if (cell.isMoved()) {
			cell.setMoved(false);
			return;
		}
		
		if (domain.canMove(cell, Direction.DOWN)) {// is empty
			var target = domain.getCellAtDirection(cell, Direction.DOWN);
			if (target.isEmpty()) {
				domain.moveCellContent(cell, target);
			} else if (target.getMaterial() == Materials.WATER) {
				domain.swapCells(cell, target);
			}
			
			
			//cell = target;
		} else {
			var canMoveLeft = domain.canMove(cell, Direction.LEFT);
			var canMoveRight = domain.canMove(cell, Direction.RIGHT);
			var direction = Direction.NONE;
			if (canMoveLeft && canMoveRight) {
				direction = (Math.random() > 0.5) ? Direction.LEFT : Direction.RIGHT;
			} else if (canMoveLeft) {
				direction = Direction.LEFT;
			} else if (canMoveRight) {
				direction = Direction.RIGHT;
			}
//
			var target = domain.getCellAtDirection(cell, direction);
			if (Math.random() > 0.8)
			 moveIfEmpty(domain, cell, target, direction == Direction.LEFT);
//			if (moved) return;
//
//			var canMoveLeftDown = canMove(domain,cell,-1,1);
//			var  canMoveRightDown= canMove(domain,cell,1,1);
//			direction = Direction.NONE;
//			if (canMoveLeftDown && canMoveRightDown) {
//				direction = (Math.random() > 0.5) ? Direction.LEFT : Direction.RIGHT;
//			} else if (canMoveLeftDown) {
//				direction = Direction.LEFT;
//			} else if (canMoveRightDown) {
//				direction = Direction.RIGHT;
//			}
//
//			target = domain.getCellAt(cell,
//							(direction == Direction.LEFT)?-1:1,1);
//			moveIfEmpty(domain, cell, target, false);
			
		}
	}
	
//	private boolean canMove(GridDomain domain, Cell cell, Direction dir) {
//		var target = domain.getCellAtDirection(cell, dir);
//		if (target == null) return false;
//		if(target.getMaterial() == Materials.WATER) return true;
//		return target.isEmpty();
//	}
	
//	private boolean canMove(GridDomain domain, Cell cell, int dx, int dy) {
//		var target = domain.getCellAt(cell,dx,dy);
//		if (target == null) return false;
//		if (!target.isEmpty()) return false;
//		return true;
//	}
	
	
	@Override
	public boolean canInteractWithMaterial(Material material) {
		return material == Materials.WATER;
	}
	
	private static boolean moveIfEmpty(GridDomain domain, Cell src, Cell dest, boolean setMoved) {
		if (dest != null) {
			if (dest.isEmpty()) {
				domain.moveCellContent(src, dest);
				dest.setMoved(setMoved);
				return true;
			} else if (dest.getMaterial() == Materials.WATER) {
				domain.swapCells(src, dest);
				src.setMoved(setMoved);
			}
		}
		return false;
	}
	
	@Override
	public Color getColor() {
		return new Color(230, 224, 53);
	}
}
