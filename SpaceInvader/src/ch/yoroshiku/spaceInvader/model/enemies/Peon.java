package ch.yoroshiku.spaceInvader.model.enemies;

import java.util.Random;



import ch.yoroshiku.spaceInvader.model.Ship;
import ch.yoroshiku.spaceInvader.model.shot.Shot;
import ch.yoroshiku.spaceInvader.model.shot.ShotFactory;
import ch.yoroshiku.spaceInvader.util.Sizes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
public class Peon extends AbstractEnemy
{
	private static final long serialVersionUID = 1L;
    private float yPositionDif = 0;
    private boolean enemyDirection = false;
    private Random random = new Random();
    private final float move = (float) 0.2;
    
    public Peon(final float x, final float y, final boolean powerUps, final Texture texture)
    {
        super(x, y, Sizes.PEON_WIDTH, Sizes.PEON_HEIGHT, powerUps, texture);
        showHealthBar = false;
    }
    
    
	@Override
	public boolean move(final float delta)
	{

		if (enemyDirection)
		{
			x += move * 25 * delta;
		} else
		{
			x -= move * 25 * delta;
		}
		yPositionDif += 6 * delta;
		if (yPositionDif >= 1.2)
		{
			y -= yPositionDif;
			yPositionDif = 0;
			enemyDirection = !enemyDirection;
		}
		return true;
	}
    
    @Override
    public Array<Shot> shoot(final Ship ship)
    {
        if (random.nextInt(300 / shootFrequency) == 0)
        {
        	Array<Shot> returnList = new Array<Shot>();
            returnList.add(ShotFactory.createShotLaser(x, y + width / 2, shotVelocity));
            return returnList;
        }
        return emptyShotList;
    }
    
    @Override
    public boolean lowerHealth(final double lowerHealth)
    {
        return super.lowerHealth(lowerHealth);
    }
    
}