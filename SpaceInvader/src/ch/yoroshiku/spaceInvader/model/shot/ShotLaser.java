package ch.yoroshiku.spaceInvader.model.shot;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;



public class ShotLaser extends Shot
{
	private static final long serialVersionUID = 1L;

	public ShotLaser(float x, float y, int damage, float movementX, float movementY, float height, float width, boolean enemyShot)
    {
        super(x, y, damage, movementX, movementY, height, width, enemyShot);
    }

	@Override
	public boolean move(float delta) {
		nextStep(delta);
		return true;
	}

	@Override
	public void drawSprite(SpriteBatch batch, float ppux, float ppuy, float offset) {
		//not used
	}

	@Override
	public void drawShape(ShapeRenderer shapeRenderer, float ppux, float ppuy, float offset) {
		if(width != 1){
			shapeRenderer.begin(ShapeType.FilledRectangle);
			shapeRenderer.filledRect(offset + x * ppux, y * ppuy, width * ppux, height * ppuy);
		}else{
			shapeRenderer.begin(ShapeType.Line);
			System.out.println(x + " - " + y + " - "  + movementX + " - " + movementY);
			shapeRenderer.line(offset + x * ppux, y * ppuy, offset + (x + movementX) * ppux, (y + height + movementY) * ppuy);
		}
		shapeRenderer.end();
	}

}