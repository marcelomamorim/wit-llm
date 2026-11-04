/*
 * @test
 * @summary Test IntPipeline.limit(long) method behavior
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntStream;
import java.util.stream.StreamSupport;
import java.util.Spliterator;
import java.util.Spliterators;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testLimitPositive();
        testLimitZero();
        testLimitNegative();
    }

    private static void testLimitPositive() {
        IntStream base = IntStream.of(1, 2, 3, 4, 5);
        IntStream limited = base.limit(3);
        int[] result = limited.toArray();
        if (result.length != 3) {
            throw new AssertionError("Expected length 3 but got " + result.length);
        }
        if (result[0] != 1 || result[1] != 2 || result[2] != 3) {
            throw new AssertionError("Expected first three elements 1,2,3 but got " + java.util.Arrays.toString(result));
        }
    }

    private static void testLimitZero() {
        IntStream base = IntStream.of(1, 2, 3);
        IntStream limited = base.limit(0);
        int[] result = limited.toArray();
        if (result.length != 0) {
            throw new AssertionError("Expected empty array but got length " + result.length);
        }
    }

    private static void testLimitNegative() {
        IntStream base = IntStream.of(1, 2, 3);
        try {
            base.limit(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative limit");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}
