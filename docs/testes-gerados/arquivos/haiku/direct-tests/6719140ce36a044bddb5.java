/*
 * @test
 * @summary Verifies FloatingDecimal.toJavaFormatString output for double and float values through the public static entry points
 * @modules java.base/jdk.internal.math
 * @run main FloatingDecimalWitupTest
 */

public class FloatingDecimalWitupTest {

    public static void main(String[] args) {
        checkDouble("1.0", 1.0);
        checkDouble("0.0", 0.0);
        checkDouble("-0.0", -0.0);
        checkDouble("-2.5", -2.5);
        checkDouble("100.0", 100.0);
        checkDouble("0.1", 0.1);
        checkDouble("0.3333333333333333", 1.0 / 3.0);
        checkDouble("0.6666666666666666", 2.0 / 3.0);
        checkDouble("9999999.0", 9999999.0);
        checkDouble("1.0E7", 1.0E7);
        checkDouble("1.0E10", 1.0E10);
        checkDouble("0.001", 0.001);
        checkDouble("1.0E-4", 0.0001);
        checkDouble("1.0E-5", 1.0E-5);
        checkDouble("4.9E-324", Double.MIN_VALUE);
        checkDouble("1.7976931348623157E308", Double.MAX_VALUE);
        checkDouble("NaN", Double.NaN);
        checkDouble("Infinity", Double.POSITIVE_INFINITY);
        checkDouble("-Infinity", Double.NEGATIVE_INFINITY);

        checkFloat("1.0", 1.0f);
        checkFloat("0.0", 0.0f);
        checkFloat("-0.0", -0.0f);
        checkFloat("0.1", 0.1f);
        checkFloat("-2.5", -2.5f);
        checkFloat("1.0E-5", 1.0E-5f);
        checkFloat("1.4E-45", Float.MIN_VALUE);
        checkFloat("3.4028235E38", Float.MAX_VALUE);
        checkFloat("NaN", Float.NaN);
        checkFloat("Infinity", Float.POSITIVE_INFINITY);
        checkFloat("-Infinity", Float.NEGATIVE_INFINITY);
    }

    static void checkDouble(String expected, double value) {
        String actual = jdk.internal.math.FloatingDecimal.toJavaFormatString(value);
        if (!expected.equals(actual)) {
            throw new RuntimeException("double " + value + ": expected " + expected + " but got " + actual);
        }
    }

    static void checkFloat(String expected, float value) {
        String actual = jdk.internal.math.FloatingDecimal.toJavaFormatString(value);
        if (!expected.equals(actual)) {
            throw new RuntimeException("float " + value + ": expected " + expected + " but got " + actual);
        }
    }
}
