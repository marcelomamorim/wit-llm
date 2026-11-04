/*
 * @test
 * @summary Verify SplittableRandom.ints() returns a deterministic, unbounded int stream
 * @run main SplittableRandomWitupTest
 */

import java.util.Arrays;
import java.util.SplittableRandom;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testSameSeedProducesSameStream();
        testDifferentSeedsProduceDifferentStreams();
        testLimitControlsLength();
        testLimitZeroIsEmpty();
        testStreamIsNotNull();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSameSeedProducesSameStream() {
        SplittableRandom a = new SplittableRandom(42L);
        SplittableRandom b = new SplittableRandom(42L);
        int[] first = a.ints().limit(100).toArray();
        int[] second = b.ints().limit(100).toArray();
        check(Arrays.equals(first, second),
              "Same seed must produce identical ints() streams");
    }

    static void testDifferentSeedsProduceDifferentStreams() {
        SplittableRandom a = new SplittableRandom(1L);
        SplittableRandom b = new SplittableRandom(2L);
        int[] first = a.ints().limit(16).toArray();
        int[] second = b.ints().limit(16).toArray();
        check(!Arrays.equals(first, second),
              "Different seeds should produce different ints() streams");
    }

    static void testLimitControlsLength() {
        SplittableRandom r = new SplittableRandom(7L);
        int[] values = r.ints().limit(25).toArray();
        check(values.length == 25,
              "limit(25) must yield exactly 25 elements, got " + values.length);
    }

    static void testLimitZeroIsEmpty() {
        SplittableRandom r = new SplittableRandom(7L);
        int[] values = r.ints().limit(0).toArray();
        check(values.length == 0,
              "limit(0) must yield no elements, got " + values.length);
    }

    static void testStreamIsNotNull() {
        SplittableRandom r = new SplittableRandom(0L);
        check(r.ints() != null, "ints() must return a non-null IntStream");
    }
}
