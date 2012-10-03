package ch.yoroshiku.spaceInvader.model;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Shot extends Rectangle
{
	private static final long serialVersionUID = 1L;
	protected double damage = 1;
    protected boolean simpleShoot = true;
    protected boolean enemyShot = true;
    protected double acceleration = 1;
    protected float movementX, movementY;

    public Shot(float x, float y, int damage, float movementX, float movementY, float height, float width, boolean enemyShot)
    {
    	super(x, y, width, height);
        this.movementY = movementY;
        this.movementX = movementX;
        this.damage = damage;
        this.enemyShot = enemyShot;
    }
    
    public Vector2 getCoordinatesForExplosion()
    {
        return new Vector2(x + width / 6, y + height / 2);
        
    }

    public double getDamage()
    {
        return damage;
    }

    public void setMovementX(float movementX)
    {
        this.movementX = movementX;
    }

    public void setAcceleration(float acceleration)
    {
        this.acceleration = acceleration;
    }

    public void setMovementY(float movementY)
    {
        this.movementY = movementY;
    }

    public boolean isSimpleShot()
    {
        return simpleShoot;
    }

    public void setSimpleShoot(boolean simpleShoot)
    {
        this.simpleShoot = simpleShoot;
    }

    public void setDamage(double damage)
    {
        this.damage = damage;
    }
    
    public List<Shot> nextStep(float delta)
    {
        if (acceleration != 1)
            accelarete();
        x += movementX * delta;
        y += movementY * delta;
        return new ArrayList<Shot>();
    }
    
    private void accelarete()
    {
        movementY *= acceleration;
    }

    public float getMovementY()
    {
        return movementY;
    }

    public float getMovementX()
    {
        return movementX;
    }

    public boolean isEnemyShoot()
    {
        return enemyShot;
    }
    
}