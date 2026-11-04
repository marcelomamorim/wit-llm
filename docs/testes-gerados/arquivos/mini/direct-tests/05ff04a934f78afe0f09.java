/*
 * @test
 * @summary Test DoublePipeline.allMatch method behavior
 * @run main DoublePipelineWitupTest
 */

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.DoublePredicate;
import java.util.stream.DoubleStream;
import java.util.stream.StreamSupport;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testAllMatchWithAllMatchingElements();
        testAllMatchWithSomeNonMatchingElements();
        testAllMatchWithEmptyStream();
        testAllMatchWithNullPredicate();
    }

    private static void testAllMatchWithAllMatchingElements() {
        double[] values = {2.0, 4.0, 6.0, 8.0};
        DoubleStream stream = DoubleStream.of(values);
        boolean result = stream.allMatch(new DoublePredicate() {
            public boolean test(double d) {
                return d % 2 == 0;
            }
        });
        if (!result) {
            throw new AssertionError("Expected allMatch to return true when all elements match predicate");
        }
    }

    private static void testAllMatchWithSomeNonMatchingElements() {
        double[] values = {2.0, 3.0, 6.0, 8.0};
        DoubleStream stream = DoubleStream.of(values);
        boolean result = stream.allMatch(new DoublePredicate() {
            public boolean test(double d) {
                return d % 2 == 0;
            }
        });
        if (result) {
            throw new AssertionError("Expected allMatch to return false when some elements do not match predicate");
        }
    }

    private static void testAllMatchWithEmptyStream() {
        DoubleStream stream = DoubleStream.empty();
        boolean result = stream.allMatch(new DoublePredicate() {
            public boolean test(double d) {
                return false;
            }
        });
        if (!result) {
            throw new AssertionError("Expected allMatch to return true for empty stream");
        }
    }

    private static void testAllMatchWithNullPredicate() {
        DoubleStream stream = DoubleStream.of(1.0, 2.0);
        try {
            stream.allMatch(null);
            throw new AssertionError("Expected NullPointerException when predicate is null");
        } catch (NullPointerException e) {
            // Expected
        }
    }
}
