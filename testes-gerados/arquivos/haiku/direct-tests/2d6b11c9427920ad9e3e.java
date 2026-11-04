/*
 * @test
 * @summary Verify IntPipeline.noneMatch behavior through the public IntStream API
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntStream;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        check(IntStream.of(1, 2, 3).noneMatch(x -> x > 5), true, "no element matches");
        check(IntStream.of(1, 2, 3).noneMatch(x -> x == 2), false, "one element matches");
        check(IntStream.of(-1, -2).noneMatch(x -> x < 0), false, "all elements match");
        check(IntStream.empty().noneMatch(x -> true), true, "empty stream");
        check(IntStream.range(0, 1000).parallel().noneMatch(x -> x == 999), false, "parallel match present");
        check(IntStream.range(0, 1000).parallel().noneMatch(x -> x < 0), true, "parallel no match");

        int[] calls = {0};
        boolean result = IntStream.of(1, 2, 3, 4).noneMatch(x -> {
            calls[0]++;
            return x == 2;
        });
        check(result, false, "short-circuit result");
        check(calls[0] == 2, "short-circuit should stop after first match");
    }

    private static void check(boolean actual, boolean expected, String message) {
        if (actual != expected) {
            throw new RuntimeException("Unexpected result: " + message + " expected=" + expected + " actual=" + actual);
        }
    }

    private static void check(boolean actual, String message) {
        check(actual, true, message);
    }
}
