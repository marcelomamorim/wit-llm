/*
 * @test
 * @summary Test UnknownFormatConversionException constructor and behavior
 * @run main UnknownFormatConversionExceptionWitupTest
 */

public class UnknownFormatConversionExceptionWitupTest {
    public static void main(String[] args) {
        testConstructorWithValidString();
        testConstructorWithNullThrowsNPE();
        testGetConversionReturnsSameString();
        testGetMessageFormat();
    }

    private static void testConstructorWithValidString() {
        String conv = "x";
        java.util.UnknownFormatConversionException ex = new java.util.UnknownFormatConversionException(conv);
        if (!conv.equals(ex.getConversion())) {
            throw new AssertionError("getConversion() did not return the expected string");
        }
    }

    private static void testConstructorWithNullThrowsNPE() {
        try {
            new java.util.UnknownFormatConversionException(null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // Expected exception
        }
    }

    private static void testGetConversionReturnsSameString() {
        String conv = "testConv";
        java.util.UnknownFormatConversionException ex = new java.util.UnknownFormatConversionException(conv);
        String returned = ex.getConversion();
        if (!conv.equals(returned)) {
            throw new AssertionError("getConversion() returned different string than constructor argument");
        }
    }

    private static void testGetMessageFormat() {
        String conv = "z";
        java.util.UnknownFormatConversionException ex = new java.util.UnknownFormatConversionException(conv);
        String msg = ex.getMessage();
        String expected = String.format("Conversion = '%s'", conv);
        if (!expected.equals(msg)) {
            throw new AssertionError("getMessage() did not return expected formatted string");
        }
    }
}
