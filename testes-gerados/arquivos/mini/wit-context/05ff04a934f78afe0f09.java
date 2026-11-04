/*
 * @test
 * @summary Test DoublePipeline.allMatch method behavior and exception on consumed pipeline
 * @run main DoublePipelineWitupTest
 */

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.DoublePredicate;
import java.util.stream.DoubleStream;
import java.util.stream.DoublePipeline;
import java.util.stream.AbstractPipeline;
import java.util.stream.StreamSupport;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testAllMatchReturnsTrueForAllMatch();
        testAllMatchReturnsFalseForNonMatching();
        testAllMatchThrowsOnConsumedPipeline();
    }

    // Helper method to create a DoublePipeline from an array
    private static DoublePipeline<Double> createDoublePipeline(final double[] values) {
        Spliterator.OfDouble spliterator = Spliterators.spliterator(values, 0);
        // Use the protected constructor via anonymous subclass to create DoublePipeline
        return new DoublePipeline<Double>(spliterator, 0, false) {
            @Override
            public void forEach(DoubleConsumer action) {
                StreamSupport.doubleStream(() -> spliterator, false).forEach(action);
            }
        };
    }

    private static void testAllMatchReturnsTrueForAllMatch() {
        DoublePipeline<Double> pipeline = createDoublePipeline(new double[] {2.0, 4.0, 6.0});
        DoublePredicate isEven = d -> d % 2 == 0;
        boolean result = pipeline.allMatch(isEven);
        if (!result) {
            throw new AssertionError("Expected allMatch to return true for all even numbers");
        }
    }

    private static void testAllMatchReturnsFalseForNonMatching() {
        DoublePipeline<Double> pipeline = createDoublePipeline(new double[] {2.0, 3.0, 6.0});
        DoublePredicate isEven = d -> d % 2 == 0;
        boolean result = pipeline.allMatch(isEven);
        if (result) {
            throw new AssertionError("Expected allMatch to return false when not all match predicate");
        }
    }

    private static void testAllMatchThrowsOnConsumedPipeline() {
        DoublePipeline<Double> pipeline = createDoublePipeline(new double[] {1.0, 2.0, 3.0});
        DoublePredicate alwaysTrue = d -> true;
        // Consume the pipeline once
        pipeline.allMatch(alwaysTrue);
        try {
            // Second call should throw IllegalStateException due to pipeline consumed
            pipeline.allMatch(alwaysTrue);
            throw new AssertionError("Expected IllegalStateException on second allMatch call on consumed pipeline");
        } catch (IllegalStateException expected) {
            // Expected exception
        }
    }
}
