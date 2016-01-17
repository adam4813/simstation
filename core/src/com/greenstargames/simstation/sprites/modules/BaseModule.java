package com.greenstargames.simstation.sprites.modules;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.greenstargames.simstation.sprites.Clickable;
import com.greenstargames.simstation.sprites.Renderable;

/**
 * Created by Adam on 12/27/2015.
 */
public abstract class BaseModule extends Renderable implements Clickable {
	protected String name = "";

	public int getWaterConsumed() {
		return waterConsumed;
	}

	public void setWaterConsumed(int waterConsumed) {
		this.waterConsumed = waterConsumed;
	}

	private int waterConsumed;

	public void setWaterOff(boolean waterOff) {
		this.waterOff = waterOff;
	}

	public boolean isWaterOff() {
		return waterOff;
	}

	private boolean waterOff;

	public int getPowerConsumed() {
		return powerConsumed;
	}

	public void setPowerConsumed(int powerConsumed) {
		this.powerConsumed = powerConsumed;
	}

	private int powerConsumed;

	public boolean isPowerOff() {
		return powerOff;
	}

	public void setPowerOff(boolean powerOff) {
		this.powerOff = powerOff;
	}

	private boolean powerOff;

	public void setPowerOff(boolean powerOff) {
		this.powerOff = powerOff;
	}

	public BaseModule(Color color, int x, int y, int width, int height, String name) {
		super(color, x, y, width, height);
		this.name = name;
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

	@Override
	public void render(SpriteBatch batch, ShapeRenderer renderer) {
		renderer.setColor(color);
		renderer.rect(x * GRID_SIZE + 2, y * GRID_SIZE + 2,
				width * GRID_SIZE - 4, height * GRID_SIZE - 4);
	}
}
