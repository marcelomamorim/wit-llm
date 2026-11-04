/*
 * @test
 * @summary Witup-derived checks for LongStream.mapToInt null handling and mapping behavior
 * @run main LongPipelineWitupTest
 */

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class LongPipelineWitupTest {
    public static void main(String[] args) {
        try {
            LongStream.of(1L).mapToInt(null);
            throw new AssertionError("NullPointerException expected for null mapper");
        } catch (NullPointerException expected) {
        }

        int[] mapped = LongStream.of(1L, 2L, 3L).mapToInt(x -> (int) (x * 10)).toArray();
        if (!Arrays.equals(mapped, new int[] {10, 20, 30})) {
            throw new AssertionError("Unexpected mapped values: " + Arrays.toString(mapped));
        }

        int[] empty = LongStream.empty().mapToInt(x -> 1).toArray();
        if (empty.length != 0) {
            throw new AssertionError("Expected empty result for empty source");
        }

        IntStream stream = LongStream.of(5L).mapToInt(x -> (int) x + 1);
        if (stream.sum() != 6) {
            throw new AssertionError("Unexpected sum for single mapped element");
        }
    }
}
