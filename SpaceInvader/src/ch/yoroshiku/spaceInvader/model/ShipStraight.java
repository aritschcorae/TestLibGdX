package ch.yoroshiku.spaceInvader.model;

import ch.yoroshiku.spaceInvader.model.shot.Shot;
import ch.yoroshiku.spaceInvader.model.shot.ShotFactory;
import ch.yoroshiku.spaceInvader.util.Sizes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;


public class ShipStraight extends Ship
{
	private static final long serialVersionUID = 1L;
    private float shotNormalVelocity, shotSlowerVelocity, shotDriftVelocity;
    
    public ShipStraight(float x, float y, Texture texture)
    {
        super(x,y, texture);
        shotLeft = (width * 0.2f);
        shotMiddle = (width * 0.5f);
        shotRight = (width * 0.8f);
        shotNormalVelocity = 50;
        shotSlowerVelocity = 45;
        shotDriftVelocity = 5;
        createShotsList();
        speed = 40f;
        maxShots = 3;
    }

    @Override
    public boolean isMaxShots()
    {
        return shots == maxShots;
    }

    private void createShotsList()
    {
        for (int i = 0; i < 1; i++)
        {
            leftShots.add(ShotFactory.createShotLaser(-10f, Sizes.DEFAULT_WORLD_HEIGHT, shotNormalVelocity, false));
//            rightShots.add(ShotFactory.createShotLaser(-10f, Sizes.DEFAULT_WORLD_HEIGHT, shotNormalVelocity, false));
//            middleShots.add(ShotFactory.createShotLaser(-10f, Sizes.DEFAULT_WORLD_HEIGHT, shotNormalVelocity, false));
        }
    }

    @Override
    public Array<Shot> getLeftShots()
    {
        return leftShots;
    }

    @Override
    public Array<Shot> getMiddleShots()
    {
        return middleShots;
    }

    @Override
    public Array<Shot> getRightShots()
    {
        return rightShots;
    }

    @Override
    public int getShotCoolDown()
    {
        return 3;
    }

    @Override
    public void shoot(float delay)
    {
//        middleShots.get(currentShot).x = (x + shotMiddle);
//        middleShots.get(currentShot).y = Sizes.SHIP_HEIGHT + shotNormalVelocity * delay;
//        middleShots.get(currentShot).setDamage(damage);
        if (shots > 1)
        {
            leftShots.get(currentShot).x = (x + shotLeft);
            leftShots.get(currentShot).y = Sizes.SHIP_HEIGHT + shotNormalVelocity * delay;
            leftShots.get(currentShot).setDamage(damage);
        }
        if (shots > 2)
        {
//        	rightShots.get(currentShot).x = (x + shotRight);
//            rightShots.get(currentShot).y = Sizes.SHIP_HEIGHT + shotNormalVelocity * delay;
            if (spray)
            {
                leftShots.get(currentShot).setMovementX(-shotDriftVelocity);
//                rightShots.get(currentShot).setMovementX(shotDriftVelocity);
                leftShots.get(currentShot).setMovementY(shotSlowerVelocity);
//                rightShots.get(currentShot).setMovementY(shotSlowerVelocity);
            }
            else
            {
                leftShots.get(currentShot).setMovementX(0);
//                rightShots.get(currentShot).setMovementX(0);
                leftShots.get(currentShot).setMovementY(shotNormalVelocity);
//                rightShots.get(currentShot).setMovementY(shotNormalVelocity);
//                rightShots.get(currentShot).setDamage(damage);
            }
        }

        if (currentShot == middleShots.size - 1)
        if (currentShot == leftShots.size - 1)      	
            currentShot = 0;
        else
            currentShot++;
    }

}