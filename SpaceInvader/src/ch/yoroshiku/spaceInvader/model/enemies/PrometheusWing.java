package ch.yoroshiku.spaceInvader.model.enemies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ch.yoroshiku.spaceInvader.model.Ship;
import ch.yoroshiku.spaceInvader.model.Shot;
import ch.yoroshiku.spaceInvader.util.Sizes;

public class PrometheusWing extends AbstractEnemy
{
    private float[] yCoordinates = new float[3], xCoordinates = new float[3];
    private int[] yExplosionCoordinates, xExplosionCoordinates;
    private boolean leftWing = false, halfHealth = false, destoryed = false;
    private double startHealth;
    private Random random = new Random();
    private Prometheus daddy;
    private int explosionStep = 0;
    private boolean exploded = false;
    private int explosionHeight;
    
    public PrometheusWing(float x, float y, boolean powerUps, boolean leftWing, float zoom)
    {
        super(x,y,Sizes.WING_WIDTH, Sizes.WING_HEIGHT, powerUps);
        height /= 2;
        height --;
        barHeight = 13;
        this.leftWing = leftWing;
        if(leftWing)
        {
            yCoordinates = new float[] { 17 * zoom, 23 * zoom, 28 * zoom };
            xCoordinates = new float[] { 14 * zoom, 25 * zoom, 36 * zoom };
            yExplosionCoordinates = new int[] { (int) (20 * zoom), (int) (2 * zoom), (int) (5 * zoom) };
            xExplosionCoordinates = new int[] { (int) (26 * zoom), (int) (3 * zoom), (int) (22 * zoom) };
        }
        else
        {
            yCoordinates = new float[] { 28 * zoom, 23 * zoom, 17 * zoom };
            xCoordinates = new float[] { 13 * zoom, 24 * zoom, 35 * zoom };
            yExplosionCoordinates = new int[] { (int) (20 * zoom), (int) (2 * zoom), (int) (5 * zoom) };
            xExplosionCoordinates = new int[] { (int) (4 * zoom), (int) (27 * zoom), (int) (8 * zoom) };
        }
        shootFrequency = 3;
    }
    
    @Override
    public void move()
    {
        //got from center part (daddy)
    }
    
    @Override
    public List<Shot> shoot(Ship ship)
    {
        if(!exploded
            && ((!leftWing && x - getWidth() <= ship.x)
            || (leftWing && x + (2*getWidth()) > ship.x)))
        {
            if (random.nextInt(20 / shootFrequency) == 4)
            {
                return createShootList(ship);
            }
        }
        return emptyShotList;
    }

    private List<Shot> createShootList(Ship ship)
    {
        float xMovement = createDirection(x + xCoordinates[0], y + yCoordinates[0], ship.x, ship.y);
        List<Shot> returnList = new ArrayList<Shot>();
        returnList.add(createShot(x + xCoordinates[0], y + yCoordinates[0], xMovement));
        returnList.add(createShot(x + xCoordinates[1], y + yCoordinates[1], xMovement));
        returnList.add(createShot(x + xCoordinates[2], y + yCoordinates[2], xMovement));
        return returnList;
    }

    @Override
    public boolean lowerHealth(double lowerHealth)
    {
        super.lowerHealth(lowerHealth);
        if(destoryed)
        {
            health = 1;
            daddy.lowerHealthWing(lowerHealth);
        }
        else if(!halfHealth && (startHealth / 2) - health > 0)
        {
            halfHealth = true;
            shotVelocity = 10;
            shootFrequency = 4;
        }
        else if(health <= 0)
        {
            if (leftWing)
                daddy.killWing(true);
            else
                daddy.killWing(false);
            destoryed = true;
            health = 1;
        }
        return false;
    }
    
    public void setDaddy(Prometheus daddy)
    {
        this.daddy = daddy;
    }

    public void setStartHealth(double startHealth)
    {
        this.startHealth = startHealth;
    }

    @Override
    public boolean bombDamage(double lowerHealth)
    {
        return false;
    }
}