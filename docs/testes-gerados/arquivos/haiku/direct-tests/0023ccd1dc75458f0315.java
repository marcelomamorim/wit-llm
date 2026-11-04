/*
 * @test
 * @summary Verifies ThreadLocalRandom.ints(long) rejects negative stream sizes and produces streams of the requested size
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        ThreadLocalRandom tlr = ThreadLocalRandom.current();

        testNegativeSizeThrows(tlr, -1L);
        testNegativeSizeThrows(tlr, Long.MIN_VALUE);

        check(tlr.ints(0L).count() == 0L, "ints(0) must be empty");

        check(tlr.ints(1L).count() == 1L, "ints(1) must have one element");
        check(tlr.ints(5L).count() == 5L, "ints(5) must have five elements");
        check(tlr.ints(1000L).count() == 1000L, "ints(1000) must have 1000 elements");

        int[] values = tlr.ints(10L).toArray();
        check(values.length == 10, "toArray length must match streamSize");

        IntStream s = tlr.ints(3L);
        check(s != null, "ints(3) must not return null");
        check(s.count() == 3L, "ints(3) stream must yield 3 elements");

        IntStream a = tlr.ints(4L);
        IntStream b = tlr.ints(4L);
        check(a != b, "each call must return a new stream");
        check(a.count() == 4L && b.count() == 4L, "independent streams must each have 4 elements");
    }

    private static void testNegativeSizeThrows(ThreadLocalRandom tlr, long size) {
        boolean thrown = false;
        try {
            tlr.ints(size);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("ints(" + size + ") should throw IllegalArgumentException");
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
