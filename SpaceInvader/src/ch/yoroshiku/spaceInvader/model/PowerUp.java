package ch.yoroshiku.spaceInvader.model;

import ch.yoroshiku.spaceInvader.util.Sizes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;


public class PowerUp extends AbstractSprite
{
	private static final long serialVersionUID = 3831898918602444432L;
    public final static Integer POWER_UP_SPEED = new Integer(0);
    public final static Integer POWER_UP_SHOTS = new Integer(1);
    public final static Integer POWER_UP_SHIELD = new Integer(2);
    public final static Integer POWER_UP_DAMAGE = new Integer(3);
    public final static Integer POWER_UP_BOMB = new Integer(4);
    public final static Integer POWER_UP_HEAL = new Integer(5);
    private final static Texture POWER_UP_TEXTURE_SPEED = new Texture(Gdx.files.internal("images/powerup_speed.png"));
    private final static Texture POWER_UP_TEXTURE_SHOTS = new Texture(Gdx.files.internal("images/powerup_shoot.png"));
    private final static Texture POWER_UP_TEXTURE_SHIELD = new Texture(Gdx.files.internal("images/powerup_shield.png"));
    private final static Texture POWER_UP_TEXTURE_DAMAGE = new Texture(Gdx.files.internal("images/powerup_damage.png"));
    private final static Texture POWER_UP_TEXTURE_BOMB = new Texture(Gdx.files.internal("images/powerup_bomb.png"));
    private final static Texture POWER_UP_TEXTURE_HEAL = new Texture(Gdx.files.internal("images/powerup_heal.png"));
    
	public static int SPEED_POWER_UP_VALUE = 4;
    private Integer type;
    private float velocity = -20;
    private boolean eated = false;
    private Texture powerUpTexture;
    
    public PowerUp(Integer type)
    {
        super((float)-10, (float)-10, Sizes.POWER_UP_WIDTH, Sizes.POWER_UP_HEIGHT);
        this.type = type;
        setTextureByType();
    }
    
    public PowerUp(float x, float y, Integer type)
    {
        super(x,y, Sizes.POWER_UP_WIDTH, Sizes.POWER_UP_EATER_HEIGHT);
        this.type = type;
        setTextureByType();
    }
    
    private void setTextureByType()
    {
    	switch (type)
		{
		case 0:
			powerUpTexture = POWER_UP_TEXTURE_SPEED;
			break;
		case 1:
			powerUpTexture = POWER_UP_TEXTURE_SHOTS;
			break;
		case 2:
			powerUpTexture = POWER_UP_TEXTURE_SHIELD;
			break;
		case 3:
			powerUpTexture = POWER_UP_TEXTURE_DAMAGE;
			break;
		case 4:
			powerUpTexture = POWER_UP_TEXTURE_BOMB;
			break;
		case 5:
			powerUpTexture = POWER_UP_TEXTURE_HEAL;
			break;
		}
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

    public boolean move(float delta)
    {
    	y += velocity * delta;
    	return true;
    }

    public void setCoordinates(float x, float y)
    {
    	this.x = x;
    	this.y = y;
    }

	public void setVelocity() {
		this.velocity = -20;
	}
	
	public void stop()
	{
		this.velocity = 0;
	}

	public Texture getPowerUpTexture()
	{
		return powerUpTexture;
	}

	@Override
	public void drawSprite(SpriteBatch batch, float ppux, float ppuy, float offset) {
		batch.draw(getPowerUpTexture(), offset + x * ppux, y * ppuy, width * ppux, height * ppuy);
	}

	@Override
	public void drawShape(ShapeRenderer shapeRenderer, float ppux, float ppuy, float offset) {
		// not used
	}
    
}