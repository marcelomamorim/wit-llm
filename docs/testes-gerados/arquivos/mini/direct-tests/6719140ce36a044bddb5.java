/*
 * @test
 * @summary Test FloatingDecimal.toJavaFormatString() method behavior
 * @run main FloatingDecimalWitupTest
 */

import jdk.internal.math.FloatingDecimal;

public class FloatingDecimalWitupTest {

    public static void main(String[] args) throws Exception {
        testToJavaFormatStringDouble();
        testToJavaFormatStringFloat();
        testAppendTo();
    }

    private static void testToJavaFormatStringDouble() {
        double[] testValues = {0.0, -0.0, 1.0, -1.0, Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, 123.456, -789.0123};
        for (double d : testValues) {
            String s = FloatingDecimal.toJavaFormatString(d);
            if (s == null || s.isEmpty()) {
                throw new RuntimeException("toJavaFormatString(double) returned null or empty string for value: " + d);
            }
            // Check that parsing back yields the same value (except for NaN)
            if (!Double.isNaN(d)) {
                double parsed = Double.parseDouble(s);
                if (Double.doubleToLongBits(parsed) != Double.doubleToLongBits(d)) {
                    throw new RuntimeException("Round-trip failed for double value " + d + ": got " + parsed + " from string '" + s + "'");
                }
            }
        }
    }

    private static void testToJavaFormatStringFloat() {
        float[] testValues = {0.0f, -0.0f, 1.0f, -1.0f, Float.NaN, Float.POSITIVE_INFINITY, Float.NEGATIVE_INFINITY, 123.456f, -789.0123f};
        for (float f : testValues) {
            String s = FloatingDecimal.toJavaFormatString(f);
            if (s == null || s.isEmpty()) {
                throw new RuntimeException("toJavaFormatString(float) returned null or empty string for value: " + f);
            }
            // Check that parsing back yields the same value (except for NaN)
            if (!Float.isNaN(f)) {
                float parsed = Float.parseFloat(s);
                if (Float.floatToIntBits(parsed) != Float.floatToIntBits(f)) {
                    throw new RuntimeException("Round-trip failed for float value " + f + ": got " + parsed + " from string '" + s + "'");
                }
            }
        }
    }

    private static void testAppendTo() throws Exception {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        double d = 123.456;
        FloatingDecimal.appendTo(d, sb);
        String s = sb.toString();
        if (s == null || s.isEmpty()) {
            throw new RuntimeException("appendTo(double, Appendable) produced null or empty string");
        }
        double parsed = Double.parseDouble(s);
        if (Double.doubleToLongBits(parsed) != Double.doubleToLongBits(d)) {
            throw new RuntimeException("appendTo produced string that does not parse back to original double");
        }
    }
}