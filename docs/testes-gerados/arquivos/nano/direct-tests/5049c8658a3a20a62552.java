/* @test    @summary Test for MissingFormatArgumentException constructor and getFormatSpecifier method    @run main MissingFormatArgumentExceptionWitupTest */

import java.util.MissingFormatArgumentException;

public class MissingFormatArgumentExceptionWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidString();
        testConstructorWithNull();
        testGetFormatSpecifier();
    }

    static void testConstructorWithValidString() {
        String specifier = "%d";
        MissingFormatArgumentException ex = new MissingFormatArgumentException(specifier);
        assert ex.getFormatSpecifier().equals(specifier) : "Failed: getFormatSpecifier should return the string passed to constructor";
        assert ex.getMessage().equals("Format specifier '" + specifier + "'") : "Failed: getMessage should return correct message";
    }

    static void testConstructorWithNull() {
        try {
            new MissingFormatArgumentException(null);
            assert false : "Expected NullPointerException when passing null";
        } catch (NullPointerException e) {
            // expected
        }
    }

    static void testGetFormatSpecifier() {
        String specifier = "%s";
        MissingFormatArgumentException ex = new MissingFormatArgumentException(specifier);
        assert ex.getFormatSpecifier().equals(specifier) : "Failed: getFormatSpecifier should return the string passed to constructor";
    }
}
