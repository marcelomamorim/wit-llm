/*
 * @test
 * @summary Basic observable behavior of FloatingDecimal.parseFloat
 * @modules java.base/jdk.internal.math
 * @run main FloatingDecimalWitupTest
 */

import jdk.internal.math.FloatingDecimal;

public class FloatingDecimalWitupTest {
    public static void main(String[] args) {
        check(FloatingDecimal.parseFloat("1.5") == 1.5f, "simple decimal");
        check(FloatingDecimal.parseFloat("  2.5  ") == 2.5f, "surrounding whitespace is trimmed");
        check(FloatingDecimal.parseFloat("1e10") == 1e10f, "exponent notation");
        check(FloatingDecimal.parseFloat("3.4028235e38") == Float.MAX_VALUE, "max float value");
        check(Float.compare(FloatingDecimal.parseFloat("-0.0"), -0.0f) == 0, "negative zero preserved");
        check(Float.isNaN(FloatingDecimal.parseFloat("NaN")), "NaN literal");
        check(FloatingDecimal.parseFloat("-Infinity") == Float.NEGATIVE_INFINITY, "negative infinity literal");
        check(FloatingDecimal.parseFloat("0x1p3") == 8.0f, "hexadecimal floating literal");
        expectNumberFormatException("abc");
        expectNumberFormatException("");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }

    static void expectNumberFormatException(String input) {
        try {
            FloatingDecimal.parseFloat(input);
        } catch (NumberFormatException e) {
            return;
        }
        throw new RuntimeException("Expected NumberFormatException for input: \"" + input + "\"");
    }
}
