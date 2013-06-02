package ch.yoroshiku.spaceInvader.model;

import ch.yoroshiku.spaceInvader.model.shot.Shot;
import ch.yoroshiku.spaceInvader.util.Sizes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * @author Rofus {@link ShipStraight} {@link ShipCircle}
 */
public abstract class Ship extends AbstractGameObject {
	private static final long serialVersionUID = 1L;
	protected Integer powerUpTypeInUse = null;
	protected Array<Shot> leftShots = new Array<Shot>();
	protected Array<Shot> middleShots = new Array<Shot>();
	protected Array<Shot> rightShots = new Array<Shot>();
	protected float speed, centerX, centerY;
	private int shield = 1, health = 3;
	protected int maxHealth = 3;
	protected int damage = 10, shots = 3, bombs = 9;
	protected int maxShots;
	private boolean invincible = false;
	protected float shotLeft, shotMiddle, shotRight;
	protected boolean spray = false;
	protected int currentShot = 0;
	private boolean slowedDown = false, defensless = false, bombless = false, illness = false, position = true;
	private boolean powerupDamage = false, powerupSpeed = false;
	protected TextureRegion shipTexture;
	private Rectangle shipHitSpace, shipPowerUpReach;

	public Ship(float x, float y, Texture texture) {
		super(x, y, Sizes.SHIP_WIDTH, Sizes.SHIP_HEIGHT);
		shipTexture = new TextureRegion(texture, texture.getHeight() / 3, texture.getWidth() / 3);
		currentShot = 0;
		shipHitSpace = new Rectangle();
		shipHitSpace.width = Sizes.SHIP_HIT_EVADE_WIDTH;
		shipHitSpace.height = Sizes.SHIP_HIT_EVADE_WIDTH;
		shipPowerUpReach = new Rectangle();
		shipPowerUpReach.width = Sizes.SHIP_POWERUP_REACH_BOOST_WIDTH;
		shipPowerUpReach.height = Sizes.SHIP_POWERUP_REACH_BOOST_WIDTH;
	}

	public void resetShip() {
		health = 3;
		shield = 1;
		if (bombs < 2) {
			bombs = 2;
		}
	}

	@Override
	public boolean move(float delta) {
		// not needed since processor does the needful
		return false;
	}

	public void moveLeft(float delta) {
		x -= speed * delta;
		if (x < 0)
			x = 0;
	}

	public void moveRight(float delta) {
		x += speed * delta;
		if (x > Sizes.DEFAULT_WORLD_WIDTH - Sizes.SHIP_WIDTH)
			x = Sizes.DEFAULT_WORLD_WIDTH - Sizes.SHIP_WIDTH;
	}

	public void drawSprite(final SpriteBatch batch, final float ppuX, final float ppuY, final float border) {
		batch.draw(shipTexture, border + x * ppuX, y * ppuY, 0, 0, width, height, ppuX, ppuY, 0);
	}

	public void drawShape(final ShapeRenderer shapeRenderer, final float ppuX, final float ppuY, final float border) {
		shapeRenderer.begin(ShapeType.Circle);
		if (isInvincible()) {
			shapeRenderer.setColor(0, 1, 0, 0);
			shapeRenderer.circle(border + (x + Sizes.SHIP_CORE) * ppuX, (y + Sizes.SHIP_CORE) * ppuY, Sizes.SHIP_RADIUS
					* ppuX);
		} else {
			final float shield = getShield();
			if (shield >= 5) {
				shapeRenderer.setColor(1, 1, 1, 1);
				shapeRenderer.circle(border + (x + Sizes.SHIP_CORE) * ppuX, (y + Sizes.SHIP_CORE) * ppuY,
						Sizes.SHIP_RADIUS * ppuX);
				if (shield < 10) {
					shapeRenderer.setColor(1, 1, 1, (shield - 5) * 0.2f);
				}
				shapeRenderer.circle(border + (x + Sizes.SHIP_CORE) * ppuX, (y + Sizes.SHIP_CORE) * ppuY,
						Sizes.SHIP_RADIUS_DOUBLE * ppuX);
			} else if (shield > 0) {
				shapeRenderer.setColor(1, 1, 1, (1 - 0.2f) * shield);
				shapeRenderer.circle(border + (x + Sizes.SHIP_CORE) * ppuX, (y + Sizes.SHIP_CORE) * ppuY,
						Sizes.SHIP_RADIUS * ppuX);
			}
		}
		shapeRenderer.end();
	}

