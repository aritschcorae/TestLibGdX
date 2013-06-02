package ch.yoroshiku.spaceInvader.screen;

import ch.yoroshiku.spaceInvader.SpaceInvader;
import ch.yoroshiku.spaceInvader.controller.MenuController;
import ch.yoroshiku.spaceInvader.util.Sizes;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

abstract class UiScreen implements Screen {

	protected final MenuController uiController;
	protected float ppuX; // pixels per unit on the X axis
	protected float ppuY; // pixels per unit on the Y axis
	protected Table wrapTable;
	protected Table buttonTable;
	protected final SpaceInvader game;
	protected final ShapeRenderer shapeRenderer;
	protected final SpriteBatch batch;
	protected final Stage stage;

	public UiScreen(SpaceInvader game) {
		super();
		this.game = game;
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		stage = new Stage(0, 0, true);
		// InputProcessor
		uiController = new MenuController(game, stage);
		initComponents();
	}

	protected void initComponents() {
		wrapTable = new Table();
		wrapTable.setFillParent(true);
		buttonTable = new Table();
	}

	public void resize(int width, int height) {
		stage.setViewport(width, height, true);
		Gdx.input.setInputProcessor(uiController);
		ppuX = width / Sizes.DEFAULT_WORLD_WIDTH;
		ppuY = height / Sizes.DEFAULT_WORLD_HEIGHT;
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // update and draw the stage actors
        stage.act( delta );
        stage.draw();
	}

	protected void setupGui() {
		stage.clear();
		wrapTable.clear();
		buttonTable.clear();
		stage.addActor(wrapTable);
		stage.addActor(wrapTable);
	}

	protected void addToButtonRow(Button button) {
		buttonTable.add(button)
				.width((int) (20 * ppuY))
				.height((int) (20 * ppuY))
				.pad((int) (20 * ppuY));
	}

}
