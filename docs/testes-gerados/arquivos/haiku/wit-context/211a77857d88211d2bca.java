/*
 * @test
 * @summary Verify Stream.skip rejects negative counts and skips elements correctly
 * @run main ReferencePipelineWitupTest
 */

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    public static void main(String[] args) {
        testNegativeCountThrows();
        testNegativeMinValueThrows();
        testZeroReturnsAllElements();
        testSkipCountRemovesPrefix();
        testSkipBeyondSizeReturnsEmpty();
        testSkipMaxValueReturnsEmpty();
    }

    static void testNegativeCountThrows() {
        try {
            Stream.of(1, 2, 3).skip(-1);
            throw new RuntimeException("Expected IllegalArgumentException for skip(-1)");
        } catch (IllegalArgumentException e) {
            check("-1".equals(e.getMessage()), "Unexpected message for skip(-1): " + e.getMessage());
        }
    }

    static void testNegativeMinValueThrows() {
        try {
            Stream.of(1).skip(Long.MIN_VALUE);
            throw new RuntimeException("Expected IllegalArgumentException for skip(Long.MIN_VALUE)");
        } catch (IllegalArgumentException e) {
            check(Long.toString(Long.MIN_VALUE).equals(e.getMessage()),
                  "Unexpected message: " + e.getMessage());
        }
    }

    static void testZeroReturnsAllElements() {
        List<Integer> result = Stream.of(1, 2, 3).skip(0).collect(Collectors.toList());
        check(result.equals(Arrays.asList(1, 2, 3)), "skip(0) changed elements: " + result);
    }

    static void testSkipCountRemovesPrefix() {
        List<Integer> result = Stream.of(1, 2, 3, 4).skip(2).collect(Collectors.toList());
        check(result.equals(Arrays.asList(3, 4)), "skip(2) returned: " + result);
    }

    static void testSkipBeyondSizeReturnsEmpty() {
        List<Integer> result = Stream.of(1, 2).skip(5).collect(Collectors.toList());
        check(result.isEmpty(), "skip(5) on size 2 returned: " + result);
    }

    static void testSkipMaxValueReturnsEmpty() {
        List<Integer> result = Stream.of(1, 2, 3).skip(Long.MAX_VALUE).collect(Collectors.toList());
        check(result.isEmpty(), "skip(Long.MAX_VALUE) returned: " + result);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
