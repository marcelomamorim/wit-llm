/*
 * @test
 * @summary Checks public FloatingDecimal conversions for exceptional (NaN and infinite) double values
 * @modules java.base/jdk.internal.math
 * @run main FloatingDecimalWitupTest
 */

import jdk.internal.math.FloatingDecimal;

public class FloatingDecimalWitupTest {

    static void check(String actual, String expected, String label) {
        if (!expected.equals(actual)) {
            throw new RuntimeException(label + ": expected " + expected + " but got " + actual);
        }
    }

    public static void main(String[] args) {
        check(FloatingDecimal.toJavaFormatString(Double.NaN), "NaN", "NaN");
        check(FloatingDecimal.toJavaFormatString(Double.POSITIVE_INFINITY), "Infinity", "+Infinity");
        check(FloatingDecimal.toJavaFormatString(Double.NEGATIVE_INFINITY), "-Infinity", "-Infinity");
    }
}
