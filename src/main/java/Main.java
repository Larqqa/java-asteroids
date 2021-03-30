import engine.controls.Keyboard;
import engine.program.Program;
import engine.utilities.Colors;
import engine.utilities.Point;
import engine.visuals.PixelCanvas;
import engine.visuals.Renderer;
import engine.visuals.Window;
import objects.asteroid.Asteroid;
import objects.ship.Missile;
import objects.ship.Ship;

public class Main extends Program {

    private final Window w;
    private final PixelCanvas p;
    private final Renderer r;
    private final Ship s;
    private final Keyboard k;
    private static final int width = 800;
    private static final int height = 600;

    public Main() {
        w = new Window.Builder()
                .setWidth(width)
                .setHeight(height)
                .setScale(1)
                .setTitle("Asteroids")
                .build();

        p = new PixelCanvas(w);
        r = p.getRenderer();
        k = p.getKeyboard();
        s = new Ship(new Point((double) width / 2, (double) height / 2), Colors.white());

        for (int i = 0; i < 30; i++) {
            Asteroid.spawnAsteroid(width, height, 30);
        }

        start();
    }

    @Override
    public void update() {
        s.move(k);
        s.shoot(k);
        s.checkBoundary(width, height);

        if (s.checkCollision()) {
            stop();
        }

        Missile.update();
        Missile.checkBoundary(width, height);
        Missile.checkCollision();

        Asteroid.update();
        Asteroid.checkBoundary(width, height);
    }

    @Override
    public void render() {
        r.clear();
        s.draw(r);
        Missile.draw(r);
        Asteroid.draw(r);
        p.paint();
    }

    public static void main(String[] args) {
        new Main();
    }
}
