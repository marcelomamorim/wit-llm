/*
 * @test
 * @summary Tests the write(ByteBuffer) method of sun.nio.ch.DatagramChannelImpl.
 * @run main DatagramChannelImplWitupTest
 */

import java.io.IOException;
import java.nio.ByteBuffer;
import sun.nio.ch.DatagramChannelImpl;

public class DatagramChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testWriteWithNullBuffer();
        testWriteWithValidBuffer();
    }

    private static void testWriteWithNullBuffer() {
        try {
            DatagramChannelImpl channel = createChannel();
            channel.write(null);
            System.err.println("Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("NullPointerException correctly thrown for null buffer");
        } catch (IOException e) {
            System.err.println("Unexpected IOException: " + e);
        }
    }

    private static void testWriteWithValidBuffer() {
        try {
            DatagramChannelImpl channel = createChannel();
            ByteBuffer buffer = ByteBuffer.allocate(10);
            int result = channel.write(buffer);
            System.out.println("write() returned: " + result);
        } catch (IOException e) {
            System.err.println("IOException during write: " + e);
        }
    }

    private static DatagramChannelImpl createChannel() throws IOException {
        // Create a dummy FileDescriptor for testing purposes
        java.io.FileDescriptor fd = new java.io.FileDescriptor();
        // Instantiate with a SelectorProvider (using the default)
        sun.nio.ch.SelectorProvider sp = sun.nio.ch.SelectorProvider.provider();
        // Create the DatagramChannelImpl with the FileDescriptor
        return new DatagramChannelImpl(sp, fd);
    }
}
