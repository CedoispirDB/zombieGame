package LevelBuilder.UserInput;

import LevelBuilder.Main.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseDragged implements MouseMotionListener {

    public MouseDragged() {}


    public void mouseDragged(MouseEvent e) {

    }

    // y < 160 x < 253
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (y <  160 && x < 253) {
            GamePanel.displayX = GamePanel.SCREEN_WIDTH - 7 * 32;
        } else {
            GamePanel.displayX = 30;
        }
    }
}
