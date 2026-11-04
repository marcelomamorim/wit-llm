/*
 * @test
 * @summary Witup-generated checks for Random.longs(long,long) argument validation and bounded deterministic output
 * @run main RandomWitupTest
 */
import java.util.Random;
import java.util.stream.LongStream;

public class RandomWitupTest {
    public static void main(String[] args) {
        testInvalidRangeThrows();
        testEqualBoundsThrows();
        testValuesWithinRange();
        testDeterministicSequence();
    }

    static void testInvalidRangeThrows() {
        Random r = new Random(42L);
        try {
            r.longs(10L, 3L);
            throw new AssertionError("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
        }
    }

    static void testEqualBoundsThrows() {
        Random r = new Random(42L);
        try {
            r.longs(5L, 5L);
            throw new AssertionError("expected IllegalArgumentException");
        } catch (IllegalArgumentException expected) {
        }
    }

    static void testValuesWithinRange() {
        long origin = 0L;
        long bound = 10L;
        long[] values = new Random(42L).longs(origin, bound).limit(1000).toArray();
        if (values.length != 1000) {
            throw new AssertionError("unexpected length: " + values.length);
        }
        for (long v : values) {
            if (v < origin || v >= bound) {
                throw new AssertionError("value out of range: " + v);
            }
        }
    }

    static void testDeterministicSequence() {
        long[] a = new Random(7L).longs(-100L, 100L).limit(50).toArray();
        long[] b = new Random(7L).longs(-100L, 100L).limit(50).toArray();
        if (a.length != b.length) {
            throw new AssertionError("length mismatch");
        }
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                throw new AssertionError("sequence mismatch at index " + i);
            }
            if (a[i] < -100L || a[i] >= 100L) {
                throw new AssertionError("value out of range: " + a[i]);
            }
        }
    }
}
