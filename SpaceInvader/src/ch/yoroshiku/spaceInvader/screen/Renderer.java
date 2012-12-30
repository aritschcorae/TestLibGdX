package ch.yoroshiku.spaceInvader.screen;

import java.util.HashMap;
import java.util.Map;

import ch.yoroshiku.spaceInvader.controller.GameController;
import ch.yoroshiku.spaceInvader.manager.EnemyManager;
import ch.yoroshiku.spaceInvader.manager.PowerUpManager;
import ch.yoroshiku.spaceInvader.manager.ShotManager;
import ch.yoroshiku.spaceInvader.manager.StarManager;
import ch.yoroshiku.spaceInvader.model.Ship;
import ch.yoroshiku.spaceInvader.screen.GameScreen.GamePhase;
import ch.yoroshiku.spaceInvader.util.Helper;
import ch.yoroshiku.spaceInvader.util.Sizes;
import ch.yoroshiku.spaceInvader.util.Textures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

public class Renderer {

	protected final SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private BitmapFont miscTextFont;
	private Map<Float, BitmapFont> genericTextBoxMap;
	private float ppux, ppuy; // pixels per unit
	private float border;
	private BitmapFont.TextBounds pauseBounds, levelFinishedBounds;

	public Renderer(final float ppux, final float border) {
		this.ppux = ppux;
		this.ppuy = ppux;
		this.border = border;
		textboxX = border + (Sizes.DEFAULT_WORLD_WIDTH / 2 - Sizes.TEXTBOX_WIDTH / 2) * ppux;
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		generateTextComponents();
	}

	private void generateTextComponents() {
		
		genericTextBoxMap = new HashMap<Float, BitmapFont>();
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/androidnation.ttf"));
		for (Float fontSize : Helper.TEXTBOX_SIZE) {
			genericTextBoxMap.put(fontSize, generator.generateFont((int) (fontSize * ppuy)));
		}
		generator.dispose();
		generator = new FreeTypeFontGenerator(Gdx.files.internal("font/androidnation.ttf"));
		miscTextFont = generator.generateFont((int) (Sizes.FONT_SIZE_MISC * ppux));
		generator.dispose();

		pauseBounds = new Label("", new LabelStyle(genericTextBoxMap.get(Helper.TEXTBOX_SIZE
				.get(Helper.TEXTBOX_INDEX_PAUSE)), Color.WHITE)).getStyle().font.getBounds(Helper.TEXTBOX_TEXT
				.get(Helper.TEXTBOX_INDEX_PAUSE));
		levelFinishedBounds = new Label("", new LabelStyle(genericTextBoxMap.get(Helper.TEXTBOX_SIZE
				.get(Helper.TEXTBOX_INDEX_LEVEL)), Color.WHITE)).getStyle().font
				.getBounds(Helper.TEXTBOX_TEXT.get(Helper.TEXTBOX_INDEX_TIME));
	}

