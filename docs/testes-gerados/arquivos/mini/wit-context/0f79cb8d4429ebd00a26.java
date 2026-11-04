/*
 * @test
 * @summary Test OutputStreamWriter(OutputStream out) constructor behavior
 * @run main OutputStreamWriterWitupTest
 */

import java.io.OutputStreamWriter;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

public class OutputStreamWriterWitupTest {
    public static void main(String[] args) throws Exception {
        // Test normal construction with ByteArrayOutputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(baos);
        if (osw == null) {
            throw new RuntimeException("OutputStreamWriter constructor returned null");
        }

        // Test that writing characters works and flushes bytes
        String testStr = "abc";
        osw.write(testStr);
        osw.flush();
        byte[] bytes = baos.toByteArray();
        if (bytes.length == 0) {
            throw new RuntimeException("No bytes written after write and flush");
        }

        // Test that constructor throws Error wrapping UnsupportedEncodingException if encoding unsupported
        // This is hard to trigger with standard ByteArrayOutputStream and null charset in this constructor,
        // so we do not test this path here.

        // Test that constructor does not throw NullPointerException when out is non-null
        // and that it throws NullPointerException if out is null (not applicable here, as constructor requires non-null OutputStream)

        // Test that passing null OutputStream throws NullPointerException
        try {
            new OutputStreamWriter(null);
            throw new RuntimeException("Expected NullPointerException for null OutputStream");
        } catch (NullPointerException expected) {
            // expected
        }

        // No other exceptions expected
    }
}
