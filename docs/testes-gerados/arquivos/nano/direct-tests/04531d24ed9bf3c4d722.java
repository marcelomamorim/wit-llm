/*
 * @test
 * @summary Tests the String.format method for correctness and regressions
 * @run main StringWitupTest
 */

import java.lang.String;
import java.util.Formatter;

public class StringWitupTest {

    public static void main(String[] args) {
        testFormatWithSimpleArguments();
        testFormatWithMultipleArguments();
        testFormatWithNullArguments();
        testFormatWithDifferentFormatStrings();
        testFormatWithEdgeCases();
    }

    static void testFormatWithSimpleArguments() {
        String result = String.format("Hello, %s!", "World");
        if (!"Hello, World!".equals(result)) {
            throw new AssertionError("Expected 'Hello, World!', but got: " + result);
        }
    }

    static void testFormatWithMultipleArguments() {
        String result = String.format("%d + %d = %d", 2, 3, 2 + 3);
        if (!"2 + 3 = 5".equals(result)) {
            throw new AssertionError("Expected '2 + 3 = 5', but got: " + result);
        }
    }

    static void testFormatWithNullArguments() {
        String result = String.format("Null object: %s", (Object) null);
        if (!"Null object: null".equals(result)) {
            throw new AssertionError("Expected 'Null object: null', but got: " + result);
        }
    }

    static void testFormatWithDifferentFormatStrings() {
        String result1 = String.format("Hex: 0x%X", 255);
        if (!"Hex: 0xFF".equals(result1)) {
            throw new AssertionError("Expected 'Hex: 0xFF', but got: " + result1);
        }
        String result2 = String.format("Float: %.2f", 3.14159);
        if (!"Float: 3.14".equals(result2)) {
            throw new AssertionError("Expected 'Float: 3.14', but got: " + result2);
        }
    }

    static void testFormatWithEdgeCases() {
        String resultEmpty = String.format("");
        if (!"".equals(resultEmpty)) {
            throw new AssertionError("Expected empty string, but got: " + resultEmpty);
        }
        String resultNullFormat = null;
        try {
            resultNullFormat = String.format(null, "test");
            throw new AssertionError("Expected NullPointerException for null format string");
        } catch (NullPointerException e) {
            // expected
        }
        String resultEmptyFormat = String.format("");
        if (!"".equals(resultEmptyFormat)) {
            throw new AssertionError("Expected empty string, but got: " + resultEmptyFormat);
        }
    }
}
