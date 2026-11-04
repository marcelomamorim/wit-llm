/*
 * @test
 * @summary Verifies IntPipeline.mapToObj(IntFunction) maps elements in order and rejects a null mapper
 * @run main IntPipelineWitupTest
 */

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IntPipelineWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAIL: " + message);
        }
    }

    public static void main(String[] args) {
        List<String> mapped = IntStream.of(1, 2, 3)
                .mapToObj(i -> "v" + i)
                .collect(Collectors.toList());
        check(mapped.equals(Arrays.asList("v1", "v2", "v3")),
              "mapToObj should map each element in order, got " + mapped);

        long emptyCount = IntStream.empty()
                .mapToObj(i -> "x")
                .count();
        check(emptyCount == 0L, "mapToObj on empty stream should yield no elements");

        List<Integer> parallelMapped = IntStream.range(0, 100)
                .parallel()
                .mapToObj(i -> Integer.valueOf(i * 2))
                .collect(Collectors.toList());
        boolean ordered = parallelMapped.size() == 100;
        for (int i = 0; ordered && i < 100; i++) {
            ordered = parallelMapped.get(i).intValue() == i * 2;
        }
        check(ordered, "parallel mapToObj should preserve encounter order");

        boolean npe = false;
        try {
            IntStream.of(1).mapToObj(null);
        } catch (NullPointerException e) {
            npe = true;
        }
        check(npe, "mapToObj(null) should throw NullPointerException");

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
