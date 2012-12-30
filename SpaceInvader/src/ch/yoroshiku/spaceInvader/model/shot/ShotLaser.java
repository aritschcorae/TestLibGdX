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
		} else {
			shapeRenderer.begin(ShapeType.Line);
			if (movementX != 0) {
				if (enemyShot) {
					shapeRenderer.line(offset + movementX/3 + (x) * ppux, y * ppuy, offset + x * ppux, (y + height) * ppuy);
				} else {
					shapeRenderer.line(offset + x * ppux, y * ppuy, offset + movementX /3 + (x) * ppux, (y + height)
							* ppuy);
				}
			} else {
				shapeRenderer.line(offset + x * ppux, y * ppuy, offset + x * ppux, (y + height) * ppuy);
			}
		}
		shapeRenderer.end();
	}

}