/*
 * @test
 * @summary Test sun.nio.ch.SocketAdaptor.sendUrgentData(int) behavior
 * @run main SocketAdaptorWitupTest
 */

import sun.nio.ch.SocketAdaptor;
import sun.nio.ch.SocketChannelImpl;
import java.io.IOException;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.SocketChannel;

public class SocketAdaptorWitupTest {

    // Minimal stub subclass to control sendOutOfBandData behavior
    static class StubSocketChannelImpl extends SocketChannelImpl {
        private int sendResult;

        protected StubSocketChannelImpl() throws IOException {
            super(null);
        }

        void setSendResult(int n) {
            sendResult = n;
        }

        @Override
        public int sendOutOfBandData(byte b) throws IOException {
            return sendResult;
        }

        // Implement abstract methods with minimal stubs
        @Override
        public SocketChannel bind(SocketAddress local) throws IOException {
            return this;
        }

        @Override
        public boolean connect(SocketAddress remote) throws IOException {
            return true;
        }

        @Override
        public boolean finishConnect() throws IOException {
            return true;
        }

        @Override
        public int read(java.nio.ByteBuffer dst) throws IOException {
            return 0;
        }

        @Override
        public int write(java.nio.ByteBuffer src) throws IOException {
            return 0;
        }

        @Override
        protected void implCloseSelectableChannel() throws IOException {
        }

        @Override
        protected void implConfigureBlocking(boolean block) throws IOException {
        }
    }

    public static void main(String[] args) throws Exception {
        testSendUrgentDataSuccess();
        testSendUrgentDataBufferFull();
    }

    private static void testSendUrgentDataSuccess() throws Exception {
        StubSocketChannelImpl stub = new StubSocketChannelImpl();
        stub.setSendResult(1); // simulate successful send
        SocketAdaptor socket = SocketAdaptor.create(stub);

        // Should not throw
        socket.sendUrgentData(0x7F);
    }

    private static void testSendUrgentDataBufferFull() throws Exception {
        StubSocketChannelImpl stub = new StubSocketChannelImpl();
        stub.setSendResult(0); // simulate buffer full
        SocketAdaptor socket = SocketAdaptor.create(stub);

        try {
            socket.sendUrgentData(0x7F);
            throw new RuntimeException("Expected IOException not thrown");
        } catch (IOException e) {
            if (!"Socket buffer full".equals(e.getMessage())) {
                throw new RuntimeException("Unexpected IOException message: " + e.getMessage());
            }
        }
    }
}
