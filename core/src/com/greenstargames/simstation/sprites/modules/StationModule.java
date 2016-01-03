package com.greenstargames.simstation.sprites.modules;

import com.badlogic.gdx.graphics.Color;
import com.greenstargames.simstation.sprites.GridElement;
import com.greenstargames.simstation.sprites.sections.Renderable;

/**
 * Created by Adam on 12/27/2015.
 */
public abstract class StationModule extends Renderable implements GridElement {
	protected String name = "";

	public StationModule(Color color, int x, int y, int width, int height, String name) {
		super(color, x, y, width, height);
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isHull() {
		return false;
	}

	@Override
	public boolean canContain(GridElement element) {
		return false;
	}
}
