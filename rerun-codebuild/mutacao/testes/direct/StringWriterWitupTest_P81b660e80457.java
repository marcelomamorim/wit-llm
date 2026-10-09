/* @test    @summary Test StringWriter.write(String, int, int) method for correct buffer appending behavior    @run main StringWriterWitupTest_P81b660e80457 */

import java.io.StringWriter;

public class StringWriterWitupTest_P81b660e80457 {

    public static void main(String[] args) {
        testWriteMethodNormalCase();
        testWriteMethodZeroLength();
        testWriteMethodNegativeOffset();
        testWriteMethodNegativeLength();
        testWriteMethodOffsetExceedsBuffer();
        testWriteMethodLengthExceedsBuffer();
    }

    static void testWriteMethodNormalCase() {
        StringWriter sw = new StringWriter();
        String testStr = "HelloWorld";
        int off = 2;
        int len = 5;
        sw.write(testStr, off, len);
        String result = sw.toString();
        assert result.equals("lloWo") : "Expected 'lloWo', but got '" + result + "'";
    }

    static void testWriteMethodZeroLength() {
        StringWriter sw = new StringWriter();
        String testStr = "Test";
        sw.write(testStr, 0, 0);
        String result = sw.toString();
        assert result.equals("") : "Expected empty string, but got '" + result + "'";
    }

    static void testWriteMethodNegativeOffset() {
        StringWriter sw = new StringWriter();
        String testStr = "Negative";
        try {
            sw.write(testStr, -1, 3);
            assert false : "Expected IndexOutOfBoundsException for negative offset";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testWriteMethodNegativeLength() {
        StringWriter sw = new StringWriter();
        String testStr = "Negative";
        try {
            sw.write(testStr, 0, -1);
            assert false : "Expected IndexOutOfBoundsException for negative length";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testWriteMethodOffsetExceedsBuffer() {
        StringWriter sw = new StringWriter();
        String testStr = "Exceeds";
        try {
            sw.write(testStr, 10, 2);
            assert false : "Expected IndexOutOfBoundsException for offset exceeding string length";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }

    static void testWriteMethodLengthExceedsBuffer() {
        StringWriter sw = new StringWriter();
        String testStr = "Length";
        try {
            sw.write(testStr, 2, 10);
            assert false : "Expected IndexOutOfBoundsException for length exceeding string bounds";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
