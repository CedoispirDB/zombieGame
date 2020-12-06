//package zombieGame;
//
//import java.awt.*;
//import java.util.Random;
//
//public class EnemyBoss extends GameObject {
//
//    private Handler handler;
//    Random r = new Random();
//    private HUD hud;
//
//    public static float enemyHealth = 100;
//    private float greenValue = 255;
//    private boolean vulnerability = true;
//
//    private int timer = 80;
//    private int timer2 = 50;
//
//
//    public EnemyBoss(int x, int y, ID id, Handler handler, HUD hud) {
//        super(x, y, id);
//
//        this.handler = handler;
//        this.hud = hud;
//
//        velX = 0;
//        velY = 2;
//    }
//
//    public void setEnemyHealth(int enemyHealth){
//        EnemyBoss.enemyHealth = enemyHealth;
//    }
//
//    public void hit() {
//        if (handler.object.size() != 0) {
//            for (int i = 0; i < handler.object.size(); i++) {
//                GameObject tempObject = handler.object.get(i);
//
//                if (tempObject.getId() == ID.Bullet) {
//                    // tempObject is now Bullet
//                    if (getBounds().intersects(tempObject.getBounds())) {
//                        // Collision code
//                        if (tempObject.getX() != this.getX() && tempObject.getY() != this.getY()) {
//                            for (int j = 0; j < handler.object.size(); j++) {
//                                GameObject enemy = handler.object.get(j);
//                                if (enemy.getId() == ID.EnemyBoss && !vulnerability){
//                                    enemyHealth = enemyHealth - 5;
//                                    handler.object.remove(tempObject);
//                                    int x = hud.getScore();
//                                    hud.setScore(x + 1);
//                                }
//                            }
//
//                        }
//                    }
//
//                }
//            }
//        }
//    }
//
//    public Rectangle getBounds() {
//        return new Rectangle((int) x, (int) y, 96, 96);
//    }
//
//    @Override
//    public Rectangle getBounds2() {
//        return null;
//    }
//
//    public void tick() {
//
//        enemyHealth = Game.clamp(enemyHealth, 0, 100);
//        greenValue = Game.clamp(greenValue, 0, 255);
//
//        greenValue = enemyHealth * 2;
//
//        x += velX;
//        y += velY;
//
//        if (timer <= 0) {
//            velY = 0;
//        } else {
//            timer--;
//        }
//        if (timer <= 0) {
//            timer2--;
//        }
//        if (timer2 <= 0) {
//            vulnerability = false;
//            if (velX == 0) {
//                velX = 3;
//            }
//            if (velX > 0) {
//                velX += 0.005;
//            }else {
//                velX -= 0.005;
//            }
//
//            velX = Game.clamp(velX,-10,10);
//            int spawn = r.nextInt(10);
//            if (spawn == 0) {
//                handler.addObject(new EnemyBossBullet((int) x + 48, (int) y + 48, ID.EnemyBullet, handler));
//            }
//
//        }
//
//        if (x <= 0 || x >= Game.WIDTH - 96) {
//            velX *= -1;
//        }
//
//        //handler.addObject(new Trail(x, y, ID.Trial,Color.red, 96, 96, 0.008f, handler));
//        hit();
//
//    }
//
//    public void render(Graphics g) {
//        g.setColor(Color.red);
//        g.fillRect((int) x, (int) y, 96, 96);
//
//
//        g.setColor(Color.gray);
//        g.fillRect(230, 15, 200, 32);
//
//        g.setColor(Color.white);
//        g.drawRect(230, 15, 200, 32);
//
//        g.setColor(new Color(75, (int)greenValue, 0));
//        g.fillRect(230, 15, (int) (enemyHealth * 2), 32);
//
//    }
//}
