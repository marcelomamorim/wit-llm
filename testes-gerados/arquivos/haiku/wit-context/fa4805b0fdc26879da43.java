/*
 * @test
 * @summary Verify DuplicateFormatFlagsException constructor null handling and getFlags/getMessage behavior
 * @run main DuplicateFormatFlagsExceptionWitupTest
 */

import java.util.DuplicateFormatFlagsException;

public class DuplicateFormatFlagsExceptionWitupTest {

    public static void main(String[] args) {
        testNullFlagsThrowsNullPointerException();
        testGetFlagsReturnsGivenValue();
        testGetMessageContainsFlags();
    }

    static void testNullFlagsThrowsNullPointerException() {
        try {
            new DuplicateFormatFlagsException(null);
            throw new RuntimeException("Expected NullPointerException was not thrown");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testGetFlagsReturnsGivenValue() {
        String flags = "--";
        DuplicateFormatFlagsException e = new DuplicateFormatFlagsException(flags);
        if (!flags.equals(e.getFlags())) {
            throw new RuntimeException("getFlags() returned unexpected value: " + e.getFlags());
        }
    }

    static void testGetMessageContainsFlags() {
        String flags = "-0";
        DuplicateFormatFlagsException e = new DuplicateFormatFlagsException(flags);
        String msg = e.getMessage();
        if (msg == null || !msg.contains(flags)) {
            throw new RuntimeException("getMessage() does not contain flags: " + msg);
        }
    }
}
