package com.me.hackathonGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.me.hackathonGame.HackathonGame;;

public class GameScreen implements Screen{

	private HackathonGame game;
	private int gameDifficulty; // 0 = easy, 1 = medium, 2 = hard, 3 = VERY hard
	private AssetManager manager;
	//private Texture gameBoard;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private SolutionGrid solnGrid;
	private PlayGrid playGrid;
	Square[][] solnSquares,playSquares;
	
	//button
	private TextButton button_up, button_down, button_left, button_right, button_reset;
	private BitmapFont white;
	private TextureAtlas atlas, atlasReset;
	private Skin skin,skinReset;
	private Stage stage;
	private boolean checked_up;
	private boolean checked_down;
	private boolean checked_right;
	private boolean checked_left;
	private boolean checked_reset;
	private boolean gridChanged;
	private TextButtonStyle styleUp, styleDown, styleLeft, styleRight, reset;
	public GameScreen(HackathonGame game,AssetManager manager,int difficulty){
		
	    camera = new OrthographicCamera();
	    camera.setToOrtho(false, 1080, 1920);
		this.game = game;
		this.gameDifficulty = difficulty;
		this.manager = manager;
		int len = 0;
		switch (gameDifficulty) {
			case 0:
				len = 3;
				break;
			case 1:
				len = 4;
				break;
			case 2:
				len = 5;
				break;
			default:
				len = 3;
		}
		//gameBoard = manager.get("data/levels/gridTest.png",Texture.class);
		//gameBoard.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		//gameBoardSprite = new Sprite(gameBoard);
		batch = new SpriteBatch();
		
		//button
		checked_up = false;
		checked_down = false;
		checked_right = false;
		checked_left = false;
		checked_reset = false;
		white = new BitmapFont(Gdx.files.internal("data/gamefont/gamefont.fnt"), false);
		atlas = new TextureAtlas(Gdx.files.internal("data/ui/arrows.pack"));
		atlasReset = new TextureAtlas(Gdx.files.internal("data/ui/button.pack"),false);
		skin = new Skin(atlas);
		skinReset = new Skin(atlasReset);
		stage = new Stage(1920, 1080, true);
		//.... other difficulties
		solnGrid = new SolutionGrid(len);
		GameColor[] colors = solnGrid.getCube().getStartColors(); //Colors the cube starts off with
		Cube cube = new Cube(colors);
		int cx = solnGrid.getStartCubeX();
		int cy = solnGrid.getStartCubeY();
		playGrid = new PlayGrid(len,cube,cx,cy);
		gridChanged = false;
		solnSquares = solnGrid.getAllSquares();
	    playSquares = playGrid.getAllSquares();
	}
	
	public void drawCurrentSquare(int diff,int x,int y){
		ShapeRenderer shape = new ShapeRenderer();
		float width = (float) Gdx.graphics.getWidth();
		float height = (float)Gdx.graphics.getHeight();
		
		float start_x = (width - (720/1080f)*width)/2;
		float start_y = (1/25f*height) + 1/8f*(height);
		
		//matrix 
		float distx = (720/1080f)*width/diff;
		float disty = (720/1920f)*height/diff;
		
		shape.begin(ShapeType.FilledRectangle);
		float startx = start_x+(x*distx)+(0.25f*distx);
		float starty = start_y+(y*disty)+(0.25f*disty);
		shape.setColor(Color.DARK_GRAY);
		shape.filledRect(startx,starty,distx/2,disty/2);
		shape.end();
		shape.dispose();
	}
	
