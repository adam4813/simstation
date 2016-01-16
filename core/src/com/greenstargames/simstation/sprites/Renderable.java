package com.greenstargames.simstation.sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.greenstargames.simstation.screens.PlayScreen;

/**
 * Created by Adam on 12/27/2015.
 */
public abstract class Renderable {
	public static final int GRID_SIZE = PlayScreen.GRID_SIZE;
	protected int x = 0;
	protected int y = 0;
	protected int width = 0;
	protected int height = 0;
	protected Color color = null;

	public Renderable(Color color, int x, int y, int width, int height) {
		this.color = color;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public abstract void render(ShapeRenderer renderer);

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public Color getColor() {
		return color;
	}
}
