/*
 * @test
 * @summary Test sun.nio.ch.SocketAdaptor.setSoTimeout(int) for valid and invalid inputs
 * @run main SocketAdaptorWitupTest
 */

import sun.nio.ch.SocketAdaptor;
import sun.nio.ch.SocketChannelImpl;
import java.net.SocketException;

public class SocketAdaptorWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy SocketChannelImpl for testing
        SocketChannelImpl sc = DummySocketChannelImpl.create();
        SocketAdaptor socketAdaptor = SocketAdaptor.create(sc);

        // Test valid timeout values
        socketAdaptor.setSoTimeout(0);
        socketAdaptor.setSoTimeout(1000);
        socketAdaptor.setSoTimeout(Integer.MAX_VALUE);

        // Test invalid timeout value (negative) - expect IllegalArgumentException
        try {
            socketAdaptor.setSoTimeout(-1);
            throw new RuntimeException("Expected IllegalArgumentException for negative timeout");
        } catch (IllegalArgumentException e) {
            // expected
        }
    }
}

// Minimal dummy implementation to allow SocketAdaptor creation
final class DummySocketChannelImpl extends SocketChannelImpl {
    private DummySocketChannelImpl() {
        super(null);
    }

    static DummySocketChannelImpl create() {
        return new DummySocketChannelImpl();
    }

    @Override
    public boolean isBlocking() {
        return true;
    }

    @Override
    public Object blockingLock() {
        return this;
    }

    @Override
    public void connect(java.net.SocketAddress remote) {
        // no-op
    }

    @Override
    public void configureBlocking(boolean block) {
        // no-op
    }

    @Override
    public boolean connect(java.net.SocketAddress remote) {
        return true;
    }

    @Override
    public java.net.SocketAddress getLocalAddress() {
        return null;
    }

    @Override
    public java.net.SocketAddress getRemoteAddress() {
        return null;
    }

    @Override
    public boolean isConnected() {
        return true;
    }

    @Override
    public boolean isConnectionPending() {
        return false;
    }

    @Override
    public void close() {
        // no-op
    }

    @Override
    public int read(java.nio.ByteBuffer dst) {
        return 0;
    }

    @Override
    public long read(java.nio.ByteBuffer[] dsts, int offset, int length) {
        return 0L;
    }

    @Override
    public int write(java.nio.ByteBuffer src) {
        return 0;
    }

    @Override
    public long write(java.nio.ByteBuffer[] srcs, int offset, int length) {
        return 0L;
    }

    @Override
    protected void implCloseSelectableChannel() {
        // no-op
    }

    @Override
    protected void implConfigureBlocking(boolean block) {
        // no-op
    }
}
