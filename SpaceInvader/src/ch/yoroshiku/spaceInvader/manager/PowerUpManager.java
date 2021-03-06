package ch.yoroshiku.spaceInvader.manager;

import ch.yoroshiku.spaceInvader.model.PowerUp;
import ch.yoroshiku.spaceInvader.model.Ship;
import ch.yoroshiku.spaceInvader.util.Helper;
import ch.yoroshiku.spaceInvader.util.Sizes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;



public class PowerUpManager extends Manager {

	private Array<PowerUp> powerUps;
	
	public PowerUpManager(){
		powerUps = new Array<PowerUp>();
	}
	

	public void move(float delta) {
        final Array<PowerUp> toRemovePowerUps = new Array<PowerUp>();
        for (final PowerUp powerUp : powerUps)
        {
            powerUp.move(delta);
            if (powerUp.y < 0 || powerUp.isEated())
                toRemovePowerUps.add(powerUp);
        }
        Helper.removeAll(powerUps, toRemovePowerUps);
	}

	public void drawSprite(SpriteBatch batch, float ppux, float ppuy, final float offset) {
		super.drawSprite(batch, ppux, ppuy, offset, powerUps);
	}

	public void drawShape(ShapeRenderer shapeRenderer, float ppux, float ppuy, final float offset) {
	}


	public void obtainedPowerUp(Ship ship) {
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


	public void createHealthPowerUp() {
		powerUps.add(new PowerUp(Sizes.DEFAULT_WORLD_WIDTH / 2, Sizes.DEFAULT_WORLD_HEIGHT / 3 * 2, PowerUp.POWER_UP_HEAL));
	}
}
