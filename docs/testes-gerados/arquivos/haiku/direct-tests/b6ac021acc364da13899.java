/*
 * @test
 * @summary Checks public FloatingDecimal formatting of exceptional double values (NaN and infinities) and zero
 * @modules java.base/jdk.internal.math
 * @run main FloatingDecimalWitupTest
 */

import jdk.internal.math.FloatingDecimal;

public class FloatingDecimalWitupTest {
    public static void main(String[] args) {
        check("NaN", FloatingDecimal.toJavaFormatString(Double.NaN));
        check("Infinity", FloatingDecimal.toJavaFormatString(Double.POSITIVE_INFINITY));
        check("-Infinity", FloatingDecimal.toJavaFormatString(Double.NEGATIVE_INFINITY));
        check("0.0", FloatingDecimal.toJavaFormatString(0.0d));

        StringBuilder sb = new StringBuilder();
        FloatingDecimal.appendTo(Double.NaN, sb);
        check("NaN", sb.toString());
    }

    static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected " + expected + " but got " + actual);
        }
    }
}
