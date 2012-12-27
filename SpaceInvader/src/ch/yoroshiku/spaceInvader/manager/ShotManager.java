package ch.yoroshiku.spaceInvader.manager;

import ch.yoroshiku.spaceInvader.model.Explosion;
import ch.yoroshiku.spaceInvader.model.Ship;
import ch.yoroshiku.spaceInvader.model.shot.Shot;
import ch.yoroshiku.spaceInvader.util.Helper;
import ch.yoroshiku.spaceInvader.util.Sizes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;


public class ShotManager extends Manager {

	public Array<Shot> enemyShots, allShots, shotsBombs;
	private Array<Shot> toDeleteShots;
	public Array<Explosion> explosions;
	private Array<Explosion> toDeleteExplosions;
	
	public ShotManager(){
		allShots = new Array<Shot>();
		toDeleteShots = new Array<Shot>();
		enemyShots = new Array<Shot>();
		shotsBombs = new Array<Shot>();
		explosions = new Array<Explosion>();
		toDeleteExplosions = new Array<Explosion>();
	}
	

	public void move(float delta) {
		//TODO
		nextStep(allShots, delta);
		nextStep(shotsBombs, delta);
		nextStep(enemyShots, delta);
		removeOutOfRangeShots(shotsBombs, false);
		removeOutOfRangeShots(enemyShots, true);
	}
	
	private void nextStep(Array<Shot> shots, final float delta){
		Array<Shot> additionalShots = new Array<Shot>();
		for(Shot shot : shots){
			additionalShots.addAll(shot.nextStep(delta));
		}
		shots.addAll(additionalShots);
	}
	
	private void removeOutOfRangeShots(Array<Shot> shots, boolean enemyShot){
		for(Shot shot : shots){
			if(enemyShot){
				if (shot.y + shot.height < 0)
		            toDeleteShots.add(shot);
			}
			else{
				if (shot.y > Sizes.DEFAULT_WORLD_HEIGHT)
					toDeleteShots.add(shot);
			}
		}
	}

	public void drawSprite(SpriteBatch batch, float ppux, float ppuy, final float offset) {
		super.drawSprite(batch, ppux, ppuy, offset, allShots);
	}

	public void drawShape(ShapeRenderer shapeRenderer, float ppux, float ppuy, final float offset) {
		shapeRenderer.setColor(Color.RED);
		super.drawShape(shapeRenderer, ppux, ppuy, offset, allShots);
		
		shapeRenderer.setColor(Color.YELLOW);
		super.drawShape(shapeRenderer, ppux, ppuy, offset, enemyShots);
		
	}


	public void addShipShots(Ship ship) {
		allShots.addAll(ship.getLeftShots());
		allShots.addAll(ship.getMiddleShots());
		allShots.addAll(ship.getRightShots());
	}


	public void cleanUpShots() {
		Helper.removeAll(enemyShots, toDeleteShots);
		Helper.removeAll(shotsBombs, toDeleteShots);
        Helper.removeAll(explosions, toDeleteExplosions);
	}


	public boolean isBombAround() {
		return shotsBombs.size == 0 && explosions.size == 0;
	}


	public void gotShipHit(Ship ship) {
		if (!ship.isInvincible()) {
			for (final Shot shot : enemyShots) {
				if (shot.overlaps(ship.getShipHitSpace())) {
					ship.gotHit((int) shot.getDamage());
					if (ship.getHealth() <= 0) {
						// gameOver(); //TODO
					}
					toDeleteShots.add(shot);
				}
			}
		}
	}
	
	public void destroyEnemyShots()
	{
	        for (Shot bomb : shotsBombs)
	            for (Shot enemyShot : enemyShots)
	            	if(bomb.overlaps(enemyShot))
	                    toDeleteShots.add(enemyShot);
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


	public void addEnemyShots(Array<Shot> shot) {
		enemyShots.addAll(shot);
	}


	public void expandExplosions() {
        for(final Explosion explosion : explosions)
        {
            if(explosion.expand())
            {
                toDeleteExplosions.add(explosion);
            }
        }		
	}


	public Array<Shot> getAllShots() {
		return allShots;
	}


	public Array<Shot> getShotsBombs() {
		return shotsBombs;
	}


	public Array<Explosion> getExplosions() {
		return explosions;
	}
}
