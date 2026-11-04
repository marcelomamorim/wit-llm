/*
 * @test
 * @summary Witup-generated tests for Channels.newChannel(InputStream)
 * @run main ChannelsWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class ChannelsWitupTest {

    public static void main(String[] args) throws Exception {
        testNullInputThrowsNullPointerException();
        testFileInputStreamReturnsUnderlyingChannel();
        testGenericStreamChannelReadsAllBytes();
        testChannelCloseClosesChannel();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullInputThrowsNullPointerException() {
        try {
            Channels.newChannel((InputStream) null);
            throw new RuntimeException("expected NullPointerException for null input");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testFileInputStreamReturnsUnderlyingChannel() throws IOException {
        File tmp = File.createTempFile("channels-witup", ".bin");
        try {
            try (FileInputStream fis = new FileInputStream(tmp)) {
                ReadableByteChannel ch = Channels.newChannel(fis);
                check(ch == fis.getChannel(), "newChannel(FileInputStream) must return the stream's channel");
            }
        } finally {
            tmp.delete();
        }
    }

    static void testGenericStreamChannelReadsAllBytes() throws IOException {
        byte[] data = new byte[] {'a', 'b', 'c'};
        InputStream in = new ByteArrayInputStream(data);
        ReadableByteChannel ch = Channels.newChannel(in);
        check(ch != null, "newChannel must not return null");

        ByteBuffer buf = ByteBuffer.allocate(16);
        int total = 0;
        int n;
        while ((n = ch.read(buf)) != -1) {
            total += n;
        }
        check(total == data.length, "expected " + data.length + " bytes, got " + total);

        buf.flip();
        check(buf.remaining() == data.length, "unexpected remaining byte count");
        for (int i = 0; i < data.length; i++) {
            check(buf.get() == data[i], "byte mismatch at index " + i);
        }
        ch.close();
    }

    static void testChannelCloseClosesChannel() throws IOException {
        ReadableByteChannel ch = Channels.newChannel(new ByteArrayInputStream(new byte[] {1}));
        check(ch.isOpen(), "channel should be open after creation");
        ch.close();
        check(!ch.isOpen(), "channel should be closed after close()");
    }
}
