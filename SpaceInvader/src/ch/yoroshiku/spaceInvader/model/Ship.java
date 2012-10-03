package ch.yoroshiku.spaceInvader.model;

import java.util.ArrayList;
import java.util.List;

import ch.yoroshiku.spaceInvader.screen.GameScreen;
import ch.yoroshiku.spaceInvader.util.Sizes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


/**
 * @author Rofus
 * {@link ShipStraight}
 * {@link ShipCircle}
 */
public abstract class Ship extends Rectangle
{
	private static final long serialVersionUID = 1L;
	protected Integer powerUpTypeInUse = null;
    private Color powerUpColor;
    protected List<Shot> leftShots = new ArrayList<Shot>();
    protected List<Shot> middleShots = new ArrayList<Shot>();
    protected List<Shot> rightShots = new ArrayList<Shot>();
    protected float speed, centerX, centerY;
    private int shield = 1, health = 3;
    protected int  maxHealth = 3;
    protected int damage = 1, shots = 3, bombs = 2;
    protected int maxShots;
    private int direction = 0;
    private boolean invincible = false;
    protected float shotLeft, shotMiddle, shotRight;
    protected boolean spray = false;
    protected int currentShot = 0;
    private boolean slowedDown = false, defensless = false, bombless = false, illness = false, position = true;
    private boolean powerupDamage = false, powerupSpeed = false;
	protected TextureRegion shipTexture;
	private Rectangle shipHitSpace, shipPowerUpReach;
    
    public Ship(float x, float y, Texture texture)
    {
		super(x, y, Sizes.SHIP_WIDTH, Sizes.SHIP_HEIGHT);
		shipTexture = new TextureRegion(texture, texture.getHeight() / 3, texture.getWidth() / 3);
        currentShot = 0;
        shipHitSpace = new Rectangle();
		shipHitSpace.width = Sizes.SHIP_HIT_EVADE_WIDTH;
		shipHitSpace.height = Sizes.SHIP_HIT_EVADE_WIDTH;
        shipPowerUpReach = new Rectangle();
        shipPowerUpReach.width = Sizes.SHIP_POWERUP_REACH_BOOST_WIDTH;
        shipPowerUpReach.height = Sizes.SHIP_POWERUP_REACH_BOOST_WIDTH;
    }
    
    public void resetShip(Vector2 coord, float zoom)
    {
        health = 3;
        shield = 1;
        if(bombs < 2)
        {
            bombs = 2;
        }
    }
    
    public int getHealth()
    {
        return health;
    }

    public void addPowerUp(final PowerUp powerUp)
    {
        if (powerUp.getType().equals(PowerUp.POWER_UP_DAMAGE)
                || powerUp.getType().equals(PowerUp.POWER_UP_SPEED))
        {
            handleSinglePowerUp(powerUp);
        }
        else if (powerUp.getType().equals(PowerUp.POWER_UP_SHIELD))
        {
            if (defensless)
                tempShield ++;
            else
                shield++;
        }
        else if (powerUp.getType().equals(PowerUp.POWER_UP_SHOTS))
        {
            handleShotPowerup();
        }
        else if (powerUp.getType().equals(PowerUp.POWER_UP_HEAL))
        {
            health = maxHealth;
        }
        else if (powerUp.getType().equals(PowerUp.POWER_UP_BOMB))
        {
            if(bombless)
                tempBombs ++;
            else
                bombs ++;
        }
    }
    
    private void handleSinglePowerUp(PowerUp powerUp)
    {
        if(powerUp == null)
            return;
        if(powerUpTypeInUse != null)
        {
            removePowerUpInUse();
        }
        if(powerUp.getType() == PowerUp.POWER_UP_DAMAGE)
            powerupDamage = true;
        if(powerUp.getType() == PowerUp.POWER_UP_SPEED)
            powerupSpeed = true;
        
        powerUpTypeInUse = powerUp.getType();
        addPowerUpPower();
    }
    
    private void addPowerUpPower()
    {
        if(powerUpTypeInUse.equals(PowerUp.POWER_UP_DAMAGE))
        {
            damage *= 2;
        }
        else if (powerUpTypeInUse.equals(PowerUp.POWER_UP_SPEED))
        {
            speed += PowerUp.SPEED_POWER_UP_VALUE;
        }
        setPowerUpColor();
    }
    
    private void setPowerUpColor()
    {
        if(powerUpTypeInUse.equals(PowerUp.POWER_UP_DAMAGE))
        {
            powerUpColor = Color.GREEN;
        }
        else if (powerUpTypeInUse.equals(PowerUp.POWER_UP_SPEED))
        {
            powerUpColor = Color.YELLOW;
        }
    }
    
