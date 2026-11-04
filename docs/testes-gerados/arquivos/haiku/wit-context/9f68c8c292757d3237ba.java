/*
 * @test
 * @summary Witup-generated tests for Channels.write on a channel created by Channels.newChannel(OutputStream)
 * @run main ChannelsWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.WritableByteChannel;

public class ChannelsWitupTest {

    public static void main(String[] args) throws Exception {
        testWriteReturnsRemainingAndWritesBytes();
        testWriteZeroRemainingReturnsZero();
        testWriteOnClosedChannelThrowsClosedChannelException();
    }

    static void testWriteReturnsRemainingAndWritesBytes() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        WritableByteChannel ch = Channels.newChannel(out);
        byte[] data = {1, 2, 3, 4, 5};
        ByteBuffer src = ByteBuffer.wrap(data);
        int n = ch.write(src);
        check(n == 5, "expected 5 bytes written, got " + n);
        check(!src.hasRemaining(), "source buffer should be fully consumed");
        byte[] result = out.toByteArray();
        check(result.length == 5, "expected 5 bytes in output, got " + result.length);
        for (int i = 0; i < data.length; i++) {
            check(result[i] == data[i], "byte mismatch at index " + i);
        }
        ch.close();
    }

    static void testWriteZeroRemainingReturnsZero() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        WritableByteChannel ch = Channels.newChannel(out);
        ByteBuffer src = ByteBuffer.allocate(0);
        int n = ch.write(src);
        check(n == 0, "expected 0 for empty buffer, got " + n);
        check(out.size() == 0, "no bytes should be written for empty buffer");
        ch.close();
    }

    static void testWriteOnClosedChannelThrowsClosedChannelException() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        WritableByteChannel ch = Channels.newChannel(out);
        ch.close();
        try {
            ch.write(ByteBuffer.wrap(new byte[] {1}));
            throw new RuntimeException("expected ClosedChannelException");
        } catch (ClosedChannelException expected) {
            // expected
        }
        check(out.size() == 0, "no bytes should be written after close");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
