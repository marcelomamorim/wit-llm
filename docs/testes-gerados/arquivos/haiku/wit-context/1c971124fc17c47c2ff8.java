/*
 * @test
 * @summary Verify Math.decrementExact throws ArithmeticException on overflow and returns a - 1 otherwise
 * @run main MathWitupTest
 */

public class MathWitupTest {

    public static void main(String[] args) {
        testOverflowThrows();
        testNormalDecrement();
        testBoundaryJustAboveMin();
        testZeroAndNegative();
    }

    static void testOverflowThrows() {
        try {
            Math.decrementExact(Integer.MIN_VALUE);
            throw new AssertionError("Expected ArithmeticException for Integer.MIN_VALUE");
        } catch (ArithmeticException expected) {
            if (!"integer overflow".equals(expected.getMessage())) {
                throw new AssertionError("Unexpected message: " + expected.getMessage());
            }
        }
    }

    static void testNormalDecrement() {
        check(Math.decrementExact(1), 0);
        check(Math.decrementExact(Integer.MAX_VALUE), Integer.MAX_VALUE - 1);
    }

    static void testBoundaryJustAboveMin() {
        check(Math.decrementExact(Integer.MIN_VALUE + 1), Integer.MIN_VALUE);
    }

    static void testZeroAndNegative() {
        check(Math.decrementExact(0), -1);
        check(Math.decrementExact(-5), -6);
    }

    static void check(int actual, int expected) {
        if (actual != expected) {
            throw new AssertionError("Expected " + expected + " but was " + actual);
        }
    }
}
