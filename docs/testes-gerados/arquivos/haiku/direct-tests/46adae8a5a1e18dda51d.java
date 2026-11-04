/*
 * @test
 * @summary Observable behavior of Random.longs(long streamSize): negative size rejection, exact stream size, and seed determinism
 * @run main RandomWitupTest
 */

import java.util.Random;
import java.util.stream.LongStream;

public class RandomWitupTest {

    public static void main(String[] args) {
        testNegativeSizeThrows();
        testMinValueSizeThrows();
        testZeroSizeIsEmpty();
        testExactStreamSize();
        testSameSeedProducesSameSequence();
    }

    static void testNegativeSizeThrows() {
        try {
            new Random(1L).longs(-1L);
            throw new RuntimeException("Expected IllegalArgumentException for size -1");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testMinValueSizeThrows() {
        try {
            new Random(1L).longs(Long.MIN_VALUE);
            throw new RuntimeException("Expected IllegalArgumentException for Long.MIN_VALUE");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    static void testZeroSizeIsEmpty() {
        long count = new Random(7L).longs(0L).count();
        if (count != 0L) {
            throw new RuntimeException("Expected 0 elements, got " + count);
        }
    }

    static void testExactStreamSize() {
        long[] values = new Random(123L).longs(5L).toArray();
        if (values.length != 5) {
            throw new RuntimeException("Expected 5 elements, got " + values.length);
        }
    }

    static void testSameSeedProducesSameSequence() {
        long[] a = new Random(42L).longs(10L).toArray();
        long[] b = new Random(42L).longs(10L).toArray();
        if (a.length != b.length) {
            throw new RuntimeException("Length mismatch");
        }
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                throw new RuntimeException("Mismatch at index " + i);
            }
        }
    }
}
