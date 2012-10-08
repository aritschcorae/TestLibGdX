package ch.yoroshiku.spaceInvader.model.enemies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import ch.yoroshiku.spaceInvader.model.Ship;
import ch.yoroshiku.spaceInvader.model.Shot;
import ch.yoroshiku.spaceInvader.model.ShotFactory;
import ch.yoroshiku.spaceInvader.util.Enemies;
import ch.yoroshiku.spaceInvader.util.Sizes;
import ch.yoroshiku.spaceInvader.util.Textures;

public class Prometheus extends AbstractEnemy
{
	private static final long serialVersionUID = 1L;
	private Random random = new Random();
    private PrometheusWing leftWing;
    private PrometheusWing rightWing;
    private float xMovement = 0;
    private float directiona = 1;
    private float duration = 0.75f;
    private boolean leftWingAlive = true, rightWingAlive = true;
    private boolean alive = true, exploded = false;
    private float[] xExplosion, yExplosion;
    private int explosionHeight, explosionStep;
    private int circleX, circleY, circleRadius;
    private Map<Rectangle, TextureRegion> explosions;
    private List<Rectangle> explosionsRectanles;

    public Prometheus(float x, float y, boolean powerUps, PrometheusWing leftWing,
            PrometheusWing rightWing, int health, int points)
    {
		super(x, y, Sizes.PROMETHEUS_WIDTH, Sizes.PROMETHEUS_HEIGHT, powerUps, Enemies.ALL_TEXTURES.get(Enemies.PROMETHEUS_ID));
        this.leftWing = leftWing;
        this.rightWing = rightWing;
        this.leftWing.setDaddy(this);
        this.rightWing.setDaddy(this);
        setHealth(health);
        leftWing.setHealth(health * 0.8);
        rightWing.setHealth(health * 0.8);
        leftWing.setStartHealth(health * 0.8);
        rightWing.setStartHealth(health * 0.8);
        setPoints(points);
        leftWing.setPoints(points * 0.8);
        rightWing.setPoints(points * 0.8);
        barHeight = 13;
        shotVelocity = -45;
        circleX = (int) (5);
        circleY = (int) (7);
        circleRadius = (int) (6);
        xExplosion = new float[] { 6, 4.6f, 2.2f, 6.4f };
        yExplosion = new float[] { 9.2f, 3.4f, 6,6f, 3.2f};

        explosions = new HashMap<Rectangle, TextureRegion>();
        explosions.put(new Rectangle(0, 0, 4, 4), new TextureRegion(Textures.EXPLOSION_TEXTURE));
        explosions.put(new Rectangle(0, 0, 4, 4), new TextureRegion(Textures.EXPLOSION_TEXTURE));
        explosions.put(new Rectangle(0, 0, 4, 4), new TextureRegion(Textures.EXPLOSION_TEXTURE));
        explosionsRectanles = new ArrayList<Rectangle>();
        for(Rectangle rect : explosions.keySet())
        {
        	explosionsRectanles.add(rect);
        }
    }
    
    @Override
    public void move(float delta)
    {
    	if(!alive && !exploded)
    	{
    		//TODO slower explosion
    		//TODO delay of exp 2 and 3
    		int i = 0;
    		for(Rectangle rect : explosionsRectanles)
    		{
    			rect.set(x + xExplosion[i], y + yExplosion[i], 
    					Sizes.DESTROY_EXPLOSION_RADIUS, Sizes.DESTROY_EXPLOSION_RADIUS);
    			i ++;
    			explosions.get(rect).setRegion(0, explosionStep * Textures.EXPLOSION_TEXTURE.getHeight() / 6, 
    					Textures.EXPLOSION_TEXTURE.getWidth(), Textures.EXPLOSION_TEXTURE.getHeight() / 6);
    		}
            explosionStep++;
            if(explosionStep == 30)
            exploded = true;
    	}
    	else
    	{
        	xMovement = 5 * delta * directiona;
        	duration -= delta;
            if(duration < 0)
            {
            	directiona *= -1;
                duration = 1.5f;
            }
            x += xMovement;
            leftWing.x += xMovement;
            rightWing.x += xMovement;
    	}
    }
    
    @Override
    public List<Shot> shoot(Ship ship)
    {
        if(alive)
        {
            if (random.nextInt(60) == 10)
            {
                List<Shot> returnList = new ArrayList<Shot>();
                returnList.add(ShotFactory.createShotLaser(x + 4, y + 6.2f, 2, 0, shotVelocity, 6, 2));
                return returnList;
            }
            if (random.nextInt(60) == 10)
            {
                List<Shot> returnList = new ArrayList<Shot>();
                returnList.add(ShotFactory.createShotLaser(x + 4, y + 7.4f, 2, 0, shotVelocity, 4, 2));
                return returnList;
            }
        }
        return emptyShotList;
    }

    
    @Override
    public boolean lowerHealth(double lowerHealth)
    {
        if(exploded)
            return true;
        else if(leftWingAlive && rightWingAlive)
            return false;
        else if(leftWingAlive)
        {
            lowerHealth /= 2;
        }
        else if(rightWingAlive)
        {
            lowerHealth /= 2;
        }
        super.lowerHealth(lowerHealth);
        if (health <= 0)
        {
            leftWing.setHealth(0);
            rightWing.setHealth(0);
            health = 1;
            alive = false;
        }
        return false;
    }
    
    @Override
    public boolean bombDamage(double lowerHealth)
    {
        health += lowerHealth;
        if(maxHealth < health)
            health = maxHealth;
        return false;
    }
    
    @Override
    public List<AbstractEnemy> getDestroyedEnemies()
    {
        List<AbstractEnemy> destroyedEnemies = new ArrayList<AbstractEnemy>();
        destroyedEnemies.add(this);
        destroyedEnemies.add(leftWing);
        destroyedEnemies.add(rightWing);
        return destroyedEnemies;
    }

    public void killWing(boolean rightWing)
    {
        if(rightWing)
            rightWingAlive = false;
        else
            leftWingAlive = false;
        if(!leftWingAlive && !rightWingAlive)
        	;
    }

    public void lowerHealthWing(double lowerHealth)
    {
        if(!leftWingAlive && !rightWingAlive)
            health = health - lowerHealth / 2;
    }
    
    @Override
    public Map<Rectangle, TextureRegion> getSubTextures()
    {
    	if(!alive && !exploded)
    	{
    		return explosions;
    	}
    	return null;
    }

}