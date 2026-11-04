/*
 * @test
 * @summary Behavioral tests for IntStream.reduce(IntBinaryOperator) via the public IntPipeline API, including reuse of a consumed stream
 * @run main IntPipelineWitupTest
 */

import java.util.OptionalInt;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void main(String[] args) {
        OptionalInt empty = IntStream.empty().reduce((a, b) -> a + b);
        check(!empty.isPresent(), "empty stream must yield empty OptionalInt");

        OptionalInt single = IntStream.of(7).reduce((a, b) -> a + b);
        check(single.isPresent() && single.getAsInt() == 7, "single element must be returned unchanged");

        OptionalInt sum = IntStream.of(1, 2, 3, 4).reduce((a, b) -> a + b);
        check(sum.isPresent() && sum.getAsInt() == 10, "sum reduction must be 10");

        OptionalInt max = IntStream.of(3, 9, 2).reduce((a, b) -> Math.max(a, b));
        check(max.isPresent() && max.getAsInt() == 9, "max reduction must be 9");

        IntStream consumed = IntStream.of(1, 2);
        consumed.reduce((a, b) -> a + b);
        boolean thrown = false;
        try {
            consumed.reduce((a, b) -> a + b);
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "reusing a consumed stream must throw IllegalStateException");
    }
}
