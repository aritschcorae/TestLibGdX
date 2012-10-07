package ch.yoroshiku.spaceInvader.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class ShotSplash extends Shot
{
	private static final long serialVersionUID = 8862516614730444636L;
    private boolean triple, splashed = false;
    private float endPointY;
    
    public ShotSplash(float x, float y, int damage, float movementX, float movementY,
            float height, float width, boolean enemyShot, boolean triple, float endPointY)
    {
        super(x, y, damage, movementX, movementY, height, width, enemyShot);
        this.triple = triple;
        this.endPointY = endPointY;
    }

    
    @Override
    public List<Shot> nextStep(float delta)
    {
        super.nextStep(delta);
        if (!splashed && y <endPointY)
        {
            List<Shot> splashShots = new ArrayList<Shot>();
            splashShots.add(ShotFactory.createShotLaser(x, y,(int) damage, -25, -25));
            splashShots.add(ShotFactory.createShotLaser(x,y,(int) damage, 25, -25));
            if (!triple)
            {
                splashShots.add(ShotFactory.createShotLaser(x,y,(int) damage, -35, -15));
                splashShots.add(ShotFactory.createShotLaser(x,y,(int) damage, 35, -15));
            }
            movementY = -40;
            splashed = true;
            return splashShots;
        }
        return Collections.emptyList();
    }

}