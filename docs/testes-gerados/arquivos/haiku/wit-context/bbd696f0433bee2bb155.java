/*
 * @test
 * @summary Checks sendUrgentData on a socket obtained from a connected SocketChannel and its IOException after close
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
        try (ServerSocketChannel server = ServerSocketChannel.open()) {
            server.bind(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0));
            InetSocketAddress addr = (InetSocketAddress) server.getLocalAddress();
            try (SocketChannel client = SocketChannel.open(addr);
                 SocketChannel accepted = server.accept()) {
                Socket socket = client.socket();
                if (socket.getChannel() != client) {
                    throw new RuntimeException("getChannel() did not return the adapted channel");
                }
                socket.sendUrgentData(0x41);
                socket.sendUrgentData(0);
                socket.close();
                boolean thrown = false;
                try {
                    socket.sendUrgentData(0x42);
                } catch (IOException e) {
                    thrown = true;
                }
                if (!thrown) {
                    throw new RuntimeException("sendUrgentData on closed socket did not throw IOException");
                }
            }
        }
    }
}
