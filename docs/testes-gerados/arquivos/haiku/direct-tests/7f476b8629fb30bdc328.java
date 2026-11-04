/*
 * @test
 * @summary Verifies LongPipeline.forEachOrdered through the public LongStream API
 * @run main LongPipelineWitupTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testSequentialOrder();
        testParallelOrder();
        testEmptyStream();
    }

    static void testSequentialOrder() {
        List<Long> seen = new ArrayList<>();
        LongStream.of(1L, 2L, 3L).forEachOrdered(seen::add);
        check(seen.equals(Arrays.asList(1L, 2L, 3L)),
              "sequential forEachOrdered must visit elements in encounter order: " + seen);
    }

    static void testParallelOrder() {
        List<Long> seen = new ArrayList<>();
        LongStream.range(0, 10000).parallel().forEachOrdered(seen::add);
        check(seen.size() == 10000, "parallel forEachOrdered must visit all elements, got " + seen.size());
        for (int i = 0; i < 10000; i++) {
            check(seen.get(i) == i, "parallel forEachOrdered out of order at index " + i + ": " + seen.get(i));
        }
    }

    static void testEmptyStream() {
        long[] count = new long[1];
        LongStream.empty().forEachOrdered(v -> count[0]++);
        check(count[0] == 0, "forEachOrdered on empty stream must not invoke action");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
