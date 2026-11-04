/*
 * @test
 * @summary Test for IntPipeline.anyMatch with valid predicate
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntPipeline;
import java.util.function.IntPredicate;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testAnyMatchWithNonMatchingPredicate();
        testAnyMatchWithMatchingPredicate();
        testAnyMatchWithNullPredicate();
    }

    private static void testAnyMatchWithNonMatchingPredicate() {
        IntPredicate predicate = i -> i > 1000;
        int[] data = {1, 2, 3, 4, 5};
        IntPipeline pipeline = IntPipeline.of(data, 0, data.length);
        boolean result = pipeline.anyMatch(predicate);
        if (result != false) {
            throw new AssertionError("Expected false for non-matching predicate");
        }
    }

    private static void testAnyMatchWithMatchingPredicate() {
        IntPredicate predicate = i -> i == 3;
        int[] data = {1, 2, 3, 4, 5};
        IntPipeline pipeline = IntPipeline.of(data, 0, data.length);
        boolean result = pipeline.anyMatch(predicate);
        if (result != true) {
            throw new AssertionError("Expected true for matching predicate");
        }
    }

    private static void testAnyMatchWithNullPredicate() {
        try {
            IntPipeline.of(new int[]{1, 2, 3}, 0, 3).anyMatch(null);
            throw new AssertionError("Expected NullPointerException for null predicate");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
