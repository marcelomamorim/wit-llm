/*
 * @test
 * @summary Verifies DoublePipeline.accept via the public DoubleStream.mapToLong path: mapper applied per element, order kept, single invocation, parallel equivalence, exception propagation
 * @run main DoublePipelineWitupTest
 */
import java.util.Arrays;
import java.util.stream.DoubleStream;

public class DoublePipelineWitupTest {

    public static void main(String[] args) {
        testMapperAppliedToEachElement();
        testOrderPreserved();
        testMapperInvokedOncePerElement();
        testParallelMatchesSequential();
        testMapperExceptionPropagates();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testMapperAppliedToEachElement() {
        long[] result = DoubleStream.of(1.5, -2.7, 0.0)
                .mapToLong(d -> (long) Math.floor(d))
                .toArray();
        check(Arrays.equals(result, new long[] {1L, -3L, 0L}),
              "mapper result incorrect: " + Arrays.toString(result));
    }

    static void testOrderPreserved() {
        long[] result = DoubleStream.of(3.0, 1.0, 2.0)
                .mapToLong(d -> (long) (d * 10))
                .toArray();
        check(Arrays.equals(result, new long[] {30L, 10L, 20L}),
              "order not preserved: " + Arrays.toString(result));
    }

    static void testMapperInvokedOncePerElement() {
        long[] calls = {0L};
        long[] result = DoubleStream.of(1.0, 2.0, 3.0)
                .mapToLong(d -> {
                    calls[0]++;
                    return (long) d;
                })
                .toArray();
        check(calls[0] == 3L, "mapper invocation count: " + calls[0]);
        check(Arrays.equals(result, new long[] {1L, 2L, 3L}),
              "unexpected result: " + Arrays.toString(result));
    }

    static void testParallelMatchesSequential() {
        double[] input = {1.1, 2.2, 3.3, 4.4, 5.5, 6.6, 7.7, 8.8};
        long[] sequential = DoubleStream.of(input)
                .mapToLong(d -> (long) (d * 10))
                .toArray();
        long[] parallel = DoubleStream.of(input)
                .parallel()
                .mapToLong(d -> (long) (d * 10))
                .toArray();
        check(Arrays.equals(sequential, parallel),
              "parallel differs: " + Arrays.toString(parallel));
    }

    static void testMapperExceptionPropagates() {
        try {
            DoubleStream.of(1.0)
                    .mapToLong(d -> {
                        throw new IllegalStateException("boom");
                    })
                    .toArray();
            check(false, "exception from mapper was not propagated");
        } catch (IllegalStateException e) {
            check("boom".equals(e.getMessage()), "wrong message: " + e.getMessage());
        }
    }
}
