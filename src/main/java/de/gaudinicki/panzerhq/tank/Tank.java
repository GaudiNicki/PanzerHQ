package de.gaudinicki.panzerhq.tank;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;

public abstract class Tank extends GameObject implements ITank {
	
	public static final double DEFAULT_TURNING_VELOCITY = 0.03;
    public static final double DEFAULT_DRIVING_VELOCITY = 2.00;
    public static final int DEFAULT_AMMO_LOADING_TIME = 50;

    protected double turningVelocity;
    protected double drivingVelocity;
    protected int ammoLoadingTime;

    private Shape transformedTankBody;
        
    private double deltaMovingAngle;
    protected double angleCannon;
    private double deltaAngleCannon;

    private boolean ableToShoot;
    private int ammoLoadingTimer;
    protected Color turretColor;
    protected Color cannonColor;
    
    protected int energy;
    protected int energyStart;
    
    protected Tank(Point position, double width, double height, double movingAngle, double movingDistance, double turningVelocity, double drivingVelocity, int ammoLoadingTime, Color turretColor, Color cannonColor, int energy) {
        super(position, width, height, movingAngle, movingDistance);

        this.turningVelocity = turningVelocity;
        this.drivingVelocity = drivingVelocity;
        this.ammoLoadingTime = ammoLoadingTime;

        this.transformedTankBody = new RoundRectangle2D.Double();
        this.deltaMovingAngle = 0;
        this.angleCannon = 0;
        this.deltaAngleCannon = 0;

        this.ableToShoot = true;
        this.ammoLoadingTimer = ammoLoadingTime;
        this.turretColor = turretColor;
        this.cannonColor = cannonColor;

        this.energy = energy;
        this.energyStart = energy;
    }

    @Override
    public int getEnergy() {
        return energy;
    }
    @Override
    public void setEnergy(int energy) {
        this.energy = energy;
    }
    @Override
    public void setAngleCannon(double angleCannon) {
        this.angleCannon = angleCannon;
    }
    @Override
    public boolean isAbleToShoot() {
        return ableToShoot;
    }

    @Override
    public Color getTurretColor() {
        return turretColor;
    }
    @Override
    public void setTurretColor(Color turretColor) {
        this.turretColor = turretColor;
    }
    @Override
    public Color getCannonColor() {
        return cannonColor;
    }
    @Override
    public void setCannonColor(Color cannonColor) {
        this.cannonColor = cannonColor;
    }  

    @Override
    public void turnTankRight() {
        deltaMovingAngle = this.turningVelocity;
    }    
    @Override
    public void turnTankLeft() {
        deltaMovingAngle = - this.turningVelocity;
    }    
    @Override
    public void stopTurningTank() {
        deltaMovingAngle = 0;
    }

    @Override
    public void turnCannonRight() {
        deltaAngleCannon = this.turningVelocity * 1.3;
    }
    @Override
    public void turnCannonLeft() {
        deltaAngleCannon = - this.turningVelocity * 1.3;
    }
    @Override
    public void stopTurningCannon() {
        deltaAngleCannon = 0;
    }

    @Override
    public void accelerateTank() {
        this.movingDistance = this.turningVelocity;
    }
    @Override
    public void decelerateTank() {
        this.movingDistance = -this.turningVelocity / 2;
    }
    @Override
    public void stopTank() {
        this.movingDistance = 0;
    }  
    
    @Override
    public boolean touches(IGameObject other) {
        Point otherPosition = other.getObjectPosition();
        double otherCenterX = otherPosition.getX() + other.getWidth()/2;
        double otherCenterY = otherPosition.getY() + other.getHeight()/2;
        
        return this.transformedTankBody.contains(otherCenterX,otherCenterY);
    }
    
    @Override
    public void makeMove() {
        double newMovingAngle = this.movingAngle + deltaMovingAngle;
        if (newMovingAngle < 0) {
            newMovingAngle = newMovingAngle + 2 * Math.PI;
        }
        if (newMovingAngle > 2 * Math.PI) {
            newMovingAngle = newMovingAngle - 2 * Math.PI;
        }
        
        this.movingAngle = newMovingAngle;
        moveCannon();
        
        if (ammoLoadingTimer > 0) {
            ammoLoadingTimer -= 1;
        }
        else {
            this.ableToShoot = true;
        }
        
        super.makeMove();
    }
    
    private void moveCannon() {
        angleCannon = angleCannon + deltaAngleCannon;
        if (angleCannon < 0) {
            angleCannon = angleCannon + 2 * Math.PI;
        }
        if (angleCannon > 2 * Math.PI) {
            angleCannon = angleCannon - 2 * Math.PI;
        }        
    }

