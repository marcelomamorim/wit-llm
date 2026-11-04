/*
 * @test
 * @summary Verifies ReferencePipeline.limit(long) argument validation and prefix semantics through the public Stream API
 * @run main ReferencePipelineWitupTest
 */

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    static int failures = 0;

    static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    static void testNegativeLimitThrows() {
        try {
            Stream.of(1, 2, 3).limit(-1);
            check(false, "limit(-1) should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            check("-1".equals(e.getMessage()),
                  "message should be \"-1\" but was \"" + e.getMessage() + "\"");
        }
    }

    static void testNegativeLargeLimitThrows() {
        try {
            Stream.of(1).limit(Long.MIN_VALUE);
            check(false, "limit(Long.MIN_VALUE) should throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            check(e.getMessage().equals(Long.toString(Long.MIN_VALUE)),
                  "unexpected message for Long.MIN_VALUE: " + e.getMessage());
        }
    }

    static void testZeroLimitIsEmpty() {
        List<Integer> result = Stream.of(1, 2, 3).limit(0).collect(Collectors.toList());
        check(result.isEmpty(), "limit(0) should produce an empty stream");
    }

    static void testLimitTakesPrefix() {
        List<Integer> result = Stream.of(1, 2, 3, 4, 5).limit(3).collect(Collectors.toList());
        check(result.equals(Arrays.asList(1, 2, 3)),
              "limit(3) should keep first three elements but was " + result);
    }

    static void testLimitLargerThanSize() {
        List<Integer> result = Stream.of(1, 2).limit(10).collect(Collectors.toList());
        check(result.equals(Arrays.asList(1, 2)),
              "limit(10) on two elements should keep both but was " + result);
    }

    static void testLimitOnInfiniteStream() {
        List<Integer> result = Stream.iterate(1, x -> x + 1).limit(4).collect(Collectors.toList());
        check(result.equals(Arrays.asList(1, 2, 3, 4)),
              "limit(4) on infinite stream should yield 1..4 but was " + result);
    }

    static void testLimitMaxValueAccepted() {
        long count = Stream.of(1, 2, 3).limit(Long.MAX_VALUE).count();
        check(count == 3L, "limit(Long.MAX_VALUE) should keep all elements but count was " + count);
    }

    static void testLimitAfterFilter() {
        List<Integer> result = Stream.of(1, 2, 3, 4, 5, 6)
                .filter(x -> x % 2 == 0)
                .limit(2)
                .collect(Collectors.toList());
        check(result.equals(Arrays.asList(2, 4)),
              "filter then limit(2) should yield [2, 4] but was " + result);
    }

    public static void main(String[] args) {
        testNegativeLimitThrows();
        testNegativeLargeLimitThrows();
        testZeroLimitIsEmpty();
        testLimitTakesPrefix();
        testLimitLargerThanSize();
        testLimitOnInfiniteStream();
        testLimitMaxValueAccepted();
        testLimitAfterFilter();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
