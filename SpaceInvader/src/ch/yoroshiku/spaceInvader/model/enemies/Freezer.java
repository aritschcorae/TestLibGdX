package ch.yoroshiku.spaceInvader.model.enemies;

import ch.yoroshiku.spaceInvader.util.Enemies;

import com.badlogic.gdx.graphics.Color;

public class Freezer extends PostThrower
{

	private static final long serialVersionUID = 1L;

	public Freezer(float x, float y,
            int shotFrequency, boolean powerUps, float fieldWidth, float fieldHeight,
            float zoom)
    {
		super(x, y, shotFrequency, powerUps, fieldWidth, fieldHeight, zoom, Enemies.allTextures.get(Enemies.FREEZER_ID));
    }

    @Override
    protected void setModification(boolean actiaved)
    {
        //gameField.slowDownShip(actiaved);TODO
    }

    @Override
    protected Color getLaserColor()
    {
        return Color.WHITE;
    }

}
