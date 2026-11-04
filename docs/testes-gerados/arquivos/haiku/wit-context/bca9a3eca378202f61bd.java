/*
 * @test
 * @summary Tests Math.incrementExact(long) boundary values and overflow behavior
 * @run main MathWitupTest
 */
public class MathWitupTest {
    public static void main(String[] args) {
        check(Math.incrementExact(0L) == 1L, "0");
        check(Math.incrementExact(-1L) == 0L, "-1");
        check(Math.incrementExact(41L) == 42L, "41");
        check(Math.incrementExact(Long.MIN_VALUE) == Long.MIN_VALUE + 1L, "MIN_VALUE");
        check(Math.incrementExact(Long.MAX_VALUE - 1L) == Long.MAX_VALUE, "MAX_VALUE-1");
        try {
            Math.incrementExact(Long.MAX_VALUE);
            throw new RuntimeException("expected ArithmeticException for Long.MAX_VALUE");
        } catch (ArithmeticException e) {
            check("long overflow".equals(e.getMessage()), "overflow message");
        }
    }

    static void check(boolean condition, String label) {
        if (!condition) {
            throw new RuntimeException("Failed: " + label);
        }
    }
}
