/*
 * @test
 * @summary Verifies public FloatingDecimal conversions of exceptional (NaN and infinite) values, which route through the ExceptionalBinaryToASCIIBuffer whose getDigits throws IllegalArgumentException
 * @modules java.base/jdk.internal.math
 * @run main FloatingDecimalWitupTest
 */

import jdk.internal.math.FloatingDecimal;

public class FloatingDecimalWitupTest {

    static void check(String actual, String expected, String label) {
        if (!expected.equals(actual)) {
            throw new RuntimeException(label + ": expected \"" + expected + "\" but got \"" + actual + "\"");
        }
    }

    public static void main(String[] args) {
        check(FloatingDecimal.toJavaFormatString(Double.NaN), "NaN", "double NaN");
        check(FloatingDecimal.toJavaFormatString(Double.POSITIVE_INFINITY), "Infinity", "double +Infinity");
        check(FloatingDecimal.toJavaFormatString(Double.NEGATIVE_INFINITY), "-Infinity", "double -Infinity");
        check(FloatingDecimal.toJavaFormatString(Float.NaN), "NaN", "float NaN");
        check(FloatingDecimal.toJavaFormatString(Float.POSITIVE_INFINITY), "Infinity", "float +Infinity");
        check(FloatingDecimal.toJavaFormatString(Float.NEGATIVE_INFINITY), "-Infinity", "float -Infinity");

        StringBuilder sb = new StringBuilder();
        FloatingDecimal.appendTo(Double.NaN, sb);
        check(sb.toString(), "NaN", "appendTo double NaN");
    }
}
