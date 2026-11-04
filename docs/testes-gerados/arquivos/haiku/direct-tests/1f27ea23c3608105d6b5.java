/*
 * @test
 * @summary Verifies anyMatch behavior on sequential reference streams
 * @run main ReferencePipelineWitupTest
 */
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {
    public static void main(String[] args) {
        check(Stream.of(1, 2, 3).anyMatch(x -> x > 2), "match expected");
        check(!Stream.of(1, 2, 3).anyMatch(x -> x > 10), "no match expected");
        check(!Stream.<Integer>empty().anyMatch(x -> true), "empty stream must return false");
        int[] calls = {0};
        boolean r = Stream.of(1, 2, 3).anyMatch(x -> { calls[0]++; return x == 1; });
        check(r, "first element should match");
        check(calls[0] == 1, "evaluation should short-circuit after first match");
        check(Stream.iterate(1, x -> x + 1).anyMatch(x -> x > 5), "infinite stream should short-circuit");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
