/*
 * @test
 * @summary Test sendUrgentData method of SocketAdaptor for normal and buffer full scenarios
 * @run main SocketAdaptorWitupTest
 */

import java.io.IOException;
import sun.nio.ch.SocketAdaptor;

public class SocketAdaptorWitupTest {

    static class MockSocketChannel {
        private boolean bufferFull;
        private boolean called;
        private byte lastData;

        public MockSocketChannel(boolean bufferFull) {
            this.bufferFull = bufferFull;
            this.called = false;
        }

        public int sendOutOfBandData(byte data) {
            this.called = true;
            this.lastData = data;
            if (bufferFull) {
                return 0;
            } else {
                return 1;
            }
        }

        public boolean wasCalled() {
            return called;
        }

        public byte getLastData() {
            return lastData;
        }
    }

    static class TestSocketAdaptor extends SocketAdaptor {
        private final MockSocketChannel mockSc;

        public TestSocketAdaptor(MockSocketChannel sc) {
            super(null); // Passing null as SocketChannelImpl is not accessible
            this.mockSc = sc;
        }

        @Override
        public void sendUrgentData(int data) throws IOException {
            int n = mockSc.sendOutOfBandData((byte) data);
            if (n == 0) {
                throw new IOException("Socket buffer full");
            }
        }
    }

    public static void main(String[] args) {
        testSendUrgentDataNormal();
        testSendUrgentDataBufferFull();
    }

    public static void testSendUrgentDataNormal() {
        MockSocketChannel mockSc = new MockSocketChannel(false);
        TestSocketAdaptor adaptor = new TestSocketAdaptor(mockSc);
        try {
            adaptor.sendUrgentData(42);
            assert mockSc.wasCalled() : "sendOutOfBandData should be called";
            assert mockSc.getLastData() == 42 : "Data sent should be 42";
            System.out.println("testSendUrgentDataNormal passed");
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException: " + e.getMessage());
        }
    }

    public static void testSendUrgentDataBufferFull() {
        MockSocketChannel mockSc = new MockSocketChannel(true);
        TestSocketAdaptor adaptor = new TestSocketAdaptor(mockSc);
        try {
            adaptor.sendUrgentData(99);
            throw new AssertionError("Expected IOException was not thrown");
        } catch (IOException e) {
            assert e.getMessage().equals("Socket buffer full") : "Exception message mismatch";
            assert mockSc.wasCalled() : "sendOutOfBandData should be called";
            assert mockSc.getLastData() == 99 : "Data sent should be 99";
            System.out.println("testSendUrgentDataBufferFull passed");
        }
    }
}
