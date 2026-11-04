/*
 * @test
 * @summary Test java.nio.channels.Channels.newChannel(OutputStream) behavior
 * @run main ChannelsWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.channels.WritableByteChannel;
import java.util.Objects;
import java.nio.channels.Channels;

public class ChannelsWitupTest {
    public static void main(String[] args) throws Exception {
        testNewChannelWithNull();
        testNewChannelWithFileOutputStream();
        testNewChannelWithOtherOutputStream();
    }

    private static void testNewChannelWithNull() {
        try {
            Channels.newChannel(null);
            throw new RuntimeException("Expected NullPointerException for null argument");
        } catch (NullPointerException npe) {
            // expected
            if (!"out".equals(npe.getMessage())) {
                throw new RuntimeException("NullPointerException message mismatch", npe);
            }
        }
    }

    private static void testNewChannelWithFileOutputStream() throws Exception {
        // Create a temporary file output stream
        java.io.File tempFile = java.io.File.createTempFile("ChannelsWitupTest", ".tmp");
        tempFile.deleteOnExit();
        FileOutputStream fos = new FileOutputStream(tempFile);
        try {
            WritableByteChannel channel = Channels.newChannel(fos);
            // The returned channel should be the same as fos.getChannel()
            if (channel != fos.getChannel()) {
                throw new RuntimeException("Expected channel to be fos.getChannel()");
            }
        } finally {
            fos.close();
        }
    }

    private static void testNewChannelWithOtherOutputStream() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        WritableByteChannel channel = Channels.newChannel(baos);
        if (channel == null) {
            throw new RuntimeException("newChannel returned null for ByteArrayOutputStream");
        }
        // The returned channel should not be the same as baos (which is not a channel)
        if (channel instanceof java.nio.channels.FileChannel) {
            throw new RuntimeException("Returned channel should not be a FileChannel for ByteArrayOutputStream");
        }
        // Test that writing through the channel writes to the underlying stream
        java.nio.ByteBuffer buffer = java.nio.ByteBuffer.wrap(new byte[] {65, 66, 67}); // ABC
        int written = channel.write(buffer);
        if (written != 3) {
            throw new RuntimeException("Expected to write 3 bytes, wrote " + written);
        }
        channel.close();
        byte[] data = baos.toByteArray();
        if (data.length != 3 || data[0] != 65 || data[1] != 66 || data[2] != 67) {
            throw new RuntimeException("Data written does not match expected bytes");
        }
    }
}
