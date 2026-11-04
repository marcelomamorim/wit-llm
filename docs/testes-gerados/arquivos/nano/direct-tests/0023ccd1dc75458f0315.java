/* @test    @summary Test for ThreadLocalRandom.ints(long) method to ensure it throws IllegalArgumentException for negative sizes and returns a stream of correct size for valid input.    @run main ThreadLocalRandomWitupTest */

import java.util.stream.IntStream;
import java.util.stream.StreamSupport;
import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testNegativeStreamSize();
        testValidStreamSize();
    }

    static void testNegativeStreamSize() {
        try {
            ThreadLocalRandom.current().ints(-1L);
            throw new AssertionError("Expected IllegalArgumentException for negative size");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }

    static void testValidStreamSize() {
        long size = 10L;
        IntStream stream = ThreadLocalRandom.current().ints(size);
        int count = (int) stream.count();
        if (count != size) {
            throw new AssertionError("Stream size mismatch: expected " + size + " but got " + count);
        }
        stream.close();
    }
}
