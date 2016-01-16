package com.greenstargames.simstation;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.greenstargames.simstation.sprites.modules.StationModule;

/**
 * Created by Adam on 12/28/2015.
 */
public class Grid {
	private GridCell[][] gridCells;
	private int width;
	private int height;

	public Grid(int width, int height) {
		this.width = width;
		this.height = height;
		gridCells = new GridCell[width][height];
	}

	// TODO: Multi-cell element
	public boolean tryPlace(StationModule module, int x, int y) {
		if ((x < width && x >= 0) && (y < height && y >= 0)) {
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
		return false;
	}

	public GridCell getCell(int x, int y) {
		if ((x < width && x >= 0) && (y < height && y >= 0)) {
			return gridCells[x][y];
		}
		return null;
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
		if ((x < width && x >= 0) && (y < height && y >= 0)) {
			GridCell cell = gridCells[x][y];
			if (cell != null) {
				cell.onClick();
			}
		}
	}

	public void update(float delta) {
		for (GridCell[] cellRow : gridCells) {
			for (GridCell cell : cellRow) {
				if (cell != null) {
					cell.update(delta);
				}
			}
		}
	}
}
