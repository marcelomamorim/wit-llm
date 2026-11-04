/* @test    @summary Test parsing Long from String with valid and invalid inputs    @run main LongWitupTest */

import java.lang.Long;

public class LongWitupTest {

    public static void main(String[] args) {
        testParseLongValid();
        testParseLongNull();
        testParseLongInvalid();
    }

    private static void testParseLongValid() {
        try {
            long result = Long.parseLong("12345");
            if (result != 12345L) {
                throw new AssertionError("Expected 12345L, got " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception for valid input", e);
        }
    }

    private static void testParseLongNull() {
        try {
            Long.parseLong(null);
            throw new AssertionError("Expected NumberFormatException for null input");
        } catch (NumberFormatException e) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type for null input", e);
        }
    }

    private static void testParseLongInvalid() {
        String[] invalidInputs = {"abc", "12a34", ""};
        for (String input : invalidInputs) {
            try {
                Long.parseLong(input);
                throw new AssertionError("Expected NumberFormatException for input: " + input);
            } catch (NumberFormatException e) {
                // expected
            } catch (Exception e) {
                throw new AssertionError("Unexpected exception type for input: " + input, e);
            }
        }
    }
}
