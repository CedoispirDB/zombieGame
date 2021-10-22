package Enemies;

import Levels.LevelManager;
import Manager.GameObject;
import Manager.Handler;
import Manager.ID;
import Map.Node;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class BasicZombie extends GameObject {

    private LevelManager levelManager;
    private Node[][] grid;
    private Node currentNode;
    private LinkedList<Node> neighbors;
    private LinkedList<Node> possiblePath;
    private LinkedList<Node> closedSet;
    private String currentType;
    private int nextX, nextY;
    private  int disX, disY;
    private Random r;
    private boolean move = true;
    private int count = 0;


    public BasicZombie(double posX, double posY, double velX, double velY, Handler handler, ID id, LevelManager levelManager) {
        super(posX, posY, velX, velY, handler, id);

        grid = levelManager.getGrid();
        currentNode = grid[(int) (posX / 32)][(int) (posY/32)];

        r = new Random();

        possiblePath = new LinkedList<>();
        closedSet = new LinkedList<>();


        System.out.println("Current Node: " + currentNode);
        System.out.println("Wall Node: " + grid[(384 + 96) / 32][224 / 32] + "\n");
    }

    public void tick() {
        boolean comp = true;
        if (move) {

            neighbors = currentNode.getNeighbors();

            for (int i = 0; i < neighbors.size(); i++) {
                Node tempNode = neighbors.get(i);
                currentType = tempNode.getType();
                if (closedSet.size() > 0) {
                    System.out.println("closedSet: "  + closedSet.get(closedSet.size() - 1) + ", tempNode: " + tempNode);
                    comp = closedSet.get(closedSet.size() - 1) != tempNode;
                }
                System.out.println(comp);
                if (!currentType.equals("w") && comp) {
//                    System.out.println("Possible Node:" + tempNode);
                    possiblePath.add(tempNode);
                }
            }

            System.out.println("Possible paths");
            possiblePath.forEach(System.out :: println);
            Node next = possiblePath.get(r.nextInt(possiblePath.size() - 1));
//            Node next = possiblePath.get(1);


            System.out.println("Next node: " + next + "\n");

            nextX = next.getX();
            nextY = next.getY();

            currentNode = next;
            possiblePath.clear();
            closedSet.add(next);
            move = false;
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

        count ++;

        if (count == 32) {
            move = true;
            count = 0;
        }

    }

    public void render(Graphics g) {
        g.setColor(new Color(29, 105, 4));

        g.fillRect((int)posX, (int)posY, 32, 32);

    }

    public Rectangle getBounds() {
        return null;
    }

    public Rectangle getBoundsX() {
        return null;
    }

    public Rectangle getBoundsY() {
        return null;
    }
}