	public void drawGrid(int diff, Square [][] solution, Square [][] s)
	{
		ShapeRenderer shape = new ShapeRenderer();
		float width = (float) Gdx.graphics.getWidth();
		float height = (float)Gdx.graphics.getHeight();
		
		float start_x = (width - (720/1080f)*width)/2;
		float start_y = (1/25f*height) + 1/8f*(height);
		
		//matrix 
		float distx = (720/1080f)*width/diff;
		float disty = (720/1920f)*height/diff;
		
		float y_pos = 0;
		shape.begin(ShapeType.FilledRectangle);
		for(int x = 0; x < diff; x++){
			for(int y = 0; y < diff; y++){
				float x_pos = start_x + x*distx;
				y_pos = start_y + y*disty;
				shape.setColor(s[x][y].getGDXColor());
				shape.filledRect(x_pos, y_pos, distx, disty);
			}
		}
		start_y = y_pos + 1/25f *width + disty;
		for(int x =0; x < diff; x++){
			for(int y =0; y < diff; y++){
				float x_pos = start_x + x*distx;
				y_pos = start_y + y*disty;
				shape.setColor(solution[x][y].getGDXColor());
				shape.filledRect(x_pos,  y_pos,  distx,  disty);
			}
		}
		shape.end();
		//shape.setProjectionMatrix(camera.combined);
				shape.begin(ShapeType.Line);
				shape.setColor(Color.BLACK);
				//draw the margins
				shape.line(1/25f*width, 1/25f*height, 1/25f*width, 24/25f*height);
				shape.line(1/25f*width, 1/25f*height, 24/25f*width, 1/25f*height);
				shape.line(24/25f*width, 1/25f*height, 24/25f*width, 24/25f*height);
				shape.line(24/25f*width, 24/25f*height, 1/25f*width, 24/25f*height);
				int tmp = diff;
				float topMax = start_y+tmp*disty;
				while(tmp >= 0){
				shape.line(start_x, start_y+tmp*disty, width-start_x, start_y+tmp*disty);
				shape.line(start_x+tmp*distx, start_y, start_x+tmp*distx, topMax);
				tmp--;
				}
				tmp = diff;
				start_y = (1/25f*height) + 1/8f*(height);
				topMax = start_y+tmp*disty;
				while(tmp >=0){
				shape.line(start_x, start_y+tmp*disty, width-start_x, start_y+tmp*disty);
				shape.line(start_x+tmp*distx, start_y, start_x+tmp*distx, topMax);
				tmp--;
				}
				shape.end();
			shape.dispose();
	}
	
	/*private void updateGrid(){
		Square[][] squares = playGrid.getAllSquares();
	    int len = playGrid.getLen();
	    for (int y = 0; y < len; y++) {
	    	for (int x = 0; x < len; x++) {
	    		Square sq = squares[y][x];
	    		GameColor c = sq.getColor();
	    		switch (c) { //WHITE, RED, BLUE, GREEN
	    			case LIGHT_GRAY:
	    				Gdx.app.log(HackathonGame.LOG, "WHITE grid updated");
	    				break;
	    			case RED:
	    				Gdx.app.log(HackathonGame.LOG, "RED grid updated");
	    				break;
	    			case BLUE:
	    				Gdx.app.log(HackathonGame.LOG, "BLUE grid updated");
	    				break;
	    			case GREEN:
	    				Gdx.app.log(HackathonGame.LOG, "GREEN grid updated");
	    				break;
	    		}
	    	}
	    }
    	gridChanged = false;
	}*/
	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    camera.update();
	    batch.setProjectionMatrix(camera.combined);
	    
	    stage.act(delta);
	    /*if(gridChanged)
	    	updateGrid();*/
		//batch.begin();
		//batch.draw(gameBoard, 0, 0);
		//batch.draw(gameBoard,Gdx.graphics.getWidth()/12.5f,3*Gdx.graphics.getHeight()/4,1820,380);
		//batch.draw(gameBoard,Gdx.graphics.getWidth()/12.5f,Gdx.graphics.getHeight()/4,1820,380);
		//gameBoardSprite.draw(batch);
		//batch.end();
	    drawGrid(solnGrid.getLen(), solnSquares, playSquares);
	    drawCurrentSquare(solnGrid.getLen(),playGrid.getCubeX(),playGrid.getCubeY());
		batch.begin();
		stage.draw();
		batch.end();
	    if(solnGrid.equals(playGrid)){
	    	Gdx.app.log(HackathonGame.LOG, "Solved");
	    	game.setScreen(new WinScreen(game,manager));
	    	Gdx.app.log(HackathonGame.LOG, "WinScreen Called");
	    }
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	    Gdx.app.log(HackathonGame.LOG, Gdx.graphics.getWidth()+"x"+Gdx.graphics.getHeight());
		if(stage == null)
			stage = new Stage(width, height, true);
		if(stage.getWidth()!=width || stage.getHeight()!=height)
		{
			stage.dispose();
			stage = new Stage(width, height, true);
		}
		stage.clear();
		Gdx.input.setInputProcessor(stage);
	    //buttons
	    
	    styleUp = new TextButtonStyle();
		styleUp.up = skin.getDrawable("arrowUpOn");
		styleUp.down = skin.getDrawable("arrowUpOff");
		styleUp.over = skin.getDrawable("arrowUpOff");
		styleUp.font = white;
		
