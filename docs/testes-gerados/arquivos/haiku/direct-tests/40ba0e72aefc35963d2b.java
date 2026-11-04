/*
 * @test
 * @summary Verify IntStream.mapToLong applies the mapper to each element and rejects a null mapper
 * @run main IntPipelineWitupTest
 */
import java.util.Arrays;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {
    public static void main(String[] args) {
        long[] result = IntStream.of(1, 2, 3)
                .mapToLong(i -> (long) i * 10000000000L)
                .toArray();
        long[] expected = {10000000000L, 20000000000L, 30000000000L};
        if (!Arrays.equals(result, expected)) {
            throw new RuntimeException("unexpected mapping result: " + Arrays.toString(result));
        }

        long sum = IntStream.range(0, 0)
                .mapToLong(i -> (long) i)
                .sum();
        if (sum != 0L) {
            throw new RuntimeException("empty stream sum expected 0 but was " + sum);
        }

        boolean npeThrown = false;
        try {
            IntStream.of(1).mapToLong(null);
        } catch (NullPointerException e) {
            npeThrown = true;
        }
        if (!npeThrown) {
            throw new RuntimeException("expected NullPointerException for null mapper");
        }
    }
}
