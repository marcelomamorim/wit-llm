/*
 * @test
 * @summary DoublePipeline.mapToInt applies the mapper lazily, preserves element order and rejects a null mapper
 * @run main DoublePipelineWitupTest
 */
import java.util.Arrays;
import java.util.stream.DoubleStream;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testMapping();
        testEmpty();
        testNullMapper();
        testLazy();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testMapping() {
        int[] result = DoubleStream.of(1.9, -2.5, 3.0)
                .mapToInt(d -> (int) d)
                .toArray();
        check(Arrays.equals(result, new int[]{1, -2, 3}),
              "mapping produced " + Arrays.toString(result));
    }

    static void testEmpty() {
        long count = DoubleStream.empty()
                .mapToInt(d -> (int) d)
                .count();
        check(count == 0L, "empty stream count was " + count);
    }

    static void testNullMapper() {
        try {
            DoubleStream.of(1.0).mapToInt(null);
            throw new RuntimeException("expected NullPointerException for null mapper");
        } catch (NullPointerException expected) {
            // expected: Objects.requireNonNull(mapper)
        }
    }

    static void testLazy() {
        final int[] calls = {0};
        java.util.stream.IntStream s = DoubleStream.of(1.0, 2.0)
                .mapToInt(d -> {
                    calls[0]++;
                    return (int) d;
                });
        check(calls[0] == 0, "mapper invoked before terminal operation: " + calls[0]);
        int total = s.sum();
        check(calls[0] == 2, "mapper invoked " + calls[0] + " times");
        check(total == 3, "sum was " + total);
    }
}
