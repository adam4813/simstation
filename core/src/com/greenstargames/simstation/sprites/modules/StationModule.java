package com.greenstargames.simstation.sprites.modules;

import com.badlogic.gdx.graphics.Color;
import com.greenstargames.simstation.sprites.Clickable;
import com.greenstargames.simstation.sprites.sections.Renderable;

/**
 * Created by Adam on 12/27/2015.
 */
public abstract class StationModule extends Renderable implements Clickable {
	protected String name = "";

	public StationModule(Color color, int x, int y, int width, int height, String name) {
		super(color, x, y, width, height);
		this.name = name;
	}

	public abstract StationModule factory(int x, int y);

	public String getName() {
		return name;
	}

	public boolean isHull() {
		return false;
	}

	public boolean canContain(StationModule module) {
		return false;
	}
}
