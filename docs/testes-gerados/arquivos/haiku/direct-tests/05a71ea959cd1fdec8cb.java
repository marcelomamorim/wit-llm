/*
 * @test
 * @summary Verifies the public Random.ints() unbounded stream: determinism for a fixed seed, laziness with limit, and empty prefix behavior.
 * @run main RandomWitupTest
 */

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class RandomWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSameSeedProducesSameStream() {
        Random r1 = new Random(42L);
        Random r2 = new Random(42L);
        int[] a = r1.ints().limit(200).toArray();
        int[] b = r2.ints().limit(200).toArray();
        check(a.length == 200, "expected 200 elements, got " + a.length);
        check(Arrays.equals(a, b), "same seed must yield identical ints() sequences");
    }

    static void testStreamIsUnboundedAndLimitedExactly() {
        Random r = new Random(7L);
        long count = r.ints().limit(1000).count();
        check(count == 1000L, "limit(1000) must yield exactly 1000 elements, got " + count);
    }

    static void testLimitZeroIsEmpty() {
        Random r = new Random(1L);
        long count = r.ints().limit(0).count();
        check(count == 0L, "limit(0) must yield no elements, got " + count);
    }

    static void testDifferentSeedsDiffer() {
        int[] a = new Random(1L).ints().limit(64).toArray();
        int[] b = new Random(2L).ints().limit(64).toArray();
        check(!Arrays.equals(a, b), "different seeds should produce different ints() sequences");
    }

    static void testConsecutiveCallsOnSameInstanceContinueState() {
        Random r = new Random(99L);
        int[] first = r.ints().limit(10).toArray();
        int[] second = r.ints().limit(10).toArray();
        check(!Arrays.equals(first, second), "second ints() stream on same instance should continue the sequence");
    }

    static void testStreamNotAllEqual() {
        int[] values = new Random(123L).ints().limit(100).toArray();
        IntStream distinct = Arrays.stream(values).distinct();
        long distinctCount = distinct.count();
        check(distinctCount > 1L, "ints() stream should not be constant");
    }

    public static void main(String[] args) {
        testSameSeedProducesSameStream();
        testStreamIsUnboundedAndLimitedExactly();
        testLimitZeroIsEmpty();
        testDifferentSeedsDiffer();
        testConsecutiveCallsOnSameInstanceContinueState();
        testStreamNotAllEqual();
    }
}
