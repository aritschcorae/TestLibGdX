package ch.yoroshiku.spaceInvader.model.enemies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import ch.yoroshiku.spaceInvader.model.Ship;
import ch.yoroshiku.spaceInvader.model.Shot;
import ch.yoroshiku.spaceInvader.screen.GameScreen;
import ch.yoroshiku.spaceInvader.util.Calculator;
import ch.yoroshiku.spaceInvader.util.Enemies;
import ch.yoroshiku.spaceInvader.util.Sizes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

abstract public class PostThrower extends AbstractEnemy
{
	private static final long serialVersionUID = 1L;
	private Random random = new Random();
	private Rectangle leftPost, rightPost;
    
    private float leftPostX, rightPostX, postY; // endPosition
    private float moveLeft, moveRight, moveDown;
    
    private boolean isPostInPosition = false;
    
    private Map<Rectangle, TextureRegion> posts = new HashMap<Rectangle, TextureRegion>();

    public PostThrower(float x, float y,
            int shotFrequency, boolean powerUps, float fieldWidth, float fieldHeight, Texture texture, int id)
    {
		super(x, y, Sizes.THROWER_WIDTH, Sizes.THROWER_HEIGHT, powerUps, texture);
        this.shootFrequency = shotFrequency;
        leftPost = new Rectangle(x - Sizes.POST_WIDTH, y, Sizes.POST_WIDTH, Sizes.POST_HEIGHT);
        posts.put(leftPost, new TextureRegion(Enemies.ALL_TEXTURES.get(id + 1)));
        rightPost = new Rectangle(x - Sizes.POST_WIDTH, y, Sizes.POST_WIDTH, Sizes.POST_HEIGHT);
        posts.put(rightPost, new TextureRegion(Enemies.ALL_TEXTURES.get(id + 1)));
        
        postY = 0;
        leftPostX = 0;
        rightPostX = GameScreen.DEFAULT_WORLD_WIDTH - Sizes.POST_WIDTH;
        moveLeft = Calculator.createDirection(leftPost.x, leftPost.y, leftPostX, postY);
        moveRight = Calculator.createDirection(leftPost.x, leftPost.y, rightPostX, postY);
        moveDown = -35;
    }

    @Override
    public void move(float delta)
    {
        if (!isPostInPosition)
        {
        	leftPost.x += moveLeft * delta;
        	leftPost.y += moveDown * delta;
        	rightPost.x += moveRight * delta;
        	rightPost.y += moveDown * delta;
            if (rightPost.y <= 0)
            {
                isPostInPosition = true;
                invincible = false;
                setModification(true);
                leftPost.x = leftPostX;
                leftPost.y = postY;
                rightPost.x = rightPostX;
                rightPost.y = postY;
            }
        }
    }
    
    @Override
    public boolean lowerHealth(double lowerHealth)
    {
        if (!invincible)
            if (super.lowerHealth(lowerHealth))
            {
                setModification(false);
                return true;
            }
        return false;
    }
    
    abstract protected void setModification(boolean actiaved);

    @Override
    public List<Shot> shoot(Ship ship)
    {
        if (random.nextInt(100 / shootFrequency) == 0)
        {
            List<Shot> returnList = new ArrayList<Shot>();
            returnList.add(createAimingShot(ship, x, y));
            return returnList;
        }
        return emptyShotList;
    }
    
    abstract protected Color getLaserColor();
    
    public Map<Rectangle, TextureRegion> getSubTextures()
    {
		return posts;
    }
    
}