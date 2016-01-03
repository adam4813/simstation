package com.greenstargames.simstation.sprites;

/**
 * Created by Adam on 12/28/2015.
 */
public interface GridElement extends Clickable {
	boolean isHull();

	boolean canContain(GridElement element);

	String getName();

	GridElement factory(int x, int y);

	void onPlaced();
}
