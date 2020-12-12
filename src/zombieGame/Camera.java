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
//        x += ((object.getX() - x));
//        y += ((object.getY() - y));

//        System.out.println("x: " + x);
//        System.out.println("w: " + Game.WIDTH);

        if (x <= 0) {
            x = 0;
        }
        //1277
//        System.out.println("w: " + Game.WIDTH);
//        System.out.println("x: " + object.getX());
        if (object.getX() == 1352) {
//            System.out.println("S");
        }

        if (y <= 0) {
            y = 0;
        }
        if (y >= Game.HEIGHT) {
            y = 0;
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
