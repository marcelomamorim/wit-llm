/*
 * @test
 * @summary Verify LongStream.asDoubleStream conversion of long elements to double through the public API
 * @run main LongPipelineWitupTest
 */

import java.util.Arrays;
import java.util.stream.LongStream;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        double[] small = LongStream.of(1L, 2L, 3L).asDoubleStream().toArray();
        check(Arrays.equals(small, new double[] {1.0, 2.0, 3.0}),
              "small values: " + Arrays.toString(small));

        double[] edge = LongStream.of(Long.MAX_VALUE, Long.MIN_VALUE, -1L, 0L)
                .asDoubleStream().toArray();
        check(Arrays.equals(edge, new double[] {
                (double) Long.MAX_VALUE, (double) Long.MIN_VALUE, -1.0, 0.0}),
              "edge values: " + Arrays.toString(edge));

        double sum = LongStream.range(0, 5).map(x -> x * 2L).asDoubleStream().sum();
        check(sum == 20.0, "mapped sum expected 20.0 but was " + sum);

        double parallelSum = LongStream.range(0, 1000).parallel().asDoubleStream().sum();
        check(parallelSum == 499500.0, "parallel sum was " + parallelSum);

        long count = LongStream.range(0, 10).asDoubleStream().count();
        check(count == 10L, "count expected 10 but was " + count);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
