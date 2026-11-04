/*
 * @test
 * @summary Verifies int-to-long widening observed through IntStream.asLongStream, which drives the anonymous Sink.accept(int) in IntPipeline
 * @run main IntPipelineWitupTest
 */

import java.util.Arrays;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {
    public static void main(String[] args) {
        long[] result = IntStream.of(1, -2, Integer.MAX_VALUE, Integer.MIN_VALUE)
                .asLongStream().toArray();
        long[] expected = {1L, -2L, 2147483647L, -2147483648L};
        if (!Arrays.equals(result, expected)) {
            throw new RuntimeException("unexpected widening result: " + Arrays.toString(result));
        }

        long sum = IntStream.of(Integer.MAX_VALUE, Integer.MAX_VALUE)
                .asLongStream().sum();
        if (sum != 2L * Integer.MAX_VALUE) {
            throw new RuntimeException("unexpected sum: " + sum);
        }

        long[] empty = IntStream.empty().asLongStream().toArray();
        if (empty.length != 0) {
            throw new RuntimeException("expected empty result");
        }
    }
}
