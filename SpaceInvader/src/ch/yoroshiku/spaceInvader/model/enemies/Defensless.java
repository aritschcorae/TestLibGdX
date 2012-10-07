package ch.yoroshiku.spaceInvader.model.enemies;

import ch.yoroshiku.spaceInvader.util.Enemies;

import com.badlogic.gdx.graphics.Color;

public class Defensless extends PostThrower
{
	private static final long serialVersionUID = 1L;
    public Defensless(float x, float y,
            int shotFrequency, boolean powerUps, float fieldWidth, float fieldHeight)
    {
		super(x, y, shotFrequency, powerUps, fieldWidth, fieldHeight, 
				Enemies.ALL_TEXTURES.get(Enemies.DEFENSLESS_ID), Enemies.DEFENSLESS_ID);
    }

    @Override
    protected void setModification(boolean actiaved)
    {
//        gameField.removeShipShield(actiaved);TODO
    }

    @Override
    protected Color getLaserColor()
    {
        return Color.GREEN;
    }

}
