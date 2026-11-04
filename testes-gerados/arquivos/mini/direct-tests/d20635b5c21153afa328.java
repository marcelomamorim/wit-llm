/*
 * @test
 * @summary Test Math.subtractExact(int,int) for correctness and overflow
 * @run main MathWitupTest
 */

public class MathWitupTest {
    public static void main(String[] args) {
        testSubtractExactNormal();
        testSubtractExactOverflow();
    }

    private static void testSubtractExactNormal() {
        // Test normal subtraction without overflow
        int result1 = java.lang.Math.subtractExact(10, 5);
        if (result1 != 5) {
            throw new Error("Expected 10 - 5 = 5 but got " + result1);
        }

        int result2 = java.lang.Math.subtractExact(-10, -5);
        if (result2 != -5) {
            throw new Error("Expected -10 - (-5) = -5 but got " + result2);
        }

        int result3 = java.lang.Math.subtractExact(0, 0);
        if (result3 != 0) {
            throw new Error("Expected 0 - 0 = 0 but got " + result3);
        }

        int result4 = java.lang.Math.subtractExact(Integer.MAX_VALUE, 0);
        if (result4 != Integer.MAX_VALUE) {
            throw new Error("Expected MAX_VALUE - 0 = MAX_VALUE but got " + result4);
        }

        int result5 = java.lang.Math.subtractExact(Integer.MIN_VALUE, 0);
        if (result5 != Integer.MIN_VALUE) {
            throw new Error("Expected MIN_VALUE - 0 = MIN_VALUE but got " + result5);
        }
    }

    private static void testSubtractExactOverflow() {
        // Test overflow cases
        try {
            java.lang.Math.subtractExact(Integer.MIN_VALUE, 1);
            throw new Error("Expected ArithmeticException for MIN_VALUE - 1 overflow");
        } catch (ArithmeticException e) {
            // expected
        }

        try {
            java.lang.Math.subtractExact(Integer.MAX_VALUE, -1);
            // MAX_VALUE - (-1) = MAX_VALUE + 1 overflows int
            throw new Error("Expected ArithmeticException for MAX_VALUE - (-1) overflow");
        } catch (ArithmeticException e) {
            // expected
        }
    }
}
