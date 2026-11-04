/* @test    @summary Test for FloatingDecimal.toJavaFormatString() method to observe behavior and exception paths    @run main FloatingDecimalWitupTest */

import java.io.StringWriter;
import java.io.Writer;
import jdk.internal.math.FloatingDecimal;

public class FloatingDecimalWitupTest {

    public static void main(String[] args) {
        testToJavaFormatStringWithNormalValues();
        testToJavaFormatStringWithEdgeCases();
        testToJavaFormatStringWithInvalidInputs();
    }

    static void testToJavaFormatStringWithNormalValues() {
        double[] testValues = {0.0, 1.0, -1.0, 123.456, -987.654, Double.MAX_VALUE, Double.MIN_VALUE, Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY};
        for (double val : testValues) {
            String result = FloatingDecimal.toJavaFormatString(val);
            System.out.println("toJavaFormatString(" + val + ") = " + result);
        }
    }

    static void testToJavaFormatStringWithEdgeCases() {
        double[] edgeValues = {Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, 0.0, -0.0};
        for (double val : edgeValues) {
            String result = FloatingDecimal.toJavaFormatString(val);
            System.out.println("Edge case toJavaFormatString(" + val + ") = " + result);
        }
    }

    static void testToJavaFormatStringWithInvalidInputs() {
        // Since the method accepts double, invalid inputs are not possible unless NaN or Infinity
        double[] invalidValues = {Double.longBitsToDouble(0x7ff8000000000000L)}; // NaN payload
        for (double val : invalidValues) {
            String result = FloatingDecimal.toJavaFormatString(val);
            System.out.println("Invalid input toJavaFormatString(" + val + ") = " + result);
        }
    }
}
