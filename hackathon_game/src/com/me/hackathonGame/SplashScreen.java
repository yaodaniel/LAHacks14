package com.me.hackathonGame;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.hackathonGame.HackathonGame;
import com.me.hackathonGame.SpriteTween;
import com.me.hackathonGame.MainMenu;

public class SplashScreen implements Screen{

	private Music splashMusic;
	private Texture splashTexture, loadTexture;
	private Sprite splashSprite;
	private SpriteBatch batch;
	private HackathonGame game;
	private TweenManager manager;
	private OrthographicCamera camera;
	private AssetManager loadManager = new AssetManager();
	private boolean status;
	
	public SplashScreen(HackathonGame gameObject) {
		// TODO Auto-generated constructor stub
		status = false;
		this.game = gameObject;
		camera = new OrthographicCamera();
		splashMusic = Gdx.audio.newMusic(Gdx.files.internal("data/sound/intro.mp3"));
		splashMusic.play();
		Texture.setEnforcePotImages(false);
		for(int x = 3; x < 44 ; x++)
			loadManager.load("data/animation/"+x+".png", Texture.class);
		loadManager.load("data/images/tutorial1.png", Texture.class);
		loadManager.load("data/images/choice.png", Texture.class);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		manager.update(delta);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		splashSprite.draw(batch);
		batch.end();
		
		if(status == true && loadManager.update())
		game.setScreen(new MainMenu(game,loadManager));
	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false, 800, 600);
	}

	@Override
	public void show() {
		splashTexture = new Texture("data/images/splashscreen.jpeg");
		splashTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		//loadTexture = new Texture("data/loading.png");
		//loadTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		splashSprite = new Sprite(splashTexture);
		splashSprite.setColor(1,1,1,0);
		
		batch = new SpriteBatch();
		
		Tween.registerAccessor(Sprite.class, new SpriteTween());
		
		manager = new TweenManager();
		
		TweenCallback cb = new TweenCallback(){

			@Override
			public void onEvent(int type, BaseTween<?> source) {
				tweencompleted();
			}
		};
		
		Tween.to(splashSprite, SpriteTween.ALPHA, 2f).target(1).ease(TweenEquations.easeInQuad).repeatYoyo(1,2.5f).setCallback(cb).setCallbackTriggers(TweenCallback.COMPLETE).start(manager);
	}
	
	private void tweencompleted(){
		status = true;
		Gdx.app.log(HackathonGame.LOG, "Tween Complete");
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		splashTexture.dispose();
		batch.dispose();
		splashMusic.dispose();
	}
}
