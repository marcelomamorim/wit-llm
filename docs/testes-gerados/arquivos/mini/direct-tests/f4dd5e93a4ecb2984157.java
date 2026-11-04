/*
 * @test
 * @summary Test UnknownFormatFlagsException constructor and behavior
 * @run main UnknownFormatFlagsExceptionWitupTest
 */

public class UnknownFormatFlagsExceptionWitupTest {
    public static void main(String[] args) {
        testConstructorWithValidFlags();
        testConstructorWithNullThrowsNPE();
        testGetFlagsReturnsCorrectValue();
        testGetMessageReturnsExpectedString();
    }

    private static void testConstructorWithValidFlags() {
        String flags = "abc";
        java.util.UnknownFormatFlagsException ex = new java.util.UnknownFormatFlagsException(flags);
        if (ex == null) {
            throw new AssertionError("Constructor returned null");
        }
    }

    private static void testConstructorWithNullThrowsNPE() {
        try {
            new java.util.UnknownFormatFlagsException(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testGetFlagsReturnsCorrectValue() {
        String flags = "xyz";
        java.util.UnknownFormatFlagsException ex = new java.util.UnknownFormatFlagsException(flags);
        String returnedFlags = ex.getFlags();
        if (!flags.equals(returnedFlags)) {
            throw new AssertionError("getFlags() returned '" + returnedFlags + "' instead of '" + flags + "'");
        }
    }

    private static void testGetMessageReturnsExpectedString() {
        String flags = "123";
        java.util.UnknownFormatFlagsException ex = new java.util.UnknownFormatFlagsException(flags);
        String message = ex.getMessage();
        String expected = "Flags = " + flags;
        if (!expected.equals(message)) {
            throw new AssertionError("getMessage() returned '" + message + "' instead of '" + expected + "'");
        }
    }
}
