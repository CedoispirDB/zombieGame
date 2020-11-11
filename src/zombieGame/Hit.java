//package zombieGame;
//
//import java.awt.*;
//
//public class Hit extends GameObject{
//
//    private Handler handler;
//    private HUD hud;
//    private int score;
//    private GameObject BasicEnemy;
//
//
//    public Hit(HUD hud, Handler handler, Game game, float x, float y, ID id) {
//        super(handler, game, x, y, id);
//
//        this.handler = handler;
//        this.hud = hud;
//    }
//
//
//    public void hit() {
//        for (int i = 0; i < handler.object.size(); i++) {
//            GameObject tempObject = handler.object.get(i);
//
//            if (tempObject.getId() == ID.Bullet) {
//                // tempObject is now Bullet
//                if (getBounds().intersects(tempObject.getBounds())) {
//                    // Collision code
//                    handler.object.remove(this);
//                    handler.object.remove(tempObject);
//                    score++;
//                    System.out.println(score);
//                }
//
//            }
//        }
//    }
//
//    public void tick() {
//       score =  hud.getScore();
//    }
//
//    public void render(Graphics g) {
//    }
//
//    public Rectangle getBounds() {
//        return BasicEnemy.g
//    }
//}
