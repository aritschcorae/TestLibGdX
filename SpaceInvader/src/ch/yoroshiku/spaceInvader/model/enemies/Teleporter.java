package ch.yoroshiku.spaceInvader.model.enemies;

import java.util.List;
import java.util.Map;
import java.util.Random;



import ch.yoroshiku.spaceInvader.model.Ship;
import ch.yoroshiku.spaceInvader.model.enemieset.EnemyGroup;
import ch.yoroshiku.spaceInvader.model.shot.Shot;
import ch.yoroshiku.spaceInvader.util.Enemies;
import ch.yoroshiku.spaceInvader.util.Sizes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

public class Teleporter extends AbstractEnemy
{
	private static final long serialVersionUID = 1L;
	private int possiblePositionHeight;
    protected float moveTeleportSpeed = 5;
    private float teleportCounter, teleportCountDown = moveTeleportSpeed;
    private boolean teleporting = false;
    private Random random = new Random();
    private List<Map<Integer, EnemyGroup>> emergencyPlan;
    private float leftShotX, rightShotX, shotY;
    private int emergencyCounter = 0;

    public Teleporter(float x, float y, float fieldHeight, float fieldWidth, int shotFrequency
            , boolean powerUps, List<Map<Integer, EnemyGroup>> emergency)
    {
		super(x, y, Sizes.TELEPORTER_WIDTH, Sizes.TELEPORTER_HEIGHT, powerUps, Enemies.ALL_TEXTURES.get(Enemies.TELEPORTER_ID));
        leftShotX = 1.8f;
        rightShotX = 5;
        shotY = 2.6f;
        this.shootFrequency = shotFrequency;
        possiblePositionHeight = (int) (fieldHeight / 2);
        emergencyPlan = emergency;
    }
    
    public boolean move(float render)
    {
        teleportCountDown -= render;

        if(teleporting)
        {
        	if(teleportCounter > 255)
        	{
        		teleporting = false;
        		invincible = false;
        		showHealthBar = true;
        	}
            teleportCounter += 30;
        }
        if(teleportCountDown <= 0)
        {
            teleport();
            teleportCountDown = moveTeleportSpeed;
        }
        return true;
    }
    
    @Override
    public Array<Shot> shoot(Ship ship)
    {
        if (!teleporting && random.nextInt(60 / shootFrequency) == 0)
        {
        	Array<Shot> returnList = new Array<Shot>();
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
        y = possiblePositionHeight + random.nextInt(possiblePositionHeight) - Sizes.TELEPORTER_HEIGHT;
        showHealthBar = false;
    }

	@Override
	public void drawShape(ShapeRenderer shapeRenderer, float ppux, float ppuy, float offset) {
		drawHealthBar(shapeRenderer, ppux, ppuy, offset);
	}
    
}
