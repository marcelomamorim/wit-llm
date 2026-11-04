/*
 * @test
 * @summary Test Math.addExact(int,int) for overflow and normal cases
 * @run main MathWitupTest
 */

public class MathWitupTest {
    public static void main(String[] args) {
        testAddExactNoOverflow();
        testAddExactOverflow();
    }

    private static void testAddExactNoOverflow() {
        // Normal cases without overflow
        int result1 = java.lang.Math.addExact(1, 2);
        if (result1 != 3) {
            throw new AssertionError("addExact(1, 2) expected 3 but got " + result1);
        }

        int result2 = java.lang.Math.addExact(-1, -2);
        if (result2 != -3) {
            throw new AssertionError("addExact(-1, -2) expected -3 but got " + result2);
        }

        int result3 = java.lang.Math.addExact(0, 0);
        if (result3 != 0) {
            throw new AssertionError("addExact(0, 0) expected 0 but got " + result3);
        }

        int result4 = java.lang.Math.addExact(Integer.MAX_VALUE, 0);
        if (result4 != Integer.MAX_VALUE) {
            throw new AssertionError("addExact(Integer.MAX_VALUE, 0) expected Integer.MAX_VALUE but got " + result4);
        }

        int result5 = java.lang.Math.addExact(Integer.MIN_VALUE, 0);
        if (result5 != Integer.MIN_VALUE) {
            throw new AssertionError("addExact(Integer.MIN_VALUE, 0) expected Integer.MIN_VALUE but got " + result5);
        }
    }

    private static void testAddExactOverflow() {
        // Overflow cases: expect ArithmeticException
        try {
            java.lang.Math.addExact(Integer.MAX_VALUE, 1);
            throw new AssertionError("Expected ArithmeticException for addExact(Integer.MAX_VALUE, 1)");
        } catch (ArithmeticException e) {
            // expected
        }

        try {
            java.lang.Math.addExact(Integer.MIN_VALUE, -1);
            throw new AssertionError("Expected ArithmeticException for addExact(Integer.MIN_VALUE, -1)");
        } catch (ArithmeticException e) {
            // expected
        }

        // Additional overflow cases
        try {
            java.lang.Math.addExact(1000000000, 2000000000);
            throw new AssertionError("Expected ArithmeticException for addExact(1000000000, 2000000000)");
        } catch (ArithmeticException e) {
            // expected
        }

        try {
            java.lang.Math.addExact(-1000000000, -2000000000);
            throw new AssertionError("Expected ArithmeticException for addExact(-1000000000, -2000000000)");
        } catch (ArithmeticException e) {
            // expected
        }
    }
}
