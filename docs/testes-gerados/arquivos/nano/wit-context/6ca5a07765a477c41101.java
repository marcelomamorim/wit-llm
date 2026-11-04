/* @test    @summary Test for Random.ints(int, int) method to verify exception handling and normal behavior    @run main RandomWitupTest */

import java.util.Random;
import java.util.stream.IntStream;

public class RandomWitupTest {

    public static void main(String[] args) {
        testIntsWithValidRange();
        testIntsWithInvalidRange();
    }

    private static void testIntsWithValidRange() {
        Random rand = new Random(12345L);
        int origin = 10;
        int bound = 20;
        try {
            IntStream stream = rand.ints(origin, bound);
            int[] values = stream.limit(5).toArray();
            boolean allInRange = true;
            for (int v : values) {
                if (v < origin || v >= bound) {
                    allInRange = false;
                    break;
                }
            }
            if (!allInRange) {
                throw new AssertionError("Values out of range: " + java.util.Arrays.toString(values));
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in valid range test: " + e, e);
        }
    }

    private static void testIntsWithInvalidRange() {
        Random rand = new Random(54321L);
        int origin = 20;
        int bound = 10;
        try {
            rand.ints(origin, bound).limit(1).toArray();
            throw new AssertionError("Expected IllegalArgumentException not thrown for invalid range");
        } catch (IllegalArgumentException e) {
            // Expected exception, test passes
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e, e);
        }
    }
}
