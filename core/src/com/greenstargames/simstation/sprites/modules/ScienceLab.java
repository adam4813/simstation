package com.greenstargames.simstation.sprites.modules;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Adam on 1/18/2016.
 */
public class ScienceLab extends Producer {
	static private final int POPULATION_CONSUMED = 4;
	static private final int POWER_CONSUMED = 6;
	static private final int WATER_CONSUMED = 2;

	public ScienceLab(int x, int y) {
		super(new Color(Color.PURPLE), x, y, 1, 1, "science lab");
		setWaterConsumed(WATER_CONSUMED);
		setWorkersConsumed(POPULATION_CONSUMED);
		setPowerConsumed(POWER_CONSUMED);
		loadTexture("science_lab.png");
	}

	public ScienceLab() {
		super(new Color(Color.PURPLE), 0, 0, 1, 1, "science lab");
		loadTexture("science_lab.png");
	}

	@Override
	public BaseModule factory(int x, int y) {
		return new ScienceLab(x, y);
	}


	@Override
	public boolean onClick() {
		return false;
	}
}
