/*
 * @test
 * @summary Tests OutputStreamWriter(OutputStream, CharsetEncoder) null check and encoding behavior
 * @run main OutputStreamWriterWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class OutputStreamWriterWitupTest {

    public static void main(String[] args) throws Exception {
        testNullEncoderThrowsNPE();
        testAsciiEncoderWritesBytes();
        testUtf8EncoderWritesBytes();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullEncoderThrowsNPE() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            new OutputStreamWriter(bos, (CharsetEncoder) null);
            throw new RuntimeException("Expected NullPointerException");
        } catch (NullPointerException e) {
            check("charset encoder".equals(e.getMessage()),
                  "Unexpected NPE message: " + e.getMessage());
        }
    }

    static void testAsciiEncoderWritesBytes() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        CharsetEncoder enc = StandardCharsets.US_ASCII.newEncoder();
        Writer w = new OutputStreamWriter(bos, enc);
        w.write("abc");
        w.flush();
        check(Arrays.equals("abc".getBytes(StandardCharsets.US_ASCII), bos.toByteArray()),
              "ASCII encoding mismatch");
        w.close();
    }

    static void testUtf8EncoderWritesBytes() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        CharsetEncoder enc = StandardCharsets.UTF_8.newEncoder();
        Writer w = new OutputStreamWriter(bos, enc);
        String text = "h\u00e9llo";
        w.write(text);
        w.flush();
        check(Arrays.equals(text.getBytes(StandardCharsets.UTF_8), bos.toByteArray()),
              "UTF-8 encoding mismatch");
        w.close();
    }
}
