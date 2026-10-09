/*
 * @test
 * @summary Test java.lang.Math.incrementExact(long) for normal and overflow cases
 * @run main MathWitupTest_P5ed3453a3917
 */

public class MathWitupTest_P5ed3453a3917 {
    public static void main(String[] args) {
        testIncrementExactNormal();
        testIncrementExactOverflow();
    }

    private static void testIncrementExactNormal() {
        long input = 0L;
        long expected = 1L;
        long result = java.lang.Math.incrementExact(input);
        if (result != expected) {
            throw new RuntimeException("incrementExact failed for input 0: expected " + expected + " but got " + result);
        }

        input = -1L;
        expected = 0L;
        result = java.lang.Math.incrementExact(input);
        if (result != expected) {
            throw new RuntimeException("incrementExact failed for input -1: expected " + expected + " but got " + result);
        }

        input = Long.MAX_VALUE - 1;
        expected = Long.MAX_VALUE;
        result = java.lang.Math.incrementExact(input);
        if (result != expected) {
            throw new RuntimeException("incrementExact failed for input Long.MAX_VALUE - 1: expected " + expected + " but got " + result);
        }
    }

    private static void testIncrementExactOverflow() {
        long input = Long.MAX_VALUE;
        try {
            java.lang.Math.incrementExact(input);
            throw new RuntimeException("incrementExact did not throw ArithmeticException on overflow input");
        } catch (ArithmeticException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.equals("long overflow")) {
                throw new RuntimeException("incrementExact threw ArithmeticException with unexpected message: " + msg);
            }
        }
    }
}
