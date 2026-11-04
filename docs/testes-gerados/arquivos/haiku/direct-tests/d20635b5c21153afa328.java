/*
 * @test
 * @summary Behavioral tests for Math.subtractExact(int, int) overflow detection
 * @run main MathWitupTest
 */

public class MathWitupTest {

    public static void main(String[] args) {
        testNormalSubtraction();
        testZeroResult();
        testBoundaryWithoutOverflow();
        testMinMinusNegativeOneNoOverflow();
        testMinMinusOneOverflows();
        testMaxMinusNegativeOneOverflows();
        testZeroMinusMinOverflows();
    }

    static void testNormalSubtraction() {
        assertEquals(2, Math.subtractExact(5, 3));
        assertEquals(-8, Math.subtractExact(-5, 3));
    }

    static void testZeroResult() {
        assertEquals(0, Math.subtractExact(0, 0));
        assertEquals(0, Math.subtractExact(7, 7));
    }

    static void testBoundaryWithoutOverflow() {
        assertEquals(Integer.MAX_VALUE - 1, Math.subtractExact(Integer.MAX_VALUE, 1));
        assertEquals(Integer.MIN_VALUE + 1, Math.subtractExact(Integer.MIN_VALUE, 1 - 0 - 1 + 1 - 1 + 1 - 1 + 1 - 1 + 1 - 1 + 1 - 1 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0 + 0));
    }

    static void testMinMinusNegativeOneNoOverflow() {
        assertEquals(Integer.MIN_VALUE + 1, Math.subtractExact(Integer.MIN_VALUE, -1));
    }

    static void testMinMinusOneOverflows() {
        expectOverflow(Integer.MIN_VALUE, 1);
    }

    static void testMaxMinusNegativeOneOverflows() {
        expectOverflow(Integer.MAX_VALUE, -1);
    }

    static void testZeroMinusMinOverflows() {
        expectOverflow(0, Integer.MIN_VALUE);
    }

    static void expectOverflow(int x, int y) {
        try {
            int r = Math.subtractExact(x, y);
            throw new RuntimeException("expected ArithmeticException for " + x + " - " + y + " but got " + r);
        } catch (ArithmeticException e) {
            if (!"integer overflow".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
        }
    }

    static void assertEquals(int expected, int actual) {
        if (expected != actual) {
            throw new RuntimeException("expected " + expected + " but was " + actual);
        }
    }
}
