package ch.yoroshiku.spaceInvader.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Enemies {

    public static final Integer MIDBOSS_EASY_ID = 0;
    public static final Integer MIDBOSS_NORMAL_ID = 1;
    public static final Integer MIDBOSS_HARD_ID = 2;
    public static final Integer PEON_EASY_ID = 3;
    public static final Integer PEON_NORMAL_ID = 4;
    public static final Integer PEON_HARD_ID = 5;
    public static final Integer PROMETHEUS_ID = 6;
    public static final Integer POWER_UP_EATER_ID = 9;
    public static final Integer EXPLOSION_ID = 10;
    public static final Integer FREEZER_ID = 11;
    public static final Integer BOMB_DEFUSER_ID = 13;
    public static final Integer DEFENSLESS_ID = 15;
    public static final Integer TELEPORTER_ID = 17;
    public static final Integer SPLASHER_ID = 18;
    public static List<Texture> ALL_TEXTURES;
    public static Map<Integer, List<Integer>> GROUP_MAPPING = new HashMap<Integer, List<Integer>>();
	public static Map<Integer, Float> ENEMY_WIDTH;
	public static Map<Integer, Float> ENEMY_HEIGHT;
	
    static
    {
    	loadTextures();
        loadGroupMapping();
        loadSizes();
    }
    
    private static void loadTextures()
    {

        ALL_TEXTURES = new ArrayList<Texture>(18);
        // normal boss
        ALL_TEXTURES.add(MIDBOSS_EASY_ID, new Texture(Gdx.files.internal("images/midboss_easy.gif")));
        ALL_TEXTURES.add(MIDBOSS_NORMAL_ID, new Texture(Gdx.files.internal("images/midboss_normal.gif")));
        ALL_TEXTURES.add(MIDBOSS_HARD_ID, new Texture(Gdx.files.internal("images/midboss_hard.gif")));

        // normal enemy
        ALL_TEXTURES.add(PEON_EASY_ID, new Texture(Gdx.files.internal("images/midboss_easy.gif")));
        ALL_TEXTURES.add(PEON_NORMAL_ID, new Texture(Gdx.files.internal("images/midboss_normal.gif")));
        ALL_TEXTURES.add(PEON_HARD_ID, new Texture(Gdx.files.internal("images/midboss_hard.gif")));
        
        // big boss
        ALL_TEXTURES.add(PROMETHEUS_ID, new Texture(Gdx.files.internal("images/prometheus.png")));
        ALL_TEXTURES.add(PROMETHEUS_ID + 1, new Texture(Gdx.files.internal("images/prometheus_left_wing.png")));
        ALL_TEXTURES.add(PROMETHEUS_ID + 2, new Texture(Gdx.files.internal("images/prometheus_right_wing.png")));

        //powerupeater
        ALL_TEXTURES.add(POWER_UP_EATER_ID, new Texture(Gdx.files.internal("images/power_up_eater.png")));
        
        //explosion
//        int height = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion).getHeight();
//        float explosionZoom;
//        if(3 <  (height * zoom) % 6)
//            explosionZoom = ((height * zoom) + (6 - (height * zoom) % 6)) / height;
//        else
//            explosionZoom = ((height * zoom) - (height * zoom) % 6) / height;
//        
//        matrix.postScale(explosionZoom, explosionZoom);
        ALL_TEXTURES.add(EXPLOSION_ID, new Texture(Gdx.files.internal("images/explosion.gif")));
        
        //postThrower
        ALL_TEXTURES.add(FREEZER_ID, new Texture(Gdx.files.internal("images/freezer.gif")));
        ALL_TEXTURES.add(FREEZER_ID + 1, new Texture(Gdx.files.internal("images/freezer_post.gif")));        
        ALL_TEXTURES.add(BOMB_DEFUSER_ID, new Texture(Gdx.files.internal("images/bomb_defuser.gif")));
        ALL_TEXTURES.add(BOMB_DEFUSER_ID + 1, new Texture(Gdx.files.internal("images/bomb_defuser_post.gif")));        
        ALL_TEXTURES.add(DEFENSLESS_ID, new Texture(Gdx.files.internal("images/bomb_defuser.gif")));
        ALL_TEXTURES.add(DEFENSLESS_ID + 1, new Texture(Gdx.files.internal("images/bomb_defuser_post.gif")));
        ALL_TEXTURES.add(TELEPORTER_ID, new Texture(Gdx.files.internal("images/teleporter.png")));
        ALL_TEXTURES.add(SPLASHER_ID, new Texture(Gdx.files.internal("images/midboss_splash.gif")));
    }
    
    private static void loadGroupMapping()
    {
        List<Integer> group = new ArrayList<Integer>();
        group.add(MIDBOSS_EASY_ID);
        group.add(MIDBOSS_NORMAL_ID);
        group.add(MIDBOSS_HARD_ID);
        GROUP_MAPPING.put(0, group);
        group = new ArrayList<Integer>();
        group.add(PEON_EASY_ID);
        group.add(PEON_NORMAL_ID);
        group.add(PEON_HARD_ID);
        GROUP_MAPPING.put(1, group);
        group = new ArrayList<Integer>();
        group.add(PROMETHEUS_ID);
        GROUP_MAPPING.put(2, group);
        group = new ArrayList<Integer>();
        group.add(FREEZER_ID);
        group.add(BOMB_DEFUSER_ID);
        group.add(DEFENSLESS_ID);
        GROUP_MAPPING.put(3, group);
        group = new ArrayList<Integer>();
        group.add(TELEPORTER_ID);
        GROUP_MAPPING.put(4, group);
        group = new ArrayList<Integer>();
        group.add(SPLASHER_ID);
        GROUP_MAPPING.put(5, group);
    }
    
    private static void loadSizes()
    {
        ENEMY_HEIGHT = new HashMap<Integer, Float>();
        ENEMY_HEIGHT.put(MIDBOSS_EASY_ID, Sizes.MIDBOSS_HEIGHT);
        ENEMY_HEIGHT.put(MIDBOSS_NORMAL_ID, Sizes.MIDBOSS_HEIGHT);
        ENEMY_HEIGHT.put(MIDBOSS_HARD_ID, Sizes.MIDBOSS_HEIGHT);
        ENEMY_HEIGHT.put(PEON_EASY_ID, Sizes.PEON_HEIGHT);
        ENEMY_HEIGHT.put(PEON_NORMAL_ID, Sizes.PEON_HEIGHT);
        ENEMY_HEIGHT.put(PEON_HARD_ID, Sizes.PEON_HEIGHT);
        ENEMY_HEIGHT.put(PROMETHEUS_ID, Sizes.PROMETHEUS_HEIGHT);
        ENEMY_HEIGHT.put(PROMETHEUS_ID + 1, Sizes.WING_HEIGHT);
        ENEMY_HEIGHT.put(PROMETHEUS_ID + 2, Sizes.WING_HEIGHT);
        ENEMY_HEIGHT.put(POWER_UP_EATER_ID, Sizes.POWER_UP_HEIGHT);
        ENEMY_HEIGHT.put(EXPLOSION_ID, Sizes.EXPLOSION_RADIUS);
        ENEMY_HEIGHT.put(FREEZER_ID, Sizes.THROWER_HEIGHT);
        ENEMY_HEIGHT.put(FREEZER_ID + 1, Sizes.POST_HEIGHT);
        ENEMY_HEIGHT.put(BOMB_DEFUSER_ID, Sizes.THROWER_HEIGHT);
        ENEMY_HEIGHT.put(BOMB_DEFUSER_ID + 1, Sizes.POST_HEIGHT);        
        ENEMY_HEIGHT.put(DEFENSLESS_ID, Sizes.THROWER_HEIGHT);
        ENEMY_HEIGHT.put(DEFENSLESS_ID + 1, Sizes.POST_HEIGHT);
        ENEMY_HEIGHT.put(TELEPORTER_ID, Sizes.POST_HEIGHT);
        ENEMY_HEIGHT.put(SPLASHER_ID, Sizes.MIDSBOSS_SPLASS_HEIGHT);
        
        ENEMY_WIDTH = new HashMap<Integer, Float>();
        ENEMY_WIDTH.put(MIDBOSS_EASY_ID, Sizes.MIDBOSS_WIDTH);
        ENEMY_WIDTH.put(MIDBOSS_NORMAL_ID, Sizes.MIDBOSS_WIDTH);
        ENEMY_WIDTH.put(MIDBOSS_HARD_ID, Sizes.MIDBOSS_WIDTH);
        ENEMY_WIDTH.put(PEON_EASY_ID, Sizes.PEON_WIDTH);
        ENEMY_WIDTH.put(PEON_NORMAL_ID, Sizes.PEON_WIDTH);
        ENEMY_WIDTH.put(PEON_HARD_ID, Sizes.PEON_WIDTH);
        ENEMY_WIDTH.put(PROMETHEUS_ID, Sizes.PROMETHEUS_WIDTH);
        ENEMY_WIDTH.put(PROMETHEUS_ID + 1, Sizes.WING_WIDTH);
        ENEMY_WIDTH.put(PROMETHEUS_ID + 2, Sizes.WING_WIDTH);
        ENEMY_WIDTH.put(POWER_UP_EATER_ID, Sizes.POWER_UP_WIDTH);
        ENEMY_WIDTH.put(EXPLOSION_ID, Sizes.EXPLOSION_RADIUS);
        ENEMY_WIDTH.put(FREEZER_ID, Sizes.THROWER_WIDTH);
        ENEMY_WIDTH.put(FREEZER_ID + 1, Sizes.POST_WIDTH);
        ENEMY_WIDTH.put(BOMB_DEFUSER_ID, Sizes.THROWER_WIDTH);
        ENEMY_WIDTH.put(BOMB_DEFUSER_ID + 1, Sizes.POST_WIDTH);        
        ENEMY_WIDTH.put(DEFENSLESS_ID, Sizes.THROWER_WIDTH);
        ENEMY_WIDTH.put(DEFENSLESS_ID + 1, Sizes.POST_WIDTH);
        ENEMY_WIDTH.put(TELEPORTER_ID, Sizes.POST_WIDTH);
        ENEMY_WIDTH.put(SPLASHER_ID, Sizes.MIDSBOSS_SPLASS_WIDTH);
    }
	
}
