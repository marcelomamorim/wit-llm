/*
 * @test
 * @summary Verify DoublePipeline.mapToLong behavior through the public DoubleStream API
 * @run main DoublePipelineWitupTest
 */
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.DoubleStream;
import java.util.stream.LongStream;

public class DoublePipelineWitupTest {
    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }

    public static void main(String[] args) {
        long[] mapped = DoubleStream.of(1.5, 2.7, -3.9).mapToLong(d -> (long) d).toArray();
        check(Arrays.equals(mapped, new long[]{1L, 2L, -3L}),
              "basic mapping: " + Arrays.toString(mapped));

        long[] empty = DoubleStream.empty().mapToLong(d -> 42L).toArray();
        check(empty.length == 0, "empty stream must yield empty result");

        long total = DoubleStream.of(1.0, 2.0, 3.0).mapToLong(d -> (long) d * 10).sum();
        check(total == 60L, "sum of mapped values: " + total);

        AtomicInteger calls = new AtomicInteger();
        LongStream lazy = DoubleStream.of(1.0, 2.0).mapToLong(d -> {
            calls.incrementAndGet();
            return (long) d;
        });
        check(calls.get() == 0, "mapper must not run before terminal operation");
        long lazySum = lazy.sum();
        check(lazySum == 3L, "lazy sum: " + lazySum);
        check(calls.get() == 2, "mapper invocations: " + calls.get());

        boolean thrown = false;
        try {
            DoubleStream.of(1.0).mapToLong(null);
        } catch (NullPointerException expected) {
            thrown = true;
        }
        check(thrown, "null mapper must throw NullPointerException");
    }
}
