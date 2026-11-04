/* @test    @summary Test constructor of MissingFormatWidthException with valid string    @run main MissingFormatWidthExceptionWitupTest */

import java.util.MissingFormatWidthException;

public class MissingFormatWidthExceptionWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidString();
        testConstructorWithNullThrows();
    }

    static void testConstructorWithValidString() {
        String testString = "%d";
        MissingFormatWidthException ex = new MissingFormatWidthException(testString);
        assert ex.getFormatSpecifier().equals(testString) : "Expected format specifier to be '" + testString + "'";
        assert ex.getMessage().equals(testString) : "Expected message to be '" + testString + "'";
    }

    static void testConstructorWithNullThrows() {
        try {
            new MissingFormatWidthException(null);
            assert false : "Expected NullPointerException";
        } catch (NullPointerException e) {
            // expected
        }
    }
}
