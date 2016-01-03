package com.greenstargames.simstation;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.greenstargames.simstation.sprites.Clickable;
import com.greenstargames.simstation.sprites.modules.StationModule;

/**
 * Created by Adam on 12/28/2015.
 */
public class GridCell implements Clickable {
	private StationModule hullModule;
	private StationModule innerModule = null;

	public GridCell(StationModule hullModule) {
		this.hullModule = hullModule;
	}

	public void setHullModule(StationModule hullModule) {
		this.hullModule = hullModule;
	}

	public StationModule getHullModule() {
		return hullModule;
	}

	public boolean canContain(StationModule module) {
		return innerModule == null && hullModule.canContain(module);
	}

	public void setInnerModule(StationModule module) {
		innerModule = module;
	}

	public StationModule getInnerModule() {
		return innerModule;
	}

	public boolean onClick() {
		if (innerModule == null) {
			hullModule.onClick();
		} else {
			innerModule.onClick();
		}
		return true;
	}

	public void render(ShapeRenderer shapeRenderer) {
		hullModule.render(shapeRenderer);
		if (innerModule != null) {
			innerModule.render(shapeRenderer);
		}
	}
}
