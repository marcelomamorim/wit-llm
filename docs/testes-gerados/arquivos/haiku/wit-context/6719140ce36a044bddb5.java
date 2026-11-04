/*
 * @test
 * @summary Verifies that the public FloatingDecimal string conversions, which reach BinaryToASCIIBuffer.toJavaFormatString(), return the expected Java format strings
 * @modules java.base/jdk.internal.math
 * @run main FloatingDecimalWitupTest
 */

public class FloatingDecimalWitupTest {

    static int failures = 0;

    static void check(String actual, String expected) {
        if (!expected.equals(actual)) {
            failures++;
            System.err.println("Expected \"" + expected + "\" but got \"" + actual + "\"");
        }
    }

    public static void main(String[] args) {
        check(FloatingDecimal.toJavaFormatString(1.0d), "1.0");
        check(FloatingDecimal.toJavaFormatString(0.0d), "0.0");
        check(FloatingDecimal.toJavaFormatString(-0.0d), "-0.0");
        check(FloatingDecimal.toJavaFormatString(-2.5d), "-2.5");
        check(FloatingDecimal.toJavaFormatString(100.0d), "100.0");
        check(FloatingDecimal.toJavaFormatString(1234567.0d), "1234567.0");
        check(FloatingDecimal.toJavaFormatString(1.0E7d), "1.0E7");
        check(FloatingDecimal.toJavaFormatString(1.0E10d), "1.0E10");
        check(FloatingDecimal.toJavaFormatString(0.001d), "0.001");
        check(FloatingDecimal.toJavaFormatString(1.0E-4d), "1.0E-4");
        check(FloatingDecimal.toJavaFormatString(0.1d), "0.1");
        check(FloatingDecimal.toJavaFormatString(Double.NaN), "NaN");
        check(FloatingDecimal.toJavaFormatString(Double.POSITIVE_INFINITY), "Infinity");
        check(FloatingDecimal.toJavaFormatString(Double.NEGATIVE_INFINITY), "-Infinity");

        check(FloatingDecimal.toJavaFormatString(1.0f), "1.0");
        check(FloatingDecimal.toJavaFormatString(0.1f), "0.1");
        check(FloatingDecimal.toJavaFormatString(-2.5f), "-2.5");
        check(FloatingDecimal.toJavaFormatString(3.4028235E38f), "3.4028235E38");
        check(FloatingDecimal.toJavaFormatString(Float.NaN), "NaN");

        if (failures != 0) {
            throw new RuntimeException(failures + " FloatingDecimal format check(s) failed");
        }
    }
}
