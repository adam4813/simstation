package com.greenstargames.simstation.sprites;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Adam on 12/21/2015.
 */
public abstract class StationSection {
	private Color color = null;
	private int x = 0;
	private int y = 0;
	private String name = "";
	private int width = 0;
	private int height = 0;
	private boolean container = false;

	public abstract StationSection factory(int x, int y);

	public StationSection(Color color, int x, int y, int width, int height, String name) {
		this.color = color;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getWidth() {
		return width;
	}

	public int getX() {
		return x;
	}

	public int getHeight() {
		return height;
	}

	public int getY() {
		return y;
	}

	public Color getColor() {
		return color;
	}

	public abstract boolean tryPlace(StationSection target);

	public boolean isContainer() {
		return container;
	}

	public void setContainer(boolean container) {
		this.container = container;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
}
