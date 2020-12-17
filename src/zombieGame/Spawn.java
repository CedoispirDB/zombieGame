package zombieGame;

public class Spawn {
    private final Handler handler;
    private final HUD hud;
    private SpriteSheet ss;
    private Game game;

    public Spawn(Handler handler, HUD hud, Game game, SpriteSheet ss) {
        this.handler = handler;
        this.hud = hud;
        this.game = game;
        this.ss = ss;
    }


    public void tick() {

    }

    public void spawnEnemies() {
//        handler.object.add(new Minion(game, 500, 500, ID.Minion, handler, hud, ss));

    }



}



