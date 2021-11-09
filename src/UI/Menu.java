package UI;

import Levels.LevelManager;
import Main.Game;
import Main.GamePanel;
import Manager.STATE;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;

public class Menu extends MouseAdapter {

    // Button variables
    private Font fnt2 = new Font("arial", Font.BOLD, 35);
    private int gap = 100;
    private int buttonWidth = 243;
    private int buttonHeight = 78;
    private int ox = (GamePanel.SCREEN_WIDTH - buttonWidth) / 2;
    private int oy = (GamePanel.SCREEN_HEIGHT - buttonHeight) / 2;
    private Color playColor;
    private Color boardColor;
    private Color helpColor;
    private Color quitColor;
    private boolean canQuit;
    private GamePanel gamePanel;

    public Menu(GamePanel gamePanel) {
        playColor = Color.WHITE;
        boardColor = Color.WHITE;
        helpColor = Color.WHITE;
        quitColor = Color.WHITE;
        canQuit = false;
        this.gamePanel = gamePanel;
    }

    public void mousePressed(MouseEvent e) {
        if (GamePanel.gameState == STATE.MENU) {
            int mx = e.getX();
            int my = e.getY();

            // Play button clicked
            if (mouseOver(mx, my, ox, oy - gap, buttonWidth, buttonHeight)) {
                playColor = Color.GRAY;
            }

            // Leaderboard button clicked
            if (mouseOver(mx, my, ox, oy, buttonWidth, buttonHeight)) {
                boardColor = Color.GRAY;
            }

            // Help button clicked
            if (mouseOver(mx, my, ox, oy + gap, buttonWidth, buttonHeight)) {
                helpColor = Color.GRAY;
            }

            // Quit button clicked
            if (mouseOver(mx, my, ox, oy + 2 * gap, buttonWidth, buttonHeight)) {
                quitColor = Color.GRAY;
                canQuit = true;
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (GamePanel.gameState == STATE.MENU) {
            int mx = e.getX();
            int my = e.getY();

            // Play button clicked
            if (mouseOver(mx, my, ox, oy - gap, buttonWidth, buttonHeight)) {
                GamePanel.gameState = STATE.GAME;
                gamePanel.startGame();
                playColor = Color.WHITE;
            }

            // Leaderboard button clicked
            if (mouseOver(mx, my, ox, oy, buttonWidth, buttonHeight)) {
                GamePanel.gameState = STATE.LEADERBOARD;
                boardColor = Color.WHITE;
            }

            // Help button clicked
            if (mouseOver(mx, my, ox, oy + gap, buttonWidth, buttonHeight)) {
                GamePanel.gameState = STATE.HELP;
                helpColor = Color.WHITE;
            }

            // Quit button clicked
            if (mouseOver(mx, my, ox, oy + 2 * gap, buttonWidth, buttonHeight)) {
                if (canQuit) {
                    System.exit(0);
                    playColor = Color.WHITE;
                    quitColor = Color.WHITE;
                }
            }
        }
    }

    public void render(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        float thickness = 5;
        g2d.setStroke(new BasicStroke(thickness));
        Font fnt = new Font("Serif", Font.BOLD, 60);
//        Color bColor = new Color(159, 226, 191);


//        g.setColor(new Color(37, 25, 14));
//        g.fillRect(0, 0, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);

        g.setColor(new Color(90, 90, 90).darker());
        g.fillRect(0, 20, GamePanel.SCREEN_WIDTH, 95);

        String title = "Dungeon";
        FontRenderContext frc = g2d.getFontRenderContext();
        int titleWidth = (int) fnt.getStringBounds(title, frc).getWidth();
        int titleHeight = (int) fnt.getStringBounds(title, frc).getHeight();

        g.setFont(fnt);
        g.setColor(Color.WHITE);
        g.drawString(title, (GamePanel.SCREEN_WIDTH/ 2) - (titleWidth / 2), 115 - (115 - (titleHeight)) / 2);

        // Buttons
        int yi = oy + (int) fnt2.getStringBounds("Play", frc).getHeight() - 5 + ((buttonHeight - (int) fnt2.getStringBounds("Play", frc).getHeight()) / 2) - 1;

        g.setColor(playColor);
        g.fillRect(ox, oy - gap, buttonWidth, buttonHeight);
        g.setColor(boardColor);
        g.fillRect(ox, oy, buttonWidth, buttonHeight);
        g.setColor(helpColor);
        g.fillRect(ox, oy + gap, buttonWidth, buttonHeight);
        g.setColor(quitColor);
        g.fillRect(ox, oy + 2  * gap, buttonWidth, buttonHeight);


        g.setColor(Color.BLACK);
        g.setFont(fnt2);
        g.drawRect(ox, oy - gap, buttonWidth, buttonHeight);
        g.drawString("Play", ox + ((buttonWidth - (int) fnt2.getStringBounds("Play", frc).getWidth()) / 2), yi - gap);

        g.drawRect(ox, oy, buttonWidth, buttonHeight);
        g.drawString("Leaderboard", ox + ((buttonWidth - (int) fnt2.getStringBounds("Leaderboard", frc).getWidth()) / 2), yi);

        g.drawRect(ox, oy + gap, buttonWidth, buttonHeight);
        g.drawString("Help", ox + ((buttonWidth - (int) fnt2.getStringBounds("Help", frc).getWidth()) / 2), yi + gap);

        g.drawRect(ox, oy + 2 * gap, buttonWidth, buttonHeight);
        g.drawString("Quit", ox + ((buttonWidth - (int) fnt2.getStringBounds("Quit", frc).getWidth()) / 2), yi + 2 * gap);

    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
        if (mx > x && mx < x + width) {
            return my > y && my < y + height;
        } else {
            return false;
        }
    }

}
