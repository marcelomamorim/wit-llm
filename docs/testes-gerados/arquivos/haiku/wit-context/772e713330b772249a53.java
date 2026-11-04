/*
 * @test
 * @summary Witup-derived checks for ThreadLocalRandom.ints(int,int) bounds validation
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        expectIllegalArgument(5, 5);
        expectIllegalArgument(10, 3);
        expectIllegalArgument(Integer.MIN_VALUE, Integer.MIN_VALUE);
        checkRange(1, 10, 200);
        checkRange(-5, 5, 200);
        checkRange(0, 1, 50);
    }

    private static void expectIllegalArgument(int origin, int bound) {
        try {
            ThreadLocalRandom.current().ints(origin, bound);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new AssertionError();
    }

    private static void checkRange(int origin, int bound, int count) {
        IntStream stream = ThreadLocalRandom.current().ints(origin, bound).limit(count);
        int[] values = stream.toArray();
        if (values.length != count) {
            throw new AssertionError();
        }
        for (int v : values) {
            if (v < origin || v >= bound) {
                throw new AssertionError();
            }
        }
    }
}
