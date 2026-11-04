/* @test    @summary Test the write method of StreamEncoder for normal and exceptional cases    @run main StreamEncoderWitupTest */

import java.io.*;
import sun.nio.cs.*;

public class StreamEncoderWitupTest {

    public static void main(String[] args) throws Exception {
        testWriteNormal();
        testWriteNegativeLength();
    }

    static void testWriteNormal() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String charsetName = "UTF-8";
        StreamEncoder encoder = StreamEncoder.forOutputStreamWriter(baos, new Object(), charsetName);
        String testStr = "Hello";
        encoder.write(testStr, 0, testStr.length());
        encoder.flushBuffer();
        String result = baos.toString("UTF-8");
        assert result.equals(testStr) : "Expected 'Hello', got '" + result + "'";
    }

    static void testWriteNegativeLength() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String charsetName = "UTF-8";
        StreamEncoder encoder = StreamEncoder.forOutputStreamWriter(baos, new Object(), charsetName);
        String testStr = "Test";
        try {
            encoder.write(testStr, 0, -1);
            assert false : "Expected IndexOutOfBoundsException";
        } catch (IndexOutOfBoundsException e) {
            // expected
        }
    }
}
