package ch.yoroshiku.spaceInvader.model;

import ch.yoroshiku.spaceInvader.util.Sizes;

import com.badlogic.gdx.math.Circle;

public class Explosion extends Circle
{
	private static final long serialVersionUID = 1L;
	private int damage = 50;

    public Explosion(float x, float y, int damage)
    {
    	super(x, y, Sizes.EXPLOSION_INIT_SIZE);
        this.damage = damage;
    }
    
    public int getDamage()
    {
        return damage;
    }

    public boolean expand()
    {
        radius += Sizes.EXPLOSION_INC_SIZE;
        damage += 50;
        return radius > 7 * Sizes.EXPLOSION_INIT_SIZE;
    }
    
    public float getOuterRadius()
    {
        return radius + radius / 5;
    }

}