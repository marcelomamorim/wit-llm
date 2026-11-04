/*
 * @test
 * @summary Test OutputStreamWriter(OutputStream, Charset) constructor for null Charset and normal usage
 * @run main OutputStreamWriterWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class OutputStreamWriterWitupTest {
    public static void main(String[] args) throws Exception {
        testNullCharsetThrows();
        testValidCharsetDoesNotThrow();
    }

    private static void testNullCharsetThrows() {
        OutputStream out = new ByteArrayOutputStream();
        try {
            new OutputStreamWriter(out, (Charset) null);
            throw new RuntimeException("Expected NullPointerException for null Charset");
        } catch (NullPointerException e) {
            if (!"charset".equals(e.getMessage())) {
                throw new RuntimeException("NullPointerException message mismatch", e);
            }
        }
    }

    private static void testValidCharsetDoesNotThrow() throws Exception {
        OutputStream out = new ByteArrayOutputStream();
        Charset cs = Charset.forName("UTF-8");
        OutputStreamWriter writer = new OutputStreamWriter(out, cs);
        // Write some characters and flush to verify basic functionality
        writer.write('A');
        writer.flush();
        writer.close();
    }
}
