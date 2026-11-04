/*
 * @test
 * @summary Verifies Math.subtractExact(long, long) returns correct results and throws ArithmeticException on long overflow
 * @run main MathWitupTest
 */

public class MathWitupTest {

    public static void main(String[] args) {
        checkEquals(2L, Math.subtractExact(5L, 3L));
        checkEquals(-2L, Math.subtractExact(3L, 5L));
        checkEquals(0L, Math.subtractExact(0L, 0L));
        checkEquals(0L, Math.subtractExact(Long.MAX_VALUE, Long.MAX_VALUE));
        checkEquals(0L, Math.subtractExact(Long.MIN_VALUE, Long.MIN_VALUE));
        checkEquals(Long.MIN_VALUE, Math.subtractExact(Long.MIN_VALUE, 0L));
        checkEquals(Long.MAX_VALUE, Math.subtractExact(Long.MAX_VALUE, 0L));
        checkEquals(Long.MIN_VALUE + 1L, Math.subtractExact(Long.MIN_VALUE, -1L));
        checkEquals(Long.MAX_VALUE - 1L, Math.subtractExact(Long.MAX_VALUE, 1L));
        checkEquals(-Long.MAX_VALUE, Math.subtractExact(0L, Long.MAX_VALUE));

        expectOverflow(Long.MIN_VALUE, 1L);
        expectOverflow(Long.MAX_VALUE, -1L);
        expectOverflow(0L, Long.MIN_VALUE);
        expectOverflow(-2L, Long.MAX_VALUE);
    }

    static void checkEquals(long expected, long actual) {
        if (expected != actual) {
            throw new RuntimeException("Expected " + expected + " but got " + actual);
        }
    }

    static void expectOverflow(long x, long y) {
        try {
            long r = Math.subtractExact(x, y);
            throw new RuntimeException("No overflow for " + x + " - " + y + ", result " + r);
        } catch (ArithmeticException e) {
            if (!"long overflow".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected message: " + e.getMessage());
            }
        }
    }
}
