package com.greenstargames.simstation;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.greenstargames.simstation.sprites.Clickable;
import com.greenstargames.simstation.sprites.modules.BaseModule;

/**
 * Created by Adam on 12/28/2015.
 */
public class GridCell implements Clickable {
	private final BaseModule hullModule;
	private BaseModule innerModule = null;

	public GridCell(BaseModule hullModule) {
		this.hullModule = hullModule;
	}

	public boolean isEmpty() {
		return innerModule == null;
	}

	public BaseModule getInnerModule() {
		return innerModule;
	}

	public void setInnerModule(BaseModule module) {
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

	public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
		hullModule.render(batch, shapeRenderer);
		if (innerModule != null) {
			innerModule.render(batch, shapeRenderer);
		}
	}
}
