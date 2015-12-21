package com.greenstargames.simstation.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.greenstargames.simstation.SimStationGame;
import com.greenstargames.simstation.sprites.sections.EmtptySection;
import com.greenstargames.simstation.sprites.StationSection;
import com.greenstargames.simstation.sprites.sections.LivingSection;

import java.util.ArrayList;

/**
 * Created by Adam on 12/19/2015.
 */
public class PlayScreen implements Screen {
	private final SimStationGame game;
	private final OrthographicCamera camera;
	private final Viewport viewport;
	private final ShapeRenderer shapeRenderer;
	private ArrayList<StationSection> sections;
	private StationSection[] availableSections = {
			new EmtptySection(0, 0),
			new LivingSection(0, 0)
	};
	private int selectedSectionIndex;

	public PlayScreen(SimStationGame simStationGame) {
		game = simStationGame;
		camera = new OrthographicCamera(SimStationGame.WIDTH, SimStationGame.HEIGHT);
		camera.translate(SimStationGame.WIDTH / 2, SimStationGame.HEIGHT / 2);
		camera.update();

		viewport = new FitViewport(SimStationGame.WIDTH, SimStationGame.HEIGHT, camera);
		shapeRenderer = new ShapeRenderer();
		sections = new ArrayList<StationSection>();
	}

	public void processInput(float delta) {
		if (Gdx.input.justTouched()) {
			int x = Gdx.input.getX() / 32;
			int y = (SimStationGame.HEIGHT - Gdx.input.getY()) / 32;

			for (StationSection section : sections) {
				if (x >= section.getX() && x < (section.getX() + section.getWidth())) {
					if (y >= section.getY() && y < (section.getY() + section.getHeight())) {
						Gdx.app.log("PlayScreen", "Clicked on " + section.getName()+ " at (" + Integer.toString(x) + ", " + Integer.toString(y) + ")");
						return;
					}
				}
			}
			Gdx.app.log("PlayScreen", "No section exists at (" + Integer.toString(x) + ", " + Integer.toString(y) + ") adding " + availableSections[selectedSectionIndex].getName());
			StationSection section = new StationSection(availableSections[selectedSectionIndex], x, y);
			sections.add(section);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)) {
			selectedSectionIndex = 0;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
			selectedSectionIndex = 1;
		}
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		processInput(delta);
		Gdx.gl.glClearColor(0.5f, 1, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.getBatch().setProjectionMatrix(camera.combined);

		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		for (StationSection section : sections) {
			shapeRenderer.setColor(section.getColor());
			shapeRenderer.rect(section.getX() * 32, section.getY() * 32, section.getWidth() * 32, section.getHeight() * 32);
		}
		shapeRenderer.end();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		shapeRenderer.dispose();
	}
}
