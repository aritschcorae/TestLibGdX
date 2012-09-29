package ch.yoroshiku.spaceInvader.model.enemieset;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import ch.yoroshiku.spaceInvader.model.Ship;

public class AbstractEnemySetCreator //TODO abstract
{

    protected Ship ship;
    protected int lvl = 0;
    protected int displayLvl = 1;
    protected int amountOfMinPowerUps = 1;
    protected int enemyKind = 0;
    protected float canvasHeight, canvasWidth;
    protected EnemySet enemySet;
    private Random random = new Random();
    protected final float zoom =1;
//    protected AbstractGameFieldView gameField;
    protected Map<Integer, List<Integer>> groupMapping = new HashMap<Integer, List<Integer>>();
    protected static final Integer MIDBOSS_EASY_ID = 0;
    protected static final Integer MIDBOSS_NORMAL_ID = 1;
    protected static final Integer MIDBOSS_HARD_ID = 2;
    protected static final Integer PEON_EASY_ID = 3;
    protected static final Integer PEON_NORMAL_ID = 4;
    protected static final Integer PEON_HARD_ID = 5;
    protected static final Integer PROMETHEUS_ID = 6;
    protected static final Integer POWER_UP_EATER_ID = 9;
    protected static final Integer EXPLOSION_ID = 10;
    protected static final Integer FREEZER_ID = 11;
    protected static final Integer BOMB_DEFUSER_ID = 13;
    protected static final Integer DEFENSLESS_ID = 15;
    protected static final Integer TELEPORTER_ID = 17;
    protected static final Integer SPLASHER_ID = 18;
    
//    public AbstractEnemySetCreator(float canvasHeight, float canvasWidth, Ship ship, EnemySet enemySet, float zoom, AbstractGameFieldView gameField)
//    {
//        this.canvasHeight = canvasHeight;
//        this.canvasWidth = canvasWidth;
//        this.ship = ship;
//        this.enemySet = enemySet;
//        this.zoom = zoom;
//        this.gameField = gameField;
//        loadBitmaps();
//        loadGroupMapping();
//    }
//
//    private void loadBitmaps()
//    {
//        allBitmap = new ArrayList<Bitmap>();
//        // normal boss
//        matrix.reset();
//        System.err.println("ZOOM: " + zoom);
//        matrix.postScale(zoom, zoom);
//        allBitmap.add(MIDBOSS_EASY_ID, createBitmapWithZoom(R.drawable.midboss_easy, matrix));
//        allBitmap.add(MIDBOSS_NORMAL_ID, createBitmapWithZoom(R.drawable.midboss_normal, matrix));
//        allBitmap.add(MIDBOSS_HARD_ID, createBitmapWithZoom(R.drawable.midboss_hard, matrix));
//
//        // normal enemy
//        matrix.reset();
//        matrix.postScale(zoom / 2, zoom / 2);
//        allBitmap.add(PEON_EASY_ID, createBitmapWithZoom(R.drawable.midboss_easy, matrix));
//        allBitmap.add(PEON_NORMAL_ID, createBitmapWithZoom(R.drawable.midboss_normal, matrix));
//        allBitmap.add(PEON_HARD_ID, createBitmapWithZoom(R.drawable.midboss_hard, matrix));
//        
//        // big boss
//        matrix.reset();
//        matrix.postScale(zoom, zoom);
//
//        allBitmap.add(PROMETHEUS_ID, createBitmapWithZoom(R.drawable.prometheus, matrix));
//        allBitmap.add(PROMETHEUS_ID + 1, createBitmapWithZoom(R.drawable.prometheus_left_wing, matrix));
//        allBitmap.add(PROMETHEUS_ID + 2, createBitmapWithZoom(R.drawable.prometheus_right_wing, matrix));
//
//        //powerupeater
//        allBitmap.add(POWER_UP_EATER_ID, createBitmapWithZoom(R.drawable.power_up_eater, matrix));
//        
//        //explosion
//        matrix.reset();
//        int height = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion).getHeight();
//        float explosionZoom;
//        if(3 <  (height * zoom) % 6)
//            explosionZoom = ((height * zoom) + (6 - (height * zoom) % 6)) / height;
//        else
//            explosionZoom = ((height * zoom) - (height * zoom) % 6) / height;
//        
//        matrix.postScale(explosionZoom, explosionZoom);
//        allBitmap.add(EXPLOSION_ID, createBitmapWithZoom(R.drawable.explosion, matrix));
//        
//        //postThrower
//        matrix.reset();
//        matrix.postScale(zoom, zoom);
//        allBitmap.add(FREEZER_ID, createBitmapWithZoom(R.drawable.freezer, matrix));
//        allBitmap.add(FREEZER_ID + 1, createBitmapWithZoom(R.drawable.freezer_post, matrix));        
//        allBitmap.add(BOMB_DEFUSER_ID, createBitmapWithZoom(R.drawable.bomb_defuser, matrix));
//        allBitmap.add(BOMB_DEFUSER_ID + 1, createBitmapWithZoom(R.drawable.bomb_defuser_post, matrix));        
//        allBitmap.add(DEFENSLESS_ID, createBitmapWithZoom(R.drawable.freezer, matrix));
//        allBitmap.add(DEFENSLESS_ID + 1, createBitmapWithZoom(R.drawable.freezer_post, matrix));
//        allBitmap.add(TELEPORTER_ID, createBitmapWithZoom(R.drawable.teleporter, matrix));
//        allBitmap.add(SPLASHER_ID, createBitmapWithZoom(R.drawable.midboss_splash, matrix));
//    }
//
//    private void loadGroupMapping()
//    {
//        List<Integer> group = new ArrayList<Integer>();
//        group.add(MIDBOSS_EASY_ID);
//        group.add(MIDBOSS_NORMAL_ID);
//        group.add(MIDBOSS_HARD_ID);
//        groupMapping.put(0, group);
//        group = new ArrayList<Integer>();
//        group.add(PEON_EASY_ID);
//        group.add(PEON_NORMAL_ID);
//        group.add(PEON_HARD_ID);
//        groupMapping.put(1, group);
//        group = new ArrayList<Integer>();
//        group.add(PROMETHEUS_ID);
//        groupMapping.put(2, group);
//        group = new ArrayList<Integer>();
//        group.add(FREEZER_ID);
//        group.add(BOMB_DEFUSER_ID);
//        group.add(DEFENSLESS_ID);
//        groupMapping.put(3, group);
//        group = new ArrayList<Integer>();
//        group.add(TELEPORTER_ID);
//        groupMapping.put(4, group);
//        group = new ArrayList<Integer>();
//        group.add(SPLASHER_ID);
//        groupMapping.put(5, group);
//    }
//    
//    private Bitmap createBitmapWithZoom(int id, Matrix matrix)
//    {
//        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);
//        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
//    }
//    
//    public EnemyGroup createPowerUpEater()
//    {
//        EnemyGroup enemyRow = new EnemyGroup(false, -1, -10, 0, 1);
//        enemyRow.add(new PowerUpEater(allBitmap.get(POWER_UP_EATER_ID), new BaseObject((float)canvasWidth /2, 
//                (float)canvasHeight / 3), true, zoom));
//        return enemyRow;
//    }
//    
//    protected float hpMultiplier = 1, pointMultiplier = 1, powerUpMultiplier = 1;
//    
//    abstract public void loadEnemiesOfNextLvl();
//
//    protected boolean createBossPowerUps(BaseObject coord)
//    {
//        return random.nextInt(8) == 4;
//    }
//    
//    protected boolean createPeonPowerUps(int amountOfEnemies)
//    {
//        return random.nextInt(amountOfEnemies * 8) == 1;
//    }
//
//    public void setLvl(int level)
//    {
//        lvl = level;
//        displayLvl = level + 1;
//    }
//    
//    public void setNextlvl()
//    {
//        lvl ++;
//        enemyKind = (lvl / 10);
//    }
//    
//    public void setNextDisplaylvl()
//    {
//        displayLvl ++;
//    }
//
//    public int getLvl()
//    {
//        return displayLvl;
//    }
//    
//    public int getPlainLvl()
//    {
//        return lvl;
//    }
//    
//    public void setMultiplierByDifficulty(int difficulty)
//    {
//        switch (difficulty)
//        {
//        case 5:
//            pointMultiplier = 1.7f;
//            hpMultiplier = 1.5f;
//            amountOfMinPowerUps = 1;
//            powerUpMultiplier = 0.7f;
//            break;
//        case 4:
//            pointMultiplier = 1.5f;
//            hpMultiplier = 1.3f;
//            powerUpMultiplier = 0.8f;
//            amountOfMinPowerUps = 1;
//            break;
//        case 2:
//            pointMultiplier = 0.75f;
//            hpMultiplier = 0.8f;
//            powerUpMultiplier = 1.1f;
//            amountOfMinPowerUps = 3;
//            break;
//        case 1:
//            pointMultiplier = 0.5f;
//            hpMultiplier = 0.5f;
//            powerUpMultiplier = 1.3f;
//            amountOfMinPowerUps = 3;
//            break;
//        }
//    }
//    
}