package ch.yoroshiku.spaceInvader.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;


public class Textures {

	public final static Texture BUTTON_TEXTURE;
	public final static Texture BOMB_TEXTURE;
	public final static Texture EXPLOSION_TEXTURE;
	
	static
    {
		BOMB_TEXTURE = new Texture(Gdx.files.internal("images/bomb.png"));
		EXPLOSION_TEXTURE = new Texture(Gdx.files.internal("images/explosion.gif"));
		BUTTON_TEXTURE = new Texture(Gdx.files.internal("images/button_background_ingame.png"));
    }
    
}
