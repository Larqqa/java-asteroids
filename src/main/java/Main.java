import engine.controls.Keyboard;
import engine.program.Program;
import engine.utilities.Colors;
import engine.utilities.Point;
import engine.visuals.PixelCanvas;
import engine.visuals.Renderer;
import engine.visuals.Window;

import java.security.Key;

public class Main extends Program {

    private Window w;
    private PixelCanvas p;
    private Renderer r;
    private Ship s;
    private Keyboard k;

    public Main() {
        w = new Window.Builder()
                .setWidth(300)
                .setHeight(300)
                .setScale(2)
                .setTitle("Asteroids")
                .build();

        p = new PixelCanvas(w);
        r = p.getRenderer();
        k = p.getKeyboard();
        s = new Ship();

//        setFrameCap(10);
        start();
    }

    @Override
    public void update() {
        s.update(k);
    }

    @Override
    public void render() {
        r.clear();
        s.draw(r);
        p.paint();
    }

    public static void main(String[] args) {
        new Main();
    }
}
