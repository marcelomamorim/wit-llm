/*
 * @test
 * @summary Test java.lang.Long.parseUnsignedLong(String) for valid and invalid inputs
 * @run main LongWitupTest
 */

public class LongWitupTest {
    public static void main(String[] args) {
        testValidParse();
        testNullInput();
        testEmptyStringInput();
    }

    private static void testValidParse() {
        // Valid decimal string
        long val = Long.parseUnsignedLong("1234567890");
        if (val != 1234567890L) {
            throw new Error("Failed to parse valid unsigned long string");
        }
        // Valid decimal string with max unsigned long value (2^64-1) as string
        // Note: parseUnsignedLong(String) parses as unsigned long in base 10, max unsigned long decimal is 18446744073709551615
        // But Long.parseUnsignedLong returns a long primitive, which is signed, so max unsigned long cannot be represented as long
        // So test a large but valid value within signed long range
        val = Long.parseUnsignedLong("9223372036854775807"); // Long.MAX_VALUE
        if (val != Long.MAX_VALUE) {
            throw new Error("Failed to parse Long.MAX_VALUE string");
        }
    }

    private static void testNullInput() {
        try {
            Long.parseUnsignedLong(null);
            throw new Error("Expected NumberFormatException for null input");
        } catch (NumberFormatException e) {
            // Expected
        }
    }

    private static void testEmptyStringInput() {
        try {
            Long.parseUnsignedLong("");
            throw new Error("Expected NumberFormatException for empty string input");
        } catch (NumberFormatException e) {
            // Expected
        }
    }
}
