package zombieGame;

public class Camera {

    private float x;
    private float y;
    private GameObject player;
    private Handler handler;

    public Camera(float x, float y, Handler handler) {
        this.x = x;
        this.y = y;
        this.handler = handler;
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ID.Player) {
                player = tempObject;
            }
        }
    }

    public void tick(GameObject object) {
        x = object.getX() - 700;
        y = object.getY() - 400;

//        System.out.println("x: " + x);
//        System.out.println("w: " + Game.WIDTH);
//        System.out.println("y: " + y);
//        System.out.println("h: " + Game.HEIGHT);

        if (x <= 0) {
            x = 0;
        }
        if (x + Game.WIDTH >= 64 * 32) {
            x = (64 * 32) - Game.WIDTH;
        }

        if (y <= 0) {
            y = 0;
        }
        if (y + Game.HEIGHT >= 40.5 * 32) {
            y = (float)(40.5 * 32) - Game.HEIGHT;
        }
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }


}
