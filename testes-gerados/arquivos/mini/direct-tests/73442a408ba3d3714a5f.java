/*
 * @test
 * @summary Test java.lang.Math.negateExact(long) for normal and overflow cases
 * @run main MathWitupTest
 */

public class MathWitupTest {
    public static void main(String[] args) {
        testNegateExactNormal();
        testNegateExactOverflow();
    }

    private static void testNegateExactNormal() {
        long[] testValues = {0L, 1L, -1L, Long.MAX_VALUE, -123456789L};
        for (long val : testValues) {
            long result = java.lang.Math.negateExact(val);
            if (result != -val) {
                throw new AssertionError("negateExact(" + val + ") returned " + result + ", expected " + (-val));
            }
        }
    }

    private static void testNegateExactOverflow() {
        try {
            java.lang.Math.negateExact(Long.MIN_VALUE);
            throw new AssertionError("Expected ArithmeticException for Long.MIN_VALUE");
        } catch (ArithmeticException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.equals("long overflow")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }
}
