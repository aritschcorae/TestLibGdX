package ch.yoroshiku.spaceInvader.model.shot;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;



class ShotCircle extends Shot
{
	private static final long serialVersionUID = 1L;

	public ShotCircle(float x, float y, int damage, float movementX, float movementY, float radius, boolean enemyShot)
    {
		super(x, y, damage, movementX, movementY, radius, radius, enemyShot);
        this.width = radius;
    }

	@Override
	public boolean move(float delta) {
		// TODO Auto-generated method stub
		nextStep(delta);
		return false;
	}

	@Override
	public void drawSprite(SpriteBatch batch, float ppux, float ppuy, float offset) {
		//note used
	}

	@Override
	public void drawShape(ShapeRenderer shapeRenderer, float ppux, float ppuy, float offset) {
		shapeRenderer.begin(ShapeType.Circle);
		shapeRenderer.circle(offset + x * ppux, y * ppuy, height);
		shapeRenderer.end();
	}

}