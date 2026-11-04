/*
 * @test
 * @summary Test java.util.jar.Manifest.read(byte[], int, int) method behavior
 * @run main ManifestWitupTest
 */

import java.util.jar.Manifest;
import java.io.IOException;

public class ManifestWitupTest {
    public static void main(String[] args) throws Exception {
        testReadReturnsMinusOneOnEmptyStream();
        testReadCopiesDataCorrectly();
        testReadDelegatesToInputStreamWhenLenExceedsBuffer();
    }

    private static void testReadReturnsMinusOneOnEmptyStream() throws IOException {
        // Create a Manifest with empty InputStream to force read() to return -1
        Manifest manifest = new Manifest(new java.io.ByteArrayInputStream(new byte[0]));

        // Use reflection to access the read(byte[], int, int) method
        // Because read(byte[], int, int) is package-private, we can call it via subclass
        ManifestReadWrapper wrapper = new ManifestReadWrapper(manifest);

        byte[] buffer = new byte[10];
        int result = wrapper.read(buffer, 0, buffer.length);

        if (result != -1) {
            throw new AssertionError("Expected -1 when reading from empty stream, got: " + result);
        }
    }

    private static void testReadCopiesDataCorrectly() throws IOException {
        // Prepare data to read
        byte[] data = new byte[] {1, 2, 3, 4, 5};
        Manifest manifest = new Manifest(new java.io.ByteArrayInputStream(data));
        ManifestReadWrapper wrapper = new ManifestReadWrapper(manifest);

        byte[] buffer = new byte[5];
        int readBytes = wrapper.read(buffer, 0, buffer.length);

        if (readBytes != data.length) {
            throw new AssertionError("Expected to read " + data.length + " bytes, got " + readBytes);
        }

        for (int i = 0; i < data.length; i++) {
            if (buffer[i] != data[i]) {
                throw new AssertionError("Data mismatch at index " + i + ": expected " + data[i] + ", got " + buffer[i]);
            }
        }
    }

    private static void testReadDelegatesToInputStreamWhenLenExceedsBuffer() throws IOException {
        // Create a custom InputStream that tracks if read(byte[], int, int) is called
        TrackingInputStream tis = new TrackingInputStream(new java.io.ByteArrayInputStream(new byte[20]));
        ManifestReadWrapper wrapper = new ManifestReadWrapper(tis);

        byte[] buffer = new byte[50]; // larger than internal buffer
        int readBytes = wrapper.read(buffer, 0, buffer.length);

        if (!tis.readCalled) {
            throw new AssertionError("Expected read(byte[], int, int) to be called on underlying InputStream");
        }

        if (readBytes <= 0) {
            throw new AssertionError("Expected positive number of bytes read, got " + readBytes);
        }
    }

    // Helper subclass to expose the package-private read method
    static class ManifestReadWrapper extends Manifest {
        ManifestReadWrapper(java.io.InputStream is) throws IOException {
            super(is);
        }

        int read(byte[] b, int off, int len) throws IOException {
            return super.read(b, off, len);
        }
    }

    // Helper InputStream to track read calls
    static class TrackingInputStream extends java.io.FilterInputStream {
        boolean readCalled = false;

        protected TrackingInputStream(java.io.InputStream in) {
            super(in);
        }

        @Override
        public int read(byte[] b, int off, int len) throws IOException {
            readCalled = true;
            return super.read(b, off, len);
        }
    }
}
