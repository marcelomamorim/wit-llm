/*
 * @test
 * @summary Test java.nio.channels.Channels.newChannel(OutputStream) for null and normal cases
 * @run main ChannelsWitupTest
 */

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.channels.WritableByteChannel;
import java.nio.channels.Channels;
import java.util.Objects;

public class ChannelsWitupTest {

    public static void main(String[] args) throws Exception {
        testNullOutputStream();
        testFileOutputStreamReturnsChannel();
        testOtherOutputStreamReturnsWritableByteChannelImpl();
    }

    private static void testNullOutputStream() {
        try {
            Channels.newChannel(null);
            throw new RuntimeException("Expected NullPointerException for null output stream");
        } catch (NullPointerException npe) {
            // expected
            if (!"out".equals(npe.getMessage())) {
                throw new RuntimeException("NullPointerException message mismatch", npe);
            }
        }
    }

    private static void testFileOutputStreamReturnsChannel() throws Exception {
        // Create a temporary file to get a FileOutputStream
        java.io.File tempFile = java.io.File.createTempFile("ChannelsWitupTest", ".tmp");
        tempFile.deleteOnExit();
        FileOutputStream fos = new FileOutputStream(tempFile);
        try {
            WritableByteChannel channel = Channels.newChannel(fos);
            if (channel == null) {
                throw new RuntimeException("newChannel returned null for FileOutputStream");
            }
            // The returned channel should be the same as fos.getChannel()
            if (channel != fos.getChannel()) {
                throw new RuntimeException("newChannel did not return fos.getChannel() instance");
            }
        } finally {
            fos.close();
        }
    }

    private static void testOtherOutputStreamReturnsWritableByteChannelImpl() throws Exception {
        OutputStream os = new OutputStream() {
            public void write(int b) {
                // do nothing
            }
        };
        WritableByteChannel channel = Channels.newChannel(os);
        if (channel == null) {
            throw new RuntimeException("newChannel returned null for generic OutputStream");
        }
        // We cannot assert the concrete class name because WritableByteChannelImpl is package-private
        // but we can assert that it is not the same as a FileOutputStream channel
        if (channel instanceof java.nio.channels.FileChannel) {
            throw new RuntimeException("newChannel returned FileChannel for generic OutputStream");
        }
    }
}
