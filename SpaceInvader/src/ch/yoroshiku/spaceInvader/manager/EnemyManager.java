package ch.yoroshiku.spaceInvader.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import ch.yoroshiku.spaceInvader.model.Explosion;
import ch.yoroshiku.spaceInvader.model.PowerUp;
import ch.yoroshiku.spaceInvader.model.Ship;
import ch.yoroshiku.spaceInvader.model.enemies.AbstractEnemy;
import ch.yoroshiku.spaceInvader.model.enemies.PowerUpEater;
import ch.yoroshiku.spaceInvader.model.enemieset.EnemyGroup;
import ch.yoroshiku.spaceInvader.model.shot.Shot;
import ch.yoroshiku.spaceInvader.util.Sizes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

public class EnemyManager extends Manager {

    private boolean cleanedUp = true;
    private Map<Integer, EnemyGroup> enemies;
    private EnemyGroup powerUpEaters;
	private EnemyGroup toRemoveEnemies;
    private Random random = new Random();
    private static Float ENEMY_SHOT_SLOW_DOWN = 0.1f;
    private float lastShot = 0;
    
    public EnemyManager()
    {
        enemies = new HashMap<Integer, EnemyGroup>();
		toRemoveEnemies = new EnemyGroup(false, 1, 0, 0, 0);
    }

	public void drawSprite(SpriteBatch batch, float ppux, float ppuy, float border) {
		for (EnemyGroup enemyGroup : enemies.values()){
			if(enemyGroup.isVisible())
			{
				for(AbstractEnemy enemy : enemyGroup){
	        		if(enemy.isVisible()){
	        			enemy.drawSprite(batch, ppux, ppuy, border);
	        		}
	        	}
			}
		}
	}

	public void drawHealthBar(ShapeRenderer shapeRenderer, float ppux, float ppuy, float border) {
        for (EnemyGroup enemyGroup : enemies.values()){
        	if(enemyGroup.isVisible())
        	for(AbstractEnemy enemy : enemyGroup){
        		if(enemy.isShowHealthBar() && enemy.isVisible())
        			enemy.drawHealthBar(shapeRenderer, ppux, ppuy, border);
        	}
        }
	}
	
	public void move(float delta) {
        for (EnemyGroup enemyGroup : enemies.values())
        {
			if (!enemyGroup.isVisible()) {
				enemyGroup.appear(delta);
				continue;
			}
			enemyGroup.move(delta);
        }
        lastShot += delta;
	}

    
    public Map<Integer, EnemyGroup> getEnemies()
    {
        return enemies;
    }

    public void addEnemyGroup(EnemyGroup enemyGroup)
    {
        enemies.put(enemies.keySet().size() + 1, enemyGroup);
        cleanedUp = false;
    }

    public void addEnemyGroup(Map<Integer, EnemyGroup> enemieGroups)
    {
        
        for(EnemyGroup group : enemieGroups.values())
        {
            addEnemyGroup(group);
        }
    }
    
    public void setEnemies(Map<Integer, EnemyGroup> enemies)
    {
        this.enemies = enemies;
        cleanedUp = false;
    }
    

	public void checkForCollision(ShotManager shotManager) {
		checkForExplosionCollision(shotManager.getExplosions());
        iterateShotList(shotManager.getShotsBombs(), true);
        iterateShotList(shotManager.getAllShots(), false);		
	}    

