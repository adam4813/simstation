package com.greenstargames.simstation.sprites.sections;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.greenstargames.simstation.sprites.GridElement;
import com.greenstargames.simstation.sprites.modules.StationModule;

/**
 * Created by Adam on 12/21/2015.
 */
public class HullSection extends StationModule {
	public HullSection(int x, int y) {
		super(new Color(Color.SLATE), x, y, 1, 1, "basic hull");
	}

	public HullSection() {
		super(new Color(Color.SLATE), 0, 0, 1, 1, "basic hull");
	}

	@Override
	public void render(ShapeRenderer renderer) {
		renderer.setColor(color);
		renderer.rect(x * GRID_SIZE, y * GRID_SIZE + 1, width * GRID_SIZE, height * GRID_SIZE - 2);
		renderer.rect(x * GRID_SIZE + 1, y * GRID_SIZE, width * GRID_SIZE - 2, height * GRID_SIZE);
	}

	@Override
	public boolean isHull() {
		return true;
	}

	@Override
	public boolean canContain(GridElement element) {
		return true;
	}

	@Override
	public boolean onClick() {
		Gdx.app.log("HullSecction", "Clicked on " + name + " at (" + Integer.toString(x) + ", " + Integer.toString(y) + ")");
		return false;
	}

	@Override
	public GridElement factory(int x, int y) {
		return new HullSection(x, y);
	}

	@Override
	public void onPlaced() {

	}
}