	public void cleanScreen() {
		Gdx.gl.glClearColor(0f, 0f, 0f, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	}

	private int score = 0;
	public void draw(final Ship ship, final StarManager starManager, final EnemyManager enemyManager,
			final PowerUpManager powerupManager, final ShotManager shotManager, final GamePhase phase, final GameController controller) {
		if (phase == GamePhase.GAMING) {
			shotManager.drawShape(shapeRenderer, ppux, ppuy, border);
			powerupManager.drawShape(shapeRenderer, ppux, ppuy, border);
			
			shapeRenderer.begin(ShapeType.FilledRectangle);
			enemyManager.drawHealthBar(shapeRenderer, ppux, ppuy, border);
			shapeRenderer.end();
		}
		shapeRenderer.setColor(Color.WHITE);
		starManager.drawShape(shapeRenderer, ppux, ppuy, border);
		ship.drawShape(shapeRenderer, ppux, ppuy, border);

		batch.begin();
		ship.drawSprite(batch, ppux, ppuy, border);
		switch (phase) {
		case LEVEL_LOAD://just waiting for refresh
		case GAMING:
			shotManager.drawSprite(batch, ppux, ppuy, border);
			powerupManager.drawSprite(batch, ppux, ppuy, border);
			enemyManager.drawSprite(batch, ppux, ppuy, border);
			break;
		case PAUSE:
			drawPauseTextBox();
			break;
		case LEVEL_SCORE:
			//TODO make counter and stuff and at the end change phase
//			score = 
			GameScreen.updatePhase(GamePhase.LEVEL_WAIT);
		case LEVEL_WAIT:
			drawLevelScore(controller, controller.getTimeBonus());
			break;
		case DEAD:
			drawTextBox(0);
			break;
		case FINISHED:
			break;
		case GAMESTART:
			break;
		default:
			break;
		}
		batch.end();
	}


	public void drawMisc(Ship ship, GameController controller) {
		batch.begin();
		
		batch.draw(Textures.BOMB_TEXTURE, border
				+ (Sizes.DEFAULT_WORLD_WIDTH - Sizes.SHOT_BOMB_WIDTH - Sizes.MISC_BOMB_INFO_OFFSET) * ppux,
				0.1f * ppuy, ppux * Sizes.SHOT_BOMB_WIDTH, ppuy * Sizes.SHOT_BOMB_HEIGHT);
		miscTextFont.draw(batch, "x" + String.valueOf(ship.getBombs()), border
				+ (Sizes.DEFAULT_WORLD_WIDTH - Sizes.MISC_BOMB_INFO_OFFSET) * ppux, Sizes.FONT_SIZE_MISC * ppuy);

		miscTextFont.draw(batch, "Level: " + controller.getLevel(), border + 1 * ppux,
				(Sizes.DEFAULT_WORLD_HEIGHT - Sizes.MISC_POINT_INFO_OFFSET) * ppuy);
		miscTextFont.draw(batch, "Points: " + controller.points, border + (Sizes.DEFAULT_WORLD_WIDTH / 2) * ppux,
				(Sizes.DEFAULT_WORLD_HEIGHT - Sizes.MISC_POINT_INFO_OFFSET) * ppuy);
		batch.end();

		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.WHITE);
		shapeRenderer.line(border, 0, border, Sizes.DEFAULT_WORLD_HEIGHT * ppuy);
		shapeRenderer.line(border + Sizes.DEFAULT_WORLD_WIDTH * ppux, 0, border + Sizes.DEFAULT_WORLD_WIDTH * ppux,
				Sizes.DEFAULT_WORLD_HEIGHT * ppuy);
		shapeRenderer.end();
	}

	private void drawPauseTextBox(){
		drawTextBox(pauseBounds.height);
		genericTextBoxMap.get(Helper.TEXTBOX_SIZE.get(Helper.TEXTBOX_INDEX_PAUSE)).draw(
				batch,
				Helper.TEXTBOX_TEXT.get(Helper.TEXTBOX_INDEX_PAUSE),
				border + (Sizes.DEFAULT_WORLD_WIDTH ) / 2 * ppux - pauseBounds.width / 2,
				(Sizes.DEFAULT_WORLD_HEIGHT - (Sizes.TEXTBOX_OFFSET + Sizes.TEXTBOX_HEIGHT)) * ppuy);
	}
	
