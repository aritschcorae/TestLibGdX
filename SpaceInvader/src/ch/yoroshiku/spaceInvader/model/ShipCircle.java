package ch.yoroshiku.spaceInvader.model;



import ch.yoroshiku.spaceInvader.model.shot.Shot;
import ch.yoroshiku.spaceInvader.model.shot.ShotFactory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;


public class ShipCircle extends Ship //TODO test everything
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
        return 5;
    }

    @Override
    public void shoot(float delay)
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
        
        if(currentShot == middleShots.size -1)
            currentShot = 0;
        else
            currentShot ++;
    }

}