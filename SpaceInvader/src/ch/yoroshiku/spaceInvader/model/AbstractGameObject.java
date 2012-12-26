package ch.yoroshiku.spaceInvader.model;

import ch.yoroshiku.spaceInvader.model.shot.Shot;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public abstract class AbstractGameObject extends AbstractSprite {
	private static final long serialVersionUID = 1L;
	protected int shootFrequency;

	public AbstractGameObject(float x, float y, float width, float height, TextureRegion texture, float speed) {
		super(x, y, width, height, texture, speed);
	}

	public AbstractGameObject(float x, float y, float width, float height, TextureRegion texture) {
		super(x, y, width, height, texture);
	}

	public AbstractGameObject(float x, float y, float width, float height) {
		super(x, y, width, height);
	}

	public Array<Shot> shot(Ship ship) {
		return Shot.EMPTY_SHOT_ARRAY;
	}

	public int getShootFrequency() {
		return shootFrequency;
	}

	public void setShootFrequency(int shootFrequency) {
		this.shootFrequency = shootFrequency;
	}

}
