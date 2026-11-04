/*
 * @test
 * @summary Tests write(ByteBuffer) of the channel returned by Channels.newChannel(OutputStream)
 * @run main ChannelsWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.WritableByteChannel;

public class ChannelsWitupTest {

    public static void main(String[] args) throws Exception {
        testWriteReturnsCountAndContent();
        testEmptyBufferReturnsZero();
        testLargeWriteTransfersAllBytes();
        testWriteAfterCloseThrows();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testWriteReturnsCountAndContent() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        WritableByteChannel ch = Channels.newChannel(bos);
        ByteBuffer src = ByteBuffer.wrap("hello".getBytes("US-ASCII"));
        int n = ch.write(src);
        check(n == 5, "expected 5 bytes written but was " + n);
        check(!src.hasRemaining(), "source buffer should be fully consumed");
        check("hello".equals(new String(bos.toByteArray(), "US-ASCII")),
              "unexpected stream content");
    }

    static void testEmptyBufferReturnsZero() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        WritableByteChannel ch = Channels.newChannel(bos);
        int n = ch.write(ByteBuffer.allocate(0));
        check(n == 0, "expected 0 for empty buffer but was " + n);
        check(bos.size() == 0, "stream should remain empty");
    }

    static void testLargeWriteTransfersAllBytes() throws Exception {
        int size = 20000;
        byte[] data = new byte[size];
        for (int i = 0; i < size; i++) {
            data[i] = (byte) (i % 256);
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        WritableByteChannel ch = Channels.newChannel(bos);
        ByteBuffer src = ByteBuffer.wrap(data);
        int n = ch.write(src);
        check(n == size, "expected " + size + " but was " + n);
        check(!src.hasRemaining(), "source buffer should be fully consumed");
        byte[] out = bos.toByteArray();
        check(out.length == size, "unexpected output length " + out.length);
        for (int i = 0; i < size; i++) {
            check(out[i] == data[i], "mismatch at index " + i);
        }
    }

    static void testWriteAfterCloseThrows() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        WritableByteChannel ch = Channels.newChannel(bos);
        ch.close();
        boolean thrown = false;
        try {
            ch.write(ByteBuffer.allocate(1));
        } catch (ClosedChannelException e) {
            thrown = true;
        }
        check(thrown, "expected ClosedChannelException after close");
    }
}
