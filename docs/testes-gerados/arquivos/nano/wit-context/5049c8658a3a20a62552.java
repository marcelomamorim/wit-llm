/* @test    @summary Test for MissingFormatArgumentException constructor with null argument    @run main MissingFormatArgumentExceptionWitupTest */

import java.util.MissingFormatArgumentException;

public class MissingFormatArgumentExceptionWitupTest {

    public static void main(String[] args) {
        testConstructorWithNull();
        testConstructorWithValidString();
    }

    private static void testConstructorWithNull() {
        boolean exceptionThrown = false;
        try {
            new MissingFormatArgumentException(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null");
        }
    }

    private static void testConstructorWithValidString() {
        String testString = "%d";
        MissingFormatArgumentException ex = new MissingFormatArgumentException(testString);
        if (!ex.getFormatSpecifier().equals(testString)) {
            throw new AssertionError("getFormatSpecifier() did not return the expected string");
        }
        String message = ex.getMessage();
        if (!message.equals("Format specifier '" + testString + "'")) {
            throw new AssertionError("getMessage() returned unexpected value: " + message);
        }
    }
}
