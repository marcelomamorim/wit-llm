/*
 * @test
 * @summary Test Math.decrementExact(int) for normal and overflow cases
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
            throw new RuntimeException("decrementExact(" + input + ") returned " + result + ", expected " + expected);
        }

        input = 1;
        expected = 0;
        result = java.lang.Math.decrementExact(input);
        if (result != expected) {
            throw new RuntimeException("decrementExact(" + input + ") returned " + result + ", expected " + expected);
        }

        input = -100;
        expected = -101;
        result = java.lang.Math.decrementExact(input);
        if (result != expected) {
            throw new RuntimeException("decrementExact(" + input + ") returned " + result + ", expected " + expected);
        }
    }

    private static void testDecrementExactOverflow() {
        int input = Integer.MIN_VALUE;
        try {
            java.lang.Math.decrementExact(input);
            throw new RuntimeException("decrementExact(" + input + ") did not throw ArithmeticException as expected");
        } catch (ArithmeticException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("overflow")) {
                throw new RuntimeException("decrementExact(" + input + ") threw ArithmeticException with unexpected message: " + msg);
            }
        }
    }
}
