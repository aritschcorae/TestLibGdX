package ch.yoroshiku.spaceInvader.screen;

import ch.yoroshiku.spaceInvader.SpaceInvader;
import ch.yoroshiku.spaceInvader.controller.GameController;
import ch.yoroshiku.spaceInvader.controller.Gamepad;
import ch.yoroshiku.spaceInvader.manager.EnemyManager;
import ch.yoroshiku.spaceInvader.manager.PowerUpManager;
import ch.yoroshiku.spaceInvader.manager.ShotManager;
import ch.yoroshiku.spaceInvader.manager.StarManager;
import ch.yoroshiku.spaceInvader.model.Ship;
import ch.yoroshiku.spaceInvader.model.ShipStraight;
import ch.yoroshiku.spaceInvader.util.Sizes;
import ch.yoroshiku.spaceInvader.util.Textures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class GameScreen implements Screen
{
	
	enum GamePhase{
		GAMESTART, GAMING, PAUSE, LEVEL_END, DEAD
	}
	private GamePhase phase = GamePhase.GAMING;

	private Renderer renderer;
	public static float buttonHeight;
	
	private Ship ship;
	private StarManager starManager;
	private EnemyManager enemyManager;
	private PowerUpManager powerupManager;
	private ShotManager shotManager;
	private GameController gameController;
	private Gamepad gamepad;
	
	
	public GameScreen(SpaceInvader game) {
		//TODO init game (ship and so on).
		starManager = new StarManager();
		enemyManager = new EnemyManager();
		powerupManager = new PowerUpManager();
		shotManager = new ShotManager();
		ship = new ShipStraight(0, 0, Textures.SHIP_STRAIGHT_TEXTURE);
		ship.x = (Sizes.DEFAULT_WORLD_WIDTH - Sizes.SHIP_WIDTH) / 2;
		shotManager.addShipShots(ship);
		gameController = new GameController(shotManager);
		gamepad = new Gamepad(null, ship);
	}

	
	public void resize(int width, int height) {
		gamepad.resize(width, height);
		renderer = new Renderer((float) height / Sizes.DEFAULT_WORLD_HEIGHT,  ((width - (height / 8 * 7)) / 2));
	}

	public void show() 
	{
		Gdx.input.setInputProcessor(gamepad);
	}

	@Override
	public void render(float delta)
	{
		renderer.cleanScreen();
		renderer.draw(ship, starManager, enemyManager, powerupManager, shotManager);
		renderer.drawMisc();
		//TODO call renderer for drawing
		switch (phase) {
		case GAMESTART:
			//TODO
			break;
		case GAMING:
			starManager.move(delta);
			gamepad.update(delta);
			//TODO
			break;
		case DEAD:
			//TODO
			break;
		case LEVEL_END:
			//TODO
			break;
		case PAUSE:
			//TODO
			break;
		}
	}


	@Override
	public void hide() {}
	@Override
	public void pause() {}
	@Override
	public void resume() {}
	@Override
	public void dispose() {	}
}
