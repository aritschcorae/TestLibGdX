package ch.yoro;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Button {
	
	static public final float SIZE = 1f;
	private String text;
	private Vector2 position = new Vector2();
	private Rectangle bounds = new Rectangle();
	private boolean selected = false;
	
	public Button(String text, Vector2 position)
	{
		this.text = text;
		this.position = position;
		bounds.width = SIZE * 2;
		bounds.height = SIZE * 2;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
