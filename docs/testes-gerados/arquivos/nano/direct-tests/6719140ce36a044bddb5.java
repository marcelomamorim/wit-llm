/* @test    @summary Test FloatingDecimal.toJavaFormatString() method for various double values    @run main FloatingDecimalWitupTest */

import java.io.StringWriter;
import java.io.Writer;
import jdk.internal.math.FloatingDecimal;

public class FloatingDecimalWitupTest {

    public static void main(String[] args) {
        testToJavaFormatStringWithZero();
        testToJavaFormatStringWithPositiveNumber();
        testToJavaFormatStringWithNegativeNumber();
        testToJavaFormatStringWithLargeNumber();
        testToJavaFormatStringWithSmallNumber();
        testToJavaFormatStringWithInfinity();
        testToJavaFormatStringWithNaN();
    }

    static void testToJavaFormatStringWithZero() {
        double value = 0.0;
        String result = FloatingDecimal.toJavaFormatString(value);
        if (!"0.0".equals(result)) {
            throw new AssertionError("Expected '0.0' but got: " + result);
        }
    }

    static void testToJavaFormatStringWithPositiveNumber() {
        double value = 123.456;
        String result = FloatingDecimal.toJavaFormatString(value);
        if (!result.contains("123.456") && !result.contains("1.23456")) {
            throw new AssertionError("Unexpected result: " + result);
        }
    }

    static void testToJavaFormatStringWithNegativeNumber() {
        double value = -789.01;
        String result = FloatingDecimal.toJavaFormatString(value);
        if (!result.contains("-789.01")) {
            throw new AssertionError("Unexpected result: " + result);
        }
    }

    static void testToJavaFormatStringWithLargeNumber() {
        double value = 1.79e308;
        String result = FloatingDecimal.toJavaFormatString(value);
        if (!result.contains("1.79E+308") && !result.contains("1.79e+308")) {
            throw new AssertionError("Unexpected result: " + result);
        }
    }

    static void testToJavaFormatStringWithSmallNumber() {
        double value = 4.9e-324;
        String result = FloatingDecimal.toJavaFormatString(value);
        if (!result.contains("4.9E-324") && !result.contains("4.9e-324")) {
            throw new AssertionError("Unexpected result: " + result);
        }
    }

    static void testToJavaFormatStringWithInfinity() {
        double value = Double.POSITIVE_INFINITY;
        String result = FloatingDecimal.toJavaFormatString(value);
        if (!"Infinity".equals(result)) {
            throw new AssertionError("Expected 'Infinity' but got: " + result);
        }
    }

    static void testToJavaFormatStringWithNaN() {
        double value = Double.NaN;
        String result = FloatingDecimal.toJavaFormatString(value);
        if (!"NaN".equals(result)) {
            throw new AssertionError("Expected 'NaN' but got: " + result);
        }
    }
}
