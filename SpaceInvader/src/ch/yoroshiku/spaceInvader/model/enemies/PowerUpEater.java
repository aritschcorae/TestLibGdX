package ch.yoroshiku.spaceInvader.model.enemies;

import java.util.List;

import ch.yoroshiku.spaceInvader.model.PowerUp;
import ch.yoroshiku.spaceInvader.model.Ship;
import ch.yoroshiku.spaceInvader.model.Shot;
import ch.yoroshiku.spaceInvader.util.Enemies;
import ch.yoroshiku.spaceInvader.util.Sizes;

public class PowerUpEater extends AbstractEnemy
{
	private static final long serialVersionUID = 1L;
	private PowerUp eatingPowerUp;
    private boolean powerUpGetting = false, powerUpDocked = false, powerUpEated = false;
    private float powerUpMoveX = 0, powerUpMoveY = 0;
    private long timeEated;
    private float powerUpXDestination;
    
    public PowerUpEater(final float x, final float y, final boolean powerUps)
    {
		super(x, y, Sizes.POWER_UP_EATER_WIDTH, Sizes.POWER_UP_EATER_HEIGHT, powerUps, Enemies.ALL_TEXTURES.get(Enemies.POWER_UP_EATER_ID));
        visible = false;
        invincible = true;
        width /= 2;
        health = 5;
        powerUpXDestination = x + 1;
    }
    
    /**
     * @param powerUp
     * @return true if powerUp will be eaten
     */
    public boolean eatPowerUp(final PowerUp powerUp)
    {
        if(eatingPowerUp == null)
        {
            eatingPowerUp = powerUp;
            visible = true;
            invincible = false;
            return true;
        }
        return false;
    }
    
    @Override
    public void move(final float delta)//TODO
    {
        if(eatingPowerUp != null)
        {
            if(powerUpGetting)
            {
                if(!powerUpDocked)
                {
                	eatingPowerUp.x += powerUpMoveX;
                	eatingPowerUp.y += powerUpMoveY;
                }
                if(eatingPowerUp.y <= y + height)
                {
                    powerUpDocked = true;
                    eatingPowerUp.x = powerUpXDestination;
                    eatingPowerUp.y = y + height - eatingPowerUp.getHeight();
                    timeEated = System.currentTimeMillis(); //TODO
                    powerUpGetting = false;
                }
            } 
            else if(eatingPowerUp.y < y - height)
            {
                powerUpGetting = true;//in 6 rounds the the power should be docked
                powerUpMoveY =  (eatingPowerUp.y - y) / 6;
                powerUpMoveX = (powerUpXDestination - eatingPowerUp.x) / 6;
                eatingPowerUp.stop();
            }
        }
    }
    
    @Override
    public boolean lowerHealth(double lowerHealth)
    {
        health --;
        if(health <= 0)
        {
            releasePowerUp();
            return true;
        }
        return false;
    }
    
    public void releasePowerUp()
    {
        eatingPowerUp.setVelocity();
    }

    @Override
    public List<Shot> shoot(Ship ship)
    {
        if(powerUpDocked)
        {
            if(System.currentTimeMillis() - timeEated >= 6000)
            {
                powerUpEated = true;
                powerUpDocked = false;
            }
        }
        else if(powerUpEated)
        {
            //if time over destroy powerup
            eatingPowerUp.setEated(true);
        }
        return emptyShotList;
    }
    
}
