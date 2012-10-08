package ch.yoroshiku.spaceInvader.model.enemies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import ch.yoroshiku.spaceInvader.model.Ship;
import ch.yoroshiku.spaceInvader.model.Shot;
import ch.yoroshiku.spaceInvader.model.ShotFactory;
import ch.yoroshiku.spaceInvader.util.Calculator;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public abstract class AbstractEnemy extends Rectangle
{
	private static final long serialVersionUID = 1L;
	protected double health = 1;
    protected double maxHealth;
    protected int shootFrequency = 1;
    protected boolean visible = true, showHealthBar = true;
    protected boolean invincible = false;
    protected int shotVelocity = -35;
    private double points = 1;
    private boolean powerUps = false;
    protected List<Shot> emptyShotList = new ArrayList<Shot>();
    protected TextureRegion texture;
    
    public AbstractEnemy(final float x, final float y, final float width, final float height, final boolean powerUps, final Texture texture)
    {
		super(x, y, width, height);
		this.texture = new TextureRegion(texture);
        this.powerUps = powerUps;
    }
    public AbstractEnemy(float x, float y, float width, float height, boolean powerUps, TextureRegion texture)
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
        return Collections.singletonList(this);
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
    
    protected Shot createShot(float xCoord, float yCoord, float movX)
    {
        return ShotFactory.createShotLaser(xCoord, yCoord, 1, movX, shotVelocity);
    }
    
    protected Shot createAimingShot(Ship ship, float x, float y)
    {
        return createShot(x, y, Calculator.createDirection(x, y, ship.x, ship.y));
    }
    
    public Map<Rectangle, TextureRegion> getSubTextures()
    {
    	return null;
    }

	public TextureRegion getTexture()
	{
		return texture;
	}

	public boolean isShowHealthBar()
	{
		return showHealthBar;
	}

	public double getMaxHealth()
	{
		return maxHealth;
	}
}