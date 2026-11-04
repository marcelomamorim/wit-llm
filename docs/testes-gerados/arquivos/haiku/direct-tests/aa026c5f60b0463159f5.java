/*
 * @test
 * @summary Behavioral checks for LongPipeline.findAny() through public LongStream API
 * @run main LongPipelineWitupTest
 */

import java.util.OptionalLong;
import java.util.stream.LongStream;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        OptionalLong empty = LongStream.empty().findAny();
        check(!empty.isPresent(), "empty stream must yield empty OptionalLong");

        OptionalLong emptyParallel = LongStream.empty().parallel().findAny();
        check(!emptyParallel.isPresent(), "empty parallel stream must yield empty OptionalLong");

        OptionalLong single = LongStream.of(42L).findAny();
        check(single.isPresent() && single.getAsLong() == 42L, "single element must be returned");

        OptionalLong minValue = LongStream.of(Long.MIN_VALUE).findAny();
        check(minValue.isPresent() && minValue.getAsLong() == Long.MIN_VALUE,
              "Long.MIN_VALUE must be returned unchanged");

        OptionalLong noMatch = LongStream.of(1L, 3L, 5L).filter(x -> x % 2 == 0).findAny();
        check(!noMatch.isPresent(), "filter with no matches must yield empty OptionalLong");

        OptionalLong shortCircuit = LongStream.iterate(1L, x -> x + 1)
                                              .filter(x -> x > 10)
                                              .findAny();
        check(shortCircuit.isPresent() && shortCircuit.getAsLong() > 10,
              "findAny on infinite stream must short-circuit with a matching element");

        long[] values = {7L, 8L, 9L};
        OptionalLong multi = LongStream.of(values).findAny();
        check(multi.isPresent() && contains(values, multi.getAsLong()),
              "result must be one of the stream elements");

        OptionalLong parallel = LongStream.range(0, 10000)
                                          .parallel()
                                          .filter(x -> x % 7 == 0)
                                          .findAny();
        check(parallel.isPresent() && parallel.getAsLong() % 7 == 0,
              "parallel findAny must return a matching element");
    }

    private static boolean contains(long[] values, long target) {
        for (long v : values) {
            if (v == target) {
                return true;
            }
        }
        return false;
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
