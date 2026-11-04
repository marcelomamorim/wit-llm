/*
 * @test
 * @summary Verifies public observable behavior of SocketChannelImpl.write(ByteBuffer) for null and closed channels
 * @run main SocketChannelImplWitupTest
 */

import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SocketChannel;

public class SocketChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testNullBufferThrowsNullPointerException();
        testWriteOnClosedChannelThrowsClosedChannelException();
    }

    static void testNullBufferThrowsNullPointerException() throws Exception {
        SocketChannel sc = SocketChannel.open();
        try {
            ByteBuffer nullBuf = null;
            boolean thrown = false;
            try {
                sc.write(nullBuf);
            } catch (NullPointerException expected) {
                thrown = true;
            }
            if (!thrown) {
                throw new AssertionError("Expected NullPointerException for null buffer");
            }
        } finally {
            sc.close();
        }
    }

    static void testWriteOnClosedChannelThrowsClosedChannelException() throws Exception {
        SocketChannel sc = SocketChannel.open();
        sc.close();
        boolean thrown = false;
        try {
            sc.write(ByteBuffer.allocate(1));
        } catch (ClosedChannelException expected) {
            thrown = true;
        }
        if (!thrown) {
            throw new AssertionError("Expected ClosedChannelException on closed channel");
        }
    }
}
