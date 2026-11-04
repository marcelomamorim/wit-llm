/*
 * @test
 * @summary Test UnknownFormatFlagsException constructor and getFlags method
 * @run main UnknownFormatFlagsExceptionWitupTest
 */

public class UnknownFormatFlagsExceptionWitupTest {
    public static void main(String[] args) {
        testConstructorWithValidFlags();
        testConstructorWithNullThrowsNPE();
    }

    private static void testConstructorWithValidFlags() {
        String flags = "abc";
        java.util.UnknownFormatFlagsException ex = new java.util.UnknownFormatFlagsException(flags);
        if (!flags.equals(ex.getFlags())) {
            throw new AssertionError("getFlags() did not return the expected flags");
        }
        String message = ex.getMessage();
        if (!message.equals("Flags = " + flags)) {
            throw new AssertionError("getMessage() did not return the expected message");
        }
    }

    private static void testConstructorWithNullThrowsNPE() {
        try {
            new java.util.UnknownFormatFlagsException(null);
            throw new AssertionError("Constructor did not throw NullPointerException when passed null");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
