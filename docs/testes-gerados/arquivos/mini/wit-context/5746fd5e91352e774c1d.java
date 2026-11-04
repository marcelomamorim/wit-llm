/*
 * @test
 * @summary Test constructor and behavior of IllegalFormatFlagsException
 * @run main IllegalFormatFlagsExceptionWitupTest
 */

public class IllegalFormatFlagsExceptionWitupTest {
    public static void main(String[] args) {
        testConstructorWithValidFlags();
        testConstructorWithNullFlags();
        testGetFlagsAndMessage();
    }

    private static void testConstructorWithValidFlags() {
        String flags = "abc";
        java.util.IllegalFormatFlagsException ex = new java.util.IllegalFormatFlagsException(flags);
        if (!flags.equals(ex.getFlags())) {
            throw new AssertionError("getFlags() returned unexpected value");
        }
    }

    private static void testConstructorWithNullFlags() {
        try {
            new java.util.IllegalFormatFlagsException(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    private static void testGetFlagsAndMessage() {
        String flags = "xyz";
        java.util.IllegalFormatFlagsException ex = new java.util.IllegalFormatFlagsException(flags);
        String message = ex.getMessage();
        if (!message.contains(flags)) {
            throw new AssertionError("getMessage() does not contain flags string");
        }
    }
}
