/*
 * @test
 * @summary Test for Byte.valueOf(String s) method, including null input.
 * @run main ByteWitupTest
 */

import java.lang.Byte;

public class ByteWitupTest {

    public static void main(String[] args) {
        testValidString();
        testNullString();
        testInvalidString();
    }

    private static void testValidString() {
        try {
            Byte b = Byte.valueOf("127");
            if (b == null || b.byteValue() != 127) {
                throw new AssertionError("Failed to parse valid string \\"127\\");
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid string", e);
        }
    }

    private static void testNullString() {
        try {
            Byte.valueOf((String) null);
            throw new AssertionError("Expected NullPointerException for null input");
        } catch (NullPointerException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for null input", e);
        }
    }

    private static void testInvalidString() {
        try {
            Byte.valueOf("notANumber");
            throw new AssertionError("Expected NumberFormatException for invalid string");
        } catch (NumberFormatException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for invalid string", e);
        }
    }
}
