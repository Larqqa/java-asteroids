package objects.asteroid;

import engine.utilities.Color;
import engine.utilities.Colors;
import engine.utilities.Point;
import engine.visuals.Renderer;
import objects.ship.Missile;
import objects.ship.Ship;

import java.util.ArrayList;

public class Asteroid {

    private final Point location;
    private final double direction;
    private final int speed = 2;
    private final int size;
    private static final double toDeg = (Math.PI / 180);
    private static final Color color = Colors.white();

    private static final ArrayList<Asteroid> asteroids = new ArrayList<>();

    private Asteroid(Point location, double direction, int maxSize) {
        this.location = location;
        this.direction = direction;
        int randomSize = (int) (maxSize * Math.random());
        this.size = Math.max(randomSize, 8);
    }

    public static void spawnAsteroid(int width, int height, int maxSize) {
        if (Math.round(Math.random()) == 1) {
            asteroids.add(new Asteroid(
                new Point(0, height * Math.random()),
                (360 * Math.random()) * toDeg, maxSize));
        } else {
            asteroids.add(new Asteroid(
                new Point(width * Math.random(), 0),
                (360 * Math.random()) * toDeg, maxSize));
        }
    }

    public static void spawnAsteroid(Point location, double direction, int maxSize) {
        asteroids.add(new Asteroid(
                new Point(location.getX(), location.getY()),
                direction, maxSize));
    }

    public static void update() {
        for (Asteroid asteroid : asteroids) {
            asteroid.getLocation().add(new Point(
                    (Math.cos(asteroid.direction) * asteroid.speed),
                    (Math.sin(asteroid.direction) * asteroid.speed)));
        }
    }

    public static void checkBoundary(int width, int height) {
        for (Asteroid asteroid : asteroids) {
            if (asteroid.getLocation().getX() < 0) {
                asteroid.getLocation().setX(width);
            }
            if (asteroid.getLocation().getX() > width + asteroid.size) {
                asteroid.getLocation().setX(0);
            }

            if (asteroid.getLocation().getY() < 0) {
                asteroid.getLocation().setY(height);
            }
            if (asteroid.getLocation().getY() > height + asteroid.size) {
                asteroid.getLocation().setY(0);
            }
        }
    }

    public static void checkMissileCollision(Missile missile) {
        for (int i = asteroids.size() - 1; i >= 0; i--) {
            Asteroid asteroid = asteroids.get(i);
            if (
                asteroid.getLocation().length(missile.getLocation()) < asteroid.size ||
                asteroid.getLocation().length(missile.getFront()) < asteroid.size
            ) {
                asteroid.spawnChildren();
                asteroids.remove(asteroid);
            }
        }
    }

    public static boolean checkShipCollision(Ship ship) {
        for (int i = asteroids.size() - 1; i >= 0; i--) {
            Asteroid asteroid = asteroids.get(i);
            if (
                asteroid.getLocation().length(ship.getFront()) < asteroid.size ||
                asteroid.getLocation().length(ship.getLeft()) < asteroid.size ||
                asteroid.getLocation().length(ship.getRight()) < asteroid.size
            ) {
                return true;
            }
        }

        return false;
    }

    private void spawnChildren() {
        if (size < 10) return;

        double angle = 45 * toDeg;
        Asteroid.spawnAsteroid(location, (direction - angle), size);
        Asteroid.spawnAsteroid(location, (direction + angle), size);
    }

    public static void draw(Renderer r) {
        for (Asteroid asteroid : asteroids) {
            r.circle(asteroid.getLocation(), asteroid.size, 1, Colors.white());
        }
    }

    public Point getLocation() {
        return location;
    }
}
