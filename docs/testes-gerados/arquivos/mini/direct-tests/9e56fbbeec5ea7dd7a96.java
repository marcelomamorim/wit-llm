/*
 * @test
 * @summary Test java.lang.Integer.parseUnsignedInt(String) method
 * @run main IntegerWitupTest
 */
public class IntegerWitupTest {
    public static void main(String[] args) {
        // Test valid decimal string
        int val1 = Integer.parseUnsignedInt("12345");
        if (val1 != 12345) {
            throw new Error("Failed to parse valid unsigned int decimal string");
        }

        // Test zero
        int val2 = Integer.parseUnsignedInt("0");
        if (val2 != 0) {
            throw new Error("Failed to parse zero");
        }

        // Test max unsigned int decimal string (2^32-1 = 4294967295) - should fail because parseUnsignedInt returns int
        // but parseUnsignedInt(String) calls parseUnsignedInt(String,10) which returns int, so 4294967295 overflows int
        // So test that NumberFormatException is thrown for value > 0xFFFFFFFF
        boolean thrown = false;
        try {
            Integer.parseUnsignedInt("4294967296"); // 2^32
        } catch (NumberFormatException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new Error("Expected NumberFormatException for value exceeding unsigned int range");
        }

        // Test invalid string (non-digit)
        thrown = false;
        try {
            Integer.parseUnsignedInt("12a34");
        } catch (NumberFormatException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new Error("Expected NumberFormatException for invalid input string");
        }

        // Test empty string
        thrown = false;
        try {
            Integer.parseUnsignedInt("");
        } catch (NumberFormatException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new Error("Expected NumberFormatException for empty string");
        }

        // Test negative sign - should throw NumberFormatException
        thrown = false;
        try {
            Integer.parseUnsignedInt("-1");
        } catch (NumberFormatException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new Error("Expected NumberFormatException for negative input string");
        }
    }
}
