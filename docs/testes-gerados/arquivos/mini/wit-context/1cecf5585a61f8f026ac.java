/*
 * @test
 * @summary Test constructor Long(String s) for valid and invalid inputs
 * @run main LongWitupTest
 */

public class LongWitupTest {
    public static void main(String[] args) {
        testValidString();
        testNullString();
        testEmptyString();
    }

    private static void testValidString() {
        Long l = new Long("12345");
        if (l.longValue() != 12345L) {
            throw new Error("Long(String) failed for valid input");
        }
        Long negative = new Long("-98765");
        if (negative.longValue() != -98765L) {
            throw new Error("Long(String) failed for negative input");
        }
    }

    private static void testNullString() {
        try {
            new Long((String) null);
            throw new Error("Expected NumberFormatException for null input");
        } catch (NumberFormatException e) {
            // expected
        }
    }

    private static void testEmptyString() {
        try {
            new Long("");
            throw new Error("Expected NumberFormatException for empty string input");
        } catch (NumberFormatException e) {
            // expected
        }
    }
}
