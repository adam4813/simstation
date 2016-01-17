package com.greenstargames.simstation.sprites.modules;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Adam on 1/17/2016.
 */
public abstract class Producer extends BaseModule {
	protected int availableUnits;
	protected int maxUnitOutput = 10;

	public Producer(Color color, int x, int y, int width, int height, String name) {
		super(color, x, y, width, height, name);
	}

	public int getMaxUnitOutput() {
		return maxUnitOutput;
	}

	public int getAvailableUnits() {
		return availableUnits;
	}

	@Override
	public void update(float delta) {
		availableUnits = maxUnitOutput;
	}
}
