/*
 * @test
 * @summary Test DoublePipeline.anyMatch(DoublePredicate) for normal and consumed state
 * @run main DoublePipelineWitupTest
 */

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.DoublePredicate;
import java.util.stream.DoubleStream;
import java.util.stream.StreamSupport;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testAnyMatchNormal();
        testAnyMatchConsumed();
    }

    private static void testAnyMatchNormal() {
        // Create a DoubleStream from a fixed array
        double[] data = {1.0, 2.0, 3.0};
        DoubleStream stream = DoubleStream.of(data);

        // Test anyMatch with a predicate that matches some elements
        boolean result = stream.anyMatch(new DoublePredicate() {
            public boolean test(double value) {
                return value > 2.0;
            }
        });

        if (!result) {
            throw new AssertionError("Expected anyMatch to return true for value > 2.0");
        }

        // Test anyMatch with a predicate that matches no elements
        stream = DoubleStream.of(data);
        result = stream.anyMatch(new DoublePredicate() {
            public boolean test(double value) {
                return value > 5.0;
            }
        });

        if (result) {
            throw new AssertionError("Expected anyMatch to return false for value > 5.0");
        }
    }

    private static void testAnyMatchConsumed() {
        // Create a DoubleStream and consume it
        double[] data = {1.0, 2.0, 3.0};
        DoubleStream stream = DoubleStream.of(data);
        stream.count(); // terminal operation consumes the stream

        // Now calling anyMatch on the consumed stream should throw IllegalStateException
        boolean threw = false;
        try {
            stream.anyMatch(new DoublePredicate() {
                public boolean test(double value) {
                    return true;
                }
            });
        } catch (IllegalStateException e) {
            threw = true;
        }

        if (!threw) {
            throw new AssertionError("Expected IllegalStateException when calling anyMatch on consumed stream");
        }
    }
}
