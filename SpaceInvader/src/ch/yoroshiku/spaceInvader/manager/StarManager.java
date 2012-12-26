package ch.yoroshiku.spaceInvader.manager;

import java.util.Random;

import ch.yoroshiku.spaceInvader.model.Star;
import ch.yoroshiku.spaceInvader.util.Sizes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Array;

public class StarManager extends Manager {

	private Array<Star> stars;
	private Random starGenerator = new Random();
	private static final int AMOUNT_OF_STARS = 40;
	
	public StarManager(){
		stars = new Array<Star>();
		for (int i = 0; i < AMOUNT_OF_STARS; i++) {
			stars.add(new Star(createPosition(Sizes.DEFAULT_WORLD_WIDTH),
							createPosition(Sizes.DEFAULT_WORLD_HEIGHT)));
		}
	}
	
	private float createPosition(float dimension){
		return starGenerator.nextInt((int) dimension);
	}

	public void move(float delta) {
		for(final Star star : stars){
			star.move(delta);
			if(star.y < 0)
				star.resetPosition(createPosition(Sizes.DEFAULT_WORLD_WIDTH), Sizes.DEFAULT_WORLD_HEIGHT);
		}
	}

	public void drawSprite(SpriteBatch batch, float ppux, float ppuy, final float offset) {
		// not used
	}

	public void drawShape(ShapeRenderer shapeRenderer, float ppux, float ppuy, final float offset) {
		shapeRenderer.begin(ShapeType.Point);
		drawShape(shapeRenderer, ppux, ppuy, offset, stars);
		shapeRenderer.end();
	}
	
}
