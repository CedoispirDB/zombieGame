package Enemies;

import Levels.LevelManager;
import Manager.EnemyObject;
import Manager.GameObject;
import Manager.Handler;
import Manager.ID;
import Map.Node;
import Player.Player;
import Player.Interface;
import Render.Animation;
import Render.CreateImages;
import Render.ImageManager;

import java.awt.*;
import java.awt.image.BufferedImage;
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
    private Handler handler;
    private Interface anInterface;
    private final ImageManager imageManager;
    private BufferedImage image;
    private LinkedList<BufferedImage> right, left, yDir;
    private Animation animation;
    private final int speed;


    public BasicZombie(double posX, double posY, double velX, double velY, Handler handler, ID id, LevelManager levelManager, Interface anInterface, ImageManager imageManager) {
        super(posX, posY, velX, velY, handler, id);

        this.anInterface = anInterface;
        this.handler = handler;
        this.levelManager = levelManager;
        this.imageManager = imageManager;

        enemyHealth = 50;
        grid = levelManager.getGrid();
        currentNode = grid[(int) (posX / 32)][(int) (posY / 32)];

        r = new Random();

        possiblePath = new LinkedList<>();
        closedSet = new LinkedList<>();

        animation = new Animation();
        image = imageManager.getSprite("bz");
        CreateImages createImages = new CreateImages();

        right = createImages.createFrames(image, 0, 3, 1);
        left = createImages.createFrames(image, 1, 3, 2);
        yDir = createImages.createFrames(image, 2, 3, 3);
        animation.init(3);
        animation.setFrames(right);

        speed = 1;

//        System.out.println("Current Node: " + currentNode);
//        System.out.println("Wall Node: " + grid[(384 + 96) / 32][224 / 32] + "\n");
    }

    public void tick() {
//        System.out.println("enemy Health: " + enemyHealth);
        if (levelManager.hasNeighbors()) {
            movement();
        }

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
            posX -= speed;
            animation.setFrames(left, nextX);

        }
        if (posX < nextX) {
            posX += speed;
            animation.setFrames(right, nextX);

        }

        if (posY > nextY) {
            posY -= speed;
            animation.setFrames(yDir, nextY);

        }
        if (posY < nextY) {
            posY += speed;
            animation.setFrames(yDir, nextY);
        }

        count++;


        if (count == 32) {

            move = true;
            count = 0;
        }
    }

    public void render(Graphics g) {

//        g.drawImage(image,(int) posX, (int) posY,null );
        animation.runAnimation();
        animation.renderAnimation(g, (int) posX, (int) posY);
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
