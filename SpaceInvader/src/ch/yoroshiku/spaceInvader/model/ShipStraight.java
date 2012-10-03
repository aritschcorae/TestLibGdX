package ch.yoroshiku.spaceInvader.model;

import java.util.List;

import ch.yoroshiku.spaceInvader.screen.GameScreen;
import ch.yoroshiku.spaceInvader.util.Sizes;

import com.badlogic.gdx.graphics.Texture;


public class ShipStraight extends Ship
{
	private static final long serialVersionUID = 1L;
	private int shotDistance = 3;
    private float shotNormalVelocity, shotSlowerVelocity, shotDriftVelocity;
    
    public ShipStraight(float x, float y, Texture texture)
    {
        super(x,y, texture);
        shotLeft = (width * 0.2f);
        shotMiddle = (width * 0.5f);
        shotRight = (width * 0.8f);
        shotNormalVelocity = 50;
        shotSlowerVelocity = 40;
        createShotsList();
        speed = 40f;
        maxShots = 3;
    }

    @Override
    public boolean isMaxShots()
    {
        return shots == maxShots;
    }

    @Override
    public void setSettingsByDifficulty(int difficulty)
    {
//        switch (difficulty)
//        {
//        case 1:
//            setShield(10);
//            break;
//        case 2:
//            setShield(5);
//            break;
//        case 6:
//            setShield(15);
//        case 4:
//        case 5:
//            setSpeed(1);
//            break;
        
//        }
    }

    private void createShotsList()//TODO
    {
        for (int i = 0; i < 10; i++)
        {
            leftShots.add(ShotFactory.createShotLaser(-10f, GameScreen.DEFAULT_WORLD_HEIGHT, shotNormalVelocity, false));
            rightShots.add(ShotFactory.createShotLaser(-10f, GameScreen.DEFAULT_WORLD_HEIGHT, shotNormalVelocity, false));
            middleShots.add(ShotFactory.createShotLaser(-10f, GameScreen.DEFAULT_WORLD_HEIGHT, shotNormalVelocity, false));
        }
    }

    @Override
    public List<Shot> getLeftShots()
    {
        return leftShots;
    }

    @Override
    public List<Shot> getMiddleShots()
    {
        return middleShots;
    }

    @Override
    public List<Shot> getRightShots()
    {
        return rightShots;
    }

    @Override
    public int getShotCoolDown()
    {
        return 3;
    }

    @Override
    public void shoot(final boolean spray)
    {
        middleShots.get(currentShot).x = (x + shotMiddle);
        middleShots.get(currentShot).y = Sizes.SHIP_HEIGHT;
        middleShots.get(currentShot).setDamage(damage);
        if (shots > 1)
        {
            leftShots.get(currentShot).x = (x + shotLeft);
            leftShots.get(currentShot).y = Sizes.SHIP_HEIGHT;
            leftShots.get(currentShot).setDamage(damage);
        }
        if (shots > 2)
        {
        	rightShots.get(currentShot).x = (x + shotRight);
            rightShots.get(currentShot).y = Sizes.SHIP_HEIGHT;
            if (spray)
            {
                leftShots.get(currentShot).setMovementX(shotDriftVelocity);
                rightShots.get(currentShot).setMovementX(-shotDriftVelocity);
                leftShots.get(currentShot).setMovementY(shotSlowerVelocity);
                rightShots.get(currentShot).setMovementY(shotSlowerVelocity);
            }
            else
            {
                leftShots.get(currentShot).setMovementX(0);
                rightShots.get(currentShot).setMovementX(0);
                leftShots.get(currentShot).setMovementY(shotNormalVelocity);
                rightShots.get(currentShot).setMovementY(shotNormalVelocity);
                rightShots.get(currentShot).setDamage(damage);
            }
        }

        if (currentShot == middleShots.size() - 1)
            currentShot = 0;
        else
            currentShot++;
    }

}