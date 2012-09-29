package ch.yoroshiku.spaceInvader.screen;

import ch.yoroshiku.spaceInvader.SpaceInvader;

import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;



public class MenuScreen extends AbstractScreen
{
	private String message = "Welcome to the great menu screen!";
    private float x, y;

    public MenuScreen(SpaceInvader game )
    {
        super( game );
    }

    @Override
    public void resize( int width, int height )
    {
        super.resize( width, height );

        // calculate the center point for the message
        TextBounds bounds = font.getBounds( message );
        x = ( width - bounds.width ) / 2;
        y = ( height - bounds.height ) / 2;
    }

    @Override
    public void render( float delta )
    {
        super.render( delta );

        // draw the message
        batch.begin();
        font.draw( batch, message, x, y );
        batch.end();
    }

}