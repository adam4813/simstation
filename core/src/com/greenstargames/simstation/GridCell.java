package com.greenstargames.simstation;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.greenstargames.simstation.sprites.Clickable;
import com.greenstargames.simstation.sprites.GridElement;
import com.greenstargames.simstation.sprites.sections.Renderable;

/**
 * Created by Adam on 12/28/2015.
 */
public class GridCell implements Clickable {
	private GridElement hullElement;
	private GridElement moduleElement = null;

	public GridCell(GridElement hullElement) {
		this.hullElement = hullElement;
	}

	public void setHullElement(GridElement hullElement) {
		this.hullElement = hullElement;
	}

	public GridElement getHullElement() {
		return hullElement;
	}

	public boolean canContain(GridElement element) {
		return moduleElement == null && hullElement.canContain(element);
	}

	public void setModuleElement(GridElement element) {
		moduleElement = element;
	}

	public GridElement getModuleElement() {
		return moduleElement;
	}

	public boolean onClick() {
		if (moduleElement == null) {
			hullElement.onClick();
		} else {
			moduleElement.onClick();
		}
		return true;
	}

	public void render(ShapeRenderer shapeRenderer) {
		((Renderable) hullElement).render(shapeRenderer);
		if (moduleElement != null) {
			((Renderable) moduleElement).render(shapeRenderer);
		}
	}
}
