/*
 * @test
 * @summary Test java.nio.channels.Channels.newChannel(InputStream) behavior
 * @run main ChannelsWitupTest
 */

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.ByteArrayInputStream;
import java.nio.channels.ReadableByteChannel;
import java.util.Objects;

public class ChannelsWitupTest {

    public static void main(String[] args) throws Exception {
        testNullInputStream();
        testFileInputStreamReturnsFileChannel();
        testOtherInputStreamReturnsReadableByteChannelImpl();
    }

    private static void testNullInputStream() {
        try {
            java.nio.channels.Channels.newChannel(null);
            throw new RuntimeException("Expected NullPointerException for null input");
        } catch (NullPointerException npe) {
            if (!"in".equals(npe.getMessage())) {
                throw new RuntimeException("NullPointerException message mismatch", npe);
            }
        }
    }

    private static void testFileInputStreamReturnsFileChannel() throws Exception {
        // Create a temporary file to get a FileInputStream
        java.io.File tempFile = java.io.File.createTempFile("ChannelsWitupTest", ".tmp");
        tempFile.deleteOnExit();
        try (FileInputStream fis = new FileInputStream(tempFile)) {
            ReadableByteChannel channel = java.nio.channels.Channels.newChannel(fis);
            if (channel == null) {
                throw new RuntimeException("Returned channel is null");
            }
            // The returned channel should be the same as fis.getChannel()
            if (channel != fis.getChannel()) {
                throw new RuntimeException("Expected channel to be FileInputStream.getChannel()");
            }
        }
    }

    private static void testOtherInputStreamReturnsReadableByteChannelImpl() throws Exception {
        byte[] data = {1, 2, 3};
        InputStream bais = new ByteArrayInputStream(data);
        ReadableByteChannel channel = java.nio.channels.Channels.newChannel(bais);
        if (channel == null) {
            throw new RuntimeException("Returned channel is null");
        }
        // The returned channel should not be the same as bais
        if (channel == bais) {
            throw new RuntimeException("Returned channel should not be the same as input stream");
        }
        // The returned channel should be open and readable
        if (!channel.isOpen()) {
            throw new RuntimeException("Returned channel should be open");
        }
        // Try reading from the channel
        java.nio.ByteBuffer buffer = java.nio.ByteBuffer.allocate(10);
        int readBytes = channel.read(buffer);
        if (readBytes != data.length) {
            throw new RuntimeException("Expected to read " + data.length + " bytes, but read " + readBytes);
        }
        // Close channel
        channel.close();
        if (channel.isOpen()) {
            throw new RuntimeException("Channel should be closed after close()");
        }
    }
}
