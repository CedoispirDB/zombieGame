package UI;

import Main.GamePanel;
import Manager.STATE;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;

public class Pause extends MouseAdapter {

    private int ox = (GamePanel.SCREEN_WIDTH - 244) / 2;
    private int oy = (GamePanel.SCREEN_HEIGHT - 78) / 2;
    private int posX;
    private int titleWidth;
    private boolean help;
    private GamePanel gamePanel;

    public Pause(GamePanel gamePanel) {
        help = false;
        this.gamePanel = gamePanel;
    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {
        if (GamePanel.gameState == STATE.PAUSE) {
            int mx = e.getX();
            int my = e.getY();

            if (mouseOver(mx, my,posX, oy - 120, titleWidth, 40)) {
                if (!help) {
                    GamePanel.gameState = STATE.GAME;
                }
            }

            if (mouseOver(mx, my,posX, oy - 60, titleWidth, 40)) {
                if (!help) {
                    help = true;
                }
            }

            if (mouseOver(mx, my,posX, oy , titleWidth, 40)) {
                if (!help) {
                    gamePanel.restartGame();
                    GamePanel.gameState = STATE.MENU;
                } else {
                    help = false;
                }
            }
        }
    }

    public void render(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        Font fnt = new Font("Serif", Font.BOLD, 50);
        Font fnt2 = new Font("Arial", Font.BOLD, 20);

        g.setColor(new Color(0.2f, 0.2f, 0.2f, 0.9f));
        g.fillRect(ox, oy - 200, 244, 300);

        g.setColor(Color.black);
        String title = "Pause";
        g.setFont(fnt);
        FontRenderContext frc = g2d.getFontRenderContext();
        titleWidth = (int) fnt.getStringBounds(title, frc).getWidth();
        posX = (ox + 122) - titleWidth / 2;
        g.drawString(title, posX, oy - 150);
        g.drawRect(ox, oy - 200, 244, 300);

        if(!help) {
            g.setColor(Color.white);
            g.fillRect(posX, oy - 120, titleWidth, 40);
            g.fillRect(posX, oy - 60, titleWidth, 40);
            g.fillRect(posX, oy, titleWidth, 40);

            g.setColor(Color.BLACK);
            g.drawRect(posX, oy - 120, titleWidth, 40);
            g.drawRect(posX, oy - 60, titleWidth, 40);
            g.drawRect(posX, oy, titleWidth, 40);

            g.setFont(fnt2);
            g.drawString("Continue", posX + ((titleWidth - (int) fnt2.getStringBounds("Continue", frc).getWidth()) / 2), oy - 93);
            g.drawString("Help", posX + ((titleWidth - (int) fnt2.getStringBounds("Help", frc).getWidth()) / 2), oy - 33);
            g.drawString("Menu", posX + ((titleWidth - (int) fnt2.getStringBounds("Menu", frc).getWidth()) / 2), oy + 27);
        } else {
            g.setColor(Color.white);
            g.fillRect(posX - 20, oy - 120, titleWidth + 40, 100);
            g.fillRect(posX, oy, titleWidth, 40);

            g.setColor(Color.BLACK);
            g.drawRect(posX - 20, oy - 120, titleWidth + 40, 100);
            g.drawRect(posX, oy, titleWidth, 40);

            g.setFont(new Font("Arial", Font.BOLD, 15));
            g.drawString("Movement: WASD", posX, oy - 85);
            g.drawString("Drop items: F", posX, oy - 65);
            g.drawString("Select items: Q and E", posX, oy - 45);

            g.setFont(fnt2);
            g.drawString("Back", posX + ((titleWidth - (int) fnt2.getStringBounds("Back", frc).getWidth()) / 2), oy + 27);




        }
    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
        if (mx > x && mx < x + width) {
            return my > y && my < y + height;
        } else {
            return false;
        }
    }

}
