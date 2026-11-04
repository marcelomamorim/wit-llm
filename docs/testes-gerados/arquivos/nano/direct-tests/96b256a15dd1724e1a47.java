/* @test    @summary Test constructor OutputStreamWriter(OutputStream, CharsetEncoder) for correct behavior and null handling    @run main OutputStreamWriterWitupTest */

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.CharsetEncoder;
import java.io.ByteArrayOutputStream;

public class OutputStreamWriterWitupTest {

    public static void main(String[] args) throws Exception {
        testConstructorWithValidEncoder();
        testConstructorWithNullEncoder();
    }

    static void testConstructorWithValidEncoder() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        CharsetEncoder encoder = java.nio.charset.StandardCharsets.UTF_8.newEncoder();
        OutputStreamWriter writer = new OutputStreamWriter(baos, encoder);
        writer.write("test");
        writer.flush();
        String result = baos.toString("UTF-8");
        if (!"test".equals(result)) {
            throw new AssertionError("Expected 'test', but got: " + result);
        }
    }

    static void testConstructorWithNullEncoder() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            new OutputStreamWriter(baos, (CharsetEncoder) null);
            throw new AssertionError("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            if (!"charset encoder".equals(e.getMessage())) {
                throw new AssertionError("Unexpected exception message: " + e.getMessage());
            }
        }
    }
}
