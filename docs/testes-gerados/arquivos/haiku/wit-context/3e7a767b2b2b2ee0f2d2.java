/*
 * @test
 * @summary Checks observable toJavaFormatString results for exceptional IEEE values handled by FloatingDecimal
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
        check("-0.0", FloatingDecimal.toJavaFormatString(-0.0d));
        check("NaN", FloatingDecimal.toJavaFormatString(Float.NaN));
        check("Infinity", FloatingDecimal.toJavaFormatString(Float.POSITIVE_INFINITY));
        check("-Infinity", FloatingDecimal.toJavaFormatString(Float.NEGATIVE_INFINITY));
        check("0.0", FloatingDecimal.toJavaFormatString(0.0f));
        check("-0.0", FloatingDecimal.toJavaFormatString(-0.0f));
    }

    private static void check(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("expected " + expected + " but got " + actual);
        }
    }
}
