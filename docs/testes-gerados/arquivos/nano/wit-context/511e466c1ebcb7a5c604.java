/* @test    @summary Test for sun.nio.cs.StreamEncoder.write(String, int, int) with various inputs    @run main StreamEncoderWitupTest */

import java.io.*;
import sun.nio.cs.*;

public class StreamEncoderWitupTest {

    public static void main(String[] args) throws Exception {
        testWriteWithValidInput();
        testWriteWithNegativeLength();
        testWriteWithOutOfBoundsOffset();
    }

    private static void testWriteWithValidInput() throws Exception {
        String testStr = "Hello, World!";
        int off = 0;
        int len = testStr.length();
        StreamEncoder encoder = createStreamEncoder();
        try {
            encoder.write(testStr, off, len);
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in valid input test", e);
        }
    }

    private static void testWriteWithNegativeLength() {
        String testStr = "Test";
        int off = 0;
        int len = -1;
        StreamEncoder encoder = createStreamEncoder();
        try {
            encoder.write(testStr, off, len);
            throw new AssertionError("Expected IndexOutOfBoundsException for negative length");
        } catch (IndexOutOfBoundsException e) {
            // Expected exception
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in negative length test", e);
        }
    }

    private static void testWriteWithOutOfBoundsOffset() {
        String testStr = "Test";
        int off = 3;
        int len = 3;
        StreamEncoder encoder = createStreamEncoder();
        try {
            encoder.write(testStr, off, len);
            throw new AssertionError("Expected StringIndexOutOfBoundsException for out-of-bounds offset/length");
        } catch (StringIndexOutOfBoundsException e) {
            // Expected exception
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in out-of-bounds offset test", e);
        }
    }

    private static StreamEncoder createStreamEncoder() {
        try {
            return StreamEncoder.forOutputStreamWriter(new ByteArrayOutputStream(), null, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 encoding not supported", e);
        }
    }
}
