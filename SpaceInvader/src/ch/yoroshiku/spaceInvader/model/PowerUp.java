package ch.yoroshiku.spaceInvader.model;

import ch.yoroshiku.spaceInvader.util.Sizes;

import com.badlogic.gdx.math.Rectangle;


public class PowerUp extends Rectangle
{
	private static final long serialVersionUID = 3831898918602444432L;
	public static int SPEED_POWER_UP_VALUE = 4;
    private Integer type;
    private float velocity = 4;
    private boolean eated = false;
    
    public PowerUp(Integer type)
    {
        super((float)-10, (float)-10, Sizes.POEWR_UP_WIDTH, Sizes.POWER_UP_EATER_HEIGHT);
        this.type = type;
    }
    
    public PowerUp(float x, float y, Integer type)
    {
        super(x,y, Sizes.POEWR_UP_WIDTH, Sizes.POWER_UP_EATER_HEIGHT);
        this.type = type;
    }

    public Integer getType()
    {
        return type;
    }

    
    public boolean isEated()
    {
        return eated;
    }

    public void setEated(boolean eated)
    {
        this.eated = eated;
    }

    public void move()
    {
    	y += velocity;
    }

    public void setCoordinates(float x, float y)
    {
    	this.x = x;
    	this.y = y;
    }

	public void setVelocity(float velocity) {
		this.velocity = velocity;
	}

    
}