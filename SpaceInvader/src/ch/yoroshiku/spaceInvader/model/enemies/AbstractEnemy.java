package ch.yoroshiku.spaceInvader.model.enemies;

import java.util.ArrayList;
import java.util.List;

import ch.yoroshiku.spaceInvader.model.Ship;
import ch.yoroshiku.spaceInvader.model.Shot;
import ch.yoroshiku.spaceInvader.model.ShotFactory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public abstract class AbstractEnemy extends Rectangle
{
	private static final long serialVersionUID = 1L;
	protected double health = 1;
    protected double maxHealth;
    protected int shootFrequency = 1;
    protected boolean visible = true;
    protected boolean invincible = false;
    protected int shotVelocity = -40;
    private double points = 1;
    private boolean powerUps = false;
    protected List<Shot> emptyShotList = new ArrayList<Shot>();
    protected Texture texture;
    
    public AbstractEnemy(float x, float y, float width, float height, boolean powerUps, Texture texture)
    {
		super(x, y, width, height);
		this.texture = texture;
        this.powerUps = powerUps;
    }
    
    public double getHealth()
    {
        return health;
    }

    public double getPoints()
    {
        return points;
    }

    public boolean getPowerUps()
    {
        return powerUps;
    }

    public void setPoints(double points)
    {
        this.points = points;
    }

    public void setHealth(double health)
    {
        maxHealth = health;
        this.health = health;
    }

    public boolean hasPowerUp()
    {
        return powerUps;
    }
    
    public void setShotFrequency(int shootFrequency)
    {
        this.shootFrequency = shootFrequency;
    }

    public boolean isVisible()
    {
        return visible;
    }

    public boolean isInvincible()
    {
        return invincible;
    }

    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }

    public void setInvincible(boolean invincible)
    {
        this.invincible = invincible;
    }

    abstract public void move(float delta);
    
    abstract public List<Shot> shoot(Ship ship);
    
    public List<AbstractEnemy> getDestroyedEnemies()
    {
        List<AbstractEnemy> destroyedEnemies = new ArrayList<AbstractEnemy>();
        destroyedEnemies.add(this);
        return destroyedEnemies;
    }
    
    /**
     * Is called when got hit.
     * @param lowerHealth
     * @return true if health lowered
     */
    public boolean lowerHealth(double lowerHealth)
    {
        if(!invincible)
        {
            health -= lowerHealth;
            return health <= 0;
        }
        return false;
    }
    
    public boolean bombDamage(double lowerHealth)
    {
        return lowerHealth(lowerHealth);
    }
    
    protected int barHeight = 11;

    public void addPowerUp()
    {
        powerUps = true;
    }
    
    /**
     * @param xCoord
     * @param yCoord
     * @param shipX
     * @param shipY
     * @return xMovement of the shot needed to hit the ship
     */
    protected float createDirection(float xCoord, float yCoord, float shipX, float shipY)
    {
        return ((shipX - xCoord) / ((shipY - yCoord) / shotVelocity));
    }
    
    protected Shot createShot(float xCoord, float yCoord, float movX)
    {
        return ShotFactory.createShotLaser(xCoord, yCoord, 1, movX, shotVelocity);
    }
    
    protected Shot createAimingShot(Ship ship, float x, float y)
    {
        return createShot(x, y, createDirection(x, y, ship.x, ship.y));
    }

	public Texture getTexture() {
		return texture;
	}
}