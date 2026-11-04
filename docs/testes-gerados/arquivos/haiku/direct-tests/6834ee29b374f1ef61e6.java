/*
 * @test
 * @summary Verifies Math.incrementExact(int) returns a+1 and throws ArithmeticException on overflow
 * @run main MathWitupTest
 */

public class MathWitupTest {

    public static void main(String[] args) {
        testRegularIncrements();
        testBoundaryBelowMax();
        testOverflowAtMaxValue();
        testNegativeBoundary();
    }

    static void testRegularIncrements() {
        check(Math.incrementExact(0) == 1, "0 + 1");
        check(Math.incrementExact(41) == 42, "41 + 1");
        check(Math.incrementExact(-1) == 0, "-1 + 1");
    }

    static void testBoundaryBelowMax() {
        check(Math.incrementExact(Integer.MAX_VALUE - 1) == Integer.MAX_VALUE,
              "MAX_VALUE - 1 incremented");
    }

    static void testOverflowAtMaxValue() {
        try {
            Math.incrementExact(Integer.MAX_VALUE);
            throw new AssertionError("expected ArithmeticException for MAX_VALUE");
        } catch (ArithmeticException expected) {
            check("integer overflow".equals(expected.getMessage()),
                  "unexpected message: " + expected.getMessage());
        }
    }

    static void testNegativeBoundary() {
        check(Math.incrementExact(Integer.MIN_VALUE) == Integer.MIN_VALUE + 1,
              "MIN_VALUE incremented");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
