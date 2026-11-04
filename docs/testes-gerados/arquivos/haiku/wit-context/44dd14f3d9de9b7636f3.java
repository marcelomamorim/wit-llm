/*
 * @test
 * @summary Checks Channels.newInputStream null rejection and EOF behavior for an empty readable channel
 * @run main ChannelsWitupTest
 */
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class ChannelsWitupTest {
    public static void main(String[] args) throws Exception {
        testNullChannelThrowsNPE();
        testEmptyChannelReturnsEof();
    }

    static void testNullChannelThrowsNPE() {
        try {
            Channels.newInputStream(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException");
    }

    static void testEmptyChannelReturnsEof() throws Exception {
        ReadableByteChannel ch = new ReadableByteChannel() {
            public int read(ByteBuffer dst) {
                return -1;
            }
            public boolean isOpen() {
                return true;
            }
            public void close() {
            }
        };
        InputStream in = Channels.newInputStream(ch);
        if (in == null) {
            throw new RuntimeException("newInputStream returned null");
        }
        if (in.read() != -1) {
            throw new RuntimeException("expected EOF from empty channel");
        }
    }
}
