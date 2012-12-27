package ch.yoroshiku.spaceInvader.model.enemies;

import java.util.Random;

import ch.yoroshiku.spaceInvader.model.Ship;
import ch.yoroshiku.spaceInvader.model.shot.Shot;
import ch.yoroshiku.spaceInvader.model.shot.ShotFactory;
import ch.yoroshiku.spaceInvader.util.Sizes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

public class Midboss extends AbstractEnemy
{
	private static final long serialVersionUID = 1L;
    private Random random = new Random();
    
    public Midboss(float x, float y, float fieldHeight, float fieldWidth, int shotFrequency, boolean powerUps, Texture texture)
    {
		super(x, y, Sizes.MIDBOSS_WIDTH, Sizes.MIDBOSS_HEIGHT, powerUps, texture);
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
        if (random.nextInt(150 / shootFrequency) == 0)
        {
        	Array<Shot> returnList = new Array<Shot>();
            returnList.add(ShotFactory.createShotLaser(x + width / 5, y, 1, 0, shotVelocity));
            returnList.add(ShotFactory.createShotLaser(x + width / 2, y, 1, 0, shotVelocity));
            returnList.add(ShotFactory.createShotLaser(x + width / 5 * 4, y, 1, 0, shotVelocity));

            return returnList;
        }
        return emptyShotList;
    }


	@Override
	public void drawShape(ShapeRenderer shapeRenderer, float ppux, float ppuy, float offset) {
		drawHealthBar(shapeRenderer, ppux, ppuy, offset);
	}
    
}
