/*
 * @test
 * @summary Test java.nio.channels.Channels.newChannel(InputStream in) for various InputStream implementations
 * @run main ChannelsWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.channels.ReadableByteChannel;
import java.io.IOException;

public class ChannelsWitupTest {

    public static void main(String[] args) {
        testByteArrayInputStream();
        testFileInputStream();
        testNullInputStream();
    }

    static void testByteArrayInputStream() {
        byte[] data = {1, 2, 3, 4, 5};
        InputStream in = new ByteArrayInputStream(data);
        ReadableByteChannel channel = null;
        try {
            channel = java.nio.channels.Channels.newChannel(in);
            if (channel == null) {
                throw new AssertionError("Channel should not be null for ByteArrayInputStream");
            }
            byte[] buffer = new byte[5];
            int readBytes = channel.read(java.nio.ByteBuffer.wrap(buffer));
            if (readBytes != 5) {
                throw new AssertionError("Expected to read 5 bytes, but read " + readBytes);
            }
            for (int i = 0; i < 5; i++) {
                if (buffer[i] != data[i]) {
                    throw new AssertionError("Data mismatch at index " + i);
                }
            }
        } catch (IOException e) {
            throw new AssertionError("IOException during ByteArrayInputStream test", e);
        } finally {
            try {
                if (channel != null) {
                    channel.close();
                }
            } catch (IOException e) {
                // ignore
            }
        }
    }

    static void testFileInputStream() {
        // Using a dummy FileInputStream with a real file is complex; instead, test with a real file if available.
        // For demonstration, create a temporary file.
        try {
            java.io.File tempFile = java.io.File.createTempFile("test", ".txt");
            tempFile.deleteOnExit();
            try (java.io.FileOutputStream fos = new java.io.FileOutputStream(tempFile)) {
                fos.write(new byte[]{10, 20, 30, 40, 50});
            }
            try (FileInputStream fis = new FileInputStream(tempFile)) {
                ReadableByteChannel channel = java.nio.channels.Channels.newChannel(fis);
                if (channel == null) {
                    throw new AssertionError("Channel should not be null for FileInputStream");
                }
                java.nio.ByteBuffer buffer = java.nio.ByteBuffer.allocate(5);
                int readBytes = channel.read(buffer);
                if (readBytes != 5) {
                    throw new AssertionError("Expected to read 5 bytes, but read " + readBytes);
                }
                buffer.flip();
                byte[] readData = new byte[5];
                buffer.get(readData);
                byte[] expected = {10, 20, 30, 40, 50};
                for (int i = 0; i < 5; i++) {
                    if (readData[i] != expected[i]) {
                        throw new AssertionError("Data mismatch at index " + i);
                    }
                }
            }
        } catch (IOException e) {
            throw new AssertionError("IOException during FileInputStream test", e);
        }
    }

    static void testNullInputStream() {
        try {
            java.nio.channels.Channels.newChannel(null);
            throw new AssertionError("Expected NullPointerException for null InputStream");
        } catch (NullPointerException e) {
            // expected
        }
    }
}
