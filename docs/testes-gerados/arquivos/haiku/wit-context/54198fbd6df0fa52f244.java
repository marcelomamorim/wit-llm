/*
 * @test
 * @summary Verifies IntStream.min() results and reuse rejection through public stream API
 * @run main IntPipelineWitupTest
 */

import java.util.OptionalInt;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testEmptyStreamMinIsEmpty();
        testMinOfPositiveAndNegativeValues();
        testMinWithBoundaryValues();
        testReusingConsumedStreamThrows();
    }

    private static void testEmptyStreamMinIsEmpty() {
        OptionalInt result = IntStream.empty().min();
        if (result.isPresent()) {
            throw new RuntimeException("min() of empty stream should be empty");
        }
    }

    private static void testMinOfPositiveAndNegativeValues() {
        OptionalInt result = IntStream.of(3, 1, 2).min();
        if (!result.isPresent() || result.getAsInt() != 1) {
            throw new RuntimeException("expected min 1 but got " + result);
        }
        OptionalInt negative = IntStream.of(5, -7, 0).min();
        if (!negative.isPresent() || negative.getAsInt() != -7) {
            throw new RuntimeException("expected min -7 but got " + negative);
        }
    }

    private static void testMinWithBoundaryValues() {
        OptionalInt result = IntStream.of(Integer.MAX_VALUE, Integer.MIN_VALUE).min();
        if (!result.isPresent() || result.getAsInt() != Integer.MIN_VALUE) {
            throw new RuntimeException("expected Integer.MIN_VALUE but got " + result);
        }
    }

    private static void testReusingConsumedStreamThrows() {
        IntStream stream = IntStream.of(1, 2, 3);
        stream.min();
        try {
            stream.min();
            throw new RuntimeException("expected IllegalStateException on reused stream");
        } catch (IllegalStateException expected) {
            // expected
        }
    }
}
