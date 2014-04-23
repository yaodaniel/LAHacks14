package com.me.hackathonGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class WinScreen implements Screen{

	private BitmapFont white;
	private TextureAtlas atlas;
	private Skin skin;
	private TextButton button_play_again;
	private Label heading;
	private Stage stage;
	private SpriteBatch batch;
	//private Sound explosion;
	private boolean checked;
	HackathonGame gameObject;
	AssetManager manager;
	
	public WinScreen(HackathonGame game,AssetManager manager){
		gameObject = game;
		this.manager = manager;
		//explosion = Gdx.audio.newSound(Gdx.files.internal("data/raw/damage.mp3"));
		white = new BitmapFont(Gdx.files.internal("data/gamefont/gamefont.fnt"), false);
		atlas = new TextureAtlas("data/ui/button.pack");
		skin = new Skin(atlas);
		batch = new SpriteBatch();
		checked = false;
	}
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		batch.begin();
		stage.draw();
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		if(stage == null)
			stage = new Stage(width, height, true);
		stage.clear();
		
		Gdx.input.setInputProcessor(stage);
		TextButtonStyle style = new TextButtonStyle();
		style.up = skin.getDrawable("button.up");
		style.down = skin.getDrawable("button.down");
		style.over = skin.getDrawable("button.down");
		style.font = white;
		
		button_play_again = new TextButton("Replay?", style);
		button_play_again.setWidth(400);
		button_play_again.setHeight(100);
		button_play_again.setX(Gdx.graphics.getWidth()/2 - button_play_again.getWidth()/2);
		button_play_again.setY(Gdx.graphics.getHeight()/2 - button_play_again.getWidth()/2);
		
		button_play_again.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y,
										int pointer, int button) {
				checked = true;
				return true;
			}
			public void touchUp(InputEvent event, float x, float y,
									int pointer, int button) {
				if(checked)
				{
					gameObject.setScreen(new DifficultyMenu(gameObject, manager));
				}
				checked = false;
			}
			public void touchDragged(InputEvent event, float x, float y, int pointer)
			{
				if(!button_play_again.isPressed())
				checked = false;
				else
					checked = true;
			}
		});
		
		LabelStyle ls = new LabelStyle(white, Color.WHITE);
		heading = new Label("You won the game!\n Congradulation!", ls);
		heading.setX(0);
		heading.setY(Gdx.graphics.getHeight()/2 + 100);
		heading.setWidth(width);
		heading.setAlignment(Align.center);
		heading.setFontScale(1.3f);
		stage.addActor(heading);
		stage.addActor(button_play_again);
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
		atlas.dispose();
		heading.remove();
		skin.dispose();
		stage.dispose();
		batch.dispose();
		button_play_again.remove();
		//explosion.dispose();
	}

}
