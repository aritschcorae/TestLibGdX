package ch.yoroshiku.spaceInvader.controller;

import com.badlogic.gdx.Game;

import ch.yoroshiku.spaceInvader.manager.EnemyManager;
import ch.yoroshiku.spaceInvader.manager.PowerUpManager;
import ch.yoroshiku.spaceInvader.manager.ShotManager;
import ch.yoroshiku.spaceInvader.manager.StarManager;
import ch.yoroshiku.spaceInvader.model.Ship;
import ch.yoroshiku.spaceInvader.model.enemieset.EnemySetFactory;
import ch.yoroshiku.spaceInvader.screen.GameScreen;
import ch.yoroshiku.spaceInvader.screen.GameScreen.GamePhase;

public class GameController
{
	private float shotCounter = 0;
	private EnemySetFactory enemySetCreator;
	public int points;
	
	public GameController(final ShotManager shotManager, final Ship ship, final EnemyManager enemyManager, final GamePhase phase) throws Exception
	{
		points = 0;
		enemySetCreator = new EnemySetFactory(ship, enemyManager);
		enemySetCreator.loadEnemiesOfNextLvl();
	}

	public void process(float delta, Ship ship, final EnemyManager enemyManager, final ShotManager shotManager,
			final StarManager starManager, final PowerUpManager powerUpManager, final GamePhase phase) {
		switch (phase) {
		case GAMESTART:
			// TODO display overlay with explanation how to do what
			GameScreen.updatePhase(GamePhase.GAMING);
			break;
		case GAMING:
			starManager.move(delta);
			powerUpManager.obtainedPowerUp(ship);
			// checkForEnemyGotThrough();TODO either kill enemies or hero dies
			enemyManager.checkForCollision(shotManager, powerUpManager);
			shotManager.destroyEnemyShots();

			shotManager.gotShipHit(ship);

			if (shotCounter > 0.16) {
				ship.shoot(shotCounter - 0.16f);
				shotCounter = 0;
			} else
				shotCounter += delta;

			enemyManager.move(delta);
			shotManager.addEnemyShots(enemyManager.shot(ship));
			powerUpManager.move(delta);
			shotManager.expandExplosions();

			shotManager.move(delta);
			shotManager.cleanUpShots();
			if (shotManager.isBombAround()) 
				ship.setInvincible(false);
			
			points += enemyManager.removeEnemies();
			if (!enemyManager.existsEnemies())
				GameScreen.updatePhase(GamePhase.LEVEL_LOAD);
			break;
		case LEVEL_LOAD:
			shotManager.cleanUpAllShots();
			nextLvl(enemyManager, powerUpManager);
			GameScreen.updatePhase(GamePhase.LEVEL_SCORE);
			break;
		case PAUSE:
		case LEVEL_SCORE:
		case LEVEL_WAIT:
			break;
		case DEAD:
			// TODO //check for gameover and display "you're dead"
			break;
		case FINISHED:
			//TODO // check for highscore and display "congrats"
			break;
		default:
			break;
		}
	}

	private void nextLvl(final EnemyManager enemyManager, final PowerUpManager powerUpManager) {
		// timeNeeded = (System.currentTimeMillis() - levelTime -
		// effectivePause) / 1000;
		// points += calculateLvlBonus();
		enemySetCreator.setNextlvl();
		enemySetCreator.loadEnemiesOfNextLvl();
		// lvlStartPoints = points;
		if (enemyManager.getEnemies() == null) {
			// TODO game finsihed
			GameScreen.updatePhase(GamePhase.FINISHED);
		} else {
			if (enemySetCreator.getPlainLvl() % 10 == 0) {
				powerUpManager.createHealthPowerUp();
			}
			// effectivePause = 0;
			// levelTime = System.currentTimeMillis();
			// gameCycle();
		}
		System.gc();
	}

	public EnemySetFactory getEnemySetCreator()
	{
		return enemySetCreator;
	}

	public void dropBomb(Ship ship, ShotManager shotmanager) {
		if (ship.getBombs() > 0) {
			shotmanager.addBomb(ship);
			ship.dropBomb();
			ship.setInvincible(true);
		}
	}
	
	public int getLevel(){
		return enemySetCreator.getPlainLvl();
	}

	public int getTimeNeeded() {
		return 20;
	}

	public int getTimeBonus() {
		return 1000;
	}

}
