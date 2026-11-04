/*
 * @test
 * @summary Test Integer.parseUnsignedInt(String) for normal and null input
 * @run main IntegerWitupTest
 */

public class IntegerWitupTest {
    public static void main(String[] args) {
        // Test normal parsing of unsigned int string
        String validNumber = "12345";
        int result = java.lang.Integer.parseUnsignedInt(validNumber);
        if (result != 12345) {
            throw new Error("parseUnsignedInt failed for valid input: expected 12345 but got " + result);
        }

        // Test parsing zero
        if (java.lang.Integer.parseUnsignedInt("0") != 0) {
            throw new Error("parseUnsignedInt failed for zero input");
        }

        // Test parsing max unsigned int value as decimal string
        String maxUnsignedIntStr = "4294967295"; // 2^32 - 1
        int maxParsed = java.lang.Integer.parseUnsignedInt(maxUnsignedIntStr);
        if (maxParsed != -1) { // Because int is signed, 4294967295 overflows to -1
            throw new Error("parseUnsignedInt failed for max unsigned int string: expected -1 but got " + maxParsed);
        }

        // Test null input triggers NullPointerException
        try {
            java.lang.Integer.parseUnsignedInt(null);
            throw new Error("parseUnsignedInt did not throw NullPointerException for null input");
        } catch (NullPointerException npe) {
            // Expected
        }

        // Test empty string input triggers NumberFormatException
        try {
            java.lang.Integer.parseUnsignedInt("");
            throw new Error("parseUnsignedInt did not throw NumberFormatException for empty string");
        } catch (NumberFormatException nfe) {
            // Expected
        }

        // Test invalid numeric string triggers NumberFormatException
        try {
            java.lang.Integer.parseUnsignedInt("-1");
            throw new Error("parseUnsignedInt did not throw NumberFormatException for negative number string");
        } catch (NumberFormatException nfe) {
            // Expected
        }

        try {
            java.lang.Integer.parseUnsignedInt("abc123");
            throw new Error("parseUnsignedInt did not throw NumberFormatException for non-numeric string");
        } catch (NumberFormatException nfe) {
            // Expected
        }
    }
}
