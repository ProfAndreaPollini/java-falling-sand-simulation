package org.fallingsand.materials;

import org.fallingsand.Cell;
import org.fallingsand.Color;
import org.fallingsand.Direction;
import org.fallingsand.GridDomain;


/*
possiamo prendere la riga in basso, se la cella è piena ed è
piena quella sopra allora scorri a destra o a sinistra, se puoi,
altrimenti fai scorrere tutto il blocco orizzontale a partire dalla
prima cella che ne ha una libera accanto.
Se la riga è tutta piena, allora fai questo sulla riga appena sopra.
 */
public class Water extends Material {
	@Override
	public void update(GridDomain domain, Cell cell) {
		if (cell.isMoved()) {
			cell.setMoved(false);
			return;
		}

//		if (Math.random() > 0.5) {
//			cell.setDirection(Direction.LEFT);
//		} else {
//			cell.setDirection(Direction.RIGHT);
//		}
		for (int i = 1; i <= 1; i++) {
			var moved = false;
			var target = domain.getCellAtDirection(cell, Direction.DOWN);
			
			if ((target != null) && target.isEmpty() ) {// is empty
					//cell.setDirection(Direction.DOWN);
					domain.moveCellContent(cell, target);
					cell = target;
				} else {
					
					var canMoveLeft = domain.canMove( cell, Direction.LEFT);
					var canMoveRight = domain.canMove( cell, Direction.RIGHT);
					var direction = Direction.NONE;
					if (canMoveLeft && canMoveRight) {
						direction = (Math.random() > 0.5) ? Direction.LEFT : Direction.RIGHT;
					} else if (canMoveLeft) {
						direction = Direction.LEFT;
					} else if (canMoveRight) {
						direction = Direction.RIGHT;
					}
					
					
					target = domain.getCellAtDirection(cell, direction);
					if (target == null) continue;
					if (!target.isEmpty()) continue;
					moved = moveIfEmpty(domain, cell, target, direction == Direction.LEFT);
					if (moved) {
						cell = target;
						var canMoveDown = domain.canMove( target, Direction.DOWN);
						if (canMoveDown) {
							var movedDown = moveIfEmpty(domain, target, domain.getCellAtDirection(target, Direction.DOWN), false);
							if (movedDown) cell =domain.getCellAtDirection(target, Direction.DOWN);
						}
//						var movedDown = moveIfEmpty(domain, target, downCell, false);
//						if (movedDown) cell = downCell;
						//cell.setMoved(true);
					}
					
				}
//			} else {
//				var canMoveLeft = canMove(domain, cell, Direction.LEFT);
//				var canMoveRight = canMove(domain, cell, Direction.RIGHT);
//				var direction = Direction.NONE;
//				if (canMoveLeft && canMoveRight) {
//					direction = (Math.random() > 0.5) ? Direction.LEFT : Direction.RIGHT;
//				} else if (canMoveLeft) {
//					direction = Direction.LEFT;
//				} else if (canMoveRight) {
//					direction = Direction.RIGHT;
//				}
//
//
//				target = domain.getCellAtDirection(cell, direction);
//				if (target == null) continue;
//				if (!target.isEmpty()) continue;
//				moved = moveIfEmpty(domain, cell, target, direction == Direction.LEFT);
//				if (moved) {
//					cell = target;
//					var downCell = domain.getCellAtDirection(target, Direction.DOWN);
//					var movedDown = moveIfEmpty(domain, target, downCell, false);
//					if (movedDown) cell = downCell;
//					//cell.setMoved(true);
//				}
//			}
		}
	}
	
	@Override
	public boolean canInteractWithMaterial(Material material) {
		return material == Materials.SAND;
	}
	
//	private boolean canMove(GridDomain domain, Cell cell, Direction dir) {
//		var target = domain.getCellAtDirection(cell, dir);
//		if (target == null) return false;
//		if(target.getMaterial() == Materials.SAND) return true;
//		if (!target.isEmpty()) return false;
//		return true;
//	}
	
	private static boolean moveIfEmpty(GridDomain domain, Cell src, Cell dest, boolean setMoved) {
		if (dest != null) {
			if (dest.isEmpty()) {
				domain.moveCellContent(src, dest);
				dest.setMoved(setMoved);
				return true;
			}else if (dest.getMaterial() == Materials.SAND && Math.random() >0.9) {
				domain.swapCells(src, dest);
				src.setMoved(setMoved);
			}
		}
		return false;
	}
	
	
	@Override
	public Color getColor() {
		return new Color(0, 0, 255);
	}
}
