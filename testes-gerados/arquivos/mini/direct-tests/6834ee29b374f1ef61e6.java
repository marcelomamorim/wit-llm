/*
 * @test
 * @summary Test java.lang.Math.incrementExact(int) for normal and overflow cases
 * @run main MathWitupTest
 */

public class MathWitupTest {
    public static void main(String[] args) {
        testIncrementExactNormal();
        testIncrementExactOverflow();
    }

    private static void testIncrementExactNormal() {
        int input = 0;
        int expected = 1;
        int result = java.lang.Math.incrementExact(input);
        if (result != expected) {
            throw new AssertionError("incrementExact(" + input + ") expected " + expected + " but got " + result);
        }

        input = -1;
        expected = 0;
        result = java.lang.Math.incrementExact(input);
        if (result != expected) {
            throw new AssertionError("incrementExact(" + input + ") expected " + expected + " but got " + result);
        }

        input = Integer.MAX_VALUE - 1;
        expected = Integer.MAX_VALUE;
        result = java.lang.Math.incrementExact(input);
        if (result != expected) {
            throw new AssertionError("incrementExact(" + input + ") expected " + expected + " but got " + result);
        }
    }

    private static void testIncrementExactOverflow() {
        int input = Integer.MAX_VALUE;
        try {
            java.lang.Math.incrementExact(input);
            throw new AssertionError("incrementExact(" + input + ") did not throw ArithmeticException on overflow");
        } catch (ArithmeticException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.equals("integer overflow")) {
                throw new AssertionError("incrementExact(" + input + ") threw ArithmeticException with unexpected message: " + msg);
            }
        }
    }
}
