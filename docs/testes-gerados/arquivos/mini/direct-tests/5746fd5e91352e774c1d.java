/*
 * @test
 * @summary Test IllegalFormatFlagsException constructor and behavior
 * @run main IllegalFormatFlagsExceptionWitupTest
 */

public class IllegalFormatFlagsExceptionWitupTest {
    public static void main(String[] args) {
        testConstructorWithValidFlags();
        testConstructorWithNullFlags();
        testGetFlags();
        testGetMessage();
    }

    private static void testConstructorWithValidFlags() {
        String flags = "abc";
        java.util.IllegalFormatFlagsException ex = new java.util.IllegalFormatFlagsException(flags);
        if (!flags.equals(ex.getFlags())) {
            throw new AssertionError("getFlags() did not return the expected flags");
        }
    }

    private static void testConstructorWithNullFlags() {
        try {
            new java.util.IllegalFormatFlagsException(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testGetFlags() {
        String flags = "xyz";
        java.util.IllegalFormatFlagsException ex = new java.util.IllegalFormatFlagsException(flags);
        String returnedFlags = ex.getFlags();
        if (!flags.equals(returnedFlags)) {
            throw new AssertionError("getFlags() returned wrong value");
        }
    }

    private static void testGetMessage() {
        String flags = "123";
        java.util.IllegalFormatFlagsException ex = new java.util.IllegalFormatFlagsException(flags);
        String msg = ex.getMessage();
        String expected = "Flags = '" + flags + "'";
        if (!expected.equals(msg)) {
            throw new AssertionError("getMessage() returned wrong value: " + msg);
        }
    }
}
