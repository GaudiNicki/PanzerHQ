package de.gaudinicki.panzerhq.tank;

import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

public interface IGameObject extends Serializable {
    Point getObjectPosition();
    double getWidth();
    double getHeight();

    void setObjectPosition(Point objectPosition);
    void setMovingAngle(double movingAngle);

    boolean isLeftOf(IGameObject that);
    boolean isAbove(IGameObject that);
    boolean touches(IGameObject that);

    void makeMove();
    void paintMe(Graphics g);
}
