/*
 * @test
 * @summary Checks public behavior of Channels.newInputStream(...).read() for unsigned byte values and end of stream
 * @run main ChannelsWitupTest
 */

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class ChannelsWitupTest {

    public static void main(String[] args) throws IOException {
        testReadReturnsUnsignedBytes();
        testReadReturnsMinusOneAtEndOfStream();
        testReadEmptyChannelReturnsMinusOne();
    }

    private static void testReadReturnsUnsignedBytes() throws IOException {
        byte[] data = {0x41, (byte) 0x80, (byte) 0xFF, 0x00};
        ReadableByteChannel ch = Channels.newChannel(new ByteArrayInputStream(data));
        InputStream in = Channels.newInputStream(ch);
        check(in.read() == 0x41, "expected 0x41");
        check(in.read() == 0x80, "expected 0x80 unsigned");
        check(in.read() == 0xFF, "expected 0xFF unsigned");
        check(in.read() == 0x00, "expected 0x00");
    }

    private static void testReadReturnsMinusOneAtEndOfStream() throws IOException {
        byte[] data = {0x7F};
        ReadableByteChannel ch = Channels.newChannel(new ByteArrayInputStream(data));
        InputStream in = Channels.newInputStream(ch);
        check(in.read() == 0x7F, "expected 0x7F");
        check(in.read() == -1, "expected -1 at end of stream");
        check(in.read() == -1, "expected -1 repeated at end of stream");
    }

    private static void testReadEmptyChannelReturnsMinusOne() throws IOException {
        ReadableByteChannel ch = Channels.newChannel(new ByteArrayInputStream(new byte[0]));
        InputStream in = Channels.newInputStream(ch);
        check(in.read() == -1, "expected -1 for empty channel");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }
}
