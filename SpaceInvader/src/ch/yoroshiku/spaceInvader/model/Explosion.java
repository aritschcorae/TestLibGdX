package ch.yoroshiku.spaceInvader.model;

import ch.yoroshiku.spaceInvader.util.Sizes;

import com.badlogic.gdx.math.Circle;

public class Explosion extends Circle
{
	private static final long serialVersionUID = 1L;
	private int damage = 50;
	private Circle outerRadius;
	
    public Explosion(float x, float y, int damage)
    {
    	super(x, y, Sizes.BOMB_EXPLOSION_INIT_SIZE);
        this.damage = damage;
        outerRadius = new Circle(x, y, 0);
    }
    
    public int getDamage()
    {
        return damage;
    }

    public boolean expand()
    {
        radius += Sizes.BOMB_EXPLOSION_INC_SIZE;
        damage += 50;
        return radius > 7 * Sizes.BOMB_EXPLOSION_INIT_SIZE;
    }
    
    public Circle getOuterRadius()
    {
    	outerRadius.radius = radius + radius / 5;
    	return outerRadius;
    }

}