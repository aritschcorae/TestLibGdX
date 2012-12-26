package ch.yoroshiku.spaceInvader;

import ch.yoroshiku.spaceInvader.SpaceInvader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;


public abstract class AbstractScreen implements Screen
{

	protected final SpaceInvader game;
    protected final ShapeRenderer shapeRenderer;
    protected final SpriteBatch batch;
    protected final Stage stage;

    public AbstractScreen(SpaceInvader game)
    {
        this.game = game;
        this.batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        this.stage = new Stage( 0, 0, true );
    }

	@Override
	public void resize(int width, int height) 
	{
		stage.setViewport(width, height, true);
	}

    @Override
    public void render(
        float delta )
    {
        Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
    }

    @Override
    public void hide()
    {
    }

    @Override
    public void dispose()
    {
        // dispose the collaborators
        stage.dispose();
        batch.dispose();
    }

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
}