package Enemies;

import Levels.LevelManager;
import Manager.EnemyObject;
import Manager.GameObject;
import Manager.Handler;
import Manager.ID;
import Map.Node;
import Player.Player;
import Player.Interface;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class BasicZombie extends EnemyObject {

    private LevelManager levelManager;
    private Node[][] grid;
    private Node currentNode;
    private LinkedList<Node> neighbors;
    private LinkedList<Node> possiblePath;
    private LinkedList<Node> closedSet;
    private String currentType;
    private int nextX, nextY;
    private int disX, disY;
    private Random r;
    private boolean move = true;
    private int count = 0;
    private GameObject player;
    private Handler handler;
    private Interface anInterface;


    public BasicZombie(double posX, double posY, double velX, double velY, Handler handler, ID id, LevelManager levelManager, Interface anInterface) {
        super(posX, posY, velX, velY, handler, id);

        this.anInterface = anInterface;
        this.handler = handler;
        enemyHealth = 100;
        grid = levelManager.getGrid();
        currentNode = grid[(int) (posX / 32)][(int) (posY / 32)];

        r = new Random();

        possiblePath = new LinkedList<>();
        closedSet = new LinkedList<>();

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject temp = handler.object.get(i);
            if (temp.getId() == ID.Player) {
                player = temp;
            }
        }



//        System.out.println("Current Node: " + currentNode);
//        System.out.println("Wall Node: " + grid[(384 + 96) / 32][224 / 32] + "\n");
    }

    public void tick() {
        movement();

//        System.out.println(enemyHealth);

        if (enemyHealth < 0) {
            anInterface.increaseScore(10);
            handler.removeEnemy(this);
        }


    }

    private void movement() {
        boolean comp = true;
        int tempR;
        int index;

        if (move) {

            neighbors = currentNode.getNeighbors();

            for (int i = 0; i < neighbors.size(); i++) {
                Node tempNode = neighbors.get(i);
                currentType = tempNode.getType();

                if (closedSet.size() > 0) {

                    comp = closedSet.get(closedSet.size() - 1) != tempNode;

                }


                if (!currentType.equals("w") && comp) {
                    possiblePath.add(tempNode);
                }
            }


            tempR = possiblePath.size() - 1;

            if (tempR <= 0) {
                index = 0;
            } else {
                index = r.nextInt(tempR);
            }

            Node next = possiblePath.get(index);


            nextX = next.getX();
            nextY = next.getY();

            closedSet.add(currentNode);

            currentNode = next;
            possiblePath.clear();
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

        count++;


        if (count == 32) {

            move = true;
            count = 0;
        }
    }

    public void render(Graphics g) {
        g.setColor(new Color(29, 105, 4));

        g.fillRect((int) posX, (int) posY, 32, 32);

    }

    public int getEnemyHealth() {
        return enemyHealth;
    }

    public void setEnemyHealth(int enemyHealth) {
        this.enemyHealth = enemyHealth;
    }


    public Node getNode() {
        return currentNode;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) posX, (int) posY, 32, 32);
    }

    public Rectangle getBoundsX() {
        return null;
    }

    public Rectangle getBoundsY() {
        return null;
    }
}
