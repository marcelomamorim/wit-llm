/*
 * @test
 * @summary Verifies IntPipeline.map applies the mapper lazily, preserves order, and rejects a null mapper
 * @run main IntPipelineWitupTest
 */

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.function.IntUnaryOperator;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testMapAppliesFunctionInOrder();
        testMapOnEmptyStream();
        testMapIsLazy();
        testMapNullMapperThrowsNPE();
    }

    static void testMapAppliesFunctionInOrder() {
        int[] result = IntStream.of(1, 2, 3).map(x -> x * 2).toArray();
        int[] expected = {2, 4, 6};
        if (!Arrays.equals(expected, result)) {
            throw new RuntimeException("Unexpected result: " + Arrays.toString(result));
        }
    }

    static void testMapOnEmptyStream() {
        int[] result = IntStream.empty().map(x -> x + 1).toArray();
        if (result.length != 0) {
            throw new RuntimeException("Expected empty result but got length " + result.length);
        }
    }

    static void testMapIsLazy() {
        int[] calls = {0};
        IntStream s = IntStream.of(1, 2).map(x -> {
            calls[0]++;
            return x;
        });
        if (calls[0] != 0) {
            throw new RuntimeException("Mapper invoked before terminal operation");
        }
        long sum = s.sum();
        if (sum != 3 || calls[0] != 2) {
            throw new RuntimeException("Expected sum 3 and 2 invocations, got sum " + sum
                    + " and " + calls[0] + " invocations");
        }
    }

    static void testMapNullMapperThrowsNPE() {
        IntUnaryOperator nullMapper = null;
        try {
            IntStream.of(1).map(nullMapper);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null mapper");
    }
}
