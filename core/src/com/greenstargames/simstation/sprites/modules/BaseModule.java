package com.greenstargames.simstation.sprites.modules;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.greenstargames.simstation.screens.PlayScreen;
import com.greenstargames.simstation.sprites.Clickable;
import com.greenstargames.simstation.sprites.Renderable;

/**
 * Created by Adam on 12/27/2015.
 */
public abstract class BaseModule extends Renderable implements Clickable {
	protected String name = "";
	private int waterConsumed = 0;
	private boolean waterOff;
	private int powerConsumed = 0;
	private boolean powerOff;
	private int workersConsumed = 0;
	private boolean workerOff;

	public BaseModule(Color color, int x, int y, int width, int height, String name) {
		super(color, x, y, width, height);
		this.name = name;
	}

	public int getWaterConsumed() {
		return waterConsumed;
	}

	public void setWaterConsumed(int waterConsumed) {
		this.waterConsumed = waterConsumed;
	}

	public boolean isWaterOff() {
		return waterOff;
	}

	public void setWaterOff(boolean waterOff) {
		this.waterOff = waterOff;
	}

	public int getPowerConsumed() {
		return powerConsumed;
	}

	public void setPowerConsumed(int powerConsumed) {
		this.powerConsumed = powerConsumed;
	}

	public boolean isPowerOff() {
		return powerOff;
	}

	public void setPowerOff(boolean powerOff) {
		this.powerOff = powerOff;
	}

	public abstract BaseModule factory(int x, int y);

	public String getName() {
		return name;
	}

	public boolean isHull() {
		return false;
	}

	public void update(float delta) {
	}

	public int getWorkersConsumed() {
		return workersConsumed;
	}

	public void setWorkersConsumed(int workersConsumed) {
		this.workersConsumed = workersConsumed;
	}

	public boolean isWorkerOff() {
		return workerOff;
	}

	public void setWorkerOff(boolean workerOff) {
		this.workerOff = workerOff;
	}

	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
		if (isWorkerOff()) {
			Sprite no_worker_sprite = PlayScreen.NO_WORKER_SPRITE;
			no_worker_sprite.setPosition(x * GRID_SIZE, y * GRID_SIZE);
			no_worker_sprite.draw(batch);
		}
		if (isWaterOff()) {
			Sprite no_water_sprite = PlayScreen.NO_WATER_SPRITE;
			no_water_sprite.setPosition(x * GRID_SIZE + GRID_SIZE / 2, y * GRID_SIZE);
			no_water_sprite.draw(batch);
		}
		if (isPowerOff()) {
			Sprite no_power_sprite = PlayScreen.NO_POWER_SPRITE;
			no_power_sprite.setPosition(x * GRID_SIZE, y * GRID_SIZE + GRID_SIZE / 2);
			no_power_sprite.draw(batch);
		}
	}
}
