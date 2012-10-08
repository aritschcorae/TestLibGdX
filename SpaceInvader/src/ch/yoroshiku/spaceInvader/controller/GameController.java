package ch.yoroshiku.spaceInvader.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ch.yoroshiku.spaceInvader.model.Explosion;
import ch.yoroshiku.spaceInvader.model.PowerUp;
import ch.yoroshiku.spaceInvader.model.PowerUpFactory;
import ch.yoroshiku.spaceInvader.model.Ship;
import ch.yoroshiku.spaceInvader.model.ShipStraight;
import ch.yoroshiku.spaceInvader.model.Shot;
import ch.yoroshiku.spaceInvader.model.ShotFactory;
import ch.yoroshiku.spaceInvader.model.enemies.AbstractEnemy;
import ch.yoroshiku.spaceInvader.model.enemieset.EnemyGroup;
import ch.yoroshiku.spaceInvader.model.enemieset.EnemySet;
import ch.yoroshiku.spaceInvader.model.enemieset.EnemySetCreator;
import ch.yoroshiku.spaceInvader.screen.GameScreen;
import ch.yoroshiku.spaceInvader.util.Sizes;
import ch.yoroshiku.spaceInvader.util.Textures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class GameController
{
	private EnemyGroup toRemoveEnemies;
	public List<Shot> enemyShots, allShots, shotsBombs;
	private List<Shot> toDeleteShots, newEnemyShots;
	public List<PowerUp> powerUps;
	public List<Explosion> explosions;
	private List<Explosion> toDeleteExplosions;
	private float shotCounter = 0;
	private boolean nextLevel;
	public Ship ship;
	public EnemySet enemySet = new EnemySet();
	private EnemySetCreator enemySetCreator;
	public int points;
	private GameState state;
	public enum GameState {
		PAUSE, PLAY, LEVELEND, GAMEOVER
	};

	public GameController()
	{
		ship = new ShipStraight(0, 0, new Texture(Gdx.files.internal("images/ship_straight.gif")));
		ship.x = (GameScreen.DEFAULT_WORLD_WIDTH - Sizes.SHIP_WIDTH) / 2;
		state = GameState.PAUSE;
		ship.y = 0;
		points = 0;
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
	}
	
	public void resize() throws IOException
	{
		enemySetCreator = new EnemySetCreator(ship, enemySet);
		enemySetCreator.loadEnemiesOfNextLvl();
	}

	
	public void process(float delta)
	{
		toDeleteShots.clear();
		checkForPowerUp();
		// checkForEnemyGotThrough();TODO
		checkForBomb();
		checkForKilledEnemies();
		destroyEnemyShots();

		checkForGotHit();

		if (shotCounter > 0.16)
		{
			ship.shoot(false, shotCounter - 0.16f);
			shotCounter = 0;
		} else
			shotCounter += delta;

		enemySet.moveEnemies(delta);
		enemyShots.addAll(enemySet.shoot(ship));
		
        movePowerUps(delta);

        for(final Explosion explosion : explosions)
        {
            if(explosion.expand())
            {
                toDeleteExplosions.add(explosion);
            }
        }
		moveShot(delta);
        explosions.removeAll(toDeleteExplosions);
        toDeleteExplosions.clear();
        shotsBombs.removeAll(toDeleteShots);
        enemyShots.removeAll(toDeleteShots);
        if ( shotsBombs.size() == 0 && explosions.size() == 0)
        {
            ship.setInvincible(false);
            
        }
        removeEnemies();
        if (!enemySet.existsEnemies())//TODO next lvl
        {
            cleanUpAllShots();
            nextLvl();
		}
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
                        if (explosion.getOuterRadius(1).contains(enemy.x, enemy.y)
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
            if (shoot.y + shoot.getHeight() < GameScreen.DEFAULT_WORLD_HEIGHT 
            		&& shoot.x >= 0 
            		&& shoot.x <= GameScreen.DEFAULT_WORLD_WIDTH)
            {
                for (final EnemyGroup enemies : enemySet.getEnemies().values())
                {
                    if (!enemies.isEmpty() && enemies.isAppeared()
                    		&& shoot.overlaps(enemies.getBounds()))
                    {
                        if (checkHit(shoot, enemies, bomb))
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
    public boolean checkHit(final Shot shoot, final EnemyGroup enemies, final boolean bomb)
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
                    final Explosion newExplosion = new Explosion(x, y, 40 * ship.getDamage());
                    explosions.add(newExplosion);
                    toDeleteShots.add(shoot);
                }
                else
                {
                    shoot.setY(GameScreen.DEFAULT_WORLD_HEIGHT + 20);
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
            if (bomb.y + bomb.height > GameScreen.DEFAULT_WORLD_HEIGHT)
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
            shot.setY(GameScreen.DEFAULT_WORLD_HEIGHT + 50);
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

	public EnemySetCreator getEnemySetCreator()
	{
		return enemySetCreator;
	}

	public void dropBomb()
	{
		if(ship.getBombs() > 0)
		{
	        shotsBombs.add(ShotFactory.createShotBitmap(Textures.BOMB_TEXTURE, 
	                ship.x + ship.width / 2, ship.y, 60, 30, 1.03f, false));
	        ship.dropBomb();
	        ship.setInvincible(true);	
		}
	}

	public GameState getState()
	{
		return state;
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

	
}
