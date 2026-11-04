/*
 * @test
 * @summary Test for DoublePipeline.anyMatch(DoublePredicate predicate)
 * @run main DoublePipelineWitupTest
 */

import java.util.stream.DoublePipeline;
import java.util.function.DoublePredicate;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testAnyMatchWithNullPredicate();
        testAnyMatchWithAlwaysTruePredicate();
        testAnyMatchWithAlwaysFalsePredicate();
    }

    private static void testAnyMatchWithNullPredicate() {
        try {
            DoublePipeline pipeline = createSamplePipeline();
            pipeline.anyMatch(null);
            System.err.println("Expected NullPointerException not thrown");
            System.exit(1);
        } catch (NullPointerException e) {
            // Expected exception
        }
    }

    private static void testAnyMatchWithAlwaysTruePredicate() {
        DoublePredicate alwaysTrue = d -> true;
        DoublePipeline pipeline = createSamplePipeline();
        boolean result = pipeline.anyMatch(alwaysTrue);
        if (!result) {
            System.err.println("Failed: expected true for alwaysTrue predicate");
            System.exit(1);
        }
    }

    private static void testAnyMatchWithAlwaysFalsePredicate() {
        DoublePredicate alwaysFalse = d -> false;
        DoublePipeline pipeline = createSamplePipeline();
        boolean result = pipeline.anyMatch(alwaysFalse);
        if (result) {
            System.err.println("Failed: expected false for alwaysFalse predicate");
            System.exit(1);
        }
    }

    private static DoublePipeline createSamplePipeline() {
        // Create a simple DoublePipeline with a known source
        // Using a spliterator of a small array for simplicity
        double[] data = {1.0, 2.0, 3.0};
        java.util.Spliterator.OfDouble spliterator = java.util.Spliterators.spliterator(data, 0);
        return new DoublePipeline.Head(spliterator, 0, false);
    }
}
