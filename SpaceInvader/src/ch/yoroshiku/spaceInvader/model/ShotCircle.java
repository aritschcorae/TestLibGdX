package ch.yoroshiku.spaceInvader.model;



class ShotCircle extends Shot
{
	private static final long serialVersionUID = 1L;

	public ShotCircle(float x, float y, int damage, float movementX, float movementY, float radius, boolean enemyShot)
    {
		super(x, y, damage, movementX, movementY, radius, radius, enemyShot);
        this.width = radius;
    }

}