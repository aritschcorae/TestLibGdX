package ch.yoroshiku.spaceInvader.model.enemieset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import ch.yoroshiku.spaceInvader.model.PowerUp;
import ch.yoroshiku.spaceInvader.model.Ship;
import ch.yoroshiku.spaceInvader.model.Shot;
import ch.yoroshiku.spaceInvader.model.enemies.AbstractEnemy;
import ch.yoroshiku.spaceInvader.model.enemies.PowerUpEater;

public class EnemySet
{
    private boolean cleanedUp = true;
    private Map<Integer, EnemyGroup> enemies;
    private EnemyGroup powerUpEaters;
    private int enemyWait = 0;
    private Random random = new Random();
    private static Integer ENEMY_SHOT_SLOW_DOWN = 4;
    
    public EnemySet()
    {
        enemies = new HashMap<Integer, EnemyGroup>();
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
    
    public void moveEnemies(long currentTime)
    {
        for (EnemyGroup enemyGroup : enemies.values())
        {
            if(enemyWait % enemyGroup.getSlowDown() == 0)
            {
                if (!enemyGroup.isVisible())
                {
                    enemyGroup.appear(currentTime);
                    continue;
                }
                enemyGroup.move();
            }
        }
        enemyWait ++;
    }
    
    public List<Shot> shoot(Ship ship)
    {
        List<Shot> returnList = new ArrayList<Shot>();
        if(enemyWait % ENEMY_SHOT_SLOW_DOWN == 0)
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