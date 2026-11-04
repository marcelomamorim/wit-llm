/*
 * @test
 * @summary Test InflaterOutputStream(OutputStream out) constructor basic behavior
 * @run main InflaterOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.InflaterOutputStream;
import java.util.zip.Inflater;

public class InflaterOutputStreamWitupTest {
    public static void main(String[] args) throws IOException {
        // Test normal construction with valid OutputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InflaterOutputStream ios = new InflaterOutputStream(baos);
        if (ios == null) {
            throw new AssertionError("InflaterOutputStream constructor returned null");
        }
        // The usesDefaultInflater field is private, so test observable behavior:
        // Write some data and close to check no exceptions thrown
        try {
            ios.write(new byte[] {0, 1, 2, 3, 4}, 0, 5);
            ios.close();
        } catch (IOException e) {
            throw new AssertionError("IOException thrown during write/close: " + e);
        }

        // Test that constructor throws NullPointerException if null OutputStream passed
        try {
            new InflaterOutputStream(null);
            throw new AssertionError("Expected NullPointerException for null OutputStream");
        } catch (NullPointerException expected) {
            // expected
        }
    }
}
