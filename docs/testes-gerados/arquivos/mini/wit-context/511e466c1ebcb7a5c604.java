/*
 * @test
 * @summary Test sun.nio.cs.StreamEncoder.write(String, int, int) for exception paths and normal behavior
 * @run main StreamEncoderWitupTest
 */

import java.io.*;
import java.nio.charset.*;
import sun.nio.cs.StreamEncoder;

public class StreamEncoderWitupTest {
    public static void main(String[] args) throws Exception {
        testWriteLenNegative();
        testWriteOffLenBounds();
        testWriteNormal();
    }

    private static void testWriteLenNegative() {
        StreamEncoder se = createDummyStreamEncoder();
        try {
            se.write("abc", 0, -1);
            throw new RuntimeException("Expected IndexOutOfBoundsException for len < 0");
        } catch (IndexOutOfBoundsException expected) {
            // expected
        } catch (IOException ioe) {
            throw new RuntimeException("Unexpected IOException", ioe);
        }
    }

    private static void testWriteOffLenBounds() {
        StreamEncoder se = createDummyStreamEncoder();
        String s = "abc";
        // off < 0
        try {
            se.write(s, -1, 1);
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for off < 0");
        } catch (StringIndexOutOfBoundsException expected) {
            // expected
        } catch (IOException ioe) {
            throw new RuntimeException("Unexpected IOException", ioe);
        }
        // off + len > s.length()
        try {
            se.write(s, 2, 2);
            throw new RuntimeException("Expected StringIndexOutOfBoundsException for off+len > str.length()");
        } catch (StringIndexOutOfBoundsException expected) {
            // expected
        } catch (IOException ioe) {
            throw new RuntimeException("Unexpected IOException", ioe);
        }
    }

    private static void testWriteNormal() throws IOException {
        StreamEncoder se = createDummyStreamEncoder();
        String s = "hello world";
        se.write(s, 0, s.length());
        // No exception expected
    }

    private static StreamEncoder createDummyStreamEncoder() {
        // Use forOutputStreamWriter factory with ByteArrayOutputStream and default charset
        try {
            return StreamEncoder.forOutputStreamWriter(new ByteArrayOutputStream(), new Object(), Charset.defaultCharset());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Failed to create StreamEncoder", e);
        }
    }
}
