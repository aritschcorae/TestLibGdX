package ch.yoroshiku.spaceInvader.util;

import com.badlogic.gdx.utils.Array;



public class Helper {

	public static final Array<String> TEXTBOX_TEXT; 
	public static final Array<Float> TEXTBOX_SIZE; 
	public static final Integer TEXTBOX_INDEX_LEVEL = 0;
	public static final Integer TEXTBOX_INDEX_TIME = 1;
	public static final Integer TEXTBOX_INDEX_BONUS = 2;
	public static final Integer TEXTBOX_INDEX_TAP = 3;
	public static final Integer TEXTBOX_INDEX_GAME_OVER = 4;
	public static final Integer TEXTBOX_INDEX_SCORE = 5;
	public static final Integer TEXTBOX_INDEX_HIGHSCORE = 6;
	public static final Integer TEXTBOX_INDEX_PAUSE = 7;
	public static final Integer TEXTBOX_INDEX_GRATULATION = 8;
	public static final int BREAK_SIZE = 1;
	
	static {
		TEXTBOX_TEXT = new Array<String>();
		TEXTBOX_SIZE = new Array<Float>();
		TEXTBOX_TEXT.add("Level: ");
		TEXTBOX_SIZE.add(5f);
		TEXTBOX_TEXT.add("Time needed: ");
		TEXTBOX_SIZE.add(2.5f);
		TEXTBOX_TEXT.add("Level Bonus: ");
		TEXTBOX_SIZE.add(2.5f);
		TEXTBOX_TEXT.add("Tap for the next Level");
		TEXTBOX_SIZE.add(2f);
		TEXTBOX_TEXT.add("Game Over");
		TEXTBOX_SIZE.add(4f);
		TEXTBOX_TEXT.add("Score: ");
		TEXTBOX_SIZE.add(7f);
		TEXTBOX_TEXT.add("New Highscore!");
		TEXTBOX_SIZE.add(7f);
		TEXTBOX_TEXT.add("Pause");
		TEXTBOX_SIZE.add(6f);
		TEXTBOX_TEXT.add("Gratz");
		TEXTBOX_SIZE.add(8f);

		//	    <item>8;Continue?;24</item>
//	    <item>9;YES   |    NO;24</item>
//	    <item>10;Continues left: ;20</item>
	}
	
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
		for(T element : toRemove){
			base.removeValue(element, false);
		}
	}

}
