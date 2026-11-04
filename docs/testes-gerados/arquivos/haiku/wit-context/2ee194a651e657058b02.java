/*
 * @test
 * @summary Verify Math.subtractExact(long,long) returns correct results and throws ArithmeticException on overflow
 * @run main MathWitupTest
 */

public class MathWitupTest {

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNormalResults() {
        check(Math.subtractExact(5L, 3L) == 2L, "5 - 3 should be 2");
        check(Math.subtractExact(0L, 0L) == 0L, "0 - 0 should be 0");
        check(Math.subtractExact(-7L, -10L) == 3L, "-7 - (-10) should be 3");
        check(Math.subtractExact(Long.MAX_VALUE, 0L) == Long.MAX_VALUE, "MAX - 0 should be MAX");
        check(Math.subtractExact(Long.MIN_VALUE, 0L) == Long.MIN_VALUE, "MIN - 0 should be MIN");
        check(Math.subtractExact(Long.MAX_VALUE, Long.MAX_VALUE) == 0L, "MAX - MAX should be 0");
        check(Math.subtractExact(-1L, Long.MAX_VALUE) == Long.MIN_VALUE, "-1 - MAX should be MIN");
    }

    static void testOverflowThrows(long x, long y) {
        boolean thrown = false;
        try {
            Math.subtractExact(x, y);
        } catch (ArithmeticException e) {
            thrown = true;
        }
        check(thrown, "expected ArithmeticException for " + x + " - " + y);
    }

    static void testOverflowCases() {
        testOverflowThrows(Long.MIN_VALUE, 1L);
        testOverflowThrows(Long.MAX_VALUE, -1L);
        testOverflowThrows(0L, Long.MIN_VALUE);
        testOverflowThrows(Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public static void main(String[] args) {
        testNormalResults();
        testOverflowCases();
    }
}
