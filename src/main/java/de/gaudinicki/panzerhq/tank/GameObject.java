package de.gaudinicki.panzerhq.tank;

import java.awt.Point;

public abstract class GameObject implements IGameObject {
	
	protected Point objectPosition;
	protected final double width;
	protected final double height;
	protected double movingAngle;
	protected double movingDistance;

    protected GameObject(Point objectPosition, double width, double height, double movingAngle, double movingDistance) {
		this.objectPosition = objectPosition;
		this.width = width;
		this.height = height;
		this.movingAngle = movingAngle;
		this.movingDistance = movingDistance;
	}

	@Override
    public boolean isLeftOf(IGameObject that) {
        return this.getObjectPosition().getX() + this.getWidth() < that.getObjectPosition().getX();
    }

    @Override
    public boolean isAbove(IGameObject that) {
        return this.getObjectPosition().getY() + this.getHeight() < that.getObjectPosition().getY();
    }
    
    protected static Point polarToCartesianCoordinates(double angle) {
    	//x-axis points to east, y-axis points to south during paint
    	double x = Math.cos(angle);
    	double y = Math.sin(angle);
    	
    	return new Point((int) x, (int) y);
    }

    @Override
    public boolean touches(IGameObject that) {
        return !(this.isLeftOf(that) || that.isLeftOf(this) || this.isAbove(that) || that.isAbove(this));
    }

    @Override
    public void makeMove() {
        Point direction = polarToCartesianCoordinates(movingAngle);
        objectPosition.setLocation(objectPosition.getX() + direction.getX() * movingDistance, objectPosition.getY() + direction.getY() * movingDistance);
    }

	@Override
    public Point getObjectPosition() {
        return objectPosition;
    }

    @Override
    public void setObjectPosition(Point objectPosition) {
        this.objectPosition = objectPosition;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public void setMovingAngle(double movingAngle) {
        this.movingAngle = movingAngle;
    }
}