	public void addPowerUp(final PowerUp powerUp) {
		if (powerUp.getType().equals(PowerUp.POWER_UP_DAMAGE) || powerUp.getType().equals(PowerUp.POWER_UP_SPEED)) {
			handleSinglePowerUp(powerUp);
		} else if (powerUp.getType().equals(PowerUp.POWER_UP_SHIELD)) {
			if (defensless)
				tempShield++;
			else
				shield++;
		} else if (powerUp.getType().equals(PowerUp.POWER_UP_SHOTS)) {
			handleShotPowerup();
		} else if (powerUp.getType().equals(PowerUp.POWER_UP_HEAL)) {
			health = maxHealth;
		} else if (powerUp.getType().equals(PowerUp.POWER_UP_BOMB)) {
			if (bombless)
				tempBombs++;
			else
				bombs++;
		}
	}

	private void handleSinglePowerUp(PowerUp powerUp) {
		if (powerUp == null)
			return;
		if (powerUpTypeInUse != null) {
			removePowerUpInUse();
		}
		if (powerUp.getType() == PowerUp.POWER_UP_DAMAGE)
			powerupDamage = true;
		if (powerUp.getType() == PowerUp.POWER_UP_SPEED)
			powerupSpeed = true;

		powerUpTypeInUse = powerUp.getType();
		addPowerUpPower();
	}

	private void addPowerUpPower() {
		if (powerUpTypeInUse.equals(PowerUp.POWER_UP_DAMAGE)) {
			damage *= 2;
		} else if (powerUpTypeInUse.equals(PowerUp.POWER_UP_SPEED)) {
			speed += PowerUp.SPEED_POWER_UP_VALUE;
		}
	}

	private void removePowerUpInUse() {
		if (powerUpTypeInUse.equals(PowerUp.POWER_UP_DAMAGE)) {
			deactivateDamagePowerUp();
			powerupDamage = false;
		} else if (powerUpTypeInUse.equals(PowerUp.POWER_UP_SPEED)) {
			deactivateSpeedPowerUp();
			powerupSpeed = false;
		}
	}

	private void deactivateDamagePowerUp() {
		if (powerupDamage) {
			damage /= 2;
			powerUpTypeInUse = null;
		}
	}

	private void deactivateSpeedPowerUp() {
		if (powerupSpeed) {
			speed -= PowerUp.SPEED_POWER_UP_VALUE;
			powerUpTypeInUse = null;
		}
	}

	abstract public Array<Shot> getLeftShots();

	abstract public Array<Shot> getMiddleShots();

	abstract public Array<Shot> getRightShots();

	abstract public int getShotCoolDown();

	abstract public void shoot(float delay);

	private float tempSpeed = 0;

	public void slowDownShip(boolean slowDown) {
		if (slowDown && !slowedDown) {
			if (powerUpTypeInUse != null && powerUpTypeInUse.equals(PowerUp.POWER_UP_SPEED))
				tempSpeed = speed - PowerUp.SPEED_POWER_UP_VALUE;
			else
				tempSpeed = speed;
			speed /= 2;
			illness = true;
		} else {
			if (powerUpTypeInUse != null && powerUpTypeInUse.equals(PowerUp.POWER_UP_SPEED))
				speed = tempSpeed + PowerUp.SPEED_POWER_UP_VALUE;
			else
				speed = tempSpeed;
			illness = false;
		}
		slowedDown = slowDown;
	}

