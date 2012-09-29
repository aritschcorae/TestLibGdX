package ch.yoroshiku.spaceInvader.controller;

import ch.yoroshiku.spaceInvader.model.Ship;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class InGameController extends AbstractController implements InputProcessor{

	private Ship ship;
	private float width, height;

	public InGameController(Ship ship) {
		this.ship = ship;
	}

	public void leftPressed() {
		keys.put(IngameKeys.LEFT, true);
	}

	public void rightPressed() {
		keys.put(IngameKeys.RIGHT, true);
	}

	public void leftReleased() {
		keys.put(IngameKeys.LEFT, false);
	}

	public void rightReleased() {
		keys.put(IngameKeys.RIGHT, false);
	}


	public void update(float delta) {
		processInput(delta);
	}
	
	public void changeMenuItem(float change)
	{
	}

	private void processInput(float change) {
		if(keys.get(IngameKeys.LEFT) && !keys.get(IngameKeys.RIGHT)){
			ship.moveLeft();
		}
		if(!keys.get(IngameKeys.LEFT) && keys.get(IngameKeys.RIGHT)){
			ship.moveRight();
		}
	}

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.LEFT)
            leftPressed();
        if (keycode == Keys.RIGHT)
            rightPressed();
        return true;
    }
 
    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Keys.LEFT)
            leftReleased();
        if (keycode == Keys.RIGHT)
            rightReleased();
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
		System.out.println(x + " - " + y + " - " + pointer);
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
		this.height = height;
	}
	
}
