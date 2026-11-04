/*
 * @test
 * @summary Tests the flatMap method of IntPipeline for correct behavior and exception handling.
 * @run main IntPipelineWitupTest
 */

import java.util.stream.IntStream;
import java.util.function.IntFunction;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testFlatMapWithValidMapper();
        testFlatMapWithNullMapper();
        testFlatMapWithMapperReturningNullStream();
    }

    static void testFlatMapWithValidMapper() {
        IntStream source = IntStream.of(1, 2, 3);
        IntFunction<? extends IntStream> mapper = t -> IntStream.of(t, t * 10);
        IntStream resultStream = source.flatMap(mapper);
        int[] result = resultStream.toArray();
        // Expected: [1, 10, 2, 20, 3, 30]
        if (result.length != 6) {
            throw new AssertionError("Expected length 6, but got " + result.length);
        }
        if (result[0] != 1 || result[1] != 10 || result[2] != 2 || result[3] != 20 || result[4] != 3 || result[5] != 30) {
            throw new AssertionError("Unexpected stream contents: " + java.util.Arrays.toString(result));
        }
    }

    static void testFlatMapWithNullMapper() {
        IntStream source = IntStream.of(1, 2, 3);
        try {
            source.flatMap(null);
            throw new AssertionError("Expected NullPointerException for null mapper");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testFlatMapWithMapperReturningNullStream() {
        IntStream source = IntStream.of(1, 2);
        IntFunction<? extends IntStream> mapper = t -> null;
        try (IntStream s = source.flatMap(mapper)) {
            s.toArray();
            throw new AssertionError("Expected NullPointerException when mapper returns null stream");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
