package org.fallingsand;

import org.fallingsand.materials.Material;
import org.fallingsand.materials.Materials;
import processing.core.PApplet;



public class FallingSandSimulation extends PApplet {
	public static final int TICK_TIMER = 1;
	private final GridDomain domain;
	private  GridDisplay gridDisplay;
	private int tickTimer = TICK_TIMER;
	
	private Material currentMaterial = Materials.STONE;
	
	public FallingSandSimulation() {
		domain = new GridDomain(200,100);
		//domain.insertAt(10,10, Materials.WATER);
		
	}
	
	@Override
	public void settings() {
		size(600,800);
	}
	
	@Override
	public void mouseDragged() {
		var cellPos = gridDisplay.worldToCell(mouseX,mouseY);
	
			domain.insertAt(cellPos.row(),cellPos.col(), currentMaterial);
	}
	
	@Override
	public void setup() {
		gridDisplay = new GridDisplay(4,domain,getGraphics());
	}
	
	@Override
	public void draw() {
		if (tickTimer <0) {
			background(color(120));
			
			
			for (int i = 0; i < 10; i++) {
				domain.tick();
			}
		
			tickTimer = TICK_TIMER;
		}	else {
			tickTimer--;
		}
		
		if (key == 's') {
			currentMaterial = Materials.STONE;
		} else if (key == 'w') {
			currentMaterial = Materials.WATER;
		} else if (key == 'a') {
			currentMaterial = Materials.SAND;
		} else if (key == 'e') {
			currentMaterial = null;
		}
		stroke(color(55));
		//gridDisplay.showGrid();
		for( var cell: domain.getDirtyCells()) {
			var c = cell.getMaterial().getColor();
			gridDisplay.setCellColor(cell.row,cell.col,color(c.r(),c.g(),c.b()));
		}
		
		
		
	}
	
	public static void main(String[] args) {
		PApplet.main("org.fallingsand.FallingSandSimulation");
	
		
	}
}
