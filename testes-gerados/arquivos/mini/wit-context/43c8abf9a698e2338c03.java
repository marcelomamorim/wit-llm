/*
 * @test
 * @summary Test java.lang.Short.valueOf(String s) for valid and invalid inputs
 * @run main ShortWitupTest
 */

public class ShortWitupTest {
    public static void main(String[] args) {
        testValidString();
        testNullString();
        testInvalidString();
    }

    private static void testValidString() {
        // Valid decimal string
        Short s1 = Short.valueOf("123");
        if (s1.shortValue() != 123) {
            throw new Error("Failed: Expected 123 but got " + s1);
        }

        // Valid negative decimal string
        Short s2 = Short.valueOf("-32768");
        if (s2.shortValue() != -32768) {
            throw new Error("Failed: Expected -32768 but got " + s2);
        }

        // Valid max value
        Short s3 = Short.valueOf("32767");
        if (s3.shortValue() != 32767) {
            throw new Error("Failed: Expected 32767 but got " + s3);
        }
    }

    private static void testNullString() {
        try {
            Short.valueOf((String) null);
            throw new Error("Failed: Expected NumberFormatException for null input");
        } catch (NumberFormatException e) {
            // Expected exception
        }
    }

    private static void testInvalidString() {
        String[] invalidInputs = {"", "abc", "32768", "-32769", "12.3", "++123", "--123", " 123", "123 ", "0x10"};
        for (String input : invalidInputs) {
            try {
                Short.valueOf(input);
                throw new Error("Failed: Expected NumberFormatException for input: '" + input + "'");
            } catch (NumberFormatException e) {
                // Expected exception
            }
        }
    }
}
