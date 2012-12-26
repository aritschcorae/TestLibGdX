package ch.yoroshiku.spaceInvader;


import ch.yoroshiku.spaceInvader.screen.GameScreen;

import com.badlogic.gdx.Game;

public class SpaceInvader extends Game {

    private GameScreen gameScreen;


    @Override
    public void create() {
        //loader screens
    	initScreens();
    	setScreen(gameScreen);
    }
    
    public void initScreens() {
        createNewGame();
    }

    public void gameOver() {
    	System.exit(0);
//        setScreen(mainMenuScreen);
    }


    public void createNewGame() {
        gameScreen = new GameScreen(this);
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

}