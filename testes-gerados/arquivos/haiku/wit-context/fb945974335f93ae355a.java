/*
 * @test
 * @summary Verify Stream.limit rejects negative sizes and keeps observable behavior for valid sizes
 * @run main ReferencePipelineWitupTest
 */

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ReferencePipelineWitupTest {

    private static int failures = 0;

    public static void main(String[] args) {
        testNegativeLimitThrowsIllegalArgumentException();
        testNegativeLimitMessageIsValue();
        testMinValueLimitThrows();
        testZeroLimitReturnsEmpty();
        testLimitLargerThanSizeReturnsAllElements();
        testLimitTruncatesToMaxSize();

        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }

    private static void testNegativeLimitThrowsIllegalArgumentException() {
        try {
            Stream.of(1, 2, 3).limit(-1L);
            fail("expected IllegalArgumentException for limit(-1)");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testNegativeLimitMessageIsValue() {
        try {
            Stream.of(1, 2, 3).limit(-5L);
            fail("expected IllegalArgumentException for limit(-5)");
        } catch (IllegalArgumentException e) {
            if (!"-5".equals(e.getMessage())) {
                fail("unexpected message: " + e.getMessage());
            }
        }
    }

    private static void testMinValueLimitThrows() {
        try {
            Stream.of(1, 2, 3).limit(Long.MIN_VALUE);
            fail("expected IllegalArgumentException for Long.MIN_VALUE");
        } catch (IllegalArgumentException expected) {
            // expected
        }
    }

    private static void testZeroLimitReturnsEmpty() {
        List<Integer> result = Stream.of(1, 2, 3).limit(0L).collect(java.util.stream.Collectors.toList());
        if (!result.isEmpty()) {
            fail("limit(0) should be empty, got " + result);
        }
    }

    private static void testLimitLargerThanSizeReturnsAllElements() {
        List<Integer> result = Stream.of(1, 2, 3).limit(10L).collect(java.util.stream.Collectors.toList());
        if (!Arrays.asList(1, 2, 3).equals(result)) {
            fail("limit(10) on 3 elements should return all, got " + result);
        }
    }

    private static void testLimitTruncatesToMaxSize() {
        List<Integer> result = Stream.of(1, 2, 3, 4).limit(2L).collect(java.util.stream.Collectors.toList());
        if (!Arrays.asList(1, 2).equals(result)) {
            fail("limit(2) should return first two, got " + result);
        }
    }

    private static void fail(String message) {
        failures++;
        System.err.println("FAIL: " + message);
    }
}
