/*
 * @test
 * @summary ThreadLocalRandom.ints(origin, bound) range, count and argument checks
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testRange(0, 10, 2000);
        testRange(-5, 5, 2000);
        testRange(Integer.MIN_VALUE, Integer.MAX_VALUE, 2000);
        testSingleValueRange();
        testCount();
        testBadRange(3, 3);
        testBadRange(5, 2);
        testBadRange(Integer.MAX_VALUE, Integer.MIN_VALUE);
    }

    static void testRange(int origin, int bound, int n) {
        int[] values = ThreadLocalRandom.current().ints(origin, bound).limit(n).toArray();
        if (values.length != n) {
            throw new RuntimeException("Expected " + n + " values but got " + values.length);
        }
        for (int v : values) {
            if (v < origin || v >= bound) {
                throw new RuntimeException("Value " + v + " outside [" + origin + ", " + bound + ")");
            }
        }
    }

    static void testSingleValueRange() {
        int[] values = ThreadLocalRandom.current().ints(5, 6).limit(50).toArray();
        for (int v : values) {
            if (v != 5) {
                throw new RuntimeException("Expected only 5 for ints(5,6) but got " + v);
            }
        }
    }

    static void testCount() {
        long count = ThreadLocalRandom.current().ints(0, 10).limit(123).count();
        if (count != 123L) {
            throw new RuntimeException("Expected count 123 but got " + count);
        }
    }

    static void testBadRange(int origin, int bound) {
        try {
            IntStream s = ThreadLocalRandom.current().ints(origin, bound);
            throw new RuntimeException("Expected IllegalArgumentException for origin=" + origin + ", bound=" + bound);
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }
}
