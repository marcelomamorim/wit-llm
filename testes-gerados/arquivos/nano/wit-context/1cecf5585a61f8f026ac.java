/* @test    @summary Test for Long(String s) constructor, covering valid and invalid inputs.    @run main LongWitupTest */

import java.lang.Long;

public class LongWitupTest {

    public static void main(String[] args) {
        testValidInput();
        testNullInput();
        testInvalidInput();
    }

    private static void testValidInput() {
        try {
            Long l = new Long("12345");
            if (l == null || l.longValue() != 12345L) {
                throw new AssertionError("Valid input test failed");
            }
        } catch (NumberFormatException e) {
            throw new AssertionError("Valid input threw exception", e);
        }
    }

    private static void testNullInput() {
        try {
            new Long((String) null);
            throw new AssertionError("Null input did not throw exception");
        } catch (NumberFormatException e) {
            // Expected exception
        } catch (NullPointerException e) {
            // Depending on implementation, may throw NullPointerException
        }
    }

    private static void testInvalidInput() {
        String[] invalidInputs = {"abc", "12a3", ""};
        for (String input : invalidInputs) {
            try {
                new Long(input);
                throw new AssertionError("Invalid input '" + input + "' did not throw exception");
            } catch (NumberFormatException e) {
                // Expected exception
            }
        }
    }
}
