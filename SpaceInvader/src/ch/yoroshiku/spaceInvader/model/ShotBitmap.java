package ch.yoroshiku.spaceInvader.model;

import ch.yoroshiku.spaceInvader.util.Sizes;

import com.badlogic.gdx.graphics.Texture;

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

}