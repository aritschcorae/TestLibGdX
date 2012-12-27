package ch.yoroshiku.spaceInvader.model.shot;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;



public class ShotLaser extends Shot
{

	private static final long serialVersionUID = 1L;

	public ShotLaser(float x, float y, int damage, float movementX, float movementY, float height, float width, boolean enemyShot)
    {
        super(x, y, damage, movementX, movementY, height, width, enemyShot);
    }

	@Override
	public boolean move(float delta) {
		x += movementX;
		y += movementY;
		return true;
	}

	@Override
	public void drawSprite(SpriteBatch batch, float ppux, float ppuy, float offset) {
		//not used
	}

	@Override
	public void drawShape(ShapeRenderer shapeRenderer, float ppux, float ppuy, float offset) {
		shapeRenderer.begin(ShapeType.Line);
		for(int i = 0 ; i < width; i ++){
			shapeRenderer.line(offset + x * ppux, y * ppuy, offset + (x + i) * ppux, (y + height) * ppuy);
		}
		shapeRenderer.end();
	}

}