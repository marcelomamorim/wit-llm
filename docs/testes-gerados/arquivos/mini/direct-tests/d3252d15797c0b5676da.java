/*
 * @test
 * @summary Test OutputStreamWriter(OutputStream, Charset) constructor behavior
 * @run main OutputStreamWriterWitupTest
 */

import java.io.OutputStreamWriter;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class OutputStreamWriterWitupTest {
    public static void main(String[] args) throws Exception {
        testNullCharsetThrows();
        testValidCharsetCreatesWriter();
    }

    private static void testNullCharsetThrows() {
        OutputStream os = new ByteArrayOutputStream();
        try {
            new OutputStreamWriter(os, null);
            throw new RuntimeException("Expected NullPointerException for null Charset");
        } catch (NullPointerException e) {
            if (!"charset".equals(e.getMessage())) {
                throw new RuntimeException("NullPointerException message mismatch", e);
            }
        }
    }

    private static void testValidCharsetCreatesWriter() throws Exception {
        OutputStream os = new ByteArrayOutputStream();
        Charset cs = StandardCharsets.UTF_8;
        OutputStreamWriter writer = new OutputStreamWriter(os, cs);
        // Write some characters and flush
        writer.write("test");
        writer.flush();
        writer.close();
        // No exception means success
    }
}
