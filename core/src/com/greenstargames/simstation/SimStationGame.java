package com.greenstargames.simstation;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.greenstargames.simstation.screens.PlayScreen;
import com.greenstargames.simstation.screens.TitleScreen;

public class SimStationGame extends Game {
	private SpriteBatch batch;
	private TitleScreen titleScreen;
	private PlayScreen playScreen;
	static public final int WIDTH = 832;
	static public final int HEIGHT = 480;

	@Override
	public void create() {
		Gdx.app.getGraphics().setDisplayMode(WIDTH, HEIGHT, false);
		batch = new SpriteBatch();
		titleScreen = new TitleScreen(this);
		setScreen(titleScreen);
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public void onNewGame() {
		playScreen = new PlayScreen(this);
		setScreen(playScreen);
	}

	@Override
	public void dispose() {
		super.dispose();
		titleScreen.dispose();
		playScreen.dispose();
		batch.dispose();
	}

	public void onQuit() {
		Gdx.app.exit();
	}
}
