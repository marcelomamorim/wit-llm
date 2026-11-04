/*
 * @test
 * @summary Verifies LongPipeline.map behavior through the public LongStream API: value mapping, empty streams, null mapper rejection, laziness, chaining and parallel equivalence
 * @run main LongPipelineWitupTest
 */

import java.util.Arrays;
import java.util.stream.LongStream;

public class LongPipelineWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }

    public static void main(String[] args) {
        // Basic mapping
        long[] mapped = LongStream.of(1L, 2L, 3L).map(x -> x * 2).toArray();
        check(Arrays.equals(mapped, new long[]{2L, 4L, 6L}), "basic mapping");

        // Mapping an empty stream yields an empty stream
        check(LongStream.empty().map(x -> x + 1).count() == 0L, "empty stream mapping");

        // Mapping to zero and negative values
        long[] zeroed = LongStream.of(5L).map(x -> x - 5L).toArray();
        check(Arrays.equals(zeroed, new long[]{0L}), "mapping to zero");
        long[] negated = LongStream.of(1L, -2L).map(x -> -x).toArray();
        check(Arrays.equals(negated, new long[]{-1L, 2L}), "mapping to negatives");

        // Null mapper must be rejected eagerly at the map call
        boolean npe = false;
        try {
            LongStream.of(1L).map(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "null mapper rejected with NullPointerException");

        // Laziness: mapper is not invoked until a terminal operation runs
        final int[] calls = {0};
        LongStream pipeline = LongStream.of(1L, 2L).map(x -> {
            calls[0]++;
            return x;
        });
        check(calls[0] == 0, "mapper not invoked before terminal operation");
        long sum = pipeline.sum();
        check(sum == 3L, "sum after map");
        check(calls[0] == 2, "mapper invoked once per element");

        // Chained maps apply in order
        long[] chained = LongStream.of(3L).map(x -> x + 1).map(x -> x * 10).toArray();
        check(Arrays.equals(chained, new long[]{40L}), "chained mapping order");

        // Parallel and sequential results must agree
        long seq = LongStream.range(0L, 1000L).map(x -> x * x).sum();
        long par = LongStream.range(0L, 1000L).parallel().map(x -> x * x).sum();
        check(seq == par, "parallel equals sequential");
    }
}
