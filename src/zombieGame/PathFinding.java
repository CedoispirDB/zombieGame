package zombieGame;

public class PathFinding {

    private Handler handler;
    private GameObject player;
    private GameObject enemy;

    public PathFinding(Handler handler) {
        this.handler = handler;

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ID.Player) {
                player = tempObject;
            }
        }

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ID.Minion) {
                enemy = tempObject;
            }
        }
    }

    public int evade(){
        return 0;
    }

    public boolean checkSurrounds(int x, int y) {
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ID.Block) {
               if (tempObject.getBounds().intersects(enemy.getBounds())){
                   System.out.println();
                   return true;
               }
            }
        }
        return false;
    }


}
