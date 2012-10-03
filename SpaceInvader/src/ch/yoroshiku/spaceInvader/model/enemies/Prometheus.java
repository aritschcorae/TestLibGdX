package ch.yoroshiku.spaceInvader.model.enemies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ch.yoroshiku.spaceInvader.model.Ship;
import ch.yoroshiku.spaceInvader.model.Shot;
import ch.yoroshiku.spaceInvader.model.ShotFactory;
import ch.yoroshiku.spaceInvader.util.Enemies;
import ch.yoroshiku.spaceInvader.util.Sizes;

public class Prometheus extends AbstractEnemy
{
    private Random random = new Random();
    private PrometheusWing leftWing;
    private PrometheusWing rightWing;
    private int position = 20;
    private int enemyDirection = 1;
    private boolean leftWingAlive = true, rightWingAlive = true;
    private boolean alive = true, exploded = false;
    private float[] xExplosion, yExplosion;
    private int explosionHeight, explosionStep;
    private int circleX, circleY, circleRadius;

    public Prometheus(float x, float y, boolean powerUps, PrometheusWing leftWing,
            PrometheusWing rightWing, int health, int points)
    {
		super(x, y, Sizes.PROMETHEUS_WIDTH, Sizes.PROMETHEUS_HEIGHT, powerUps, Enemies.ALL_TEXTURES.get(Enemies.PROMETHEUS_ID));
        this.leftWing = leftWing;
        this.rightWing = rightWing;
        this.leftWing.setDaddy(this);
        this.rightWing.setDaddy(this);
        setHealth(health);
        leftWing.setHealth(health * 0.8);
        rightWing.setHealth(health * 0.8);
        leftWing.setStartHealth(health * 0.8);
        rightWing.setStartHealth(health * 0.8);
        setPoints(points);
        leftWing.setPoints(points * 0.8);
        rightWing.setPoints(points * 0.8);
        barHeight = 13;
        shotVelocity = 10;
        circleX = (int) (5);
        circleY = (int) (7);
        circleRadius = (int) (6);
        xExplosion = new float[] { 6, 4.6f, 2.2f, 6.4f };
        yExplosion = new float[] { 9.2f, 3.4f, 6,6f, 3.2f};
    }
    
    @Override
    public void move(float delta) //TODO
    {
        position ++;
        if(position == 40)
        {
            enemyDirection *= -1;
            position = 0;
        }
        x += enemyDirection;
        leftWing.x += enemyDirection;
        rightWing.x += enemyDirection;
    }
    
    @Override
    public List<Shot> shoot(Ship ship)
    {
        if(alive)
        {
            if (random.nextInt(20) == 10)
            {
                List<Shot> returnList = new ArrayList<Shot>();
                //TODO zoom coords
                returnList.add(ShotFactory.createShotLaser(x + 20, y + 31, 2, 0, shotVelocity, 30, 10));
                return returnList;
            }
            if (random.nextInt(20) == 10)
            {
                List<Shot> returnList = new ArrayList<Shot>();//TODO position
                returnList.add(ShotFactory.createShotLaser(x + 20, y + 37, 2, 0, shotVelocity, 20, 10));
                return returnList;
            }
        }
        return emptyShotList;
    }

    
    @Override
    public boolean lowerHealth(double lowerHealth)
    {
        if(exploded)
            return true;
        if(leftWingAlive && rightWingAlive)
            return false;
        else if(leftWingAlive)
        {
            lowerHealth /= 2;
        }
        else if(rightWingAlive)
        {
            lowerHealth /= 2;
        }
        super.lowerHealth(lowerHealth);
        if (health <= 0)
        {
            leftWing.setHealth(0);
            rightWing.setHealth(0);
            health = 1;
            alive = false;
        }
        return false;
    }
    
    @Override
    public boolean bombDamage(double lowerHealth)
    {
        health += lowerHealth;
        if(maxHealth < health)
            health = maxHealth;
        return false;
    }
    
    @Override
    public List<AbstractEnemy> getDestroyedEnemies()
    {
        List<AbstractEnemy> destroyedEnemies = new ArrayList<AbstractEnemy>();
        destroyedEnemies.add(this);
        destroyedEnemies.add(leftWing);
        destroyedEnemies.add(rightWing);
        return destroyedEnemies;
    }

    public void killWing(boolean rightWing)
    {
        if(rightWing)
            rightWingAlive = false;
        else
            leftWingAlive = false;
        if(!leftWingAlive && !rightWingAlive)
        	;
    }

    public void lowerHealthWing(double lowerHealth)
    {
        if(!leftWingAlive && !rightWingAlive)
            health = health - lowerHealth / 2;
    }

}