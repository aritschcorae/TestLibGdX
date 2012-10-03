package ch.yoroshiku.spaceInvader.screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ch.yoroshiku.spaceInvader.SpaceInvader;
import ch.yoroshiku.spaceInvader.controller.InGameController;
import ch.yoroshiku.spaceInvader.model.Explosion;
import ch.yoroshiku.spaceInvader.model.PowerUp;
import ch.yoroshiku.spaceInvader.model.PowerUpFactory;
import ch.yoroshiku.spaceInvader.model.Ship;
import ch.yoroshiku.spaceInvader.model.ShipStraight;
import ch.yoroshiku.spaceInvader.model.Shot;
import ch.yoroshiku.spaceInvader.model.enemies.AbstractEnemy;
import ch.yoroshiku.spaceInvader.model.enemieset.EnemyGroup;
import ch.yoroshiku.spaceInvader.model.enemieset.EnemySet;
import ch.yoroshiku.spaceInvader.model.enemieset.EnemySetCreator;
import ch.yoroshiku.spaceInvader.util.Sizes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class GameScreen extends AbstractScreen 
{
	//TODO drop bomb
	public static float DEFAULT_WORLD_WIDTH = 54;
	public static final float DEFAULT_WORLD_HEIGHT = 64;
	private int x = 0;
	private boolean nextLevel;
	private float ppuX; // pixels per unit on the X axis
	private float ppuY; // pixels per unit on the Y axis
	private EnemyGroup toRemoveEnemies;
	private List<Shot> enemyShots, toDeleteShots, shotsBombs, allShots, newEnemyShots;
	private List<PowerUp> powerUps;
	private List<Explosion> explosions, toDeleteExplosions;
	private InGameController controller;
	private Ship ship;
	private EnemySet enemySet = new EnemySet();
	private EnemySetCreator enemySetCreator;
	private int points;
	private Color c;
	
	public GameScreen(SpaceInvader game) {
		super(game);
		try 
		{
			enemySetCreator = new EnemySetCreator(ship, enemySet);
			enemySetCreator.loadEnemiesOfNextLvl();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		controller.resize(width, height);
		ppuY = (float) height / DEFAULT_WORLD_HEIGHT;
		ppuX = ppuY;
		border = (ppuY * DEFAULT_WORLD_WIDTH - DEFAULT_WORLD_WIDTH) / 2;
	}

	@Override
	public void show() {
		ship = new ShipStraight(0, 0, new Texture(Gdx.files.internal("images/ship_straight.gif")));
		ship.x = (DEFAULT_WORLD_WIDTH - Sizes.SHIP_WIDTH) / 2;
		ship.y = 0;
		points = 0;
		controller = new InGameController(ship);
		allShots = new ArrayList<Shot>();
		allShots.addAll(ship.getLeftShots());
		allShots.addAll(ship.getMiddleShots());
		allShots.addAll(ship.getRightShots());
		toDeleteShots = new ArrayList<Shot>();
		enemyShots = new ArrayList<Shot>();
		shotsBombs = new ArrayList<Shot>();
		powerUps = new ArrayList<PowerUp>();
		explosions = new ArrayList<Explosion>();
		toDeleteExplosions = new ArrayList<Explosion>();
		toRemoveEnemies = new EnemyGroup(false, 1, 0, 0, 0);
		Gdx.input.setInputProcessor(controller);
		c = batch.getColor();
	}

	@Override
	public void render(float delta)
	{
		super.render(delta);
		controller.update(delta);

		toDeleteShots.clear();
		checkForPowerUp();
		// checkForEnemyGotThrough();TODO
		checkForBomb();
		checkForKilledEnemies();
		destroyEnemyShots();

		checkForGotHit();

		if (x > 10)
		{
			ship.shoot(false);
			x = 0;
		} else
			x++;// TODO exact

		enemySet.moveEnemies(delta);
		enemyShots.addAll(enemySet.shoot(ship));
		
//		handleStars();
        movePowerUps(delta);

        for(final Explosion explosion : explosions)
        {
            if(explosion.expand())
            {
                toDeleteExplosions.add(explosion);
            }
        }
        explosions.removeAll(toDeleteExplosions);
        toDeleteExplosions.clear();
        shotsBombs.removeAll(toDeleteShots);
        enemyShots.removeAll(toDeleteShots);
        if ( shotsBombs.size() == 0 && explosions.size() == 0)
        {
            ship.setInvincible(false);
            
        }
        removeEnemies();
		moveShot(delta);
        if (!enemySet.existsEnemies())//TODO next lvl
        {
            cleanUpAllShots();
            nextLvl();
		}
		drawCanvas();
	}

    protected void checkForPowerUp()
    {
    	if(powerUps.isEmpty())
    		return;
        List<PowerUp> powerUpsToRemove = new ArrayList<PowerUp>();
        for (final PowerUp powerUp : powerUps)
        {
            if(powerUp.overlaps(ship.getShipPowerUpReach()))
            {
                ship.addPowerUp(powerUp);
                powerUpsToRemove.add(powerUp);
                if (ship.isSpray())
                {
//                    updateSprayButton ();//TODO look at it
                }
            }
        }
        powerUps.removeAll(powerUpsToRemove);
    }
    

    private void checkForBomb()
    {
        for (final Explosion explosion : explosions)
        {
            for (final EnemyGroup enemyGroup : enemySet.getEnemies().values())
            {
                if (enemyGroup.isAppeared())
                {
                    for (final AbstractEnemy enemy : enemyGroup)
                    {
                        if (explosion.getOuterRadius().contains(enemy.x, enemy.y)
                        		&& !explosion.contains(enemy.x, enemy.y))
                        {
                            if(enemy.bombDamage(explosion.getDamage()))
                            {
                                enemyKilled(enemy);
                            }
                        }
                    }
                }
                enemySet.remove(enemyGroup, toRemoveEnemies);
            }
        }
    }

    private void checkForKilledEnemies()
    {
        iterateShotList(shotsBombs, true);
        iterateShotList(allShots, false);
    }

    private void iterateShotList(final List<Shot> shots, final boolean bomb)
    {
        for (final Shot shoot : shots)
        {
            if (shoot.y + shoot.getHeight() < DEFAULT_WORLD_HEIGHT 
            		&& shoot.x >= 0 
            		&& shoot.x <= DEFAULT_WORLD_WIDTH)
            {
                for (final EnemyGroup enemies : enemySet.getEnemies().values())
                {
                    if (!enemies.isEmpty() && enemies.isAppeared()
                    		&& shoot.overlaps(enemies.getBounds()))
                    {
                        if (checkHit(shoot, shoot.getX() + shoot.getWidth(), shoot.getY() + shoot.getHeight(), enemies, bomb))
                        {
                            enemySet.remove(enemies, toRemoveEnemies);
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * @return true if hitted
     */
    public boolean checkHit(final Shot shoot, final float shootXEnd, final float shootYEnd, final EnemyGroup enemies, final boolean bomb)
    {
        for (final AbstractEnemy enemy : enemies)
        {
            if (shoot.overlaps(enemy))
            {
                if((bomb && enemy.bombDamage(shoot.getDamage()))
               || (!bomb && enemy.lowerHealth(shoot.getDamage())))
                {
                    enemyKilled(enemy); 
                }
                if (!shoot.isSimpleShot())
                {
                    final float x;
                    final float y;
                    if (shoot.x <= enemy.x)
                        x = enemy.x;
                    else
                        x = shoot.getX();
                    if (shoot.y <= enemy.y)
                        y = enemy.y;
                    else
                        y = shoot.getY();
                    final Explosion newExplosion = new Explosion(x, y, 20 * ship.getDamage());
                    explosions.add(newExplosion);
                    toDeleteShots.add(shoot);
                }
                else
                {
                    shoot.setY(DEFAULT_WORLD_HEIGHT + 20);
                }
                return true;
            }
        }
        return false;
    }

    public void enemyKilled(final AbstractEnemy enemy)
    {
        for (final AbstractEnemy destroyedEnemy : enemy.getDestroyedEnemies())
        {
            toRemoveEnemies.add(destroyedEnemy);
            if (enemy.hasPowerUp())
            {
                PowerUp powerUp = PowerUpFactory.createPowerUp(ship, enemySetCreator.getPlainLvl());
                powerUp.setCoordinates(enemy.getX() + enemy.getWidth() / 2, (enemy.getY() + enemy.getHeight() / 2));
                powerUps.add(powerUp);
                enemySet.registerPowerUp(powerUp);
            }
        }
    }

	
	private void destroyEnemyShots()
	{
	        for (Shot bomb : shotsBombs)
	            for (Shot enemyShot : enemyShots)
	            	if(bomb.overlaps(enemyShot))
	                    toDeleteShots.add(enemyShot);
	}

	private void checkForGotHit()
	{
        if (!ship.isInvincible())
        {
            for (final Shot shot : enemyShots)
            {
                if(shot.overlaps(ship.getShipHitSpace()))
                {
                    ship.gotHit((int) shot.getDamage());
                    if (ship.getHealth() <= 0)
                    {
//                        gameOver(); //TODO
                    }
                    toDeleteShots.add(shot);
                }
            }
        }
	}
	
    private void movePowerUps(float delta)
    {
        final List<PowerUp> toRemovePowerUps = new ArrayList<PowerUp>();
        for (final PowerUp powerUp : powerUps)
        {
            powerUp.move(delta);
            if (powerUp.y < 0 || powerUp.isEated())
                toRemovePowerUps.add(powerUp);
        }
        powerUps.removeAll(toRemovePowerUps);
    }

    private void moveShot(float delta)
    {
		for(Shot shot : allShots)
		{
			shot.nextStep(delta);
		}
        for (Shot bomb : shotsBombs)
        {
            bomb.nextStep(delta);
            if (bomb.y + bomb.height < 0)
                toDeleteShots.add(bomb);
        }
        newEnemyShots = new ArrayList<Shot>();
        for (Shot shoot : enemyShots)
        {
            newEnemyShots.addAll(shoot.nextStep(delta));
            if (shoot.y - shoot.height < 0)
                toDeleteShots.add(shoot);
        }
        enemyShots.addAll(newEnemyShots);
    }

    public void removeEnemies()
    {
        for (AbstractEnemy enemy : toRemoveEnemies)
        {
            points += enemy.getPoints();
        }
        toRemoveEnemies.clear();
    }

    public void cleanUpAllShots()
    {
        enemyShots.clear();
        explosions.clear();
        shotsBombs.clear();
        cleanUpShipShots();
    }
    
    private void cleanUpShipShots()
    {
        for (final Shot shot : allShots)
            shot.setY(DEFAULT_WORLD_HEIGHT + 50);
    }

    private void nextLvl()
    {
//        timeNeeded = (System.currentTimeMillis() - levelTime - effectivePause) / 1000;
//        points += calculateLvlBonus();
        nextLevel = true;
        enemySetCreator.setNextlvl();
        enemySetCreator.loadEnemiesOfNextLvl();
//        lvlStartPoints = points;
        System.gc();
    }

//    public void loadNextLvl()
//    {
//        if(nextLevel)
//        {
//            nextLevel = false;
//            loadNextLevel = false;
//            enemySetCreator.setNextDisplaylvl();
//            if(enemySet.getEnemies() == null || (difficulty == 6 && enemySetCreator.getPlainLvl() == 15))
//            {
//                highScoreEntered = false;
//                gameFinished = true;
//            }
//            else
//            {
//                if (enemySetCreator.getPlainLvl() % 10 == 0)
//                {
//                    powerUps.add(PowerUpFactory.createHealPowerUp(new Coordinates(canvasWidth / 2, canvasHeight / 2), getContext()));
//                }
//                effectivePause = 0;
//                levelTime = System.currentTimeMillis();
//                gameCycle();
//            }
//            showBonus = 0;
//        }
//    }

    
	private void drawCanvas()
	{
//		Camera a = new OrthographicCamera(DEFAULT_WORLD_WIDTH, DEFAULT_WORLD_HEIGHT);
//		Camera a = new PerspectiveCamera();
//		a.update();
		batch.begin();
		for(EnemyGroup enemyGroup : enemySet.getEnemies().values())
		{
			batch.setColor(enemyGroup.getColor());
			for(AbstractEnemy enemy : enemyGroup)
			{
				if(enemy.isVisible())
				{
					batch.draw(enemy.getTexture(), border + enemy.x * ppuX, enemy.y * ppuY, ppuX * enemy.width, ppuY * enemy.height);
				}
			}
			batch.setColor(c);
		}
		batch.draw(ship.getShipTexture(), border + ship.x * ppuX, ship.y * ppuY,
                0, 0, ship.width, ship.height, ppuX, ppuY, 0);

	    batch.setColor(c);
	    drawPowerUps();
		batch.end();
		drawShots();
		drawBorder();
//		debug();
	}
	
	private void drawPowerUps()
	{
		for(final PowerUp powerUp : powerUps)
		{
			batch.draw(powerUp.getPowerUpTexture(), border + powerUp.x * ppuX, powerUp.y * ppuY, powerUp.width * ppuX, powerUp.height * ppuY);
		}
	}

	private void drawShots()
	{
		shapeRenderer.begin(ShapeType.FilledRectangle);
		shapeRenderer.setColor(Color.RED);
		for(Shot shot : allShots)
		{
			shapeRenderer.filledRect(border + shot.x * ppuX, shot.y * ppuY, shot.width * ppuX, shot.height * ppuY);
		}
		shapeRenderer.setColor(Color.YELLOW);
		for(Shot shot : enemyShots)
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

	private void debug()
	{
		shapeRenderer.begin(ShapeType.Rectangle);
//		shapeRenderer.rect(ship.getShipHitSpace().x * ppuX, ship.getShipHitSpace().y * ppuY, ship.getShipHitSpace().width * ppuX, ship.getShipHitSpace().height * ppuY);
		for(EnemyGroup enemyGroup : enemySet.getEnemies().values())
		{
			batch.setColor(enemyGroup.getColor());
			for(AbstractEnemy enemy : enemyGroup)
			{
//				if(enemy.isVisible())
//				{
					shapeRenderer.rect(enemy.x * ppuX, enemy.y * ppuY, ppuX * enemy.width, ppuY * enemy.height);
//				}
			}
			batch.setColor(c);
		}
		shapeRenderer.end();
		shapeRenderer.begin(ShapeType.FilledRectangle);
		for(Shot shot : allShots)
		{
			shapeRenderer.filledRect(shot.x * ppuX, shot.y * ppuY, shot.width * ppuX, shot.height * ppuY);
		}
//		for(EnemyGroup e : enemySet.getEnemies().values())
//		{
//			shapeRenderer.rect(border + 
//					e.getBounds().x * ppuX, e.getBounds().y * ppuY, 
//					e.getBounds().width * ppuX, e.getBounds().height * ppuY);
//			for(AbstractEnemy enemy : e)
//			{
//				shapeRenderer.rect(border + enemy.x * ppuX, enemy.y * ppuY, enemy.width * ppuX, enemy.height * ppuY);
//			}
//		}
		shapeRenderer.end();
	}
	
	public static float border;

	@Override
	public void hide() {}
	@Override
	public void pause() {}
	@Override
	public void resume() {}
}
