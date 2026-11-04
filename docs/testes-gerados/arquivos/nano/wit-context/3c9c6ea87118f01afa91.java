/* @test    @summary Test for MissingFormatWidthException behavior and exception path.    @run main MissingFormatWidthExceptionWitupTest */

import java.util.MissingFormatWidthException;

public class MissingFormatWidthExceptionWitupTest {

    public static void main(String[] args) {
        testConstructorWithNull();
        testConstructorWithValidString();
    }

    private static void testConstructorWithNull() {
        boolean exceptionThrown = false;
        try {
            new MissingFormatWidthException(null);
        } catch (NullPointerException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            throw new AssertionError("Expected NullPointerException when passing null");
        }
    }

    private static void testConstructorWithValidString() {
        String testString = "width";
        MissingFormatWidthException ex = new MissingFormatWidthException(testString);
        if (ex.getFormatSpecifier() == null || !ex.getFormatSpecifier().equals(testString)) {
            throw new AssertionError("getFormatSpecifier() did not return expected value");
        }
        if (ex.getMessage() == null || !ex.getMessage().equals(testString)) {
            throw new AssertionError("getMessage() did not return expected value");
        }
    }
}
