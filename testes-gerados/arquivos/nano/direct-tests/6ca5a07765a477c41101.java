/* @test    @summary Test Random.ints with valid range    @run main RandomWitupTest */

import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public class RandomWitupTest {

    public static void main(String[] args) {
        testIntsWithinRange();
        testIntsInvalidRange();
    }

    static void testIntsWithinRange() {
        java.util.Random rand = new java.util.Random(12345L);
        int origin = 10;
        int bound = 20;
        IntStream stream = rand.ints(origin, bound);
        int count = 0;
        for (int num : stream.limit(100)) {
            if (num < origin || num >= bound) {
                throw new AssertionError("Number out of range: " + num);
            }
            count++;
        }
        if (count != 100) {
            throw new AssertionError("Stream did not produce 100 elements");
        }
    }

    static void testIntsInvalidRange() {
        java.util.Random rand = new java.util.Random(54321L);
        try {
            rand.ints(20, 10).limit(1).forEach(i -> {});
            throw new AssertionError("Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception
        }
    }
}
