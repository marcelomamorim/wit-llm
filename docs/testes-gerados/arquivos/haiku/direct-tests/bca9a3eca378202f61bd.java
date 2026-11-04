/*
 * @test
 * @summary Verifies Math.incrementExact(long) returns a+1 and throws ArithmeticException with message "long overflow" at Long.MAX_VALUE
 * @run main MathWitupTest
 */

public class MathWitupTest {
    public static void main(String[] args) {
        check(Math.incrementExact(0L) == 1L, "incrementExact(0)");
        check(Math.incrementExact(-1L) == 0L, "incrementExact(-1)");
        check(Math.incrementExact(41L) == 42L, "incrementExact(41)");
        check(Math.incrementExact(Long.MAX_VALUE - 1L) == Long.MAX_VALUE, "incrementExact(MAX-1)");
        check(Math.incrementExact(Long.MIN_VALUE) == Long.MIN_VALUE + 1L, "incrementExact(MIN)");

        try {
            Math.incrementExact(Long.MAX_VALUE);
            throw new RuntimeException("expected ArithmeticException for Long.MAX_VALUE");
        } catch (ArithmeticException e) {
            if (!"long overflow".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }
    }

    static void check(boolean condition, String what) {
        if (!condition) {
            throw new RuntimeException("failed: " + what);
        }
    }
}
