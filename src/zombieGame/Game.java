package zombieGame;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 1200, HEIGHT = WIDTH / 12 * 8;

    private Thread thread;
    private boolean running = false;


    public static boolean paused = false;
    public static int diff = 0;
    public static Color backGroundColor = new Color(0, 255, 51);

    // 0 = normal
    // 1 == hard

    private final Handler handler;
    private final HUD hud;
    private final Spawn spawner;
    private final Menu menu;
    private final EnemyBoss eb;

    Random r = new Random();


    public enum STATE {
        Menu,
        Select,
        Help,
        Game,
        End
    }

    public static STATE gameState = STATE.Menu;

    // Constructor
    public Game() {
        hud = new HUD();
        handler = new Handler(hud);
        eb = new EnemyBoss((WIDTH / 2) - 48, -115, ID.EnemyBoss, handler, hud);
        menu = new Menu(this, handler, hud, eb);

        this.addKeyListener(new KeyInput(handler, this, hud));
        this.addMouseListener(menu);

        new Window(WIDTH, HEIGHT, "Game", this);

        spawner = new Spawn(handler, hud, this);

        if (gameState == STATE.Game) {
            handler.addObject(new Player(WIDTH / 2 - 32, HEIGHT / 2 - 32, ID.Player, handler, hud));
//            for (int i = 0; i <= 10; i++ ) {
            handler.addObject(new Obstacles(this, 100, 100, ID.Obstacles, handler, hud));
            handler.addObject(new Obstacles(this, 400, 90, ID.Obstacles, handler, hud));
            handler.addObject(new Obstacles(this, 800, 200, ID.Obstacles, handler, hud));
            handler.addObject(new Obstacles(this, 300, 300, ID.Obstacles, handler, hud));
            handler.addObject(new Obstacles(this, 200, 500, ID.Obstacles, handler, hud));
            handler.addObject(new Obstacles(this, 900, 500, ID.Obstacles, handler, hud));
            handler.addObject(new Obstacles(this, 700, 600, ID.Obstacles, handler, hud));
            handler.addObject(new Obstacles(this, 600, 400, ID.Obstacles, handler, hud));
            handler.addObject(new Obstacles(this, 1100, 400, ID.Obstacles, handler, hud));
            handler.addObject(new Obstacles(this, 1000, 90, ID.Obstacles, handler, hud));
            handler.addObject(new EasyZombie(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.SmartEnemy, handler, hud));


//            }
//            handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.BasicEnemy, handler, hud));

        } else {
            for (int i = 0; i < 10; i++) {
                handler.addObject(new MenuParticle(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), ID.MenuParticle, handler));
            }
        }


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


        if (gameState == STATE.Game) {
            if (!paused) {
                hud.tick();
                spawner.tick();
                handler.tick();

                if (HUD.HEALTH <= 0) {
                    HUD.HEALTH = 100;
                    //Change State to end *not to game*
                    gameState = STATE.Game;
                    handler.clearEnemys();
//                    for (int i = 0; i < 10; i++) {
//                        handler.addObject(new MenuParticle(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), ID.MenuParticle, handler));
//                    }
                    handler.addObject(new Obstacles(this, 100, 100, ID.Obstacles, handler, hud));
                    handler.addObject(new Obstacles(this, 400, 90, ID.Obstacles, handler, hud));
                    handler.addObject(new Obstacles(this, 800, 200, ID.Obstacles, handler, hud));
                    handler.addObject(new Obstacles(this, 300, 300, ID.Obstacles, handler, hud));
                    handler.addObject(new Obstacles(this, 200, 500, ID.Obstacles, handler, hud));
                    handler.addObject(new Obstacles(this, 900, 500, ID.Obstacles, handler, hud));
                    handler.addObject(new Obstacles(this, 700, 600, ID.Obstacles, handler, hud));
                    handler.addObject(new Obstacles(this, 600, 400, ID.Obstacles, handler, hud));
                    handler.addObject(new Obstacles(this, 1100, 400, ID.Obstacles, handler, hud));
                    handler.addObject(new Obstacles(this, 1000, 90, ID.Obstacles, handler, hud));
                    handler.addObject(new EasyZombie(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.SmartEnemy, handler, hud));
                }
                if (EnemyBoss.enemyHealth <= 0) {
                    EnemyBoss.enemyHealth = 200;
                    gameState = STATE.End;
                    handler.clearEnemys();
                    for (int i = 0; i <= 10; i++) {
                        handler.addObject(new MenuParticle(r.nextInt(Game.WIDTH), r.nextInt(Game.HEIGHT), ID.MenuParticle, handler));
                    }
                }
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


        g.setColor(backGroundColor.darker());
        g.fillRect(0, 0, WIDTH, HEIGHT);

        handler.render(g);

        if (paused) {
            g.setColor(Color.white);
            g.drawString("PAUSED", 100, 100);
        }

        if (gameState == STATE.Game) {
            hud.render(g);
        } else if (gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.End || gameState == STATE.Select) {
            menu.render(g);
        }

        g.dispose();
        bs.show();

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
