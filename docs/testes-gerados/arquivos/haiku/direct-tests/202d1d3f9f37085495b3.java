/*
 * @test
 * @summary LongPipeline.mapToInt applies the mapper, preserves order, is lazy and rejects a null mapper
 * @run main LongPipelineWitupTest
 */
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class LongPipelineWitupTest {
    public static void main(String[] args) {
        testNullMapperRejected();
        testMapping();
        testEmptyStream();
        testLaziness();
        testTruncationByMapper();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullMapperRejected() {
        try {
            LongStream.of(1L).mapToInt(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null mapper");
    }

    static void testMapping() {
        int[] result = LongStream.of(1L, 2L, 3L)
                .mapToInt(l -> (int) (l * 10))
                .toArray();
        check(result.length == 3, "unexpected length");
        check(result[0] == 10 && result[1] == 20 && result[2] == 30,
              "unexpected mapped values or order");
    }

    static void testEmptyStream() {
        int[] result = LongStream.empty()
                .mapToInt(l -> (int) l)
                .toArray();
        check(result.length == 0, "empty stream should produce no elements");
    }

    static void testLaziness() {
        AtomicInteger calls = new AtomicInteger();
        IntStream stream = LongStream.of(1L, 2L)
                .mapToInt(l -> {
                    calls.incrementAndGet();
                    return (int) l;
                });
        check(calls.get() == 0, "mapper should not run before a terminal operation");
        int sum = stream.sum();
        check(calls.get() == 2, "mapper should run once per element");
        check(sum == 3, "unexpected sum of mapped values");
    }

    static void testTruncationByMapper() {
        int[] result = LongStream.of(0x100000001L)
                .mapToInt(l -> (int) l)
                .toArray();
        check(result.length == 1 && result[0] == 1, "mapper result should be used as is");
    }
}
