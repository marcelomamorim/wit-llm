/*
 * @test
 * @summary Verifies IntPipeline.max() returns the maximum element, OptionalInt.empty() for empty streams, and rejects reuse of a consumed stream with IllegalStateException
 * @run main IntPipelineWitupTest
 */

import java.util.OptionalInt;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testMaxOfPositiveValues();
        testMaxOfNegativeValues();
        testMaxOfSingleMinValue();
        testMaxOfEmptyStream();
        testReuseAfterMaxThrowsIllegalStateException();
    }

    static void testMaxOfPositiveValues() {
        OptionalInt result = IntStream.of(3, 1, 2).max();
        check(result.isPresent(), "max of non-empty stream must be present");
        check(result.getAsInt() == 3, "expected 3 but was " + result.getAsInt());
    }

    static void testMaxOfNegativeValues() {
        OptionalInt result = IntStream.of(-5, -2, -9).max();
        check(result.isPresent(), "max of negative stream must be present");
        check(result.getAsInt() == -2, "expected -2 but was " + result.getAsInt());
    }

    static void testMaxOfSingleMinValue() {
        OptionalInt result = IntStream.of(Integer.MIN_VALUE).max();
        check(result.isPresent(), "max of single element must be present");
        check(result.getAsInt() == Integer.MIN_VALUE,
              "expected Integer.MIN_VALUE but was " + result.getAsInt());
    }

    static void testMaxOfEmptyStream() {
        OptionalInt result = IntStream.empty().max();
        check(!result.isPresent(), "max of empty stream must be empty");
    }

    static void testReuseAfterMaxThrowsIllegalStateException() {
        IntStream stream = IntStream.of(1, 2);
        stream.max();
        try {
            stream.max();
            throw new RuntimeException("expected IllegalStateException on reused stream");
        } catch (IllegalStateException expected) {
            // expected: stream already operated upon or closed
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
