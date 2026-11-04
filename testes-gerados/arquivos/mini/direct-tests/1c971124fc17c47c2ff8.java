/*
 * @test
 * @summary Test java.lang.Math.decrementExact(int) for normal and overflow cases
 * @run main MathWitupTest
 */

public class MathWitupTest {
    public static void main(String[] args) {
        testDecrementExactNormal();
        testDecrementExactOverflow();
    }

    private static void testDecrementExactNormal() {
        int input = 0;
        int expected = -1;
        int result = java.lang.Math.decrementExact(input);
        if (result != expected) {
            throw new AssertionError("decrementExact(0) expected -1 but got " + result);
        }

        input = 1;
        expected = 0;
        result = java.lang.Math.decrementExact(input);
        if (result != expected) {
            throw new AssertionError("decrementExact(1) expected 0 but got " + result);
        }

        input = -100;
        expected = -101;
        result = java.lang.Math.decrementExact(input);
        if (result != expected) {
            throw new AssertionError("decrementExact(-100) expected -101 but got " + result);
        }

        input = Integer.MAX_VALUE;
        expected = Integer.MAX_VALUE - 1;
        result = java.lang.Math.decrementExact(input);
        if (result != expected) {
            throw new AssertionError("decrementExact(Integer.MAX_VALUE) expected " + expected + " but got " + result);
        }
    }

    private static void testDecrementExactOverflow() {
        int input = Integer.MIN_VALUE;
        try {
            java.lang.Math.decrementExact(input);
            throw new AssertionError("Expected ArithmeticException for decrementExact(Integer.MIN_VALUE) but none thrown");
        } catch (ArithmeticException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.equals("integer overflow")) {
                throw new AssertionError("Expected message 'integer overflow' but got: " + msg);
            }
        }
    }
}
