/*
 * @test
 * @summary Test java.lang.Short.parseShort(String) method
 * @run main ShortWitupTest
 */

public class ShortWitupTest {
    public static void main(String[] args) {
        testValidDecimalStrings();
        testInvalidStrings();
        testNullInput();
        testEmptyString();
        testLeadingPlusMinus();
        testOutOfRangeValues();
    }

    private static void testValidDecimalStrings() {
        // Basic valid decimal parsing
        short val1 = Short.parseShort("0");
        if (val1 != 0) throw new RuntimeException("Expected 0 but got " + val1);

        short val2 = Short.parseShort("32767");
        if (val2 != 32767) throw new RuntimeException("Expected 32767 but got " + val2);

        short val3 = Short.parseShort("-32768");
        if (val3 != -32768) throw new RuntimeException("Expected -32768 but got " + val3);

        short val4 = Short.parseShort("12345");
        if (val4 != 12345) throw new RuntimeException("Expected 12345 but got " + val4);
    }

    private static void testInvalidStrings() {
        // Strings that are not valid numbers should throw NumberFormatException
        try {
            Short.parseShort("abc");
            throw new RuntimeException("Expected NumberFormatException for 'abc'");
        } catch (NumberFormatException expected) {}

        try {
            Short.parseShort("12.34");
            throw new RuntimeException("Expected NumberFormatException for '12.34'");
        } catch (NumberFormatException expected) {}

        try {
            Short.parseShort("--123");
            throw new RuntimeException("Expected NumberFormatException for '--123'");
        } catch (NumberFormatException expected) {}

        try {
            Short.parseShort("+ 123");
            throw new RuntimeException("Expected NumberFormatException for '+ 123'");
        } catch (NumberFormatException expected) {}
    }

    private static void testNullInput() {
        try {
            Short.parseShort(null);
            throw new RuntimeException("Expected NumberFormatException for null input");
        } catch (NumberFormatException expected) {}
    }

    private static void testEmptyString() {
        try {
            Short.parseShort("");
            throw new RuntimeException("Expected NumberFormatException for empty string");
        } catch (NumberFormatException expected) {}
    }

    private static void testLeadingPlusMinus() {
        short valPlus = Short.parseShort("+123");
        if (valPlus != 123) throw new RuntimeException("Expected 123 but got " + valPlus);

        short valMinus = Short.parseShort("-123");
        if (valMinus != -123) throw new RuntimeException("Expected -123 but got " + valMinus);
    }

    private static void testOutOfRangeValues() {
        // Values outside short range should throw NumberFormatException
        try {
            Short.parseShort("32768");
            throw new RuntimeException("Expected NumberFormatException for 32768");
        } catch (NumberFormatException expected) {}

        try {
            Short.parseShort("-32769");
            throw new RuntimeException("Expected NumberFormatException for -32769");
        } catch (NumberFormatException expected) {}
    }
}
