/* @test    @summary Test StringWriter.write(String, int, int) method for correct behavior and exception handling    @run main StringWriterWitupTest_Pa7a30ed37de8 */

import java.io.StringWriter;

public class StringWriterWitupTest_Pa7a30ed37de8 {

    public static void main(String[] args) {
        testWriteValidRange();
        testWriteNegativeOffset();
        testWriteNegativeLength();
        testWriteOffsetExceedsBuffer();
        testWriteLengthExceedsBuffer();
        testWriteZeroLength();
    }

    static void testWriteValidRange() {
        StringWriter sw = new StringWriter();
        String testStr = "HelloWorld";
        try {
            sw.write(testStr, 0, 5); // Writes "Hello"
            sw.write(testStr, 5, 5); // Writes "World"
            String result = sw.toString();
            if (!"HelloWorld".equals(result)) {
                throw new AssertionError("Expected 'HelloWorld', but got: " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testWriteValidRange: " + e);
        }
    }

    static void testWriteNegativeOffset() {
        StringWriter sw = new StringWriter();
        String testStr = "Test";
        try {
            sw.write(testStr, -1, 2);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative offset");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }

    static void testWriteNegativeLength() {
        StringWriter sw = new StringWriter();
        String testStr = "Test";
        try {
            sw.write(testStr, 1, -2);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative length");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }

    static void testWriteOffsetExceedsBuffer() {
        StringWriter sw = new StringWriter();
        String testStr = "Test";
        try {
            sw.write(testStr, 5, 1);
            throw new AssertionError("Expected IndexOutOfBoundsException for offset exceeding buffer length");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }

    static void testWriteLengthExceedsBuffer() {
        StringWriter sw = new StringWriter();
        String testStr = "Test";
        try {
            sw.write(testStr, 2, 3); // offset=2, len=3, total=5 > length of testStr
            throw new AssertionError("Expected IndexOutOfBoundsException for length exceeding buffer");
        } catch (IndexOutOfBoundsException e) {
            // Expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception type: " + e);
        }
    }

    static void testWriteZeroLength() {
        StringWriter sw = new StringWriter();
        String testStr = "Test";
        try {
            sw.write(testStr, 0, 0); // Should not throw
            String result = sw.toString();
            if (!"".equals(result)) {
                throw new AssertionError("Expected empty string, but got: " + result);
            }
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception in testWriteZeroLength: " + e);
        }
    }
}