    private void removePowerUpInUse()
    {
        if(powerUpTypeInUse.equals(PowerUp.POWER_UP_DAMAGE))
        {
            deactivateDamagePowerUp();
            powerupDamage = false;
        }
        else if (powerUpTypeInUse.equals(PowerUp.POWER_UP_SPEED))
        {
            deactivateSpeedPowerUp();
            powerupSpeed = false;
        }
    }

    private void deactivateDamagePowerUp()
    {
        if(powerupDamage)
        {
            damage /= 2;
            powerUpTypeInUse = null;
        }
    }

    private void deactivateSpeedPowerUp()
    {
        if(powerupSpeed)
        {
            speed -= PowerUp.SPEED_POWER_UP_VALUE;
            powerUpTypeInUse = null;
        }
    }
    
    public Integer getPowerUpTypeInUse()
    {
        return powerUpTypeInUse;
    }
    
    protected void handleShotPowerup()
    {
        if (shots < maxShots)
            shots++;
        else if(!spray)
            spray = true;
        else
            shield ++; //TODO max power up
    }

    public boolean isSpray()
    {
        return spray;
    }

    public float getSpeed()
    {
        return speed;
    }

    public int getShield()
    {
        return shield;
    }

    public void gotHit(final int damage)
    {
        if(powerUpTypeInUse != null)
        {
            removePowerUpInUse();
        }
        else if(shield > 0)
        {
            this.shield -= damage;
        }
        else
        {
            health -= damage;
        }
        if(shield < 0)
        {
            health += shield;
            shield = 0;
        }
    }

    public int getDamage()
    {
        return damage;
    }

    public int getBombs()
    {
        return bombs;
    }

    public void setBombs(int bombs)
    {
        this.bombs = bombs;
    }

    public void dropBombs()
    {
        bombs --;
    }

    public int getShots()
    {
        return shots;
    }
    
    public int getMaxShots()
    {
        return maxShots;
    }

    public void setShield(int shield)
    {
        this.shield = shield;
    }

    public void setSpeed(int speed)
    {
        this.speed = speed;
    }

    public void setHealth(int health)
    {

        this.health = health;
    }

    abstract public boolean isMaxShots();
    
    abstract public void setSettingsByDifficulty(int difficulty);

    public void setInvincible(boolean invincible)
    {
        this.invincible = invincible;
    }

    public boolean isInvincible()
    {
        return invincible;
    }

    abstract public List<Shot> getLeftShots();
    abstract public List<Shot> getMiddleShots();
    abstract public List<Shot> getRightShots();
    abstract public int getShotCoolDown();
    abstract public void shoot(boolean spray);

//    public void reload(Map<String, Integer> gameStats)
//    {
//        damage = gameStats.get(GameStateSave.MAP_KEY_WORD_SHIP_DAMAGE);
//        health = gameStats.get(GameStateSave.MAP_KEY_WORD_SHIP_HEALTH);
//        shield = gameStats.get(GameStateSave.MAP_KEY_WORD_SHIP_SHIELD);
//        shots = gameStats.get(GameStateSave.MAP_KEY_WORD_SHIP_SHOTS);
//        speed = gameStats.get(GameStateSave.MAP_KEY_WORD_SHIP_SPEED);
//        bombs = gameStats.get(GameStateSave.MAP_KEY_WORD_SHIP_BOMB);
//        powerupDamage = gameStats.get(GameStateSave.MAP_KEY_WORD_POWER_UP_DAMAGE) == 1;
//        powerupSpeed = gameStats.get(GameStateSave.MAP_KEY_WORD_POWER_UP_SPEED) == 1;
//        powerUpTypeInUse = gameStats.get(GameStateSave.MAP_KEY_WORD_POWER_UP_USED) == -1 ? null : gameStats
//                .get(GameStateSave.MAP_KEY_WORD_POWER_UP_USED);
//        setPowerUpColor();
//    }
//
//    public Map<String, Integer> save(Map<String, Integer> stats)
//    {
//        stats.put(GameStateSave.MAP_KEY_WORD_SHIP_DAMAGE, damage);
//        stats.put(GameStateSave.MAP_KEY_WORD_SHIP_HEALTH, health);
//        stats.put(GameStateSave.MAP_KEY_WORD_SHIP_SHIELD, shield);
//        stats.put(GameStateSave.MAP_KEY_WORD_SHIP_SHOTS, shots);
//        stats.put(GameStateSave.MAP_KEY_WORD_SHIP_SPEED, speed);
//        stats.put(GameStateSave.MAP_KEY_WORD_SHIP_BOMB, bombs);
//        stats.put(GameStateSave.MAP_KEY_WORD_POWER_UP_DAMAGE, powerupDamage ? 1 : 0);
//        stats.put(GameStateSave.MAP_KEY_WORD_POWER_UP_SPEED, powerupSpeed ? 1 : 0);
//        stats.put(GameStateSave.MAP_KEY_WORD_POWER_UP_USED, powerUpTypeInUse == null ? -1 : powerUpTypeInUse);
//        return stats;
//    }

