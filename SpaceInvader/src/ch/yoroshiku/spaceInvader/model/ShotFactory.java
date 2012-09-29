package ch.yoroshiku.spaceInvader.model;



public class ShotFactory
{
    private static ShotFactory instance;
    private static float nHeight, nWidth;
    private static float fieldHeight, fieldWidth;
    private static float nYMovement;
    private static float splashEndPoint;

    private ShotFactory(float zoom, float fieldHeight, float fieldWidth)
    {
        nHeight = zoom * 10;
        nWidth = 2;
        this.fieldHeight = fieldHeight;
        this.fieldWidth = fieldWidth;
        splashEndPoint = fieldHeight - (170 * zoom);//TODO
    }
    
    public static void createInstance(float zoom, float fieldHeight, float fieldWidth)
    {
        new ShotFactory(zoom, fieldHeight, fieldWidth);
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
        return new ShotLaser(x,y, damage, movementX, movementY, nHeight, nWidth, true);
    }

    public static Shot createShotLaser(float x, float y, float movementY, boolean enemyShoot)
    {
        return new ShotLaser(x,y, 1, 0, movementY, nHeight, nWidth, enemyShoot);
    }

    public static Shot createShotLaser(float x, float y, float movementY)
    {
        return new ShotLaser(x,y, 1, 0.0f, movementY, nHeight, nWidth, true);
    }
    
    //SHOTS SPLASH
    public static Shot createShotSplash(float x, float y, int damage, int movementX, float position, int steps, boolean triple)
    {
        return new ShotSplash(x,y, damage, movementX, ((int) (splashEndPoint - position) / steps), nHeight, nWidth, true, steps, triple);
    }
    
    //SHOTS CIRCLE
    public static Shot createShotCircle(float x, float y, int movementY, boolean enemyShot, int width)
    {
		return new ShotCircle(x, y, 1, 0, movementY, width, enemyShot);
    }
}
