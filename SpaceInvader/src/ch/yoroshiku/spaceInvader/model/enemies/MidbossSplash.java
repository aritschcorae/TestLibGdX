package ch.yoroshiku.spaceInvader.model.enemies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ch.yoroshiku.spaceInvader.model.Ship;
import ch.yoroshiku.spaceInvader.model.Shot;
import ch.yoroshiku.spaceInvader.model.ShotFactory;
import ch.yoroshiku.spaceInvader.screen.GameScreen;
import ch.yoroshiku.spaceInvader.util.Enemies;
import ch.yoroshiku.spaceInvader.util.Sizes;

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
    public void move(final float delta)
    {
        float yposition;
        float xposition;
        do
        {
            yposition = y + (random.nextFloat() - 0.5f) * 100 * delta;
        }
        while(yposition > GameScreen.DEFAULT_WORLD_HEIGHT || yposition < GameScreen.DEFAULT_WORLD_HEIGHT / 3);
        y = yposition;
        do
        {
            xposition = x + (random.nextFloat() - 0.5f) * 100 * delta;
        }
        while(xposition + width > GameScreen.DEFAULT_WORLD_WIDTH || xposition < 1);
        x = xposition;
    }
    
    @Override
    public List<Shot> shoot(Ship ship)
    {
        if (random.nextInt(50 / shootFrequency) == 0)
        {
            List<Shot> returnList = new ArrayList<Shot>();
            returnList.add(ShotFactory.createShotSplash(x + width / 2, y - height, 1, 0, y - height, true));
            return returnList;
        }
        return emptyShotList;
    }
    
}
