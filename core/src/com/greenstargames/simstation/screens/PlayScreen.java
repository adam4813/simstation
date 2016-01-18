package com.greenstargames.simstation.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.greenstargames.simstation.Grid;
import com.greenstargames.simstation.SimStationGame;
import com.greenstargames.simstation.sprites.modules.BaseModule;
import com.greenstargames.simstation.sprites.modules.HullSection;
import com.greenstargames.simstation.sprites.modules.LivingQuarters;
import com.greenstargames.simstation.sprites.modules.PowerGenerator;
import com.greenstargames.simstation.sprites.modules.ScienceLab;
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
			new WaterPurifier(),
			new ScienceLab()
	};
	public static Sprite NO_WORKER_SPRITE;
	public static Sprite NO_POWER_SPRITE;
	public static Sprite NO_WATER_SPRITE;
	private final Stage stage;
	private final SimStationGame game;
	private final OrthographicCamera camera;
	private final Viewport viewport;
	private final ShapeRenderer shapeRenderer;
	private final Grid grid;
	private int selectedPlaceableModule = placeableModules.length;
	private boolean hoverToolArea = false;
	private int dragAccumulatorX = 0;
	private int dragAccumulatorY = 0;
	private boolean mapDragging = false;
	private Sprite bkgd;

	public PlayScreen(SimStationGame simStationGame) {
		game = simStationGame;
		camera = new OrthographicCamera(SimStationGame.WIDTH, SimStationGame.HEIGHT);
		camera.translate(SimStationGame.WIDTH / 2, SimStationGame.HEIGHT / 2);
		camera.update();

		viewport = new FitViewport(SimStationGame.WIDTH, SimStationGame.HEIGHT, camera);
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);

		grid = new Grid();

		stage = new Stage();
		stage.getViewport().update(SimStationGame.WIDTH, SimStationGame.HEIGHT, true);

		buildToolbox();
		NO_WORKER_SPRITE = new Sprite(new Texture(Gdx.files.internal("no_worker.png")));
		NO_WORKER_SPRITE.setColor(1.0f, 0.0f, 1.0f, 1.0f);
		NO_WORKER_SPRITE.setSize(16.0f, 16.0f);
		NO_POWER_SPRITE = new Sprite(new Texture(Gdx.files.internal("no_power.png")));
		NO_POWER_SPRITE.setColor(1.0f, 0.0f, 1.0f, 1.0f);
		NO_POWER_SPRITE.setSize(16.0f, 16.0f);
		NO_WATER_SPRITE = new Sprite(new Texture(Gdx.files.internal("no_water.png")));
		NO_WATER_SPRITE.setColor(1.0f, 0.0f, 1.0f, 1.0f);
		NO_WATER_SPRITE.setSize(16.0f, 16.0f);
		bkgd = new Sprite(new Texture(Gdx.files.internal("bkgd.png")));
		bkgd.getTexture().setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
		bkgd.setPosition(-8192.0f, -8192.0f);
		bkgd.setSize(16536.0f, 16536.0f);
		bkgd.setU(16536.0f);
		bkgd.setV(16536.0f);
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
			Sprite moduleSprite = module.getSprite();
			ImageButton placeableModuleButton = null;
			if (moduleSprite != null) {
				Sprite buttonSprite = new Sprite(module.getSprite().getTexture());
				buttonSprite.setSize(GRID_SIZE, GRID_SIZE);
				SpriteDrawable drawable = new SpriteDrawable(buttonSprite);
				placeableModuleButton = new ImageButton(drawable); // Use the initialized skin
			} else {
				placeableModuleButton = new ImageButton(skin.newDrawable("background", module.getColor())); // Use the initialized skin
			}

			final int finalI = i;
			placeableModuleButton.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					selectedPlaceableModule = finalI;
					hoverToolArea = false;
				}
			});
			placeableModuleButton.addListener(new ClickListener() {
				@Override
				public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
					super.enter(event, x, y, pointer, fromActor);
					hoverToolArea = true;
				}

				@Override
				public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
					super.exit(event, x, y, pointer, toActor);
					hoverToolArea = false;
				}
			});
			placeableModuleButton.setPosition(startingX + i * GRID_SIZE, SimStationGame.HEIGHT - GRID_SIZE);
			stage.addActor(placeableModuleButton);
		}
		Sprite homeButtonSprite = new Sprite(new Texture(Gdx.files.internal("home.png")));
		homeButtonSprite.setSize(GRID_SIZE, GRID_SIZE);
		ImageButton homeButton = new ImageButton(new SpriteDrawable(homeButtonSprite));

		homeButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				camera.position.set(SimStationGame.WIDTH / 2, SimStationGame.HEIGHT / 2, 0.0f);
			}
		});
		homeButton.addListener(new ClickListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				super.enter(event, x, y, pointer, fromActor);
				hoverToolArea = true;
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				super.exit(event, x, y, pointer, toActor);
				hoverToolArea = false;
			}
		});
		homeButton.setPosition(startingX + placeableModules.length * GRID_SIZE, SimStationGame.HEIGHT - GRID_SIZE);
		stage.addActor(homeButton);

		Sprite menuButtonSprite = new Sprite(new Texture(Gdx.files.internal("menu.png")));
		menuButtonSprite.setSize(GRID_SIZE, GRID_SIZE);
		ImageButton menuButton = new ImageButton(new SpriteDrawable(menuButtonSprite));

		menuButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.onPause();
			}
		});
		menuButton.addListener(new ClickListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				super.enter(event, x, y, pointer, fromActor);
				hoverToolArea = true;
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				super.exit(event, x, y, pointer, toActor);
				hoverToolArea = false;
			}
		});
		menuButton.setPosition(startingX - GRID_SIZE, SimStationGame.HEIGHT - GRID_SIZE);
		stage.addActor(menuButton);

		Sprite saveButtonSprite = new Sprite(new Texture(Gdx.files.internal("save.png")));
		saveButtonSprite.setSize(GRID_SIZE, GRID_SIZE);
		ImageButton saveButton = new ImageButton(new SpriteDrawable(saveButtonSprite));

		saveButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				grid.save();
			}
		});
		saveButton.addListener(new ClickListener() {
			@Override
			public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
				super.enter(event, x, y, pointer, fromActor);
				hoverToolArea = true;
			}

			@Override
			public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
				super.exit(event, x, y, pointer, toActor);
				hoverToolArea = false;
			}
		});
		saveButton.setPosition(startingX - GRID_SIZE * 2, SimStationGame.HEIGHT - GRID_SIZE);
		stage.addActor(saveButton);
	}

	public void loadSave(String filename) {
		grid.load(filename);
	}

	private void processInput(float delta) {
		if (Gdx.input.isTouched()) {
			dragAccumulatorX += Gdx.input.getDeltaX();
			dragAccumulatorY += Gdx.input.getDeltaY();
			int x = 0;
			int y = 0;
			if (dragAccumulatorX > GRID_SIZE) {
				x = -GRID_SIZE;
				dragAccumulatorX -= GRID_SIZE;
			} else if (dragAccumulatorX < -GRID_SIZE) {
				x = GRID_SIZE;
				dragAccumulatorX += GRID_SIZE;
			}
			if (dragAccumulatorY > GRID_SIZE) {
				y = GRID_SIZE;
				dragAccumulatorY -= GRID_SIZE;
			} else if (dragAccumulatorY < -GRID_SIZE) {
				y = -GRID_SIZE;
				dragAccumulatorY += GRID_SIZE;
			}
			if (x != 0 || y != 0) {
				mapDragging = true;
				camera.position.add(x, y, 0);
			}
		} else {
			mapDragging = false;
			dragAccumulatorX = 0;
			dragAccumulatorY = 0;
		}

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
		processInput(delta);
		camera.update();
		grid.update(delta);
		stage.act(delta);
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		SpriteBatch batch = game.getBatch();
		batch.enableBlending();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		bkgd.draw(batch);
		grid.render(batch);
		// input.getX and input.getY are in world coordinates so unproject them to screen coordinates.
		Vector3 worldCoords = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(worldCoords);
		if (selectedPlaceableModule < placeableModules.length && !mapDragging) {
			if (!hoverToolArea) {
				if (!grid.canPlace(placeableModules[selectedPlaceableModule],
						(int) (worldCoords.x / GRID_SIZE), (int) (worldCoords.y / GRID_SIZE))) {
					placeableModules[selectedPlaceableModule].getSprite().setColor(1.0f, 1.0f, 1.0f, 0.1f);
				} else {
					placeableModules[selectedPlaceableModule].getSprite().setColor(1.0f, 1.0f, 1.0f, 1.0f);
				}
				placeableModules[selectedPlaceableModule].setX((int) (worldCoords.x / GRID_SIZE));
				placeableModules[selectedPlaceableModule].setY((int) (worldCoords.y / GRID_SIZE));
				placeableModules[selectedPlaceableModule].render(batch);

			}
		}
		batch.end();

		stage.draw();

		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		if (selectedPlaceableModule < placeableModules.length) {
			Actor selectedActor = stage.getActors().get(selectedPlaceableModule);
			float actorX = selectedActor.getX() + (camera.position.x - SimStationGame.WIDTH / 2);
			float actorY = selectedActor.getY() + (camera.position.y - SimStationGame.HEIGHT / 2);
			shapeRenderer.setColor(Color.RED);
			shapeRenderer.rectLine(actorX, actorY,
					actorX + selectedActor.getWidth(), actorY, 1.0f);
			shapeRenderer.rectLine(actorX, actorY,
					actorX, actorY + selectedActor.getHeight(), 1.0f);
			shapeRenderer.rectLine(actorX + selectedActor.getWidth(), actorY,
					actorX + selectedActor.getWidth(), actorY + selectedActor.getHeight(), 1.0f);
			shapeRenderer.rectLine(actorX, actorY + selectedActor.getHeight(),
					actorX + selectedActor.getWidth(), actorY + selectedActor.getHeight(), 1.0f);
		}
		shapeRenderer.end();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
		stage.getViewport().update(width, height, true);
		Gdx.input.setInputProcessor(stage);
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
		stage.dispose();
	}

	public void newGame() {
		grid.reset(26, 14);
	}
}
