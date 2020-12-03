package zombieGame;

public class Camera {

    private float x;
    private float y;

    public Camera(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void tick(GameObject object) {
        x = object.getX() - 600;
        y = object.getY() - 600;
//        x += ((object.getX() - x));
//        y += ((object.getY() - y));

        if (x <= 0) {
            x = 0;
        }
        if (x >= Game.WIDTH) {
            x = Game.WIDTH;
        }

        if (y <= 0) {
            y = 0;
        }
        if (y >= Game.HEIGHT) {
            y = Game.HEIGHT;
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
