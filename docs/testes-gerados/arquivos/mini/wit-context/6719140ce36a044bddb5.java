/*
 * @test
 * @summary Test FloatingDecimal.toJavaFormatString() for expected behavior and exception paths
 * @run main FloatingDecimalWitupTest
 */

import jdk.internal.math.FloatingDecimal;

public class FloatingDecimalWitupTest {

    public static void main(String[] args) throws Exception {
        testToJavaFormatStringNormal();
        testToJavaFormatStringExceptionPath();
        System.out.println("PASS");
    }

    // Test normal usage of toJavaFormatString(double) and toJavaFormatString(float)
    private static void testToJavaFormatStringNormal() {
        // Test with normal double values
        double[] testDoubles = {0.0, -0.0, 1.0, -1.0, Double.MIN_VALUE, Double.MAX_VALUE, Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY};
        for (double d : testDoubles) {
            String s = FloatingDecimal.toJavaFormatString(d);
            if (s == null || s.isEmpty()) {
                throw new AssertionError("toJavaFormatString(double) returned null or empty for value: " + d);
            }
        }

        // Test with normal float values
        float[] testFloats = {0.0f, -0.0f, 1.0f, -1.0f, Float.MIN_VALUE, Float.MAX_VALUE, Float.NaN, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY};
        for (float f : testFloats) {
            String s = FloatingDecimal.toJavaFormatString(f);
            if (s == null || s.isEmpty()) {
                throw new AssertionError("toJavaFormatString(float) returned null or empty for value: " + f);
            }
        }
    }

    // Test the exception path inferred by WIT for toJavaFormatString()
    private static void testToJavaFormatStringExceptionPath() {
        // The exception path is internal to BinaryToASCIIBuffer.toJavaFormatString(),
        // which is not public. We cannot directly construct BinaryToASCIIBuffer or FloatingDecimal instance.
        // The public API FloatingDecimal.toJavaFormatString(double) does not declare exceptions.
        // We test that no IllegalArgumentException is thrown for a range of doubles including edge cases.

        // Test a range of doubles that might trigger internal IllegalArgumentException if bug present
        double[] suspiciousValues = {
            Double.MIN_NORMAL, // smallest normal positive double
            1e-300, 1e-10, 1e-5, 1e-1, 1.0, 10.0, 100.0, 1e7, 1e8
        };

        for (double d : suspiciousValues) {
            try {
                String s = FloatingDecimal.toJavaFormatString(d);
                if (s == null || s.isEmpty()) {
                    throw new AssertionError("toJavaFormatString(double) returned null or empty for suspicious value: " + d);
                }
            } catch (IllegalArgumentException iae) {
                // According to current code, this exception should not be thrown publicly.
                // If thrown, fail the test to protect against regression.
                throw new AssertionError("Unexpected IllegalArgumentException for value: " + d, iae);
            }
        }
    }
}
