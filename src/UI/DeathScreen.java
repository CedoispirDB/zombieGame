package UI;

import DataManager.ScoreManager;
import Main.GamePanel;
import Manager.STATE;
import Player.Interface;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;

public class DeathScreen extends MouseAdapter {

    private int gap = 100;
    private int buttonWidth = 243;
    private int buttonHeight = 78;
    private int ox = (GamePanel.SCREEN_WIDTH - buttonWidth) / 2;
    private int oy = (GamePanel.SCREEN_HEIGHT - buttonHeight) / 2;
    private final Interface anInterface;
    private final ScoreManager scoreManager;
    private final GamePanel gamePanel;
    private String input;
    private Color inputColor;
    private boolean canWrite;
    private Color submitColor;
    private String leaderboard;
    private boolean nameAvailable;
    private boolean promptClicked;
    private Color promptColor;
    private Font fnt = new Font("Serif", Font.BOLD, 60);
    private Font fnt2 = new Font("Arial", Font.BOLD, 30);
    private Font fnt3 = new Font("Arial", Font.BOLD, 25);
    private Font fnt4 = new Font("Arial", Font.BOLD, 35);
    private int buttonX, buttonY;
    private int inputX, inputY;
    private int promptX, promptY;
    private boolean r;


    public DeathScreen(Interface anInterface, ScoreManager scoreManager, GamePanel gamePanel) {
        this.anInterface = anInterface;
        this.scoreManager = scoreManager;
        this.gamePanel = gamePanel;
        input = "";
        inputColor = Color.WHITE;
        submitColor = Color.WHITE;
        promptColor = Color.WHITE;
        canWrite = false;
        promptClicked = false;
    }

    public void reloadString(String c, String opt) {
        if (canWrite) {
            if (opt.equals("a")) {

                input = input + c;

            } else if (opt.equals("r")) {
                if (!input.equals("")) {
                    input = input.substring(0, input.length() - 1);
                }
            } else {
                System.out.println("Option not valid");
            }
        }
    }

    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (GamePanel.gameState == STATE.DEATH) {
            if (promptClicked) {
                if (mouseOver(mx, my, buttonX, buttonY, 50, 45)) {
                    submitColor = Color.GRAY;
                    nameAvailable = scoreManager.saveScore(anInterface.getScore(), input);
                }

            } else {
                if (mouseOver(mx, my, promptX, promptY, 194, 28)) {
                    promptColor = Color.RED;
                }
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (GamePanel.gameState == STATE.DEATH) {
            int mx = e.getX();
            int my = e.getY();

            if (promptClicked) {
                if (mouseOver(mx, my, inputX, inputY, 300, 45)) {
                    inputColor = Color.red.darker();
                    canWrite = true;
                } else if (mouseOver(mx, my, buttonX, buttonY, 50, 45)) {
                    submitColor = Color.WHITE;
                    if (nameAvailable) {
                        inputColor = Color.WHITE;
                        scoreManager.saveScore(anInterface.getScore(), input);
                        leaderboard = scoreManager.getLeaderboard(10);
                        input = "";
                    }
                } else {
                    inputColor = Color.WHITE;
                }

            } else {

                if (mouseOver(mx, my, promptX, promptY, 194, 28)) {
                    promptClicked = true;
                }
            }

        }

    }

    private int x = 0;

    public void render(Graphics g) {
        if (x == 0) {
            r = scoreManager.newHighest(anInterface.getScore());
            x++;
        }


        // Painting background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT);


        if (r) {
            buttonX = 648;
            buttonY = 232;

            promptX = 412;
            promptY = 241;

            inputX = 338;
            inputY = 232;

            congratulations(g);
        } else {
            buttonX = 648;
            buttonY = 170;

            promptX = 412;
            promptY = 181;

            inputX = 338;
            inputY = 170;

            death(g);
        }


    }

    private void death(Graphics g) {
        int y = 100;

        Graphics2D g2d = (Graphics2D) g;
        float thickness = 2;
        g2d.setStroke(new BasicStroke(thickness));
        FontRenderContext frc = g2d.getFontRenderContext();


        // Death message
        String mess = "YOU DIED";
        int pos = ox + ((buttonWidth - (int) fnt.getStringBounds(mess, frc).getWidth()) / 2);

        g.setColor(Color.red);
        g.setFont(fnt);
        g.drawString(mess, pos, y);

        // Rest
        String text = "Total Score: " + anInterface.getScore();

        int pos2 = ox + ((buttonWidth - (int) fnt2.getStringBounds(text, frc).getWidth()) / 2);


        if (promptClicked) {
            renderInput(g, fnt3, pos - 25, y + 70);
        } else {
            renderPrompt(g, fnt3, pos2, frc, y + 103);
        }


        renderTotalScore(g, pos2, y, text);


        // scoreboard
        renderLeaderboard(g, fnt, frc, y);

    }

    private void congratulations(Graphics g) {
        int y = 100;
        Graphics2D g2d = (Graphics2D) g;
        FontRenderContext frc = g2d.getFontRenderContext();

        String mess = "YOU DIED";
        int pos = ox + ((buttonWidth - (int) fnt.getStringBounds(mess, frc).getWidth()) / 2);

        g.setColor(Color.red);
        g.setFont(fnt);
        g.drawString(mess, pos, y);
        g.setColor(Color.GREEN.darker());
        g.setFont(fnt4);
        mess = "But you beat the highest score!!!";
        g.drawString(mess, ox + ((buttonWidth - (int) fnt4.getStringBounds(mess, frc).getWidth()) / 2), y + 65);

        String text = "Total Score: " + anInterface.getScore();

        int pos2 = ox + ((buttonWidth - (int) fnt2.getStringBounds(text, frc).getWidth()) / 2);


        if (promptClicked) {
            renderInput(g, fnt3, pos - 25, y + 132);

        } else {
            renderPrompt(g, fnt3, pos2, frc, y + 163);
        }

        renderTotalScore(g, pos2, y + 70, text);

        renderLeaderboard(g, fnt, frc, y + 62);

    }

    private void renderTotalScore(Graphics g, int pos, int y, String text) {
        // Total score
        g.setColor(Color.white);
        g.setFont(fnt2);
        g.drawString(text, pos, y + 50);
    }

    private void renderPrompt(Graphics g, Font fnt, int pos, FontRenderContext frc, int y) {
        g.setColor(promptColor);
        g.setFont(fnt);
        g.drawString("Save your score", pos, y);
    }

    private void renderInput(Graphics g, Font fnt, int pos, int y) {
        g.setColor(new Color(1f, 1f, 1f, 0.9f));
        g.fillRect(pos, y, 300, 45);
        g.setColor(inputColor);

        g.drawRect(pos, y, 300, 45);
        g.setColor(Color.BLACK);
        g.setFont(fnt);
        g.drawString(input, pos + 10, y + 33);

        // Submit button
        g.setColor(submitColor);
        g.fillRect(pos + 310, y, 50, 45);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.BLACK);
        g.drawString("Add", pos + 315, y + 30);
    }

    private void renderLeaderboard(Graphics g, Font fnt, FontRenderContext frc, int y) {
        leaderboard = scoreManager.getLeaderboard(8);
        y += 180;
        for (String line : leaderboard.split("\n")) {
            g.drawString(line, ox + ((buttonWidth - (int) fnt.getStringBounds("YOU DIED", frc).getWidth()) / 2), y);
            y = y + 50;
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
