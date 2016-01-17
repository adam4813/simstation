package com.greenstargames.simstation.sprites.modules;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.greenstargames.simstation.sprites.Population;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Adam on 12/21/2015.
 */
public class LivingQuarters extends BaseModule {
	static private final Color blueColor = new Color(Color.BLUE);
	static private final int MAX_POPULATION = 5;
	static private final int POWER_CONSUMED = 2;
	static private final int WATER_CONSUMED = 1;
	private final ArrayList<Population> populations = new ArrayList<Population>();

	public LivingQuarters(int x, int y) {
		super(new Color(Color.CORAL), x, y, 1, 1, "living quarters");
		setPowerConsumed(POWER_CONSUMED);
		setWaterConsumed(WATER_CONSUMED);
	}

	public LivingQuarters() {
		super(new Color(Color.CORAL), 0, 0, 1, 1, "living quarters");
	}

	public BaseModule factory(int x, int y) {
		return new LivingQuarters(x, y);
	}

	@Override
	public void render(SpriteBatch batch, ShapeRenderer renderer) {
		renderer.setColor(color);
		renderer.rect(x * GRID_SIZE + 2, y * GRID_SIZE + 2,
				width * GRID_SIZE - 4, height * GRID_SIZE - 4);
		renderer.setColor(blueColor);
		renderer.set(ShapeRenderer.ShapeType.Filled);
		for (Population population : populations) {
			renderer.circle(population.getPositionX(), population.getPositionY(), 2.0f);
		}
		if (isPowerOff()) {
			renderer.setColor(Color.RED);
			renderer.rectLine(x * GRID_SIZE + 4, y * GRID_SIZE + 4, (x + width / 2.0f) * GRID_SIZE - 4, (y + height / 2.0f) * GRID_SIZE - 4, 3.0f);
			renderer.rectLine(x * GRID_SIZE + 4, (y + height / 2.0f) * GRID_SIZE - 4, (x + width / 2.0f) * GRID_SIZE - 4, y * GRID_SIZE + 4, 3.0f);
			renderer.setColor(Color.GREEN);
			renderer.rectLine(x * GRID_SIZE + 4, y * GRID_SIZE + 4, (x + width / 2.0f) * GRID_SIZE - 4, (y + height / 2.0f) * GRID_SIZE - 4, 1.0f);
			renderer.rectLine(x * GRID_SIZE + 4, (y + height / 2.0f) * GRID_SIZE - 4, (x + width / 2.0f) * GRID_SIZE - 4, y * GRID_SIZE + 4, 1.0f);
		}
		if (isWaterOff()) {
			renderer.setColor(Color.RED);
			renderer.rectLine((x + width / 2.0f) * GRID_SIZE + 4, y * GRID_SIZE + 4, (x + width) * GRID_SIZE - 4, (y + height / 2.0f) * GRID_SIZE - 4, 3.0f);
			renderer.rectLine((x + width / 2.0f) * GRID_SIZE + 4, (y + height / 2.0f) * GRID_SIZE - 4, (x + width) * GRID_SIZE - 4, y * GRID_SIZE + 4, 3.0f);
			renderer.setColor(Color.BLUE);
			renderer.rectLine((x + width / 2.0f) * GRID_SIZE + 4, y * GRID_SIZE + 4, (x + width) * GRID_SIZE - 4, (y + height / 2.0f) * GRID_SIZE - 4, 1.0f);
			renderer.rectLine((x + width / 2.0f) * GRID_SIZE + 4, (y + height / 2.0f) * GRID_SIZE - 4, (x + width) * GRID_SIZE - 4, y * GRID_SIZE + 4, 1.0f);
		}
	}

	@Override
	public boolean onClick() {
		if (populations.size() < MAX_POPULATION) {
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
	}
}
