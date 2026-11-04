/*
 * @test
 * @summary Behavioral tests for SplittableRandom.longs() using only public API
 * @run main SplittableRandomWitupTest
 */

import java.util.Arrays;
import java.util.SplittableRandom;

public class SplittableRandomWitupTest {

    public static void main(String[] args) {
        testCountMatchesLimit();
        testZeroLimitIsEmpty();
        testSameSeedIsDeterministic();
        testParallelStreamCount();
        testSplitInstancesStillProduceStreams();
    }

    private static void testCountMatchesLimit() {
        long count = new SplittableRandom(42L).longs().limit(100L).count();
        if (count != 100L) {
            throw new RuntimeException("expected 100 longs, got " + count);
        }
    }

    private static void testZeroLimitIsEmpty() {
        long count = new SplittableRandom(1L).longs().limit(0L).count();
        if (count != 0L) {
            throw new RuntimeException("expected 0 longs, got " + count);
        }
    }

    private static void testSameSeedIsDeterministic() {
        long[] a = new SplittableRandom(12345L).longs().limit(16L).toArray();
        long[] b = new SplittableRandom(12345L).longs().limit(16L).toArray();
        if (a.length != 16 || b.length != 16) {
            throw new RuntimeException("unexpected lengths: " + a.length + ", " + b.length);
        }
        if (!Arrays.equals(a, b)) {
            throw new RuntimeException("same seed produced different sequences");
        }
    }

    private static void testParallelStreamCount() {
        long count = new SplittableRandom(7L).longs().parallel().limit(64L).count();
        if (count != 64L) {
            throw new RuntimeException("expected 64 longs in parallel, got " + count);
        }
    }

    private static void testSplitInstancesStillProduceStreams() {
        SplittableRandom parent = new SplittableRandom(99L);
        SplittableRandom child = parent.split();
        long countParent = parent.longs().limit(8L).count();
        long countChild = child.longs().limit(8L).count();
        if (countParent != 8L || countChild != 8L) {
            throw new RuntimeException("unexpected counts: " + countParent + ", " + countChild);
        }
    }
}
