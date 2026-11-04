/*
 * @test
 * @summary Verifies that SplittableRandom.longs() produces the same sequence as nextLong() and shares state with the generator
 * @run main SplittableRandomWitupTest
 */

import java.util.SplittableRandom;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testMatchesNextLongSequence();
        testLimitZeroIsEmpty();
        testCountWithLimit();
        testStreamAdvancesGeneratorState();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testMatchesNextLongSequence() {
        long seed = 42L;
        SplittableRandom streamSource = new SplittableRandom(seed);
        SplittableRandom reference = new SplittableRandom(seed);
        long[] got = streamSource.longs().limit(8).toArray();
        check(got.length == 8, "expected 8 elements but got " + got.length);
        for (int i = 0; i < got.length; i++) {
            long expected = reference.nextLong();
            check(got[i] == expected,
                  "mismatch at index " + i + ": expected " + expected + " but got " + got[i]);
        }
    }

    static void testLimitZeroIsEmpty() {
        SplittableRandom r = new SplittableRandom(7L);
        long count = r.longs().limit(0).count();
        check(count == 0L, "expected 0 but got " + count);
    }

    static void testCountWithLimit() {
        SplittableRandom r = new SplittableRandom(7L);
        long count = r.longs().limit(100).count();
        check(count == 100L, "expected 100 but got " + count);
    }

    static void testStreamAdvancesGeneratorState() {
        long seed = 99L;
        SplittableRandom withStream = new SplittableRandom(seed);
        SplittableRandom reference = new SplittableRandom(seed);
        withStream.longs().limit(3).toArray();
        for (int i = 0; i < 3; i++) {
            reference.nextLong();
        }
        long expected = reference.nextLong();
        long actual = withStream.nextLong();
        check(actual == expected,
              "generator state not advanced by stream: expected " + expected + " but got " + actual);
    }
}
