package de.gaudinicki.panzerhq.tank;

import java.awt.Color;
import java.awt.Point;

public class PlayerTank extends Tank {
    public PlayerTank(Point position, double width, double height, double movingAngle, double movingDistance) {
        super(position, width, height, movingAngle, movingDistance, Tank.DEFAULT_TURNING_VELOCITY, Tank.DEFAULT_DRIVING_VELOCITY, Tank.DEFAULT_AMMO_LOADING_TIME, new Color(160,184,98), new Color(72,94,10), 10);
    }
}
