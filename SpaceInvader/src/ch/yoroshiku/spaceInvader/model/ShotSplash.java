package ch.yoroshiku.spaceInvader.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ShotSplash extends Shot
{
    
	private static final long serialVersionUID = 8862516614730444636L;
	private int countdown;
    private boolean triple;
    
    public ShotSplash(float x, float y, int damage, int movementX, int movementY,
            float height, float width, boolean enemyShot, int steps, boolean triple)
    {
        super(x, y, damage, movementX, movementY, height, width, enemyShot);
        this.triple = triple;
        countdown = steps;
    }

    
    @Override
    public List<Shot> nextStep(float delta)
    {
        super.nextStep(delta);
        if (countdown == -1)
        {
            List<Shot> splashShots = new ArrayList<Shot>();
            splashShots.add(ShotFactory.createShotLaser(x, y,(int) damage, -25, 25));
            splashShots.add(ShotFactory.createShotLaser(x,y,(int) damage, 25, 25));
            if (!triple)
            {
                splashShots.add(ShotFactory.createShotLaser(x,y,(int) damage, -35, 15));
                splashShots.add(ShotFactory.createShotLaser(x,y,(int) damage, 35, 15));
            }
            movementY = 10;
            return splashShots;
        }
        return Collections.emptyList();
    }

}