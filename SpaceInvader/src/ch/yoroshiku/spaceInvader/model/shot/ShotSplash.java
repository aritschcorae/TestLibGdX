package ch.yoroshiku.spaceInvader.model.shot;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

class ShotSplash extends ShotLaser
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
    public Array<Shot> nextStep(float delta)
    {
        super.nextStep(delta);
        if (!splashed && y <endPointY)
        {
            Array<Shot> splashShots = new Array<Shot>();
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
        return new Array<Shot>();
    }


	@Override
	public boolean move(float delta) {
		nextStep(delta);
		return false;
	}


	@Override
	public void drawSprite(SpriteBatch batch, float ppux, float ppuy, float offset) {
	}


	@Override
	public void drawShape(ShapeRenderer shapeRenderer, float ppux, float ppuy, float offset) {
		super.drawShape(shapeRenderer, ppux, ppuy, offset);
	}

}