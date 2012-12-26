package ch.yoroshiku.spaceInvader.manager;

import ch.yoroshiku.spaceInvader.model.PowerUp;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;



public class PowerUpManager extends Manager {

	private Array<PowerUp> powerUps;
	
	public PowerUpManager(){
		powerUps = new Array<PowerUp>();
	}
	

	public void move(float delta) {
		super.move(delta, powerUps);
	}

	public void drawSprite(SpriteBatch batch, float ppux, float ppuy, final float offset) {
		super.drawSprite(batch, ppux, ppuy, offset, powerUps);
	}

	public void drawShape(ShapeRenderer shapeRenderer, float ppux, float ppuy, final float offset) {
	}
}
