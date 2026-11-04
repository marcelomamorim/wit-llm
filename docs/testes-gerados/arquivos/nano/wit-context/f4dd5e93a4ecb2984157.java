/* @test    @summary Test for UnknownFormatFlagsException constructor with null argument    @run main UnknownFormatFlagsExceptionWitupTest */

import java.util.UnknownFormatFlagsException;

public class UnknownFormatFlagsExceptionWitupTest {

    public static void main(String[] args) {
        testConstructorWithNull();
        testConstructorWithValidString();
    }

    private static void testConstructorWithNull() {
        try {
            new UnknownFormatFlagsException(null);
            System.err.println("Failed: Expected NullPointerException for null argument");
        } catch (NullPointerException expected) {
            // Test passes
        } catch (Throwable t) {
            System.err.println("Failed: Unexpected exception type: " + t);
        }
    }

    private static void testConstructorWithValidString() {
        String testFlags = "-+0";
        UnknownFormatFlagsException ex = new UnknownFormatFlagsException(testFlags);
        if (!ex.getFlags().equals(testFlags)) {
            System.err.println("Failed: getFlags() did not return the expected string");
        }
        String message = ex.getMessage();
        if (!message.equals("Flags = " + testFlags)) {
            System.err.println("Failed: getMessage() did not return the expected message");
        }
    }
}
