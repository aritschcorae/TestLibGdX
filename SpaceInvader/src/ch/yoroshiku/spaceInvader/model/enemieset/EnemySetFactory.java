package ch.yoroshiku.spaceInvader.model.enemieset;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import ch.yoroshiku.spaceInvader.manager.EnemyManager;
import ch.yoroshiku.spaceInvader.model.Ship;
import ch.yoroshiku.spaceInvader.model.enemies.AbstractEnemy;
import ch.yoroshiku.spaceInvader.model.enemies.BombDefuser;
import ch.yoroshiku.spaceInvader.model.enemies.Defensless;
import ch.yoroshiku.spaceInvader.model.enemies.Freezer;
import ch.yoroshiku.spaceInvader.model.enemies.Midboss;
import ch.yoroshiku.spaceInvader.model.enemies.MidbossSplash;
import ch.yoroshiku.spaceInvader.model.enemies.Peon;
import ch.yoroshiku.spaceInvader.model.enemies.PowerUpEater;
import ch.yoroshiku.spaceInvader.model.enemies.Prometheus;
import ch.yoroshiku.spaceInvader.model.enemies.PrometheusWing;
import ch.yoroshiku.spaceInvader.model.enemies.Teleporter;
import ch.yoroshiku.spaceInvader.util.Enemies;
import ch.yoroshiku.spaceInvader.util.Sizes;

import com.badlogic.gdx.Gdx;


public class EnemySetFactory
{
    private Map<Integer, String[]> levelStructure;
    protected Ship ship;
    protected int lvl = 1;
    protected int amountOfMinPowerUps = 1;
    protected int enemyKind = 0;
    protected float canvasHeight = Sizes.DEFAULT_WORLD_HEIGHT;
    protected float canvasWidth = Sizes.DEFAULT_WORLD_WIDTH;
    protected float hpMultiplier = 1, pointMultiplier = 1, powerUpMultiplier = 1;
    protected EnemyManager enemySet;
    private Random random = new Random();
    
	public void setNextlvl() {
		lvl++;
		enemyKind = (lvl / 10);
	}

	public int getPlainLvl() {
		return lvl;
	}

    public EnemySetFactory(Ship ship, EnemyManager enemySet) throws IOException
    {
        this.ship = ship;
        this.enemySet = enemySet;
        readLevelInformation(Gdx.files.internal("level/levels.txt").readString());
    }
    
    private void readLevelInformation(String readData) throws IOException
    {
        levelStructure = new HashMap<Integer, String[]>();
        int i = 0;
        
        for(String line : readData.split("--"))
        {
        	if(!line.startsWith("<"))
        	{
        		String[] lvl = line.split("#");
        		i = new Integer(lvl[0].trim());
        		levelStructure.put(i, lvl[1].split(":"));
        	}
        }
	}
    
    private Integer[] convertStringToInteger(String[] toConvertArray)
    {
        Integer[] resultArray = new Integer[toConvertArray.length];
        for(int i = 0; i < toConvertArray.length; i++)
        {
            resultArray[i] = new Integer(toConvertArray[i].replaceAll("\t", "").trim());
        }
        return resultArray;
    }
    
    public void loadEnemiesOfNextLvl()
    {
        enemySet.cleanUp();
        if (levelStructure.get(lvl) != null)
        {
            for (String levelInfos : levelStructure.get(lvl))
            {
                Integer[] attributes = convertStringToInteger(levelInfos.trim().split(";"));
                if (Enemies.GROUP_MAPPING.get(1).contains(attributes[1])) // check if peon
                {
                    enemySet.addEnemyGroup(createPeonSet(attributes[2], attributes[3], attributes[4], attributes[5],
                            attributes[6], attributes[7], attributes[0], attributes[1]));
                }
                else if (Enemies.GROUP_MAPPING.get(0).contains(attributes[1])) // check if midboss
                {
                    enemySet.addEnemyGroup(createMidBossSet(attributes[2], attributes[3], attributes[4], attributes[5],
                            attributes[6], attributes[7], attributes[0], attributes[1]));
                }
                else if (Enemies.GROUP_MAPPING.get(3).contains(attributes[1])) // check if post thrower
                {
                    enemySet.addEnemyGroup(createThrower(attributes[3], attributes[4], attributes[5], attributes[6],
                            attributes[7], attributes[0], attributes[1]));
                }
                else if (Enemies.GROUP_MAPPING.get(2).contains(attributes[1])) // check if prometheus
                {
                    enemySet.addEnemyGroup(createBigBoss(attributes[3], attributes[4], attributes[5], attributes[6],
                            attributes[7], attributes[0], attributes[1]));
                }
                else if (Enemies.GROUP_MAPPING.get(4).contains(attributes[1])) // check if teleporter
                {
                    enemySet.addEnemyGroup(createTeleporterSet(attributes[2], attributes[3], attributes[4],
                            attributes[5], attributes[6], attributes[7], attributes[0], attributes[1], enemySet));
                }
                else if (Enemies.GROUP_MAPPING.get(5).contains(attributes[1])) // check if splasher
                {
                    enemySet.addEnemyGroup(createSplasher(attributes[2], attributes[3], attributes[4], attributes[5],
                            attributes[6], attributes[7], attributes[0], attributes[1]));
                }
            }
            enemySet.addPowerUpEater(createPowerUpEater());
        }
        else
        {
            enemySet.setEnemies(null);
        }
    }
   
