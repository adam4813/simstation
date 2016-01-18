package com.greenstargames.simstation.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.greenstargames.simstation.screens.PlayScreen;

/**
 * Created by Adam on 12/27/2015.
 */
public abstract class Renderable {
	protected static final int GRID_SIZE = PlayScreen.GRID_SIZE;
	protected int x = 0;
	protected int y = 0;
	protected int width = 0;
	protected int height = 0;
	protected Color color = null;
	private Sprite sprite;


	public Renderable(Color color, int x, int y, int width, int height) {
		this.color = color;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void loadTexture(String spriteName) {
		Texture texture = new Texture(Gdx.files.internal(spriteName));
		sprite = new Sprite(texture);
		sprite.setSize(width * GRID_SIZE, height * GRID_SIZE);
	}

	public void render(SpriteBatch batch, ShapeRenderer renderer) {
		if (sprite != null) {
			sprite.setPosition(x * GRID_SIZE, y * GRID_SIZE);
			sprite.draw(batch);
		}
	}


	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Sprite getSprite() {
		return sprite;
	}
}
