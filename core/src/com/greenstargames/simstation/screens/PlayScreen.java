package com.greenstargames.simstation.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.greenstargames.simstation.Grid;
import com.greenstargames.simstation.SimStationGame;
import com.greenstargames.simstation.sprites.modules.BaseModule;
import com.greenstargames.simstation.sprites.modules.HullSection;
import com.greenstargames.simstation.sprites.modules.LivingQuarters;
import com.greenstargames.simstation.sprites.modules.PowerGenerator;
import com.greenstargames.simstation.sprites.modules.WaterPurifier;


/**
 * Created by Adam on 12/19/2015.
 */
public class PlayScreen implements Screen {
	public static final int GRID_SIZE = 32;
	private static final BaseModule[] placeableModules = {
			new HullSection(),
			new LivingQuarters(),
			new PowerGenerator(),
			new WaterPurifier()
	};
	private final Stage stage;
	private final SimStationGame game;
	private final OrthographicCamera camera;
	private final Viewport viewport;
	private final ShapeRenderer shapeRenderer;
	private final Grid grid;
	private int selectedPlaceableModule = 0;

	public PlayScreen(SimStationGame simStationGame) {
		game = simStationGame;
		camera = new OrthographicCamera(SimStationGame.WIDTH, SimStationGame.HEIGHT);
		camera.translate(SimStationGame.WIDTH / 2, SimStationGame.HEIGHT / 2);
		camera.update();

		viewport = new FitViewport(SimStationGame.WIDTH, SimStationGame.HEIGHT, camera);
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);

		grid = new Grid(SimStationGame.WIDTH / GRID_SIZE, SimStationGame.HEIGHT / GRID_SIZE - 1);

		stage = new Stage();
		stage.setViewport(viewport);
		Gdx.input.setInputProcessor(stage);// Make the stage consumePower events
		buildToolbox();
	}

	private void buildToolbox() {
		Skin skin = new Skin();
		Pixmap pixmap = new Pixmap(GRID_SIZE, GRID_SIZE, Pixmap.Format.RGB888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		pixmap.setColor(Color.BLACK);
		pixmap.drawRectangle(0, 0, GRID_SIZE, GRID_SIZE);
		skin.add("background", new Texture(pixmap));

		int startingX = (SimStationGame.WIDTH / 2);
		startingX -= (placeableModules.length * GRID_SIZE / 2);
		for (int i = 0; i < placeableModules.length; ++i) {
			BaseModule module = placeableModules[i];
			ImageButton placeableModuleButton = new ImageButton(skin.newDrawable("background", module.getColor())); // Use the initialized skin
			final int finalI = i;
			placeableModuleButton.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					selectedPlaceableModule = finalI;
				}
			});
			placeableModuleButton.setPosition(startingX + i * GRID_SIZE, SimStationGame.HEIGHT - GRID_SIZE);
			stage.addActor(placeableModuleButton);
		}
	}

	private void processInput(float delta) {
		if (Gdx.input.justTouched()) {
			// input.getX and input.getY are in world coordinates so unproject them to screen coordinates.
			Vector3 worldCoordinates = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			viewport.unproject(worldCoordinates);
			int x = (int) (worldCoordinates.x / GRID_SIZE);
			int y = (int) (worldCoordinates.y / GRID_SIZE);

			if (selectedPlaceableModule >= placeableModules.length) {
				grid.onClick(x, y);
			} else {
				grid.tryPlace(placeableModules[selectedPlaceableModule].factory(x, y), x, y);
			}
		}

//		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)) {
//			selectedPlaceableModule = placeableModules.length;
//		}
//		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
//			selectedPlaceableModule = 0;
//		}
//		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
//			selectedPlaceableModule = 1;
//		}
//		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
//			selectedPlaceableModule = 2;
//		}
//		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
//			selectedPlaceableModule = 3;
//		}
//		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_5)) {
//			selectedPlaceableModule = 4;
//		}
//		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_6)) {
//			selectedPlaceableModule = 5;
//		}
//		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_7)) {
//			selectedPlaceableModule = 6;
//		}
//		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_8)) {
//			selectedPlaceableModule = 7;
//		}
//		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_9)) {
//			selectedPlaceableModule = 8;
//		}
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		grid.update(delta);
		processInput(delta);
		stage.act(delta);
		Gdx.gl.glClearColor(0.5f, 1, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.getBatch().setProjectionMatrix(camera.combined);
		game.getBatch().begin();

		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		grid.render(game.getBatch(), shapeRenderer);
		stage.draw();
		// input.getX and input.getY are in world coordinates so unproject them to screen coordinates.
		Vector3 worldCoords = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		viewport.unproject(worldCoords);
		if (selectedPlaceableModule < placeableModules.length) {
			Actor selectedActor = stage.getActors().get(selectedPlaceableModule);
			shapeRenderer.setColor(Color.RED);
			shapeRenderer.rectLine(selectedActor.getX(), selectedActor.getY(),
					selectedActor.getX() + selectedActor.getWidth(), selectedActor.getY(), 3.0f);
			shapeRenderer.rectLine(selectedActor.getX(), selectedActor.getY(),
					selectedActor.getX(), selectedActor.getY() + selectedActor.getHeight(), 3.0f);
			shapeRenderer.rectLine(selectedActor.getWidth() + selectedActor.getX(), selectedActor.getY(),
					selectedActor.getWidth() + selectedActor.getX(), selectedActor.getY() + selectedActor.getHeight(), 3.0f);
			shapeRenderer.rectLine(selectedActor.getX(), selectedActor.getY() + selectedActor.getHeight(),
					selectedActor.getX() + selectedActor.getWidth(), selectedActor.getY() + selectedActor.getHeight(), 3.0f);
			Vector3 toolArea = new Vector3(0, GRID_SIZE, 0);
			viewport.project(toolArea);
			if (Gdx.input.getY() > toolArea.y) {
				placeableModules[selectedPlaceableModule].setX((int) (worldCoords.x / GRID_SIZE));
				placeableModules[selectedPlaceableModule].setY((int) (worldCoords.y / GRID_SIZE));
				placeableModules[selectedPlaceableModule].render(game.getBatch(), shapeRenderer);
			}
		}
		shapeRenderer.end();
		game.getBatch().end();
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
