package de.gaudinicki.panzerhq.tank;

import java.awt.Color;

public interface ITank extends IGameObject{
    int getEnergy();
    void setEnergy(int energy);
    void setAngleCannon(double angleCannon);
    boolean isAbleToShoot();

    Color getTurretColor();
    void setTurretColor(Color turretColor);
    Color getCannonColor();
    void setCannonColor(Color cannonColor);

    void turnTankRight();
    void turnTankLeft();
    void stopTurningTank();

    void turnCannonRight();
    void turnCannonLeft();
    void stopTurningCannon();

    void accelerateTank();
    void decelerateTank();
    void stopTank();

    Missile shoot();
}
