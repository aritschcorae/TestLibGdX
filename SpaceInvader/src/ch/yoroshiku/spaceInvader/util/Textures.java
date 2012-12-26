package ch.yoroshiku.spaceInvader.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;


public class Textures {

	public final static Texture BUTTON_TEXTURE;
	public final static Texture BOMB_TEXTURE;
	public final static Texture EXPLOSION_TEXTURE;
	public final static Texture TEXTBOX_TOP_TEXTURE;
	public final static Texture TEXTBOX_MIDDLE_TEXTURE;
	public final static Texture TEXTBOX_BOTTOM_TEXTURE;
	public final static Texture SHIP_STRAIGHT_TEXTURE;
    public final static BitmapFont TEMP_FONT;
	
	
	static
    {
		BOMB_TEXTURE = new Texture(Gdx.files.internal("images/bomb.png"));
		EXPLOSION_TEXTURE = new Texture(Gdx.files.internal("images/explosion.gif"));
		BUTTON_TEXTURE = new Texture(Gdx.files.internal("images/button_background_ingame.png"));
		TEXTBOX_TOP_TEXTURE = new Texture(Gdx.files.internal("images/textbox_end_top.png"));
		TEXTBOX_BOTTOM_TEXTURE = new Texture(Gdx.files.internal("images/textbox_end.png"));
		TEXTBOX_MIDDLE_TEXTURE = new Texture(Gdx.files.internal("images/textbox_middle.png"));
		SHIP_STRAIGHT_TEXTURE = new Texture(Gdx.files.internal("images/ship_straight.gif"));

		TEMP_FONT = new BitmapFont(Gdx.files.internal("font/buttonfont.fnt"), false);
	}
    
}
