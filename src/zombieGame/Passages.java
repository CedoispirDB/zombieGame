package zombieGame;

import java.awt.Rectangle;

public class Passages {

    private final Map map;
    private final Game game;
    private final Handler handler;
    private HUD hud;


    public Passages(Game game, Map map, Handler handler, HUD hud) {
        this.map = map;
        this.game = game;
        this.handler = handler;
        this.hud = hud;
    }

    public void check() {
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ID.Player) {
                if (game.gameState == Game.STATE.Castle) {
                    if (tempObject.getBounds().intersects(this.getBounds())) {
                        game.gameState = Game.STATE.Garden;
                        handler.clear();
                        handler.addObject(new DemonKing(game, 972, 50, ID.Player, handler, hud, map));
                        handler.addObject(new Poppy(game, 1004, 50, ID.Poppy, handler, hud, map));
                    }
                }
            }
        }
    }


    public Rectangle getBounds() {
        return new Rectangle((Game.WIDTH / 2) - 38, (int) map.castleHeight + 30, 148, 20);
    }
}
