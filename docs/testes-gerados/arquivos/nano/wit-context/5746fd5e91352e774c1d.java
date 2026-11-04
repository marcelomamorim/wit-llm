/* @test    @summary Test for IllegalFormatFlagsException constructor with null argument    @run main IllegalFormatFlagsExceptionWitupTest */

import java.util.IllegalFormatFlagsException;

public class IllegalFormatFlagsExceptionWitupTest {

    public static void main(String[] args) {
        testConstructorWithNull();
        testConstructorWithValidString();
    }

    private static void testConstructorWithNull() {
        try {
            new IllegalFormatFlagsException(null);
            System.err.println("Failed: Expected NullPointerException for null argument");
            System.exit(1);
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testConstructorWithValidString() {
        String flags = "-+0";
        IllegalFormatFlagsException e = new IllegalFormatFlagsException(flags);
        if (!flags.equals(e.getFlags())) {
            System.err.println("Failed: getFlags() did not return the expected value");
            System.exit(1);
        }
        String message = e.getMessage();
        if (!message.contains(flags)) {
            System.err.println("Failed: getMessage() does not contain the flags string");
            System.exit(1);
        }
    }
}
