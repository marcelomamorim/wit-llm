/*
 * @test
 * @summary Checks public FloatingDecimal conversions for exceptional (NaN and infinite) values
 * @modules java.base/jdk.internal.math
 * @run main FloatingDecimalWitupTest
 */

public class FloatingDecimalWitupTest {

    public static void main(String[] args) {
        checkString("NaN", FloatingDecimal.toJavaFormatString(Double.NaN));
        checkString("Infinity", FloatingDecimal.toJavaFormatString(Double.POSITIVE_INFINITY));
        checkString("-Infinity", FloatingDecimal.toJavaFormatString(Double.NEGATIVE_INFINITY));

        checkString("NaN", FloatingDecimal.toJavaFormatString(Float.NaN));
        checkString("Infinity", FloatingDecimal.toJavaFormatString(Float.POSITIVE_INFINITY));
        checkString("-Infinity", FloatingDecimal.toJavaFormatString(Float.NEGATIVE_INFINITY));

        StringBuilder sb = new StringBuilder();
        FloatingDecimal.appendTo(Double.NaN, sb);
        checkString("NaN", sb.toString());

        sb.setLength(0);
        FloatingDecimal.appendTo(Double.NEGATIVE_INFINITY, sb);
        checkString("-Infinity", sb.toString());
    }

    private static void checkString(String expected, String actual) {
        if (!expected.equals(actual)) {
            throw new RuntimeException("Expected " + expected + " but got " + actual);
        }
    }
}
