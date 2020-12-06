package zombieGame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {

    private Handler handler;
    private GameObject player;
    private int dir;
    protected SpriteSheet ss;

    public MouseInput(Handler handler) {
        this.handler = handler;
    }

//    public void mousePressed(MouseEvent e) {
//
//        int mx = e.getX();
//        int my = e.getY();
//
//        if (tempPlayer != null) {
//
//            float angle = (float) Math.atan2(my - tempPlayer.y, mx - tempPlayer.x);
//            int bulletVel = 5;
//            handler.addObject(new Bullet(tempPlayer.x + 8, tempPlayer.y + 8, ID.Bullet, handler, dir));
//            for (int i = 0; i < handler.object.size(); i++) {
//                if (handler.object.get(i).getId() == ID.Bullet) {
//                    GameObject tempBullet = handler.object.get(i);
//
//                    tempBullet.velX = (float)((bulletVel) * Math.cos(angle));
//                    tempBullet.velY = (float)((bulletVel) * Math.sin(angle));
//                }
//            }
//        } else{
//            findPlayer();
//        }
//    }

    public void mousePressed(MouseEvent e) {

        int mx = e.getX();
        int my = e.getY();

//        System.out.println(mx + " || " + my);

        for (int i = 0; i < handler.object.size(); i++) {
            if (handler.object.get(i).getId() == ID.Player) {
                player = handler.object.get(i);
            }
        }
        handler.addObject(new BulletMouse(player.getX() + 8, player.getY() + 8, ID.Bullet, handler, mx, my, ss));

    }

}
