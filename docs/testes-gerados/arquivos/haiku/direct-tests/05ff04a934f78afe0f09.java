/*
 * @test
 * @summary Behavioral tests for DoublePipeline.allMatch via the public DoubleStream API
 * @run main DoublePipelineWitupTest
 */

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.DoubleStream;

public class DoublePipelineWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testAllMatchTrue() {
        boolean result = DoubleStream.of(1.0, 2.0, 3.0).allMatch(d -> d > 0.0);
        check(result, "allMatch should return true when all elements match");
    }

    static void testAllMatchFalse() {
        boolean result = DoubleStream.of(1.0, -2.0, 3.0).allMatch(d -> d > 0.0);
        check(!result, "allMatch should return false when one element does not match");
    }

    static void testAllMatchEmptyStream() {
        boolean result = DoubleStream.empty().allMatch(d -> false);
        check(result, "allMatch on an empty stream should return true");
    }

    static void testAllMatchShortCircuits() {
        AtomicInteger calls = new AtomicInteger();
        boolean result = DoubleStream.of(1.0, -1.0, 2.0, 3.0).allMatch(d -> {
            calls.incrementAndGet();
            return d > 0.0;
        });
        check(!result, "allMatch should return false for a non-matching element");
        check(calls.get() == 2, "allMatch should stop after the first non-matching element, calls=" + calls.get());
    }

    static void testAllMatchSingleElement() {
        check(DoubleStream.of(0.5).allMatch(d -> d == 0.5), "single matching element should yield true");
        check(!DoubleStream.of(0.5).allMatch(d -> d == 0.25), "single non-matching element should yield false");
    }

    public static void main(String[] args) {
        testAllMatchTrue();
        testAllMatchFalse();
        testAllMatchEmptyStream();
        testAllMatchShortCircuits();
        testAllMatchSingleElement();
    }
}
