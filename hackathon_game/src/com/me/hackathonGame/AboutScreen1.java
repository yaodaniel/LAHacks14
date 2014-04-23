package com.me.hackathonGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class AboutScreen1 implements Screen {

	HackathonGame gameObject;
	AssetManager loadManager;
	
	private BitmapFont white;
	private TextButton button_back, button_next;
	private Texture background;
	private TextureAtlas atlas;
	private SpriteBatch batch;
	private Skin skin;
	private Stage stage;
	private boolean checked;
	private OrthographicCamera camera;
	
	public AboutScreen1(HackathonGame game,AssetManager manager){
	    camera = new OrthographicCamera();
	    camera.setToOrtho(false, 1920, 1080);
		gameObject = game;
		loadManager = manager;
		white = new BitmapFont(Gdx.files.internal("data/gamefont/gamefont.fnt"), false);
		atlas = new TextureAtlas(Gdx.files.internal("data/ui/button.pack"));
		skin = new Skin(atlas);
		batch = new SpriteBatch();
		stage = new Stage(1920, 1080, true);
		checked = false;
		background = loadManager.get("data/images/tutorial1.png",Texture.class);
		background.setFilter(TextureFilter.Linear, TextureFilter.Linear);
	}
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		stage.act(delta);
		batch.begin();
		batch.draw(background, 0, 0,1920,1080);
		batch.end();
		
		batch.begin();
		stage.draw();
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		if(stage == null)
			stage = new Stage(width, height, true);
		if(stage.getWidth()!=width || stage.getHeight()!=height)
		{
			stage.dispose();
			stage = new Stage(width, height, true);
		}
		stage.clear();
		Gdx.input.setInputProcessor(stage);
		TextButtonStyle style = new TextButtonStyle();
		style.up = skin.getDrawable("button.up");
		style.down = skin.getDrawable("button.down");
		style.over = skin.getDrawable("button.down");
		style.font = white;
		button_back = new TextButton("back", style);
		button_back.setWidth(Gdx.graphics.getWidth()/3);
		button_back.setHeight(Gdx.graphics.getHeight()/20);
		button_back.setX(Gdx.graphics.getWidth()/5*3);
		button_back.setY(Gdx.graphics.getHeight()/3 - button_back.getHeight()/2 - 0.2f*Gdx.graphics.getHeight());
		button_back.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y,
										int pointer, int button) {
				checked = true;
				return true;
			}
			public void touchUp(InputEvent event, float x, float y,
									int pointer, int button) {
				if(checked)
				gameObject.setScreen(new MainMenu(gameObject,loadManager));
				checked = false;
			}
			public void touchDragged(InputEvent event, float x, float y, int pointer)
			{
				if(!button_back.isPressed())
				checked = false;
				else
					checked = true;
			}
		});
		button_next = new TextButton("next", style);
		button_next.setWidth(Gdx.graphics.getWidth()/3);
		button_next.setHeight(Gdx.graphics.getHeight()/20);
		button_next.setX(Gdx.graphics.getWidth()/5*3);
		button_next.setY(Gdx.graphics.getHeight()/3 - button_next.getHeight() - 0.25f*Gdx.graphics.getHeight());
		button_next.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y,
										int pointer, int button) {
				checked = true;
				return true;
			}
			public void touchUp(InputEvent event, float x, float y,
									int pointer, int button) {
				if(checked)
				gameObject.setScreen(new DifficultyMenu(gameObject,loadManager));
				checked = false;
			}
			public void touchDragged(InputEvent event, float x, float y, int pointer)
			{
				if(!button_next.isPressed())
				checked = false;
				else
					checked = true;
			}
		});
		stage.addActor(button_next);
		stage.addActor(button_back);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
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
		white.dispose();
		button_back.remove();
		button_next.remove();
		atlas.dispose();
		batch.dispose();
		skin.dispose();
		stage.dispose();
	}
}
