package ch.yoroshiku.spaceInvader.screen;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

import ch.yoroshiku.spaceInvader.SpaceInvader;
import ch.yoroshiku.spaceInvader.controller.GameController;
import ch.yoroshiku.spaceInvader.controller.InGameController;
import ch.yoroshiku.spaceInvader.model.Explosion;
import ch.yoroshiku.spaceInvader.model.PowerUp;
import ch.yoroshiku.spaceInvader.model.Shot;
import ch.yoroshiku.spaceInvader.model.ShotBitmap;
import ch.yoroshiku.spaceInvader.model.enemies.AbstractEnemy;
import ch.yoroshiku.spaceInvader.model.enemieset.EnemyGroup;
import ch.yoroshiku.spaceInvader.util.Sizes;
import ch.yoroshiku.spaceInvader.util.Textures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class GameScreen extends AbstractScreen 
{
	public static float DEFAULT_WORLD_WIDTH = 54;
	public static final float DEFAULT_WORLD_HEIGHT = 64;
	public static float border, buttonHeight;
	private float ppuX; // pixels per unit on the X axis
	private float ppuY; // pixels per unit on the Y axis
	private float[] starsX, starsY;
	private InGameController shipController;
	private GameController gameController; 
	private Color c;
	private Random starGenerator = new Random();
	
	public GameScreen(SpaceInvader game) throws IOException {
		super(game);
		gameController = new GameController();
		font.setColor(Color.WHITE);
		font.setScale(0.9f);
		starsX = new float[30];
		starsY = new float[30];
		for(int i = 0; i < 30; i ++)
		{
			starsX[i] = starGenerator.nextInt((int)DEFAULT_WORLD_WIDTH);
			starsY[i] = starGenerator.nextInt((int)DEFAULT_WORLD_HEIGHT);
		}
	}

	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		shipController.resize(width, height);
		ppuY = (float) height / DEFAULT_WORLD_HEIGHT;
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
		shipController = new InGameController(gameController);
		Gdx.input.setInputProcessor(shipController);
		c = batch.getColor();
	}

	@Override
	public void render(float delta)
	{
		super.render(delta);
		shipController.update(delta);
		gameController.process(delta);
		handleStars(delta);
		drawCanvas();
	}
	
	private void handleStars(float delta)
	{
		for(int i = 0; i < 30; i ++)
		{
			starsY[i] -= DEFAULT_WORLD_HEIGHT * delta / 2;
			if(starsY[i] < 0)
			{
				starsX[i] = starGenerator.nextInt((int)DEFAULT_WORLD_WIDTH);
				starsY[i] = DEFAULT_WORLD_HEIGHT;
			}
		}
	}

    
	private void drawCanvas()
	{
		batch.begin();
		drawEnemies();
	    drawBombs();
	    drawButtons();
	    drawPowerUps();
		drawStats();
		batch.draw(gameController.ship.getShipTexture(), border + gameController.ship.x * ppuX, gameController.ship.y * ppuY,
                0, 0, gameController.ship.width, gameController.ship.height, ppuX, ppuY, 0);
		batch.end();
		drawHealth();
		drawShots();
		drawBorder();
		drawStars();
		drawCircle();
		debug();
	}


	private void drawEnemies()
	{
		for(EnemyGroup enemyGroup : gameController.enemySet.getEnemies().values())
		{
			batch.setColor(enemyGroup.getColor());
			for(AbstractEnemy enemy : enemyGroup)
			{
				if(enemy.isVisible())
				{
					batch.draw(enemy.getTexture(), border + enemy.x * ppuX, enemy.y * ppuY, ppuX * enemy.width, ppuY * enemy.height);
					if(enemy.getSubTextures() != null)
					{
						final Map<Rectangle, TextureRegion> map = enemy.getSubTextures();
						for(final Rectangle rect : map.keySet())
						{
							batch.draw(map.get(rect), border + rect.x * ppuX, rect.y * ppuY, rect.width * ppuX, rect.height * ppuY);
						}
					}
				}
			}
			batch.setColor(c);
		}
	}


	private void drawHealth()
	{
		shapeRenderer.begin(ShapeType.FilledRectangle);
		for(EnemyGroup enemyGroup : gameController.enemySet.getEnemies().values())
		{
			for(AbstractEnemy enemy : enemyGroup)
			{
				if(enemyGroup.isVisible() && enemy.isVisible() && enemy.isShowHealthBar())
				{
					//border
					shapeRenderer.setColor(Color.YELLOW);
					shapeRenderer.filledRect(border + enemy.x * ppuX, (Sizes.ENEMY_HEALTHBAR_DISTANCE + enemy.y + enemy.height) * ppuY - 1, 
							enemy.width * ppuX, 1);
					shapeRenderer.filledRect(border + enemy.x * ppuX, (Sizes.HEALTHBAR_HEIGHT + Sizes.ENEMY_HEALTHBAR_DISTANCE + enemy.y + enemy.height) * ppuY, 
							enemy.width * ppuX, 1);
					//Total Health
					shapeRenderer.setColor(Color.RED);
					shapeRenderer.filledRect(border + enemy.x * ppuX, (Sizes.ENEMY_HEALTHBAR_DISTANCE + enemy.y + enemy.height) * ppuY, 
							enemy.width * ppuX, Sizes.HEALTHBAR_HEIGHT * ppuY);
					//health remaining
					shapeRenderer.setColor(Color.GREEN);
					shapeRenderer.filledRect(border + enemy.x * ppuX, (Sizes.ENEMY_HEALTHBAR_DISTANCE + enemy.y + enemy.height) * ppuY, 
							(float)(enemy.width * ppuX * enemy.getHealth() / enemy.getMaxHealth()), Sizes.HEALTHBAR_HEIGHT * ppuY);
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
				border + (DEFAULT_WORLD_WIDTH - Sizes.SHOT_BOMB_WIDTH - 3) * ppuX, 0);
	}
	
	private void drawButtons()
	{
		batch.draw(Textures.BUTTON_TEXTURE, 0, DEFAULT_WORLD_HEIGHT * ppuY - buttonHeight, border, buttonHeight);
	}
	
	private void drawPowerUps()
	{
		for(final PowerUp powerUp : gameController.powerUps)
		{
			batch.draw(powerUp.getPowerUpTexture(), border + powerUp.x * ppuX, powerUp.y * ppuY, powerUp.width * ppuX, powerUp.height * ppuY);
		}
	}

	private void drawStats()
	{
		final String pointAmount = "Points: " + gameController.points;
		font.draw(batch, pointAmount , border + 1 * ppuX, DEFAULT_WORLD_HEIGHT * ppuY);
		final String lvl = "lvl " + gameController.getEnemySetCreator().getPlainLvl();
		font.draw(batch, lvl , border + DEFAULT_WORLD_WIDTH * ppuX - font.getSpaceWidth() * pointAmount.length() - ppuX, DEFAULT_WORLD_HEIGHT * ppuY); 
		
		font.draw(batch, ": " +gameController.ship.getBombs() , border + (DEFAULT_WORLD_WIDTH - 3)* ppuX, font.getCapHeight());
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
		shapeRenderer.line(border, 0, border, DEFAULT_WORLD_HEIGHT * ppuY);
		shapeRenderer.line(border + DEFAULT_WORLD_WIDTH * ppuX, 0, border + DEFAULT_WORLD_WIDTH * ppuX, DEFAULT_WORLD_HEIGHT * ppuY);
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
		{
			
	        for(int i = (int) (explosion.getOuterRadius(ppuX).radius - explosion.radius); i >=0 ; i--)
	        {
	        	float green = 1 / (explosion.getOuterRadius(ppuX).radius - explosion.radius);
	        	shapeRenderer.setColor(1, green, 0, 0);
	        	shapeRenderer.circle(border + explosion.x  * ppuX, explosion.y * ppuY, explosion.radius * ppuX);
	            
	        }
		}
	}
	
	private void drawShield()
	{
		if (gameController.ship.isInvincible())
		{
			shapeRenderer.setColor(0, 1, 0, 0);
			shapeRenderer.circle(border + (gameController.ship.x + Sizes.SHIP_CORE) * ppuX, 
					(gameController.ship.y + Sizes.SHIP_CORE) * ppuY, Sizes.SHIP_RADIUS * ppuX);
		}
		else
		{
			final float shield = gameController.ship.getShield();
	        if (shield >= 5)
	        {
				shapeRenderer.setColor(1, 1, 1, 1);
				shapeRenderer.circle(border + (gameController.ship.x + Sizes.SHIP_CORE) * ppuX, 
						(gameController.ship.y + Sizes.SHIP_CORE) * ppuY, Sizes.SHIP_RADIUS * ppuX);
	            if (shield < 10)
	            {
	    			shapeRenderer.setColor(1, 1, 1, (shield - 5) * 0.2f);
	            }
				shapeRenderer
					.circle(border + (gameController.ship.x + Sizes.SHIP_CORE) * ppuX, 
							(gameController.ship.y + Sizes.SHIP_CORE) * ppuY, Sizes.SHIP_RADIUS_DOUBLE * ppuX);
	        }
	        else if(shield > 0)
	        {
				shapeRenderer.setColor(1, 1, 1, (1 - 0.2f) * shield);
				shapeRenderer.circle(border + (gameController.ship.x + Sizes.SHIP_CORE) * ppuX, 
						(gameController.ship.y + Sizes.SHIP_CORE) * ppuY, Sizes.SHIP_RADIUS * ppuX);
	        }
		}
	}
	

	private void debug()
	{
		boolean a = false;
		if (a)
		{
			shapeRenderer.begin(ShapeType.Rectangle);
			// shapeRenderer.rect(ship.getShipHitSpace().x * ppuX,
			// ship.getShipHitSpace().y * ppuY, ship.getShipHitSpace().width *
			// ppuX, ship.getShipHitSpace().height * ppuY);
			// for(Shot bomb : gameController.shotsBombs)
			// {
			// shapeRenderer.rect(bomb.x * ppuX, bomb.y * ppuY, bomb.width,
			// bomb.height);
			// }
			// for(EnemyGroup enemyGroup :
			// gameController.enemySet.getEnemies().values())
			// {
			// batch.setColor(enemyGroup.getColor());
			// for(AbstractEnemy enemy : enemyGroup)
			// {
			// // if(enemy.isVisible())
			// // {
			// shapeRenderer.rect(enemy.x * ppuX, enemy.y * ppuY, ppuX *
			// enemy.width, ppuY * enemy.height);
			// // }
			// }
			// batch.setColor(c);
			// }

			for (Explosion explosion : gameController.explosions)
			{

				for (int i = (int) (explosion.getOuterRadius(1).radius - explosion.radius); i >= 0; i--)
				{
					float green = 1 / (explosion.getOuterRadius(1).radius - explosion.radius);
					shapeRenderer.setColor(Color.WHITE);
					shapeRenderer.rect(border + explosion.x * ppuX, explosion.y * ppuY,
							(explosion.radius + explosion.radius) * 2, (explosion.radius + explosion.radius) * 2);

				}
			}
			shapeRenderer.end();
			shapeRenderer.begin(ShapeType.Circle);
			for (Explosion explosion : gameController.explosions)
			{

				for (int i = (int) (explosion.getOuterRadius(1).radius - explosion.radius); i >= 0; i--)
				{
					float green = 1 / (explosion.getOuterRadius(1).radius - explosion.radius);
					shapeRenderer.setColor(Color.WHITE);
					shapeRenderer.circle(explosion.x, explosion.y, explosion.radius);

				}
			}
			shapeRenderer.end();
			shapeRenderer.begin(ShapeType.FilledRectangle);
			for (Shot shot : gameController.allShots)
			{
				shapeRenderer.filledRect(shot.x * ppuX, shot.y * ppuY, shot.width * ppuX, shot.height * ppuY);
			}
			// for(EnemyGroup e : enemySet.getEnemies().values())
			// {
			// shapeRenderer.rect(border +
			// e.getBounds().x * ppuX, e.getBounds().y * ppuY,
			// e.getBounds().width * ppuX, e.getBounds().height * ppuY);
			// for(AbstractEnemy enemy : e)
			// {
			// shapeRenderer.rect(border + enemy.x * ppuX, enemy.y * ppuY,
			// enemy.width * ppuX, enemy.height * ppuY);
			// }
			// }
			shapeRenderer.end();

		}
	}
	

	@Override
	public void hide() {}
	@Override
	public void pause() {}
	@Override
	public void resume() {}
}
