package de.gaudinicki.panzerhq.tank;

import java.awt.Color;
import java.awt.Point;
import java.util.Random;

public class EnemyTank extends Tank {
	
	private final ITank playersTank;

	private boolean targetLocked = false;
	
	public EnemyTank(Point position, double width, double height, double movingAngle, double movingDistance, ITank playersTank) {
		super(
            position,
            width,
            height,
            movingAngle,
            movingDistance,
            Tank.DEFAULT_TURNING_VELOCITY / 10 + Math.random() * 0.01,
            Tank.DEFAULT_DRIVING_VELOCITY / 10 + Math.random(),
            Tank.DEFAULT_AMMO_LOADING_TIME * 2,
            new Color(190,124,68),
            new Color(72,94,10),
            (int)(width / 10 + new Random().nextInt() * 5)
        );

		this.playersTank = playersTank;
		
		accelerateTank();
	}

    public boolean isTargetLocked() {
        return targetLocked;
    }

    @Override
    public void makeMove() {
    	if(this.objectPosition.getX() > 800 || this.objectPosition.getY() < 200) {
    		stopTank();
    	}
    	aimAtPlayer();
    	
    	super.makeMove();
    }
    
    public void aimAtPlayer() {
        // position of players tank center
        double playersTankCenterX = playersTank.getObjectPosition().getX() + playersTank.getWidth()/2;
        double playersTankCenterY = playersTank.getObjectPosition().getY() + playersTank.getHeight()/2;

        // position of enemies tank center
        double enemyTankCenterX = this.objectPosition.getX() + this.width / 2;
        double enemyTankCenterY = this.objectPosition.getY() + this.height / 2;
        
        // angle calculation
        double x = playersTankCenterX - enemyTankCenterX;
        double y = playersTankCenterY - enemyTankCenterY;
        double angleToPlayer = Math.atan2(y, x);

        if (angleToPlayer < 0) {
            angleToPlayer = angleToPlayer + 2 * Math.PI;
        }

        double absoluteCannonAngle = this.angleCannon + this.movingAngle;
        if (absoluteCannonAngle > 2 * Math.PI) {
            absoluteCannonAngle = absoluteCannonAngle - 2 * Math.PI;
        }

        double deltaAngle = angleToPlayer - absoluteCannonAngle; 
        
        if (Math.abs(deltaAngle) <= 0.01) {
            this.targetLocked = true;
            stopTurningCannon();
        }
        else {
            this.targetLocked = false;
            if (deltaAngle < 0.01) {
                turnCannonLeft();
                if (Math.abs(deltaAngle) > Math.toRadians(180)) {
                    turnCannonRight();
                }
            }
            if (deltaAngle > 0.01) {
                turnCannonRight();
                if (Math.abs(deltaAngle) >  Math.toRadians(180)) {
                    turnCannonLeft();
                }
            }
        }
    }

    @Override
    public boolean isAbleToShoot() {
	    return super.isAbleToShoot() && this.targetLocked;
    }
}