		//up
		button_up = new TextButton("", styleUp);
		button_up.setWidth(Gdx.graphics.getWidth()/10);
		button_up.setHeight(Gdx.graphics.getHeight()/10);
		button_up.setX(Gdx.graphics.getWidth()/2 - button_up.getWidth());
		button_up.setY(button_up.getHeight()/2);
		button_up.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y,
										int pointer, int button) {
				checked_up = true;
				return true;
			}
			public void touchUp(InputEvent event, float x, float y,
									int pointer, int button) {
				if(checked_up)
					//implement stuff
					if(playGrid.moveCube(Direction.NORTH))
						gridChanged = true;
				checked_up = false;
			}
			public void touchDragged(InputEvent event, float x, float y, int pointer)
			{
				if(!button_up.isPressed())
				checked_up = false;
				else
					checked_up = true;
			}
		});
		
		styleDown = new TextButtonStyle();
		styleDown.font = white;
		button_down = new TextButton("", styleDown);
		styleDown.up = skin.getDrawable("arrowDownOn");
		styleDown.down = skin.getDrawable("arrowDownOff");
		styleDown.over = skin.getDrawable("arrowDownOff");
		button_down.setWidth(Gdx.graphics.getWidth()/10);
		button_down.setHeight(Gdx.graphics.getHeight()/10);
		button_down.setX(Gdx.graphics.getWidth()/2 + button_down.getWidth()/2);
		button_down.setY(button_down.getHeight()/2);
		button_down.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				checked_down = true;
				return true;
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button){
				if(checked_down)
					//implement stuff
					if(playGrid.moveCube(Direction.SOUTH))
						gridChanged = true;
				checked_down = false;
			}
			public void touchDragged(InputEvent event, float x, float y, int pointer)
			{
				if(!button_down.isPressed())
				checked_down = false;
				else
					checked_down = true;
			}
		});
		
		styleRight = new TextButtonStyle();
		styleRight.font = white;
		styleRight.up = skin.getDrawable("arrowRightOn");
		styleRight.down = skin.getDrawable("arrowRightOff");
		styleRight.over = skin.getDrawable("arrowRightOff");
		button_right = new TextButton("", styleRight);
		button_right.setWidth(Gdx.graphics.getWidth()/10);
		button_right.setHeight(Gdx.graphics.getHeight()/10);
		button_right.setX(Gdx.graphics.getWidth()/2 + 5*button_right.getWidth()/2);
		button_right.setY(button_right.getHeight()/2);
		button_right.addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				checked_right = true;
				return true;
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button){
				if(checked_right)
					//implement stuff
					if(playGrid.moveCube(Direction.EAST))
						gridChanged = true;
				checked_right = false;
			}
			public void touchDragged(InputEvent event, float x, float y, int pointer)
			{
				if(!button_right.isPressed())
				checked_right = false;
				else
					checked_right = true;
			}
		});
		
		styleLeft = new TextButtonStyle();
		styleLeft.font = white;
		styleLeft.up = skin.getDrawable("arrowLeftOn");
		styleLeft.down = skin.getDrawable("arrowLeftOff");
		styleLeft.over = skin.getDrawable("arrowLeftOff");
		button_left = new TextButton("", styleLeft);
		button_left.setWidth(Gdx.graphics.getWidth()/10);
		button_left.setHeight(Gdx.graphics.getHeight()/10);
		button_left.setX(Gdx.graphics.getWidth()/2 - button_left.getWidth()*3);
		button_left.setY(button_left.getHeight()/2);
		button_left.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				checked_left = true;
				return true;
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				if(checked_left)
					if(playGrid.moveCube(Direction.WEST))
						gridChanged = true;
				checked_left = false;
			}
			public void touchDragged(InputEvent event, float x, float y, int pointer)
			{
				if(!button_left.isPressed())
				checked_left = false;
				else
					checked_left = true;
			}
		});
		
		reset = new TextButtonStyle();
		
		reset.font = white;
		reset.up = skinReset.getDrawable("button.up");
		reset.down = skinReset.getDrawable("button.down");
		reset.over = skinReset.getDrawable("button.down");
		button_reset = new TextButton("Reset", reset);
		button_reset.setWidth(Gdx.graphics.getWidth()/5);
		button_reset.setHeight(Gdx.graphics.getHeight()/24);
		button_reset.setX(0);
		button_reset.setY(0);
		button_reset.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				checked_reset = true;
				return true;
			}
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				if(checked_reset)
					playGrid.reset();
					drawGrid(solnGrid.getLen(), solnSquares, playSquares);
						gridChanged = true;
				checked_reset = false;
			}
			public void touchDragged(InputEvent event, float x, float y, int pointer)
			{
				if(!button_reset.isPressed())
				checked_reset = false;
				else
					checked_reset = true;
			}
		});
		stage.addActor(button_left);
		stage.addActor(button_right);
		stage.addActor(button_down);
		stage.addActor(button_up);
		stage.addActor(button_reset);
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
		//gameBoard.dispose();
		batch.dispose();
		button_up.remove();
		button_down.remove();
		button_left.remove();
		button_right.remove();
		white.dispose();
		atlas.dispose();
		skin.dispose();
		stage.dispose();
	}
}
