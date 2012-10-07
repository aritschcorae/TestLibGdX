package ch.yoroshiku.spaceInvader.util;



public class Calculator {

	private static float DEFAULT_SHOT_VELOCITY = -35;
    /**
     * @param xCoord origin
     * @param yCoord origin
     * @param targetX
     * @param targetY
     * @return xMovement of the shot needed to hit the ship
     */
    public static float createDirection(float xCoord, float yCoord, float shipX, float shipY)
    {
        return ((shipX - xCoord) / ((shipY - yCoord) / DEFAULT_SHOT_VELOCITY));
    }
    
}
