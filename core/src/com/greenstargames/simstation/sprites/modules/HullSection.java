package com.greenstargames.simstation.sprites.modules;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

/**
 * Created by Adam on 12/21/2015.
 */
public class HullSection extends BaseModule {
	public HullSection(int x, int y) {
		super(new Color(Color.SLATE), x, y, 1, 1, "basic hull");
		loadTexture("hull.png");
	}

	public HullSection() {
		super(new Color(Color.SLATE), 0, 0, 1, 1, "basic hull");
		loadTexture("hull.png");
	}

	@Override
	public boolean isHull() {
		return true;
	}

	@Override
	public boolean onClick() {
		Gdx.app.log("HullSection", "Clicked on " + name + " at (" + Integer.toString(x) + ", " + Integer.toString(y) + ")");
		return false;
	}

	@Override
	public BaseModule factory(int x, int y) {
		return new HullSection(x, y);
	}
}
