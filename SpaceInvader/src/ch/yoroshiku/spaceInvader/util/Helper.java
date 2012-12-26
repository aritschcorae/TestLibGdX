package ch.yoroshiku.spaceInvader.util;

import com.badlogic.gdx.utils.Array;



public class Helper {

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

	public static <T> void removeAll(Array<T> base, Array<T> toRemove){
		for(T element : base){
			base.removeValue(element, false);
		}
	}

}
