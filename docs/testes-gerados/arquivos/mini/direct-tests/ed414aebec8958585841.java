/*
 * @test
 * @summary Test java.lang.Math.negateExact(int) for normal and overflow cases
 * @run main MathWitupTest
 */

public class MathWitupTest {
    public static void main(String[] args) {
        testNegateExactNormal();
        testNegateExactOverflow();
    }

    private static void testNegateExactNormal() {
        int[] testValues = {0, 1, -1, 12345, -12345, Integer.MAX_VALUE, -100};
        for (int v : testValues) {
            int result = java.lang.Math.negateExact(v);
            if (result != -v) {
                throw new AssertionError("negateExact(" + v + ") returned " + result + ", expected " + (-v));
            }
        }
    }

    private static void testNegateExactOverflow() {
        try {
            java.lang.Math.negateExact(Integer.MIN_VALUE);
            throw new AssertionError("Expected ArithmeticException for Integer.MIN_VALUE");
        } catch (ArithmeticException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("integer overflow")) {
                throw new AssertionError("Unexpected exception message: " + msg);
            }
        }
    }
}
