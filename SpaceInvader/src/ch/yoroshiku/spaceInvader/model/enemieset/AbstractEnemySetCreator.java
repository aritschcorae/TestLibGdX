package ch.yoroshiku.spaceInvader.model.enemieset;

import java.util.Random;

import ch.yoroshiku.spaceInvader.model.Ship;
import ch.yoroshiku.spaceInvader.model.enemies.PowerUpEater;
import ch.yoroshiku.spaceInvader.screen.GameScreen;

public abstract class AbstractEnemySetCreator
{

    protected Ship ship;
    protected int lvl = 1;
    protected int displayLvl = 1;
    protected int amountOfMinPowerUps = 1;
    protected int enemyKind = 0;
    protected float canvasHeight = GameScreen.DEFAULT_WORLD_HEIGHT;
    protected float canvasWidth = GameScreen.DEFAULT_WORLD_WIDTH;
    protected EnemySet enemySet;
    private Random random = new Random();
    
    public AbstractEnemySetCreator(Ship ship, EnemySet enemySet)
    {
        this.ship = ship;
        this.enemySet = enemySet;
    }

    
    public EnemyGroup createPowerUpEater()
    {
        EnemyGroup enemyRow = new EnemyGroup(false, -1, -10, 0, 1);
        enemyRow.add(new PowerUpEater(canvasWidth /2,  canvasHeight / 2, true));
        return enemyRow;
    }
    
    protected float hpMultiplier = 1, pointMultiplier = 1, powerUpMultiplier = 1;
    
    abstract public void loadEnemiesOfNextLvl();

    protected boolean createBossPowerUps()
    {
        return random.nextInt(8) == 4;
    }
    
    protected boolean createPeonPowerUps(int amountOfEnemies)
    {
        return random.nextInt(amountOfEnemies * 8) == 1;
    }

    public void setLvl(int level)
    {
        lvl = level;
        displayLvl = level + 1;
    }
    
    public void setNextlvl()
    {
        lvl ++;
        enemyKind = (lvl / 10);
    }
    
    
    public int getPlainLvl()
    {
        return lvl;
    }
    
    public void setMultiplierByDifficulty(int difficulty)
    {
        switch (difficulty)
        {
        case 5:
            pointMultiplier = 1.7f;
            hpMultiplier = 1.5f;
            amountOfMinPowerUps = 1;
            powerUpMultiplier = 0.7f;
            break;
        case 4:
            pointMultiplier = 1.5f;
            hpMultiplier = 1.3f;
            powerUpMultiplier = 0.8f;
            amountOfMinPowerUps = 1;
            break;
        case 2:
            pointMultiplier = 0.75f;
            hpMultiplier = 0.8f;
            powerUpMultiplier = 1.1f;
            amountOfMinPowerUps = 3;
            break;
        case 1:
            pointMultiplier = 0.5f;
            hpMultiplier = 0.5f;
            powerUpMultiplier = 1.3f;
            amountOfMinPowerUps = 3;
            break;
        }
    }
    
}