/*
 * @test
 * @summary Test MissingFormatWidthException constructor and behavior
 * @run main MissingFormatWidthExceptionWitupTest
 */

public class MissingFormatWidthExceptionWitupTest {
    public static void main(String[] args) {
        testConstructorWithValidString();
        testConstructorWithNullThrowsNPE();
    }

    private static void testConstructorWithValidString() {
        String specifier = "d";
        java.util.MissingFormatWidthException ex = new java.util.MissingFormatWidthException(specifier);
        if (!specifier.equals(ex.getFormatSpecifier())) {
            throw new AssertionError("getFormatSpecifier() returned unexpected value");
        }
        if (!specifier.equals(ex.getMessage())) {
            throw new AssertionError("getMessage() returned unexpected value");
        }
    }

    private static void testConstructorWithNullThrowsNPE() {
        try {
            new java.util.MissingFormatWidthException(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // Expected exception
        }
    }
}
