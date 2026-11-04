/*
 * @test
 * @summary Tests for DoublePipeline.allMatch(DoublePredicate)
 * @run main DoublePipelineWitupTest
 */

import java.util.stream.DoublePipeline;
import java.util.function.DoublePredicate;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testAllMatchWithNullPredicate();
        testAllMatchWithPredicateThatReturnsFalse();
        testAllMatchWithPredicateThatReturnsTrue();
    }

    private static void testAllMatchWithNullPredicate() {
        try {
            DoublePipeline pipeline = createSamplePipeline();
            pipeline.allMatch(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Throwable t) {
            System.out.println("FAIL: Unexpected exception: " + t);
        }
    }

    private static void testAllMatchWithPredicateThatReturnsFalse() {
        DoublePipeline pipeline = createSamplePipeline();
        DoublePredicate predicate = d -> false;
        boolean result = pipeline.allMatch(predicate);
        if (result == false) {
            System.out.println("PASS: allMatch with predicate returning false yields false");
        } else {
            System.out.println("FAIL: Expected false, got " + result);
        }
    }

    private static void testAllMatchWithPredicateThatReturnsTrue() {
        DoublePipeline pipeline = createSamplePipeline();
        DoublePredicate predicate = d -> true;
        boolean result = pipeline.allMatch(predicate);
        if (result == true) {
            System.out.println("PASS: allMatch with predicate returning true yields true");
        } else {
            System.out.println("FAIL: Expected true, got " + result);
        }
    }

    private static DoublePipeline createSamplePipeline() {
        // Create a simple DoubleStream for testing
        return DoublePipeline.of(new double[]{1.0, 2.0, 3.0});
    }
}
