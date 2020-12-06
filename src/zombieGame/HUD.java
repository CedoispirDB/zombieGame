package zombieGame;


import java.awt.*;

public class HUD {

    public static float HEALTH = 100;
    private float greenValue = 102;

    private int score;
    private int level = 1;
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

//        g.setColor(Color.white);
//        g.drawRect(15, 15, 200, 32);
//        g.drawString("Score: " + score, 15, 64);
//        g.drawString("Level: " + level, 15, 80);

        // Ammo
        g.setColor(Color.white.brighter());
        g.drawString("Ammo: " + game.ammo, 16, 60);


    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
