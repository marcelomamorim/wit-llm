/*
 * @test
 * @summary Test constructor and behavior of MissingFormatArgumentException
 * @run main MissingFormatArgumentExceptionWitupTest
 */

public class MissingFormatArgumentExceptionWitupTest {
    public static void main(String[] args) {
        testConstructorWithValidString();
        testConstructorWithNullThrowsNPE();
        testGetFormatSpecifier();
        testGetMessage();
    }

    private static void testConstructorWithValidString() {
        String specifier = "d";
        java.util.MissingFormatArgumentException ex = new java.util.MissingFormatArgumentException(specifier);
        if (!specifier.equals(ex.getFormatSpecifier())) {
            throw new AssertionError("getFormatSpecifier() returned unexpected value");
        }
    }

    private static void testConstructorWithNullThrowsNPE() {
        try {
            new java.util.MissingFormatArgumentException(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        }
    }

    private static void testGetFormatSpecifier() {
        String specifier = "s";
        java.util.MissingFormatArgumentException ex = new java.util.MissingFormatArgumentException(specifier);
        String result = ex.getFormatSpecifier();
        if (!specifier.equals(result)) {
            throw new AssertionError("getFormatSpecifier() returned wrong value");
        }
    }

    private static void testGetMessage() {
        String specifier = "%";
        java.util.MissingFormatArgumentException ex = new java.util.MissingFormatArgumentException(specifier);
        String message = ex.getMessage();
        String expected = "Format specifier '" + specifier + "'";
        if (!expected.equals(message)) {
            throw new AssertionError("getMessage() returned wrong value: " + message);
        }
    }
}
