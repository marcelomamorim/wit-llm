/*
 * @test
 * @summary Tests for IntPipeline.limit(long)
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntPipeline;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testLimitWithNegative();
        testLimitWithZero();
        testLimitWithPositive();
    }

    private static void testLimitWithNegative() {
        IntStream stream = IntStream.of(1, 2, 3);
        try {
            IntStream result = stream.limit(-1);
            // Expecting an exception, so if no exception, fail
            System.err.println("Expected IllegalArgumentException for negative maxSize, but none was thrown.");
        } catch (IllegalArgumentException e) {
            // Expected exception
            System.out.println("Caught expected IllegalArgumentException for negative maxSize.");
        }
    }

    private static void testLimitWithZero() {
        IntStream stream = IntStream.of(1, 2, 3);
        IntStream limitedStream = stream.limit(0);
        int[] result = limitedStream.toArray();
        if (result.length != 0) {
            System.err.println("Expected empty array for limit(0), but got length: " + result.length);
        } else {
            System.out.println("limit(0) produces empty stream as expected.");
        }
    }

    private static void testLimitWithPositive() {
        IntStream stream = IntStream.of(1, 2, 3, 4, 5);
        IntStream limitedStream = stream.limit(3);
        int[] result = limitedStream.toArray();
        if (result.length != 3) {
            System.err.println("Expected array length 3, but got: " + result.length);
        } else {
            boolean correct = true;
            for (int i = 0; i < 3; i++) {
                if (result[i] != i + 1) {
                    correct = false;
                    break;
                }
            }
            if (correct) {
                System.out.println("limit(3) produces expected elements.");
            } else {
                System.err.println("Elements do not match expected sequence.");
            }
        }
    }
}