	private void drawLevelScore(GameController gamecontroller, int score) {
		drawTextBox(levelFinishedBounds.height * 3.5f);
		genericTextBoxMap.get(Helper.TEXTBOX_SIZE.get(Helper.TEXTBOX_INDEX_LEVEL)).draw(
				batch,
				Helper.TEXTBOX_TEXT.get(Helper.TEXTBOX_INDEX_LEVEL) + gamecontroller.getLevel(), //TODO correct level display
				border + (Sizes.DEFAULT_WORLD_WIDTH ) / 2 * ppux - levelFinishedBounds.width / 2,
				(Sizes.DEFAULT_WORLD_HEIGHT - (Sizes.TEXTBOX_OFFSET + Sizes.TEXTBOX_HEIGHT)) * ppuy);
		
		genericTextBoxMap.get(Helper.TEXTBOX_SIZE.get(Helper.TEXTBOX_INDEX_TIME)).draw(
				batch,
				Helper.TEXTBOX_TEXT.get(Helper.TEXTBOX_INDEX_TIME) + gamecontroller.getTimeNeeded(),
				border + (Sizes.DEFAULT_WORLD_WIDTH ) / 2 * ppux - levelFinishedBounds.width / 2,
				(Sizes.DEFAULT_WORLD_HEIGHT - (Sizes.TEXTBOX_OFFSET + Sizes.TEXTBOX_HEIGHT + Helper.BREAK_SIZE)) * ppuy - levelFinishedBounds.height);

		genericTextBoxMap.get(Helper.TEXTBOX_SIZE.get(Helper.TEXTBOX_INDEX_BONUS)).draw(
				batch,
				Helper.TEXTBOX_TEXT.get(Helper.TEXTBOX_INDEX_BONUS) + score,
				border + (Sizes.DEFAULT_WORLD_WIDTH ) / 2 * ppux - levelFinishedBounds.width / 2,
				(Sizes.DEFAULT_WORLD_HEIGHT - (Sizes.TEXTBOX_OFFSET + Sizes.TEXTBOX_HEIGHT + Helper.BREAK_SIZE)) * ppuy - levelFinishedBounds.height * 2);
		//TODO bounds for tab and others
		genericTextBoxMap.get(Helper.TEXTBOX_SIZE.get(Helper.TEXTBOX_INDEX_TAP)).draw(
				batch,
				Helper.TEXTBOX_TEXT.get(Helper.TEXTBOX_INDEX_TAP),
				border + (Sizes.DEFAULT_WORLD_WIDTH ) / 2 * ppux - levelFinishedBounds.width / 2,
				(Sizes.DEFAULT_WORLD_HEIGHT - (Sizes.TEXTBOX_OFFSET + Sizes.TEXTBOX_HEIGHT + Helper.BREAK_SIZE)) * ppuy - levelFinishedBounds.height * 3);
	}
	
	private float textboxX;
	private void drawTextBox(float height) {
		batch.draw(Textures.TEXTBOX_TOP_TEXTURE, 
				textboxX, 
				(Sizes.DEFAULT_WORLD_HEIGHT - Sizes.TEXTBOX_OFFSET) * ppuy, 
				Sizes.TEXTBOX_WIDTH * ppux, 
				Sizes.TEXTBOX_HEIGHT * ppuy);

		batch.draw(Textures.TEXTBOX_MIDDLE_TEXTURE, 
				textboxX, 
				(Sizes.DEFAULT_WORLD_HEIGHT - (Sizes.TEXTBOX_OFFSET + Sizes.TEXTBOX_HEIGHT * 2)) * ppuy - height, 
				Sizes.TEXTBOX_WIDTH * ppux,	
				height	+ (Sizes.TEXTBOX_HEIGHT * 2) * ppuy);

		batch.draw(Textures.TEXTBOX_BOTTOM_TEXTURE, 
				textboxX, 
				(Sizes.DEFAULT_WORLD_HEIGHT - (Sizes.TEXTBOX_OFFSET + Sizes.TEXTBOX_HEIGHT * 3)) * ppuy - height, 
				Sizes.TEXTBOX_WIDTH * ppux, 
				Sizes.TEXTBOX_HEIGHT * ppuy);
	}

	
	public void cleanBorders() {
		shapeRenderer.begin(ShapeType.FilledRectangle);
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.filledRect(0, 0, border - 1, Sizes.DEFAULT_WORLD_HEIGHT * ppux);
		shapeRenderer.filledRect(border + Sizes.DEFAULT_WORLD_WIDTH * ppux, 0, 
				2 * border + Sizes.DEFAULT_WORLD_WIDTH * ppux, 
				Sizes.DEFAULT_WORLD_HEIGHT * ppux);
		
		shapeRenderer.end();
	}

	public float getPpux() {
		return ppux;
	}

	public void dispose() {
		batch.dispose();
	}


}
