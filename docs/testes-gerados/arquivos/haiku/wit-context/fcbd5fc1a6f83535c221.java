/*
 * @test
 * @summary Verifies IntStream.mapToDouble converts int elements to double through the public API, without relying on the internal anonymous Sink in IntPipeline.
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntStream;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        double sum = IntStream.of(1, 2, 3).mapToDouble(i -> i).sum();
        check(sum == 6.0, "sum of mapToDouble(1,2,3) should be 6.0");

        double[] values = IntStream.of(Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE)
                .mapToDouble(i -> i)
                .toArray();
        check(values.length == 5, "length should be 5");
        check(values[0] == (double) Integer.MIN_VALUE, "MIN_VALUE conversion");
        check(values[1] == -1.0, "-1 conversion");
        check(values[2] == 0.0, "0 conversion");
        check(values[3] == 1.0, "1 conversion");
        check(values[4] == (double) Integer.MAX_VALUE, "MAX_VALUE conversion");

        long count = IntStream.empty().mapToDouble(i -> i).count();
        check(count == 0L, "empty stream should yield count 0");

        double filtered = IntStream.range(0, 5)
                .filter(i -> i % 2 == 0)
                .mapToDouble(i -> i * 0.5)
                .sum();
        check(filtered == 3.0, "filtered and scaled sum should be 3.0");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
