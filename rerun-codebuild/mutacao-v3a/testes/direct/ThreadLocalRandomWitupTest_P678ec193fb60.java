/* @test    @summary Test for ThreadLocalRandom.ints() method.    @run main ThreadLocalRandomWitupTest_P678ec193fb60 */

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public class ThreadLocalRandomWitupTest_P678ec193fb60 {

    public static void main(String[] args) {
        testIntsMethodReturnsStream();
    }

    public static void testIntsMethodReturnsStream() {
        ThreadLocalRandom tLR = ThreadLocalRandom.current();
        IntStream stream = tLR.ints();
        // Verify that the stream is not null
        if (stream == null) {
            throw new AssertionError("ints() returned null");
        }
        // Verify that the stream produces elements
        int[] generated = stream.limit(10).toArray();
        if (generated.length != 10) {
            throw new AssertionError("Stream produced incorrect number of elements: " + generated.length);
        }
        // Check that elements are within the full int range
        for (int value : generated) {
            // No specific range check, as ints() can generate any int
            // But ensure it's an int (always true)
        }
        // Additional: verify that stream is not empty
        if (generated.length == 0) {
            throw new AssertionError("Stream is empty");
        }
        System.out.println("testIntsMethodReturnsStream passed.");
    }
}
