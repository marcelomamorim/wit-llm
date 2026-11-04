/*
 * @test
 * @summary Verifies Math.addExact(long, long) returns correct sums and throws ArithmeticException on long overflow
 * @run main MathWitupTest
 */

public class MathWitupTest {

    public static void main(String[] args) {
        checkNormalSums();
        checkOverflowPositive();
        checkUnderflowNegative();
        checkBoundaryNoOverflow();
    }

    private static void checkNormalSums() {
        if (Math.addExact(2L, 3L) != 5L) {
            throw new AssertionError("2 + 3 expected 5");
        }
        if (Math.addExact(-7L, 4L) != -3L) {
            throw new AssertionError("-7 + 4 expected -3");
        }
        if (Math.addExact(0L, 0L) != 0L) {
            throw new AssertionError("0 + 0 expected 0");
        }
    }

    private static void checkOverflowPositive() {
        try {
            Math.addExact(Long.MAX_VALUE, 1L);
            throw new AssertionError("Expected ArithmeticException for MAX_VALUE + 1");
        } catch (ArithmeticException expected) {
            // expected
        }
    }

    private static void checkUnderflowNegative() {
        try {
            Math.addExact(Long.MIN_VALUE, -1L);
            throw new AssertionError("Expected ArithmeticException for MIN_VALUE + -1");
        } catch (ArithmeticException expected) {
            // expected
        }
    }

    private static void checkBoundaryNoOverflow() {
        if (Math.addExact(Long.MAX_VALUE, 0L) != Long.MAX_VALUE) {
            throw new AssertionError("MAX_VALUE + 0 expected MAX_VALUE");
        }
        if (Math.addExact(Long.MIN_VALUE, 0L) != Long.MIN_VALUE) {
            throw new AssertionError("MIN_VALUE + 0 expected MIN_VALUE");
        }
    }
}
