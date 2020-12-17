package zombieGame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseInput extends MouseAdapter {

    private final Handler handler;
    private GameObject player;
    protected SpriteSheet bss;
    private final Camera camera;
    private final Game game;
    private Map map;

    public MouseInput(Game game, Handler handler, Camera camera, SpriteSheet bss, Map map) {
        this.handler = handler;
        this.camera = camera;
        this.game = game;
        this.bss = bss;
        this.map = map;
    }

    public void mousePressed(MouseEvent e) {

        int mx = (int) (e.getX() + camera.getX());
        int my = (int) (e.getY() + camera.getY());

        for (int i = 0; i < handler.object.size(); i++) {
            if (handler.object.get(i).getId() == ID.Player) {
                player = handler.object.get(i);

                if (Game.mana >= 1) {
                    handler.addBullet(new Spell(player.getX() + 16, player.getY() + 24, ID.Spell, handler, mx, my, bss, map));

                    Game.mana--;
                    HUD.manaSize -= 1.3;
                }
            }
        }

    }

    public void mx(MouseEvent e){
        System.out.println(e.getX());
    }



}
