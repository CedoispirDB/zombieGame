package zombieGame;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {

    public static int WIDTH = 1400;
    public static int HEIGHT = 800;
    //HEIGHT = WIDTH / 12 * 8;

    private Thread thread;
    private boolean running = false;


    public static boolean paused = false;
    // Debugging purpose
    public static boolean showBounds = false;

    private final Handler handler;
    private final HUD hud;
    private final Camera camera;
    private final Menu menu;
    private final Spawn spawn;
    private final Map map;


    private SpriteSheet ss;
    private SpriteSheet sc;



    public static int mana = 100;
    public static boolean started = false;
    public static int i = 0;


    public enum STATE {
        Menu,
        Select,
        Help,
        Garden,
        End,
        Castle,
    }

    public STATE gameState = STATE.Menu;


    // Constructor
    public Game() {

        hud = new HUD(this);
        handler = new Handler(hud, this, ss);
        camera = new Camera(0, 0, handler);
        BufferedImageLoader loader = new BufferedImageLoader();

        //load map
        map = new Map(this, handler, hud);

        spawn = new Spawn(handler, hud, this, ss);


        MouseInput mouseInput = new MouseInput(this, handler, camera, ss, map);


        this.addKeyListener(new KeyInput(handler, this));
        this.addMouseListener(mouseInput);


        new Window(WIDTH, HEIGHT, "Demon King", this);

        menu = new Menu(this, handler, hud, ss, sc, spawn, map);

        this.addMouseListener(menu);

        //Pass it to a map class


    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Game loop
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            if (running) {
                render();
            }
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
//                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {
//        System.out.println(gameState);
        for (int i = 0; i < handler.object.size(); i++) {
            if (handler.object.get(i).getId() == ID.Player) {
                camera.tick(handler.object.get(i));
            }
        }
        if (gameState == STATE.Castle) {
            handler.tick();
        } else if (gameState == STATE.Garden) {
            if (!paused) {
                hud.tick();
                handler.tick();
                if (HUD.HEALTH <= 0) {
                    gameState = STATE.End;
                }

            }

            map.chooseLevel();

        } else if (gameState == STATE.Menu || gameState == STATE.End || gameState == STATE.Select) {
            menu.tick();
            handler.tick();
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;

        if (gameState == STATE.Castle) {
            map.chooseLevel();
            map.render(g);
            handler.render(g);
        } else if (gameState == STATE.Garden) {
            g2d.translate(-camera.getX(), -camera.getY());

            map.render(g);

            handler.render(g);

            g2d.translate(camera.getX(), camera.getY());

            hud.render(g);
        } else if (gameState == STATE.Menu) {
            map.render(g);
            menu.render(g);
        } else if (gameState == STATE.End) {
            menu.render(g);
        }
        if (paused) {
            g.setColor(Color.white);
            g.drawString("PAUSED", 100, 100);
        }

        g.dispose();
        bs.show();

    }

    // Loading the level
    private void loadLevel(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                int pixel = image.getRGB(x, y);

                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                if (red == 255) {
                    handler.addObject(new Block(this, x * 32, y * 32, ID.Block, handler, hud, ss));
                }

                if (blue == 255 && green == 0) {
                    handler.addObject(new Santa(this, x * 32, y * 32, ID.Santa, handler, hud, sc, ss));
//                    handler.addObject(new Enemy(this, x * 32, y * 32, ID.Enemy, handler, hud, ss));

                }

                if (green == 255 && blue == 0) {
//                    handler.addObject(new Enemy(this, x * 32, y * 32, ID.Enemy, handler, hud, ss));
                }

                if (green == 255 && blue == 255) {
//                    handler.object.add(new Crate(this, x * 32, y * 32, ID.Crate, handler, hud, ss));
                }
            }
        }

    }

    public static float clamp(float var, float min, float max) {
        if (var >= max) {
            return max;
        } else if (var <= min) {
            return min;
        }
        return var;
    }

    public static void main(String[] args) {
        new Game();
    }

}


