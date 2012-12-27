package ch.yoroshiku.spaceInvader.screen;

import ch.yoroshiku.spaceInvader.manager.EnemyManager;
import ch.yoroshiku.spaceInvader.manager.PowerUpManager;
import ch.yoroshiku.spaceInvader.manager.ShotManager;
import ch.yoroshiku.spaceInvader.manager.StarManager;
import ch.yoroshiku.spaceInvader.model.Ship;
import ch.yoroshiku.spaceInvader.screen.GameScreen.GamePhase;
import ch.yoroshiku.spaceInvader.util.Sizes;
import ch.yoroshiku.spaceInvader.util.Textures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class Renderer {

	protected final SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private float ppux, ppuy; // pixels per unit
	private float border;

	public Renderer(final float ppux, final float border) {
		this.ppux = ppux;
		this.ppuy = ppux;
		this.border = border;
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
	}

	public void cleanScreen() {
		Gdx.gl.glClearColor(0f, 0f, 0f, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	}

	public void draw(final Ship ship, final StarManager starManager, final EnemyManager enemyManager,
			final PowerUpManager powerupManager, final ShotManager shotManager, final GamePhase phase) {
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
		case GAMING:
			powerupManager.drawSprite(batch, ppux, ppuy, border);
			enemyManager.drawSprite(batch, ppux, ppuy, border);
			break;
		case PAUSE:
			drawTextBox(0);
			break;
		case HIGHSCORE:
		case LEVEL_WAIT:
			drawTextBox(10);
			GameScreen.updatePhase(GamePhase.LEVEL_WAIT);
			break;
		case DEAD:
			drawTextBox(0);
			break;
		case FINISHED:
			break;
		case GAMESTART:
			break;
		case LEVEL_LOAD:
			break;
		default:
			break;
		}
		batch.end();
	}

	public void drawMisc() {
		batch.begin();
		batch.draw(Textures.BOMB_TEXTURE, border + (Sizes.DEFAULT_WORLD_WIDTH - Sizes.SHOT_BOMB_WIDTH - 3) * ppux, 0);
		batch.end();

		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.WHITE);
		shapeRenderer.line(border, 0, border, Sizes.DEFAULT_WORLD_HEIGHT * ppuy);
		shapeRenderer.line(border + Sizes.DEFAULT_WORLD_WIDTH * ppux, 0, border + Sizes.DEFAULT_WORLD_WIDTH * ppux,
				Sizes.DEFAULT_WORLD_HEIGHT * ppuy);
		shapeRenderer.end();
	}

	private void drawTextBox(float height) {
		batch.draw(Textures.TEXTBOX_TOP_TEXTURE, border + (Sizes.DEFAULT_WORLD_WIDTH / 2 - Sizes.TEXTBOX_WIDTH / 2)
				* ppux, (Sizes.DEFAULT_WORLD_HEIGHT - 4) * ppuy, Sizes.TEXTBOX_WIDTH * ppux, Sizes.TEXTBOX_HEIGHT
				* ppuy);

		batch.draw(Textures.TEXTBOX_MIDDLE_TEXTURE, border + (Sizes.DEFAULT_WORLD_WIDTH / 2 - Sizes.TEXTBOX_WIDTH / 2)
				* ppux, (Sizes.DEFAULT_WORLD_HEIGHT - (4 + Sizes.TEXTBOX_HEIGHT)- height) * ppuy, Sizes.TEXTBOX_WIDTH * ppux,
				(height + Sizes.TEXTBOX_HEIGHT) * ppuy);

		batch.draw(Textures.TEXTBOX_BOTTOM_TEXTURE, border + (Sizes.DEFAULT_WORLD_WIDTH / 2 - Sizes.TEXTBOX_WIDTH / 2)
				* ppux, (Sizes.DEFAULT_WORLD_HEIGHT - (4 + Sizes.TEXTBOX_HEIGHT * 2) - height) * ppuy, Sizes.TEXTBOX_WIDTH
				* ppux, Sizes.TEXTBOX_HEIGHT * ppuy);
	}

}
