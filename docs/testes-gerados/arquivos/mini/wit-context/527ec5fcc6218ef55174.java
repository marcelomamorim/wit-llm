/*
 * @test
 * @summary Test java.lang.Short.parseShort(String) for valid and invalid inputs
 * @run main ShortWitupTest
 */

public class ShortWitupTest {
    public static void main(String[] args) {
        testValidParse();
        testNullInput();
        testEmptyString();
    }

    private static void testValidParse() {
        // Valid decimal string
        short val = java.lang.Short.parseShort("12345");
        if (val != 12345) {
            throw new Error("Expected 12345 but got " + val);
        }
        // Valid negative decimal string
        val = java.lang.Short.parseShort("-32768");
        if (val != -32768) {
            throw new Error("Expected -32768 but got " + val);
        }
        // Valid positive decimal string with plus sign
        val = java.lang.Short.parseShort("+32767");
        if (val != 32767) {
            throw new Error("Expected 32767 but got " + val);
        }
    }

    private static void testNullInput() {
        try {
            java.lang.Short.parseShort(null);
            throw new Error("Expected NumberFormatException for null input");
        } catch (NumberFormatException e) {
            // Expected
        }
    }

    private static void testEmptyString() {
        try {
            java.lang.Short.parseShort("");
            throw new Error("Expected NumberFormatException for empty string input");
        } catch (NumberFormatException e) {
            // Expected
        }
    }
}
