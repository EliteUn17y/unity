package me.eliteun17y.unity.util.math;

import java.util.Random;

public class RandomUtil {
    private static final Random random = new Random();

    public static int randomInt(int min, int max) {
        return min + random.nextInt((max - min) + 1);
    }
}
