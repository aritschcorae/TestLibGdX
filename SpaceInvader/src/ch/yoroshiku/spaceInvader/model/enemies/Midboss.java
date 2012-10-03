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

public class Midboss extends AbstractEnemy
{
	private static final long serialVersionUID = 1L;
    private Random random = new Random();
    
    public Midboss(float x, float y, float fieldHeight, float fieldWidth, int shotFrequency, boolean powerUps)
    {
		super(x, y, Sizes.MIDBOSS_WIDTH, Sizes.MIDBOSS_HEIGHT, powerUps, Enemies.ALL_TEXTURES.get(Enemies.MIDBOSS_EASY_ID));
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
            returnList.add(ShotFactory.createShotLaser(x + width / 5, y, 1, 0, shotVelocity));
            returnList.add(ShotFactory.createShotLaser(x + width / 2, y, 1, 0, shotVelocity));
            returnList.add(ShotFactory.createShotLaser(x + width / 5 * 4, y, 1, 0, shotVelocity));

            return returnList;
        }
        return emptyShotList;
    }
    
}
