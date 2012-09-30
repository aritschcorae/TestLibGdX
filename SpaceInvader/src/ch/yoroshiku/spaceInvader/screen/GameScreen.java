package ch.yoroshiku.spaceInvader.screen;

import java.io.IOException;

import ch.yoroshiku.spaceInvader.SpaceInvader;
import ch.yoroshiku.spaceInvader.controller.InGameController;
import ch.yoroshiku.spaceInvader.model.Ship;
import ch.yoroshiku.spaceInvader.model.ShipStraight;
import ch.yoroshiku.spaceInvader.model.enemies.AbstractEnemy;
import ch.yoroshiku.spaceInvader.model.enemieset.EnemyGroup;
import ch.yoroshiku.spaceInvader.model.enemieset.EnemySet;
import ch.yoroshiku.spaceInvader.model.enemieset.EnemySetCreator;
import ch.yoroshiku.spaceInvader.util.Sizes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class GameScreen extends AbstractScreen {

	private static final float DEFAULT_WORLD_WIDTH = 96;
	private static final float DEFAULT_WORLD_HEIGHT = 84;
	private float ppuX; // pixels per unit on the X axis
	private float ppuY; // pixels per unit on the Y axis

	private InGameController controller;
	private Ship ship;
	private EnemySet enemySet = new EnemySet();
	private EnemySetCreator creator;
	
	public GameScreen(SpaceInvader game) {
		super(game);
		try {
			creator = new EnemySetCreator(ship, enemySet);
			creator.loadEnemiesOfNextLvl();
		} catch (IOException e) {
//			e.printStackTrace();
		}
	}

	private Color c;

	@Override
	public void show() {
		ship = new ShipStraight(0, 0, new Texture(Gdx.files.internal("images/ship_straight.gif")));
		controller = new InGameController(ship);
		loadTextures();
		Gdx.input.setInputProcessor(controller);
		c = batch.getColor();
	}

	private void loadTextures() {
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		controller.update(delta);
		batch.begin();
//		batch.disableBlending();
		Sprite b = new Sprite(ship.getShipTexture());
		for(EnemyGroup e : enemySet.getEnemies().values())
		{
			e.move();
			batch.setColor(e.getColor());
			for(AbstractEnemy enemy : e)
			{
				if(enemy.isVisible())
				{
					//TODO timer
					batch.draw(enemy.getTexture(), enemy.x * ppuX, enemy.y * ppuY, ppuX * enemy.width, ppuY * enemy.height);
				}
			}
			batch.setColor(c);
		}
		batch.draw(ship.getShipTexture(), ship.x, ship.y, ppuX * ship.width, ppuY * ship.height);

	    batch.setColor(c);
		batch.end();
	}
	
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		ship.x = width / 2;
		ship.y = Sizes.SHIP_HEIGHT;
		controller.resize(width, height);
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
