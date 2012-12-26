package ch.yoroshiku.spaceInvader.model.shot;

import ch.yoroshiku.spaceInvader.util.Sizes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ShotBitmap extends Shot
{
	private static final long serialVersionUID = 1L;
	private Texture bitmap;

    public ShotBitmap(Texture bitmap, float x, float y, int damage, float movementX, float movementY,
            boolean enemyShot, float acceleration)
    {
		super(x, y, damage, 0, movementY, Sizes.SHOT_BOMB_HEIGHT, Sizes.SHOT_BOMB_WIDTH, enemyShot);
        this.acceleration = acceleration;
        this.enemyShot = enemyShot;
        this.simpleShoot = false;
        this.bitmap = bitmap;
    }

	public Texture getBitmap()
	{
		return bitmap;
	}

	@Override
	public boolean move(float delta) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void drawSprite(SpriteBatch batch, float ppux, float ppuy, float offset) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawShape(ShapeRenderer shapeRenderer, float ppux, float ppuy, float offset) {
		// TODO Auto-generated method stub
		
	}

}