package com.greenstargames.simstation;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.greenstargames.simstation.sprites.modules.BaseModule;
import com.greenstargames.simstation.sprites.modules.HullSection;
import com.greenstargames.simstation.sprites.modules.PowerGenerator;
import com.greenstargames.simstation.sprites.modules.Producer;
import com.greenstargames.simstation.sprites.modules.WaterPurifier;

import java.util.ArrayList;

/**
 * Created by Adam on 12/28/2015.
 */
public class Grid {
	private GridCell[][] gridCells;
	private ArrayList<Producer> producers = new ArrayList<Producer>();
	private int width;
	private int height;

	public Grid(int width, int height) {
		this.width = width;
		this.height = height;
		gridCells = new GridCell[width][height];
		gridCells[10][10] = new GridCell(new HullSection(10, 10));
	}

	// TODO: Multi-cell element
	public boolean tryPlace(BaseModule module, int x, int y) {
		if ((x < width && x >= 0) && (y < height && y >= 0)) {
			if (gridCells[x][y] == null) {
				if (module.isHull()) {
					int neighborCount = 0;
					for (int i = -1; i <= 1; i += 2) {
						if ((x + i < width) && (x + i >= 0)) {
							if (gridCells[x + i][y] != null) {
								neighborCount++;
							}
						}
					}
					for (int i = -1; i <= 1; i += 2) {
						if ((y + i < height) && (y + i >= 0)) {
							if (gridCells[x][y + i] != null) {
								neighborCount++;
							}
						}
					}
					if (neighborCount > 0) {
						gridCells[x][y] = new GridCell(module);
					}
				} else {
					return false;
				}
			} else {
				if (!module.isHull() && gridCells[x][y].isEmpty()) {
					gridCells[x][y].setInnerModule(module);
					if (module instanceof Producer) {
						producers.add((Producer) module);
					}
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

	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		for (GridCell[] cellRow : gridCells) {
			for (GridCell cell : cellRow) {
				if (cell != null) {
					cell.render(batch, shapeRenderer);
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
		int totalAvailablePower = 0;
		int totalAvailableWater = 0;
		for (Producer producer : producers) {
			producer.update(delta);
			if (producer instanceof PowerGenerator) {
				totalAvailablePower += producer.getAvailableUnits();
			} else if (producer instanceof WaterPurifier) {
				totalAvailableWater += producer.getAvailableUnits();
			}
		}
		//Gdx.app.log("Grid::Update", "Total Power: " + Integer.toString(totalAvailablePower));
		//Gdx.app.log("Grid::Update", "Total Water: " + Integer.toString(totalAvailableWater));
		for (GridCell[] cellRow : gridCells) {
			for (GridCell cell : cellRow) {
				if (cell != null) {
					BaseModule module = cell.getInnerModule();
					if (module != null) {
						int modulePower = module.getPowerConsumed();
						if (modulePower <= totalAvailablePower) {
							module.setPowerOff(false);
							totalAvailablePower -= modulePower;
						} else {
							module.setPowerOff(true);
						}
						int moduleWater = module.getWaterConsumed();
						if (moduleWater <= totalAvailableWater) {
							module.setWaterOff(false);
							totalAvailableWater -= moduleWater;
						} else {
							module.setWaterOff(true);
						}
						module.update(delta);
					}
				}
			}
		}
		//Gdx.app.log("Grid::Update", "Power Left: " + Integer.toString(totalAvailablePower));
		//Gdx.app.log("Grid::Update", "Water Left: " + Integer.toString(totalAvailableWater));
	}
}
