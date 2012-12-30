package ch.yoroshiku.spaceInvader.controller;

import ch.yoroshiku.spaceInvader.screen.GameScreen;
import ch.yoroshiku.spaceInvader.screen.GameScreen.GamePhase;
import ch.yoroshiku.spaceInvader.util.Sizes;

import com.badlogic.gdx.input.GestureDetector.GestureAdapter;

public class GesturePad extends GestureAdapter {

	private float pauseVelocity, shotVelocity;
	private GameScreen screen;
	private boolean flingSprayOption = false, flingBombOption = false;
	
	public GesturePad (final GameScreen screen){
		this.screen = screen;
	}
	
	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		if(velocityY > shotVelocity || velocityY < -shotVelocity){
			if(flingBombOption){
				screen.dropBomb();
			} else if (flingSprayOption){
				screen.spray();
			}
		}
		else if(velocityX > pauseVelocity || velocityX < -pauseVelocity){
			GameScreen.updatePhase(GamePhase.PAUSE);
		}
		return false;
	}
	
	public void resize(final float ppux){
		pauseVelocity = ppux * Sizes.DEFAULT_WORLD_WIDTH / 4;
		shotVelocity = ppux * Sizes.DEFAULT_WORLD_HEIGHT / 2;
	}

	public void setFlingSprayOption(boolean b) {
		flingSprayOption = b;
	}

	public void setFlingBombOption(boolean b) {
		flingBombOption = b;
	}

}
