package com.greenstargames.simstation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.greenstargames.simstation.sprites.modules.BaseModule;
import com.greenstargames.simstation.sprites.modules.HullSection;
import com.greenstargames.simstation.sprites.modules.LivingQuarters;
import com.greenstargames.simstation.sprites.modules.PowerGenerator;
import com.greenstargames.simstation.sprites.modules.Producer;
import com.greenstargames.simstation.sprites.modules.ScienceLab;
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
	private String saveFilename = "";

	public Grid() {
	}

	public void reset(int width, int height) {
		resize(width, height);
		cells[10][10] = new Cell(new HullSection(10, 10));
	}

	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
		cells = new Cell[width][height];
	}

	public void save() {
		String output = width + "," + height + ",";
		for (int i = 0; i < width; ++i) {
			for (int j = 0; j < height; ++j) {
				if (cells[i][j] != null) {
					BaseModule module = cells[i][j].innerModule;
					if (module != null) {
						if (module instanceof PowerGenerator) {
							output += "2";
						} else if (module instanceof WaterPurifier) {
							output += "3";
						} else if (module instanceof LivingQuarters) {
							output += "4";
						} else if (module instanceof ScienceLab) {
							output += "5";
						}
					} else {
						output += "1";
					}
				} else {
					output += "0";
				}
			}
		}

		final String finalOutput = output;
		Gdx.input.getTextInput(new TextInputListener() {
			@Override
			public void input(String text) {
				saveFilename = text;
				FileHandle file = Gdx.files.local("saves/" + text + ".sss");
				file.writeString(finalOutput, true);
			}

			@Override
			public void canceled() {
			}
		}, "save as..", "", saveFilename);
	}

	public void load(String filename) {
		FileHandle file = Gdx.files.local("saves/" + filename + ".sss");
		saveFilename = filename;
		String input = file.readString();
		int width = Integer.parseInt(input.substring(0, input.indexOf(",")));
		int height = Integer.parseInt(input.substring(input.indexOf(",") + 1, input.indexOf(",", input.indexOf(",") + 1)));
		resize(width, height);
		String cellString = input.substring(input.lastIndexOf(",") + 1);
		for (int j = 0; j < height; ++j) {
			for (int i = 0; i < width; ++i) {
				char c = cellString.charAt(i * height + j);
				if (c != '0') {
					cells[i][j] = new Cell(new HullSection(i, j));
					switch (c) {
						case '5':
							tryPlace(new ScienceLab(i, j), i, j);
							break;
						case '4':
							tryPlace(new LivingQuarters(i, j), i, j);
							break;
						case '3':
							tryPlace(new WaterPurifier(i, j), i, j);
							break;
						case '2':
							tryPlace(new PowerGenerator(i, j), i, j);
							break;
						case '1':
							tryPlace(new HullSection(i, j), i, j);
							break;
					}
				}
			}
		}
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

	public void render(SpriteBatch batch) {
		for (Cell[] cellRow : cells) {
			for (Cell cell : cellRow) {
				if (cell != null) {
					cell.hullModule.render(batch);
					if (cell.innerModule != null) {
						cell.innerModule.render(batch);
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
		int totalAvailablePopulation = 0;
		for (Producer producer : producers) {
			producer.update(delta);
			if (producer instanceof PowerGenerator) {
				totalAvailablePower += producer.getAvailableUnits();
			} else if (producer instanceof WaterPurifier) {
				totalAvailableWater += producer.getAvailableUnits();
			} else if (producer instanceof LivingQuarters) {
				totalAvailablePopulation += producer.getAvailableUnits();
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
						int modulePopulation = cell.innerModule.getWorkersConsumed();
						if (modulePopulation <= totalAvailablePopulation) {
							cell.innerModule.setWorkerOff(false);
							totalAvailablePopulation -= modulePopulation;
						} else {
							cell.innerModule.setWorkerOff(true);
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
