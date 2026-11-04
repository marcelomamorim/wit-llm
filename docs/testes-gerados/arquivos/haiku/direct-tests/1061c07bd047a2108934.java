/*
 * @test
 * @summary Verify that DoubleStream.mapToInt, which runs DoublePipeline.accept(double), maps each element once, in order, with the given mapper
 * @run main DoublePipelineWitupTest
 */
import java.util.Arrays;
import java.util.stream.DoubleStream;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        int[] result = DoubleStream.of(1.5, 2.7, -3.9)
                .mapToInt(d -> (int) d)
                .toArray();
        check(Arrays.equals(new int[]{1, 2, -3}, result),
              "mapToInt values/order mismatch: " + Arrays.toString(result));

        int[] calls = {0};
        int sum = DoubleStream.of(0.0, 1.0, 2.0)
                .mapToInt(d -> {
                    calls[0]++;
                    return (int) d;
                })
                .sum();
        check(sum == 3, "sum mismatch: " + sum);
        check(calls[0] == 3, "mapper invocation count mismatch: " + calls[0]);

        int[] empty = DoubleStream.empty()
                .mapToInt(d -> (int) d)
                .toArray();
        check(empty.length == 0, "empty stream should produce no elements");

        int[] truncated = DoubleStream.of(-0.5, 0.5)
                .mapToInt(d -> (int) d)
                .toArray();
        check(Arrays.equals(new int[]{0, 0}, truncated),
              "truncation mismatch: " + Arrays.toString(truncated));
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
