package ch.yoroshiku.spaceInvader.model.enemies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ch.yoroshiku.spaceInvader.model.Ship;
import ch.yoroshiku.spaceInvader.model.Shot;
import ch.yoroshiku.spaceInvader.model.ShotFactory;
import ch.yoroshiku.spaceInvader.util.Enemies;
import ch.yoroshiku.spaceInvader.util.Sizes;
public class Peon extends AbstractEnemy
{
	private static final long serialVersionUID = 1L;
	private int position = -2;
    private boolean enemyDirection = false;
    private Random random = new Random();
    private final float move = (float) 0.2;
    
    public Peon(float x, float y, boolean powerUps)
    {
    	//TODO
        super(x, y, Sizes.PEON_WIDTH, Sizes.PEON_HEIGHT, powerUps, Enemies.allTextures.get(Enemies.PEON_EASY_ID));
    }
    
    @Override
    public void move()
    {
        
        if(enemyDirection)
        {
            position += 1;
            x += move;
        }
        else
        {
            position -= 1;
            x -= move;
        }
        if(position == -3)
        {
            enemyDirection = true;
            y -= 1.2;
        }
        else if (position == 3)
        {
            enemyDirection = false;
            y -= 1.2;
        }
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