package ch.yoroshiku.spaceInvader;


import ch.yoroshiku.spaceInvader.screen.GameScreen;
import ch.yoroshiku.spaceInvader.screen.MenuScreen;

import com.badlogic.gdx.Game;

public class SpaceInvader extends Game {

    private GameScreen gameScreen;
    private MenuScreen menuScreen;


    @Override
    public void create() {
		// loader screens
		initScreens();
		setScreen(menuScreen);
	}
    
    public void initScreens() {
        menuScreen = new MenuScreen(this);
//        createNewGame();
    }

    public void gameOver() {
    	System.exit(0);
//        setScreen(mainMenuScreen);
    }


    public void createNewGame() {
        try {
			gameScreen = new GameScreen(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

	public void gameFinished(int points) {
		// TODO highscore shizzle
		setScreen(menuScreen);
	}

}