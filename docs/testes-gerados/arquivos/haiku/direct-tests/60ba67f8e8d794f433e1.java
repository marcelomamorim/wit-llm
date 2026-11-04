/*
 * @test
 * @summary Verifies Math.addExact(long, long) returns correct sums and throws ArithmeticException with message "long overflow" on overflow
 * @run main MathWitupTest
 */
public class MathWitupTest {
    public static void main(String[] args) {
        check(Math.addExact(1L, 2L) == 3L, "1+2");
        check(Math.addExact(0L, 0L) == 0L, "0+0");
        check(Math.addExact(-5L, 3L) == -2L, "-5+3");
        check(Math.addExact(-5L, -3L) == -8L, "-5+-3");
        check(Math.addExact(Long.MAX_VALUE, 0L) == Long.MAX_VALUE, "MAX+0");
        check(Math.addExact(Long.MIN_VALUE, 0L) == Long.MIN_VALUE, "MIN+0");
        check(Math.addExact(Long.MAX_VALUE, Long.MIN_VALUE) == -1L, "MAX+MIN");
        check(Math.addExact(Long.MIN_VALUE, Long.MAX_VALUE) == -1L, "MIN+MAX");

        expectOverflow(Long.MAX_VALUE, 1L);
        expectOverflow(1L, Long.MAX_VALUE);
        expectOverflow(Long.MIN_VALUE, -1L);
        expectOverflow(Long.MAX_VALUE, Long.MAX_VALUE);
        expectOverflow(Long.MIN_VALUE, Long.MIN_VALUE);
    }

    static void check(boolean condition, String msg) {
        if (!condition) {
            throw new RuntimeException("Failed: " + msg);
        }
    }

    static void expectOverflow(long x, long y) {
        try {
            long r = Math.addExact(x, y);
            throw new RuntimeException("No exception for " + x + " + " + y + " -> " + r);
        } catch (ArithmeticException e) {
            check("long overflow".equals(e.getMessage()), "unexpected message: " + e.getMessage());
        }
    }
}
