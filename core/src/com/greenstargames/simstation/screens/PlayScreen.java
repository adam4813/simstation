package com.greenstargames.simstation.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.greenstargames.simstation.SimStationGame;
import com.greenstargames.simstation.sprites.sections.EmptySection;
import com.greenstargames.simstation.sprites.StationSection;
import com.greenstargames.simstation.sprites.sections.LivingSection;

import java.util.ArrayList;

/**
 * Created by Adam on 12/19/2015.
 */
public class PlayScreen implements Screen {
	public static final int GRID_SIZE = 32;
	private final SimStationGame game;
	private final OrthographicCamera camera;
	private final Viewport viewport;
	private final ShapeRenderer shapeRenderer;
	private ArrayList<StationSection> sections;
	private static final StationSection[] availableSections = {
			new EmptySection(),
			new LivingSection()
	};
	private int selectedSectionIndex;

	public PlayScreen(SimStationGame simStationGame) {
		game = simStationGame;
		camera = new OrthographicCamera(SimStationGame.WIDTH, SimStationGame.HEIGHT);
		camera.translate(SimStationGame.WIDTH / 2, SimStationGame.HEIGHT / 2);
		camera.update();

		viewport = new StretchViewport(SimStationGame.WIDTH, SimStationGame.HEIGHT, camera);
		shapeRenderer = new ShapeRenderer();
		sections = new ArrayList<StationSection>();
	}

	public void processInput(float delta) {
		if (Gdx.input.justTouched()) {
			// input.getX and input.getY are in world coordinates so unproject them to screen coordinates.
			Vector3 worldCoordinates = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(worldCoordinates);
			int x = (int) (worldCoordinates.x / GRID_SIZE);
			int y = (int) (worldCoordinates.y / GRID_SIZE);

			StationSection highlightedSection = null;

			// Check if a section already exists at the input coordinates.
			for (StationSection section : sections) {
				if (x >= section.getX() && x < (section.getX() + section.getWidth())) {
					if (y >= section.getY() && y < (section.getY() + section.getHeight())) {
						Gdx.app.log("PlayScreen", "Clicked on " + section.getName() + " at (" + Integer.toString(x) + ", " + Integer.toString(y) + ")");
						highlightedSection = section;
						break;
					}
				}
			}
			StationSection section = availableSections[selectedSectionIndex].factory(x, y);
			if (highlightedSection == null) {
				Gdx.app.log("PlayScreen", "No section exists at (" + Integer.toString(x) + ", " + Integer.toString(y) + ")");
			}
			if (section.tryPlace(highlightedSection)) {
				sections.add(section);
			} else {
				Gdx.app.log("PlayScreen", "Can't place section there.");
			}
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
			shapeRenderer.rect(section.getX() * GRID_SIZE, section.getY() * GRID_SIZE, section.getWidth() * GRID_SIZE, section.getHeight() * GRID_SIZE);
		}
		// input.getX and input.getY are in world coordinates so unproject them to screen coordinates.
		Vector3 worldCoords = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(worldCoords);
		shapeRenderer.setColor(availableSections[selectedSectionIndex].getColor());
		shapeRenderer.rect((int) (worldCoords.x / GRID_SIZE) * GRID_SIZE, (int) (worldCoords.y / GRID_SIZE) * GRID_SIZE, availableSections[selectedSectionIndex].getWidth() * GRID_SIZE, availableSections[selectedSectionIndex].getHeight() * GRID_SIZE);
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
