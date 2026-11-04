/*
 * @test
 * @summary Verifies IntPipeline map stage (IntPipeline.accept sink) applies the mapper to each element in order and passes results downstream
 * @run main IntPipelineWitupTest
 */

import java.util.Arrays;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {

    public static void main(String[] args) {
        testMapAppliesFunctionToEachElement();
        testMapInvokesMapperInEncounterOrder();
        testMapOnEmptyStreamDoesNotInvokeMapper();
        testChainedMapsCompose();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void checkArray(int[] actual, int[] expected, String message) {
        check(Arrays.equals(actual, expected),
              message + " expected=" + Arrays.toString(expected)
              + " actual=" + Arrays.toString(actual));
    }

    static void testMapAppliesFunctionToEachElement() {
        int[] result = IntStream.of(1, 2, 3)
                                .map(x -> x * 2)
                                .toArray();
        checkArray(result, new int[] {2, 4, 6}, "map did not apply mapper to each element");
    }

    static void testMapInvokesMapperInEncounterOrder() {
        final int[] seen = new int[3];
        final int[] pos = {0};
        int[] result = IntStream.of(5, 6, 7)
                                .map(x -> {
                                    seen[pos[0]++] = x;
                                    return x + 100;
                                })
                                .toArray();
        checkArray(seen, new int[] {5, 6, 7}, "mapper not invoked in encounter order");
        checkArray(result, new int[] {105, 106, 107}, "mapped values not forwarded downstream");
    }

    static void testMapOnEmptyStreamDoesNotInvokeMapper() {
        final int[] count = {0};
        long sum = IntStream.empty()
                            .map(x -> {
                                count[0]++;
                                return x;
                            })
                            .sum();
        check(count[0] == 0, "mapper invoked on empty stream: " + count[0]);
        check(sum == 0L, "sum of empty mapped stream should be 0 but was " + sum);
    }

    static void testChainedMapsCompose() {
        int[] result = IntStream.of(1, 2)
                                .map(x -> x + 1)
                                .map(x -> x * 3)
                                .toArray();
        checkArray(result, new int[] {6, 9}, "chained map stages did not compose correctly");
    }
}
