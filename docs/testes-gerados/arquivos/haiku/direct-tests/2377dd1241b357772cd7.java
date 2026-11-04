/*
 * @test
 * @summary Witup-generated tests for Math.decrementExact(long)
 * @run main MathWitupTest
 */

public class MathWitupTest {

    public static void main(String[] args) {
        check(Math.decrementExact(0L) == -1L, "0 should decrement to -1");
        check(Math.decrementExact(1L) == 0L, "1 should decrement to 0");
        check(Math.decrementExact(-1L) == -2L, "-1 should decrement to -2");
        check(Math.decrementExact(Long.MIN_VALUE + 1L) == Long.MIN_VALUE, "MIN_VALUE+1 should decrement to MIN_VALUE");
        check(Math.decrementExact(Long.MAX_VALUE) == Long.MAX_VALUE - 1L, "MAX_VALUE should decrement to MAX_VALUE-1");

        try {
            Math.decrementExact(Long.MIN_VALUE);
            throw new RuntimeException("expected ArithmeticException for Long.MIN_VALUE");
        } catch (ArithmeticException e) {
            check("long overflow".equals(e.getMessage()), "unexpected message: " + e.getMessage());
        }
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
