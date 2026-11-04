/*
 * @test
 * @summary Behavioral tests for LongPipeline.findFirst() through the public LongStream API
 * @run main LongPipelineWitupTest
 */

import java.util.OptionalLong;
import java.util.stream.LongStream;

public class LongPipelineWitupTest {

    private static int failures = 0;

    private static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    public static void main(String[] args) {
        testFindFirstReturnsFirstElement();
        testFindFirstOnEmptyStreamIsEmpty();
        testFindFirstAfterFilter();
        testFindFirstWithMinValue();
        testFindFirstOnInfiniteStreamTerminates();
        testFindFirstAfterMap();
        testFindFirstParallelRespectsEncounterOrder();
        testFindFirstShortCircuits();

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }

    static void testFindFirstReturnsFirstElement() {
        OptionalLong r = LongStream.of(5L, 7L).findFirst();
        check(r.isPresent(), "findFirst on non-empty stream should be present");
        check(r.getAsLong() == 5L, "findFirst should return first element 5, got " + r);
        check(r.equals(OptionalLong.of(5L)), "result should equal OptionalLong.of(5)");
    }

    static void testFindFirstOnEmptyStreamIsEmpty() {
        OptionalLong r = LongStream.empty().findFirst();
        check(!r.isPresent(), "findFirst on empty stream should be empty");
        check(r.equals(OptionalLong.empty()), "result should equal OptionalLong.empty()");
    }

    static void testFindFirstAfterFilter() {
        OptionalLong r = LongStream.of(1L, 2L, 3L).filter(x -> x > 1L).findFirst();
        check(r.isPresent() && r.getAsLong() == 2L, "findFirst after filter should be 2, got " + r);
    }

    static void testFindFirstWithMinValue() {
        OptionalLong r = LongStream.of(Long.MIN_VALUE).findFirst();
        check(r.isPresent() && r.getAsLong() == Long.MIN_VALUE,
              "findFirst should preserve Long.MIN_VALUE, got " + r);
    }

    static void testFindFirstOnInfiniteStreamTerminates() {
        OptionalLong r = LongStream.iterate(1L, x -> x + 1L).findFirst();
        check(r.isPresent() && r.getAsLong() == 1L,
              "findFirst on infinite stream should return seed 1, got " + r);
    }

    static void testFindFirstAfterMap() {
        OptionalLong r = LongStream.range(0L, 10L).map(x -> x * 2L).findFirst();
        check(r.isPresent() && r.getAsLong() == 0L, "findFirst after map should be 0, got " + r);
    }

    static void testFindFirstParallelRespectsEncounterOrder() {
        OptionalLong r = LongStream.range(0L, 1000L).parallel().findFirst();
        check(r.isPresent() && r.getAsLong() == 0L,
              "ordered parallel findFirst should return 0, got " + r);
    }

    static void testFindFirstShortCircuits() {
        final long[] consumed = {0L};
        OptionalLong r = LongStream.of(1L, 2L, 3L)
                .peek(x -> consumed[0]++)
                .findFirst();
        check(r.isPresent() && r.getAsLong() == 1L, "findFirst should return 1, got " + r);
        check(consumed[0] == 1L,
              "findFirst should consume only one element, consumed " + consumed[0]);
    }
}
