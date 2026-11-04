/* @test    @summary Test ThreadLocalRandom.ints method for valid and invalid ranges    @run main ThreadLocalRandomWitupTest */

import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public class ThreadLocalRandomWitupTest {

    public static void main(String[] args) {
        testValidRange();
        testInvalidRange();
    }

    static void testValidRange() {
        int origin = 10;
        int bound = 20;
        IntStream stream = null;
        try {
            stream = new java.util.concurrent.ThreadLocalRandom().ints(origin, bound);
            int count = (int) stream.limit(100).count();
            if (count != 100) {
                throw new AssertionError("Expected 100 elements, got " + count);
            }
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    static void testInvalidRange() {
        int origin = 20;
        int bound = 10;
        boolean exceptionThrown = false;
        try {
            java.util.concurrent.ThreadLocalRandom r = new java.util.concurrent.ThreadLocalRandom();
            r.ints(origin, bound).limit(1).count();
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected IllegalArgumentException for invalid range") ;
        }
    }
}
