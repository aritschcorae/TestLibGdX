package ch.yoroshiku.spaceInvader.model;

import java.util.Random;

public class PowerUpFactory
{
    public static Integer POWER_UP_SPEED = new Integer(0);
    public static Integer POWER_UP_SHOTS = new Integer(1);
    public static Integer POWER_UP_SHIELD = new Integer(2);
    public static Integer POWER_UP_DAMAGE = new Integer(3);
    public static Integer POWER_UP_BOMB = new Integer(4);
    public static Integer POWER_UP_HEAL = new Integer(5);
    private static Random random = new Random();
    private static int create;
    private static int specialPowerUpCounter = 2;
    private static int specialPowerUpFrequency = 4;
    
    public static PowerUp createPowerUp(Ship ship, int lvl)
    {
        if(lvl >= 2 && !ship.isMaxShots())
            return createShotPowerUp();
        else if(ship.getHealth() == 1 && random.nextBoolean())
            return createHealPowerUp();

        while(true)
        {
            specialPowerUpCounter ++;
            create = random.nextInt(5);
            switch (create)
            {
            case 0:
                if (ship.getPowerUpTypeInUse() != null && specialPowerUpCounter > specialPowerUpFrequency)
                {
                    specialPowerUpCounter = 0;
                    return createSpeedPowerUp();
                }
                break;
            case 1:
                if(ship.isSpray())
                    break;
                return createShotPowerUp();
            case 2:
                return createShieldPowerUp();
            case 3:
                if ((ship.getPowerUpTypeInUse() == null || ship.getPowerUpTypeInUse() != 3) && specialPowerUpCounter > specialPowerUpFrequency)
                {
                    specialPowerUpCounter = 0;
                    return createDamagePowerUp();
                }
                break;
            case 4:
                if(ship.getBombs() == 9)
                    break;
                return createBombPowerUp();
            }
        }
    }
    
    
    private static PowerUp createSpeedPowerUp()
    {
        return new PowerUp(POWER_UP_SPEED);
    }

    private static PowerUp createShotPowerUp()
    {
        return new PowerUp(POWER_UP_SHOTS);
    }

    private static PowerUp createShieldPowerUp()
    {
        return new PowerUp(POWER_UP_SHIELD);
    }

    private static PowerUp createBombPowerUp()
    {
        return new PowerUp(POWER_UP_BOMB);
    }

    private static PowerUp createHealPowerUp()
    {
        return new PowerUp(POWER_UP_HEAL);
    }
    
    public static PowerUp createDamagePowerUp()
    {
        return new PowerUp(POWER_UP_DAMAGE);
    }

}