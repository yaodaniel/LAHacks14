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

public class DifficultyMenu implements Screen{

	HackathonGame gameObject;
	AssetManager loadManager;
	
	private BitmapFont white;
	private TextButton button_easy, button_normal, button_hard, button_back;
	private Texture background;
	private TextureAtlas atlas;
	private SpriteBatch batch;
	private Skin skin;
	private Stage stage;
	private boolean checked;
	private OrthographicCamera camera;
	
	public DifficultyMenu(HackathonGame game,AssetManager loadManager){
		this.gameObject = game;
		camera = new OrthographicCamera();
	    camera.setToOrtho(false, 1920, 1080);
		gameObject = game;
		this.loadManager = loadManager;
		white = new BitmapFont(Gdx.files.internal("data/gamefont/gamefont.fnt"), false);
		atlas = new TextureAtlas(Gdx.files.internal("data/ui/button.pack"));
		skin = new Skin(atlas);
		batch = new SpriteBatch();
		stage = new Stage(1920, 1080, true);
		checked = false;
		background = loadManager.get("data/images/choice.png",Texture.class);
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
		button_easy = new TextButton("Easy", style);
		button_easy.setWidth(Gdx.graphics.getWidth()/1.5f);
		button_easy.setHeight(Gdx.graphics.getHeight()/10);
		button_easy.setX(Gdx.graphics.getWidth()/2 - button_easy.getWidth()/2);
		button_easy.setY(Gdx.graphics.getHeight()/2 + 1.5f*button_easy.getHeight());
		button_easy.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y,
										int pointer, int button) {
				checked = true;
				return true;
			}
			public void touchUp(InputEvent event, float x, float y,
									int pointer, int button) {
				if(checked)
				gameObject.setScreen(new GameScreen(gameObject,loadManager,0));
				checked = false;
			}
			public void touchDragged(InputEvent event, float x, float y, int pointer)
			{
				if(!button_easy.isPressed())
				checked = false;
				else
					checked = true;
			}
		});
		button_normal = new TextButton("Normal", style);
		button_normal.setWidth(Gdx.graphics.getWidth()/1.5f);
		button_normal.setHeight(Gdx.graphics.getHeight()/10);
		button_normal.setX(Gdx.graphics.getWidth()/2 - button_normal.getWidth()/2);
		button_normal.setY(Gdx.graphics.getHeight()/2);
		button_normal.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y,
										int pointer, int button) {
				checked = true;
				return true;
			}
			public void touchUp(InputEvent event, float x, float y,
									int pointer, int button) {
				if(checked)
				gameObject.setScreen(new GameScreen(gameObject,loadManager,1));
				checked = false;
			}
			public void touchDragged(InputEvent event, float x, float y, int pointer)
			{
				if(!button_normal.isPressed())
				checked = false;
				else
					checked = true;
			}
		});
		button_hard = new TextButton("Hard", style);
		button_hard.setWidth(Gdx.graphics.getWidth()/1.5f);
		button_hard.setHeight(Gdx.graphics.getHeight()/10);
		button_hard.setX(Gdx.graphics.getWidth()/2 - button_hard.getWidth()/2);
		button_hard.setY(Gdx.graphics.getHeight()/2 - 3f*button_hard.getHeight()/2);
		button_hard.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y,
										int pointer, int button) {
				checked = true;
				return true;
			}
			public void touchUp(InputEvent event, float x, float y,
									int pointer, int button) {
				if(checked)
				gameObject.setScreen(new GameScreen(gameObject,loadManager,2));
				checked = false;
			}
			public void touchDragged(InputEvent event, float x, float y, int pointer)
			{
				if(!button_normal.isPressed())
				checked = false;
				else
					checked = true;
			}
		});
		button_back = new TextButton("Back", style);
		button_back.setWidth(Gdx.graphics.getWidth()/3);
		button_back.setHeight(Gdx.graphics.getHeight()/20);
		button_back.setX(button_back.getWidth()/5);
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
		stage.addActor(button_easy);
		stage.addActor(button_normal);
		stage.addActor(button_hard);
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
		button_easy.remove();
		button_normal.remove();
		button_hard.remove();
		button_back.remove();
		atlas.dispose();
		batch.dispose();
		skin.dispose();
		stage.dispose();
	}
}
