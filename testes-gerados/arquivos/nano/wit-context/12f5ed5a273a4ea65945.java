/*
 * @test
 * @summary Tests for java.util.jar.Manifest.read(byte[], int, int)
 * @run main ManifestWitupTest
 */

import java.util.jar.Manifest;
import java.io.IOException;

public class ManifestWitupTest {

    public static void main(String[] args) throws IOException {
        testReadNormal();
        testReadWithNegativeOffset();
        testReadWithLargeLength();
    }

    private static void testReadNormal() throws IOException {
        byte[] data = {0, 1, 2, 3, 4, 5};
        Manifest manifest = new Manifest();
        byte[] buffer = new byte[10];
        int result = manifest.read(data, 0, data.length);
        assert result == data.length : "Expected " + data.length + " but got " + result;
    }

    private static void testReadWithNegativeOffset() {
        byte[] data = {0, 1, 2};
        Manifest manifest = new Manifest();
        try {
            manifest.read(data, -1, 2);
            assert false : "Expected IndexOutOfBoundsException for negative offset";
        } catch (IndexOutOfBoundsException e) {
            // expected
        } catch (IOException e) {
            assert false : "Unexpected IOException";
        }
    }

    private static void testReadWithLargeLength() throws IOException {
        byte[] data = {0, 1, 2};
        Manifest manifest = new Manifest();
        byte[] buffer = new byte[2];
        int result = manifest.read(data, 0, 10);
        assert result == data.length : "Expected " + data.length + " but got " + result;
    }
}
