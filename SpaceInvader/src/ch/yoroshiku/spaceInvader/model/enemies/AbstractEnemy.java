package ch.yoroshiku.spaceInvader.model.enemies;

import java.util.Map;


import ch.yoroshiku.spaceInvader.model.AbstractGameObject;
import ch.yoroshiku.spaceInvader.model.Ship;
import ch.yoroshiku.spaceInvader.model.shot.Shot;
import ch.yoroshiku.spaceInvader.model.shot.ShotFactory;
import ch.yoroshiku.spaceInvader.util.Helper;
import ch.yoroshiku.spaceInvader.util.Sizes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public abstract class AbstractEnemy extends AbstractGameObject
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
    protected Array<Shot> emptyShotList = new Array<Shot>();
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

    abstract public Array<Shot> shoot(Ship ship);
    
    public Array<AbstractEnemy> getDestroyedEnemies()
    {
    	Array<AbstractEnemy> a = new Array<AbstractEnemy>();
    	a.add(this);
        return a;
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
        return createShot(x, y, Helper.createDirection(x, y, ship.x, ship.y));
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
	
	public void drawHealthBar(final ShapeRenderer shapeRenderer, final float ppux, final float ppuy, final float offset){

		//border
		shapeRenderer.setColor(Color.YELLOW);
		shapeRenderer.filledRect(offset + x * ppux, (Sizes.ENEMY_HEALTHBAR_DISTANCE + y + height) * ppuy - 1, 
				width * ppux, 1);
		shapeRenderer.filledRect(offset + x * ppux, (Sizes.HEALTHBAR_HEIGHT + Sizes.ENEMY_HEALTHBAR_DISTANCE + y + height) * ppuy, 
				width * ppux, 1);
		//Total Health
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.filledRect(offset + x * ppux, (Sizes.ENEMY_HEALTHBAR_DISTANCE + y + height) * ppuy, 
				width * ppux, Sizes.HEALTHBAR_HEIGHT * ppuy);
		//health remaining
		shapeRenderer.setColor(Color.GREEN);
		shapeRenderer.filledRect(offset + x * ppux, (Sizes.ENEMY_HEALTHBAR_DISTANCE + y + height) * ppuy, 
				(float)(width * ppux * health / maxHealth), Sizes.HEALTHBAR_HEIGHT * ppuy);
	}
	
	public void drawSprite(final SpriteBatch batch, final float ppuX, final float ppuY, final float border){
		batch.draw(getTexture(), border + x * ppuX, y * ppuY, ppuX * width, ppuY * height);
		if(getSubTextures() != null)
		{
			final Map<Rectangle, TextureRegion> map = getSubTextures();
			for(final Rectangle rect : map.keySet())
			{
				batch.draw(map.get(rect), border + rect.x * ppuX, rect.y * ppuY, rect.width * ppuX, rect.height * ppuY);
			}
		}
	}
	
	@Override
	public void drawShape(ShapeRenderer shapeRenderer, float ppux, float ppuy, float offset) {
		//dummy method
	}
	
}