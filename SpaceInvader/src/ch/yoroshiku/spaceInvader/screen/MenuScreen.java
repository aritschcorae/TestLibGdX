package ch.yoroshiku.spaceInvader.screen;

import ch.yoroshiku.spaceInvader.SpaceInvader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MenuScreen extends UiScreen {
    private Label titleLabel;

    //Buttons
    private Button playButton;
    private Button btnOptions;
    private Button btnHighscore;
    private Button btnInfo;
    private Button btnExit;
    public static final String PLAY_BUTTON_IMAGE = "images/playbutton.png";
    public static final TextureRegionDrawable PLAY_BUTTON_TEXTURE_REGION = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(PLAY_BUTTON_IMAGE))));

    public MenuScreen(SpaceInvader game) {
        super(game);
    }

    @Override
    protected void initComponents() {
        super.initComponents();

        ClickListener buttonListener = new MainMenuListener();

        titleLabel = new Label("Space Invader", new LabelStyle( new BitmapFont(Gdx.files.internal("font/buttonfont.fnt"), false), Color.BLUE));
        playButton = new Button(PLAY_BUTTON_TEXTURE_REGION);
        playButton.addListener(buttonListener);
    }

    @Override
    protected void setupGui() {
        super.setupGui();

        //Get the correct font sizes
//        lbTitle.setStyle(UiStyles.getSpaceLabelStyle(ppuY));

        //Header
        wrapTable.add(titleLabel).padBottom((int) (10 * ppuY)).padTop((int) (10 * ppuY));

        wrapTable.row();

        //Add Play-Buttons
        wrapTable.add(playButton).width((int) (40 * ppuY)).height((int) (20 * ppuY));

        //Setup ButtonRow
        addToButtonRow(btnOptions);
//        addToButtonRow(btnHighscore);
//        addToButtonRow(btnInfo);
//        addToButtonRow(btnExit);
        wrapTable.row();
        wrapTable.add(buttonTable).bottom().expandY();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        setupGui();
        Gdx.input.setCatchBackKey(false);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        //maybe for later use
    }



    private class MainMenuListener extends ClickListener {

    	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
    		super.touchDown(event, x, y, pointer, button);
            if (playButton.equals(event.getListenerActor())) {
            game.createNewGame();
            game.setScreen(game.getGameScreen());}
    		return true;
    	}
    }



	@Override
	public void show() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
	}

}
