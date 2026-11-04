/*
 * @test
 * @summary Tests IntStream.map null-mapper rejection and mapping behavior through the public API
 * @run main IntPipelineWitupTest
 */
import java.util.Arrays;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testNullMapperThrowsNPE();
        testMapTransformsElements();
        testMapOnEmptyStream();
    }

    static void testNullMapperThrowsNPE() {
        IntUnaryOperator nullMapper = null;
        try {
            IntStream.of(1, 2, 3).map(nullMapper);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null mapper");
    }

    static void testMapTransformsElements() {
        IntUnaryOperator doubler = x -> x * 2;
        int[] result = IntStream.of(1, 2, 3).map(doubler).toArray();
        int[] expected = {2, 4, 6};
        if (!Arrays.equals(result, expected)) {
            throw new RuntimeException("unexpected map result: " + Arrays.toString(result));
        }
    }

    static void testMapOnEmptyStream() {
        IntUnaryOperator inc = x -> x + 1;
        long count = IntStream.empty().map(inc).count();
        if (count != 0) {
            throw new RuntimeException("expected empty result, got count " + count);
        }
    }
}
