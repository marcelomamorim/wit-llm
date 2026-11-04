/* @test    @summary Test SocketChannelImpl.read(ByteBuffer) for normal and edge cases    @run main SocketChannelImplWitupTest */

import java.io.IOException;
import java.nio.ByteBuffer;
import sun.nio.ch.SocketChannelImpl;

public class SocketChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testReadNormal();
        testReadInputClosed();
        testReadNullBuffer();
    }

    static void testReadNormal() throws IOException {
        SocketChannelImpl channel = new SocketChannelImpl();
        ByteBuffer buffer = ByteBuffer.allocate(10);
        int result = channel.read(buffer);
        if (result < 0) {
            throw new AssertionError("Expected non-negative result, got " + result);
        }
        if (buffer.position() != result) {
            throw new AssertionError("Buffer position mismatch: " + buffer.position() + " != " + result);
        }
    }

    static void testReadInputClosed() throws IOException {
        SocketChannelImpl channel = new SocketChannelImpl();
        // Simulate input closed
        channel.isInputClosed = true;
        ByteBuffer buffer = ByteBuffer.allocate(10);
        int result = channel.read(buffer);
        if (result != -1) {
            throw new AssertionError("Expected EOF (-1), got " + result);
        }
    }

    static void testReadNullBuffer() {
        try {
            SocketChannelImpl channel = new SocketChannelImpl();
            channel.read(null);
            throw new AssertionError("Expected NullPointerException");
        } catch (NullPointerException expected) {
            // expected
        } catch (Exception e) {
            throw new AssertionError("Unexpected exception: " + e);
        }
    }
}
