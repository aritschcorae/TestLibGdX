package ch.yoroshiku.spaceInvader.model.enemies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ch.yoroshiku.spaceInvader.model.Ship;
import ch.yoroshiku.spaceInvader.model.Shot;
import ch.yoroshiku.spaceInvader.util.Sizes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

abstract public class PostThrower extends AbstractEnemy
{
	private static final long serialVersionUID = 1L;
	private Random random = new Random();
    private float rightPostCoordX, rightPostCoordY;
    private float leftPostCoordX, leftPostCoordY;
    
    private float leftPostX, rightPostX, postY;
    private float moveLeft, moveRight, moveDown;
    
    private float laserLeft, laserRight, laserTop, laserBottom;
    private float fieldHeight;
    
    private boolean isPostInPosition = false;
    private float shotXMove, shotYMove;
    
    private int steps = 40, countSteps = 0;

    public PostThrower(float x, float y,
            int shotFrequency, boolean powerUps, float fieldWidth, float fieldHeight, Texture texture)
    {
		super(x, y, Sizes.THROWER_WIDTH, Sizes.THROWER_HEIGHT, powerUps, texture);
        this.shootFrequency = shotFrequency;
        this.fieldHeight = fieldHeight;

        leftPostX = 1;
    }

    @Override
    public void move(float delta)//TODO
    {
        if (!isPostInPosition)
        {
        	leftPostCoordX += moveLeft;
            leftPostCoordY += moveDown;
            rightPostCoordX += moveRight;
            rightPostCoordY += moveDown;
            countSteps ++;
            if (steps == countSteps)
            {
                isPostInPosition = true;
                invincible = false;
                setModification(true);
                leftPostCoordX = leftPostX;
                rightPostCoordX = rightPostX;
                leftPostCoordY = postY;
                rightPostCoordY = postY;
            }
        }
    }
    
    @Override
    public boolean lowerHealth(double lowerHealth)
    {
        if (!invincible)
            if (super.lowerHealth(lowerHealth))
            {
                setModification(false);
                return true;
            }
        return false;
    }
    
    abstract protected void setModification(boolean actiaved);

    @Override
    public List<Shot> shoot(Ship ship)
    {
        if (random.nextInt(30 / shootFrequency) == 0)
        {
            List<Shot> returnList = new ArrayList<Shot>();
            returnList.add(createAimingShot(ship, x + shotXMove, y + shotYMove));
            return returnList;
        }
        return emptyShotList;
    }
    
    abstract protected Color getLaserColor();
    
}
