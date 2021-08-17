package de.gaudinicki.panzerhq.tank;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;

public class Missile extends GameObject {
	private int range;
	
	public Missile(Point position, double size, double movingAngle, double movingDistance) {
		super(position, size, size / 3, movingAngle, movingDistance);

		this.range = 100;
	}

    @Override
    public void makeMove() {
    	if(range > 0) {
    		super.makeMove();
		}

    	range--;
    }
    
    @Override
    public void paintMe(java.awt.Graphics g) {
    	
    	Graphics2D g2d = (Graphics2D) g;
    	g2d.setColor(Color.BLACK);
    	
    	AffineTransform transform = new AffineTransform();
    	RoundRectangle2D missileShape = new RoundRectangle2D.Double(
    			this.objectPosition.getX(),
				this.objectPosition.getY(),
				this.width,
				this.height,
				3,
				3
		);
    	
    	transform.rotate(this.movingAngle, missileShape.getCenterX(), missileShape.getCenterY());
    	Shape transformedMissileShape = transform.createTransformedShape(missileShape);
    	
    	g2d.fill(transformedMissileShape);
    }

	public int getRange() {
		return range;
	}
}
