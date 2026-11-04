/*
 * @test
 * @summary Verifies that LongPipeline's map stage (Sink accept) applies the mapper to each element and forwards the result downstream
 * @run main LongPipelineWitupTest
 */
import java.util.Arrays;
import java.util.stream.LongStream;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testMapAppliesMapper();
        testMapPreservesOrder();
        testMapEmptyStream();
        testMapCalledOncePerElement();
        testMapChainedWithFilter();
        testMapExceptionPropagates();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testMapAppliesMapper() {
        long[] result = LongStream.of(1L, 2L, 3L).map(x -> x * 2L).toArray();
        check(Arrays.equals(result, new long[] {2L, 4L, 6L}),
              "map should double each element, got " + Arrays.toString(result));
    }

    static void testMapPreservesOrder() {
        long[] result = LongStream.of(5L, -1L, 10L, 0L).map(x -> x + 100L).toArray();
        check(Arrays.equals(result, new long[] {105L, 99L, 110L, 100L}),
              "map should preserve encounter order, got " + Arrays.toString(result));
    }

    static void testMapEmptyStream() {
        long[] result = LongStream.empty().map(x -> x + 1L).toArray();
        check(result.length == 0, "map on empty stream should produce no elements");
    }

    static void testMapCalledOncePerElement() {
        final long[] calls = {0L};
        long[] result = LongStream.of(7L, 8L, 9L).map(x -> {
            calls[0]++;
            return x;
        }).toArray();
        check(calls[0] == 3L, "mapper should be invoked once per element, calls=" + calls[0]);
        check(result.length == 3, "map should emit all elements");
    }

    static void testMapChainedWithFilter() {
        long[] result = LongStream.rangeClosed(1L, 5L)
                .map(x -> x * x)
                .filter(x -> x % 2L == 1L)
                .toArray();
        check(Arrays.equals(result, new long[] {1L, 9L, 25L}),
              "map then filter should yield [1, 9, 25], got " + Arrays.toString(result));
    }

    static void testMapExceptionPropagates() {
        boolean thrown = false;
        try {
            LongStream.of(1L).map(x -> {
                throw new IllegalStateException("boom");
            }).toArray();
        } catch (IllegalStateException e) {
            thrown = "boom".equals(e.getMessage());
        }
        check(thrown, "exception thrown by mapper should propagate unchanged");
    }
}
