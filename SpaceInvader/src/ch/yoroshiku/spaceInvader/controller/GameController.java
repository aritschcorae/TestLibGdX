package ch.yoroshiku.spaceInvader.controller;

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
			// TODO
			break;
		case GAMING:
			starManager.move(delta);
			powerUpManager.obtainedPowerUp(ship);
			// checkForEnemyGotThrough();TODO
			enemyManager.checkForCollision(shotManager);
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
			if (shotManager.isBombAround()) {
				ship.setInvincible(false);

			}
			points += enemyManager.removeEnemies();
			if (!enemyManager.existsEnemies())
				GameScreen.updatePhase(GamePhase.LEVEL_LOAD);
			// TODO
			break;
		case LEVEL_LOAD:
			shotManager.cleanUpAllShots();
			nextLvl(enemyManager, powerUpManager);
			GameScreen.updatePhase(GamePhase.HIGHSCORE);
			break;
		case PAUSE:
		case HIGHSCORE:
		case LEVEL_WAIT:
			break;
		case DEAD:
			// TODO
			break;
		case FINISHED:
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

	public void dropBomb(Ship ship) {
		if (ship.getBombs() > 0) {
			// shotsBombs.add(ShotFactory.createShotBitmap(Textures.BOMB_TEXTURE,
			// ship.x + ship.width / 2, ship.y, 60, 30, 1.03f, false));
			// ship.dropBomb();
			// ship.setInvincible(true);
		}
	}

}
