/* @test    @summary Test constructor of IllegalFormatFlagsException with valid string    @run main IllegalFormatFlagsExceptionWitupTest */

import java.util.IllegalFormatFlagsException;

public class IllegalFormatFlagsExceptionWitupTest {

    public static void main(String[] args) {
        testValidFlags();
        testNullFlagThrows();
    }

    static void testValidFlags() {
        String flags = "-+0";
        IllegalFormatFlagsException e = new IllegalFormatFlagsException(flags);
        assert "Flags = '" + flags + "'".equals(e.getMessage()) : "Unexpected message";
        assert flags.equals(e.getFlags()) : "Flags do not match";
    }

    static void testNullFlagThrows() {
        try {
            new IllegalFormatFlagsException(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
