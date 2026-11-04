/*
 * @test
 * @summary Tests for Random.longs(long, long): range validation, bounded values, determinism and degenerate ranges
 * @run main RandomWitupTest
 */

public class RandomWitupTest {

    public static void main(String[] args) {
        testInvalidRangeThrows();
        testBoundedValues();
        testDeterministicSequence();
        testSingleValueRange();
        testFullLongRange();
        System.out.println("All Random.longs tests passed");
    }

    static void testInvalidRangeThrows() {
        checkIAE(5L, 5L);
        checkIAE(10L, 3L);
        checkIAE(0L, -1L);
    }

    static void checkIAE(long origin, long bound) {
        Random r = new Random(1L);
        boolean thrown = false;
        try {
            r.longs(origin, bound);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("Expected IllegalArgumentException for origin=" + origin + " bound=" + bound);
        }
    }

    static void testBoundedValues() {
        Random r = new Random(12345L);
        long[] values = r.longs(1L, 10L).limit(1000).toArray();
        if (values.length != 1000) {
            throw new RuntimeException("Expected 1000 values, got " + values.length);
        }
        for (long v : values) {
            if (v < 1L || v >= 10L) {
                throw new RuntimeException("Value out of range [1,10): " + v);
            }
        }
    }

    static void testDeterministicSequence() {
        long[] a = new Random(42L).longs(-100L, 100L).limit(200).toArray();
        long[] b = new Random(42L).longs(-100L, 100L).limit(200).toArray();
        if (a.length != b.length) {
            throw new RuntimeException("Length mismatch");
        }
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                throw new RuntimeException("Sequences differ at index " + i);
            }
            if (a[i] < -100L || a[i] >= 100L) {
                throw new RuntimeException("Value out of range [-100,100): " + a[i]);
            }
        }
    }

    static void testSingleValueRange() {
        long[] values = new Random(7L).longs(-5L, -4L).limit(50).toArray();
        for (long v : values) {
            if (v != -5L) {
                throw new RuntimeException("Expected only -5 in range [-5,-4), got " + v);
            }
        }
    }

    static void testFullLongRange() {
        long origin = Long.MIN_VALUE;
        long bound = Long.MAX_VALUE;
        long[] values = new Random(99L).longs(origin, bound).limit(500).toArray();
        if (values.length != 500) {
            throw new RuntimeException("Expected 500 values, got " + values.length);
        }
        for (long v : values) {
            if (v < origin || v >= bound) {
                throw new RuntimeException("Value out of full range: " + v);
            }
        }
    }
}
