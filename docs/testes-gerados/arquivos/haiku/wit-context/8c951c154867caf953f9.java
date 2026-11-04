/*
 * @test
 * @summary Verify that DatagramSocketAdaptor.connect(null) throws NullPointerException with the documented message
 * @modules java.base/sun.nio.ch
 * @run main DatagramSocketAdaptorWitupTest
 */

import java.net.DatagramSocket;
import java.net.SocketAddress;
import sun.nio.ch.DatagramSocketAdaptor;

public class DatagramSocketAdaptorWitupTest {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = DatagramSocketAdaptor.create(null);
        try {
            socket.connect((SocketAddress) null);
        } catch (NullPointerException e) {
            if (!"Address can't be null".equals(e.getMessage())) {
                throw new RuntimeException("unexpected message: " + e.getMessage());
            }
            return;
        }
        throw new RuntimeException("NullPointerException not thrown");
    }
}
