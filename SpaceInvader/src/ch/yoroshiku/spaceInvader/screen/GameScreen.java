package ch.yoroshiku.spaceInvader.screen;

import java.io.IOException;

import ch.yoroshiku.spaceInvader.SpaceInvader;
import ch.yoroshiku.spaceInvader.controller.InGameController;
import ch.yoroshiku.spaceInvader.model.Ship;
import ch.yoroshiku.spaceInvader.model.ShipStraight;
import ch.yoroshiku.spaceInvader.model.enemies.AbstractEnemy;
import ch.yoroshiku.spaceInvader.model.enemieset.EnemySet;
import ch.yoroshiku.spaceInvader.model.enemieset.EnemySetCreator;
import ch.yoroshiku.spaceInvader.util.Sizes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;

public class GameScreen extends AbstractScreen {

	private static final float DEFAULT_WORLD_WIDTH = 96;
	private static final float DEFAULT_WORLD_HEIGHT = 84;
	private float ppuX; // pixels per unit on the X axis
	private float ppuY; // pixels per unit on the Y axis

	private InGameController controller;
	private Ship ship;
	private EnemySet enemy = new EnemySet();
	private EnemySetCreator creator;
	
	public GameScreen(SpaceInvader game) {
		super(game);
		try {
			creator = new EnemySetCreator(ship, enemy);
			creator.loadEnemiesOfNextLvl();
		} catch (IOException e) {
//			e.printStackTrace();
		}
	}


	@Override
	public void show() {
		ship = new ShipStraight(0, 0, new Texture(Gdx.files.internal("images/ship_straight.gif")));
		controller = new InGameController(ship);
		loadTextures();
		Gdx.input.setInputProcessor(controller);
	}

	private void loadTextures() {
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		controller.update(delta);
		batch.begin();
		for(Integer e : enemy.getEnemies().keySet())
		{
			for(AbstractEnemy eemy : enemy.getEnemies().get(e))
			{
				batch.draw(eemy.getTexture(), eemy.x, eemy.y, ppuX * eemy.width, ppuY * eemy.height);
			}
		}
		batch.draw(ship.getShipTexture(), ship.x, ship.y, ppuX * ship.width, ppuY * ship.height);
		batch.end();
	}
	
	private boolean f = true;

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		if(f)
		{
			ship.x = width / 2;
			ship.y = Sizes.SHIP_HEIGHT;
			controller.resize(width, height);
		}
		ppuX = (float) width / DEFAULT_WORLD_WIDTH;
		ppuY = (float) height / DEFAULT_WORLD_HEIGHT;
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}


	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

}
