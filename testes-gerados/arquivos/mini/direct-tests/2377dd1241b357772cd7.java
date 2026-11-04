/*
 * @test
 * @summary Test java.lang.Math.decrementExact(long) for normal and boundary cases
 * @run main MathWitupTest
 */

public class MathWitupTest {
    public static void main(String[] args) {
        testDecrementExactNormal();
        testDecrementExactMinValue();
    }

    private static void testDecrementExactNormal() {
        long input = 10L;
        long expected = 9L;
        long result = java.lang.Math.decrementExact(input);
        if (result != expected) {
            throw new RuntimeException("decrementExact failed for input " + input + ": expected " + expected + ", got " + result);
        }

        input = 0L;
        expected = -1L;
        result = java.lang.Math.decrementExact(input);
        if (result != expected) {
            throw new RuntimeException("decrementExact failed for input " + input + ": expected " + expected + ", got " + result);
        }

        input = -5L;
        expected = -6L;
        result = java.lang.Math.decrementExact(input);
        if (result != expected) {
            throw new RuntimeException("decrementExact failed for input " + input + ": expected " + expected + ", got " + result);
        }
    }

    private static void testDecrementExactMinValue() {
        try {
            java.lang.Math.decrementExact(Long.MIN_VALUE);
            throw new RuntimeException("decrementExact did not throw ArithmeticException for Long.MIN_VALUE");
        } catch (ArithmeticException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.equals("long overflow")) {
                throw new RuntimeException("decrementExact threw ArithmeticException with unexpected message: " + msg);
            }
        }
    }
}
