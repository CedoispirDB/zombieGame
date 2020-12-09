package zombieGame;


import java.awt.*;

public class HUD {

    public static float HEALTH = 100;
    private float greenValue = 102;

    private Color manaColor = new Color(0, 255, 255);

    private int score;
    Game game;

    public HUD(Game game) {
        this.game = game;
    }

    public void tick() {
        HEALTH = Game.clamp(HEALTH, 0, 100);
        greenValue = Game.clamp(greenValue, 0, 255);

        greenValue = HEALTH * 2;

        score = getScore();

    }

    public void render(Graphics g) {
        // Health bar
        g.setColor(Color.gray);
        g.fillRect(15, 15, 200, 32);
        g.setColor(new Color(75, (int)greenValue, 0).darker());
        g.fillRect(15, 15, (int) (HEALTH * 2), 32);
        g.setColor(Color.black);
        g.drawRect(15, 15, 200, 32);

        // Mana
        g.setColor(Color.gray);
        g.fillRect(15, 50, 200, 16);
        g.setColor(manaColor);
        g.fillRect(15, 50,  game.ammo + 100, 16);
        g.setColor(Color.black);
        g.drawRect(15, 50, 200, 16);



    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

}
