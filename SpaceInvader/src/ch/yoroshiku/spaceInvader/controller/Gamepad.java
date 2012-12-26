package ch.yoroshiku.spaceInvader.controller;

import java.util.HashMap;
import java.util.Map;



import ch.yoroshiku.spaceInvader.model.Ship;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class Gamepad implements InputProcessor{

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
	private GameController gameController;
	private float width;

	public Gamepad(GameController controller, Ship ship) {
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


	public void update(float delta) {
		processInput(delta);
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
        if (keycode == Keys.LEFT)
            leftPressed();
        else if (keycode == Keys.RIGHT)
            rightPressed();
        else if (keycode == Keys.UP)
            gameController.dropBomb();
        else if (keycode == Keys.DOWN)
        	ship.setSpray(!ship.isSpray());
        return true;
    }
 
    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Keys.LEFT)
            leftReleased();
        if (keycode == Keys.RIGHT)
            rightReleased();
        if (keycode == Keys.UP)
            upReleased();
        return true;
    }
	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		if(x <= width / 2)
			leftPressed();
		else
			rightPressed();
		return true;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		if(x <= width / 2)
			leftReleased();
		else
			rightReleased();
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		return false;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
	
	public void resize(float width, float height)
	{
		this.width = width;
	}
	
}