    private float tempSpeed = 0;
    public void slowDownShip(boolean slowDown)
    {
        if (slowDown && !slowedDown)
        {
            if(powerUpTypeInUse != null && powerUpTypeInUse.equals(PowerUp.POWER_UP_SPEED))
                tempSpeed = speed - PowerUp.SPEED_POWER_UP_VALUE;
            else
                tempSpeed = speed;
            speed /= 2;
            illness = true;
        }
        else
        {
            if(powerUpTypeInUse != null && powerUpTypeInUse.equals(PowerUp.POWER_UP_SPEED))
                speed = tempSpeed + PowerUp.SPEED_POWER_UP_VALUE;
            else
                speed = tempSpeed;
            illness = false;
        }
        slowedDown = slowDown;
    }

    private int tempShield = 0;
    public void removeShipShield(boolean deactivateShield)
    {
        if (deactivateShield && !defensless)
        {
            tempShield = shield;
            shield = 0;
            illness = true;
        }
        else
        {
            shield = tempShield;
            tempShield = 0;
            illness = false;
        }
        defensless = deactivateShield;
    }
    
    private int tempBombs = 0;
    public void deactivateBombs(boolean deactivateBomb)
    {
        if(deactivateBomb && !bombless)
        {
            tempBombs = bombs;
            bombs = 0;
            illness = true;
        }
        else
        {
            bombs = tempBombs;
            tempBombs = 0;
            illness = false;
        }
            
        bombless = deactivateBomb;
    }

    public void setSpeedPowerUp()
    {
        if(powerupSpeed)
        {
            if(powerUpTypeInUse != null && powerUpTypeInUse.equals(PowerUp.POWER_UP_SPEED))
            {
                deactivateSpeedPowerUp();
            }
            else
            {
                deactivateDamagePowerUp();
                powerUpTypeInUse = PowerUp.POWER_UP_SPEED;
                addPowerUpPower();
            }
        }
    }

    public void setDamagePowerUp()
    {
        if(powerupDamage)
        {
            if(powerUpTypeInUse != null && powerUpTypeInUse.equals(PowerUp.POWER_UP_DAMAGE))
            {
                deactivateDamagePowerUp();
            }
            else
            {
                deactivateSpeedPowerUp();
                powerUpTypeInUse = PowerUp.POWER_UP_DAMAGE;
                addPowerUpPower();
            }
        }
    }
    
    public void setMovingLeft()
    {
    	shipTexture.setRegion((maxHealth - health) * 28, 0, 28, 28);
    }
    
    public void setMovingRight()
    {
    	shipTexture.setRegion((maxHealth - health) * 28, 56, 28, 28);
    }
    
    public void setMovingStop()
    {
    	shipTexture.setRegion((maxHealth - health) * 28, 28, 28, 28);
    }
    
    public void moveLeft(float delta){
    	x -= speed * delta;
    	if(x < 0)
    		x = 0;
    }
    
    public void moveRight(float delta){
    	x += speed * delta;
    	if(x > GameScreen.DEFAULT_WORLD_WIDTH - Sizes.SHIP_WIDTH)
    		x = GameScreen.DEFAULT_WORLD_WIDTH - Sizes.SHIP_WIDTH;
    }

	public TextureRegion getShipTexture() {
		return shipTexture;
	}

	public Rectangle getShipHitSpace()
	{
		shipHitSpace.x = x + Sizes.SHIP_HIT_EVADE;
		shipHitSpace.y = y + Sizes.SHIP_HIT_EVADE;
		return shipHitSpace;
	}

	public Rectangle getShipPowerUpReach()
	{
		shipPowerUpReach.x = x + Sizes.SHIP_POWERUP_REACH_BOOST;
		shipPowerUpReach.y = y + Sizes.SHIP_POWERUP_REACH_BOOST;
		return shipPowerUpReach;
	}
}