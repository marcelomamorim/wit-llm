/*
 * @test
 * @summary Test sun.nio.cs.StreamEncoder.write(String, int, int) method
 * @run main StreamEncoderWitupTest
 */

import java.io.*;
import java.nio.charset.*;
import sun.nio.cs.StreamEncoder;

public class StreamEncoderWitupTest {
    public static void main(String[] args) throws Exception {
        testWriteValid();
        testWriteNegativeLen();
    }

    private static void testWriteValid() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        StreamEncoder se = StreamEncoder.forOutputStreamWriter(baos, new Object(), "UTF-8");

        String testStr = "Hello, world!";
        se.write(testStr, 0, testStr.length());
        se.flush();

        byte[] result = baos.toByteArray();
        String decoded = new String(result, "UTF-8");

        if (!testStr.equals(decoded)) {
            throw new RuntimeException("testWriteValid failed: expected '" + testStr + "' but got '" + decoded + "'");
        }

        se.close();
    }

    private static void testWriteNegativeLen() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        StreamEncoder se = StreamEncoder.forOutputStreamWriter(baos, new Object(), "UTF-8");

        String testStr = "Hello";
        boolean threw = false;
        try {
            se.write(testStr, 0, -1);
        } catch (IndexOutOfBoundsException e) {
            threw = true;
        }

        if (!threw) {
            throw new RuntimeException("testWriteNegativeLen failed: expected IndexOutOfBoundsException");
        }

        se.close();
    }
}
