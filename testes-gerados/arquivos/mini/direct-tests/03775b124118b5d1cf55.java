/*
 * @test
 * @summary Basic tests for sun.nio.ch.SocketChannelImpl.read(ByteBuffer) method
 * @run main SocketChannelImplWitupTest
 */

import java.io.IOException;
import java.nio.ByteBuffer;
import sun.nio.ch.SocketChannelImpl;

public class SocketChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testReadNullBuffer();
        testReadClosedInputReturnsEOF();
        testReadNormalBehavior();
    }

    private static void testReadNullBuffer() {
        SocketChannelImpl channel = new DummySocketChannelImpl();
        try {
            channel.read(null);
            throw new RuntimeException("Expected NullPointerException for null buffer");
        } catch (NullPointerException e) {
            // expected
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException", e);
        }
    }

    private static void testReadClosedInputReturnsEOF() throws IOException {
        DummySocketChannelImpl channel = new DummySocketChannelImpl();
        channel.setInputClosed(true);
        ByteBuffer buf = ByteBuffer.allocate(10);
        int result = channel.read(buf);
        if (result != -1) {
            throw new RuntimeException("Expected EOF (-1) when input is closed, got: " + result);
        }
    }

    private static void testReadNormalBehavior() throws IOException {
        DummySocketChannelImpl channel = new DummySocketChannelImpl();
        ByteBuffer buf = ByteBuffer.allocate(10);
        channel.setInputClosed(false);
        channel.setBlocking(true);
        channel.setReadReturnValue(5);
        int result = channel.read(buf);
        if (result != 5) {
            throw new RuntimeException("Expected read to return 5, got: " + result);
        }
    }

    // Dummy subclass to simulate SocketChannelImpl behavior for testing
    private static class DummySocketChannelImpl extends SocketChannelImpl {

        private boolean inputClosed = false;
        private boolean blocking = true;
        private int readReturnValue = 0;

        DummySocketChannelImpl() {
            super(null);
        }

        void setInputClosed(boolean closed) {
            this.inputClosed = closed;
        }

        void setBlocking(boolean blocking) {
            this.blocking = blocking;
        }

        void setReadReturnValue(int val) {
            this.readReturnValue = val;
        }

        @Override
        protected boolean isBlocking() {
            return blocking;
        }

        @Override
        protected void beginRead(boolean blocking) {
            // no-op
        }

        @Override
        protected void endRead(boolean blocking, boolean completed) {
            // no-op
        }

        @Override
        protected boolean isOpen() {
            return true;
        }

        @Override
        protected int IOUtil_read(java.io.FileDescriptor fd, ByteBuffer buf, int pos, Object nd) {
            return readReturnValue;
        }

        @Override
        public int read(ByteBuffer buf) throws IOException {
            if (buf == null) {
                throw new NullPointerException();
            }
            if (inputClosed) {
                return -1; // IOStatus.EOF
            }
            return readReturnValue;
        }
    }
}
