package ch.yoroshiku.spaceInvader.model.enemies;

import ch.yoroshiku.spaceInvader.util.Enemies;

import com.badlogic.gdx.graphics.Color;

public class BombDefuser extends PostThrower
{
	private static final long serialVersionUID = 1L;
    public BombDefuser(float x,float y,
            int shotFrequency, boolean powerUps, float fieldWidth, float fieldHeight)
    {
		super(x, y, shotFrequency, powerUps, fieldWidth, fieldHeight, Enemies.ALL_TEXTURES.get(Enemies.BOMB_DEFUSER_ID),
				Enemies.BOMB_DEFUSER_ID);
    }

    @Override
    protected void setModification(boolean actiaved)
    {
//        gameField.deactivateBombs(actiaved);TODO modification activate
    }

    @Override
    protected Color getLaserColor()
    {
        return new Color(256, 204, 204, 100);
    }

}
