package com.me.hackathonGame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "hackathon_game";
		cfg.useGL20 = true;
		cfg.width = 720;
		cfg.height = 1280;
		
		new LwjglApplication(new HackathonGame(), cfg);
	}
}
