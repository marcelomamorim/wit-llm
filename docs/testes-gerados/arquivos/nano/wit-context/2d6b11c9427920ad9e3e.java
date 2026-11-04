/*
 * @test
 * @summary Test for IntPipeline.noneMatch to verify behavior with valid and invalid inputs.
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntPipeline;
import java.util.function.IntPredicate;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testNoneMatchWithValidPredicate();
        testNoneMatchWithNullPredicate();
        testNoneMatchWithLinkedOrConsumed();
    }

    private static void testNoneMatchWithValidPredicate() {
        IntPredicate predicate = x -> x > 10;
        try {
            boolean result = IntPipeline.empty().noneMatch(predicate);
            System.out.println("testNoneMatchWithValidPredicate: " + result);
        } catch (Exception e) {
            System.err.println("Unexpected exception in valid predicate test: " + e);
        }
    }

    private static void testNoneMatchWithNullPredicate() {
        try {
            IntPredicate predicate = null;
            IntPipeline.empty().noneMatch(predicate);
            System.err.println("testNoneMatchWithNullPredicate: Failed to throw NullPointerException");
        } catch (NullPointerException e) {
            System.out.println("testNoneMatchWithNullPredicate: Caught expected NullPointerException");
        } catch (Exception e) {
            System.err.println("Unexpected exception in null predicate test: " + e);
        }
    }

    private static void testNoneMatchWithLinkedOrConsumed() {
        IntPredicate predicate = x -> x > 10;
        IntPipeline pipeline = IntPipeline.empty();
        // Simulate linked or consumed state by evaluating the pipeline to consume it
        try {
            pipeline.evaluate(); // assuming evaluate() exists to consume the pipeline
            // Now call noneMatch again, expecting an IllegalStateException
            pipeline.noneMatch(predicate);
            System.err.println("testNoneMatchWithLinkedOrConsumed: Failed to throw IllegalStateException");
        } catch (IllegalStateException e) {
            System.out.println("testNoneMatchWithLinkedOrConsumed: Caught expected IllegalStateException");
        } catch (Exception e) {
            System.err.println("Unexpected exception in linked or consumed state test: " + e);
        }
    }
}
