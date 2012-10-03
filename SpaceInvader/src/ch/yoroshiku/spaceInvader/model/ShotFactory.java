package ch.yoroshiku.spaceInvader.model;

import ch.yoroshiku.spaceInvader.screen.GameScreen;
import ch.yoroshiku.spaceInvader.util.Sizes;



public class ShotFactory
{
    private static ShotFactory instance;
    private static float splashEndPoint;

    private ShotFactory()
    {
        splashEndPoint = GameScreen.DEFAULT_WORLD_HEIGHT - 40;
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
        return new ShotLaser(x, y, 1, movementX, movementY, height, width, true);
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
	public static Shot createShotSplash(float x, float y, int damage, int movementX, float position, int steps,
			boolean triple)
	{
		return new ShotSplash(x, y, damage, movementX, ((int) (splashEndPoint - position) / steps),
				Sizes.SHOT_LASER_HEIGHT, Sizes.SHOT_LASER_WIDTH, true, steps, triple);
	}

    //SHOTS CIRCLE
    public static Shot createShotCircle(float x, float y, int movementY, boolean enemyShot, int width)
    {
		return new ShotCircle(x, y, 1, 0, movementY, width, enemyShot);
    }
}
