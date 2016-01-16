package com.greenstargames.simstation.sprites;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.greenstargames.simstation.sprites.modules.LivingQuartersModule;

/**
 * Created by Adam on 1/2/2016.
 */
public class Population {
	private final LivingQuartersModule home;
	private final float speed = 1.0f;
	private Vector2 position;
	private Vector2 currentPosition;
	private Vector2 targetPosition;
	public Population(LivingQuartersModule home) {
		this.home = home;
		position = new Vector2();
		currentPosition = new Vector2();
		targetPosition = new Vector2();
	}

	public LivingQuartersModule getHome() {
		return home;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
		currentPosition = position;
		targetPosition = position;
	}

	public float getPositionX() {
		return position.x;
	}

	public float getPositionY() {
		return position.y;
	}

	public void setTargetPosition(Vector2 target) {
		this.targetPosition = target;
	}

	public void update(float delta) {
		float totalDistance = position.dst(targetPosition);
		float currentDistance = currentPosition.dst(targetPosition);
		float percent = (1.0f - currentDistance / totalDistance) + speed * delta;

		currentPosition = position.interpolate(targetPosition, percent, Interpolation.linear);

		if (currentPosition.dst(targetPosition) < 0.1f) {
			position = targetPosition;
		}
	}

	public boolean movingToTarget() {
		return currentPosition.dst(targetPosition) >= 0.1f;
	}
}
