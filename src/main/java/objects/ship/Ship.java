package objects.ship;

import engine.controls.Keyboard;
import engine.utilities.Color;
import engine.utilities.Colors;
import engine.utilities.Point;
import engine.visuals.Renderer;
import objects.asteroid.Asteroid;

import java.sql.SQLOutput;

public class Ship {

    private final int size = 10;
    private final double toDeg = (Math.PI / 180);
    private final Point location;
    private final Point front;
    private final Point left;
    private final Point right;
    private final Color color;

    private final double maxThrust = 4;
    private final double thrustModifier = 0.1;
    private final double maxTorque = 4;
    private final double torqueModifier = 0.3;

    private int angle = -90;
    private double deg = angle * toDeg;
    private double thrust = 0;
    private double torque = 0;
    private int shotDelay = 0;

    public Ship(Point startingLocation, Color color) {
        location = startingLocation;
        this.color = color;

        double deg = angle * toDeg;
        front = new Point(
                Math.round(location.getX() + (Math.cos(deg) * size)),
                Math.round(location.getY() + (Math.sin(deg) * size)));

        deg = (angle - 110) * toDeg;
        left = new Point(
                Math.round(location.getX() + (Math.cos(deg) * size / 2)),
                Math.round(location.getY() + (Math.sin(deg) * size / 2)));

        deg = (angle + 110) * toDeg;
        right = new Point(
                Math.round(location.getX() + (Math.cos(deg) * size / 2)),
                Math.round(location.getY() + (Math.sin(deg) * size / 2)));
    }

    public void move(Keyboard k) {
        if (k.isKeyDown("W")) {
            thrust += thrustModifier;
        }

        if (k.isKeyDown("S")) {
            thrust -= thrustModifier;
        }


        if (k.isKeyDown("A")) {
            torque += torqueModifier;
        }

        if (k.isKeyDown("D")) {
            torque -= torqueModifier;
        }

        if (thrust > maxThrust) {
            thrust = maxThrust;
        }

        if (torque > maxTorque) {
            torque = maxTorque;
        }

        if (thrust > -thrustModifier && thrust < thrustModifier) {
            thrust = 0;
        } else {
            thrust *= 1 - (thrustModifier / 10);
        }

        if (torque > -torqueModifier && torque < torqueModifier) {
            torque = 0;
        } else {
            torque *= 1 - (torqueModifier / 10);
        }

        angle += torque;

        deg = (angle) * toDeg;
        front.setX(Math.round(location.getX() + (Math.cos(deg) * size)));
        front.setY(Math.round(location.getY() + (Math.sin(deg) * size)));

        deg = (angle - 110) * toDeg;
        left.setX(Math.round(location.getX() + (Math.cos(deg) * size / 2)));
        left.setY(Math.round(location.getY() + (Math.sin(deg) * size / 2)));

        deg = (angle + 110) * toDeg;
        right.setX(Math.round(location.getX() + (Math.cos(deg) * size / 2)));
        right.setY(Math.round(location.getY() + (Math.sin(deg) * size / 2)));

        deg = Math.atan2(front.getY() - location.getY(), front.getX() - location.getX());
        location.add(new Point((Math.cos(deg) * thrust), (Math.sin(deg) * thrust)));
    }

    public void shoot(Keyboard k) {
        if (k.isKeyDown("Space")) {
            if (shotDelay == 0) {
                Missile.shootMissile(location, deg);
            }

            shotDelay++;
            if (shotDelay > 10) {
                shotDelay = 0;
            }
        }
    }

    public boolean checkCollision() {
        return Asteroid.checkShipCollision(this);
    }

    public void checkBoundary(int width, int height) {
        if (location.getX() < 0) {
            location.setX(width);
        }
        if (location.getX() > width + size) {
            location.setX(0);
        }

        if (location.getY() < 0) {
            location.setY(height);
        }
        if (location.getY() > height + size) {
            location.setY(0);
        }
    }

    public void draw(Renderer r) {
        r.triangle(left, front, right, 1, color);
        Missile.draw(r);
    }

    public Point getFront() {
        return front;
    }

    public Point getLeft() {
        return left;
    }

    public Point getRight() {
        return right;
    }
}
