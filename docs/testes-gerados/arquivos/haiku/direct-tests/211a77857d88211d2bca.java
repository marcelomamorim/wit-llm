/*
 * @test
 * @summary Verifies ReferencePipeline.skip(long) argument validation and observable element skipping through the public Stream API
 * @run main ReferencePipelineWitupTest
 */

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    private static int failures = 0;

    private static void check(boolean condition, String message) {
        if (!condition) {
            failures++;
            System.err.println("FAILED: " + message);
        }
    }

    public static void main(String[] args) {
        testSkipZeroReturnsSameStage();
        testSkipRemovesLeadingElements();
        testSkipBeyondSizeYieldsEmpty();
        testSkipMaxValueYieldsEmpty();
        testSkipOnEmptyStream();
        testChainedSkips();
        testNegativeSkipThrows();
        testNegativeMinValueMessage();

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }

    static void testSkipZeroReturnsSameStage() {
        Stream<Integer> s = Stream.of(1, 2, 3);
        check(s.skip(0) == s, "skip(0) should return this pipeline stage");
    }

    static void testSkipRemovesLeadingElements() {
        List<Integer> r = Stream.of(1, 2, 3, 4, 5).skip(2).collect(Collectors.toList());
        check(r.equals(Arrays.asList(3, 4, 5)), "skip(2) should drop first two elements, got " + r);
    }

    static void testSkipBeyondSizeYieldsEmpty() {
        List<Integer> r = Stream.of(1, 2).skip(5).collect(Collectors.toList());
        check(r.isEmpty(), "skip(5) on two elements should be empty, got " + r);
    }

    static void testSkipMaxValueYieldsEmpty() {
        List<Integer> r = Stream.of(1, 2, 3).skip(Long.MAX_VALUE).collect(Collectors.toList());
        check(r.isEmpty(), "skip(Long.MAX_VALUE) should be empty, got " + r);
    }

    static void testSkipOnEmptyStream() {
        List<Integer> r = Stream.<Integer>empty().skip(1).collect(Collectors.toList());
        check(r.isEmpty(), "skip(1) on empty stream should be empty, got " + r);
    }

    static void testChainedSkips() {
        List<Integer> r = Stream.of(1, 2, 3, 4, 5, 6).skip(1).skip(2).collect(Collectors.toList());
        check(r.equals(Arrays.asList(4, 5, 6)), "chained skip(1).skip(2) should yield [4, 5, 6], got " + r);
    }

    static void testNegativeSkipThrows() {
        boolean thrown = false;
        String message = null;
        try {
            Stream.of(1).skip(-1);
        } catch (IllegalArgumentException e) {
            thrown = true;
            message = e.getMessage();
        }
        check(thrown, "skip(-1) should throw IllegalArgumentException");
        check("-1".equals(message), "skip(-1) message should be \"-1\", got " + message);
    }

    static void testNegativeMinValueMessage() {
        boolean thrown = false;
        String message = null;
        try {
            Stream.of(1).skip(Long.MIN_VALUE);
        } catch (IllegalArgumentException e) {
            thrown = true;
            message = e.getMessage();
        }
        check(thrown, "skip(Long.MIN_VALUE) should throw IllegalArgumentException");
        check("-9223372036854775808".equals(message),
              "skip(Long.MIN_VALUE) message mismatch, got " + message);
    }
}
