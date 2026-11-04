/*
 * @test
 * @summary Test LongPipeline.flatMap(LongFunction) behavior
 * @run main LongPipelineWitupTest
 */

import java.util.stream.LongStream;
import java.util.function.LongFunction;
import java.util.Objects;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testFlatMapWithNullMapper();
        testFlatMapWithEmptyStream();
        testFlatMapWithIdentityMapper();
        testFlatMapWithCancellationRequested();
    }

    static void testFlatMapWithNullMapper() {
        LongStream stream = LongStream.of(1, 2, 3);
        try {
            stream.flatMap(null);
            throw new AssertionError("Expected NullPointerException for null mapper");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testFlatMapWithEmptyStream() {
        LongStream stream = LongStream.empty();
        LongStream result = stream.flatMap(new LongFunction<LongStream>() {
            public LongStream apply(long value) {
                return LongStream.of(value + 1);
            }
        });
        long count = result.count();
        if (count != 0) {
            throw new AssertionError("Expected empty result stream, got count=" + count);
        }
    }

    static void testFlatMapWithIdentityMapper() {
        LongStream stream = LongStream.of(1, 2, 3);
        LongStream result = stream.flatMap(new LongFunction<LongStream>() {
            public LongStream apply(long value) {
                return LongStream.of(value);
            }
        });
        long[] expected = {1, 2, 3};
        long[] actual = result.toArray();
        if (actual.length != expected.length) {
            throw new AssertionError("Result length mismatch");
        }
        for (int i = 0; i < expected.length; i++) {
            if (actual[i] != expected[i]) {
                throw new AssertionError("Mismatch at index " + i + ": expected " + expected[i] + ", got " + actual[i]);
            }
        }
    }

    static void testFlatMapWithCancellationRequested() {
        // This test attempts to exercise the cancellationRequested path.
        // Since cancellationRequested is internal, we simulate by using findFirst which short-circuits.
        LongStream stream = LongStream.of(1, 2, 3, 4, 5);
        LongStream result = stream.flatMap(new LongFunction<LongStream>() {
            public LongStream apply(long value) {
                return LongStream.of(value, value * 10);
            }
        });
        // findFirst triggers short-circuit and cancellationRequested
        long first = result.findFirst().orElse(-1);
        if (first != 1) {
            throw new AssertionError("Expected first element 1, got " + first);
        }
    }
}
