/* @test    @summary Test for DuplicateFormatFlagsException constructor behavior and exception throwing    @run main DuplicateFormatFlagsExceptionWitupTest */

import java.util.DuplicateFormatFlagsException;

public class DuplicateFormatFlagsExceptionWitupTest {

    public static void main(String[] args) {
        testConstructorWithNull();
        testConstructorWithValidString();
    }

    private static void testConstructorWithNull() {
        boolean exceptionThrown = false;
        try {
            new DuplicateFormatFlagsException(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null");
        }
    }

    private static void testConstructorWithValidString() {
        String testFlags = "abc";
        DuplicateFormatFlagsException ex = new DuplicateFormatFlagsException(testFlags);
        if (ex.getFlags() == null || !ex.getFlags().equals(testFlags)) {
            throw new AssertionError("Flags do not match the input string");
        }
        String message = ex.getMessage();
        if (message == null || !message.contains(testFlags)) {
            throw new AssertionError("Message does not contain the flags string");
        }
    }
}
