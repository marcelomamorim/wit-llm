/*
 * @test
 * @summary Test Math.incrementExact(long) for normal and overflow cases
 * @run main MathWitupTest
 */

public class MathWitupTest {
    public static void main(String[] args) {
        testIncrementExactNormal();
        testIncrementExactOverflow();
    }

    private static void testIncrementExactNormal() {
        long input = 0L;
        long expected = 1L;
        long result = java.lang.Math.incrementExact(input);
        if (result != expected) {
            throw new AssertionError("incrementExact(" + input + ") expected " + expected + " but got " + result);
        }

        input = -1L;
        expected = 0L;
        result = java.lang.Math.incrementExact(input);
        if (result != expected) {
            throw new AssertionError("incrementExact(" + input + ") expected " + expected + " but got " + result);
        }

        input = 123456789L;
        expected = 123456790L;
        result = java.lang.Math.incrementExact(input);
        if (result != expected) {
            throw new AssertionError("incrementExact(" + input + ") expected " + expected + " but got " + result);
        }
    }

    private static void testIncrementExactOverflow() {
        long input = Long.MAX_VALUE;
        try {
            java.lang.Math.incrementExact(input);
            throw new AssertionError("incrementExact(" + input + ") did not throw ArithmeticException on overflow");
        } catch (ArithmeticException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("overflow")) {
                throw new AssertionError("incrementExact(" + input + ") threw ArithmeticException with unexpected message: " + msg);
            }
        } catch (Throwable t) {
            throw new AssertionError("incrementExact(" + input + ") threw unexpected exception type: " + t);
        }
    }
}
