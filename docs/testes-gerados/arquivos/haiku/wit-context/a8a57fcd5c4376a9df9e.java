/*
 * @test
 * @summary Verifies LongStream.mapToDouble rejects a null mapper and maps values correctly
 * @run main LongPipelineWitupTest
 */
import java.util.Arrays;
import java.util.stream.LongStream;

public class LongPipelineWitupTest {
    public static void main(String[] args) {
        testNullMapperThrowsNPE();
        testMappingResults();
    }

    static void testNullMapperThrowsNPE() {
        try {
            LongStream.of(1L, 2L).mapToDouble(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null mapper");
    }

    static void testMappingResults() {
        double[] result = LongStream.of(1L, 2L, 3L)
                .mapToDouble(x -> x * 0.5d)
                .toArray();
        double[] expected = {0.5d, 1.0d, 1.5d};
        if (!Arrays.equals(result, expected)) {
            throw new RuntimeException("unexpected mapping result: " + Arrays.toString(result));
        }
    }
}
