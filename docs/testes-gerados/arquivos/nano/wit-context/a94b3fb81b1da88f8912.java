/*
 * @test
 * @summary Test for ThreadLocalRandom.longs(long streamSize)
 * @run main ThreadLocalRandomWitupTest
 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testValidStreamSize();
        testNegativeStreamSize();
    }

    private static void testValidStreamSize() {
        long size = 5L;
        LongStream stream = ThreadLocalRandom.current().longs(size);
        long count = stream.count();
        if (count != size) {
            throw new AssertionError("Expected stream size: " + size + ", but got: " + count);
        }
    }

    private static void testNegativeStreamSize() {
        try {
            ThreadLocalRandom.current().longs(-1L);
            throw new AssertionError("Expected IllegalArgumentException for negative stream size");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        }
    }
}
