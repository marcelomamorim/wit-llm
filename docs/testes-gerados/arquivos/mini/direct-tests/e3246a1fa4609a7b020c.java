/*
 * @test
 * @summary Test UnknownFormatConversionException constructor and methods
 * @run main UnknownFormatConversionExceptionWitupTest
 */

public class UnknownFormatConversionExceptionWitupTest {
    public static void main(String[] args) {
        testConstructorAndGetters();
        testConstructorNullArgument();
    }

    private static void testConstructorAndGetters() {
        String conv = "x";
        java.util.UnknownFormatConversionException ex = new java.util.UnknownFormatConversionException(conv);
        if (!conv.equals(ex.getConversion())) {
            throw new RuntimeException("getConversion() returned unexpected value");
        }
        String msg = ex.getMessage();
        if (msg == null || !msg.contains(conv)) {
            throw new RuntimeException("getMessage() does not contain conversion string");
        }
    }

    private static void testConstructorNullArgument() {
        try {
            new java.util.UnknownFormatConversionException(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        }
    }
}
