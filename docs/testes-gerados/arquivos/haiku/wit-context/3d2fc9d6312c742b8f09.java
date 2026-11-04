/*
 * @test
 * @summary Verify that setOOBInline always throws SocketException on SSL sockets
 * @run main BaseSSLSocketImplWitupTest
 */

import java.net.Socket;
import java.net.SocketException;
import javax.net.ssl.SSLSocketFactory;

public class BaseSSLSocketImplWitupTest {

    public static void main(String[] args) throws Exception {
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        Socket socket = factory.createSocket();
        try {
            checkThrows(socket, true);
            checkThrows(socket, false);
        } finally {
            socket.close();
        }
    }

    private static void checkThrows(Socket socket, boolean on) throws Exception {
        try {
            socket.setOOBInline(on);
        } catch (SocketException e) {
            String msg = e.getMessage();
            if (msg == null || !msg.contains("ineffective")) {
                throw new RuntimeException("unexpected message: " + msg);
            }
            return;
        }
        throw new RuntimeException("expected SocketException for on=" + on);
    }
}