	private int tempShield = 0;

	public void removeShipShield(boolean deactivateShield) {
		if (deactivateShield && !defensless) {
			tempShield = shield;
			shield = 0;
			illness = true;
		} else {
			shield = tempShield;
			tempShield = 0;
			illness = false;
		}
		defensless = deactivateShield;
	}

	private int tempBombs = 0;

	public void deactivateBombs(boolean deactivateBomb) {
		if (deactivateBomb && !bombless) {
			tempBombs = bombs;
			bombs = 0;
			illness = true;
		} else {
			bombs = tempBombs;
			tempBombs = 0;
			illness = false;
		}

		bombless = deactivateBomb;
	}

	public void setSpeedPowerUp() {
		if (powerupSpeed) {
			if (powerUpTypeInUse != null && powerUpTypeInUse.equals(PowerUp.POWER_UP_SPEED)) {
				deactivateSpeedPowerUp();
			} else {
				deactivateDamagePowerUp();
				powerUpTypeInUse = PowerUp.POWER_UP_SPEED;
				addPowerUpPower();
			}
		}
	}

	public void setDamagePowerUp() {
		if (powerupDamage) {
			if (powerUpTypeInUse != null && powerUpTypeInUse.equals(PowerUp.POWER_UP_DAMAGE)) {
				deactivateDamagePowerUp();
			} else {
				deactivateSpeedPowerUp();
				powerUpTypeInUse = PowerUp.POWER_UP_DAMAGE;
				addPowerUpPower();
			}
		}
	}

	public Integer getPowerUpTypeInUse() {
		return powerUpTypeInUse;
	}

	protected void handleShotPowerup() {
		if (shots < maxShots)
			shots++;
		else if (!spray)
			spray = true;
		else
			shield++; // TODO max power up
	}

	public int getHealth() {
		return health;
	}

	public boolean isSpray() {
		return spray;
	}

	public float getSpeed() {
		return speed;
	}

	public int getShield() {
		return shield;
	}

	public void gotHit(final int damage) {
		if (powerUpTypeInUse != null) {
			removePowerUpInUse();
		} else if (shield > 0) {
			this.shield -= damage;
		} else {
			health -= damage;
		}
		if (shield < 0) {
			health += shield;
			shield = 0;
		}
	}

	public int getDamage() {
		return damage;
	}

	public int getBombs() {
		return bombs;
	}

	public void setBombs(int bombs) {
		this.bombs = bombs;
	}

	public void dropBomb() {
		bombs--;
	}

	public int getShots() {
		return shots;
	}

	public int getMaxShots() {
		return maxShots;
	}

	public void setShield(int shield) {
		this.shield = shield;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setHealth(int health) {

		this.health = health;
	}

	abstract public boolean isMaxShots();

	public void setInvincible(boolean invincible) {
		this.invincible = invincible;
	}

	public boolean isInvincible() {
		return invincible;
	}

	public void setMovingLeft() {
		shipTexture.setRegion((maxHealth - health) * 28, 0, 28, 28);
	}

	public void setMovingRight() {
		shipTexture.setRegion((maxHealth - health) * 28, 56, 28, 28);
	}

	public void setMovingStop() {
		shipTexture.setRegion((maxHealth - health) * 28, 28, 28, 28);
	}

	public TextureRegion getShipTexture() {
		return shipTexture;
	}

	public Rectangle getShipHitSpace() {
		shipHitSpace.x = x + Sizes.SHIP_HIT_EVADE;
		shipHitSpace.y = y + Sizes.SHIP_HIT_EVADE;
		return shipHitSpace;
	}

	public Rectangle getShipPowerUpReach() {
		shipPowerUpReach.x = x + Sizes.SHIP_POWERUP_REACH_BOOST;
		shipPowerUpReach.y = y + Sizes.SHIP_POWERUP_REACH_BOOST;
		return shipPowerUpReach;
	}

	public void setSpray(boolean spray) {
		this.spray = spray;
	}

}