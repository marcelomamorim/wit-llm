/* @test    @summary Test negateExact with positive, negative, zero, and overflow input    @run main MathWitupTest */

public class MathWitupTest {

    public static void main(String[] args) {
        testNegateExactPositive();
        testNegateExactNegative();
        testNegateExactZero();
        testNegateExactOverflow();
    }

    static void testNegateExactPositive() {
        int input = 12345;
        int expected = -12345;
        int result = Math.negateExact(input);
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    static void testNegateExactNegative() {
        int input = -6789;
        int expected = 6789;
        int result = Math.negateExact(input);
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    static void testNegateExactZero() {
        int input = 0;
        int expected = 0;
        int result = Math.negateExact(input);
        if (result != expected) {
            throw new AssertionError("Expected " + expected + " but got " + result);
        }
    }

    static void testNegateExactOverflow() {
        int input = Integer.MIN_VALUE;
        try {
            Math.negateExact(input);
            throw new AssertionError("Expected ArithmeticException for input: " + input);
        } catch (ArithmeticException e) {
            // Expected exception
        }
    }
}
