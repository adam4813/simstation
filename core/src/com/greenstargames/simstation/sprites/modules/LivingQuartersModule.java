package com.greenstargames.simstation.sprites.modules;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Adam on 12/21/2015.
 */
public class LivingQuartersModule extends StationModule {
	private int population = 0;
	private ArrayList<Vector2> populationLocations = new ArrayList<Vector2>();
	static Color blueColor = new Color(Color.BLUE);

	public LivingQuartersModule(int x, int y) {
		super(new Color(Color.CORAL), x, y, 1, 1, "living quarters");
	}

	public LivingQuartersModule() {
		super(new Color(Color.CORAL), 0, 0, 1, 1, "living quarters");
	}

	public StationModule factory(int x, int y) {
		return new LivingQuartersModule(x, y);
	}

	@Override
	public void render(ShapeRenderer renderer) {
		renderer.setColor(color);
		renderer.rect(x * GRID_SIZE + 2, y * GRID_SIZE + 2,
				width * GRID_SIZE - 4, height * GRID_SIZE - 4);
		renderer.setColor(blueColor);
		for (Vector2 location : populationLocations) {
			renderer.point(location.x, location.y, 0);
		}
	}

	@Override
	public boolean onClick() {
		population++;
		java.util.Random random = new Random();

		populationLocations.add(new Vector2(x * GRID_SIZE + 2 + random.nextFloat() * (GRID_SIZE - 4.0f),
				y * GRID_SIZE + 2 + random.nextFloat() * (GRID_SIZE - 4.0f)));

		return true;
	}
}
