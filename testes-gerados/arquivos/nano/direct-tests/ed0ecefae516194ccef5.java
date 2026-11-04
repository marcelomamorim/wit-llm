/* @test    @summary Test Manifest.readLine(byte[] b) method for correct behavior and exception handling.    @run main ManifestWitupTest */

import java.io.IOException;
import java.util.jar.Manifest;

public class ManifestWitupTest {

    public static void main(String[] args) {
        testReadLineNormal();
        testReadLineEmpty();
        testReadLineNullBuffer();
    }

    static void testReadLineNormal() {
        Manifest manifest = new Manifest();
        byte[] buffer = new byte[10];
        try {
            int result = manifest.readLine(buffer);
            // Since readLine implementation calls readLine(b, 0, b.length),
            // and no data is set, result should be -1 indicating end of stream.
            assert result == -1 : "Expected -1 for empty stream, got " + result;
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testReadLineNormal", e);
        }
    }

    static void testReadLineEmpty() {
        Manifest manifest = new Manifest();
        byte[] buffer = new byte[0];
        try {
            int result = manifest.readLine(buffer);
            assert result == -1 : "Expected -1 for empty buffer, got " + result;
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testReadLineEmpty", e);
        }
    }

    static void testReadLineNullBuffer() {
        Manifest manifest = new Manifest();
        try {
            manifest.readLine(null);
            throw new AssertionError("Expected NullPointerException for null buffer");
        } catch (NullPointerException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException in testReadLineNullBuffer", e);
        }
    }
}
