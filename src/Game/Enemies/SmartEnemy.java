package Game.Enemies;

import Game.Levels.LevelManager;
import Game.Manager.EnemyObject;
import Game.Manager.GameObject;
import Game.Manager.Handler;
import Game.Manager.ID;
import Game.Map.Node;

import java.awt.*;
import java.util.LinkedList;

public class SmartEnemy extends EnemyObject {

    private LevelManager levelManager;
    private LinkedList<Node> openSet = new LinkedList<>();
    private LinkedList<Node> closedSet = new LinkedList<>();
    private LinkedList<Node> path = new LinkedList<>();
    private LinkedList<Node> walls = new LinkedList<>();
    private Node start, end;
    private Node current;
    private boolean found = false;
    private int count = 0;
    private boolean move = true;
    private int nextX = 0;
    private int nextY = 0;
    private Node[][] grid;
    private Handler handler;
    private int i;
    private boolean run = true;
    private GameObject player;
    private int playerX, playerY;

    public SmartEnemy(double posX, double posY, double velX, double velY, Handler handler, ID id, LevelManager levelManager) {
        super(posX, posY, velX, velY, handler, id);

        this.levelManager = levelManager;
        this.handler = handler;

        playerX = -1;
        playerY = -1;
        grid = levelManager.getGrid();

        start = grid[(int) (posX / 32)][(int) (posY / 32)];

        openSet.add(start);

    }

    int x = 0;

    public void tick() {

        for (int j = 0; j < handler.object.size(); j++) {
            GameObject temp = handler.object.get(j);
            if (temp.getId() == ID.PLAYER) {
                player = temp;
            }
        }
        if (player != null) {
            int pX = (int) player.getPosX();
            int pY = (int) player.getPosY();
//            System.out.println("pX: " + pX + " playerX: " + playerX);
            if (run) {
                if (!found) {
                    end = grid[pX / 32][pY / 32];

                    pathFinding();
                } else {
                    if (x == 0) {
                        i = path.size() - 1;
                        path.forEach(System.out::println);
                        x++;
                    }


                    if (move) {
                        if (i >= 0) {
                            Node temp = path.get(i);
                            nextX = temp.getX();
                            nextY = temp.getY();
                            move = false;
                        } else {
//                        run = false;
                        }
                        System.out.println("\nnextX: " + nextX);
                        System.out.println("nextY: " + nextY);

                    }


                    if (posX > nextX) {
                        posX -= 1;

                    }

                    if (posX < nextX) {
                        posX += 1;

                    }

                    if (posY > nextY) {
                        posY -= 1;

                    }
                    if (posY < nextY) {
                        posY += 1;
                    }

                    count++;


                    if (count == 32) {
                        count = 0;
                        move = true;
                        i--;
                    }

                }
            }
        }
    }

    private void pathFinding(){
        if (openSet.size() > 0) {
            // We can keep going
            int winner = 0;

            for (int i = 0; i < openSet.size(); i++) {
                if (openSet.get(i).getF() < openSet.get(winner).getF()) {
                    winner = i;
                }
            }

            current = openSet.get(winner);

            if (current == end) {

                // find the path
                Node temp = current;
                path.add(temp);

                while (temp.getPrevious() != null) {
                    path.add(temp.getPrevious());
                    temp = temp.getPrevious();
                }


                System.out.println("Done!");
                found = true;


            }

            closedSet.add(current);
            openSet.remove(current);

            LinkedList<Node> neighbors = current.getNeighbors();

            for (int i = 0; i < neighbors.size(); i++) {
                Node neighbor = neighbors.get(i);
//                System.out.println("neighbor of " + current + ": " + neighbor);
                // Added !neighbor.isWall()
                if (!closedSet.contains(neighbor) && !neighbor.getType().equals("w")) {

                    int tempG = neighbor.getG() + 1;

                    if (openSet.contains(neighbor)) {
                        if (tempG < neighbor.getG()) {
                            neighbor.setG(tempG);
                        }
                    } else {
//                        System.out.println("setting g to:" + tempG);
                        neighbor.setG(tempG);
                        openSet.add(neighbor);
                    }

                    neighbor.setH(heuristic(neighbor, end));
                    neighbor.setF(neighbor.getG() + neighbor.getH());
                    neighbor.setPrevious(current);
                }
            }

        } else {
            // No solution
        }
    }

    private int heuristic(Node a, Node b) {
        int x = Math.abs(a.getX() - b.getX());
        int y = Math.abs(a.getY() - b.getY());
        return (int) Math.sqrt(x * x + y * y);
    }


    public void render(Graphics g) {
        for (int i = 0; i < path.size(); i++) {
            path.get(i).render(g, Color.blue);
        }
        g.setColor(Color.red.darker());
        g.fillRect((int) posX, (int) posY, 32, 32);

//        for (int i = 0; i < openSet.size(); i++) {
//            openSet.get(i).render(g, Color.green);
//        }
//
//        for (int i = 0; i < closedSet.size(); i++) {
//            closedSet.get(i).render(g, Color.red);
//        }

//

    }

    public Rectangle getBounds() {
        return null;
    }

    public Node getNode() {
        return null;
    }

    public Rectangle getBoundsX() {
        return null;
    }

    public Rectangle getBoundsY() {
        return null;
    }
}
