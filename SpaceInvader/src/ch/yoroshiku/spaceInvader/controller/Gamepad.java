package ch.yoroshiku.spaceInvader.controller;

import java.util.HashMap;
import java.util.Map;

import ch.yoroshiku.spaceInvader.model.Ship;
import ch.yoroshiku.spaceInvader.screen.GameScreen;
import ch.yoroshiku.spaceInvader.screen.GameScreen.GamePhase;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.input.GestureDetector;

public class Gamepad extends GestureDetector{

	enum IngameKeys {
		LEFT, RIGHT, BOMB, SPRAY, BACK, CONFIRM
	}

	static Map<IngameKeys, Boolean> keys = new HashMap<IngameKeys, Boolean>();
	static {
		keys.put(IngameKeys.LEFT, false);
		keys.put(IngameKeys.RIGHT, false);
		
		keys.put(IngameKeys.BOMB, false);
		keys.put(IngameKeys.SPRAY, false);
	};
	
	private Ship ship;
	private GameScreen gameController;
	private float width;
	private GesturePad gesturePad;

	public Gamepad(GameScreen controller, Ship ship, GesturePad gesturepad) {
		super(gesturepad);
		this.gesturePad = gesturepad;
		this.ship = ship;
		this.gameController = controller;
	}

	public void leftPressed() {
		ship.setMovingLeft();
		keys.put(IngameKeys.LEFT, true);
	}

	public void rightPressed() {
		ship.setMovingRight();
		keys.put(IngameKeys.RIGHT, true);
	}

	public void upPressed() {
		keys.put(IngameKeys.BOMB, true);
	}

	public void upReleased() {
		keys.put(IngameKeys.BOMB, false);
	}

	public void leftReleased() {
		ship.setMovingStop();
		keys.put(IngameKeys.LEFT, false);
	}

	public void rightReleased() {
		ship.setMovingStop();
		keys.put(IngameKeys.RIGHT, false);
	}


	public void update(float delta, final GamePhase phase) {
		switch (phase) {
		case GAMESTART:
		case GAMING:
			processInput(delta);
			break;
		case LEVEL_LOAD:
			break;
		case PAUSE:
		case LEVEL_WAIT:
			if(isKeyPressed())
				GameScreen.updatePhase(GamePhase.GAMING);
			break;
		case DEAD:
		case FINISHED:
			if(isKeyPressed())
			gameController.gameOver();
			//TODO open highscore
			break;
		case LEVEL_SCORE:
			break;
		default:
			break;
		}
	}
	
	private boolean isKeyPressed() {
		return keys.get(IngameKeys.LEFT) || keys.get(IngameKeys.RIGHT);
	}

	private void processInput(float change) {
		if(keys.get(IngameKeys.LEFT) && !keys.get(IngameKeys.RIGHT)){
			ship.moveLeft(change);
		}
		else if(!keys.get(IngameKeys.LEFT) && keys.get(IngameKeys.RIGHT)){
			ship.moveRight(change);
		}
	}

    @Override
    public boolean keyDown(int keycode) {
    	super.keyDown(keycode);
        if (keycode == Keys.LEFT)
            leftPressed();
        else if (keycode == Keys.RIGHT)
            rightPressed();
        else if (keycode == Keys.UP)
            gameController.dropBomb();
        else if (keycode == Keys.DOWN)
        	gameController.spray();
        return true;
    }
 
    @Override
    public boolean keyUp(int keycode) {
    	super.keyUp(keycode);
        if (keycode == Keys.LEFT)
            leftReleased();
        if (keycode == Keys.RIGHT)
            rightReleased();
        if (keycode == Keys.UP)
            upReleased();
        return true;
    }
	
	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		super.touchUp(x, y, pointer, button);
		if(x <= width / 2){
			leftReleased();
			if (pointer == 0)
				gesturePad.setFlingSprayOption(false);
		} else {
			rightReleased();
			if (pointer == 0)
				gesturePad.setFlingBombOption(false);
		}
		return false;
	}

	@Override
	public boolean touchDown (int x, int y, int pointer, int button) {
		if(super.touchDown(x, y, pointer, button)){
			return true;
		}
			
		if(x <= width / 2){
			leftPressed();
			if(pointer == 0)
				gesturePad.setFlingSprayOption(true);
		} else {
			rightPressed();
			if (pointer == 0)
				gesturePad.setFlingBombOption(true);
		}
		return false;
	}
	
	public void releaseButtons(){
		leftReleased();
		rightReleased();
		upReleased();
	}


	public void resize(float width, float height){this.width = width;}
	@Override
	public boolean keyTyped(char character) {return super.keyTyped(character);}
	@Override
	public boolean touchDragged(int x, int y, int pointer) {return super.touchDragged(x, y, pointer);}
	@Override
	public boolean scrolled(int amount) {return super.scrolled(amount);}
	
}
