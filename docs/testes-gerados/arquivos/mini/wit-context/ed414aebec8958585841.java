/*
 * @test
 * @summary Test Math.negateExact(int) for correct negation and overflow exception
 * @run main MathWitupTest
 */

public class MathWitupTest {
    public static void main(String[] args) {
        testNegateExactNormal();
        testNegateExactOverflow();
        System.out.println("MathWitupTest passed.");
    }

    private static void testNegateExactNormal() {
        // Test normal negation
        int[] testValues = {0, 1, -1, 12345, -12345, Integer.MAX_VALUE, -100};
        for (int v : testValues) {
            int result = java.lang.Math.negateExact(v);
            if (result != -v) {
                throw new AssertionError("negateExact failed for value: " + v + ", expected: " + (-v) + ", got: " + result);
            }
        }
    }

    private static void testNegateExactOverflow() {
        // Test that negateExact throws ArithmeticException for Integer.MIN_VALUE
        int minValue = Integer.MIN_VALUE;
        try {
            java.lang.Math.negateExact(minValue);
            throw new AssertionError("Expected ArithmeticException not thrown for Integer.MIN_VALUE");
        } catch (ArithmeticException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("overflow")) {
                throw new AssertionError("ArithmeticException message unexpected: " + msg);
            }
        }
    }
}
