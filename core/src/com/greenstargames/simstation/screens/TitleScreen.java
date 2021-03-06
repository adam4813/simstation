package com.greenstargames.simstation.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.greenstargames.simstation.SimStationGame;

/**
 * Created by Adam on 12/19/2015.
 */
public class TitleScreen implements Screen {
	private final SimStationGame game;
	private final Stage stage;
	private final Viewport viewport;
	private Skin skin;
	private boolean paused;

	public TitleScreen(SimStationGame simStationGame) {
		game = simStationGame;
		OrthographicCamera camera = new OrthographicCamera(SimStationGame.WIDTH, SimStationGame.HEIGHT);
		camera.translate(SimStationGame.WIDTH / 2, SimStationGame.HEIGHT / 2);
		camera.update();

		viewport = new FitViewport(SimStationGame.WIDTH, SimStationGame.HEIGHT, camera);

		stage = new Stage();
		stage.setViewport(viewport);

		createBasicSkin();
	}

	private void createBasicSkin() {
		//Create a font
		BitmapFont font = new BitmapFont();
		skin = new Skin();
		skin.add("default", font);

		//Create a texture
		Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 10, Pixmap.Format.RGB888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("background", new Texture(pixmap));

		//Create a button style
		TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("background", Color.GRAY);
		textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
		textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
		textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.getBatch().begin();
		game.getBatch().end();
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		stage.clear();
		if (paused) {
			TextButton newGameButton = new TextButton("Resume", skin); // Use the initialized skin
			newGameButton.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					game.onResume();
				}
			});
			newGameButton.setPosition(viewport.getWorldWidth() / 2 - viewport.getWorldWidth() / 8, viewport.getWorldHeight() / 4 * 3 - viewport.getWorldHeight() / 8);
			stage.addActor(newGameButton);
		} else {
			TextButton newGameButton = new TextButton("New game", skin); // Use the initialized skin
			newGameButton.addListener(new ChangeListener() {
				@Override
				public void changed(ChangeEvent event, Actor actor) {
					game.onNewGame();
				}
			});
			newGameButton.setPosition(viewport.getWorldWidth() / 2 - viewport.getWorldWidth() / 8, viewport.getWorldHeight() / 4 * 3 - viewport.getWorldHeight() / 8);
			stage.addActor(newGameButton);
		}

		TextButton loadGameButton = new TextButton("Load game", skin); // Use the initialized skin
		loadGameButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.onShowLoadScreen();
			}
		});
		loadGameButton.setPosition(viewport.getWorldWidth() / 2 - viewport.getWorldWidth() / 8, viewport.getWorldHeight() / 2);
		stage.addActor(loadGameButton);

		TextButton quitGameButton = new TextButton("Quit", skin); // Use the initialized skin
		quitGameButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.onQuit();
			}
		});
		quitGameButton.setPosition(viewport.getWorldWidth() / 2 - viewport.getWorldWidth() / 8, viewport.getWorldHeight() / 4 + viewport.getWorldHeight() / 8);
		stage.addActor(quitGameButton);
		Gdx.input.setInputProcessor(stage);// Make the stage consume events
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
		stage.dispose();
		skin.dispose();
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}
}