    private Map<Integer, EnemyGroup> createTeleporterSet(int amount, int timeAppear, int kindAppear, int health,
            int points, int frequency, int id, int enemyBitmap, EnemyManager enemySet)
    {
        Map<Integer, EnemyGroup> enemies = new HashMap<Integer, EnemyGroup>();
        EnemyGroup enemyRow = new EnemyGroup(true, id, timeAppear, kindAppear, 4);
        List<Map<Integer, EnemyGroup>> helperEnemies = new ArrayList<Map<Integer, EnemyGroup>>();

        final float x = (canvasWidth / 2 - Enemies.ENEMY_WIDTH.get(enemyBitmap) / 2);
        final float y = canvasHeight - 3 * Enemies.ENEMY_HEIGHT.get(enemyBitmap);
        helperEnemies.add(createPeonSet(amount, -1, 1, health / 14, health, frequency, 2, Enemies.PEON_EASY_ID));
        helperEnemies.add(createPeonSet(amount, -1, 1, health / 12, health, frequency, 2, Enemies.PEON_NORMAL_ID));
        helperEnemies.add(createPeonSet(amount, -1, 1, health / 10, health, frequency, 2, Enemies.PEON_HARD_ID));
        
        AbstractEnemy tempEnemy = new Teleporter(x,y, canvasHeight, canvasWidth,
                frequency, createBossPowerUps(), helperEnemies);
        tempEnemy.setHealth((int) (health * hpMultiplier));
        tempEnemy.setPoints((int) (points * pointMultiplier));

        for(Map<Integer, EnemyGroup> helperGroup : helperEnemies)
            enemySet.addEnemyGroup(helperGroup);

        enemyRow.add(tempEnemy);
        enemies.put(4, enemyRow);
        return enemies;
    }

    private Map<Integer, EnemyGroup> createPeonSet(int rows, int timeAppear, int kindAppear, int health, int points, int freq, int id, int enemyKind)
    {
        Map<Integer, EnemyGroup> enemies = new HashMap<Integer, EnemyGroup>();
        for(int i = 3; i > 0; i--)
        {   
            EnemyGroup enemyRow = new EnemyGroup(true, id, timeAppear, kindAppear, 4);
            enemies.put(i, enemyRow);
        }
        int group;
        for(int row = rows; row > 0; row--) // starts with lowest row
        {   
            group = 1;
			final float extraBorder = Sizes.DEFAULT_WORLD_WIDTH / 2
					- (Enemies.ENEMY_WIDTH.get(enemyKind) * 10 + 4.5f) / 2 - (row%2 - 1) * Enemies.ENEMY_WIDTH.get(enemyKind) / 2;
			for (int inLine = 0, enemieCounter = 0; inLine < 9 + row % 2; inLine++, enemieCounter++)
            {
				final float x = inLine * Enemies.ENEMY_WIDTH.get(enemyKind) + extraBorder + (inLine * 0.5f);
                final float y = canvasHeight - 5 - row * Enemies.ENEMY_HEIGHT.get(enemyKind) - row * 1.5f;
				final AbstractEnemy tempEnemy = new Peon(x, y, createPeonPowerUps(rows), Enemies.ALL_TEXTURES.get(enemyKind));
                tempEnemy.setHealth((int) (health * hpMultiplier));
                tempEnemy.setPoints((int) (points * pointMultiplier));
                tempEnemy.setShotFrequency(freq);
                if(enemieCounter > 2 && group < 3)
                {
                    group ++;
                    enemieCounter = 0;
                }
                enemies.get(group).add(tempEnemy);
            }
        }
        switch (kindAppear)
        {
        case 1:
            for (int i = 1; i <= 3; i++)
            {
                final float movementY = -6;
                final float movementX = 10 * (i - 2);
                final float endX = enemies.get(i).getX();
                final float endY = enemies.get(i).getY();
                for(AbstractEnemy enemy : enemies.get(i))
                {
                    enemy.x -= movementX;
                    enemy.y -= movementY;
                }
                enemies.get(i).setAppearanceVariable(movementX / 16, movementY / 16, endX, endY);
            }
            break;
        }
        
        for(int i = 0; i < amountOfMinPowerUps;)
        {
            final EnemyGroup tempList = enemies.get(random.nextInt(3) + 1);
            final AbstractEnemy tempEnemy = tempList.getRandomMember();
            if(!tempEnemy.hasPowerUp())
            {
                tempEnemy.addPowerUp();
                i++;
            }
        }
        return enemies;
    }

