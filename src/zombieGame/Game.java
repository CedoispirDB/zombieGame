package zombieGame;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.util.Random;

public class Game extends Canvas implements Runnable {

    public static int WIDTH = 2020;
    public static int HEIGHT = 1275;
    //HEIGHT = WIDTH / 12 * 8;

    private Thread thread;
    private boolean running = false;


    public static boolean paused = false;
    public static int diff = 0;
    public static Color backGroundColor = new Color(11, 191, 47);

    // 0 = normal
    // 1 == hard

    private final Handler handler;
    private final HUD hud;
    //    private final Spawn spawner;
//    private final Menu menu;
    //    private final EnemyBoss eb;
    private final MouseInput mouseInput;
    private final Camera camera;
    private SpriteSheet ss;

    public int ammo = 100;

    private BufferedImage spriteSheet = null;

    Random r = new Random();


    public enum STATE {
        Menu,
        Select,
        Help,
        Game,
        End,
        Test
    }

    public static STATE gameState = STATE.Test;

    // Constructor
    public Game() throws IOException {
        hud = new HUD();
        handler = new Handler(hud, this, ss);
//        eb = new EnemyBoss((WIDTH / 2) - 48, -115, ID.EnemyBoss, handler, hud);
//        menu = new Menu(this, handler, hud, eb);
        mouseInput = new MouseInput(handler);
        camera = new Camera(0, 0);


        this.addKeyListener(new KeyInput(handler, this, hud));
        this.addMouseListener(mouseInput);
//        this.addMouseListener(menu);


        new Window(WIDTH, HEIGHT, "Game", this);

//        spawner = new Spawn(handler, hud, this);

        BufferedImageLoader loader = new BufferedImageLoader();
        BufferedImage level = loader.loadImage("/level2.png");
        spriteSheet = loader.loadImage("/player.png");
        ss = new SpriteSheet(spriteSheet);


//        if (gameState == STATE.Game) {
//            handler.addObject(new Player(this, WIDTH / 2 - 32, HEIGHT / 2 - 32, ID.Player, handler, hud, ss));
////            for (int i = 0; i <= 10; i++ ) {
//            handler.addObject(new Obstacles(this, 100, 100, ID.Obstacles, handler, hud));
//            handler.addObject(new Obstacles(this, 400, 90, ID.Obstacles, handler, hud));
//            handler.addObject(new Obstacles(this, 800, 200, ID.Obstacles, handler, hud));
//            handler.addObject(new Obstacles(this, 300, 300, ID.Obstacles, handler, hud));
//            handler.addObject(new Obstacles(this, 200, 500, ID.Obstacles, handler, hud));
//            handler.addObject(new Obstacles(this, 900, 500, ID.Obstacles, handler, hud));
//            handler.addObject(new Obstacles(this, 700, 600, ID.Obstacles, handler, hud));
//            handler.addObject(new Obstacles(this, 600, 400, ID.Obstacles, handler, hud));
//            handler.addObject(new Obstacles(this, 1100, 400, ID.Obstacles, handler, hud));
//            handler.addObject(new Obstacles(this, 1000, 90, ID.Obstacles, handler, hud));
//            handler.addObject(new EasyZombie(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.SmartEnemy, handler, hud));
//
//
////            }
////            handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.BasicEnemy, handler, hud));
//
//        } else if (gameState == STATE.Test) {
//            loadLevel(level);
//
//        } else {
//            for (int i = 0; i < 10; i++) {
//                handler.addObject(new MenuParticle(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), ID.MenuParticle, handler));
//            }
//        }

        loadLevel(level);


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

//        if (gameState == STATE.Game) {
//            if (!paused) {
//                hud.tick();
////                spawner.tick();
//                handler.tick();
//
//                if (HUD.HEALTH <= 0) {
//                    HUD.HEALTH = 100;
//                    //Change State to end *not to game*
//                    gameState = STATE.Game;
//                    handler.clearEnemys();
////                    for (int i = 0; i < 10; i++) {
////                        handler.addObject(new MenuParticle(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), ID.MenuParticle, handler));
////                    }
//                    handler.addObject(new Obstacles(this, 100, 100, ID.Obstacles, handler, hud));
//                    handler.addObject(new Obstacles(this, 400, 90, ID.Obstacles, handler, hud));
//                    handler.addObject(new Obstacles(this, 800, 200, ID.Obstacles, handler, hud));
//                    handler.addObject(new Obstacles(this, 300, 300, ID.Obstacles, handler, hud));
//                    handler.addObject(new Obstacles(this, 200, 500, ID.Obstacles, handler, hud));
//                    handler.addObject(new Obstacles(this, 900, 500, ID.Obstacles, handler, hud));
//                    handler.addObject(new Obstacles(this, 700, 600, ID.Obstacles, handler, hud));
//                    handler.addObject(new Obstacles(this, 600, 400, ID.Obstacles, handler, hud));
//                    handler.addObject(new Obstacles(this, 1100, 400, ID.Obstacles, handler, hud));
//                    handler.addObject(new Obstacles(this, 1000, 90, ID.Obstacles, handler, hud));
//                    handler.addObject(new EasyZombie(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.SmartEnemy, handler, hud));
//                    hud.setScore(0);
//                }
//                if (EnemyBoss.enemyHealth <= 0) {
//                    EnemyBoss.enemyHealth = 200;
//                    gameState = STATE.End;
//                    handler.clearEnemys();
//                    for (int i = 0; i <= 10; i++) {
//                        handler.addObject(new MenuParticle(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), ID.MenuParticle, handler));
//                    }
//                }
//            }
//        } else if (gameState == STATE.Menu || gameState == STATE.End || gameState == STATE.Select) {
//            menu.tick();
//            handler.tick();
//        } else if (gameState == STATE.Test) {
//            hud.tick();
//            handler.tick();
//        }

        hud.tick();
        handler.tick();
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;


        g.setColor(backGroundColor.darker());
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g2d.translate(-camera.getX(), -camera.getY());

        handler.render(g);

        g2d.translate(camera.getX(), camera.getY());


        if (paused) {
            g.setColor(Color.white);
            g.drawString("PAUSED", 100, 100);
        }

//
//        if (gameState == STATE.Game) {
//            hud.render(g);
//        } else if (gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.End || gameState == STATE.Select) {
//            menu.render(g);
//        } else if (gameState == STATE.Test) {
//            hud.render(g);
//        }


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
                    handler.addObject(new Player(this, x * 32, y * 32, ID.Player, handler, hud, ss));
                }

                if (green == 255 && blue == 0) {
                    handler.addObject(new Enemy(this, x * 32, y * 32, ID.Enemy, handler, hud, ss));
                }

                if (green == 255 && blue == 255) {
                    handler.object.add(new Crate(this, x * 32, y * 32, ID.Crate, handler, hud, ss));
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

    public static void main(String[] args) throws IOException {
        new Game();
    }

}


