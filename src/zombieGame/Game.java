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
//    public static Color backGroundColor = new Color(11, 191, 47);

    // 0 = normal
    // 1 == hard

    private final Handler handler;
    private final HUD hud;
    private final Camera camera;
    private final Menu menu;
    private final Spawn spawn;


    private SpriteSheet ss;
    private SpriteSheet bss;
    private SpriteSheet sc;

    private final BufferedImage floor;
    BufferedImage level;

    public static int mana = 100;
    public static boolean started = false;
    public static int i = 0;


    public enum STATE {
        Menu,
        Select,
        Help,
        Game,
        End,
    }

    public STATE gameState = STATE.Menu;


    // Constructor
    public Game() {

        hud = new HUD(this);
        handler = new Handler(hud, this, ss);
        camera = new Camera(0, 0, handler);

        BufferedImageLoader loader = new BufferedImageLoader();
        // Load level
        level = loader.loadImage("/M.png");
        // Load Sprite Sheet
        BufferedImage spriteSheet = loader.loadImage("/B.png");
        ss = new SpriteSheet(spriteSheet);
        //Load Santa(Boss)
        BufferedImage santa = loader.loadImage("/C.png");
        sc = new SpriteSheet(santa);

        spawn = new Spawn(handler, hud, this, ss);
        // Load Bullet sprite sheet
        //BufferedImage bulletSS = loader.loadImage("/S.png");
        //bss = new SpriteSheet(bulletSS);


        MouseInput mouseInput = new MouseInput(this, handler, camera, ss);


        this.addKeyListener(new KeyInput(handler, this));
        this.addMouseListener(mouseInput);


        new Window(WIDTH, HEIGHT, "Demon King", this);

        menu = new Menu(this, handler, hud, ss, sc, spawn);

        this.addMouseListener(menu);

        if (gameState == STATE.Game) {
            handler.addObject(new DemonKing(this, 32, 32, ID.Player, handler, hud, ss));
//        handler.addObject(new Enemy(this, 48, 48, ID.Enemy, handler, hud, ss));
//        handler.addObject(new Poppy(this, 48, 48, ID.Poppy, handler, hud, ss));
            handler.addObject(new Santa(this, 500, 500, ID.Santa, handler, hud, sc, ss));
        }
        floor = ss.grabImage(4, 2, 32, 32);


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
        for (int i = 0; i < handler.object.size(); i++) {
            if (handler.object.get(i).getId() == ID.Player) {
                camera.tick(handler.object.get(i));
            }
        }
        if (gameState == STATE.Game) {
            if (!paused) {
                hud.tick();
                handler.tick();
                if (HUD.HEALTH <= 0) {
                    gameState = STATE.End;
                }

            }
            if (i == 0) {
                loadLevel(level);
                i++;
            }


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

        if (gameState == STATE.Game) {
            g2d.translate(-camera.getX(), -camera.getY());

            for (int x = 0; x < 30 * 70; x += 32) {
                for (int y = 0; y < 30 * 70; y += 32) {

                    g.drawImage(floor, x, y, null);

                }
            }

            handler.render(g);

            g2d.translate(camera.getX(), camera.getY());

            hud.render(g);
        } else if (gameState == STATE.Menu) {
            for (int x = 0; x < 30 * 72; x += 32) {
                for (int y = 0; y < 30 * 72; y += 32) {
                    g.drawImage(floor, x, y, null);

                }
            }
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
//                    handler.addObject(new Santa(this, x * 32, y * 32, ID.Santa, handler, hud, sc, ss));
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


