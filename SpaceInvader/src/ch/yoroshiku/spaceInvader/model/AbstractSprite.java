package ch.yoroshiku.spaceInvader.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public abstract class AbstractSprite extends Rectangle {

	private static final long serialVersionUID = -5268095085491479865L;
	protected int health;
	protected TextureRegion textureRegion;

	protected float speed;

	/**
	 * @param delta
	 * @return true if still alive
	 */
	abstract public boolean move(final float delta);

	abstract public void drawSprite(final SpriteBatch batch, final float ppux, final float ppuy, final float offset);
	abstract public void drawShape(final ShapeRenderer shapeRenderer, final float ppux, final float ppuy, final float offset);

	public AbstractSprite(final float x, final float y, final float width, final float height) {
		super(x, y, width, height);
	}

	public AbstractSprite(final float x, final float y, final float width, final float height,
			final TextureRegion texture) {
		super(x, y, width, height);
		this.textureRegion = texture;
	}
	
	public AbstractSprite(final float x, final float y, final float width, final float height,
			final TextureRegion texture, final float speed) {
		super(x, y, width, height);
		this.speed = speed;
		this.textureRegion = texture;
	}
	

	public int getHealth() {
		return health;
	}

	public float getSpeed() {
		return speed;
	}

	public TextureRegion getTextureRegion() {
		return textureRegion;
	}

	public void setTextureRegion(final TextureRegion textureRegion) {
		this.textureRegion = textureRegion;
	}

}
