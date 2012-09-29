package ch.yoroshiku.spaceInvader.model.enemies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ch.yoroshiku.spaceInvader.model.Ship;
import ch.yoroshiku.spaceInvader.model.Shot;
import ch.yoroshiku.spaceInvader.model.ShotFactory;
import ch.yoroshiku.spaceInvader.util.Sizes;
public class Peon extends AbstractEnemy
{
	private static final long serialVersionUID = 1L;
	private int position = -2;
    private int enemyDirection = 1;
    private Random random = new Random();

    public Peon(float x, float y, boolean powerUps)
    {
        super(x, y, Sizes.PEON_WIDTH, Sizes.PEON_HEIGHT, powerUps);
    }
    
    @Override
    public void move()
    {
        int move = 0;
        if(enemyDirection == 1)
        {
            move = 1;
            position += move;
        }
        else if(enemyDirection == 0)
        {
            move = -1;
            position += move;
        }
        if(position == -3)
        {
            enemyDirection = 1;
            y += 6;
        }
        else if (position == 3)
        {
            enemyDirection = 0;
            y += 6;
        }
        x += move;
    }
    
    @Override
    public List<Shot> shoot(Ship ship)
    {
        if (random.nextInt(100 / shootFrequency) == 0)
        {
            List<Shot> returnList = new ArrayList<Shot>();
            returnList.add(ShotFactory.createShotLaser(x, y, shotVelocity));
            return returnList;
        }
        return emptyShotList;
    }
    
    @Override
    public boolean lowerHealth(double lowerHealth)
    {
        return super.lowerHealth(lowerHealth);
    }
    
}