/*
 * @test
 * @summary Test DoublePipeline.noneMatch method behavior
 * @run main java.util.stream.DoublePipelineWitupTest
 */

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.DoublePredicate;
import java.util.stream.DoubleStream;
import java.util.stream.StreamSupport;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testNoneMatchWithEmptyStream();
        testNoneMatchWithNoMatchingElements();
        testNoneMatchWithSomeMatchingElements();
        testNoneMatchWithAllMatchingElements();
    }

    private static void testNoneMatchWithEmptyStream() {
        DoubleStream emptyStream = DoubleStream.empty();
        boolean result = emptyStream.noneMatch(new DoublePredicate() {
            public boolean test(double value) {
                return true; // predicate always true
            }
        });
        if (!result) {
            throw new AssertionError("noneMatch should return true for empty stream");
        }
    }

    private static void testNoneMatchWithNoMatchingElements() {
        DoubleStream stream = DoubleStream.of(1.0, 2.0, 3.0);
        boolean result = stream.noneMatch(new DoublePredicate() {
            public boolean test(double value) {
                return value > 5.0;
            }
        });
        if (!result) {
            throw new AssertionError("noneMatch should return true when no elements match predicate");
        }
    }

    private static void testNoneMatchWithSomeMatchingElements() {
        DoubleStream stream = DoubleStream.of(1.0, 6.0, 3.0);
        boolean result = stream.noneMatch(new DoublePredicate() {
            public boolean test(double value) {
                return value > 5.0;
            }
        });
        if (result) {
            throw new AssertionError("noneMatch should return false when some elements match predicate");
        }
    }

    private static void testNoneMatchWithAllMatchingElements() {
        DoubleStream stream = DoubleStream.of(6.0, 7.0, 8.0);
        boolean result = stream.noneMatch(new DoublePredicate() {
            public boolean test(double value) {
                return value > 5.0;
            }
        });
        if (result) {
            throw new AssertionError("noneMatch should return false when all elements match predicate");
        }
    }
}
