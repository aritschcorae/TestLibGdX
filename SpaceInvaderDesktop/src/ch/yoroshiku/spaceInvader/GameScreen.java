package ch.yoroshiku.spaceInvader;

import java.io.IOException;
import java.util.Random;


import ch.yoroshiku.spaceInvader.SpaceInvader;
import ch.yoroshiku.spaceInvader.controller.GameController;
import ch.yoroshiku.spaceInvader.controller.Gamepad;
import ch.yoroshiku.spaceInvader.controller.GameController.GameState;
import ch.yoroshiku.spaceInvader.model.Explosion;
import ch.yoroshiku.spaceInvader.model.PowerUp;
import ch.yoroshiku.spaceInvader.model.enemies.AbstractEnemy;
import ch.yoroshiku.spaceInvader.model.enemieset.EnemyGroup;
import ch.yoroshiku.spaceInvader.model.shot.Shot;
import ch.yoroshiku.spaceInvader.model.shot.ShotBitmap;
import ch.yoroshiku.spaceInvader.util.Sizes;
import ch.yoroshiku.spaceInvader.util.Textures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class GameScreen extends AbstractScreen 
{
	public static float border, buttonHeight;
	private float ppuX; // pixels per unit on the X axis
	private float ppuY; // pixels per unit on the Y axis
	private float[] starsX, starsY;
	private Gamepad shipController;
	private GameController gameController; 
	private Color c;
	private Random starGenerator = new Random();
	private BitmapFont font;
	
	public GameScreen(SpaceInvader game) {
		super(game);
		gameController = new GameController();
		font = Textures.TEMP_FONT;
		starsX = new float[30];
		starsY = new float[30];
		for(int i = 0; i < 30; i ++)
		{
			starsX[i] = starGenerator.nextInt((int)Sizes.DEFAULT_WORLD_WIDTH);
			starsY[i] = starGenerator.nextInt((int)Sizes.DEFAULT_WORLD_HEIGHT);
		}
	}

	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		shipController.resize(width, height);
		ppuY = (float) height / Sizes.DEFAULT_WORLD_HEIGHT;
		ppuX = ppuY;
		border = (width - (height / 8 * 7)) / 2;
		buttonHeight = border * 0.8f;
		try
		{
			gameController.resize();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void show() 
	{
		shipController = new Gamepad(gameController, gameController.ship);
		Gdx.input.setInputProcessor(shipController);
		c = batch.getColor();
	}

	@Override
	public void render(float delta)
	{
		super.render(delta);
		if(gameController.getState().equals(GameState.PLAY))
		{
			shipController.update(delta);
			gameController.process(delta);
			handleStars(delta);
		}
		drawCanvas();
	}
	
	private void handleStars(float delta)
	{
		for(int i = 0; i < 30; i ++)
		{
			starsY[i] -= Sizes.DEFAULT_WORLD_HEIGHT * delta / 2;
			if(starsY[i] < 0)
			{
				starsX[i] = starGenerator.nextInt((int)Sizes.DEFAULT_WORLD_WIDTH);
				starsY[i] = Sizes.DEFAULT_WORLD_HEIGHT;
			}
		}
	}

    
	private void drawCanvas()
	{
		if(gameController.getState().equals(GameState.PLAY))
		{
			batch.begin();
			drawEnemies();
		    drawBombs();
		    drawButtons();
		    drawPowerUps();
			drawStats();
			gameController.ship.draw(batch, ppuX, ppuY, border);
			batch.end();
			
			drawHealth();
			drawShots();
			drawBorder();
			drawStars();
			drawCircle();
		}
		else if(gameController.getState().equals(GameState.PAUSE))
		{
			batch.begin();
			drawTextBox();
			drawPauseText();
			drawStats();
			batch.end();
			drawBorder();
		}
	}


	private void drawEnemies()
	{
		for(EnemyGroup enemyGroup : gameController.enemyManager.getEnemies().values())
		{
			batch.setColor(enemyGroup.getColor());
			for(AbstractEnemy enemy : enemyGroup)
			{
				if(enemy.isVisible())
				{
					enemy.draw(batch, ppuX, ppuY, border);
				}
			}
			batch.setColor(c);
		}
	}


	private void drawHealth()
	{
		shapeRenderer.begin(ShapeType.FilledRectangle);
		for(EnemyGroup enemyGroup : gameController.enemyManager.getEnemies().values())
		{
			for(AbstractEnemy enemy : enemyGroup)
			{
				if(enemyGroup.isVisible() && enemy.isVisible() && enemy.isShowHealthBar())
				{
					enemy.drawHealthBar(shapeRenderer, ppuX, ppuY, border);
				}
			}
		}
		shapeRenderer.end();
	}
	
	private void drawBombs()
	{
		for(Shot shot : gameController.shotsBombs)
		{
			final ShotBitmap bomb = (ShotBitmap) shot;
			batch.draw(bomb.getBitmap(), border + bomb.x * ppuX, bomb.y * ppuY);
		}
		batch.draw(Textures.BOMB_TEXTURE, 
				border + (Sizes.DEFAULT_WORLD_WIDTH - Sizes.SHOT_BOMB_WIDTH - 3) * ppuX, 0);
	}
	
	private void drawButtons()
	{
		batch.draw(Textures.BUTTON_TEXTURE, 0, Sizes.DEFAULT_WORLD_HEIGHT * ppuY - buttonHeight, border, buttonHeight);
	}
	
	private void drawPowerUps()
	{
		for(final PowerUp powerUp : gameController.powerUps)
		{
			powerUp.draw(batch, ppuX, ppuY, border);
		}
	}

	private void drawStats()
	{
		font.setColor(Color.WHITE);
		final String pointAmount = "Points: " + gameController.points;
		font.draw(batch, pointAmount , border + 1 * ppuX, Sizes.DEFAULT_WORLD_HEIGHT * ppuY);
		final String lvl = "lvl " + gameController.getEnemySetCreator().getPlainLvl();
		font.draw(batch, lvl , border + Sizes.DEFAULT_WORLD_WIDTH * ppuX - font.getSpaceWidth() * pointAmount.length() - ppuX, Sizes.DEFAULT_WORLD_HEIGHT * ppuY); 
		
		font.draw(batch, ": " +gameController.ship.getBombs() , border + (Sizes.DEFAULT_WORLD_WIDTH - 3)* ppuX, font.getCapHeight());
	}

	private void drawShots()
	{
		shapeRenderer.begin(ShapeType.FilledRectangle);
		shapeRenderer.setColor(Color.RED);
		for(Shot shot : gameController.allShots)
		{
			shapeRenderer.filledRect(border + shot.x * ppuX, shot.y * ppuY, shot.width * ppuX, shot.height * ppuY);
		}
		shapeRenderer.setColor(Color.YELLOW);
		for(Shot shot : gameController.enemyShots)
		{
			shapeRenderer.filledRect(border + shot.x * ppuX, shot.y * ppuY, shot.width * ppuX, shot.height * ppuY);
		}
		shapeRenderer.end();
	}
	
	private void drawBorder()
	{
		shapeRenderer.begin(ShapeType.Line);
		shapeRenderer.setColor(Color.WHITE);
		shapeRenderer.line(border, 0, border, Sizes.DEFAULT_WORLD_HEIGHT * ppuY);
		shapeRenderer.line(border + Sizes.DEFAULT_WORLD_WIDTH * ppuX, 0, border + Sizes.DEFAULT_WORLD_WIDTH * ppuX, Sizes.DEFAULT_WORLD_HEIGHT * ppuY);
		shapeRenderer.end();
	}
	
	private void drawStars()
	{
		shapeRenderer.begin(ShapeType.Point);
		shapeRenderer.setColor(Color.WHITE);
		for(int i = 0; i < 30; i ++)
		{
			shapeRenderer.point(border + starsX[i] * ppuX, starsY[i] * ppuY, 0);
		}
		shapeRenderer.end();
	}
	
	private void drawCircle()
	{
		shapeRenderer.begin(ShapeType.Circle);
		drawExplosions();
		drawShield();
		shapeRenderer.end();
	}
	
	private void drawExplosions()
	{
		for(Explosion explosion : gameController.explosions)
			explosion.draw(shapeRenderer, ppuX, ppuY, border);
	}
	
	private void drawShield()
	{
		gameController.ship.drawShield(shapeRenderer, ppuX, ppuY, border);
	}

	private void drawTextBox()
	{
		batch.draw(Textures.TEXTBOX_TOP_TEXTURE, 
				border + (Sizes.DEFAULT_WORLD_WIDTH / 2 - Sizes.TEXTBOX_WIDTH / 2) * ppuX,
				(Sizes.DEFAULT_WORLD_HEIGHT - 4) * ppuY,
				Sizes.TEXTBOX_WIDTH * ppuX, Sizes.TEXTBOX_HEIGHT * ppuY);
		
		batch.draw(Textures.TEXTBOX_MIDDLE_TEXTURE, 
				border + (Sizes.DEFAULT_WORLD_WIDTH / 2 - Sizes.TEXTBOX_WIDTH / 2) * ppuX,
				(Sizes.DEFAULT_WORLD_HEIGHT - (4 + Sizes.TEXTBOX_HEIGHT)) * ppuY,
				Sizes.TEXTBOX_WIDTH * ppuX, Sizes.TEXTBOX_HEIGHT * ppuY);

		batch.draw(Textures.TEXTBOX_BOTTOM_TEXTURE, 
				border + (Sizes.DEFAULT_WORLD_WIDTH / 2 - Sizes.TEXTBOX_WIDTH / 2) * ppuX,
				(Sizes.DEFAULT_WORLD_HEIGHT - (4 + Sizes.TEXTBOX_HEIGHT * 2)) * ppuY,
				Sizes.TEXTBOX_WIDTH * ppuX, Sizes.TEXTBOX_HEIGHT * ppuY);
	}

	private final String pauseText = "Pause";
	private void drawPauseText()
	{
		font.setColor(Color.RED);
		font.draw(batch, pauseText , border, 50);
		font.setScale(2);
		font.draw(batch, pauseText , border + 30 * ppuX, 50);
		font.setScale(0.9f);
	}


	@Override
	public void hide() {}
	@Override
	public void pause() {}
	@Override
	public void resume() {}
}
