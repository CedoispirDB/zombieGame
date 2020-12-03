package utils;

import java.sql.Timestamp;

public class printTools {

    public static void d(Object x) {
        System.out.println(new Timestamp(System.currentTimeMillis()) + " : " + x);
    }
}
