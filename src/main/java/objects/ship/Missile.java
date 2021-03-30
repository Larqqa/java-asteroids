package objects.ship;

import engine.utilities.Color;
import engine.utilities.Colors;
import engine.utilities.Point;
import engine.visuals.Renderer;
import objects.asteroid.Asteroid;

import java.util.ArrayList;

public class Missile {
    private final Point location;
    private final Point front;
    private final double direction;
    private final int speed = 2;
    private final int length = 3;
    private static final Color color = Colors.white();

    private static final ArrayList<Missile> missiles = new ArrayList<>();

    private Missile(Point location, double direction) {
        this.location = location;
        this.direction = direction;
        this.front = new Point(
            Math.round(location.getX() + (Math.cos(direction) * length)),
            Math.round(location.getY() + (Math.sin(direction) * length)));
    }

    public static void shootMissile(Point location, double direction) {
        missiles.add(new Missile(new Point(location.getX(), location.getY()), direction));
    }

    public static void update() {
        for (Missile missile : missiles) {
            missile.getLocation().add(new Point(
                    (Math.cos(missile.direction) * missile.speed),
                    (Math.sin(missile.direction) * missile.speed)));
            missile.getFront().setX(Math.round(missile.getLocation().getX() + (Math.cos(missile.direction) * 3)));
            missile.getFront().setY(Math.round(missile.getLocation().getY() + (Math.sin(missile.direction) * 3)));
        }
    }

    public static void checkCollision() {
        for (Missile missile : missiles) {
            Asteroid.checkMissileCollision(missile);
        }
    }

    public static void checkBoundary(int width, int height) {
        for (int i = missiles.size() - 1; i >= 0; i--) {
            Missile missile = missiles.get(i);

            if (missile.getLocation().getX() < 0 || missile.getLocation().getX() > width) {
                missiles.remove(missile);
            }

            if (missile.getLocation().getY() < 0 || missile.getLocation().getY() > height) {
                missiles.remove(missile);
            }
        }
    }

    public static void draw(Renderer r) {
        for (Missile missile : missiles) {
            r.line(missile.getLocation(), missile.front, color);
            missile.getLocation().add(new Point((Math.cos(missile.direction) * 2), (Math.sin(missile.direction) * 2)));
        }
    }

    public Point getLocation() {
        return location;
    }

    public Point getFront() {
        return front;
    }
}
