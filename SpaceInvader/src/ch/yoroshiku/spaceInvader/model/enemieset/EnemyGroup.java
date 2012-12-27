package ch.yoroshiku.spaceInvader.model.enemieset;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;


import ch.yoroshiku.spaceInvader.model.enemies.AbstractEnemy;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;

public class EnemyGroup extends HashSet<AbstractEnemy>
{
    private AbstractEnemy farLeft, farRight, bottom, top;
    private Rectangle bounds = new Rectangle();
    private boolean visible, appeared = false;
    private boolean needForLevel = true;
    private Random random = new Random();
    private int id;
    private int timeAppear, kindAppear, timePassed;
    private float endPositionY, endPositionX, movementX, movementY;
    private int slowDown;
    private Color color = new Color(1, 1, 1, 0);
    
    private static final long serialVersionUID = 1L;

    
    public EnemyGroup(boolean neededForLevel, int id, int timeAppear, int appearKind, int slowDown)
    {
        this.id = id;
        visible = timeAppear == 0;
        this.timeAppear = timeAppear;
        this.needForLevel = neededForLevel;
        this.kindAppear = appearKind;
        timePassed = 0;
        bounds = new Rectangle();
        this.setSlowDown(slowDown);
    }
    
    @Override
    public boolean addAll(Collection<? extends AbstractEnemy> collection)
    {
        boolean returnValue = super.addAll(collection);
        if(returnValue)
        {
            resetCoordinates();
        }
        return returnValue;
    }
    
    @Override
    public boolean add(AbstractEnemy object)
    {
        boolean returnValue = super.add(object);
        if(returnValue)
        {
            if(farLeft == null)
            {
                setEnemyCoordinates(object);
            }
            else
            {
                checkCoordinates(object);
            }
        }
        return returnValue;
    }
    
    private void resetCoordinates()
    {
        for(AbstractEnemy enemy : this)
        {
            if(farLeft == null)
                setEnemyCoordinates(enemy);
            else
                checkCoordinates(enemy);
        }
    }
    
    private void setEnemyCoordinates(AbstractEnemy enemy)
    {
        farLeft = enemy;
        farRight = enemy;
        top = enemy;
        bottom = enemy;
    }
    
    private void checkCoordinates(AbstractEnemy enemy)
    {
        recheckLeft();
        recheckRight();
        recheckBottom();
        recheckTop();
    }
    
    
    public boolean removeAll(EnemyGroup collection)
    {
        boolean returnValue = super.removeAll(collection);
        if(collection.contains(farLeft))
        {
            farLeft = null;
            recheckLeft();
        }
        if(collection.contains(farRight))
        {
            farRight = null;
            recheckRight();
        }
        if(collection.contains(bottom))
        {
            bottom = null;
            recheckBottom();
        }
        if(collection.contains(top))
        {
            top = null;
            recheckTop();
        }
        return returnValue;
    }
    
    private void recheckLeft()
    {
        for(AbstractEnemy enemy : this)
            if(farLeft == null || enemy.x < getX())
                farLeft = enemy;
    }
    
    private void recheckRight()
    {
        for(AbstractEnemy enemy : this)
            if(farRight == null || enemy.x + enemy.getWidth() > getXWithWidth())
                farRight = enemy;
    }
    
    private void recheckTop()
    {
        for(AbstractEnemy enemy : this)
            if(top == null || enemy.y > getY())
                top = enemy;
    }
    
    private void recheckBottom()
    {
        for(AbstractEnemy enemy : this)
            if(bottom == null || enemy.y + enemy.getHeight() < getYWithHeight())
                bottom = enemy;
    }

    public AbstractEnemy getRandomMember()
    {
        int randomMember = random.nextInt(this.size());
        int i = 0;
        for(AbstractEnemy enemy : this)
        {
            if(i == randomMember)
            {
                return enemy;
            }
            i++;
        }
        return null;
    }
    
    public void setAppearanceVariable(float movementX, float movementY, float endPositionX, float endPositionY)
    {
        this.movementX = movementX;
        this.movementY = movementY;
        this.endPositionX = endPositionX;
        this.endPositionY = endPositionY;
    }
    
    public void move(final float delta) //TODO slowdown?
    {
        if(appeared)
        {
            for(AbstractEnemy enemy : this)
            {
                enemy.move(delta);
            }
        }
        else if(visible)
        {
            if(kindAppear == 0)
            {
                color.a += 0.1f;
                if(color.a >= 0.8)
                {
                	color.a = 1;
                    appeared = true;
                }
            }
            else if(kindAppear == 1)
            {
            	color.a += 0.2f;
                for(AbstractEnemy enemy : this)
                {
                	enemy.x += movementX;
                	enemy.y += movementY;
                }
                if((top.y == endPositionY)
                 && farLeft.x == endPositionX)
                {
                    appeared = true;
                    color.a = 1;
                }
            }
        }
    }
    


    public float getX()
    {
        return farLeft.x;
    }

    public float getY()
    {
        return top.y;
    }

    public float getXWithWidth()
    {
        return farRight.x + farRight.getWidth();
    }

    public float getYWithHeight()
    {
        return bottom.y + bottom.getHeight();
    }
    
    public float getHeight()
    {
        return getYWithHeight() - getY();
    }
    
    public float getWidth()
    {
        return getXWithWidth() - getX();
    }

    public int getId()
    {
        return id;
    }

    public boolean isNeedForLevel()
    {
        return needForLevel;
    }

    public void setNeedForLevel(boolean needForLevel)
    {
        this.needForLevel = needForLevel;
    }

    public boolean isAppeared()
    {
        return appeared;
    }


    public boolean isVisible()
    {
        return visible;
    }

    public void setVisible(boolean visible)
    {
        this.visible = visible;
    }
    
    public void appear()
    {
        visible = true;
    }
    
    public void appear(float time)
    {
    	timePassed += time * 1000;
        if(timeAppear > 0 && timePassed >= timeAppear)
            visible = true;
    }

    public int getAppearAfterId()
    {
        return -timeAppear;
    }

    public void setSlowDown(int slowDown)
    {
        this.slowDown = slowDown;
    }

    public int getSlowDown()
    {
        return slowDown;
    }

	public Color getColor() {
		return color;
	}
	
	public Rectangle getBounds()
	{
		if(isEmpty())
		{
			bounds.x = 0;
			bounds.y = 0;
			bounds.height = 0;
			bounds.width = 0;
			return bounds;
		}
		bounds.x = farLeft.x;
		bounds.width = (farRight.x + farRight.width) - bounds.x;
		bounds.y = bottom.y;
		bounds.height = (top.y + top.height) - bounds.y;
		
		return bounds;
	}

}