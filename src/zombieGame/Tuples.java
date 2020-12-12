package zombieGame;

import java.util.LinkedList;

public class Tuples {

    public static LinkedList<Float> xValues = new LinkedList<>();
    public static LinkedList<Float> yValues = new LinkedList<>();

    public Tuples() {

    }

    public void print() {
        xValues.forEach(System.out :: println);
        yValues.forEach(System.out :: println);
    }

    public void addTuple(float x, float y) {
        xValues.add(x);
        yValues.add(y);
    }

    public boolean compareTuple(float x, float y) {
        return !xValues.contains(x) || !yValues.contains(y);
    }

    public float getX(int x) {
        return xValues.get(x);


    }

    public float getY(int y) {
        return yValues.get(y);

    }

    public void clearTuples() {
//        xValues.clear();
//        yValues.clear();
    }

    public void removeX(int i) {
        xValues.remove(i);
    }

    public void removeY(int i) {
        yValues.remove(i);

    }

}
