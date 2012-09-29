//package ch.yoroshiku.spaceInvader.model.enemieset;
//
//import ch.yoroshiku.spaceInvader.model.Coordinates;
//import ch.yoroshiku.spaceInvader.model.PowerUp;
//import ch.yoroshiku.spaceInvader.model.PowerUpFactory;
//import ch.yoroshiku.spaceInvader.model.Ship;
//
//public class EnemySetCreatorBomb extends EnemySetCreatorFun
//{
//
//    public EnemySetCreatorBomb(float canvasHeight, float canvasWidth, Ship ship, EnemySet enemySet, float zoom, AbstractGameFieldView gameField)
//    {
//        super(canvasHeight, canvasWidth, ship, enemySet, zoom, gameField);
//    }
//
//    protected PowerUp createBombPowerUp(Coordinates coord)
//    {
//        return PowerUpFactory.createDamagePowerUp();
//    }
//}