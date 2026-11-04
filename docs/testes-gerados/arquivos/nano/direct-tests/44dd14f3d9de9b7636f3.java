/*
 * @test
 * @summary Test for Channels.newInputStream method.
 * @run main ChannelsWitupTest
 */

import java.io.InputStream;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.Pipe;

public class ChannelsWitupTest {

    public static void main(String[] args) throws Exception {
        testNewInputStreamWithValidChannel();
        testNewInputStreamWithNullChannel();
    }

    static void testNewInputStreamWithValidChannel() throws Exception {
        Pipe pipe = Pipe.open();
        ReadableByteChannel channel = pipe.source();
        InputStream is = java.nio.channels.Channels.newInputStream(channel);
        if (is == null) {
            throw new AssertionError("InputStream should not be null for valid channel");
        }
        // Read from the stream to ensure it works
        byte[] buffer = new byte[1];
        int readBytes = is.read(buffer);
        // Since the pipe is empty, read should return -1
        if (readBytes != -1) {
            throw new AssertionError("Expected end of stream, but read: " + readBytes);
        }
        is.close();
        channel.close();
    }

    static void testNewInputStreamWithNullChannel() {
        try {
            java.nio.channels.Channels.newInputStream(null);
            throw new AssertionError("Expected NullPointerException for null channel");
        } catch (NullPointerException e) {
            // Expected exception
        }
    }
}
