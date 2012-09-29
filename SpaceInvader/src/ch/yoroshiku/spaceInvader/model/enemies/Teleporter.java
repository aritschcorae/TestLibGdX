package ch.yoroshiku.spaceInvader.model.enemies;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import ch.yoroshiku.spaceInvader.model.Ship;
import ch.yoroshiku.spaceInvader.model.Shot;
import ch.yoroshiku.spaceInvader.model.enemieset.EnemyGroup;
import ch.yoroshiku.spaceInvader.util.Enemies;
import ch.yoroshiku.spaceInvader.util.Sizes;

public class Teleporter extends AbstractEnemy
{
	private static final long serialVersionUID = 1L;
	private int fieldWidthMiddle, possiblePositionHeight;
    protected int moveTeleportSpeed = 30;
    private float oldX, oldY;
    private int teleportCounter, teleportCountDown = moveTeleportSpeed;
    private boolean teleporting = false;
    private Random random = new Random();
    private List<Map<Integer, EnemyGroup>> emergencyPlan;
    private float leftShotX, rightShotX, shotY;
    private int emergencyCounter = 0;

    public Teleporter(float x, float y, float fieldHeight, float fieldWidth, int shotFrequency
            , boolean powerUps, List<Map<Integer, EnemyGroup>> emergency, float zoom)
    {
		super(x, y, Sizes.TELEPORTER_WIDTH, Sizes.TELEPORTER_HEIGHT, powerUps, Enemies.allTextures.get(Enemies.TELEPORTER_ID));
        leftShotX = 9 * zoom;
        rightShotX = 20 * zoom;
        shotY = 13 * zoom;
        fieldWidthMiddle = (int)fieldWidth / 2;
        this.shootFrequency = shotFrequency;
        possiblePositionHeight = (int) (fieldHeight / 2);
        emergencyPlan = emergency;
    }
    
    @Override
    public void move()
    {
        if(teleportCountDown == 0)
        {
            teleport();
            teleportCountDown = moveTeleportSpeed;
        }
        else
            teleportCountDown --;
    }
    
    @Override
    public List<Shot> shoot(Ship ship)
    {
        if (!teleporting && random.nextInt(20 / shootFrequency) == 0)
        {
            List<Shot> returnList = new ArrayList<Shot>();
            returnList.add(createAimingShot(ship, x + leftShotX, y + shotY));
            returnList.add(createAimingShot(ship, x + rightShotX, y + shotY));
            return returnList;
        }
        return emptyShotList;
    }
    
    public boolean bombDamage(double lowerHealth)
    {
        return false;
    }
    
    @Override
    public boolean lowerHealth(double damage)
    {
        if(!invincible)
        {
            teleport();
            health -= damage;
            if(maxHealth / (emergencyPlan.size() + 1) * (emergencyPlan.size() - emergencyCounter) > health)
            {
                for(EnemyGroup group : emergencyPlan.get(emergencyCounter).values())
                    group.appear();
                emergencyCounter ++;
            }
            return health <= 0;
        }
        else
            return false;
    }
    
    private void teleport()
    {
        invincible = true;
        teleporting = true;
        teleportCounter = 50;
        oldX = x;
        x = random.nextInt(fieldWidthMiddle);
        if(oldX - fieldWidthMiddle < 0)
        {
        	x += (fieldWidthMiddle - width);
        }
        oldY = y;
        y = random.nextInt(possiblePositionHeight);
    }
    
    
}
