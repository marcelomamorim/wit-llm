/*
 * @test
 * @summary Test Math.decrementExact(long) for normal and overflow cases
 * @run main MathWitupTest_P8dc145600882
 */

public class MathWitupTest_P8dc145600882 {
    public static void main(String[] args) {
        testDecrementExactNormal();
        testDecrementExactOverflow();
    }

    private static void testDecrementExactNormal() {
        long input = 0L;
        long expected = -1L;
        long result = java.lang.Math.decrementExact(input);
        if (result != expected) {
            throw new AssertionError("decrementExact(" + input + ") expected " + expected + " but got " + result);
        }

        input = 1L;
        expected = 0L;
        result = java.lang.Math.decrementExact(input);
        if (result != expected) {
            throw new AssertionError("decrementExact(" + input + ") expected " + expected + " but got " + result);
        }

        input = -1L;
        expected = -2L;
        result = java.lang.Math.decrementExact(input);
        if (result != expected) {
            throw new AssertionError("decrementExact(" + input + ") expected " + expected + " but got " + result);
        }
    }

    private static void testDecrementExactOverflow() {
        long input = Long.MIN_VALUE;
        try {
            java.lang.Math.decrementExact(input);
            throw new AssertionError("Expected ArithmeticException for decrementExact(Long.MIN_VALUE) but none thrown");
        } catch (ArithmeticException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("overflow")) {
                throw new AssertionError("ArithmeticException message does not contain 'overflow': " + msg);
            }
        } catch (Throwable t) {
            throw new AssertionError("Expected ArithmeticException but got " + t.getClass().getName(), t);
        }
    }
}
