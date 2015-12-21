package com.greenstargames.simstation.sprites.sections;

import com.badlogic.gdx.graphics.Color;
import com.greenstargames.simstation.sprites.StationSection;

/**
 * Created by Adam on 12/21/2015.
 */
public class EmtptySection extends StationSection {
	public EmtptySection(int x, int y) {
		super(new Color(Color.SLATE), x, y, 1, 1, "empty section");
	}
}
