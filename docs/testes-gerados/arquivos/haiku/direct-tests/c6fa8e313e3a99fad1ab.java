/*
 * @test
 * @summary Verifies IntPipeline.forEachOrdered visits elements in encounter order through the public IntStream API
 * @run main IntPipelineWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testSequentialOrder() {
        List<Integer> seen = new ArrayList<>();
        IntStream.of(1, 2, 3, 4, 5).forEachOrdered(seen::add);
        check(seen.equals(Arrays.asList(1, 2, 3, 4, 5)),
              "sequential order mismatch: " + seen);
    }

    static void testEmptyStream() {
        List<Integer> seen = new ArrayList<>();
        IntStream.empty().forEachOrdered(seen::add);
        check(seen.isEmpty(), "empty stream produced elements: " + seen);
    }

    static void testIntermediateOperations() {
        List<Integer> seen = new ArrayList<>();
        IntStream.range(0, 10)
                 .map(x -> x * 2)
                 .filter(x -> x % 4 == 0)
                 .forEachOrdered(seen::add);
        check(seen.equals(Arrays.asList(0, 4, 8, 12, 16)),
              "pipeline result mismatch: " + seen);
    }

    static void testParallelPreservesEncounterOrder() {
        List<Integer> seen = new ArrayList<>();
        IntStream.range(0, 1000).parallel().forEachOrdered(seen::add);
        check(seen.size() == 1000, "unexpected size: " + seen.size());
        for (int i = 0; i < 1000; i++) {
            check(seen.get(i) == i, "parallel order mismatch at " + i + ": " + seen.get(i));
        }
    }

    public static void main(String[] args) {
        testSequentialOrder();
        testEmptyStream();
        testIntermediateOperations();
        testParallelPreservesEncounterOrder();
    }
}
