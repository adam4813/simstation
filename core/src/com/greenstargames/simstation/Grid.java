package com.greenstargames.simstation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.greenstargames.simstation.sprites.GridElement;

/**
 * Created by Adam on 12/28/2015.
 */
public class Grid {
	private GridCell[][] gridCells;

	public Grid(int width, int height) {
		gridCells = new GridCell[width][height];
	}

	// TODO: Multi-cell element
	public boolean tryPlace(GridElement element, int x, int y) {
		if (gridCells[x][y] == null) {
			if (element.isHull()) {
				gridCells[x][y] = new GridCell(element);
			} else {
				Gdx.app.log("Grid -> tryPlace", "Can't place non-hull element in an empty grid cell.");
				return false;
			}
		} else {
			if (!element.isHull() && gridCells[x][y].canContain(element)) {
				gridCells[x][y].setModuleElement(element);
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
