/*
 * @test
 * @summary Test for OutputStreamWriter constructor with CharsetEncoder parameter
 * @run main OutputStreamWriterWitupTest
 */

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.io.ByteArrayOutputStream;

public class OutputStreamWriterWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorWithNullEncoder();
        testConstructorWithValidEncoder();
    }

    private static void testConstructorWithNullEncoder() {
        OutputStream out = new ByteArrayOutputStream();
        try {
            new OutputStreamWriter(out, (CharsetEncoder) null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            // Expected exception
            assert e.getMessage().equals("charset encoder") : "Unexpected message: " + e.getMessage();
        }
    }

    private static void testConstructorWithValidEncoder() throws Exception {
        OutputStream out = new ByteArrayOutputStream();
        Charset charset = Charset.forName("UTF-8");
        CharsetEncoder encoder = charset.newEncoder();
        OutputStreamWriter writer = new OutputStreamWriter(out, encoder);
        assert writer != null : "OutputStreamWriter should be created successfully";
        // Additional observable behavior: write and flush
        String testString = "hello";
        writer.write(testString);
        writer.flush();
        // Verify that the underlying stream has data
        byte[] data = ((ByteArrayOutputStream) out).toByteArray();
        assert data.length > 0 : "Underlying stream should contain data";
    }
}
