package com.greenstargames.simstation.sprites;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Adam on 12/21/2015.
 */
public class StationSection {
	private Color color = null;
	private int x = 0;
	private int y = 0;
	private String name = "";
	private int width = 0;
	private int height = 0;

	public StationSection(Color color, int x, int y, int width, int height, String name) {
		this.color = color;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.name = name;
	}

	public StationSection(StationSection other, int x, int y) {
		this.color = other.color;
		this.x = x;
		this.y = y;
		this.width = other.width;
		this.height = other.height;
		this.name = other.name;
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
}
