/*
 * @test
 * @summary Test OutputStreamWriter(OutputStream, CharsetEncoder) constructor for null and valid encoder
 * @run main OutputStreamWriterWitupTest
 */

import java.io.OutputStreamWriter;
import java.io.OutputStream;
import java.nio.charset.CharsetEncoder;
import java.io.UnsupportedEncodingException;

public class OutputStreamWriterWitupTest {
    public static void main(String[] args) throws Exception {
        testNullCharsetEncoder();
        testValidCharsetEncoder();
    }

    private static void testNullCharsetEncoder() {
        OutputStream dummyOut = new OutputStream() {
            public void write(int b) {
                // no-op
            }
        };

        try {
            new OutputStreamWriter(dummyOut, (CharsetEncoder) null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            if (!"charset encoder".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected NullPointerException message: " + e.getMessage());
            }
        }
    }

    private static void testValidCharsetEncoder() throws Exception {
        OutputStream dummyOut = new OutputStream() {
            public void write(int b) {
                // no-op
            }
        };

        // Use the default charset encoder from the platform default charset
        java.nio.charset.Charset cs = java.nio.charset.Charset.defaultCharset();
        CharsetEncoder encoder = cs.newEncoder();

        OutputStreamWriter writer = new OutputStreamWriter(dummyOut, encoder);
        if (writer == null) {
            throw new RuntimeException("OutputStreamWriter constructor returned null");
        }
    }
}