    private void iterateShotList(final Array<Shot> shots, final boolean bomb)
    {
        for (final Shot shoot : shots)
        {
            if (shoot.y + shoot.getHeight() < Sizes.DEFAULT_WORLD_HEIGHT 
            		&& shoot.x >= 0 
            		&& shoot.x <= Sizes.DEFAULT_WORLD_WIDTH)
            {
                for (final EnemyGroup enemyGroup : enemies.values())
                {
                    if (!enemyGroup.isEmpty() && enemyGroup.isAppeared()
                    		&& shoot.overlaps(enemyGroup.getBounds()))
                    {
                        if (checkHit(shoot, enemyGroup, bomb))
                        {
                            remove(enemyGroup, toRemoveEnemies);
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * @return true if hitted
     */
    public boolean checkHit(final Shot shoot, final EnemyGroup enemies, final boolean bomb)
    {
        for (final AbstractEnemy enemy : enemies)
        {
            if (shoot.overlaps(enemy))
            {
                if((bomb && enemy.bombDamage(shoot.getDamage()))
                	|| (!bomb && enemy.lowerHealth(shoot.getDamage())))
                {
                    enemyKilled(enemy); 
                }
                if (!shoot.isSimpleShot())
                {
                    final float x;
                    final float y;
                    if (shoot.x <= enemy.x)
                        x = enemy.x;
                    else
                        x = shoot.getX();
                    if (shoot.y <= enemy.y)
                        y = enemy.y;
                    else
                        y = shoot.getY();
//                    final Explosion newExplosion = new Explosion(x, y, 40 * ship.getDamage());
//                    explosions.add(newExplosion);
//                    toDeleteShots.add(shoot);
                }
                else
                {
                    shoot.setY(Sizes.DEFAULT_WORLD_HEIGHT + 20);
                }
                return true;
            }
        }
        return false;
    }

    public void enemyKilled(final AbstractEnemy enemy)
    {
        for (final AbstractEnemy destroyedEnemy : enemy.getDestroyedEnemies())
        {
            toRemoveEnemies.add(destroyedEnemy);
            if (enemy.hasPowerUp())
            {
//                PowerUp powerUp = PowerUpFactory.createPowerUp(ship, enemySetCreator.getPlainLvl());
//                powerUp.setCoordinates(enemy.getX() + enemy.getWidth() / 2, (enemy.getY() + enemy.getHeight() / 2));
//                powerUps.add(powerUp);
//                registerPowerUp(powerUp);
            }
        }
    }

    public void checkForExplosionCollision(Array<Explosion> explosions)
    {
        for (final Explosion explosion : explosions)
        {
            for (final EnemyGroup enemyGroup : enemies.values())
            {
                if (enemyGroup.isAppeared())
                {
                    for (final AbstractEnemy enemy : enemyGroup)
                    {
                        if (explosion.getOuterRadius(1).contains(enemy.x, enemy.y)
                        		&& !explosion.contains(enemy.x, enemy.y))
                        {
                            if(enemy.bombDamage(explosion.getDamage()))
                            {
                                enemyKilled(enemy);
                            }
                        }
                    }
                }
                remove(enemyGroup, toRemoveEnemies);
            }
        }
    }


    public int removeEnemies()
    {
    	int points = 0;
        for (AbstractEnemy enemy : toRemoveEnemies)
        {
            points += enemy.getPoints();
        }
        toRemoveEnemies.clear();
        return points;
    }
    
    public void cleanUp()
    {
        if(!cleanedUp  && enemies != null)
        {
            if(powerUpEaters != null && powerUpEaters.isVisible())
            {
                for(AbstractEnemy eater : powerUpEaters)
                {
                    ((PowerUpEater)eater).releasePowerUp();
                }
            }
            enemies.clear();
        }
        cleanedUp = true;
    }


    public boolean isCleanedUp()
    {
        return cleanedUp;
    }
    
    public void addPowerUpEater(EnemyGroup powerUpEaters)
    {
        this.powerUpEaters = powerUpEaters;
        enemies.put(enemies.keySet().size() + 1, this.powerUpEaters);
    }
    
    
    public Array<Shot> shot(Ship ship)
    {
    	Array<Shot> returnList = new Array<Shot>();
        if(lastShot >= ENEMY_SHOT_SLOW_DOWN)
        {
            for(EnemyGroup enemyGroup : enemies.values())
            {
                if(enemyGroup.isAppeared())
                {
                    for(AbstractEnemy enemy : enemyGroup)
                    {
                        returnList.addAll(enemy.shoot(ship));
                    }
                }
            }
            lastShot = 0;
        }
        return returnList;
    }
    
    public boolean existsEnemies()
    {
        for(Integer row : enemies.keySet())
        {
            if(enemies.get(row).isNeedForLevel() && enemies.get(row).size() > 0)
                return true;
        }
        return false;
    }
    
    public void registerPowerUp(PowerUp powerUp)
    {
        if(random.nextInt(5) == 2)
        {
            powerUpEaters.setVisible(true);
            for(AbstractEnemy eater : powerUpEaters)
            {
                if(((PowerUpEater)eater).eatPowerUp(powerUp))
                {
                    return;
                }
            }
        }
    }
    
    public void remove(EnemyGroup enemiesGroup, EnemyGroup toRemoveEnemies)
    {
        enemiesGroup.removeAll(toRemoveEnemies);
        if(enemiesGroup.isEmpty())
        {
            enemies.remove(enemiesGroup);
            if(isIdExtinct(enemiesGroup.getId()))
            for(EnemyGroup group : enemies.values())
            {
                if(!group.isVisible() && enemiesGroup.getId() == group.getAppearAfterId())
                {
                    group.appear();
                }
            }
        }
    }
    
    private boolean isIdExtinct(int id)
    {
        for(EnemyGroup group : enemies.values())
        {
            if(!group.isEmpty() && group.getId() == id)
                return false;
        }
        return true;
    }

}
