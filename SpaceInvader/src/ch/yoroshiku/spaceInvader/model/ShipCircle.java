package ch.yoroshiku.spaceInvader.model;

import java.util.List;

import com.badlogic.gdx.graphics.Texture;


public class ShipCircle extends Ship
{
	private static final long serialVersionUID = 1L;
	private int shotWidthMiddle = 9, shotWidthWing = 7;
    private int shotMiddleVelocity = -11, shotWingVelocity = -9;
    private boolean widerShot = false;
    
    public ShipCircle(float x, float y, Texture texture)
    {
    	super(x,y, texture);
        shotLeft = (width / 9 * 2) - shotWidthWing / 2;
        shotMiddle = (width / 2 ) - shotWidthMiddle / 2;
        shotRight = (width / 9 * 8) - shotWidthWing / 2;
        createShotsList();
        speed = 6;
        maxShots = 3;
        setHealth(5);
        maxHealth = 5;
        damage = 2;
    }
    
    @Override
    protected void handleShotPowerup()
    {
        if (shots < maxShots)
            shots++;
        else if(!widerShot)
        {
            shotWidthMiddle ++;
            shotWidthWing ++;
            widerShot = true;
        }
        else if(!spray)
            spray = true;
        else
            damage++;
    }

    @Override
    public boolean isMaxShots()
    {
        return shots == maxShots;
    }
    
    @Override
    public void setSettingsByDifficulty(int difficulty)
    {
//        setSpeed(2);
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

    
    private void createShotsList()
    {
        for(int i = 0; i < 10; i++)
        {
            leftShots.add(ShotFactory.createShotCircle(-10f, -10f, shotWingVelocity, false, shotWidthWing));
            rightShots.add(ShotFactory.createShotCircle(-10f, -10f, shotWingVelocity, false, shotWidthWing));
            middleShots.add(ShotFactory.createShotCircle(-10f, -10f, shotMiddleVelocity, false, shotWidthMiddle));
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
        return 5;
    }

    @Override
    public void shoot(boolean spray)
    { 
        middleShots.get(currentShot).x = (x + shotMiddle);
        middleShots.get(currentShot).y = y - shotWidthMiddle;
        middleShots.get(currentShot).setDamage(damage);
        if (shots > 1)
        {
            leftShots.get(currentShot).x = (x + shotLeft);
            leftShots.get(currentShot).y = y - shotWidthWing;
            leftShots.get(currentShot).setDamage(damage);
        }
        if (shots > 2)
        {
            rightShots.get(currentShot).x = (x + shotRight);
            rightShots.get(currentShot).y = y - shotWidthWing;
            rightShots.get(currentShot).setDamage(damage);
            if (spray)
            {
                leftShots.get(currentShot).setMovementY(shotWingVelocity -1);
                rightShots.get(currentShot).setMovementY(shotWingVelocity -1);
                leftShots.get(currentShot).setMovementX(-1);
                rightShots.get(currentShot).setMovementX(1);
            }
            else
            {
                leftShots.get(currentShot).setMovementY(shotWingVelocity);
                rightShots.get(currentShot).setMovementY(shotWingVelocity);
                leftShots.get(currentShot).setMovementX(0);
                rightShots.get(currentShot).setMovementX(0);
            }
        }
        if(widerShot)
        {
            middleShots.get(currentShot).setWidth(shotWidthMiddle);
            leftShots.get(currentShot).setWidth(shotWidthWing);
            rightShots.get(currentShot).setWidth(shotWidthWing);
        }
        
        if(currentShot == middleShots.size() -1)
            currentShot = 0;
        else
            currentShot ++;
    }
    
}