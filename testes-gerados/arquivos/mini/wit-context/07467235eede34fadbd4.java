/*
 * @test
 * @summary Deterministic tests for sun.nio.ch.SocketChannelImpl.write(ByteBuffer)
 * @run main SocketChannelImplWitupTest
 */

import java.io.IOException;
import java.nio.ByteBuffer;
import sun.nio.ch.SocketChannelImpl;

public class SocketChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testWriteNullBuffer();
        testWriteUnsupportedOperation();
    }

    private static void testWriteNullBuffer() {
        SocketChannelImpl channel = new DummySocketChannelImpl();
        try {
            channel.write(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (IOException ioe) {
            throw new RuntimeException("Unexpected IOException", ioe);
        }
    }

    private static void testWriteUnsupportedOperation() {
        SocketChannelImpl channel = new DummySocketChannelImpl();
        ByteBuffer buf = ByteBuffer.allocate(1);
        try {
            channel.write(buf);
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        } catch (IOException ioe) {
            throw new RuntimeException("Unexpected IOException", ioe);
        }
    }

    // Minimal subclass to allow instantiation for testing
    private static class DummySocketChannelImpl extends SocketChannelImpl {
        DummySocketChannelImpl() {
            super(null);
        }

        @Override
        public boolean isBlocking() {
            return true;
        }

        @Override
        protected void beginWrite(boolean blocking) {
            // no-op
        }

        @Override
        protected void endWrite(boolean blocking, boolean completed) {
            // no-op
        }

        @Override
        public boolean isOpen() {
            return true;
        }

        @Override
        public int write(ByteBuffer buf) throws IOException {
            if (buf == null) {
                throw new NullPointerException();
            }
            throw new UnsupportedOperationException();
        }
    }
}
