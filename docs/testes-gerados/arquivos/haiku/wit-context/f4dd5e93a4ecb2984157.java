/*
 * @test
 * @summary Witup-generated behavioral tests for the UnknownFormatFlagsException(String) constructor
 * @run main UnknownFormatFlagsExceptionWitupTest
 */

public class UnknownFormatFlagsExceptionWitupTest {

    public static void main(String[] args) {
        testNullFlagsThrowsNullPointerException();
        testGetFlagsReturnsGivenFlags();
        testGetMessageContainsFlags();
        testEmptyFlagsAccepted();
        testIsIllegalFormatException();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullFlagsThrowsNullPointerException() {
        try {
            new UnknownFormatFlagsException(null);
            check(false, "expected NullPointerException for null flags");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testGetFlagsReturnsGivenFlags() {
        String flags = "-+";
        UnknownFormatFlagsException e = new UnknownFormatFlagsException(flags);
        check(flags.equals(e.getFlags()), "getFlags() should return the flags passed to the constructor");
    }

    static void testGetMessageContainsFlags() {
        UnknownFormatFlagsException e = new UnknownFormatFlagsException("-+");
        check("Flags = -+".equals(e.getMessage()), "unexpected message: " + e.getMessage());
    }

    static void testEmptyFlagsAccepted() {
        UnknownFormatFlagsException e = new UnknownFormatFlagsException("");
        check("".equals(e.getFlags()), "empty flags should be stored as given");
        check("Flags = ".equals(e.getMessage()), "unexpected message for empty flags: " + e.getMessage());
    }

    static void testIsIllegalFormatException() {
        Object e = new UnknownFormatFlagsException("x");
        check(e instanceof IllegalFormatException, "should be an IllegalFormatException");
    }
}
