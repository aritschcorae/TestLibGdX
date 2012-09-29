package ch.yoroshiku.spaceInvader;


import ch.yoroshiku.spaceInvader.screen.GameScreen;

import com.badlogic.gdx.Game;

public class SpaceInvader extends Game {

	@Override
	public void create() {
		setScreen(new GameScreen(this));
	}

}
