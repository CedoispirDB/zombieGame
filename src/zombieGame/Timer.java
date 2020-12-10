package zombieGame;

public class Timer {
    public boolean timer(int l) {
        boolean leave = false;
        long start = System.currentTimeMillis() / 1000;
        long end = 0;
        while (!(end - start == l)) {
            end = System.currentTimeMillis() / 1000;
            leave = true;
        }
        return leave;
    }

}
