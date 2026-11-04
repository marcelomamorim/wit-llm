/* @test    @summary Test Integer(String s) constructor for valid and invalid inputs    @run main IntegerWitupTest */

import java.lang.Integer;

public class IntegerWitupTest {

    public static void main(String[] args) {
        testValidString();
        testNullString();
        testInvalidString();
    }

    private static void testValidString() {
        try {
            Integer i = new Integer("123");
            if (i == null || i.intValue() != 123) {
                throw new AssertionError("Valid string test failed");
            }
        } catch (NumberFormatException e) {
            throw new AssertionError("Valid string threw exception", e);
        }
    }

    private static void testNullString() {
        try {
            new Integer((String) null);
            throw new AssertionError("Null string did not throw exception");
        } catch (NumberFormatException e) {
            // Expected exception
        } catch (NullPointerException e) {
            // Depending on implementation, may throw NullPointerException
        }
    }

    private static void testInvalidString() {
        String[] invalidInputs = {"abc", "12.3", ""};
        for (String s : invalidInputs) {
            try {
                new Integer(s);
                throw new AssertionError("Invalid string '" + s + "' did not throw exception");
            } catch (NumberFormatException e) {
                // Expected exception
            }
        }
    }
}
