package ch.yoroshiku.spaceInvader.controller;

import java.io.IOException;

import ch.yoroshiku.spaceInvader.manager.EnemyManager;
import ch.yoroshiku.spaceInvader.manager.ShotManager;
import ch.yoroshiku.spaceInvader.model.Explosion;
import ch.yoroshiku.spaceInvader.model.PowerUp;
import ch.yoroshiku.spaceInvader.model.Ship;
import ch.yoroshiku.spaceInvader.model.ShipStraight;
import ch.yoroshiku.spaceInvader.model.enemieset.EnemySetFactory;
import ch.yoroshiku.spaceInvader.model.shot.Shot;
import ch.yoroshiku.spaceInvader.model.shot.ShotFactory;
import ch.yoroshiku.spaceInvader.util.Helper;
import ch.yoroshiku.spaceInvader.util.Sizes;
import ch.yoroshiku.spaceInvader.util.Textures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class GameController
{
	public Array<Explosion> explosions;
	private Array<Explosion> toDeleteExplosions;
	private float shotCounter = 0;
	private boolean nextLevel;
	public EnemyManager enemyManager = new EnemyManager();
	private EnemySetFactory enemySetCreator;
	public int points;
	
	public GameController(final ShotManager shotManager)
	{
		points = 0;
		explosions = new Array<Explosion>();
		toDeleteExplosions = new Array<Explosion>();
	}
	
	public void resize() throws IOException
	{
		enemySetCreator = new EnemySetFactory(ship, enemyManager);
		enemySetCreator.loadEnemiesOfNextLvl();
	}

	
	public void process(float delta)
	{
		toDeleteShots.clear();
		checkForPowerUp();
		// checkForEnemyGotThrough();TODO
		enemyManager.checkForBomb(explosions);
		enemyManager.checkForKilledEnemies(shotsBombs, allShots);
		destroyEnemyShots();

		checkForGotHit();

		if (shotCounter > 0.16)
		{
			ship.shoot(false, shotCounter - 0.16f);
			shotCounter = 0;
		} else
			shotCounter += delta;

		enemyManager.moveEnemies(delta);
		enemyShots.addAll(enemyManager.shoot(ship));
		
        movePowerUps(delta);

        for(final Explosion explosion : explosions)
        {
            if(explosion.expand())
            {
                toDeleteExplosions.add(explosion);
            }
        }
		moveShot(delta);
        Helper.removeAll(explosions, toDeleteExplosions);
        toDeleteExplosions.clear();
        Helper.removeAll(shotsBombs, toDeleteShots);
        Helper.removeAll(enemyShots, toDeleteShots);
        if ( shotsBombs.size == 0 && explosions.size == 0)
        {
            ship.setInvincible(false);
            
        }
        points += enemyManager.removeEnemies();
        if (!enemyManager.existsEnemies())//TODO next lvl
        {
            cleanUpAllShots();
            nextLvl();
		}
	}
	

    protected void checkForPowerUp()
    {
    	if(powerUps.size == 0)
    		return;
    	Array<PowerUp> powerUpsToRemove = new Array<PowerUp>();
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
        Helper.removeAll(powerUps, powerUpsToRemove);
    }
    
	private void destroyEnemyShots()
	{
	        for (Shot bomb : shotsBombs)
	            for (Shot enemyShot : enemyShots)
	            	if(bomb.overlaps(enemyShot))
	                    toDeleteShots.add(enemyShot);
	}

	
    private void movePowerUps(float delta)
    {
        final Array<PowerUp> toRemovePowerUps = new Array<PowerUp>();
        for (final PowerUp powerUp : powerUps)
        {
            powerUp.move(delta);
            if (powerUp.y < 0 || powerUp.isEated())
                toRemovePowerUps.add(powerUp);
        }
        Helper.removeAll(powerUps, toRemovePowerUps);
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
            shot.setY(Sizes.DEFAULT_WORLD_HEIGHT + 50);
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

	public EnemySetFactory getEnemySetCreator()
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
