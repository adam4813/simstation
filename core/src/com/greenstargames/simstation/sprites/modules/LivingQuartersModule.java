package com.greenstargames.simstation.sprites.modules;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.greenstargames.simstation.sprites.Population;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Adam on 12/21/2015.
 */
public class LivingQuartersModule extends StationModule {
	private ArrayList<Population> populations = new ArrayList<Population>();
	private int maxPopulation = 5;
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
		renderer.set(ShapeRenderer.ShapeType.Filled);
		for (Population population : populations) {
			renderer.circle(population.getPositionX(), population.getPositionY(), 2.0f);
		}
	}

	@Override
	public boolean onClick() {
		if (populations.size() < maxPopulation) {
			Population population = new Population(this);
			java.util.Random random = new Random();

			Vector2 position = new Vector2(x * GRID_SIZE + 2 + random.nextFloat() * (GRID_SIZE - 4.0f),
					y * GRID_SIZE + 5);
			population.setPosition(position);
			populations.add(population);
		}
		return true;
	}

	@Override
	public void update(float delta) {
		for (Population population : populations) {
			if (population.movingToTarget()) {
				population.update(delta);
			} else {
				java.util.Random random = new Random();
				Vector2 target = new Vector2(x * GRID_SIZE + 2 + random.nextFloat() * (GRID_SIZE - 4.0f),
						y * GRID_SIZE + 5);
				population.setTargetPosition(target);
			}
		}
		return;
	}
}
