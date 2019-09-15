package com.greenstargames.simstation;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.greenstargames.simstation.screens.LoadScreen;
import com.greenstargames.simstation.screens.PlayScreen;
import com.greenstargames.simstation.screens.TitleScreen;

public class SimStationGame extends Game {
	static public final int WIDTH = 832;
	static public final int HEIGHT = 480;
	private SpriteBatch batch;
	private TitleScreen titleScreen;
	private PlayScreen playScreen;
	private LoadScreen loadScreen;

	@Override
	public void create() {
		Gdx.app.getGraphics().setWindowedMode(WIDTH, HEIGHT);
		batch = new SpriteBatch();
		titleScreen = new TitleScreen(this);
		playScreen = new PlayScreen(this);
		loadScreen = new LoadScreen(this);
		setScreen(titleScreen);
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public void onNewGame() {
		playScreen.newGame();
		setScreen(playScreen);
	}

	public void onResume() {
		setScreen(playScreen);
	}

	public void onPause() {
		titleScreen.setPaused(true);
		setScreen(titleScreen);
	}

	@Override
	public void dispose() {
		super.dispose();
		titleScreen.dispose();
		if (playScreen != null) {
			playScreen.dispose();
		}
		batch.dispose();
	}

	public void onQuit() {
		Gdx.app.exit();
	}

	public void onShowLoadScreen() {
		setScreen(loadScreen);
	}

	public void onLoadSave(String filename) {
		playScreen.loadSave(filename);
		setScreen(playScreen);
	}
}
