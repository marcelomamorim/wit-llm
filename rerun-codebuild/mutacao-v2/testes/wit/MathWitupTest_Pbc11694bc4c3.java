/*
 * @test
 * @summary Test java.lang.Math.negateExact(long) for correct negation and overflow exception
 * @run main MathWitupTest_Pbc11694bc4c3
 */

public class MathWitupTest_Pbc11694bc4c3 {
    public static void main(String[] args) {
        testNegateExactNormal();
        testNegateExactOverflow();
        System.out.println("MathWitupTest_Pbc11694bc4c3 passed.");
    }

    private static void testNegateExactNormal() {
        // Test normal negation
        long[] testValues = {0L, 1L, -1L, Long.MAX_VALUE, -123456789L};
        for (long val : testValues) {
            long result = java.lang.Math.negateExact(val);
            if (result != -val) {
                throw new AssertionError("negateExact failed for value: " + val + ", expected: " + (-val) + ", got: " + result);
            }
        }
    }

    private static void testNegateExactOverflow() {
        // Test that negateExact throws ArithmeticException on Long.MIN_VALUE
        long val = Long.MIN_VALUE;
        try {
            java.lang.Math.negateExact(val);
            throw new AssertionError("Expected ArithmeticException not thrown for Long.MIN_VALUE");
        } catch (ArithmeticException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("overflow")) {
                throw new AssertionError("ArithmeticException message unexpected: " + msg);
            }
        }
    }
}
