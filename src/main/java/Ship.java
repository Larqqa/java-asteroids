import engine.controls.Keyboard;
import engine.utilities.Color;
import engine.utilities.Colors;
import engine.utilities.Point;
import engine.visuals.Renderer;

public class Ship {

    private int height = 10;
    private int angle = 0;
    private double thrust = 0;
    private double torque = 0;

    private Point location = new Point(150,150);
    private Point front;
    private Point left;
    private Point right;
    private final double toDeg = (Math.PI / 180);

    private final Color color = new Color(1.0,1.0,1.0);

    public Ship() {
        double deg = angle * toDeg;
        front = new Point(
                Math.round(location.getX() + (Math.cos(deg) * height)),
                Math.round(location.getY() + (Math.sin(deg) * height)));

        deg = (angle - 110) * toDeg;
        left = new Point(
                Math.round(location.getX() + (Math.cos(deg) * height / 2)),
                Math.round(location.getY() + (Math.sin(deg) * height / 2)));

        deg = (angle + 110) * toDeg;
        right = new Point(
                Math.round(location.getX() + (Math.cos(deg) * height / 2)),
                Math.round(location.getY() + (Math.sin(deg) * height / 2)));
    }

    public void update(Keyboard k) {

        if (k.isKeyDown("W")) {
            thrust += .1;
        }

        if (k.isKeyDown("S")) {
            thrust -= .1;
        }


        if (k.isKeyDown("A")) {
            torque += .3;
        }

        if (k.isKeyDown("D")) {
            torque -= .3;
        }

        if (thrust > 5) {
            thrust = 5;
        }
        if (torque > 5) {
            torque = 5;
        }

//        location.add(new Point(1,1));
        angle += torque;

        double deg = (angle) * toDeg;
        front.setX(Math.round(location.getX() + (Math.cos(deg) * height)));
        front.setY(Math.round(location.getY() + (Math.sin(deg) * height)));

        deg = (angle - 110) * toDeg;
        left.setX(Math.round(location.getX() + (Math.cos(deg) * height / 2)));
        left.setY(Math.round(location.getY() + (Math.sin(deg) * height / 2)));

        deg = (angle + 110) * toDeg;
        right.setX(Math.round(location.getX() + (Math.cos(deg) * height / 2)));
        right.setY(Math.round(location.getY() + (Math.sin(deg) * height / 2)));

        deg = Math.atan2(front.getY() - location.getY(), front.getX() - location.getX());
        location.add(new Point((Math.cos(deg) * thrust), (Math.sin(deg) * thrust)));

        if (thrust > -0.1 && thrust < 0.1) {
            thrust = 0;
        } else {
            thrust *= 0.99;
        }

        if (torque > -0.1 && torque < 0.1) {
            torque = 0;
        } else {
            torque *= 0.97;
        }

        if (location.getX() < 0) {
            location.setX(300);
        }
        if (location.getX() > 300 + height) {
            location.setX(0);
        }

        if (location.getY() < 0) {
            location.setY(300);
        }
        if (location.getY() > 300 + height) {
            location.setY(0);
        }
    }

    public void draw(Renderer r) {
        r.triangle(left, front, right, 1, color);
    }
}
