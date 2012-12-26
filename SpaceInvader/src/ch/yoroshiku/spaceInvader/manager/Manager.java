package ch.yoroshiku.spaceInvader.manager;

import ch.yoroshiku.spaceInvader.model.AbstractSprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

public abstract class Manager {

	public void move(final float delta, final Array<? extends AbstractSprite> sprites){
		for(AbstractSprite sprite : sprites){
			sprite.move(delta);
		}
	}

	public void drawSprite(final SpriteBatch batch, final float ppux, final float ppuy, final float offset,
			final Array<? extends AbstractSprite> sprites) {
		batch.begin();
		for (AbstractSprite sprite : sprites) {
			sprite.drawSprite(batch, ppux, ppuy, offset);
		}
		batch.end();
	}

	/**
	 * shaperenderer has to be in status "begin"
	 */
	public void drawShape(final ShapeRenderer shapeRenderer, final float ppux, final float ppuy, final float offset,
			final Array<? extends AbstractSprite> sprites) {
		for (AbstractSprite sprite : sprites) {
			sprite.drawShape(shapeRenderer, ppux, ppuy, offset);
		}
	}
	
}
