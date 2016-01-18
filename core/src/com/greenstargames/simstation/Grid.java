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

	private Cell[][] cells;
	private ArrayList<Producer> producers = new ArrayList<Producer>();
	private int width;
	private int height;
	public Grid(int width, int height) {
		this.width = width;
		this.height = height;
		cells = new Cell[width][height];
		cells[10][10] = new Cell(new HullSection(10, 10));
	}

	// TODO: Multi-cell element
	public boolean tryPlace(BaseModule module, int x, int y) {
		if ((x < width && x >= 0) && (y < height && y >= 0)) {
			if (cells[x][y] == null) {
				if (module.isHull()) {
					int neighborCount = 0;
					for (int i = -1; i <= 1; i += 2) {
						if ((x + i < width) && (x + i >= 0)) {
							if (cells[x + i][y] != null) {
								neighborCount++;
							}
						}
					}
					for (int i = -1; i <= 1; i += 2) {
						if ((y + i < height) && (y + i >= 0)) {
							if (cells[x][y + i] != null) {
								neighborCount++;
							}
						}
					}
					if (neighborCount > 0) {
						cells[x][y] = new Cell(module);
					}
				} else {
					return false;
				}
			} else {
				if (!module.isHull() && cells[x][y].innerModule == null) {
					cells[x][y].innerModule = module;
					if (module instanceof Producer) {
						producers.add((Producer) module);
					}
				}
			}
			return true;
		}
		return false;
	}

	public boolean canPlace(BaseModule module, int x, int y) {
		if ((x < width && x >= 0) && (y < height && y >= 0)) {
			if (cells[x][y] == null) {
				if (module.isHull()) {
					return true;
				}
			} else {
				if (!module.isHull() && cells[x][y].innerModule == null) {
					return true;
				}
			}
		}
		return false;
	}

	public Cell getCell(int x, int y) {
		if ((x < width && x >= 0) && (y < height && y >= 0)) {
			return cells[x][y];
		}
		return null;
	}

	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		for (Cell[] cellRow : cells) {
			for (Cell cell : cellRow) {
				if (cell != null) {
					cell.hullModule.render(batch, shapeRenderer);
					if (cell.innerModule != null) {
						cell.innerModule.render(batch, shapeRenderer);
					}
				}
			}
		}
	}

	public void onClick(int x, int y) {
		if ((x < width && x >= 0) && (y < height && y >= 0)) {
			Cell cell = cells[x][y];
			if (cell != null) {
				cell.innerModule.onClick();
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
		for (Cell[] cellRow : cells) {
			for (Cell cell : cellRow) {
				if (cell != null) {
					if (cell.innerModule != null) {
						int modulePower = cell.innerModule.getPowerConsumed();
						if (modulePower <= totalAvailablePower) {
							cell.innerModule.setPowerOff(false);
							totalAvailablePower -= modulePower;
						} else {
							cell.innerModule.setPowerOff(true);
						}
						int moduleWater = cell.innerModule.getWaterConsumed();
						if (moduleWater <= totalAvailableWater) {
							cell.innerModule.setWaterOff(false);
							totalAvailableWater -= moduleWater;
						} else {
							cell.innerModule.setWaterOff(true);
						}
						cell.innerModule.update(delta);
					}
				}
			}
		}
	}

	public class Cell {
		private final BaseModule hullModule;
		private BaseModule innerModule = null;

		public Cell(BaseModule hullModule) {
			this.hullModule = hullModule;
		}

		public BaseModule getInnerModule() {
			return innerModule;
		}
	}
}
