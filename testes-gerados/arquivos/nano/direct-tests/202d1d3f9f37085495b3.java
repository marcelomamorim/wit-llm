/*
 * @test
 * @summary Tests the mapToInt method of LongPipeline for correct behavior.
 * @run main LongPipelineWitupTest
 */
import java.util.stream.LongStream;
import java.util.stream.IntStream;
import java.util.function.LongToIntFunction;

public class LongPipelineWitupTest {
    public static void main(String[] args) {
        testMapToIntWithValidFunction();
        testMapToIntWithNullFunction();
    }

    static void testMapToIntWithValidFunction() {
        LongStream longStream = LongStream.of(1L, 2L, 3L);
        LongToIntFunction mapper = t -> (int) (t * 10);
        IntStream result = longStream.mapToInt(mapper);
        int[] expected = {10, 20, 30};
        int index = 0;
        for (int val : result.toArray()) {
            if (val != expected[index]) {
                throw new AssertionError("Expected " + expected[index] + " but got " + val);
            }
            index++;
        }
    }

    static void testMapToIntWithNullFunction() {
        try {
            LongStream longStream = LongStream.of(1L, 2L);
            longStream.mapToInt(null);
            throw new AssertionError("Expected NullPointerException for null mapper");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
