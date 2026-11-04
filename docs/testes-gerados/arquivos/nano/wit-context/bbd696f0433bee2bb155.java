/*
 * @test
 * @summary Test for sendUrgentData method in SocketAdaptor.
 * @run main SocketAdaptorWitupTest
 */

import java.io.IOException;
import sun.nio.ch.SocketAdaptor;
import sun.nio.ch.SocketChannelImpl;

public class SocketAdaptorWitupTest {

    public static void main(String[] args) throws Exception {
        testSendUrgentData_Normal();
        testSendUrgentData_BufferFull();
    }

    private static void testSendUrgentData_Normal() throws Exception {
        SocketChannelImpl sc = createSocketChannelImpl();
        SocketAdaptor adaptor = new SocketAdaptor(sc);
        int data = 42;
        // Mock sendOutOfBandData to simulate successful send
        overrideSendOutOfBandData(sc, 1);
        try {
            adaptor.sendUrgentData(data);
            System.out.println("testSendUrgentData_Normal passed");
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException", e);
        }
    }

    private static void testSendUrgentData_BufferFull() throws Exception {
        SocketChannelImpl sc = createSocketChannelImpl();
        SocketAdaptor adaptor = new SocketAdaptor(sc);
        int data = 42;
        // Mock sendOutOfBandData to simulate buffer full (return 0)
        overrideSendOutOfBandData(sc, 0);
        try {
            adaptor.sendUrgentData(data);
            throw new AssertionError("Expected IOException not thrown");
        } catch (IOException e) {
            if (!e.getMessage().equals("Socket buffer full")) {
                throw new AssertionError("Unexpected exception message", e);
            }
            System.out.println("testSendUrgentData_BufferFull passed");
        }
    }

    private static SocketChannelImpl createSocketChannelImpl() {
        // Since SocketChannelImpl constructor is package-private, we simulate with a stub
        return new SocketChannelImplStub();
    }

    private static void overrideSendOutOfBandData(SocketChannelImpl sc, int returnValue) {
        if (sc instanceof SocketChannelImplStub) {
            ((SocketChannelImplStub) sc).setSendOutOfBandDataReturn(returnValue);
        }
    }

    // Stub class to simulate SocketChannelImpl behavior
    static class SocketChannelImplStub extends SocketChannelImpl {
        private int returnValue = -1;
        public SocketChannelImplStub() {
            super(null); // super constructor requires a Socket
        }
        public void setSendOutOfBandDataReturn(int value) {
            this.returnValue = value;
        }
        @Override
        public int sendOutOfBandData(byte data) {
            return returnValue;
        }
    }
}
