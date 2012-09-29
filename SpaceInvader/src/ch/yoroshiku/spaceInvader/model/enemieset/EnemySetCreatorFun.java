package ch.yoroshiku.spaceInvader.model.enemieset;
//package ch.yoroshiku.spaceInvader.model.enemieset;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;
//
//import ch.yoroshiku.spaceInvader.model.Coordinates;
//import ch.yoroshiku.spaceInvader.model.Ship;
//import ch.yoroshiku.spaceInvader.model.enemies.AbstractEnemy;
//import ch.yoroshiku.spaceInvader.model.enemies.Midboss;
//import ch.yoroshiku.spaceInvader.model.enemies.Peon;
//
//public class EnemySetCreatorFun extends AbstractEnemySetCreator
//{
//    private double groundHealth = 1;
//    private int bosslvl = 1;
//    private Random random = new Random();
//
//    public EnemySetCreatorFun(float canvasHeight, float canvasWidth, Ship ship, EnemySet enemySet, float zoom, AbstractGameFieldView gameField)
//    {
//        super(canvasHeight, canvasWidth, ship, enemySet, zoom, gameField);
//    }
//    
//    @Override
//    public void loadEnemiesOfNextLvl()
//    {
//        enemySet.cleanUp();
//        groundHealth = Math.pow((lvl * 2 - 1), 2) / 3;
//        
//        if(bosslvl % 10 == 2 || bosslvl % 10 == 5 || bosslvl % 10 == 8 || bosslvl % 10 == 0)
//        {
//            enemySet.setEnemies(createEnemyBossSet(random.nextInt(4) + 3, groundHealth, groundHealth * 3, (lvl / 20)+1 + random.nextInt(5)));
//        }
//        enemySet.setEnemies(createEasyEnemySet(random.nextInt(4) + 3, groundHealth * 0.6, groundHealth * 1.2, (lvl / 20)+1));
//    }
//    
//    private Map<Integer, EnemyGroup> createEasyEnemySet(int rows, double health, double points, int freq)
//    {
//        Map<Integer, EnemyGroup> enemies = new HashMap<Integer, EnemyGroup>();
//        for(int i = rows; i > 0; i--)
//        {
//            EnemyGroup enemyRow = new EnemyGroup(true, 1, 0, 0, 4);
//            int extraBorder = ((i%2 - 1)* allBitmap.get(enemyKind + 3).getHeight() * -1)/2; 
//            for(int inLine = 0; inLine < 9 + i%2; inLine++)
//            {
//                Coordinates coord = new Coordinates((float)inLine * allBitmap.get(enemyKind + 3).getWidth() + 3 + extraBorder + (inLine * 2),
//                    (float)((i + 4) * allBitmap.get(enemyKind + 3).getHeight() + i * 4));
//                AbstractEnemy tempEnemy = new Peon(allBitmap.get(enemyKind + 3), coord, createPeonPowerUps(rows));
//                tempEnemy.setHealth((int) (health * hpMultiplier * (0.8 + enemyKind * 0.1)));
//                tempEnemy.setPoints((int) (points * pointMultiplier));
//                tempEnemy.setShotFrequency(freq);
//                enemyRow.add(tempEnemy);
//            }
//            enemies.put(i, enemyRow);
//        }
//        return enemies;
//    }
//    
//    private Map<Integer, EnemyGroup> createEnemyBossSet(int amount, double health, double points, int frequency)
//    {
//        Map<Integer, EnemyGroup> enemies = new HashMap<Integer, EnemyGroup>();
//        if(health < 10)
//        {
//            health = 10;
//        }
//        EnemyGroup enemyRow = new EnemyGroup(true, 1, 0, 0, 4);
//        for (int i = 0; i < amount; i++)
//        {
//            Coordinates coord = new Coordinates(canvasWidth / (amount + 1) * (i+1) - allBitmap.get(enemyKind).getWidth() / 2
//                    , (float)random.nextInt(100) + allBitmap.get(enemyKind).getHeight());
//            AbstractEnemy tempEnemy = new Midboss(allBitmap.get(enemyKind), coord, canvasHeight, canvasWidth, frequency,
//                    createBossPowerUps(coord));
//            tempEnemy.setHealth((int) (health * hpMultiplier * (0.8 + enemyKind * 0.1)));
//            tempEnemy.setPoints((int) (points * pointMultiplier));
//
//            enemyRow.add(tempEnemy);
//        }
//        enemies.put(1, enemyRow);
//        return enemies;
//    }
//}