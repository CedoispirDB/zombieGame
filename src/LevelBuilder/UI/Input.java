package LevelBuilder.UI;

import LevelBuilder.Main.GamePanel;
import LevelBuilder.UserInput.KeyInput;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.security.Key;

public class Input extends KeyAdapter {

    public static String num;

    public Input() {
        num = "1";
    }

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (GamePanel.canInput) {
            if (code > 48 && code < 58) {
                num = num + (char) code;
            }
            if (code == 8) {
                num = num.substring(0, num.length() - 1);
            }
        }


    }

    public void render(Graphics g) {
        int w = 300;

        g.setColor(new Color(159, 226, 191));
        g.fillRect(GamePanel.SCREEN_WIDTH / 2 - w / 2, 10, w ,50);
        g.setColor(Color.white);
        g.fillRect((GamePanel.SCREEN_WIDTH / 2 - w / 2) + 5, 15, w - 10 ,40);
        g.setColor(Color.BLACK);
        g.drawString(num, (GamePanel.SCREEN_WIDTH / 2 - w / 2) + 10, 40);
    }

    public static int getNum() {
        System.out.println(num);
        return Integer.parseInt(num);
    }

    public static void updateNum() {
        num = String.valueOf(Integer.parseInt(num) + 1);
    }


}