    private Map<Integer, EnemyGroup> createMidBossSet(int amount, int timeAppear, int kindAppear, int health,
            int points, int frequency, int id, int enemyBitmap)
    {
        Map<Integer, EnemyGroup> enemies = new HashMap<Integer, EnemyGroup>();
        for(int i = 0; i < amount; i++)
        {
            EnemyGroup enemyRow = new EnemyGroup(true, id, timeAppear, kindAppear, 4);
            final float x = canvasWidth / (amount + 1) * (i+1) - Sizes.MIDBOSS_WIDTH / 2;
            final float y = (canvasHeight / 4 * 3)  + random.nextInt(10) - 5;
            AbstractEnemy tempEnemy = new Midboss(x,y, canvasHeight, canvasWidth, frequency, 
            		createBossPowerUps(), Enemies.ALL_TEXTURES.get(enemyBitmap));
            tempEnemy.setHealth((int) (health * hpMultiplier));
            tempEnemy.setPoints((int) (points * pointMultiplier));
            enemyRow.add(tempEnemy);
            enemies.put(i + 1, enemyRow);
        }
        return enemies;
    }

    private Map<Integer, EnemyGroup> createSplasher(int amount, int timeAppear, int kindAppear, int health,
            int points, int frequency, int id, int enemyBitmap)
    {
        Map<Integer, EnemyGroup> enemies = new HashMap<Integer, EnemyGroup>();
        for(int i = 0; i < amount; i++)
        {
            EnemyGroup enemyRow = new EnemyGroup(true, id, timeAppear, kindAppear, 4);
            final float x = canvasWidth / (amount + 1) * (i+1) - Enemies.ENEMY_WIDTH.get(Enemies.SPLASHER_ID) / 2;
            final float y = (canvasHeight / 4 * 3)  + random.nextInt(10) - 5;
            AbstractEnemy tempEnemy = new MidbossSplash(x, y, frequency, createBossPowerUps());
            tempEnemy.setHealth((int) (health * hpMultiplier));
            tempEnemy.setPoints((int) (points * pointMultiplier));

            enemyRow.add(tempEnemy);
            enemies.put(i + 1, enemyRow);
        }
        return enemies;
    }
    

    private Map<Integer, EnemyGroup> createThrower(int timeAppear, int kindAppear, int health, int points,
            int frequency, int id, int enemyBitmap)
    {
        health *= hpMultiplier;
        points *= pointMultiplier;
        Map<Integer, EnemyGroup> enemies = new HashMap<Integer, EnemyGroup>();
        EnemyGroup enemyGroup = new EnemyGroup(true, id, timeAppear, kindAppear, 1);

		final float x = (canvasWidth / 2) - (Sizes.THROWER_WIDTH / 2);
		final float y = canvasHeight - Sizes.POST_HEIGHT * 2;
		if (enemyBitmap == Enemies.FREEZER_ID) {
			enemyGroup.add(new Freezer(x, y, 3, true, canvasWidth, canvasHeight));
		} else if (enemyBitmap == Enemies.BOMB_DEFUSER_ID) {
			enemyGroup.add(new BombDefuser(x, y, 3, true, canvasWidth, canvasHeight));
		} else if (enemyBitmap == Enemies.DEFENSLESS_ID) {
			enemyGroup.add(new Defensless(x, y, 3, true, canvasWidth, canvasHeight));
		}
		enemies.put(id, enemyGroup);
        return enemies;
    }
    
    private Map<Integer, EnemyGroup>createBigBoss(int timeAppear, int kindAppear, int health, int points,
            int frequency, int id, int enemyBitmap)
    {
        health *= hpMultiplier;
        points *= pointMultiplier;
        Map<Integer, EnemyGroup> enemies = new HashMap<Integer, EnemyGroup>();
        EnemyGroup enemyGroup = new EnemyGroup(true, id, timeAppear, kindAppear, 4);
        
        final float xDeplacement = canvasWidth / 2 - (Sizes.PROMETHEUS_WIDTH / 2);
        final float yDeplacement = canvasHeight / 6 * 4;
        float x = xDeplacement - Sizes.WING_WIDTH;
        float y = yDeplacement + Sizes.PROMETHEUS_HEIGHT - Sizes.WING_HEIGHT;
        PrometheusWing leftWing = new PrometheusWing(x,y, createPeonPowerUps(1), 1);
        enemyGroup.add(leftWing);
        
        x = xDeplacement + Sizes.PROMETHEUS_WIDTH;
		PrometheusWing rightWing = new PrometheusWing(x, y, createPeonPowerUps(1), 2);
        enemyGroup.add(rightWing);

        x = xDeplacement;
        y = yDeplacement;
        AbstractEnemy tempEnemy = new Prometheus(x,y, createPeonPowerUps(1),
                leftWing, rightWing, 400, 6000);
        enemyGroup.add(tempEnemy);
            
        enemies.put(id, enemyGroup);
        return enemies;
    }
    
    public EnemyGroup createPowerUpEater()
    {
        EnemyGroup enemyRow = new EnemyGroup(false, -1, -10, 0, 1);
        enemyRow.add(new PowerUpEater(canvasWidth /2,  canvasHeight / 2, true));
        return enemyRow;
    }
    
    protected boolean createBossPowerUps()
    {
        return random.nextInt(8) == 4;
    }
    
    protected boolean createPeonPowerUps(int amountOfEnemies)
    {
        return random.nextInt(amountOfEnemies * 8) == 1;
    }

}