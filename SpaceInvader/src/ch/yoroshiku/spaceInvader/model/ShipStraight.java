package ch.yoroshiku.spaceInvader.model;

import java.util.List;


public class ShipStraight extends Ship
{
	private static final long serialVersionUID = 1L;
	private int shotDistance = 12;
    private float shotNormalVelocity, shotSlowerVelocity, shotDriftVelocity;
    
    public ShipStraight(float x, float y)
    {
        super(x,y);
        shotLeft = (width * 0.4f) - 2;
        shotMiddle = (width * 0.6f) - 2;
        shotRight = (width * 0.8f) - 2;
        shotNormalVelocity = -2;
        shotSlowerVelocity = -1.6f;
        shotDriftVelocity = -2f;
        createShotsList(1);//TODO
        speed = 1.6f;
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

    private void createShotsList(float zoom)//TODO
    {
        for (int i = 0; i < 10; i++)
        {
            leftShots.add(ShotFactory.createShotLaser(-10f, -10f, -10 * zoom, false));
            rightShots.add(ShotFactory.createShotLaser(-10f, -10f, -10 * zoom, false));
            middleShots.add(ShotFactory.createShotLaser(-10f, -10f, -10 * zoom, false));
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
        middleShots.get(currentShot).y = y - shotDistance;
        middleShots.get(currentShot).setDamage(damage);
        if (shots > 1)
        {
            leftShots.get(currentShot).x = (x + shotLeft);
            leftShots.get(currentShot).y =  y - shotDistance;
            leftShots.get(currentShot).setDamage(damage);
        }
        if (shots > 2)
        {
        	rightShots.get(currentShot).x = (x + shotRight);
            rightShots.get(currentShot).y =  y - shotDistance;
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