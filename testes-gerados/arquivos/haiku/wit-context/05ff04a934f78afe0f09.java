/*
 * @test
 * @summary Witup-generated checks for DoublePipeline.allMatch behavior on the public DoubleStream API
 * @run main DoublePipelineWitupTest
 */

import java.util.stream.DoubleStream;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testAllMatchTrueOnAllElements();
        testAllMatchFalseWhenOneElementFails();
        testAllMatchOnEmptyStreamReturnsTrue();
        testAllMatchShortCircuits();
        testAllMatchNullPredicateThrowsNPE();
        testReuseAfterTerminalThrowsISE();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testAllMatchTrueOnAllElements() {
        boolean result = DoubleStream.of(1.0, 2.0, 3.0).allMatch(d -> d > 0.0);
        check(result, "allMatch should return true when all elements match");
    }

    static void testAllMatchFalseWhenOneElementFails() {
        boolean result = DoubleStream.of(1.0, -2.0, 3.0).allMatch(d -> d > 0.0);
        check(!result, "allMatch should return false when one element does not match");
    }

    static void testAllMatchOnEmptyStreamReturnsTrue() {
        boolean result = DoubleStream.empty().allMatch(d -> false);
        check(result, "allMatch on empty stream should return true");
    }

    static void testAllMatchShortCircuits() {
        final int[] calls = {0};
        boolean result = DoubleStream.of(1.0, 2.0, 3.0, 4.0).allMatch(d -> {
            calls[0]++;
            return d < 2.0;
        });
        check(!result, "allMatch should return false for the failing element");
        check(calls[0] == 2, "allMatch should stop after the first non-matching element, calls=" + calls[0]);
    }

    static void testAllMatchNullPredicateThrowsNPE() {
        boolean thrown = false;
        try {
            DoubleStream.of(1.0).allMatch(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "allMatch(null) should throw NullPointerException");
    }

    static void testReuseAfterTerminalThrowsISE() {
        DoubleStream s = DoubleStream.of(1.0, 2.0);
        s.allMatch(d -> true);
        boolean thrown = false;
        try {
            s.allMatch(d -> true);
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "reusing a consumed stream should throw IllegalStateException");
    }
}
