package com.greenstargames.simstation;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.greenstargames.simstation.sprites.Clickable;
import com.greenstargames.simstation.sprites.modules.StationModule;

/**
 * Created by Adam on 12/28/2015.
 */
public class GridCell implements Clickable {
	private final StationModule hullModule;
	private StationModule innerModule = null;

	public GridCell(StationModule hullModule) {
		this.hullModule = hullModule;
	}

	public boolean isEmpty() {
		return innerModule == null;
	}

	public StationModule getInnerModule() {
		return innerModule;
	}

	public void setInnerModule(StationModule module) {
		innerModule = module;
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

	public void update(float delta) {
		if (innerModule != null) {
			innerModule.update(delta);
		}
	}
}
