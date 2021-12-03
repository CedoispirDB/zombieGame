package Player;

import Manager.GameObject;
import Manager.Handler;
import Manager.ID;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Shooting extends MouseAdapter {

    private Handler handler;
    private GameObject player;

    public Shooting(Handler handler) {
        this.handler = handler;
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject temp = handler.object.get(i);
            if (temp.getId() == ID.PLAYER) {
                player = temp;
            }
        }

        if (player != null) {
//            handler.addObject(new Bullet(player.getPosX() + 16, player.getPosY() + 16, 5, 5, handler, ID.Bullet, mx, my));
        }
    }

}
