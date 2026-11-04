/*
 * @test
 * @summary Verifies that DoublePipeline mapToInt stage applies the mapper once per element and forwards the mapped values downstream in order
 * @run main DoublePipelineWitupTest
 */
import java.util.Arrays;
import java.util.stream.DoubleStream;

public class DoublePipelineWitupTest {
    public static void main(String[] args) {
        int[] calls = {0};
        int[] result = DoubleStream.of(1.5, 2.7, -3.2)
                .mapToInt(d -> { calls[0]++; return (int) d; })
                .toArray();
        check(Arrays.equals(result, new int[]{1, 2, -3}), "mapped values mismatch: " + Arrays.toString(result));
        check(calls[0] == 3, "mapper invocation count expected 3 but was " + calls[0]);

        calls[0] = 0;
        long count = DoubleStream.empty()
                .mapToInt(d -> { calls[0]++; return (int) d; })
                .count();
        check(count == 0, "empty stream count expected 0 but was " + count);
        check(calls[0] == 0, "mapper must not run for empty stream");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
