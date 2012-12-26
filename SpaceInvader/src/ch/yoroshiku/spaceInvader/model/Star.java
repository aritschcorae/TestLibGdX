package ch.yoroshiku.spaceInvader.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Star extends AbstractSprite {

	private static final long serialVersionUID = 1L;

	public Star(final float x, final float y) {
		super(x, y, 0,0);
		speed = 8;
	}

	@Override
	public boolean move(float delta) {
		y -= speed * delta;
		return false;
	}

	@Override
	public void drawSprite(SpriteBatch batch, float ppux, float ppuy, final float offset) {
		// not used
	}

	@Override
	public void drawShape(ShapeRenderer shapeRenderer, float ppux, float ppuy, final float offset) {
		shapeRenderer.point(offset + ppux * x, ppuy * y, 0);
	}
	
	public void resetPosition(final float x, final float y){
		this.x = x;
		this.y = y;
	}
}
