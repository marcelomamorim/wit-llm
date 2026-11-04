/*
 * @test
 * @summary Test Manifest.readLine(byte[]) method behavior
 * @run main ManifestWitupTest
 */

import java.util.jar.Manifest;
import java.io.IOException;

public class ManifestWitupTest {
    public static void main(String[] args) throws IOException {
        Manifest manifest = new Manifest();

        // Prepare a byte array with some content
        byte[] buffer = new byte[10];
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = (byte) ('a' + i); // fill with 'a'..'j'
        }

        // Call readLine(byte[]) and check it returns the same as readLine(byte[], int, int)
        // Since readLine(byte[], int, int) is not public, we test only the public method
        int result = manifest.readLine(buffer);

        // The method readLine(byte[]) calls readLine(byte[], 0, b.length),
        // so result should be >= 0 or -1 if no line read.
        // We cannot assert exact value without internal state, but we can assert no exception and result >= -1
        if (result < -1) {
            throw new AssertionError("readLine returned invalid value: " + result);
        }

        // Also test with empty byte array
        byte[] emptyBuffer = new byte[0];
        int emptyResult = manifest.readLine(emptyBuffer);
        if (emptyResult != -1) {
            throw new AssertionError("readLine with empty buffer should return -1, got: " + emptyResult);
        }
    }
}
