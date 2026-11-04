/*
 * @test
 * @summary Tests IntPipeline.anyMatch on empty, sequential, parallel and short-circuiting int streams
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntStream;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        check(!IntStream.empty().anyMatch(i -> true), "empty stream must return false");
        check(IntStream.of(1, 2, 3).anyMatch(i -> i == 2), "matching element must return true");
        check(!IntStream.of(1, 2, 3).anyMatch(i -> i > 10), "no matching element must return false");
        check(IntStream.of(5).anyMatch(i -> i == 5), "single matching element must return true");
        check(IntStream.of(1, -1).anyMatch(i -> i < 0), "negative match must return true");
        check(!IntStream.of(0, 0).anyMatch(i -> i != 0), "all zeros must not match non-zero predicate");
        check(IntStream.range(0, 1000).parallel().anyMatch(i -> i == 999), "parallel match must return true");
        check(!IntStream.range(0, 1000).parallel().anyMatch(i -> i < 0), "parallel no match must return false");

        int[] calls = {0};
        boolean result = IntStream.of(1, 2, 3, 4).anyMatch(i -> {
            calls[0]++;
            return i == 1;
        });
        check(result, "short-circuit case must return true");
        check(calls[0] == 1, "anyMatch must stop after first match in sequential stream, calls=" + calls[0]);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
