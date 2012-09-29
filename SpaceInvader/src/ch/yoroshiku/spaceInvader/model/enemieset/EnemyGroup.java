package ch.yoroshiku.spaceInvader.model.enemieset;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

import ch.yoroshiku.spaceInvader.model.enemies.AbstractEnemy;

public class EnemyGroup extends HashSet<AbstractEnemy>
{
    private AbstractEnemy farLeft, farRight, bottom, top;
    private boolean visible, appeared = false;
    private boolean needForLevel = true;
    private Random random = new Random();
    private int id;
    private int groupAlpha = 0;
    private int timeAppear, kindAppear;
    private float endPositionY, endPositionX, movementX, movementY;
    private int slowDown;
    
    private static final long serialVersionUID = 1L;

    
    public EnemyGroup(boolean neededForLevel, int id, int timeAppear, int appearKind, int slowDown)
    {
        this.id = id;
        visible = timeAppear == 0;
        this.timeAppear = timeAppear;
        this.needForLevel = neededForLevel;
        this.kindAppear = appearKind;
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
            if(top == null || enemy.y < getY())
                top = enemy;
    }
    
    private void recheckBottom()
    {
        for(AbstractEnemy enemy : this)
            if(bottom == null || enemy.y + enemy.getHeight() > getYWithHeight())
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
    
    public void move()
    {
        if(appeared)
        {
            for(AbstractEnemy enemy : this)
            {
                enemy.move();
            }
        }
        else if(visible)
        {
            if(kindAppear == 0)
            {
                groupAlpha += 25;
                if(groupAlpha >= 220)
                {
                    groupAlpha = 255;
                    appeared = true;
                }
            }
            else if(kindAppear == 1)
            {
                groupAlpha += 40;
                for(AbstractEnemy enemy : this)
                {
                	enemy.x += movementX;
                	enemy.y += movementY;
                }
                if((top.y == endPositionY)
                 && farLeft.x == endPositionX)
                {
                    appeared = true;
                    groupAlpha = 255;
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
    
    public void appear(long time)
    {
        if(timeAppear > 0 && time >= timeAppear)
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

}