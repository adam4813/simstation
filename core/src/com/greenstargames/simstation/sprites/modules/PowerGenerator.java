package com.greenstargames.simstation.sprites.modules;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Adam on 1/17/2016.
 */
public class PowerGenerator extends Producer {
	static private final int POPULATION_CONSUMED = 2;
	static private final int MAX_POWER = 10;
	static private final int WATER_CONSUMED = 2;

	public PowerGenerator(int x, int y) {
		super(new Color(Color.GREEN), x, y, 1, 1, "power generator");
		setWaterConsumed(WATER_CONSUMED);
		setWorkersConsumed(POPULATION_CONSUMED);
		setMaxUnitOutput(MAX_POWER);
		loadTexture("power_generator.png");
	}

	public PowerGenerator() {
		super(new Color(Color.GREEN), 0, 0, 1, 1, "power generator");
		loadTexture("power_generator.png");
	}

	@Override
	public BaseModule factory(int x, int y) {
		return new PowerGenerator(x, y);
	}

	@Override
	public boolean onClick() {
		return false;
	}
}
