/*
 * @test
 * @summary Witup-derived checks for FloatingDecimal.parseFloat valid and invalid inputs
 * @modules java.base/jdk.internal.math
 * @run main FloatingDecimalWitupTest
 */

public class FloatingDecimalWitupTest {

    public static void main(String[] args) {
        check(FloatingDecimal.parseFloat("1.5") == 1.5f, "plain decimal");
        check(FloatingDecimal.parseFloat("  2.5  ") == 2.5f, "surrounding whitespace is trimmed");
        check(FloatingDecimal.parseFloat("1.5f") == 1.5f, "trailing f suffix");
        check(Float.isNaN(FloatingDecimal.parseFloat("NaN")), "NaN literal");
        check(FloatingDecimal.parseFloat("-Infinity") == Float.NEGATIVE_INFINITY, "negative infinity literal");
        check(Float.floatToRawIntBits(FloatingDecimal.parseFloat("-0.0"))
                == Float.floatToRawIntBits(-0.0f), "negative zero preserved");

        expectNumberFormatException("abc");
        expectNumberFormatException("");
        expectNumberFormatException("1.5.5");
        expectNumberFormatException("   ");
    }

    static void expectNumberFormatException(String input) {
        try {
            FloatingDecimal.parseFloat(input);
        } catch (NumberFormatException expected) {
            return;
        }
        throw new RuntimeException("NumberFormatException not thrown for input: \"" + input + "\"");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }
}
