/*
 * @test
 * @summary Test DoublePipeline.noneMatch(DoublePredicate) for normal and consumed state
 * @run main DoublePipelineWitupTest
 */

import java.util.Spliterator;
import java.util.function.DoublePredicate;
import java.util.stream.DoubleStream;
import java.util.stream.StreamSupport;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testNoneMatchNormal();
        testNoneMatchConsumed();
    }

    private static void testNoneMatchNormal() {
        // Create a DoubleStream from a fixed array
        double[] data = {1.0, 2.0, 3.0};
        DoubleStream stream = DoubleStream.of(data);

        // noneMatch with predicate that matches no elements (e.g. x < 0)
        boolean result = stream.noneMatch(new DoublePredicate() {
            public boolean test(double value) {
                return value < 0;
            }
        });

        if (!result) {
            throw new AssertionError("Expected noneMatch to return true when no elements match predicate");
        }

        // noneMatch with predicate that matches some elements (e.g. x > 2)
        stream = DoubleStream.of(data);
        result = stream.noneMatch(new DoublePredicate() {
            public boolean test(double value) {
                return value > 2;
            }
        });

        if (result) {
            throw new AssertionError("Expected noneMatch to return false when some elements match predicate");
        }
    }

    private static void testNoneMatchConsumed() {
        // Create a DoubleStream and consume it once
        DoubleStream stream = DoubleStream.of(1.0, 2.0, 3.0);
        stream.count(); // terminal operation consumes the stream

        try {
            // Calling noneMatch again on the consumed stream should throw IllegalStateException
            stream.noneMatch(new DoublePredicate() {
                public boolean test(double value) {
                    return true;
                }
            });
            throw new AssertionError("Expected IllegalStateException when calling noneMatch on consumed stream");
        } catch (IllegalStateException expected) {
            // Expected exception
        }
    }
}
