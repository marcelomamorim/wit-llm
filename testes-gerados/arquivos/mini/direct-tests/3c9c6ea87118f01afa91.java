/*
 * @test
 * @summary Test constructor and methods of MissingFormatWidthException
 * @run main MissingFormatWidthExceptionWitupTest
 */

import java.util.MissingFormatWidthException;

public class MissingFormatWidthExceptionWitupTest {
    public static void main(String[] args) {
        testConstructorWithValidString();
        testConstructorWithNullThrowsNPE();
        testGetFormatSpecifierAndGetMessage();
    }

    private static void testConstructorWithValidString() {
        String specifier = "10";
        MissingFormatWidthException ex = new MissingFormatWidthException(specifier);
        if (!specifier.equals(ex.getFormatSpecifier())) {
            throw new AssertionError("getFormatSpecifier() returned wrong value");
        }
        if (!specifier.equals(ex.getMessage())) {
            throw new AssertionError("getMessage() returned wrong value");
        }
    }

    private static void testConstructorWithNullThrowsNPE() {
        try {
            new MissingFormatWidthException(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testGetFormatSpecifierAndGetMessage() {
        String specifier = "abc";
        MissingFormatWidthException ex = new MissingFormatWidthException(specifier);
        if (!specifier.equals(ex.getFormatSpecifier())) {
            throw new AssertionError("getFormatSpecifier() returned wrong value");
        }
        if (!specifier.equals(ex.getMessage())) {
            throw new AssertionError("getMessage() returned wrong value");
        }
    }
}
