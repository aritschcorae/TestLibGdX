package ch.yoroshiku.spaceInvader.manager;

import ch.yoroshiku.spaceInvader.model.Ship;
import ch.yoroshiku.spaceInvader.model.shot.Shot;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;


public class ShotManager extends Manager {

	public Array<Shot> enemyShots, allShots, shotsBombs;
	private Array<Shot> toDeleteShots, newEnemyShots;
	
	public ShotManager(){
		allShots = new Array<Shot>();
		toDeleteShots = new Array<Shot>();
		enemyShots = new Array<Shot>();
		shotsBombs = new Array<Shot>();
	}
	

	public void move(float delta) {

		super.move(delta, allShots);
        super.move(delta, shotsBombs);
//            if (bomb.y + bomb.height > Sizes.DEFAULT_WORLD_HEIGHT)
//                toDeleteShots.add(bomb);
        newEnemyShots = new Array<Shot>();
        for (Shot shoot : enemyShots)
        {
            newEnemyShots.addAll(shoot.nextStep(delta));
            if (shoot.y - shoot.height < 0)
                toDeleteShots.add(shoot);
        }
        enemyShots.addAll(newEnemyShots);
	}

	private void checkForGotHit(Ship ship)
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
	
	public void drawSprite(SpriteBatch batch, float ppux, float ppuy, final float offset) {
		super.drawSprite(batch, ppux, ppuy, offset, allShots);
	}

	public void drawShape(ShapeRenderer shapeRenderer, float ppux, float ppuy, final float offset) {
		//TODO
		shapeRenderer.begin(ShapeType.FilledRectangle);
		super.drawShape(shapeRenderer, ppux, ppuy, offset, allShots);
		shapeRenderer.end();
	}


	public void addShipShots(Ship ship) {
		allShots.addAll(ship.getLeftShots());
		allShots.addAll(ship.getMiddleShots());
		allShots.addAll(ship.getRightShots());
	}
}
