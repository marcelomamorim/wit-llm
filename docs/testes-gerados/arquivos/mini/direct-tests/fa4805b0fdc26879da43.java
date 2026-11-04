/*
 * @test
 * @summary Test DuplicateFormatFlagsException constructor and behavior
 * @run main DuplicateFormatFlagsExceptionWitupTest
 */

public class DuplicateFormatFlagsExceptionWitupTest {
    public static void main(String[] args) {
        testConstructorWithValidFlags();
        testConstructorWithNullFlags();
        testGetFlags();
        testGetMessage();
    }

    private static void testConstructorWithValidFlags() {
        String flags = "-+";
        java.util.DuplicateFormatFlagsException ex = new java.util.DuplicateFormatFlagsException(flags);
        if (!flags.equals(ex.getFlags())) {
            throw new AssertionError("getFlags() returned unexpected value");
        }
    }

    private static void testConstructorWithNullFlags() {
        try {
            new java.util.DuplicateFormatFlagsException(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testGetFlags() {
        String flags = "abc";
        java.util.DuplicateFormatFlagsException ex = new java.util.DuplicateFormatFlagsException(flags);
        String returnedFlags = ex.getFlags();
        if (!flags.equals(returnedFlags)) {
            throw new AssertionError("getFlags() did not return the expected flags");
        }
    }

    private static void testGetMessage() {
        String flags = "xyz";
        java.util.DuplicateFormatFlagsException ex = new java.util.DuplicateFormatFlagsException(flags);
        String message = ex.getMessage();
        String expectedMessage = String.format("Flags = '%s'", flags);
        if (!expectedMessage.equals(message)) {
            throw new AssertionError("getMessage() returned unexpected message: " + message);
        }
    }
}
