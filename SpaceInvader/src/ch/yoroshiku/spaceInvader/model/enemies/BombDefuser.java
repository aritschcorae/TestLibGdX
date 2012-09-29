package ch.yoroshiku.spaceInvader.model.enemies;

import ch.yoroshiku.spaceInvader.util.Enemies;

import com.badlogic.gdx.graphics.Color;

public class BombDefuser extends PostThrower
{
	private static final long serialVersionUID = 1L;
    public BombDefuser(float x,float y,
            int shotFrequency, boolean powerUps, float fieldWidth, float fieldHeight,
            float zoom)
    {
		super(x, y, shotFrequency, powerUps, fieldWidth, fieldHeight, zoom, Enemies.allTextures.get(Enemies.BOMB_DEFUSER_ID));
    }

    @Override
    protected void setModification(boolean actiaved)
    {
//        gameField.deactivateBombs(actiaved);TODO
    }

    @Override
    protected Color getLaserColor()
    {
        return new Color(256, 204, 204, 100);
    }

}
