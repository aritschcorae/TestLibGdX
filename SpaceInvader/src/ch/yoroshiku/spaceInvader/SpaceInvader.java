package ch.yoroshiku.spaceInvader;


import java.io.IOException;

import ch.yoroshiku.spaceInvader.screen.GameScreen;

import com.badlogic.gdx.Game;

public class SpaceInvader extends Game {

	@Override
	public void create() {
//		setScreen(new MenuScreen(this));
		try
		{
			setScreen(new GameScreen(this));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
