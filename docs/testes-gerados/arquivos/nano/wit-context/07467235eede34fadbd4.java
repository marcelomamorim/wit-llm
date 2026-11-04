/*
 * @test
 * @summary Tests the write(ByteBuffer) method of sun.nio.ch.SocketChannelImpl.
 * @run main SocketChannelImplWitupTest
 */

import java.io.IOException;
import java.nio.ByteBuffer;
import sun.nio.ch.SocketChannelImpl;

public class SocketChannelImplWitupTest {

    public static void main(String[] args) throws Exception {
        testWriteWithNullBuffer();
        testWriteWithNonNullBuffer();
    }

    private static void testWriteWithNullBuffer() {
        SocketChannelImpl channel = createSocketChannelImpl();
        try {
            channel.write(null);
            System.out.println("FAIL: Expected NullPointerException not thrown");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (IOException e) {
            System.out.println("FAIL: Unexpected IOException: " + e);
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testWriteWithNonNullBuffer() {
        SocketChannelImpl channel = createSocketChannelImpl();
        ByteBuffer buffer = ByteBuffer.allocate(10);
        try {
            int result = channel.write(buffer);
            System.out.println("PASS: write(ByteBuffer) returned: " + result);
        } catch (IOException e) {
            System.out.println("FAIL: IOException thrown: " + e);
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static SocketChannelImpl createSocketChannelImpl() {
        // Since the constructor is package-private and no public factory is evident,
        // we assume a mock or a minimal instantiation for testing purposes.
        // In real tests, this might be replaced with a proper factory or a subclass.
        return new SocketChannelImpl();
    }
}
