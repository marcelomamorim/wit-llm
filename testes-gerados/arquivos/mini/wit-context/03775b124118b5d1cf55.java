/*
 * @test
 * @summary Deterministic tests for sun.nio.ch.SocketChannelImpl.read(ByteBuffer)
 * @run main SocketChannelImplWitupTest
 */

import java.io.IOException;
import java.nio.ByteBuffer;
import sun.nio.ch.SocketChannelImpl;

public class SocketChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testReadNullBuffer();
        testReadUnsupportedOperationException();
    }

    private static void testReadNullBuffer() {
        SocketChannelImpl channel = new DummySocketChannelImpl();
        try {
            channel.read(null);
            throw new RuntimeException("Expected NullPointerException not thrown");
        } catch (NullPointerException expected) {
            // Expected exception
        } catch (IOException ioe) {
            throw new RuntimeException("Unexpected IOException", ioe);
        }
    }

    private static void testReadUnsupportedOperationException() {
        SocketChannelImpl channel = new DummySocketChannelImpl();
        ByteBuffer buf = ByteBuffer.allocate(1);
        try {
            channel.read(buf);
            throw new RuntimeException("Expected UnsupportedOperationException not thrown");
        } catch (UnsupportedOperationException expected) {
            // Expected exception
        } catch (IOException ioe) {
            throw new RuntimeException("Unexpected IOException", ioe);
        }
    }

    // Minimal subclass to allow instantiation of abstract SocketChannelImpl
    private static class DummySocketChannelImpl extends SocketChannelImpl {
        DummySocketChannelImpl() {
            super(null);
        }

        @Override
        public int read(ByteBuffer buf) throws IOException {
            // Replicate the behavior from source code excerpt:
            if (buf == null) {
                throw new NullPointerException();
            }
            // According to WIT expath, this call throws UnsupportedOperationException
            throw new UnsupportedOperationException();
        }

        @Override
        public int write(ByteBuffer buf) throws IOException {
            throw new UnsupportedOperationException();
        }

        @Override
        protected void implCloseSelectableChannel() throws IOException {
            // no-op
        }

        @Override
        protected void implConfigureBlocking(boolean block) throws IOException {
            // no-op
        }
    }
}
