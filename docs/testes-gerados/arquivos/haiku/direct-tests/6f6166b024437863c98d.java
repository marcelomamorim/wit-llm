/*
 * @test
 * @summary Verify DoublePipeline.map applies the mapper lazily, in order, and rejects a null mapper
 * @run main DoublePipelineWitupTest
 */
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.DoubleStream;

public class DoublePipelineWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }

    public static void main(String[] args) {
        double[] mapped = DoubleStream.of(1.0, 2.0, 3.0).map(x -> x * 2).toArray();
        check(Arrays.equals(mapped, new double[]{2.0, 4.0, 6.0}), "map should double each element");

        double[] negated = DoubleStream.of(0.5, -1.5).map(x -> -x).toArray();
        check(Arrays.equals(negated, new double[]{-0.5, 1.5}), "map should negate each element");

        AtomicInteger calls = new AtomicInteger();
        DoubleStream stream = DoubleStream.of(1.0, 2.0).map(x -> {
            calls.incrementAndGet();
            return x;
        });
        check(calls.get() == 0, "map should be lazy before a terminal operation");
        double sum = stream.sum();
        check(calls.get() == 2, "mapper should be invoked once per element on terminal operation");
        check(sum == 3.0, "sum after identity map should be 3.0");

        double[] chained = DoubleStream.of(1.0).map(x -> x + 1).map(x -> x * 10).toArray();
        check(Arrays.equals(chained, new double[]{20.0}), "chained maps should apply in order");

        double[] ordered = DoubleStream.of(1.0, 2.0, 3.0).map(x -> x * x).toArray();
        check(Arrays.equals(ordered, new double[]{1.0, 4.0, 9.0}), "map should preserve encounter order");

        boolean npe = false;
        try {
            DoubleStream.of(1.0).map(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "map(null) should throw NullPointerException");
    }
}
