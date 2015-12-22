package com.greenstargames.simstation.sprites.sections;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.greenstargames.simstation.sprites.StationSection;

/**
 * Created by Adam on 12/21/2015.
 */
public class LivingSection extends StationSection {
	public LivingSection(int x, int y) {
		super(new Color(Color.CORAL), x, y, 1, 1, "living quarters");
	}

	public LivingSection() {
		super(new Color(Color.CORAL), 0, 0, 0, 0, "living quarters");
	}

	@Override
	public StationSection factory(int x, int y) {
		return new LivingSection(x, y);
	}

	@Override
	public boolean tryPlace(StationSection target) {
		if (target != null && target.isContainer()) {
			return true;
		}
		Gdx.app.log("LivingSection", "Living quarters can only be placed on an empty section.");
		return false;
	}
}
