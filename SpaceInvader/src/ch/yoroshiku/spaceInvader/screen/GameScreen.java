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
	
	public enum GamePhase{
		GAMESTART, GAMING, PAUSE, LEVEL_LOAD, HIGHSCORE, LEVEL_WAIT, DEAD, FINISHED
	}
	private static GamePhase phase = GamePhase.GAMING;

	private Renderer renderer;
	public static float buttonHeight;
	
	private Ship ship;
	private StarManager starManager;
	private EnemyManager enemyManager;
	private PowerUpManager powerupManager;
	private ShotManager shotManager;
	private GameController gameController;
	private Gamepad gamepad;
	
	
	public GameScreen(SpaceInvader game) throws Exception {
		//TODO init game (ship and so on).
		starManager = new StarManager();
		enemyManager = new EnemyManager();
		powerupManager = new PowerUpManager();
		shotManager = new ShotManager();
//		ship = new ShipCircle(0, 0, Textures.SHIP_STRAIGHT_TEXTURE); //
		ship = new ShipStraight(0, 0, Textures.SHIP_STRAIGHT_TEXTURE);
		ship.x = (Sizes.DEFAULT_WORLD_WIDTH - Sizes.SHIP_WIDTH) / 2;
		shotManager.addShipShots(ship);
		gameController = new GameController(shotManager, ship, enemyManager, phase);
		gamepad = new Gamepad(gameController, ship);
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
		gamepad.update(delta, phase);
		renderer.draw(ship, starManager, enemyManager, powerupManager, shotManager, phase);
		renderer.drawMisc();
		//TODO call renderer for drawing
		gameController.process(delta, ship, enemyManager, shotManager, starManager, powerupManager, phase);
	}

	public static void updatePhase(GamePhase updatedPhase){
		phase = updatedPhase;
	}

	@Override
	public void hide() {}
	GamePhase stateBeforeBreak;
	@Override
	public void pause() {
		stateBeforeBreak = phase;
		phase = GamePhase.PAUSE;
	}
	@Override
	public void resume() {
		phase = stateBeforeBreak;
	}
	@Override
	public void dispose() {
		System.exit(0);
	}
}
