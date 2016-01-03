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
import com.greenstargames.simstation.Grid;
import com.greenstargames.simstation.SimStationGame;
import com.greenstargames.simstation.sprites.modules.LivingQuartersModule;
import com.greenstargames.simstation.sprites.modules.StationModule;
import com.greenstargames.simstation.sprites.sections.HullSection;


/**
 * Created by Adam on 12/19/2015.
 */
public class PlayScreen implements Screen {
	public static final int GRID_SIZE = 32;
	private Grid grid;

	private static final StationModule[] placeableModules = {
			new HullSection(),
			new LivingQuartersModule()
	};
	private int selectedPlaceableModule = 0;

	private final SimStationGame game;
	private final OrthographicCamera camera;
	private final Viewport viewport;
	private final ShapeRenderer shapeRenderer;

	public PlayScreen(SimStationGame simStationGame) {
		game = simStationGame;
		camera = new OrthographicCamera(SimStationGame.WIDTH, SimStationGame.HEIGHT);
		camera.translate(SimStationGame.WIDTH / 2, SimStationGame.HEIGHT / 2);
		camera.update();

		viewport = new StretchViewport(SimStationGame.WIDTH, SimStationGame.HEIGHT, camera);
		shapeRenderer = new ShapeRenderer();

		grid = new Grid(15, 15);
	}

	public void processInput(float delta) {
		if (Gdx.input.justTouched()) {
			// input.getX and input.getY are in world coordinates so unproject them to screen coordinates.
			Vector3 worldCoordinates = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(worldCoordinates);
			int x = (int) (worldCoordinates.x / GRID_SIZE);
			int y = (int) (worldCoordinates.y / GRID_SIZE);

			if (selectedPlaceableModule >= placeableModules.length) {
					grid.onClick(x, y);
			} else {
				grid.tryPlace(placeableModules[selectedPlaceableModule].factory(x, y), x, y);
			}
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)) {
			selectedPlaceableModule = placeableModules.length;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
			selectedPlaceableModule = 0;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
			selectedPlaceableModule = 1;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
			selectedPlaceableModule = 2;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
			selectedPlaceableModule = 3;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) {
			selectedPlaceableModule = 4;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)) {
			selectedPlaceableModule = 5;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_7)) {
			selectedPlaceableModule = 6;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_8)) {
			selectedPlaceableModule = 7;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_9)) {
			selectedPlaceableModule = 8;
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
		grid.render(shapeRenderer);
		// input.getX and input.getY are in world coordinates so unproject them to screen coordinates.
		Vector3 worldCoords = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(worldCoords);
		if (selectedPlaceableModule < placeableModules.length) {
			placeableModules[selectedPlaceableModule].setX((int) (worldCoords.x / GRID_SIZE));
			placeableModules[selectedPlaceableModule].setY((int) (worldCoords.y / GRID_SIZE));
			placeableModules[selectedPlaceableModule].render(shapeRenderer);
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
