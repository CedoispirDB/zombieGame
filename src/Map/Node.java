package Map;

import java.awt.*;
import java.util.LinkedList;

public class Node {

    /* type can be:
        w: wall (can't pass)
        n: none (can pass)

        The type represents what object is located in the same position
    */
    private String type;
    private int x, y, w, h, i, j, rows, cols, f, g;
    private LinkedList<Node> neighbors = new LinkedList<>();
    private Node previous;


    public Node(int x, int y, int w, int h, int i, int j, int rows, int cols, String type) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.i = i;
        this.j = j;
        this.rows = rows;
        this.cols = cols;
        this.type = type;
    }

    public void addNeighbors(Node[][] grid) {


        if (i < cols - 1) {
            neighbors.add(grid[i + 1][j]);
        }
        if (i > 0) {
            neighbors.add(grid[i - 1][j]);
        }
        if (j < rows - 1) {
            neighbors.add(grid[i][j + 1]);
        }
        if (j > 0) {
            neighbors.add(grid[i][j - 1]);
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    public void render(Graphics g, Color color) {
        g.setColor(color);
        g.fillRect(x, y, 32 ,32);
    }

    public LinkedList<Node> getNeighbors() {
        return neighbors;
    }


    @Override
    public String toString() {
        return "Node{" +
                "type='" + type + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", w=" + w +
                ", h=" + h +
                ", rows=" + rows +
                ", cols=" + cols +
                '}';
    }
}
