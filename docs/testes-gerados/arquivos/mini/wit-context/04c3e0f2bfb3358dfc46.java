/*
 * @test
 * @summary Test IntPipeline.limit(long) for valid and invalid inputs
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntStream;
import java.util.stream.SliceOps;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testLimitNegative();
        testLimitZero();
        testLimitPositive();
    }

    private static void testLimitNegative() {
        try {
            IntStream stream = SliceOps.makeInt(null, 0, 0); // dummy to call limit
            // The actual limit method is final and called on IntPipeline instance,
            // but we cannot instantiate IntPipeline directly (abstract).
            // Instead, test limit by calling limit on a known IntStream implementation.
            // Using IntStream.range(0,1) as a concrete IntStream.
            IntStream.range(0, 1).limit(-1);
            throw new AssertionError("Expected IllegalArgumentException for negative limit");
        } catch (IllegalArgumentException e) {
            // Expected
            if (!"-1".equals(e.getMessage())) {
                throw new AssertionError("Exception message mismatch: " + e.getMessage());
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }

    private static void testLimitZero() {
        IntStream stream = IntStream.range(0, 10).limit(0);
        int count = (int) stream.count();
        if (count != 0) {
            throw new AssertionError("Expected count 0 for limit(0), got " + count);
        }
    }

    private static void testLimitPositive() {
        IntStream stream = IntStream.range(0, 10).limit(5);
        int count = (int) stream.count();
        if (count != 5) {
            throw new AssertionError("Expected count 5 for limit(5), got " + count);
        }
    }
}
