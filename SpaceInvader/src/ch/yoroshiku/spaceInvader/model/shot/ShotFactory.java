package ch.yoroshiku.spaceInvader.model.shot;

import ch.yoroshiku.spaceInvader.util.Sizes;

import com.badlogic.gdx.graphics.Texture;



public class ShotFactory
{
    private static ShotFactory instance;
    private static final float splashEndPoint = Sizes.DEFAULT_WORLD_HEIGHT / 3;

    private ShotFactory()
    {//singleton
    }
    
    public static void createInstance()
    {
    	if(instance == null)
    		instance =  new ShotFactory();
    }
    
    public static ShotFactory getInstance()
    {
        return instance;
    }

    
    //SHOT LASER
    public static Shot createShotLaser(float x, float y, int damage, float movementX, float movementY, float height, float width)
    {
        return new ShotLaser(x, y, damage, movementX, movementY, height, width, true);
    }

    public static Shot createShotLaser(float x, float y, int damage, float movementX, float movementY)
    {
        return new ShotLaser(x,y, damage, movementX, movementY, Sizes.SHOT_LASER_HEIGHT, Sizes.SHOT_LASER_WIDTH, true);
    }

    public static Shot createShotLaser(float x, float y, float movementY, boolean enemyShoot)
    {
        return new ShotLaser(x,y, 1, 0, movementY, Sizes.SHOT_LASER_HEIGHT, Sizes.SHOT_LASER_WIDTH, enemyShoot);
    }

    public static Shot createShotLaser(float x, float y, float movementY)
    {
        return new ShotLaser(x,y, 1, 0.0f, movementY, Sizes.SHOT_LASER_HEIGHT, Sizes.SHOT_LASER_WIDTH, true);
    }
    
    //SHOTS SPLASH
	public static Shot createShotSplash(float x, float y, int damage, float movementX, float position,
			boolean triple)
	{
		return new ShotSplash(x, y, damage, movementX, -((position - splashEndPoint)),
				Sizes.SHOT_LASER_HEIGHT, Sizes.SHOT_LASER_WIDTH, true, triple, splashEndPoint);
	}

    //SHOTS CIRCLE
    public static Shot createShotCircle(float x, float y, float movementY, boolean enemyShot, int width)
    {
		return new ShotCircle(x, y, 1, 0, movementY, width, enemyShot);
    }
    
    //SHOTS BITMAP
    public static Shot createShotBitmap(Texture bitmap , float x, float y, int damage,
            float movementY, float acceleration, boolean enemyShoot)
    {
		return new ShotBitmap(bitmap, x, y, damage, 0, movementY, enemyShoot, acceleration);
    }
}
