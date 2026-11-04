/*
 * @test
 * @summary Verifies observable behavior of LongPipeline.forEach through the public LongStream API
 * @run main LongPipelineWitupTest
 */

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

public class LongPipelineWitupTest {

    public static void main(String[] args) {
        testForEachSequentialOrder();
        testForEachAfterFilter();
        testForEachEmptyStream();
        testForEachAfterMap();
        testReuseThrowsIllegalState();
        System.out.println("PASS");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testForEachSequentialOrder() {
        List<Long> seen = new ArrayList<>();
        LongStream.of(3L, 1L, 2L).forEach(v -> seen.add(v));
        check(seen.size() == 3, "expected 3 elements");
        check(seen.get(0) == 3L, "first element out of order");
        check(seen.get(1) == 1L, "second element out of order");
        check(seen.get(2) == 2L, "third element out of order");
    }

    static void testForEachAfterFilter() {
        List<Long> seen = new ArrayList<>();
        LongStream.of(1L, 2L, 3L, 4L, 5L, 6L)
                  .filter(v -> v % 2 == 0)
                  .forEach(v -> seen.add(v));
        check(seen.size() == 3, "expected 3 even elements");
        check(seen.get(0) == 2L && seen.get(1) == 4L && seen.get(2) == 6L,
              "filtered elements incorrect");
    }

    static void testForEachAfterMap() {
        long[] sum = new long[1];
        LongStream.range(1L, 4L)
                  .map(v -> v * 10L)
                  .forEach(v -> sum[0] += v);
        check(sum[0] == 60L, "expected sum 60 but was " + sum[0]);
    }

    static void testForEachEmptyStream() {
        int[] calls = new int[1];
        LongStream.empty().forEach(v -> calls[0]++);
        check(calls[0] == 0, "action must not run for empty stream");
    }

    static void testReuseThrowsIllegalState() {
        LongStream s = LongStream.of(1L, 2L);
        s.forEach(v -> { });
        boolean thrown = false;
        try {
            s.forEach(v -> { });
        } catch (IllegalStateException e) {
            thrown = true;
        }
        check(thrown, "reusing a consumed stream must throw IllegalStateException");
    }
}
