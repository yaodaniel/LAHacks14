package com.me.hackathonGame;

import com.badlogic.gdx.Game;

public class HackathonGame extends Game{
	
	public static final String VERSION = "0.0.1";
	public static final String LOG = "Hackathon_game";
	
	@Override
	public void create() {
		this.setScreen(new SplashScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {		
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
