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
    public static List<Texture> allTextures;
    public static Map<Integer, List<Integer>> groupMapping = new HashMap<Integer, List<Integer>>();
    
    static
    {
    	loadTextures();
        loadGroupMapping();
    }
    
    private static void loadTextures()
    {

        allTextures = new ArrayList<Texture>(18);
        // normal boss
        allTextures.add(MIDBOSS_EASY_ID, new Texture(Gdx.files.internal("images/midboss_easy.gif")));
        allTextures.add(MIDBOSS_NORMAL_ID, new Texture(Gdx.files.internal("images/midboss_normal.gif")));
        allTextures.add(MIDBOSS_HARD_ID, new Texture(Gdx.files.internal("images/midboss_hard.gif")));

        // normal enemy
        allTextures.add(PEON_EASY_ID, new Texture(Gdx.files.internal("images/midboss_easy.gif")));
        allTextures.add(PEON_NORMAL_ID, new Texture(Gdx.files.internal("images/midboss_normal.gif")));
        allTextures.add(PEON_HARD_ID, new Texture(Gdx.files.internal("images/midboss_hard.gif")));
        
        // big boss
        allTextures.add(PROMETHEUS_ID, new Texture(Gdx.files.internal("images/prometheus.png")));
        allTextures.add(PROMETHEUS_ID + 1, new Texture(Gdx.files.internal("images/prometheus_left_wing.png")));
        allTextures.add(PROMETHEUS_ID + 2, new Texture(Gdx.files.internal("images/prometheus_left_wing.png")));

        //powerupeater
        allTextures.add(POWER_UP_EATER_ID, new Texture(Gdx.files.internal("images/power_up_eater.png")));
        
        //explosion
//        int height = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion).getHeight();
//        float explosionZoom;
//        if(3 <  (height * zoom) % 6)
//            explosionZoom = ((height * zoom) + (6 - (height * zoom) % 6)) / height;
//        else
//            explosionZoom = ((height * zoom) - (height * zoom) % 6) / height;
//        
//        matrix.postScale(explosionZoom, explosionZoom);
        allTextures.add(EXPLOSION_ID, new Texture(Gdx.files.internal("images/explosion.gif")));
        
        //postThrower
        allTextures.add(FREEZER_ID, new Texture(Gdx.files.internal("images/freezer.gif")));
        allTextures.add(FREEZER_ID + 1, new Texture(Gdx.files.internal("images/freezer_post.gif")));        
        allTextures.add(BOMB_DEFUSER_ID, new Texture(Gdx.files.internal("images/bomb_defuser.gif")));
        allTextures.add(BOMB_DEFUSER_ID + 1, new Texture(Gdx.files.internal("images/bomb_defuser_post.gif")));        
        allTextures.add(DEFENSLESS_ID, new Texture(Gdx.files.internal("images/bomb_defuser.gif")));
        allTextures.add(DEFENSLESS_ID + 1, new Texture(Gdx.files.internal("images/bomb_defuser_post.gif")));
        allTextures.add(TELEPORTER_ID, new Texture(Gdx.files.internal("images/teleporter.png")));
        allTextures.add(SPLASHER_ID, new Texture(Gdx.files.internal("images/midboss_splash.gif")));
    }
    
    private static void loadGroupMapping()
    {
        List<Integer> group = new ArrayList<Integer>();
        group.add(MIDBOSS_EASY_ID);
        group.add(MIDBOSS_NORMAL_ID);
        group.add(MIDBOSS_HARD_ID);
        groupMapping.put(0, group);
        group = new ArrayList<Integer>();
        group.add(PEON_EASY_ID);
        group.add(PEON_NORMAL_ID);
        group.add(PEON_HARD_ID);
        groupMapping.put(1, group);
        group = new ArrayList<Integer>();
        group.add(PROMETHEUS_ID);
        groupMapping.put(2, group);
        group = new ArrayList<Integer>();
        group.add(FREEZER_ID);
        group.add(BOMB_DEFUSER_ID);
        group.add(DEFENSLESS_ID);
        groupMapping.put(3, group);
        group = new ArrayList<Integer>();
        group.add(TELEPORTER_ID);
        groupMapping.put(4, group);
        group = new ArrayList<Integer>();
        group.add(SPLASHER_ID);
        groupMapping.put(5, group);
    }
	
}
