/* @test    @summary Test parseUnsignedLong with null input and empty string to trigger exceptions    @run main LongWitupTest */

import java.lang.Long;

public class LongWitupTest {

    public static void main(String[] args) {
        testParseUnsignedLongNull();
        testParseUnsignedLongEmpty();
    }

    private static void testParseUnsignedLongNull() {
        try {
            Long.parseUnsignedLong(null);
            System.err.println("Expected NumberFormatException for null input, but none was thrown.");
            System.exit(1);
        } catch (NumberFormatException e) {
            // Expected exception
        }
    }

    private static void testParseUnsignedLongEmpty() {
        try {
            Long.parseUnsignedLong("");
            System.err.println("Expected NumberFormatException for empty string, but none was thrown.");
            System.exit(1);
        } catch (NumberFormatException e) {
            // Expected exception
        }
    }
}
