package ch.yoroshiku.spaceInvader.model.enemies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ch.yoroshiku.spaceInvader.model.Ship;
import ch.yoroshiku.spaceInvader.model.Shot;
import ch.yoroshiku.spaceInvader.model.ShotFactory;
import ch.yoroshiku.spaceInvader.util.Enemies;
import ch.yoroshiku.spaceInvader.util.Sizes;

public class Midboss extends AbstractEnemy
{
	private static final long serialVersionUID = 1L;
    private Random random = new Random();
    
    public Midboss(float x, float y, float fieldHeight, float fieldWidth, int shotFrequency, boolean powerUps)
    {
    	//TODO
		super(x, y, Sizes.MIDBOSS_WIDTH, Sizes.MIDBOSS_HEIGHT, powerUps, Enemies.allTextures.get(Enemies.MIDBOSS_EASY_ID));
        this.shootFrequency = shotFrequency;
    }
    
    @Override
    public void move()
    {
        float yposition;
        float xposition;
        do
        {
            yposition = y + random.nextFloat() - 0.5f;
        }
        while(yposition >= 84 || yposition < 30);
        y = yposition;
        do
        {
            xposition = x + random.nextFloat() - 0.5f;
        }
        while(xposition + width > 96 || xposition < 1);
        x = xposition;
    }
    
    @Override
    public List<Shot> shoot(Ship ship)
    {
        if (random.nextInt(50 / shootFrequency) == 0)
        {
            List<Shot> returnList = new ArrayList<Shot>();
            returnList.add(ShotFactory.createShotLaser(x + 10, y + height, 1, 0, shotVelocity));
            returnList.add(ShotFactory.createShotLaser(x + width / 2, y + height, 1, 0, shotVelocity));
            returnList.add(ShotFactory.createShotLaser(x + width - 10, y + height, 1, 0, shotVelocity));

            return returnList;
        }
        return emptyShotList;
    }
    
}
