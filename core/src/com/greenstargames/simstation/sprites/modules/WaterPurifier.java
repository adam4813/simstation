package com.greenstargames.simstation.sprites.modules;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Adam on 1/17/2016.
 */
public class WaterPurifier extends Producer {
	public WaterPurifier(int x, int y) {
		super(new Color(Color.BLUE), x, y, 1, 1, "water purification");
		setPowerConsumed(2);
		loadTexture("water_purifier.png");
	}

	public WaterPurifier() {
		super(new Color(Color.BLUE), 0, 0, 1, 1, "water purification");
		loadTexture("water_purifier.png");
	}

	@Override
	public BaseModule factory(int x, int y) {
		return new WaterPurifier(x, y);
	}

	@Override
	public boolean onClick() {
		return false;
	}
}
