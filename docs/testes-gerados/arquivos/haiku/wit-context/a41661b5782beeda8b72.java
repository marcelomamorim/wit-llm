/*
 * @test
 * @summary Verifies null-buffer and closed-channel behavior of DatagramChannelImpl.write(ByteBuffer) through the public DatagramChannel API
 * @run main DatagramChannelImplWitupTest
 */

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;

public class DatagramChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testNullBufferThrowsNPE();
        testWriteOnClosedChannelThrowsClosedChannelException();
    }

    static void testNullBufferThrowsNPE() throws IOException {
        DatagramChannel ch = DatagramChannel.open();
        try {
            ByteBuffer nullBuf = null;
            try {
                ch.write(nullBuf);
                throw new RuntimeException("Expected NullPointerException for null buffer");
            } catch (NullPointerException expected) {
                // expected
            }
        } finally {
            ch.close();
        }
    }

    static void testWriteOnClosedChannelThrowsClosedChannelException() throws IOException {
        DatagramChannel ch = DatagramChannel.open();
        ch.close();
        ByteBuffer buf = ByteBuffer.allocate(4);
        try {
            ch.write(buf);
            throw new RuntimeException("Expected ClosedChannelException on closed channel");
        } catch (ClosedChannelException expected) {
            // expected
        }
    }
}
