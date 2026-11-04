/* @test    @summary Tests for java.lang.Short constructor with String argument    @run main ShortWitupTest */

import java.lang.Short;

public class ShortWitupTest {

    public static void main(String[] args) {
        testValidShortStrings();
        testNullStringThrowsException();
        testInvalidShortStringsThrowsException();
    }

    private static void testValidShortStrings() {
        String[] validStrings = {"0", "-32768", "32767", "12345", "-12345"};
        for (String s : validStrings) {
            try {
                Short sObj = new Short(s);
                assert sObj != null : "Short object should not be null for input: " + s;
                assert sObj.toString().equals(s.trim()) || sObj.toString().equals(Short.toString(Short.parseShort(s.trim(), 10))) : "Mismatch in string representation for input: " + s;
            } catch (NumberFormatException e) {
                throw new AssertionError("Unexpected NumberFormatException for valid input: " + s, e);
            }
        }
    }

    private static void testNullStringThrowsException() {
        try {
            new Short((String) null);
            throw new AssertionError("Expected NumberFormatException for null input");
        } catch (NumberFormatException e) {
            // Expected exception
        }
    }

    private static void testInvalidShortStringsThrowsException() {
        String[] invalidStrings = {"", "abc", "32768", "-32769", "12.34", "+"};
        for (String s : invalidStrings) {
            try {
                new Short(s);
                throw new AssertionError("Expected NumberFormatException for invalid input: " + s);
            } catch (NumberFormatException e) {
                // Expected exception
            }
        }
    }
}
