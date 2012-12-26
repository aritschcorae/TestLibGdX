package ch.yoroshiku.spaceInvader.model.enemies;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import ch.yoroshiku.spaceInvader.model.Ship;
import ch.yoroshiku.spaceInvader.model.shot.Shot;
import ch.yoroshiku.spaceInvader.util.Helper;
import ch.yoroshiku.spaceInvader.util.Enemies;
import ch.yoroshiku.spaceInvader.util.Sizes;
import ch.yoroshiku.spaceInvader.util.Textures;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class PrometheusWing extends AbstractEnemy
{
	private static final long serialVersionUID = 1L;
	private float[] yCoordinates = new float[3], xCoordinates = new float[3];
    private float[] yExplosionCoordinates, xExplosionCoordinates;
    private boolean leftWing = false, halfHealth = false, destoryed = false;
    private double startHealth;
    private Random random = new Random();
    private Prometheus daddy;
    
    private int explosionStep = 0;
    private boolean exploded = false;
    private int explosionHeight;
    private Map<Rectangle, TextureRegion> explosions;
    private Array<Rectangle> explosionsRectanles;
    
    public PrometheusWing(float x, float y, boolean powerUps, int leftWing)// 1 left, 2 right
    {
		super(x, y, Sizes.WING_WIDTH, Sizes.WING_HEIGHT, powerUps, 
				new TextureRegion(Enemies.ALL_TEXTURES.get(Enemies.PROMETHEUS_ID + leftWing), 
						Enemies.ALL_TEXTURES.get(Enemies.PROMETHEUS_ID + leftWing).getWidth(),
						Enemies.ALL_TEXTURES.get(Enemies.PROMETHEUS_ID + leftWing).getHeight() / 2));
		
		// barHeight = 13;
		this.leftWing = leftWing == 1;
		if (this.leftWing)
		{
			yCoordinates = new float[] { 5.6f, 4.6f, 3.4f };
			xCoordinates = new float[] { 2.8f, 5, 7.2f };
			yExplosionCoordinates = new float[] { 5, 0.4f, 1 };
			xExplosionCoordinates = new float[] { 5.2f, 0.6f, 4.4f };
		} else
		{
			yCoordinates = new float[] { 3.4f, 4.6f, 5.6f };
			xCoordinates = new float[] { 2.6f, 4.8f, 7 };
			yExplosionCoordinates = new float[] { 4, 0.4f, 1 };
			xExplosionCoordinates = new float[] { 0.8f, 5.4f, 1.6f };
		}
        shootFrequency = 3;
        explosions = new HashMap<Rectangle, TextureRegion>();
        explosions.put(new Rectangle(0, 0, 4, 4), new TextureRegion(Textures.EXPLOSION_TEXTURE));
        explosions.put(new Rectangle(0, 0, 4, 4), new TextureRegion(Textures.EXPLOSION_TEXTURE));
        explosions.put(new Rectangle(0, 0, 4, 4), new TextureRegion(Textures.EXPLOSION_TEXTURE));
        explosionsRectanles = new Array<Rectangle>();
        for(Rectangle rect : explosions.keySet())
        {
        	explosionsRectanles.add(rect);
        }
    }
    
    private final float explosionDuration = 2.4f;
    @Override
    public boolean move(float delta)
    {
        //got from center part (daddy)
    	if(destoryed && !exploded)
    	{
    		//TODO slower explosion
    		//TODO delay of exp 2 and 3
    		int i = 0;
    		for(Rectangle rect : explosionsRectanles)
    		{
    			rect.set(x + xExplosionCoordinates[i], y + yExplosionCoordinates[i], 
    					Sizes.DESTROY_EXPLOSION_RADIUS, Sizes.DESTROY_EXPLOSION_RADIUS);
    			i ++;
    			explosions.get(rect).setRegion(0, explosionStep * Textures.EXPLOSION_TEXTURE.getHeight() / 6, 
    					Textures.EXPLOSION_TEXTURE.getWidth(), Textures.EXPLOSION_TEXTURE.getHeight() / 6);
    		}
            explosionStep++;
            if (explosionStep == 6)
                exploded = true;
    	}
    	return true;
    }
    
    @Override
    public Array<Shot> shoot(Ship ship)
    {
        if(!exploded
            && ((!leftWing && x - width <= ship.x)
            || (leftWing && x + (2*width) > ship.x)))
        {
            if (random.nextInt(20 / shootFrequency) == 4)
            {
                return createShootList(ship);
            }
        }
        return emptyShotList;
    }

    private Array<Shot> createShootList(Ship ship)
    {
        float xMovement = Helper.createDirection(x + xCoordinates[0], y + yCoordinates[0], ship.x, ship.y);
        Array<Shot> returnList = new Array<Shot>();
        returnList.add(createShot(x + xCoordinates[0], y + yCoordinates[0], xMovement));
        returnList.add(createShot(x + xCoordinates[1], y + yCoordinates[1], xMovement));
        returnList.add(createShot(x + xCoordinates[2], y + yCoordinates[2], xMovement));
        return returnList;
    }

    @Override
    public boolean lowerHealth(double lowerHealth)
    {
        super.lowerHealth(lowerHealth);
        if(destoryed)
        {
            health = 1;
            daddy.lowerHealthWing(lowerHealth);
        }
        else if(!halfHealth && (startHealth / 2) - health > 0)
        {
        	texture.setRegion(0, 40, texture.getRegionWidth(), texture.getRegionHeight());
            halfHealth = true;
            shotVelocity = -40;
            shootFrequency = 4;
        }
        else if(health <= 0)
        {
            if (leftWing)
                daddy.killWing(true);
            else
                daddy.killWing(false);
            destoryed = true;
            health = 1;
        }
        return false;
    }
    
    public void setDaddy(Prometheus daddy)
    {
        this.daddy = daddy;
    }

    public void setStartHealth(double startHealth)
    {
        this.startHealth = startHealth;
    }

    @Override
    public boolean bombDamage(double lowerHealth)
    {
        return false;
    }
    
    @Override
    public Map<Rectangle, TextureRegion> getSubTextures()
    {
    	if(destoryed && !exploded)
    	{
    		return explosions;
    	}
    	return null;
    }

	@Override
	public void drawShape(ShapeRenderer shapeRenderer, float ppux, float ppuy, float offset) {
		drawHealthBar(shapeRenderer, ppux, ppuy, offset);
	}
}