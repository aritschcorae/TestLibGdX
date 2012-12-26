package ch.yoroshiku.spaceInvader.model;

import ch.yoroshiku.spaceInvader.util.Sizes;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

public class Explosion extends Circle
{
	private static final long serialVersionUID = 1L;
	private int damage;
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
        damage *= 3 / 2;
        return radius > 5 * Sizes.BOMB_EXPLOSION_INIT_SIZE;
    }
    
    public Circle getOuterRadius(float ppux)
    {
    	outerRadius.radius = radius + radius / 5;
    	return outerRadius;
    }
    
    public void draw(final ShapeRenderer shapeRenderer, final float ppuX, final float ppuY, final float border){
        for(int i = (int) (getOuterRadius(ppuX).radius - radius); i >=0 ; i--)
        {
        	float green = 1 / (getOuterRadius(ppuX).radius - radius);
        	shapeRenderer.setColor(1, green, 0, 0);
        	shapeRenderer.circle(border + x  * ppuX, y * ppuY, radius * ppuX);
            
        }
    }

}