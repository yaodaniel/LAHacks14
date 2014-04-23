package com.me.hackathonGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class MainMenu implements Screen {
	
	HackathonGame gameObject;
	private BitmapFont white;
	private TextButton button_start, button_about;
	private TextureAtlas atlas;
	private SpriteBatch batch;
	private Skin skin;
	private Stage stage;
	private boolean checked;
	private OrthographicCamera camera;
	private TextureRegion[] array;
	private AssetManager loadManager;
	private Animation animation;
	private float time;
	private ShapeRenderer renderer;
	private Music splashMusic;
	
	public MainMenu(HackathonGame game_object, AssetManager manager) {
		gameObject = game_object;
		loadManager = manager;
		white = new BitmapFont(Gdx.files.internal("data/gamefont/gamefont.fnt"), false);
		atlas = new TextureAtlas(Gdx.files.internal("data/ui/button.pack"));
		skin = new Skin(atlas);
		batch = new SpriteBatch();
		stage = new Stage(800, 600, true);
		checked = false;
		camera = new OrthographicCamera();
		camera.setToOrtho(false,800,600);
		array = new TextureRegion[41];
		for(int x = 0; x < 41 ; x++)
			array[x] = new TextureRegion(loadManager.get("data/animation/"+(x+3)+".png",Texture.class));
		animation = new Animation(1/10f, array);
		animation.setPlayMode(Animation.LOOP);
		renderer = new ShapeRenderer();
		splashMusic = Gdx.audio.newMusic(Gdx.files.internal("data/sound/menu.mp3"));
			splashMusic.setLooping(true);
			splashMusic.play();
	}

	private void draw(){
		renderer.begin(ShapeType.Line);
        renderer.setColor(Color.DARK_GRAY);
        for(int x = 0; x< 15; x++){
        renderer.line(50, 300+x, 750, 300+x);
        }
        renderer.end();
		renderer.begin(ShapeType.FilledRectangle);
        renderer.filledRect(300, 400, 100, 100);
        renderer.setColor(Color.RED);
        renderer.filledRect(400, 400, 100, 100);
        renderer.end();
	}
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		stage.act(delta);
		batch.begin();
		batch.draw(animation.getKeyFrame(time += delta),0,0);
		batch.end();
		
		batch.begin();
		stage.draw();
		//draw();
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
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
		button_about = new TextButton("About", style);
		button_about.setWidth(Gdx.graphics.getWidth()/1.5f);
		button_about.setHeight(Gdx.graphics.getHeight()/10);
		button_about.setX(Gdx.graphics.getWidth()/2 - button_about.getWidth()/2);
		button_about.setY(Gdx.graphics.getHeight()/3 - button_about.getHeight()/2 - 0.2f*Gdx.graphics.getHeight());
		button_about.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y,
										int pointer, int button) {
				checked = true;
				return true;
			}
			public void touchUp(InputEvent event, float x, float y,
									int pointer, int button) {
				if(checked)
				gameObject.setScreen(new AboutScreen1(gameObject,loadManager));
				checked = false;
			}
			public void touchDragged(InputEvent event, float x, float y, int pointer)
			{
				if(!button_start.isPressed())
				checked = false;
				else
					checked = true;
			}
		});
		button_start = new TextButton("Start!", style);
		button_start.setWidth(Gdx.graphics.getWidth()/1.5f);
		button_start.setHeight(Gdx.graphics.getHeight()/10);
		button_start.setX(Gdx.graphics.getWidth()/2 - button_start.getWidth()/2);
		button_start.setY(Gdx.graphics.getHeight()/2 - button_start.getHeight()/2 - 0.25f*Gdx.graphics.getHeight());
		button_start.addListener(new InputListener() {
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
				if(!button_start.isPressed())
				checked = false;
				else
					checked = true;
			}
		});
		stage.addActor(button_start);
		stage.addActor(button_about);
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
		//gameObject.dispose();
		atlas.dispose();
		batch.dispose();
		skin.dispose();
		white.dispose();
		stage.clear();
		stage.dispose();
		button_start.remove();
		button_about.remove();
		renderer.dispose();
		//splashMusic.dispose();
	}
}
