package ch.yoroshiku.spaceInvader;
//package old;
//
//import ch.yoroshiku.spaceInvader.SpaceInvader;
//
//import com.badlogic.gdx.Input;
//import com.badlogic.gdx.InputProcessor;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//
///**
// * InputProcessor f�r das Userinterface ausserhalb des Gamescreens
// */
//public class UiController implements InputProcessor {
//
//    private final SpaceInvader game;
//    private final Stage stage;
//
//    public UiController(SpaceInvader game, Stage stage) {
//        this.game = game;
//        this.stage = stage;
//    }
//
//    @Override
//    public boolean keyDown(int i) {
//        stage.keyDown(i);
//        if (i == Input.Keys.BACK || i == Input.Keys.ESCAPE) {
////            if (!(game.getScreen() instanceof MenuScreen)) {
////                game.setScreen(game.getMainMenuScreen());
//            }
//        }
//
//        return true;
//    }
//
//    @Override
//    public boolean keyUp(int i) {
//        return stage.keyUp(i);
//    }
//
//    @Override
//    public boolean keyTyped(char c) {
//        return stage.keyTyped(c);
//    }
//
//    @Override
//    public boolean touchDown(int i, int i1, int i2, int i3) {
//        return stage.touchDown(i, i1, i2, i3);
//    }
//
//    @Override
//    public boolean touchUp(int i, int i1, int i2, int i3) {
//        return stage.touchUp(i, i1, i2, i3);
//    }
//
//    @Override
//    public boolean touchDragged(int i, int i1, int i2) {
//        return stage.touchDragged(i, i1, i2);
//    }
//
//    @Override
//    public boolean touchMoved(int i, int i1) {
//        return stage.touchMoved(i, i1);
//    }
//
//    @Override
//    public boolean scrolled(int i) {
//        return stage.scrolled(i);
//    }
//}
