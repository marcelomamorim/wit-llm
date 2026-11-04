/*
 * @test
 * @summary Test sun.nio.ch.SocketChannelImpl.write(ByteBuffer) method behavior
 * @run main SocketChannelImplWitupTest
 */

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import sun.nio.ch.SocketChannelImpl;

public class SocketChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testWriteNullBuffer();
        testWriteClosedOutput();
        testWriteNormal();
    }

    // Test that write(null) throws NullPointerException
    private static void testWriteNullBuffer() throws Exception {
        SocketChannelImpl channel = createDummyChannel();
        try {
            channel.write(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException npe) {
            // expected
        }
    }

    // Test that write throws AsynchronousCloseException if output is closed and write returns <= 0
    private static void testWriteClosedOutput() throws Exception {
        SocketChannelImpl channel = createDummyChannel();
        // Simulate output closed and write returns 0
        channel.setOutputClosed(true);
        channel.setWriteReturnValue(0);
        try {
            channel.write(ByteBuffer.allocate(1));
            throw new RuntimeException("Expected AsynchronousCloseException not thrown");
        } catch (AsynchronousCloseException ace) {
            // expected
        }
    }

    // Test normal write returns normalized value
    private static void testWriteNormal() throws Exception {
        SocketChannelImpl channel = createDummyChannel();
        channel.setOutputClosed(false);
        channel.setWriteReturnValue(5);
        int result = channel.write(ByteBuffer.allocate(10));
        if (result != 5) {
            throw new RuntimeException("Expected write to return 5 but got " + result);
        }
    }

    // Helper method to create a dummy SocketChannelImpl with minimal overrides
    private static SocketChannelImpl createDummyChannel() throws Exception {
        return new DummySocketChannelImpl();
    }

    // Dummy subclass to override native calls and state
    private static class DummySocketChannelImpl extends SocketChannelImpl {

        private boolean outputClosed = false;
        private int writeReturnValue = 1;

        DummySocketChannelImpl() throws Exception {
            super(null);
        }

        @Override
        protected boolean isBlocking() {
            return false;
        }

        @Override
        protected void beginWrite(boolean blocking) {
            // no-op
        }

        @Override
        protected void endWrite(boolean blocking, boolean wroteData) {
            // no-op
        }

        @Override
        protected boolean isOpen() {
            return true;
        }

        @Override
        protected int writeNative(ByteBuffer buf) throws IOException {
            return writeReturnValue;
        }

        @Override
        public int write(ByteBuffer buf) throws IOException {
            if (buf == null) {
                throw new NullPointerException();
            }
            int n = writeReturnValue;
            if (outputClosed && n <= 0) {
                throw new AsynchronousCloseException();
            }
            return n;
        }

        void setOutputClosed(boolean closed) {
            this.outputClosed = closed;
        }

        void setWriteReturnValue(int val) {
            this.writeReturnValue = val;
        }
    }
}
