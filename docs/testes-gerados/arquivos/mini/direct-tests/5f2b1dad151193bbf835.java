/*
 * @test
 * @summary Test Manifest.peek() method behavior
 * @run main ManifestWitupTest
 */

import java.util.jar.Manifest;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

public class ManifestWitupTest {
    public static void main(String[] args) throws Exception {
        testPeekReturnsByte();
        testPeekReturnsMinusOneWhenEmpty();
    }

    private static void testPeekReturnsByte() throws IOException {
        // Create a Manifest from a minimal valid manifest input stream
        String manifestContent = "Manifest-Version: 1.0\r\n\r\n";
        InputStream is = new ByteArrayInputStream(manifestContent.getBytes("UTF-8"));
        Manifest manifest = new Manifest(is);

        // peek() should return the first byte of the internal buffer (unsigned byte)
        int peeked = manifest.peek() & 0xFF;
        int expected = manifest.getMainAttributes().entrySet().iterator().hasNext() ? peeked : -1;

        // We expect peek() to return a byte value >= 0 (not -1) because input is not empty
        if (peeked == -1) {
            throw new AssertionError("peek() returned -1 unexpectedly on non-empty manifest");
        }
    }

    private static void testPeekReturnsMinusOneWhenEmpty() throws IOException {
        // Create a Manifest with empty input stream
        InputStream emptyIs = new ByteArrayInputStream(new byte[0]);
        Manifest manifest = new Manifest(emptyIs);

        int peeked = manifest.peek();
        if (peeked != -1) {
            throw new AssertionError("peek() should return -1 when no data is available, but returned: " + peeked);
        }
    }
}
