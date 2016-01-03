package com.greenstargames.simstation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.greenstargames.simstation.sprites.modules.StationModule;

/**
 * Created by Adam on 12/28/2015.
 */
public class Grid {
	private GridCell[][] gridCells;

	public Grid(int width, int height) {
		gridCells = new GridCell[width][height];
	}

	// TODO: Multi-cell element
	public boolean tryPlace(StationModule module, int x, int y) {
		if (gridCells[x][y] == null) {
			if (module.isHull()) {
				gridCells[x][y] = new GridCell(module);
			} else {
				return false;
			}
		} else {
			if (!module.isHull() && gridCells[x][y].canContain(module)) {
				gridCells[x][y].setInnerModule(module);
			}
		}
		return true;
	}

	public GridCell getCell(int x, int y) {
		return gridCells[x][y];
	}

	public void render(ShapeRenderer shapeRenderer) {
		for (GridCell[] cellRow : gridCells) {
			for (GridCell cell : cellRow) {
				if (cell != null) {
					cell.render(shapeRenderer);
				}
			}
		}
	}

	public void onClick(int x, int y) {
		GridCell cell = gridCells[x][y];
		if (cell != null) {
			cell.onClick();
		}
	}
}
