package com.greenstargames.simstation.sprites.sections;

import com.badlogic.gdx.graphics.Color;
import com.greenstargames.simstation.sprites.StationSection;

/**
 * Created by Adam on 12/21/2015.
 */
public class EmptySection extends StationSection {
	public EmptySection(int x, int y) {
		super(new Color(Color.SLATE), x, y, 1, 1, "empty section");
		setContainer(true);
	}

	public EmptySection() {
		super(new Color(Color.SLATE), 0, 0, 0, 0, "empty section");
	}

	@Override
	public boolean tryPlace(StationSection target) {
		if (target == null) {
			return true;
		}
		return false;
	}

	@Override
	public StationSection factory(int x, int y) {
		return new EmptySection(x, y);
	}
}
