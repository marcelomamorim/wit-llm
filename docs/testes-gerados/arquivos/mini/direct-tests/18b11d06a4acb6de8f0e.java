/*
 * @test
 * @summary Test DoublePipeline.anyMatch with simple predicates
 * @run main DoublePipelineWitupTest
 */

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.DoublePredicate;
import java.util.stream.DoubleStream;
import java.util.stream.StreamSupport;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testAnyMatchWithMatchingElement();
        testAnyMatchWithNoMatchingElement();
        testAnyMatchWithEmptyStream();
    }

    private static void testAnyMatchWithMatchingElement() {
        double[] data = {1.0, 2.0, 3.0};
        DoubleStream stream = DoubleStream.of(data);
        boolean result = stream.anyMatch(new DoublePredicate() {
            public boolean test(double value) {
                return value > 2.5;
            }
        });
        if (!result) {
            throw new AssertionError("Expected anyMatch to return true when element matches predicate");
        }
    }

    private static void testAnyMatchWithNoMatchingElement() {
        double[] data = {1.0, 2.0, 3.0};
        DoubleStream stream = DoubleStream.of(data);
        boolean result = stream.anyMatch(new DoublePredicate() {
            public boolean test(double value) {
                return value > 5.0;
            }
        });
        if (result) {
            throw new AssertionError("Expected anyMatch to return false when no element matches predicate");
        }
    }

    private static void testAnyMatchWithEmptyStream() {
        DoubleStream stream = DoubleStream.empty();
        boolean result = stream.anyMatch(new DoublePredicate() {
            public boolean test(double value) {
                return true;
            }
        });
        if (result) {
            throw new AssertionError("Expected anyMatch to return false on empty stream");
        }
    }

}
