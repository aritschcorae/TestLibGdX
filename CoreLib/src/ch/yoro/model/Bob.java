package ch.yoro.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bob {

	public enum State {
		IDLE, WALKING, JUMPING, DYING
	}

	static final float JUMP_VELOCITY = 1f;
	static public final float SIZE = 0.5f; // half a unit
	public static final float SPEED = 4f;   // unit per second
	 
	public void setState(State newState) {
	    this.state = newState;
	}
	 
	public void update(float delta) {
	    position.add(velocity.tmp().mul(delta));
	}

	Vector2 position = new Vector2();
	Vector2 acceleration = new Vector2();
	Vector2 velocity = new Vector2();
	Rectangle bounds = new Rectangle();
	State state = State.IDLE;
	boolean facingLeft = true;

	public Bob(Vector2 position) {
		this.position = position;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public Vector2 getVelocity() {
		return velocity;
	}
	
	public Vector2 getAcceleration()
	{
		return acceleration;
	}
	
}
