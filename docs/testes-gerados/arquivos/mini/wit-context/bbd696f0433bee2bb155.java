/*
 * @test
 * @summary Test sun.nio.ch.SocketAdaptor.sendUrgentData(int) for expected exceptions and behavior
 * @run main SocketAdaptorWitupTest
 */

import sun.nio.ch.SocketAdaptor;
import sun.nio.ch.SocketChannelImpl;
import java.io.IOException;
import java.net.Socket;

public class SocketAdaptorWitupTest {
    public static void main(String[] args) throws Exception {
        // Create a dummy SocketChannelImpl subclass to test sendUrgentData
        SocketChannelImpl sc = new DummySocketChannelImpl();
        Socket socket = SocketAdaptor.create(sc);

        // Test sendUrgentData with data that triggers sendOutOfBandData returning 0 -> IOException
        try {
            socket.sendUrgentData(0xFF);
            throw new RuntimeException("Expected IOException not thrown");
        } catch (IOException e) {
            if (!"Socket buffer full".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected IOException message: " + e.getMessage());
            }
        }

        // Test sendUrgentData with data that triggers sendOutOfBandData returning > 0 -> no exception
        ((DummySocketChannelImpl) sc).setSendOutOfBandDataReturnValue(1);
        socket.sendUrgentData(0x01); // should not throw
    }

    // Minimal dummy subclass to simulate sendOutOfBandData behavior
    static class DummySocketChannelImpl extends SocketChannelImpl {
        private int sendOutOfBandDataReturnValue = 0;

        DummySocketChannelImpl() throws Exception {
            super(null, null, -1);
        }

        void setSendOutOfBandDataReturnValue(int val) {
            this.sendOutOfBandDataReturnValue = val;
        }

        @Override
        public int sendOutOfBandData(byte b) {
            if (sendOutOfBandDataReturnValue == 0) {
                return 0; // simulate buffer full
            } else {
                return sendOutOfBandDataReturnValue;
            }
        }

        // Stub implementations for abstract methods
        @Override
        public boolean isConnected() { return true; }
        @Override
        public boolean isConnectionPending() { return false; }
        @Override
        public boolean connect(java.net.SocketAddress remote) { return true; }
        @Override
        public boolean finishConnect() { return true; }
        @Override
        public int read(java.nio.ByteBuffer dst) { return 0; }
        @Override
        public long read(java.nio.ByteBuffer[] dsts, int offset, int length) { return 0; }
        @Override
        public int write(java.nio.ByteBuffer src) { return 0; }
        @Override
        public long write(java.nio.ByteBuffer[] srcs, int offset, int length) { return 0; }
        @Override
        protected void implCloseSelectableChannel() { }
        @Override
        protected void implConfigureBlocking(boolean block) { }
    }
}
