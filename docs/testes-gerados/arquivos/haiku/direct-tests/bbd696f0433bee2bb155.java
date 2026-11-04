/*
 * @test
 * @summary Verifies that Socket.sendUrgentData on a socket obtained from a connected SocketChannel (SocketAdaptor) succeeds and that getChannel returns the adapted channel
 * @run main SocketAdaptorWitupTest
 */

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class SocketAdaptorWitupTest {

    public static void main(String[] args) throws Exception {
        InetAddress lo = InetAddress.getLoopbackAddress();
        try (ServerSocketChannel ssc = ServerSocketChannel.open()) {
            ssc.bind(new InetSocketAddress(lo, 0));
            try (SocketChannel client = SocketChannel.open(ssc.getLocalAddress())) {
                SocketChannel server = ssc.accept();
                try {
                    Socket s = client.socket();
                    if (s.getChannel() != client) {
                        throw new RuntimeException("getChannel did not return the adapted channel");
                    }
                    s.sendUrgentData(65);
                    s.sendUrgentData(0);
                    s.sendUrgentData(255);
                } finally {
                    if (server != null) {
                        server.close();
                    }
                }
            }
        }
    }
}
