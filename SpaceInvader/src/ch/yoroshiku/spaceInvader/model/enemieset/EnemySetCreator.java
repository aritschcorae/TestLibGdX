package ch.yoroshiku.spaceInvader.model.enemieset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import ch.yoroshiku.spaceInvader.model.Ship;
import ch.yoroshiku.spaceInvader.model.enemies.AbstractEnemy;
import ch.yoroshiku.spaceInvader.model.enemies.BombDefuser;
import ch.yoroshiku.spaceInvader.model.enemies.Defensless;
import ch.yoroshiku.spaceInvader.model.enemies.Freezer;
import ch.yoroshiku.spaceInvader.model.enemies.Midboss;
import ch.yoroshiku.spaceInvader.model.enemies.MidbossSplash;
import ch.yoroshiku.spaceInvader.model.enemies.Peon;
import ch.yoroshiku.spaceInvader.model.enemies.Prometheus;
import ch.yoroshiku.spaceInvader.model.enemies.PrometheusWing;
import ch.yoroshiku.spaceInvader.model.enemies.Teleporter;


public class EnemySetCreator extends AbstractEnemySetCreator
{
    private Map<Integer, String[]> levelStructure;
    private Random random = new Random();
    
//    public EnemySetCreator(float canvasHeight, float canvasWidth, Ship ship, EnemySet enemySet, float zoom,
//            AbstractGameFieldView gameField)
//    {
//        super(canvasHeight, canvasWidth, ship, enemySet, zoom, gameField);
//        readLevelInformation({"a","a"});
//    }
    
//    private void readLevelInformation(String[] readData)
//    {
//        levelStructure = new HashMap<Integer, String[]>();
//        int i = 0;
//        for(String levelInformation : readData)
//        {
//            levelStructure.put(i, levelInformation.split(":"));
//            i ++;
//        }
//    }
//    
//    private Integer[] convertStringToInteger(String[] toConvertArray)
//    {
//        Integer[] resultArray = new Integer[toConvertArray.length];
//        for(int i = 0; i < toConvertArray.length; i++)
//        {
//            resultArray[i] = new Integer(toConvertArray[i].replaceAll("\t", "").trim());
//        }
//        return resultArray;
//    }
//    
//    @Override
//    public void loadEnemiesOfNextLvl()
//    {
//        enemySet.cleanUp();
//        if (levelStructure.get(lvl) != null)
//        {
//            for (String levelInfos : levelStructure.get(lvl))
//            {
//                Integer[] attributes = convertStringToInteger(levelInfos.trim().split(";"));
//                if (groupMapping.get(1).contains(attributes[1])) // check if peon
//                {
//                    enemySet.addEnemyGroup(createPeonSet(attributes[2], attributes[3], attributes[4], attributes[5],
//                            attributes[6], attributes[7], attributes[0], attributes[1]));
//                }
//                else if (groupMapping.get(0).contains(attributes[1])) // check if midboss
//                {
//                    enemySet.addEnemyGroup(createMidBossSet(attributes[2], attributes[3], attributes[4], attributes[5],
//                            attributes[6], attributes[7], attributes[0], attributes[1]));
//                }
//                else if (groupMapping.get(3).contains(attributes[1])) // check if post thrower
//                {
//                    enemySet.addEnemyGroup(createThrower(attributes[3], attributes[4], attributes[5], attributes[6],
//                            attributes[7], attributes[0], attributes[1]));
//                }
//                else if (groupMapping.get(2).contains(attributes[1])) // check if prometheus
//                {
//                    enemySet.addEnemyGroup(createBigBoss(attributes[3], attributes[4], attributes[5], attributes[6],
//                            attributes[7], attributes[0], attributes[1]));
//                }
//                else if (groupMapping.get(4).contains(attributes[1])) // check if teleporter
//                {
//                    enemySet.addEnemyGroup(createTeleporterSet(attributes[2], attributes[3], attributes[4],
//                            attributes[5], attributes[6], attributes[7], attributes[0], attributes[1], enemySet));
//                }
//                else if (groupMapping.get(5).contains(attributes[1])) // check if splasher
//                {
//                    enemySet.addEnemyGroup(createSplasher(attributes[2], attributes[3], attributes[4], attributes[5],
//                            attributes[6], attributes[7], attributes[0], attributes[1]));
//                }
//            }
//            enemySet.addPowerUpEater(createPowerUpEater());
//        }
//        else
//        {
//            enemySet.setEnemies(null);
//        }
//    }
//   
//    private Map<Integer, EnemyGroup> createTeleporterSet(int amount, int timeAppear, int kindAppear, int health,
//            int points, int frequency, int id, int enemyBitmap, EnemySet enemySet)
//    {
//        Map<Integer, EnemyGroup> enemies = new HashMap<Integer, EnemyGroup>();
//        EnemyGroup enemyRow = new EnemyGroup(true, id, timeAppear, kindAppear, 4);
//        Coordinates coord = new Coordinates(canvasWidth / 2 - allBitmap.get(enemyBitmap).getWidth() / 2,
//                (float) random.nextInt(100) + allBitmap.get(enemyBitmap).getHeight());
//        List<Map<Integer, EnemyGroup>> helperEnemies = new ArrayList<Map<Integer, EnemyGroup>>();
//
//        helperEnemies.add(createPeonSet(amount, -1, 1, health / 14, health, frequency, 2, AbstractEnemySetCreator.PEON_EASY_ID));
//        helperEnemies.add(createPeonSet(amount, -1, 1, health / 12, health, frequency, 2, AbstractEnemySetCreator.PEON_NORMAL_ID));
//        helperEnemies.add(createPeonSet(amount, -1, 1, health / 10, health, frequency, 2, AbstractEnemySetCreator.PEON_HARD_ID));
//        
//        AbstractEnemy tempEnemy = new Teleporter(allBitmap.get(enemyBitmap), coord, canvasHeight, canvasWidth,
//                frequency, createBossPowerUps(coord), helperEnemies, zoom);
//        tempEnemy.setHealth((int) (health * hpMultiplier));
//        tempEnemy.setPoints((int) (points * pointMultiplier));
//
//        for(Map<Integer, EnemyGroup> helperGroup : helperEnemies)
//            enemySet.addEnemyGroup(helperGroup);
//
//        enemyRow.add(tempEnemy);
//        enemies.put(4, enemyRow);
//        return enemies;
//    }
//
//    private Map<Integer, EnemyGroup> createPeonSet(int rows, int timeAppear, int kindAppear, int health, int points, int freq, int id, int enemyKind)
//    {
//        Map<Integer, EnemyGroup> enemies = new HashMap<Integer, EnemyGroup>();
//        for(int i = 3; i > 0; i--)
//        {   
//            EnemyGroup enemyRow = new EnemyGroup(true, id, timeAppear, kindAppear, 4);
//            enemies.put(i, enemyRow);
//        }
//        int group;
//        for(int i = rows; i > 0; i--)
//        {   
//            group = 1;
//            int extraBorder = 10 + ((i%2 - 1) * allBitmap.get(enemyKind).getWidth() * -1) / 2; 
//            for(int inLine = 0, enemieCounter = 0; inLine < 9 + i%2; inLine++, enemieCounter ++)
//            {
//                Coordinates coord = new Coordinates((float)inLine * allBitmap.get(enemyKind).getWidth() + 3 + extraBorder + (inLine * 2),
//                        (float)((i + 4) * allBitmap.get(enemyKind).getHeight() + i * 4));
//                AbstractEnemy tempEnemy = new Peon(allBitmap.get(enemyKind), coord, createPeonPowerUps(rows));
//                tempEnemy.setHealth((int) (health * hpMultiplier));
//                tempEnemy.setPoints((int) (points * pointMultiplier));
//                tempEnemy.setShotFrequency(freq);
//                if(enemieCounter > 2 && group < 3)
//                {
//                    group ++;
//                    enemieCounter = 0;
//                }
//                enemies.get(group).add(tempEnemy);
//            }
//        }
//        switch (kindAppear)
//        {
//        case 1:
//            for (int i = 1; i < 4; i++)
//            {
//                int movementY = 35;
//                int movementX = 50 * (i - 2);
//                float endX = enemies.get(i).getX();
//                float endY = enemies.get(i).getY();
//                for(AbstractEnemy enemy : enemies.get(i))
//                {
//                    enemy.repositionX(movementX);
//                    enemy.repositionY(-movementY);
//                }
//                enemies.get(i).setAppearanceVariable(-(movementX / 5), movementY / 5, endX, endY);
//            }
//            break;
//        }
//        
//        for(int i = 0; i < amountOfMinPowerUps;)
//        {
//            EnemyGroup tempList = enemies.get(random.nextInt(3) + 1);
//            AbstractEnemy tempEnemy = tempList.getRandomMember();
//            if(!tempEnemy.hasPowerUp())
//            {
//                tempEnemy.addPowerUp();
//                i++;
//            }
//        }
//        return enemies;
//    }
//
//    private Map<Integer, EnemyGroup> createMidBossSet(int amount, int timeAppear, int kindAppear, int health,
//            int points, int frequency, int id, int enemyBitmap)
//    {
//        Map<Integer, EnemyGroup> enemies = new HashMap<Integer, EnemyGroup>();
//        for(int i = 0; i < amount; i++)
//        {
//            EnemyGroup enemyRow = new EnemyGroup(true, id, timeAppear, kindAppear, 4);
//            Coordinates coord = new Coordinates(canvasWidth / (amount + 1) * (i+1) - allBitmap.get(enemyBitmap).getWidth() / 2
//                    , (float)random.nextInt(100) + allBitmap.get(enemyBitmap).getHeight());
//            AbstractEnemy tempEnemy = new Midboss(allBitmap.get(enemyBitmap), coord,
//                    canvasHeight, canvasWidth, frequency, createBossPowerUps(coord));
//            tempEnemy.setHealth((int) (health * hpMultiplier));
//            tempEnemy.setPoints((int) (points * pointMultiplier));
//
//            enemyRow.add(tempEnemy);
//            enemies.put(i + 1, enemyRow);
//        }
//        return enemies;
//    }
//
//    private Map<Integer, EnemyGroup> createSplasher(int amount, int timeAppear, int kindAppear, int health,
//            int points, int frequency, int id, int enemyBitmap)
//    {
//        Map<Integer, EnemyGroup> enemies = new HashMap<Integer, EnemyGroup>();
//        for(int i = 0; i < amount; i++)
//        {
//            EnemyGroup enemyRow = new EnemyGroup(true, id, timeAppear, kindAppear, 4);
//            Coordinates coord = new Coordinates(canvasWidth / (amount + 1) * (i+1) - allBitmap.get(enemyBitmap).getWidth() / 2
//                    , (float)random.nextInt(100) + allBitmap.get(enemyBitmap).getHeight());
//            AbstractEnemy tempEnemy = new MidbossSplash(allBitmap.get(enemyBitmap), coord,
//                    canvasHeight, canvasWidth, frequency, createBossPowerUps(coord));
//            tempEnemy.setHealth((int) (health * hpMultiplier));
//            tempEnemy.setPoints((int) (points * pointMultiplier));
//
//            enemyRow.add(tempEnemy);
//            enemies.put(i + 1, enemyRow);
//        }
//        return enemies;
//    }
//    
//
//    private Map<Integer, EnemyGroup> createThrower(int timeAppear, int kindAppear, int health, int points,
//            int frequency, int id, int enemyBitmap)
//    {
//        health *= hpMultiplier;
//        points *= pointMultiplier;
//        Map<Integer, EnemyGroup> enemies = new HashMap<Integer, EnemyGroup>();
//        EnemyGroup enemyGroup = new EnemyGroup(true, id, timeAppear, kindAppear, 1);
//
//        Coordinates coord = new Coordinates(canvasWidth / 2, 5f);
//        if(enemyBitmap == FREEZER_ID)
//        {
//            enemyGroup.add(new Freezer(allBitmap.get(enemyBitmap), allBitmap.get(enemyBitmap + 1), allBitmap
//                .get(enemyBitmap + 1), coord, 3, true, canvasWidth, canvasHeight, gameField, zoom));
//        }
//        else if(enemyBitmap == BOMB_DEFUSER_ID)
//        {
//            enemyGroup.add(new BombDefuser(allBitmap.get(enemyBitmap), allBitmap.get(enemyBitmap + 1), allBitmap
//                    .get(enemyBitmap + 1), coord, 3, true, canvasWidth, canvasHeight, gameField, zoom));
//        }
//        else if(enemyBitmap == DEFENSLESS_ID)
//        {
//            enemyGroup.add(new Defensless(allBitmap.get(enemyBitmap), allBitmap.get(enemyBitmap + 1), allBitmap
//                    .get(enemyBitmap + 1), coord, 3, true, canvasWidth, canvasHeight, gameField, zoom));
//        }
//        enemies.put(id, enemyGroup);
//        return enemies;
//    }
//    
//    private Map<Integer, EnemyGroup>createBigBoss(int timeAppear, int kindAppear, int health, int points,
//            int frequency, int id, int enemyBitmap)
//    {
//        health *= hpMultiplier;
//        points *= pointMultiplier;
//        Map<Integer, EnemyGroup> enemies = new HashMap<Integer, EnemyGroup>();
//        EnemyGroup enemyGroup = new EnemyGroup(true, id, timeAppear, kindAppear, 4);
//        
//        float deplacement = canvasWidth / 2 - allBitmap.get(PROMETHEUS_ID).getWidth() - allBitmap.get(PROMETHEUS_ID + 2).getWidth() / 2;
//        
//        Coordinates coord = new Coordinates(deplacement, (float)50);
//        PrometheusWing tempEnemyLeft = new PrometheusWing(allBitmap.get(PROMETHEUS_ID + 1), coord, createPeonPowerUps(1), true, zoom, allBitmap.get(EXPLOSION_ID));
//        enemyGroup.add(tempEnemyLeft);
//        
//        coord = new Coordinates(deplacement
//                + ((allBitmap.get(PROMETHEUS_ID).getWidth() + allBitmap.get(PROMETHEUS_ID + 2).getWidth())), (float) 50);
//        PrometheusWing tempEnemyRight = new PrometheusWing(allBitmap.get(PROMETHEUS_ID + 2), coord,
//                createPeonPowerUps(1), false, zoom, allBitmap.get(EXPLOSION_ID));
//        enemyGroup.add(tempEnemyRight);
//
//        coord = new Coordinates(deplacement + (allBitmap.get(PROMETHEUS_ID).getWidth()), (float) 50);
//        AbstractEnemy tempEnemy = new Prometheus(allBitmap.get(PROMETHEUS_ID), coord, createPeonPowerUps(1),
//                tempEnemyLeft, tempEnemyRight, 400, 6000, zoom, allBitmap.get(EXPLOSION_ID));
//        enemyGroup.add(tempEnemy);
//            
//        enemies.put(id, enemyGroup);
//        return enemies;
//    }
}