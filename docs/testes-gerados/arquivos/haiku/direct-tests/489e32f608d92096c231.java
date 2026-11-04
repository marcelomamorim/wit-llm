/*
 * @test
 * @summary Witup-style tests for SplittableRandom.nextInt(int origin, int bound)
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testSingleValueRange();
        testValuesWithinRange();
        testDeterministicWithSameSeed();
        testOriginEqualsBoundThrows();
        testOriginGreaterThanBoundThrows();
        testFullIntRange();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSingleValueRange() {
        SplittableRandom rnd = new SplittableRandom(42L);
        for (int i = 0; i < 100; i++) {
            int v = rnd.nextInt(0, 1);
            check(v == 0, "nextInt(0,1) must return 0, got " + v);
        }
        for (int i = 0; i < 100; i++) {
            int v = rnd.nextInt(-7, -6);
            check(v == -7, "nextInt(-7,-6) must return -7, got " + v);
        }
    }

    static void testValuesWithinRange() {
        SplittableRandom rnd = new SplittableRandom(12345L);
        for (int i = 0; i < 10000; i++) {
            int v = rnd.nextInt(5, 10);
            check(v >= 5 && v < 10, "value out of [5,10): " + v);
        }
        for (int i = 0; i < 10000; i++) {
            int v = rnd.nextInt(-100, 100);
            check(v >= -100 && v < 100, "value out of [-100,100): " + v);
        }
    }

    static void testDeterministicWithSameSeed() {
        SplittableRandom a = new SplittableRandom(987654321L);
        SplittableRandom b = new SplittableRandom(987654321L);
        for (int i = 0; i < 1000; i++) {
            int va = a.nextInt(0, 1000);
            int vb = b.nextInt(0, 1000);
            check(va == vb, "same seed must produce same sequence at index " + i);
        }
    }

    static void testOriginEqualsBoundThrows() {
        SplittableRandom rnd = new SplittableRandom(1L);
        boolean thrown = false;
        try {
            rnd.nextInt(5, 5);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "nextInt(5,5) must throw IllegalArgumentException");
    }

    static void testOriginGreaterThanBoundThrows() {
        SplittableRandom rnd = new SplittableRandom(2L);
        boolean thrown = false;
        try {
            rnd.nextInt(10, 3);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        check(thrown, "nextInt(10,3) must throw IllegalArgumentException");
    }

    static void testFullIntRange() {
        SplittableRandom rnd = new SplittableRandom(777L);
        for (int i = 0; i < 10000; i++) {
            int v = rnd.nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
            check(v >= Integer.MIN_VALUE && v < Integer.MAX_VALUE,
                  "value out of full int range: " + v);
        }
    }
}
