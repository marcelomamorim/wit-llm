/*
 * @test
 * @summary Test MissingFormatArgumentException constructor and behavior
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
        java.util.MissingFormatArgumentException ex = new java.util.MissingFormatArgumentException("d");
        if (ex == null) {
            throw new RuntimeException("Constructor returned null");
        }
    }

    private static void testConstructorWithNullThrowsNPE() {
        try {
            new java.util.MissingFormatArgumentException(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    private static void testGetFormatSpecifier() {
        java.util.MissingFormatArgumentException ex = new java.util.MissingFormatArgumentException("x");
        String spec = ex.getFormatSpecifier();
        if (!"x".equals(spec)) {
            throw new RuntimeException("getFormatSpecifier() returned '" + spec + "' instead of 'x'");
        }
    }

    private static void testGetMessage() {
        java.util.MissingFormatArgumentException ex = new java.util.MissingFormatArgumentException("y");
        String msg = ex.getMessage();
        if (!"Format specifier 'y'".equals(msg)) {
            throw new RuntimeException("getMessage() returned '" + msg + "' instead of 'Format specifier 'y''");
        }
    }
}