    @Override
	public Missile shoot() {
        double tankCenterX = this.objectPosition.getX() + this.width * 0.5;
        double tankCenterY = this.objectPosition.getY() + this.height * 0.5;
        double cannonLength = this.width * 0.8;
        
        double missileSize = this.width * 0.12;
        double missileAngle = this.angleCannon + this.movingAngle;
        Point missileDirection = polarToCartesianCoordinates(missileAngle);
        double cannonEndX = missileDirection.getX() * cannonLength;
        double cannonEndY = missileDirection.getY() * cannonLength;
        
        Point missileStartPosition = new Point((int) (tankCenterX + cannonEndX - missileSize / 2), (int) (tankCenterY + cannonEndY - missileSize / 6));
                
        Missile missile = new Missile(missileStartPosition, missileSize, missileAngle, 5);
        this.ableToShoot = false;
        
        return missile;        
    }
	
	@Override
    public void paintMe(java.awt.Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        paintTankStatusBars(g2d);
        paintTank(g2d);
    }

    private void paintTankStatusBars(Graphics2D g2d) {
        double barOffsetY = this.height * 0.6;

        // paint Tank Energy Bar
        g2d.setColor(Color.DARK_GRAY);
        RoundRectangle2D tankEnergyBarFrame = new RoundRectangle2D.Double(
                Math.round(this.objectPosition.getX()) - 1.0d,
                Math.round(this.objectPosition.getY() - barOffsetY) - 1.0d,
                this.width + 1,
                6,
                0,
                0
        );
        g2d.draw(tankEnergyBarFrame);

        if (getEnergy() > 3) {
            g2d.setColor(Color.GREEN);
        } else {
            g2d.setColor(Color.RED);
        }

        RoundRectangle2D tankEnergyBar = new RoundRectangle2D.Double(
                Math.round(this.objectPosition.getX()),
                Math.round(this.objectPosition.getY() - barOffsetY),
                this.width / this.energyStart * energy,
                5,
                0,
                0
        );
        g2d.fill(tankEnergyBar);

        // paint Ammo Loading Bar
        g2d.setColor(Color.DARK_GRAY);
        RoundRectangle2D ammoLoadingBar = new RoundRectangle2D.Double(
                Math.round(this.objectPosition.getX()),
                Math.round(this.objectPosition.getY() - barOffsetY) - 5.0d,
                this.width / this.ammoLoadingTime * this.ammoLoadingTimer,
                2,
                0,
                0
        );

        if (!isAbleToShoot()) {
            g2d.fill(ammoLoadingBar);
        }
    }

	private void paintTank(Graphics2D g2d) {        
        RoundRectangle2D tankBody = new RoundRectangle2D.Double(
                this.objectPosition.getX() + this.width * 0.05,
                this.objectPosition.getY(),
                this.width * 0.9,
                this.height,
                15,
                8
        );
        
        RoundRectangle2D tankTrackLeft = new RoundRectangle2D.Double(
                this.objectPosition.getX(),
                this.objectPosition.getY(),
                this.width,
                this.height * 0.3,
                15,
                8
        );
        
        RoundRectangle2D tankTrackRight = new RoundRectangle2D.Double(
                this.objectPosition.getX(),
                this.objectPosition.getY() + this.height * 0.7,
                this.width,
                this.height * 0.3,
                15,
                8
        );
        
        RoundRectangle2D tankCannon = new RoundRectangle2D.Double(
                this.objectPosition.getX() + this.width * 0.5,
                this.objectPosition.getY() + this.height * 0.41,
                this.width * 0.8,
                this.height * 0.18,
                5,
                5
        );
        
        RoundRectangle2D tankTurret = new RoundRectangle2D.Double(
                this.objectPosition.getX() + this.width * 0.14,
                this.objectPosition.getY() + this.height * 0.1,
                this.width * 0.65,
                this.height * 0.8,
                15,
                8
        );

        AffineTransform transform = new AffineTransform();                        

        transform.rotate(this.movingAngle, tankBody.getCenterX(), tankBody.getCenterY());
        g2d.setColor(Color.GRAY);
        Shape transformed = transform.createTransformedShape(tankBody);
        g2d.fill(transformed);
        this.transformedTankBody = transformed;

        g2d.setColor(Color.BLACK);
        transformed = transform.createTransformedShape(tankTrackLeft);
        g2d.fill(transformed);

        transformed = transform.createTransformedShape(tankTrackRight);
        g2d.fill(transformed);

        g2d.setColor(cannonColor);
        transform.rotate(this.angleCannon, tankBody.getCenterX(), tankBody.getCenterY());
        transformed = transform.createTransformedShape(tankCannon);
        g2d.fill(transformed);

        g2d.setColor(turretColor);        
        transformed = transform.createTransformedShape(tankTurret);
        g2d.fill(transformed);        
    }
}
