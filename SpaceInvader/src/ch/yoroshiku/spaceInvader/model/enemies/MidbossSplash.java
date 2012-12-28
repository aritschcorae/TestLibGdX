package ch.yoroshiku.spaceInvader.model.enemies;

import java.util.Random;



import ch.yoroshiku.spaceInvader.model.Ship;
import ch.yoroshiku.spaceInvader.model.shot.Shot;
import ch.yoroshiku.spaceInvader.model.shot.ShotFactory;
import ch.yoroshiku.spaceInvader.util.Enemies;
import ch.yoroshiku.spaceInvader.util.Sizes;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

public class MidbossSplash extends AbstractEnemy
{
	private static final long serialVersionUID = 1L;
    private Random random = new Random();
    
    public MidbossSplash(float x, float y, int shotFrequency, boolean powerUps)
    {
		super(x, y, Sizes.MIDSBOSS_SPLASS_WIDTH, Sizes.MIDSBOSS_SPLASS_HEIGHT,
				powerUps, Enemies.ALL_TEXTURES.get(Enemies.SPLASHER_ID));
        this.shootFrequency = shotFrequency;
    }
    
    @Override
    public boolean move(final float delta)
    {
        float yposition;
        float xposition;
        do
        {
            yposition = y + (random.nextFloat() - 0.5f) * 40 * delta;
        }
        while(yposition > Sizes.DEFAULT_WORLD_HEIGHT || yposition < Sizes.DEFAULT_WORLD_HEIGHT / 3);
        y = yposition;
        do
        {
            xposition = x + (random.nextFloat() - 0.5f) * 40 * delta;
        }
        while(xposition + width > Sizes.DEFAULT_WORLD_WIDTH || xposition < 1);
        x = xposition;
        return true;
    }
    
    @Override
    public Array<Shot> shoot(Ship ship)
    {
//        if (random.nextInt(50 / shootFrequency) == 0)
//        {
//        	Array<Shot> returnList = new Array<Shot>();
//            returnList.add(ShotFactory.createShotSplash(x + width / 2, y - height, 1, 0, y - height, true));
//            return returnList;
//        }
        return emptyShotList;
    }

	@Override
	public void drawShape(ShapeRenderer shapeRenderer, float ppux, float ppuy, float offset) {
		drawHealthBar(shapeRenderer, ppux, ppuy, offset);
	}
    
}
