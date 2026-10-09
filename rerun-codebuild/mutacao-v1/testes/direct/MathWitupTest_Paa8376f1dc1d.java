/* @test    @summary Test negateExact with positive, negative, and overflow cases    @run main MathWitupTest_Paa8376f1dc1d */

public class MathWitupTest_Paa8376f1dc1d {

    public static void main(String[] args) {
        testNegateExactPositive();
        testNegateExactNegative();
        testNegateExactOverflow();
    }

    static void testNegateExactPositive() {
        long input = 123456789L;
        long result = Math.negateExact(input);
        if (result != -input) {
            throw new AssertionError("Failed: negateExact positive case. Expected " + (-input) + " but got " + result);
        }
    }

    static void testNegateExactNegative() {
        long input = -987654321L;
        long result = Math.negateExact(input);
        if (result != -input) {
            throw new AssertionError("Failed: negateExact negative case. Expected " + (-input) + " but got " + result);
        }
    }

    static void testNegateExactOverflow() {
        long input = Long.MIN_VALUE;
        try {
            Math.negateExact(input);
            throw new AssertionError("Failed: negateExact overflow case. Expected ArithmeticException but none was thrown.");
        } catch (ArithmeticException e) {
            // Expected exception
        }
    }
}
